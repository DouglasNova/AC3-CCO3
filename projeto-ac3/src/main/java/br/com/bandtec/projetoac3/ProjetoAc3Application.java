package br.com.bandtec.projetoac3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProjetoAc3Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoAc3Application.class, args);
	}

}
