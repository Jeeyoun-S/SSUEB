package com.ssafy.user.login.repository;

import com.ssafy.db.entity.User;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserLoginRepository extends JpaRepository<User, Long> {

	/** 
	 * userId를 기준으로 User 정보를 가져옴(권한 정보도 같이 가져옴)
	 * @param userId
	 * @return Optional<User>
	 */
	// ? @EntityGraph: 쿼리 수행 시 Lazy 조회가 아닌 Eager 조회로 authorities 정보를 같이 가져옴
	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesById(String userId);
}