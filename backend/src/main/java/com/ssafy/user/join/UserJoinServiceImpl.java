package com.ssafy.user.join;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.ImageFile;
import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserAuthority;
import com.ssafy.user.join.repository.JoinConsultantRepository;
import com.ssafy.user.join.repository.JoinUserAuthorityRepository;
import com.ssafy.user.join.repository.JoinUserRepository;
import com.ssafy.user.join.request.ConsultantJoinRequest;
import com.ssafy.user.join.request.JoinRequest;

@Service("UserJoinService")
public class UserJoinServiceImpl implements UserJoinService {
	
	// 확인용 출력
	static boolean isDebug = true;
	
	// 유효성 검사
	ParameterCheck parameterCheck = new ParameterCheck();
	
	@Autowired
	JoinUserRepository userRepository;
	
	@Autowired
	JoinUserAuthorityRepository userAuthorityRepository;
	
	@Autowired
	JoinConsultantRepository consultantRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	ImageFile imageCheck = new ImageFile();
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Override
	public boolean checkUserValid(JoinRequest joinRequest, int role) {
		
		// 입력 받은 값 변수에 넣어주기
		String id = joinRequest.getId();
		String password = joinRequest.getUserPassword();
		String nickname = null;
		if (role == 0) nickname = joinRequest.getUserNickname();
		String name = joinRequest.getUserName();
		String phone = joinRequest.getUserPhone();
		int alertFlag = joinRequest.getUserAlertFlag();
		
		if (isDebug) System.out.println("#아이디 문제 "+id);
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && !userRepository.findById(id).isPresent() && parameterCheck.isValidId(id)) {
			
			if (isDebug) System.out.println("#비밀번호 문제 "+password);
			// 비밀번호 유효성 검사
			if (!parameterCheck.isEmpty(password) && parameterCheck.isValidPassword(password)) {
				
				if (isDebug) System.out.println("#닉네임 문제 "+nickname);
				// 닉네임 유효성 검사
				if ((role == 0 && !parameterCheck.isEmpty(nickname) && parameterCheck.isValidNickname(nickname)) || role == 1) {
					
					if (isDebug) System.out.println("#이름 문제 "+name);
					// 이름 유효성 검사
					if (!parameterCheck.isEmpty(name) && parameterCheck.isValidName(name)) {
						
						if (isDebug) System.out.println("#휴대폰 번호 문제 "+phone);
						// 휴대폰 번호 유효성 검사
						if (!parameterCheck.isEmpty(phone) && parameterCheck.isValidPhone(phone)) {
							
							if (isDebug) System.out.println("#알람 방식 번호 문제 "+alertFlag);
							// 알림 방식 유효성 검사
							if (parameterCheck.isValidAlertFlag(alertFlag)) {
								
								return true;
							}
						}
					}
				}
			}
			
		} return false;
	}
	
	@Override
	public boolean joinUser(JoinRequest joinRequest, int role) {
		
		// 유효성 검사
		boolean valid = checkUserValid(joinRequest, role);
		
		if (valid) {
			
			// 비밀번호 암호화하기
			String password = passwordEncoder.encode(joinRequest.getUserPassword());
			
			// Request DTO에서 User DTO로
			User user = new User();
			user.setId(joinRequest.getId());
			user.setUserPassword(password);
			if (role == 0) user.setUserNickname(joinRequest.getUserNickname());
			user.setUserName(joinRequest.getUserName());
			user.setUserPhone(joinRequest.getUserPhone());
			user.setUserAlertFlag(joinRequest.getUserAlertFlag());
			
			// DB에 전달 받은 회원정보 저장해 두기
			User insertResult = userRepository.save(user);
			if (insertResult != null) return true;
		}
			
		return false;
	}
	
	@Override
	public boolean joinConsultant(String id, ConsultantJoinRequest consultantJoinRequest) {
		
		// ! 이미지 및 동영상 파일을 저장할 폴더 경로 및 최대 크기 정하기
		// 이미지 파일 가져오기
		MultipartFile licenseImage = consultantJoinRequest.getConsultantLicenseCopyImage();
		
		// 파일 이름 만들기
		String licenseName = imageCheck.makeFilename(licenseImage.getOriginalFilename());
		
		// 파일 저장하기
		imageCheck.saveImage(licenseImage, licenseName, "C:\\Users\\SSAFY");
		
		// Consultant DTO 생성
		Consultant consultant = new Consultant();
		
		consultant.setId(id);
		consultant.setConsultantPetType(consultantJoinRequest.getConsultPetType());
		consultant.setConsultantIntro(null);
		consultant.setConsultantRate((double) 0);
		consultant.setConsultantProfile(null);
		consultant.setConsultantLicenseNumber(consultantJoinRequest.getConsultantLicenseNumber());
		consultant.setConsultantLicenseCopyImage(licenseName);
		consultant.setConsultantReservationCount(0);
		consultant.setConsultantCertified(0);
		
		// consultant를 DB에 넣어주기
		Consultant insertResult = consultantRepository.save(consultant);
		if (insertResult != null) return true;
		return false;
	}

	@Override
	public boolean grantAuthority(String id, String authorityName) {
		
		// 사용자 권한 저장하기
		UserAuthority userAuthority = new UserAuthority();
		userAuthority.setId(id);
		userAuthority.setAuthorityName(authorityName);
		UserAuthority insertResult = userAuthorityRepository.save(userAuthority);
		if (insertResult != null) return true;
		return false;
	}
	
	@Override
	public boolean joinPartner(JoinRequest joinRequest) {
		
		// 유효성 검사
		boolean valid = checkUserValid(joinRequest, 0);
		
		if (valid) {
			
			// 회원가입 후 로그인을 위해 영속성 컨텍스트에 넣고, 마지막에 DB에 반영
			EntityManager entityManager = entityManagerFactory.createEntityManager();
			EntityTransaction transaction = entityManager.getTransaction();
			
			// 트랜잭션 시작
			transaction.begin();
			
			// 비밀번호 암호화하기
			String password = passwordEncoder.encode(joinRequest.getUserPassword());
			
			// Request DTO에서 User DTO로
			User user = new User();
			user.setId(joinRequest.getId());
			user.setUserPassword(password);
			user.setUserNickname(joinRequest.getUserNickname());
			user.setUserName(joinRequest.getUserName());
			user.setUserPhone(joinRequest.getUserPhone());
			user.setUserAlertFlag(joinRequest.getUserAlertFlag());
			entityManager.persist(user);
			
			// 사용자 권한 저장하기
			UserAuthority userAuthority = new UserAuthority();
			userAuthority.setId(joinRequest.getId());
			userAuthority.setAuthorityName("ROLE_USER");
			entityManager.persist(userAuthority);
			
			// 트랜잭션 종료 및 flush & commit
			transaction.commit();
			
			return true;
		}
			
		return false;
	}
}
