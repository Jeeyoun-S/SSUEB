package com.ssafy.board;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.request.BoardFixReq;
import com.ssafy.board.request.HeartReq;
import com.ssafy.board.request.HeartWhetherReq;
import com.ssafy.board.response.BoardSummary;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.Heart;
import com.ssafy.db.entity.Matching;
import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.basic.request.ReservationDignosis;
import com.ssafy.reservation.basic.request.ReservationReivew;
import com.ssafy.reservation.matching.request.MatchingConfirm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Api(tags = {"Board"}, description = "게시판 관련 API")
@RestController
@RequestMapping("/api/board/")
public class BoardController {
	
	@Autowired
	BoardService bService;
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/notice")
	@ApiOperation(value = "공지사항 목록 불러오기", notes = "공지사항 목록을 전부 불러온다.", response = BoardSummary.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readNotice() {
		try {
			List<BoardSummary> result = bService.readNotice();
			
			return new ResponseEntity<List<BoardSummary>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}	
	
	@GetMapping("/community")
	@ApiOperation(value = "자유게시판 글 목록 불러오기", notes = "자유게시판 글 목록을 전부 불러온다.", response = BoardSummary.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readBoard() {
		try {
			List<BoardSummary> result = bService.readAllBoard();	
			return new ResponseEntity<List<BoardSummary>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}	
	
	@GetMapping("/community/{no}")
	@ApiOperation(value = "자유게시판 글 상세정보", notes = "해당 no에 대한 자유게시판 글의 상세정보를 불러온다.", response = Board.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readBoardDetail(@PathVariable int no) {
		try {
			Board result = bService.readBoard(no);
			return new ResponseEntity<Board>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/community")
	@ApiOperation(value = "자유게시판 글 작성하기", notes = "자유게시판에 글을 작성한다.", response = Board.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> createBoard(@RequestBody Board board) {
		try {
			Board result = bService.createBoard(board);
			return new ResponseEntity<Board>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/community")
	@ApiOperation(value = "자유게시판 글 수정하기", notes = "자유게시판의 글을 수정한다", response = Board.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> fixBoard(@RequestBody BoardFixReq bfr) {
		try {
			Board result = bService.fixBoard(bfr);
			return new ResponseEntity<Board>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/community/{no}")
	@ApiOperation(value = "자유게시판 글 삭제하기", notes = "자유게시판의 no에 해당하는 글을 삭제한다", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> deleteBoard(@PathVariable int no) {
		try {
			bService.deleteBoard(no);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	@GetMapping("/community/title/{titleLike}")
	@ApiOperation(value = "게시판 제목 기반 검색 기능", notes = "자유게시판 글 중에 해당 단어를 포함하는 제목의 글을 가져온다.", response = BoardSummary.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readBoardByTitle(@PathVariable String titleLike) {//검색이 안되네? 키워드 알아봐야할듯
		try {
			List<BoardSummary> result = bService.readBoardTitleLike(titleLike);
			return new ResponseEntity<List<BoardSummary>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/view-cnt/{no}")
	@ApiOperation(value = "글 조회수 증가", notes = "해당 no에 해당하는 글의 조회수를 1 증가시킨다.", response = Board.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> increaseBoardViews(@PathVariable int no) {
		try {
			Board result = bService.increaseboardViews(no);
			return new ResponseEntity<Board>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/heart")
	@ApiOperation(value = "좋아요를 추가하거나 삭제한다.", notes = "flag가 0이면 좋아요를 추가, 1이면 좋아요를 삭제한다.", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> cdLike(@RequestBody HeartReq heartReq) {//보드에 heartnum도 추가/삭제 해야함 -> 각자 service의 함수 안에 있음.
		try {
			if(heartReq.getFlag() == 0) {
				Heart result = bService.createLike(new Heart(0,heartReq.getBoardNo(),heartReq.getUserId()));
				return new ResponseEntity<Heart>(result, HttpStatus.OK);
			}
			else {
				bService.deleteLike(heartReq.getNo());
				return new ResponseEntity<Void>(HttpStatus.OK);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//이 글을 추천했었는지 안했는지 판별하는 함수
	@PostMapping("/heart/whether")
	@ApiOperation(value = "좋아요를 눌렀는지 여부", notes = "해당 유저가 해당 글에 좋아요를 눌렀는지 안눌렀는지 여부를 알려준다.", response = Boolean.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> whetherLike(@RequestBody HeartWhetherReq heartwhetherReq) {//보드에 heartnum도 추가/삭제 해야함 -> 각자 service의 함수 안에 있음.
		try {
			return new ResponseEntity<Boolean>(bService.whetherLike(heartwhetherReq.getBoardNo(), heartwhetherReq.getUserId()), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	@GetMapping("/popular")
	@ApiOperation(value = "인기 글목록 가져오기", notes = "좋아요가 많은 순으로 인기글 5개를 가져온다.", response = BoardSummary.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readPopular() {
		try {
			List<BoardSummary> result = bService.readPopular();	
			return new ResponseEntity<List<BoardSummary>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
}