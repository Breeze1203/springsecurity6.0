package org.pt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName TestController
 * @Author pt
 * @Description
 * @Date 2025/2/28 11:38
 **/
@RestController
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "Server is running";
    }
}
