package com.ssafy.board;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.board.request.BoardFixReq;
import com.ssafy.board.response.BoardSummary;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Heart;
import com.ssafy.db.entity.Reply;

public interface BoardService {
	//공지사항 목록 불러오기
	List<BoardSummary> readNotice() throws SQLException;
	
	//board 생성
	Board createBoard(Board board) throws SQLException;
	//board 전체 목록 불러오기 -> 타이틀, 작성자 , 조회수
	List<BoardSummary> readAllBoard() throws SQLException;//후에 pageable 추가할 수도 있음
	//게시글 제목 검색 기능 -> 게시글 제목에 해당 검색 키워드가 포함된 게시글 목록들만 조회
	List<BoardSummary> readBoardTitleLike(String titleLike) throws SQLException;//후에 pageable 추가할 수도 있음
	//no에 해당하는 board 상세 정보 가져오기
	Board readBoard(int no) throws SQLException;
	
	//보드 수정
	Board fixBoard(BoardFixReq bfr) throws SQLException;
	//보드 삭제
	void deleteBoard(int no) throws SQLException;
	//보드 조회수 증가
	Board increaseboardViews(int no) throws SQLException;
	
	
	//좋아요 클릭
	Heart createLike(Heart heart) throws SQLException;
	//좋아요 취소
	void deleteLike(int no) throws SQLException;
	//좋아요 여부
	boolean whetherLike(int boardNo, String userId) throws SQLException;
	
	//해당하는 게시글에 달린 댓글 전부를 가져온다
	List<Reply> readReply(int boardNo) throws SQLException;
	//작성한 댓글 저장
	Reply createReply(Reply reply) throws SQLException;
	//작성된 댓글 삭제
	void deleteReply(int no) throws SQLException;
	
	//인기있는 게시글 가져오기 -> 좋아요 수에 따라
	List<BoardSummary> readPopular() throws SQLException;
}