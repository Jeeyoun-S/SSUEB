package com.ssafy.board;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.response.BoardSummary;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Reply;

@Repository
public interface ReplyRepo extends JpaRepository<Reply,Integer>{
	List<Reply> findByBoardNo(int boardNo);//0을 넣어서 어드민이 작성한 글만 가져온다
}
