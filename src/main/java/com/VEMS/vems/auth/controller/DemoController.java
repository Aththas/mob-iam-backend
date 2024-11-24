package com.VEMS.vems.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {
    public String demo(){
        return "Hi, Aththas";
    }
}
