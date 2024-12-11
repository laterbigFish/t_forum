package com.example.froum;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
 public class test {
  @RequestMapping("/hello")

    public String hello(){
      return "hello";
  }
}
