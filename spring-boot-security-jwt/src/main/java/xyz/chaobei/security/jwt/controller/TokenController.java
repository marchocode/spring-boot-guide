package xyz.chaobei.security.jwt.controller;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TokenController {

    @Autowired
    private JwtEncoder jwtEncoder;

    @PostMapping("/login")
    public String login() {

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet
                .builder()
                .subject("user")
                .id(UUID.randomUUID().toString())
                .issuer("spring")
                .build();

        JwtEncoderParameters from = JwtEncoderParameters.from(jwtClaimsSet);
        return jwtEncoder.encode(from).getTokenValue();
    }


}
