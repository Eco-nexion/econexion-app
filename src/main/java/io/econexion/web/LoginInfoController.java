package io.econexion.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginInfoController {

    @GetMapping(value = "/login", produces = MediaType.TEXT_PLAIN_VALUE)
    public String loginInfo() {
        return "Usa POST /api/auth/login con JSON {username,password}";
    }
}
