package com.ssafy.board;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Heart;

@Repository
public interface HeartRepo extends JpaRepository<Heart,Integer>{
	boolean existsByBoardNoAndUserId(int boardNo, String userId);
	
	@Transactional
	void deleteByBoardNoAndUserId(int boardNo, String userId);
}
