package com.ssafy.user.info.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.user.join.response.BasicResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"User/Info"}, description = "회원정보 API")
@RestController
@RequestMapping("/api/user/info")
public class UserInfoPasswordController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserInfoPasswordRepository userInfoPasswordRepository;
	
	@PostMapping("/password")
	@ApiOperation(value = "회원 비밀번호 확인", notes = "회원정보 수정 전 사용자의 비밀번호를 확인한다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", value = "사용자 아이디", required = true),
		@ApiImplicitParam(name = "password", value = "사용자 비밀번호", required = true)
	})
	public ResponseEntity<BasicResponse> modifyPartnerInfo(@RequestParam(value = "id") String id, @RequestParam(value = "password") String password) {
		
		// DB에서 비밀번호 가져오기
		String originalPW = userInfoPasswordRepository.findById(id).getUserPassword();
		
		// 가져온 비밀번호가 null이 아닌 경우
		if (originalPW != null) {
			
			// 비밀번호 확인
			boolean checkResult = passwordEncoder.matches(password, originalPW);
			
			// 일치하면 성공 반환
			if (checkResult) return ResponseEntity.status(200).body(new BasicResponse("success"));
		}
		
		return ResponseEntity.status(200).body(new BasicResponse("failure"));
	}
}
