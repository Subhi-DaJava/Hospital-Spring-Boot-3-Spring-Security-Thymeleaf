package com.uyghur.java.hospital.hospital.security_config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/403")
    public String notAuthorizedRequest() {
        return "/errors/403";
    }
}
