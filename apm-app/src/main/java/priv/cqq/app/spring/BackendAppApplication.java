package priv.cqq.app.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendAppApplication {

    /**
         VM Option:
            -javaagent:D:\development\idea\workspace\personal\byte-buddy\byte-buddy-agent\target\byte-buddy-agent-1.0-SNAPSHOT-jar-with-dependencies.jar
     */

    public static void main(String[] args) {
        SpringApplication.run(BackendAppApplication.class, args);
    }

}
