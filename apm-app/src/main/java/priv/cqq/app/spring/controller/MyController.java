package priv.cqq.app.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class MyController {

    // http://127.0.0.1:8080/controller?name=lisi
    @GetMapping("/controller")
    @ResponseBody
    public String hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        log.info("Controller: {}", name);
        return "Hello " + name;
    }
}
