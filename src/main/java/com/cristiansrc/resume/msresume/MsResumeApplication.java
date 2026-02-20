package com.cristiansrc.resume.msresume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MsResumeApplication {

	private MsResumeApplication() {
	}

	public static void main(final String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "80876Samy"; // Cambia esto por tu contraseña real
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println("Encoded password: " + encodedPassword);
		SpringApplication.run(MsResumeApplication.class, args);
	}

}
