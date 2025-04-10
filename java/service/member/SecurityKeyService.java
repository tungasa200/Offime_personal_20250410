package Offime.Offime.service.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SecurityKeyService {

    @Value("${securityKey}")
    private String securityKey;

//    private static final Logger logger = LoggerFactory.getLogger(SecurityKeyService.class);

//    securityKey 생성 -
//    public SecurityKeyService() {
//        generateKey();
//    }
//
//    public void generateKey() {
//        securityKey = UUID.randomUUID().toString();
//        logger.info("Generated new securityKey: {}", securityKey);
//    }
//
//    @Scheduled(fixedRate = 3600000)
//    public void refreshKey() {
//        generateKey();
//    }

    public String getSecurityKey() {
        return securityKey;
    }

    public boolean validateSecurityKey(String inputKey) {
        return securityKey.equals(inputKey);
    }

}
