package Offime.Offime.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import jakarta.servlet.MultipartConfigElement;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("10MB"));
        factory.setMaxRequestSize(DataSize.parse("10MB"));
        return factory.createMultipartConfig();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);

        registry.addMapping("/images/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // 🛠 추가: 정적 리소스 핸들러 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // toUri = OS에 따라 적절한 URI로 변환
        // Paths.get = OS에 따라 적절한 경로로 변환
        // System.getProperty("user.dir") = 프로젝트의 루트 경로를 가져옴
        String uploadPath = Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString();

        // addResourceHandler = URL 요청 패턴 처리
        // addResourceLocations() = 요청된걸 어떤거에서 가져올지 정해줌
        // /uploads/ 로 시작하는 모든 요청을 uploadPath로 변환
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/IntelliJ/Offime/uploads/");
    }

}
