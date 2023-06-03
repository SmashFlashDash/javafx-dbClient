package org.dbclient.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"common.data"})
public class ServerApp {
    public static void main(String[] args) {
        System.out.println(org.hibernate.Version.getVersionString());
        SpringApplication.run(ServerApp.class);
    }
}
