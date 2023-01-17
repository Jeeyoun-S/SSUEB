package com.ssafy.user.join;

import org.springframework.data.repository.Repository;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;

public interface UserJoinRepository extends Repository<User, String> {
	
	void save(User user);
	
	void save(Consultant consultant);
	
	User findById(String id);
}