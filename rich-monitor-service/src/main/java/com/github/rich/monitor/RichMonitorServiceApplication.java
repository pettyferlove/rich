package com.github.rich.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author Petty
 */
@Slf4j
@EnableAdminServer
@EnableHystrix
@EnableDiscoveryClient
@EnableTurbine
@SpringCloudApplication
public class RichMonitorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RichMonitorServiceApplication.class, args);
    }

}
