package com.ssafy.user.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.ImageFile;
import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Pet;
import com.ssafy.user.pet.request.PetRequest;

@Service("UserPetService")
public class UserPetServiceImpl implements UserPetService {
	
	static boolean isDebug = true;
	
	@Autowired
	UserPetRepository petRepository;
	
	ParameterCheck parameterCheck = new ParameterCheck();
	
	ImageFile imageCheck = new ImageFile();
	
	@Value("${file.image.path.pet}")
	String petImagePath;
	
	@Override
	public boolean isValidPetInfo(PetRequest petRequest) {
		
		// 파일 크기 및 확장자 유효성 검사
		if (isDebug) System.out.println("#파일");
		MultipartFile petImage = petRequest.getPetImage();
		if (petImage != null) {
			if (!parameterCheck.isValidImage(petImage, false)) return false;
		}
		
		// 반려동물 이름 NN
		if (isDebug) System.out.println("#이름");
		String name = petRequest.getPetName();
		if (name == null || name.length() > 20) return false;
		
		// 반려동물 대분류 NN
		if (isDebug) System.out.println("#대분류");
		String petType = petRequest.getPetType();
		if (petType == null) return false;
		if (petType != null) {
			if (!parameterCheck.isValidPetType(petType)) {
				return false;
			}
		}
		
		// 반려동물 품종
		if (isDebug) System.out.println("#품종");
		String petVariety = petRequest.getPetVariety();
		if (petVariety != null) {
			if (parameterCheck.isSpecialChar(petVariety) || petVariety.length() > 20) {
				return false;
			}
		}
		
		// 반려동물 생일
		String birth = petRequest.getPetBirth();
		System.out.println("#생일 "+birth);
		if (birth != null) {
			if (!parameterCheck.isValidPetBirth(birth)) {
				return false;
			}
		}
		
		// 반려동물 특이사항
		String info = petRequest.getPetInfo();
		System.out.println("#특이사항 "+info);
		if (info != null) {
			if (info.length() > 80) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Pet addPet(String id, PetRequest petRequest) {
		
		boolean result = true;
		String imageName = null;
		
		// 파일 생성
		if (petRequest.getPetImage() != null) {
			
			MultipartFile imageFile = petRequest.getPetImage();
			
			// 파일 이름 생성
			imageName = imageCheck.makeFilename(imageFile.getOriginalFilename());
			
			// 이미지 크기 300px:300px로 조절해서 저장하기
			result = imageCheck.saveImage300(imageFile, imageName, petImagePath);
		}
		
		if (result) {
			// Pet Entity 생성
			Pet pet = new Pet();
			pet.setUserId(id);
			if (imageName != null) pet.setPetImage(imageName);
			pet.setPetName(petRequest.getPetName());
			pet.setPetType(petRequest.getPetType());
			pet.setPetVariety(petRequest.getPetVariety());
			if (petRequest.getPetBirth() != null) pet.setPetBirth(petRequest.getPetBirth()+"-01");
			pet.setPetInfo(petRequest.getPetInfo());
			
			Pet saveResult = petRepository.save(pet);
			return saveResult;
		}
		
		return null;
	}
	
	@Override
	public String modifyPet(int no, PetRequest petRequest, boolean isPetDeleteImage) {
		
		// Pet Entity 생성
		Pet pet = petRepository.findByNo(no);
		
		String imageName = "";
		
		// 기존 파일 가져오기
		String beforeFileName = pet.getPetImage();
		
		if (petRequest.getPetImage() != null) {
			
			// 파일
			MultipartFile imageFile = petRequest.getPetImage();
			
			// 파일 이름 생성
			imageName = imageCheck.makeFilename(imageFile.getOriginalFilename());
			
			// 이미지 크기 300px:300px로 조절해서 저장하기
			boolean result = imageCheck.saveImage300(imageFile, imageName, petImagePath);
			
			if (result && beforeFileName != null) {
				
				// 기존 파일 삭제하기
				imageCheck.deleteFile(beforeFileName, petImagePath);
			}
			pet.setPetImage(imageName);
		}
		
		else if (isPetDeleteImage && beforeFileName != null) {
			
			// 기존 파일 삭제하기
			imageCheck.deleteFile(beforeFileName, petImagePath);
			pet.setPetImage(null);
		}
		
		pet.setPetName(petRequest.getPetName());
		pet.setPetType(petRequest.getPetType());
		pet.setPetVariety(petRequest.getPetVariety());
		if (petRequest.getPetBirth() != null) pet.setPetBirth(petRequest.getPetBirth()+"-01");
		pet.setPetInfo(petRequest.getPetInfo());
		
		petRepository.save(pet);
		
		if (petRequest.getPetImage() != null) return imageName;
		else if (beforeFileName != null && !isPetDeleteImage) return beforeFileName;
		return null;
	}
	
}
