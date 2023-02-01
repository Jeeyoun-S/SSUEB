package com.ssafy.user.login.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.graph.GraphNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ssafy.db.entity.User;
import com.ssafy.user.login.repository.UserLoginRepository;

// 현재 액세스 토큰으로 부터 인증된 유저의 부가 상세정보(활성화 여부, 만료, 롤 등) 정의
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	UserLoginRepository userLoginRepository;
//	private final UserLoginRepository userLoginRepository;
//	public CustomUserDetailsService(UserLoginRepository userLoginRepository) {
//		this.userLoginRepository = userLoginRepository; 
//	}

	/** 
	 * 로그인 시 DB에서 유저정보와 권한정보를 가져와 userdetails.User 객체를 생성하여 반환
	 * @param userId
	 * @return UserDetails
	 */
	@Override
	@Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//		logger.info("#21# CustomUser 실행 중 : {}", userId);
		
		Optional<User> temp = userLoginRepository.findOneWithAuthoritiesById(userId);
//		logger.info("#21# find 실행 결과 확인: id - {}", temp.get().getId());
        
		logger.info("#21# 어디갔냥 권한: {}", userLoginRepository.findOneWithAuthoritiesById(userId));
		return userLoginRepository.findOneWithAuthoritiesById(userId)
                .map(user -> createUser(userId, user))
        		.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

	private org.springframework.security.core.userdetails.User createUser(String userId, User user) {
//		logger.info("#21# createUser 실행: {}, {}", userId, user.getUserActivated());
		// 해당 user의 활성화 여부 확인
		// i) 비활성화일 경우
		if (user.getUserActivated() == 1) {
			throw new RuntimeException(userId + " 사용자는 활성화되어 있지 않습니다.");
		}
		
		// ii) 활성화일 경우
		logger.info("#21# 권한 확인: {}, {}", user.getAuthorities(), user.toString());
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
				.collect(Collectors.toList());
		logger.info("#21# 권한 확인: {}", grantedAuthorities);
		
//		UserDetails details = new org.springframework.security.core.userdetails.User(user.getId(), user.getUserPassword(), grantedAuthorities);
//		logger.info("#21# userdetails 확인: {}", details);
		
		return new org.springframework.security.core.userdetails.User(user.getId(),
				user.getUserPassword(),
				grantedAuthorities);
	}
	
}
