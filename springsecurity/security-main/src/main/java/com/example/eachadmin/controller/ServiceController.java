package com.example.eachadmin.controller;


import com.example.eachadmin.entity.CheckCode;
import com.example.eachadmin.util.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;


@RestController
public class ServiceController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @GetMapping("/image")
    public void image(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 创建验证码文本
        String capText = defaultKaptcha.createText();
        // 创建验证码图片
        BufferedImage image = defaultKaptcha.createImage(capText);
        // 将验证码文本放进 Session 中
        CheckCode code = new CheckCode(capText);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
        // 将验证码图片返回，禁止验证码图片缓存
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(HttpServletResponse response) throws IOException {
        return "登录成功";
    }

    @PostMapping("/test01")
    public String test01() {
        return "test01";
    }

    @PostMapping("/test02")
    public String test02() {
        return "test02";
    }

    @GetMapping("/getPrincipal")
    public String home(Principal principal) {
        return "Hello, " + principal.getName() + "!";
    }
}
