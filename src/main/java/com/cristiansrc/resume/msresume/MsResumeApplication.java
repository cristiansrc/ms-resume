package com.cristiansrc.resume.msresume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class MsResumeApplication {

	private MsResumeApplication() {
	}

	public static void main(final String[] args) {
		SpringApplication.run(MsResumeApplication.class, args);
	}

}
