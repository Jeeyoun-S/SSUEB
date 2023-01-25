package com.ssafy.user.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.common.exception.handler.NotFoundUserException;
import com.ssafy.common.util.SecurityUtil;
import com.ssafy.user.auth.response.UserAuthResponse;
import com.ssafy.user.login.repository.UserLoginRepository;

@Service
public class UserAuthService {
	
	@Autowired
	UserLoginRepository userLoginRepository;

	/** 
	 * userId를 기준으로 정보 가져오기
	 * @param userId
	 * @return UserAuthResponse
	 */
	@Transactional(readOnly = true)
    public UserAuthResponse getUserWithAuthorities(String userId) {
        return UserAuthResponse.from(userLoginRepository.findOneWithAuthoritiesById(userId).orElse(null));
    }

	/** 
	 * Security Context에 저장된 userId의 정보만 가져오기
	 * @param void
	 * @return UserAuthResponse
	 */
    @Transactional(readOnly = true)
    public UserAuthResponse getMyUserWithAuthorities() {
        return UserAuthResponse.from(
                SecurityUtil.getCurrentUserId()
                        .flatMap(userLoginRepository::findOneWithAuthoritiesById)
                        .orElseThrow(() -> new NotFoundUserException("User not found"))
        );
    }

}
