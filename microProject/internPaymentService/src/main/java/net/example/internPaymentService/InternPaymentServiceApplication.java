package net.example.internPaymentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class InternPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternPaymentServiceApplication.class, args);
	}

}
