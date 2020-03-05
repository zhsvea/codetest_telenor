package zh.codetest.telenor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class MainApplicationClass {

    public static final String SERVER_PORT = "5000";

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MainApplicationClass.class);
        springApplication.setDefaultProperties(Collections.singletonMap("server.port", SERVER_PORT));
        springApplication.run(args);
    }

}