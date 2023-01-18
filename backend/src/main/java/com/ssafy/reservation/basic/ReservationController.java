package com.ssafy.reservation.basic;

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

import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.basic.request.ReservationDignosis;
import com.ssafy.reservation.basic.request.ReservationReivew;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@Api(tags = {"Reservation/Basic"}, description = "예약 생성/삭제/검색 관련 API")
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping()
	@ApiOperation(value = "예약 생성", notes = "입력한 정보에 따라 예약을 생성한다. 2023-01-17 16:50:31 \n"
			+ "no, finish, open, registertime은 공란으로 하면 알아서 default 값이 들어간다.", response = Reservation.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
		try {
			Reservation res = reservationService.createReservation(reservation);
			//System.out.println(res);
			return new ResponseEntity<Reservation>(res, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{no}")
	@ApiOperation(value = "예약 삭제", notes = "해당 no에 해당하는 예약을 삭제한다.", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> deleteReservation(@PathVariable int no) {
		try {
			reservationService.deleteReservation(no);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/finish/{no}")
	@ApiOperation(value = "상담 완료", notes = "상담을 완료하고 해당 no에 해당하는 예약 테이블의 finish flag를 1로 바꾼다", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> finishReservation(@PathVariable int no) {
		try {
			reservationService.finishReservation(no);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/{no}")
	@ApiOperation(value = "예약 상세 정보 읽기", notes = "해당 no에 해당하는 예약에 관한 정보를 모두 불러온다.", response = Reservation.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readReservation(@PathVariable int no) {
		try {
			Reservation result = reservationService.readReservation(no);
			//System.out.println(result);
			return new ResponseEntity<Reservation>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}	
	
	@PutMapping("/dignosis/{no}")
	@ApiOperation(value = "진단서 작성", notes = "상담을 완료하고 해당 no에 해당하는 예약 테이블에 진단 기록을 저장한다.", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> writeDignosis(@RequestBody ReservationDignosis reservationDignosis, @PathVariable int no) {
		try {
			reservationService.writeDignosis(no, reservationDignosis.getReservationDignosisRecord());
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/review/{no}")
	@ApiOperation(value = "리뷰 작성", notes = "상담을 완료하고 해당 no에 해당하는 예약 테이블에 리뷰 점수와 내용을 기록한다", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> writeReview(@PathVariable int no, @RequestBody ReservationReivew review) {
		try {
			reservationService.writeReview(no,review.getReviewComment(),review.getReviewGrade(),review.getReservationOpen());
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
}
