package org.talang.wabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
public class WaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaBackendApplication.class, args);
    }

}
