package ru.nesterov.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Sergey Nesterov
 */
@Controller
public class MyCont {
    @GetMapping("/securities")
    public String getSecurities(){
        return "securities";
    }
    @GetMapping("/histories")
    public String getHistories(){
        return "histories";
    }
}
