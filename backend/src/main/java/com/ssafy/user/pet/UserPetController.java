package com.ssafy.user.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.ParameterCheck;
import com.ssafy.user.pet.request.PetModifyRequest;
import com.ssafy.user.pet.request.PetRequest;
import com.ssafy.user.pet.response.PetBasicResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"User Pet"}, description = "반려동물  API")
@RestController
@RequestMapping("/api/user/pet")
public class UserPetController {
	
	@Autowired
	UserPetService userPetService;
	
	@Autowired
	UserPetRepository petRepository;
	
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@PostMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "반려동물 등록", notes = "반려동물 정보를 입력 받아 사용자의 반려동물로 추가한다.")
	@ApiImplicitParam(name = "id", value = "사용자 아이디", required = true)
	public ResponseEntity<PetBasicResponse> registerPet(@PathVariable String id, PetRequest petRequest) {
		
		// token 관련해서 어떻게 할 건지 결정 X, 그래서 id 유효성 검사 생략된 상태
		
		// 입력 받은 정보 유효성 검사 (생일, 품종, 파일)
		if (userPetService.isValidPetInfo(petRequest, true)) {
			
			// DB에 넣기
			boolean result = userPetService.addPet(id, petRequest);
			if (result) {
				return ResponseEntity.status(200).body(new PetBasicResponse("success", "반려동물 등록에 성공했습니다."));
			}
		}
		
		return ResponseEntity.status(200).body(new PetBasicResponse("failure", "반려동물 등록에 실패했습니다."));
	}
	
	@DeleteMapping("/{no}")
	@ApiOperation(value = "반려동물 삭제", notes = "반려동물 번호를 입력 받아 반려동물을 삭제한다.")
	@ApiImplicitParam(name = "no", value = "반려동물 번호", dataType = "int", example = "0", required = true)
	public ResponseEntity<PetBasicResponse> removePet(@PathVariable int no) {
		
		// DB에서 no 삭제
		int deleteNo = petRepository.deleteByNo(no);
		if (deleteNo > 0) {
			return ResponseEntity.status(200).body(new PetBasicResponse("success", "반려동물을 삭제했습니다."));
		} else {
			return ResponseEntity.status(200).body(new PetBasicResponse("failure", "존재하지 않는 반려동물입니다."));
		}
	}
	
	@PutMapping(value = "/{no}")
	@ApiOperation(value = "반려동물 정보 수정", notes = "반려동물 정보 일부를 입력 받아 정보를 수정한다.")
	@ApiImplicitParam(name = "no", value = "반려동물 번호", dataType = "int", example = "0", required = true)
	public ResponseEntity<PetBasicResponse> modifyPet(@PathVariable(value = "no") int no, PetModifyRequest petModifyRequest, @RequestPart(value = "petImage", required = false) MultipartFile petImage) {
		
		System.out.println(petModifyRequest);
		
		PetRequest petRequest = new PetRequest();
		petRequest.setPetImage(petImage);
		petRequest.setPetBirth(petModifyRequest.getPetBirth());
		petRequest.setPetInfo(petModifyRequest.getPetInfo());
		petRequest.setPetName(petModifyRequest.getPetName());
		petRequest.setPetType(petModifyRequest.getPetType());
		petRequest.setPetVariety(petModifyRequest.getPetVariety());
		
		// 입력 받은 정보 유효성 검사 (생일, 품종, 파일)
		if (userPetService.isValidPetInfo(petRequest, false)) {
			
			// DB에 넣기
			boolean result = userPetService.modifyPet(no, petRequest);
			if (result) {
				return ResponseEntity.status(200).body(new PetBasicResponse("success", "반려동물 정보를 수정했습니다."));
			}
		}
		
		return ResponseEntity.status(200).body(new PetBasicResponse("failure", "반려동물 정보 수정에 실패했습니다."));
	}
	
}