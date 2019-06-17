package com.github.rich.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author Petty
 */
@SpringCloudApplication
public class RichZipkinServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RichZipkinServiceApplication.class, args);
    }

}
