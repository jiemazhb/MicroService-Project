package com.example.internApiGateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;


@SpringBootApplication
public class InternApiGatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(InternApiGatewayApplication.class, args);

	}
	//熔断器 当其它的微服务死机时候，它会做出相应的反应
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
		return factory -> factory.configureDefault(
			id -> new Resilience4JConfigBuilder(id).circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()).build()
		);
	}
	// 限制每秒请求次数
	@Bean
	KeyResolver userIdSolver(){
		return exchange -> Mono.just("userId");
	}

}
