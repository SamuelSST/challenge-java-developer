package br.com.neurotech.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.com.neurotech.challenge.repository")
public class ChallengeJavaDeveloperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeJavaDeveloperApplication.class, args);
	}

}
