package com.ssafy.board;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.response.BoardSummary;
import com.ssafy.db.entity.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board,Integer>{
	List<BoardSummary> findByBoardFlag(int boardFlag);//0을 넣어서 어드민이 작성한 글만 가져온다
	List<BoardSummary> findByBoardFlagGreaterThanEqual(int boardFlag);//1을 넣어서 전문가와 유저 모두가 작성한 글을 가져온다.
	List<BoardSummary> findByBoardTitleLike(String titleLike);//1을 넣어서 전문가와 유저 모두가 작성한 글을 가져온다.
	
	List<BoardSummary> findTop5ByOrderByBoardHeartnumDesc();//인기 많은 순으로 5개 가져오기 -> 추후에 1주일 이내의 글 한정 이런식으로 할지도
}
