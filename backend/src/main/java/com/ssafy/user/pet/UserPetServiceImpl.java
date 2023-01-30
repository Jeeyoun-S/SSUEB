package com.ssafy.user.pet;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Pet;
import com.ssafy.user.pet.request.PetRequest;

@Service("UserPetService")
public class UserPetServiceImpl implements UserPetService {
	
	@Autowired
	UserPetRepository petRepository;
	
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@Override
	public boolean isValidPetInfo(PetRequest petRequest, boolean checkEmpty) {
		
		// 파일 크기 및 확장자 유효성 검사
		MultipartFile petImage = petRequest.getPetImage();
		if (petImage != null)
			if (!(parameterCheck.isValidFileSize(5000000, petImage) && parameterCheck.isValidExtension(petImage))) return false;
		
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
			if (!Pattern.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$", birth)) {
				return false;
			}
		
		return true;
	}
	
	@Override
	public boolean addPet(String id, PetRequest petRequest) {
		
		// 이미지 저장하기
		String petImageName = saveImage(petRequest.getPetImage());
		
		// Pet Entity 생성
		Pet pet = new Pet();
		pet.setUserId(id);
		pet.setPetImage(petImageName);
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
		
		if (petRequest.getPetImage() != null) {
			
			// 이미지 저장하기
			String petImageName = saveImage(petRequest.getPetImage());
			pet.setPetImage(petImageName);
		}
		
		if (petRequest.getPetName() != null) pet.setPetName(petRequest.getPetName());
		if (petRequest.getPetType() != null) pet.setPetType(petRequest.getPetType());
		if (petRequest.getPetVariety() != null) pet.setPetVariety(petRequest.getPetVariety());
		if (petRequest.getPetBirth() != null) pet.setPetBirth(petRequest.getPetBirth());
		if (petRequest.getPetInfo() != null) pet.setPetInfo(petRequest.getPetInfo());
		
		Pet result = petRepository.save(pet);
		if (result != null) return true;
		return false;
	}
	
	@Override
	public String saveImage(MultipartFile imageFile) {
		
		// 확장자 추출
		String fileName = imageFile.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf("."));
		
		// 파일명 생성
		String imageName = UUID.randomUUID().toString() + extension;
		
		// 저장 경로 설정하기 : 현재는 빠르게 확인하기 위해 사용자 주소로 설정해 두었음.
		String path = "C:\\Users\\SSAFY";
		
		// File 객체 생성
		File target = new File(path, imageName);
		
		// 폴더로 옮겨주기
		try {
			imageFile.transferTo(target);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return imageName;
	}
}
