package com.github.rich.txmanager;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author Petty
 */
@EnableTransactionManagerServer
@SpringCloudApplication
public class RichTxManagerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RichTxManagerServiceApplication.class, args);
    }

}
