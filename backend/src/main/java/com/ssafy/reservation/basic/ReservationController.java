package com.ssafy.reservation.basic;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.basic.request.ReservationDignosis;
import com.ssafy.reservation.basic.request.ReservationReivew;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
	@ApiOperation(value = "예약 생성", notes = "입력한 정보에 따라 예약을 생성한다."
			+ "no, finish, open, registertime은 공란으로 하면 알아서 default 값이 들어간다.", response = Reservation.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
		try {
			//System.out.println(reservation.getNo());
			Reservation res = reservationService.createReservation(reservation);
			//System.out.println(res);
			return new ResponseEntity<Reservation>(res, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{no}")
	@ApiOperation(value = "예약 삭제", notes = "해당 no에 해당하는 예약을 삭제한다.", response = Void.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "no", value = "삭제하고자 하는 상담예약의 번호", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
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
	@ApiImplicitParams({
		@ApiImplicitParam(name = "no", value = "상담을 종료하고자 하는 상담예약의 번호", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
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
	@ApiImplicitParams({
		@ApiImplicitParam(name = "no", value = "상세 정보를 가져오고 싶은 예약 테이블의 번호", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
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
	
	@PutMapping("/dignosis")
	@ApiOperation(value = "진단서 작성", notes = "상담을 완료하고 해당 no에 해당하는 예약 테이블에 진단 기록을 저장한다.", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> writeDignosis(@RequestBody ReservationDignosis reservationDignosis) {
		try {
			reservationService.writeDignosis(reservationDignosis.getNo(), reservationDignosis.getReservationDignosisRecord());
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/review")
	@ApiOperation(value = "리뷰 작성", notes = "상담을 완료하고 해당 no에 해당하는 예약 테이블에 리뷰 점수와 내용을 기록한다", response = Void.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> writeReview(@RequestBody ReservationReivew review) {
		try {
			reservationService.writeReview(review.getNo(),review.getReviewComment(),review.getReviewGrade(),review.getReservationOpen());
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/open")
	@ApiOperation(value = "공개된 상담 기록 보기", notes = "공개된 상담 정보를 모두 가져온다.", response = Reservation.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readOpenReservation() {
		try {
			List<Reservation> result = reservationService.getOpenReservation();
			//System.out.println(result);
			return new ResponseEntity<List<Reservation>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}	
	
	
	
	
	@PostMapping("/save")
    @ApiOperation(value="업로드",notes = "", response = Void.class)
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> save(@RequestPart(value = "files")  MultipartFile multipartFile){
		//for(MultipartFile multipartFile : multipartFiles) {
			System.out.println(multipartFile.getContentType());
			System.out.println(multipartFile.getName());
			System.out.println(multipartFile.getOriginalFilename());
			System.out.println(multipartFile.getSize());
			try {
				String uuid = UUID.randomUUID().toString();
				String savefileName = "C:/Temp" + File.separator + uuid + "_" + multipartFile.getOriginalFilename();

				multipartFile.transferTo(new File(savefileName));
				return new ResponseEntity<Void>(HttpStatus.OK);
			} catch (Exception e) {
				return exceptionHandling(e);
			}
    }
}
