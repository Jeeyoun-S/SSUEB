package com.ssafy.user.login.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *  user 모델 관련 디비 쿼리 생성을 위한 구현 정의
 */
@Repository
public class UserLoginRepositorySupport {
	
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;

    // [/partner] 해당 id의 회원정보 조회
    public Optional<User> findUserByUserId(String userId) {
    	User user = jpaQueryFactory.select(qUser).from(qUser).where(qUser.id.eq(userId)).fetchOne();
    	
    	if (user == null) return Optional.empty(); 
    	return Optional.ofNullable(user);
    }
}
