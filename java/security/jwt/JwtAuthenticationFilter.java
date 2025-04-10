package Offime.Offime.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    //Authorization
    @Value("${jwt.header}") private String HEADER_STRING;

    //Bearer
    @Value("${jwt.prefix} ") private String TOKEN_PREFIX;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Thread currentThread = Thread.currentThread();
        log.info("현재 실행 중인 스레드: " + currentThread.getName());

        // get token
        String header = request.getHeader(HEADER_STRING); //헤더에서 키값이 Authorization인 헤더를 가져온다
        String username = null;
        String authToken = null;

        //토큰이 있는 경우
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX,"");
            log.info("authToken:{}",authToken);
            try {
                username = this.jwtTokenUtil.getUsernameFromToken(authToken); //JWT로 부터 사용자 아이디(username)를 추출
                log.info("username:{}",username);
            } catch (IllegalArgumentException ex) {
                log.info("fail get user id");
                ex.printStackTrace();
            } catch (ExpiredJwtException ex) {
                log.info("Token expired");
                ex.printStackTrace();
            } catch (MalformedJwtException ex) {
                log.info("Invalid JWT !!");
                System.out.println();
                ex.printStackTrace();
            } catch (Exception e) {
                log.info("Unable to get JWT Token !!");
                e.getStackTrace();
            }

        }
        //토큰이 없는 경우
        else {
            log.info("JWT does not begin with Bearer !!");
        }

        //SecurityContextHolder : 현재 실행 중인 요청의 보안 컨텍스트(Security Context:현재 사용자 인증 정보 / 사용자 권한 / 사용자 인증 여부(로그인상태))를 관리하는 객체입니다.
        //이 객체는 애플리케이션의 어느 곳에서든 현재 사용자의 보안 정보를 가져올 수 있도록 해줍니다.
        //getAuthentication() : 현재 사용자의 인증 정보(아이디, 권한, 인증 여부 등)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { //아직 인증되지 않은 상태일 때의 경우(JWT에서 사용자 아이디를 성공적으로 가져왔고, 현재 SecurityContext에 인증 정보가 없는 경우)
            //log.info(SecurityContextHolder.getContext().getAuthentication().getName()); //현재 사용자 아이디
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username); //DB에서 사용자 정보 조회
            if (this.jwtTokenUtil.validateToken(authToken, userDetails)) { //JWT가 유효한지 확인

                //인증 토큰(권한 정보 가짐)
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //userDetails.getAuthorities()를 통해 사용자의 권한을 설정합니다.

                authenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); //매우 중요 : SecurityContextHolder를 사용하여 현재 요청에 대한 인증 정보(Security Context)를 설정

            } else {
                log.info("Invalid JWT Token !!");
            }
        } else {
            log.info("JWT Token is either missing or already authenticated.");
        }
        filterChain.doFilter(request, response);
    }

}
