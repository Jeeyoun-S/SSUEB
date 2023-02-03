package com.ssafy.user.info.consultant;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;
import com.ssafy.user.info.consultant.repository.UserInfoConsultantRepository;
import com.ssafy.user.info.consultant.repository.UserInfoConsultantUserRepository;
import com.ssafy.user.info.consultant.response.UserInfoResponseData;
import com.ssafy.user.join.response.BasicResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/user/join")
public class UserInfoConsultantController {
	
	// 유효성 검사
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@Autowired
	UserInfoConsultantUserRepository userInfoConsultantUserRepository;
	
	@Autowired
	UserInfoConsultantRepository userInfoConsultantRepository;
	
	@GetMapping("/user/info/consultant/{id}")
	@ApiOperation(value = "전문가 회원정보 조회", notes = "전문가의 이력, 회원정보, 그래프 데이터를 조회한다.")
	@ApiImplicitParam(name = "id", value = "전문가 아이디", required = true)
	public ResponseEntity<BasicResponse> getConsultantInfo(@PathVariable String id) {
		
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && parameterCheck.isValidId(id)) {
			
			// DB에서 사용자 정보 불러오기
			Optional<User> optionUser = userInfoConsultantUserRepository.findById(id);
			if (optionUser.isPresent()) {
				
				Optional<Consultant> optionConsultant = userInfoConsultantRepository.findById(id);
				
				if (optionConsultant.isPresent()) {
					
					User user = optionUser.get();
					Consultant consultant = optionConsultant.get();
					
					UserInfoResponseData userInfoResponseData = new UserInfoResponseData();
					userInfoResponseData.setId(id);
					userInfoResponseData.setConsultantIntro(consultant.getConsultantIntro());
					userInfoResponseData.setConsultantProfile(consultant.getConsultantProfile());
					userInfoResponseData.setConsultantPetType(consultant.getConsultantPetType());
					userInfoResponseData.setConsultantReservationCount(consultant.getConsultantReservationCount());
					userInfoResponseData.setUserName(user.getUserName());
					userInfoResponseData.setUserPhone(user.getUserPhone());
					userInfoResponseData.setUserAlertFlag(user.getUserAlertFlag());
					
				}
			}
		}
		
		
		return null;
	}
	
	@PostMapping("/user/info/consultant")
	@ApiOperation(value = "전문가 회원정보 수정", notes = "전문가의 이력, 회원정보를 수정한다.")
	@ApiImplicitParam(name = "id", value = "전문가 아이디", required = true)
	public ResponseEntity<BasicResponse> modifyConsultantInfo() {
		
		
		
		return null;
	}
	
}
