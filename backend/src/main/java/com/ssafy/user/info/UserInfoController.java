package com.ssafy.user.info;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;


@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/user/info")
public class UserInfoController {
	
	@GetMapping("/partner/{id}")
	public static ResponseEntity<?> getPartner(@PathVariable String id) {
		return null;
	}
}
