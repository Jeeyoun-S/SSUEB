package com.ssafy.user.info.partner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.User;
import com.ssafy.user.info.partner.repository.InfoPetRepository;
import com.ssafy.user.info.partner.repository.PartnerUserRepository;
import com.ssafy.user.info.partner.request.UserInfoPartnerBasicResponse;
import com.ssafy.user.info.partner.request.UserInfoPartnerBasicResponse.UserPartnerInfoData;
import com.ssafy.user.info.partner.request.UserInfoResponse;
import com.ssafy.user.info.partner.request.UserPetResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"User Info"}, description = "회원정보 API")
@RestController
@RequestMapping("/api/user/info")
public class UserInfoPartnerController {
	
	@Autowired
	PartnerUserRepository partnerUserRepository;
	
	@Autowired
	InfoPetRepository infoPetRepository;
	
	@GetMapping("/partner/{id}")
	@ApiOperation(value = "반려인 회원 정보 조회", notes = "반려인 아이디를 받아 마이페이지에 보여줄 정보를 조회한다.")
	@ApiImplicitParam(name = "id", value = "사용자 아이디", required = true)
	public ResponseEntity<UserInfoPartnerBasicResponse> getPartnerInfo(@PathVariable String id) {
		
		// token 관련해서 어떻게 할 건지 결정 X, 그래서 id 유효성 검사 생략된 상태
		
		UserInfoPartnerBasicResponse UserInfoPartnerBasicResponse = new UserInfoPartnerBasicResponse();
		UserPartnerInfoData userPartnerInfoData = new UserPartnerInfoData();
		
		// 회원 정보 가져오기
		Optional<User> optionalUser = partnerUserRepository.findById(id);
		
		if (optionalUser.isPresent()) {
			
			User user = optionalUser.get();
			
			UserInfoResponse userInfoResponse = new UserInfoResponse();
			userInfoResponse.setId(user.getId());
			userInfoResponse.setUserName(user.getUserName());
			userInfoResponse.setUserNickname(user.getUserNickname());
			userInfoResponse.setUserPhone(user.getUserPhone());
			
			// 응답 값에 넣어주기
			userPartnerInfoData.setUserInfo(userInfoResponse);
		}
		
		// 전체 반려동물 정보 가져오기
		List<Pet> petList = infoPetRepository.findByUserId(id);
		if (petList.size() > 0) {
			List<UserPetResponse> petInfo = new ArrayList<>();
			for (Pet pet : petList) {
				
				UserPetResponse userPetResponse = new UserPetResponse();
				
				userPetResponse.setNo(pet.getNo());
//				userPetResponse.setPetImage(pet.getPetImage());
				userPetResponse.setPetName(pet.getPetName());
				userPetResponse.setPetType(pet.getPetType());
				userPetResponse.setPetVariety(pet.getPetVariety());
				userPetResponse.setPetBirth(pet.getPetBirth());
				userPetResponse.setPetInfo(pet.getPetInfo());
				
				petInfo.add(userPetResponse);
			}
			
			// 응답값에 넣어주기
			userPartnerInfoData.setPetInfo(petInfo);;
		}
		
		return null;
	}
}