package com.ssafy.user.pet;

import java.io.File;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.ImageFile;
import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Pet;
import com.ssafy.user.pet.request.PetRequest;

@Service("UserPetService")
public class UserPetServiceImpl implements UserPetService {
	
	@Autowired
	UserPetRepository petRepository;
	
	ParameterCheck parameterCheck = new ParameterCheck();
	
	ImageFile imageCheck = new ImageFile();
	
	@Override
	public boolean isValidPetInfo(PetRequest petRequest, boolean checkEmpty) {
		
		// 파일 크기 및 확장자 유효성 검사
		MultipartFile petImage = petRequest.getPetImage();
		if (petImage != null)
			if (!parameterCheck.isValidImage(petImage, false)) return false;
		
		// 반려동물 이름
		if (checkEmpty && petRequest.getPetName() == null) return false;
		
		// 반려동물 대분류
		String petType = petRequest.getPetType();
		if (checkEmpty && petType == null) return false;
		if (petType != null)
			if (!(petType.equals("강아지") || petType.equals("고양이") || petType.equals("토끼") || petType.equals("페럿") || petType.equals("기니피그") || petType.equals("햄스터"))) {
				return false;
			}
		
		// 생일 확인
		String birth = petRequest.getPetBirth();
		if (birth != null)
			if (!Pattern.matches("^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$", birth)) {
				return false;
			}
		
		return true;
	}
	
	@Override
	public boolean addPet(String id, PetRequest petRequest) {
		
		// 파일 생성
		MultipartFile imageFile = petRequest.getPetImage();
		
		// 이미지 크기 100KB 이하로 조절해서 저정하기
		String imageName = imageCheck.saveMultipartFile100KB(imageFile, "C:\\Users\\SSAFY");
		
		// Pet Entity 생성
		Pet pet = new Pet();
		pet.setUserId(id);
		pet.setPetImage(imageName);
		pet.setPetName(petRequest.getPetName());
		pet.setPetType(petRequest.getPetType());
		pet.setPetVariety(petRequest.getPetVariety());
		pet.setPetBirth(petRequest.getPetBirth());
		pet.setPetInfo(petRequest.getPetInfo());
		
		Pet result = petRepository.save(pet);
		if (result != null) return true;
		return false;
	}
	
	@Override
	public boolean modifyPet(int no, PetRequest petRequest) {
		
		// Pet Entity 생성
		Pet pet = petRepository.findByNo(no);
		
		System.out.println("### 여기 오긴 왔다.");
		System.out.println(petRequest.getPetImage());
		
		if (petRequest.getPetImage() != null) {
			
			System.out.println("### 파일 수정까지 옴");
			
			// 기존 파일 가져오기
			String beforeFileName = pet.getPetImage();
			
			// 기존 파일 삭제하기
			File file = new File("C:\\Users\\SSAFY\\"+beforeFileName);
			file.delete();
			
			// 파일
			MultipartFile imageFile = petRequest.getPetImage();
			
			// 이미지 크기 100KB 이하로 조절해서 저정하기
			String imageName = imageCheck.saveMultipartFile100KB(imageFile, "C:\\Users\\SSAFY");
			
			pet.setPetImage(imageName);
		}
		
		if (petRequest.getPetName() != null && !petRequest.getPetName().equals(""))
			pet.setPetName(petRequest.getPetName());
		if (petRequest.getPetType() != null && !petRequest.getPetType().equals(""))
			pet.setPetType(petRequest.getPetType());
		if (petRequest.getPetVariety() != null && !petRequest.getPetVariety().equals(""))
			pet.setPetVariety(petRequest.getPetVariety());
		if (petRequest.getPetBirth() != null && !petRequest.getPetBirth().equals(""))
			pet.setPetBirth(petRequest.getPetBirth());
		if (petRequest.getPetInfo() != null && !petRequest.getPetInfo().equals(""))
			pet.setPetInfo(petRequest.getPetInfo());
		
		Pet result = petRepository.save(pet);
		if (result != null) return true;
		return false;
	}
	
}
