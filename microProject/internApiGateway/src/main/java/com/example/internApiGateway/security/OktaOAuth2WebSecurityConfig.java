package com.example.internApiGateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class OktaOAuth2WebSecurityConfig {

    @Value("${okta.oauth2.issuer}")
    private String issuerUri;

    @Value("${okta.oauth2.client-id}")
    private String clientId;

    @Value("${okta.oauth2.client-secret}")
    private String clientSecret;

    @Value("${okta.oauth2.redirect-uri}")
    private String redirectUri;

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistrations
                .fromIssuerLocation(issuerUri)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectUri(redirectUri)
                .scope("openid", "profile", "email", "offline_access")
                .build();

        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
    }
    //配置Spring WebFlux 应用的安全策略
    //ServerHttpSecurity是配置 WebFlux 安全性的入口。
    // 它提供了一组方法，用于定义不同的安全策略（如授权规则、登录方式等）
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
                .oauth2Login(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));
        return http.build();
    }
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//                .csrf(csrf -> csrf.disable())  // 使用新的语法禁用 CSRF
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers("/eureka/**").permitAll()  // 允许对 /eureka/** 的无认证访问
//                        .anyExchange().authenticated())  // 其他请求需要认证
//                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);  // 使用 JWT 进行认证
//        return http.build();
//    }

}
