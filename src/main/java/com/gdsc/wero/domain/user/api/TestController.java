package com.gdsc.wero.domain.user.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("/login")
    public String login() {

        return "login/social-login";
    }


    @GetMapping("test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>( "test", HttpStatus.ACCEPTED);
    }

}
