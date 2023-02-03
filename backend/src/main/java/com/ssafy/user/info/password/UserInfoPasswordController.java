package com.ssafy.user.info.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.util.ParameterCheck;
import com.ssafy.user.join.response.BasicResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"User/Info"}, description = "회원정보 API")
@RestController
@RequestMapping("/api/user/info")
public class UserInfoPasswordController {
	
	// 유효성 검사
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserInfoPasswordRepository userInfoPasswordRepository;
	
	@PostMapping("/password")
	@ApiOperation(value = "회원 비밀번호 확인", notes = "회원정보 수정 전 사용자의 비밀번호를 확인한다.")
	public ResponseEntity<BasicResponse> checkPassword(@RequestBody UserInfoPasswordRequest userInfoPasswordRequest) {
		
		String id = userInfoPasswordRequest.getId();
		String password = userInfoPasswordRequest.getPassword();
		
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && parameterCheck.isValidId(id)) {
		
			// DB에서 비밀번호 가져오기
			String originalPW = userInfoPasswordRepository.findById(id).getUserPassword();
			
			// 가져온 비밀번호가 null이 아닌 경우
			if (originalPW != null) {
				
				// 비밀번호 확인
				boolean checkResult = passwordEncoder.matches(password, originalPW);
				
				// 일치하면 성공 반환
				if (checkResult) return ResponseEntity.status(200).body(new BasicResponse("success"));
			}
		}
		
		return ResponseEntity.status(200).body(new BasicResponse("failure"));
	}
}
