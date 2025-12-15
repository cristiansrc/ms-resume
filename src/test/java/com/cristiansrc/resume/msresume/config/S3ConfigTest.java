package com.cristiansrc.resume.msresume.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import software.amazon.awssdk.services.s3.S3Client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = S3Config.class)
@TestPropertySource(properties = {
    "aws.s3.access-key=test-key",
    "aws.s3.secret-key=test-secret",
    "aws.s3.region=us-east-1"
})
class S3ConfigTest {

    @Autowired
    private S3Client s3Client;

    @Test
    void s3Client() {
        assertNotNull(s3Client);
    }
}
