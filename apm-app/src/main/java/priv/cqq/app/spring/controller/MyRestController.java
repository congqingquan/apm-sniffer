package priv.cqq.app.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MyRestController {

    // http://127.0.0.1:8080/restController?name=lisi
    @GetMapping("/restController")
    public String hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        log.info("Rest controller: {}", name);
        return "Hello " + name;
    }
}
