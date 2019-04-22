package com.github.rich.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author Petty
 */
@SpringCloudApplication
public class RichMessageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RichMessageServiceApplication.class, args);
    }

}
