package com.mulan.springsecurity_test.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 韩志雄
 * @date 2021/4/6 0:12
 */
@RestController
@RequestMapping("/")
public class MyController {

	@GetMapping("user/login")
	public String hello(){
		return "hello security";
	}


}
