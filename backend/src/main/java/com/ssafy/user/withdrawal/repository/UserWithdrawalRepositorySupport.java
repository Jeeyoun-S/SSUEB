package com.ssafy.user.withdrawal.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.QConsultant;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// user 모델 관련 디비 쿼리 생성을 위한 구현 정의 (회원탈퇴 기능 관련)
@Repository
public class UserWithdrawalRepositorySupport {
	
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUser qUser = QUser.user;
    QConsultant qConsultant = QConsultant.consultant; 
    
    /** 
	 * 회원탈퇴 확인
	 * @param id 조회할 유저 id
	 */
	public Optional<User> getUserWithdrawal(String id) {
		User user = jpaQueryFactory.select(qUser).from(qUser).where(qUser.id.eq(id)).fetchOne();
		
		if (user == null) return Optional.empty(); 
		return Optional.ofNullable(user);
	}
}
