package net.nvsoftware.internProductService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class InternProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternProductServiceApplication.class, args);
	}

}
