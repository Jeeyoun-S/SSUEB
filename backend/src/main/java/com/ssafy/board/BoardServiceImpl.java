package com.ssafy.board;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.response.BoardSummary;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Like;
import com.ssafy.db.entity.Reply;


@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	BoardRepo bRepo;
	
	@Autowired
	LikeRepo lRepo;
	
	@Autowired
	ReplyRepo rRepo;

	@Override
	public List<BoardSummary> readNotice() throws SQLException {
		return bRepo.findByBoardFlag(0);//0 == admin -> admin이 작성한 글 목록 모두 불러오기
	}

	@Override
	public Board createBoard(Board board) throws SQLException {
		return bRepo.save(board);
	}

	@Override
	public List<BoardSummary> readAllBoard() throws SQLException {
		return bRepo.findByBoardFlagGreaterThanEqual(1);//1과 2는 전문가와 유저가 작성한 글
	}

	@Override
	public List<BoardSummary> readBoardTitleLike(String titleLike) throws SQLException {
		return bRepo.findByBoardTitleLike(titleLike);
	}

	@Override
	public Board readBoard(int no) throws SQLException {
		return bRepo.findById(no).get();
	}

	@Override
	public Board fixBoard(Board board) throws SQLException {//이게 no을 유지하면서 저장하면 그대로 바꿔주는듯 -> 프론트와 알아서 잘 맞춰서
		return bRepo.save(board);
	}

	@Override
	public void deleteBoard(int no) throws SQLException {
		bRepo.deleteById(no);
	}

	@Override
	public Board increaseboardViews(int no) throws SQLException {
		Board board = bRepo.findById(no).get();
		board.setBoardViews(board.getBoardViews()+1);//1증가
		return bRepo.save(board);
	}

	@Override
	public Like createLike(Like like) throws SQLException {
		return lRepo.save(like);
	}

	@Override
	public void deleteLike(int no) throws SQLException {
		lRepo.deleteById(no);
	}

	@Override
	public List<Reply> readReply(int boardNo) throws SQLException {
		return rRepo.findByBoardNo(boardNo);
	}

	@Override
	public Reply createReply(Reply reply) throws SQLException {
		return rRepo.save(reply);
	}

	@Override
	public void deleteReply(int no) throws SQLException {
		rRepo.deleteById(no);
	}

	@Override
	public List<BoardSummary> readPopular() throws SQLException {
		return bRepo.findTop5ByOrderByBoardLikenumDesc();
	}
	
}
