package com.ssafy.user.auth.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.User;
import com.ssafy.user.auth.dto.OAuthAttributes;
import com.ssafy.user.auth.dto.SessionUser;
import com.ssafy.user.join.UserJoinService;
import com.ssafy.user.join.request.JoinRequest;
import com.ssafy.user.login.repository.UserLoginRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	
	private final UserLoginRepository userLoginRepository; 
	private final HttpSession httpSession;
	
	@Autowired
	UserJoinService userJoinService;
	
	
	/** 
	 * 
	 * @param OAuth2UserRequest
	 * @return OAuth2User
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		// i) DefaultOAuth2UserService 서비스를 통해 User 정보를 가져와야 하기 때문에 대리자 생성
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService(); 
		OAuth2User oAuth2User = delegate.loadUser(userRequest); 
		
		// ii) OAuth2 서비스 Id (구글, 카카오, 네이버) 구분
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		
		// iii) OAuth2 로그인 진행 시 키가 되는 필드 값(like PK, 네이버 or 카카오는 지원하지 않음) 
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		// iv) OAuth2UserService를 통해 가져온 데이터를 담을 클래스
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		// v) 로그인 한 유저 정보
		User user = saveOrUpdate(attributes); 
		// vi) httpSession에 유저 속성을 설정
		httpSession.setAttribute("user", new SessionUser(user));			// SessionUser: 직렬화된 User 엔티티 클래스 사용
		
		// vii) 로그인한 유저를 return 한다.
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
	}
	
	/** 
	 * 저장되어 있지 않다면 DB 저장 or 저장되어 있다면 update 진행
	 * @param OAuthAttributes
	 * @return User
	 */
	private User saveOrUpdate(OAuthAttributes attributes) {
		User user = userLoginRepository.findById(attributes.getEmail())
				.map(entity -> entity.update(attributes.getName()))
				.orElse(attributes.toEntity());
		
		return userLoginRepository.save(user);
		//Optional<User> user = userLoginRepository.findById(attributes.getEmail());
		// 만약, 회원가입 되어 있지 않은 회원이라면 회원가입 처리
//		if (user == null) {
//			// !! 일단 반려인으로 다 함
//			JoinRequest joinRequest = new JoinRequest(); 
//			joinRequest.setId(attributes.getEmail());
//			joinRequest.setUserPassword("SSAFY01#FFY");
//			joinRequest.setUserNickname("임시닉네임 OAUTH때무네");
//			joinRequest.setUserName("임시이름닉네임과동일");
//			joinRequest.setUserPhone("임시폰번호");
//			joinRequest.setUserRole(0);						// > 추후 역할 빼기(컬럼 없앰)
//			joinRequest.setUserAlertFlag(0);
//			boolean joinResult = userJoinService.joinUser(joinRequest);
//			
//			// 권한 설정
//			userJoinService.grantAuthority(attributes.getEmail(), "ROLE_USER");
//		}
//		
//		return userLoginRepository.save(user);
	}
	
}
