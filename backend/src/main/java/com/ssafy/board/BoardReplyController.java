package com.ssafy.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.db.entity.Reply;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Api(tags = {"Board/Reply"}, description = "게시판 댓글 관련 API")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/board/reply")
public class BoardReplyController {
	
	@Autowired
	BoardService bService;
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{boardNo}")
	@ApiOperation(value = "해당 글의 댓글 불러오기", notes = "boardNo에 해당하는 게시글에 달린 댓글을 전부 가져온다", response = Reply.class) 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "boardNo", value = "댓글들을 가져오고자 하는 게시글의 번호", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readReply(@PathVariable int boardNo) {
		try {
			List<Reply> result = bService.readReply(boardNo);
			return new ResponseEntity<List<Reply>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("")
	@ApiOperation(value = "댓글 작성하기", notes = "해당 게시판에 댓글을 작성한다.", response = Reply.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> createReply(@RequestBody Reply reply) {
		try {
			System.out.println(reply);
			Reply result = bService.createReply(reply);
			System.out.println(result);
			return new ResponseEntity<Reply>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/community/{no}")
	@ApiOperation(value = "댓글 삭제하기", notes = "no에 해당하는 댓글을 삭제한다", response = Void.class) 
	@ApiImplicitParams({
		@ApiImplicitParam(name = "no", value = "삭제하고 싶은 댓글의 번호", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> deleteReply(@PathVariable int no) {
		try {
			bService.deleteReply(no);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
}
