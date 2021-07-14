package com.albert.SocialMedia.helloworld;

import com.albert.SocialMedia.helloworld.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    MessageSource messageSource;

    @GetMapping(path = "hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping(path = "hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World Bean");
    }

    @GetMapping(path = "hello-world/path-variable/{name}")
    public HelloWorldBean helloWorld(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello , %s", name));
    }

    @GetMapping(path = "hello-world-internationalization")
    public String helloWorldInternationalizard() {
        return messageSource.getMessage("good.morning.message", null, "Default message", LocaleContextHolder.getLocale());
    }
}
