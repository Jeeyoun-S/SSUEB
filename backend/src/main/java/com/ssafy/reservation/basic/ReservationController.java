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
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.google.common.io.Files;
import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.basic.request.ReservationDignosis;
import com.ssafy.reservation.basic.request.ReservationReivew;
import com.ssafy.reservation.pet.PetService;
import com.ssafy.reservation.pet.response.PetSummary;
import com.ssafy.reservation.pet.response.ReservationPetFinish;

import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.ImageFormats;
import io.github.techgnious.dto.ResizeResolution;
import io.github.techgnious.dto.VideoFormats;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/reservation")
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	PetService petService;
	
	@Autowired
	AttachService attachService;
	
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
	public ResponseEntity<?> createReservation(@RequestPart(value = "reservation") Reservation reservation, 
			@RequestPart(value = "files", required = false)  List<MultipartFile> files) {
		try {
			Reservation res = reservationService.createReservation(reservation);
			
			if(files != null && files.size() != 0)
				attachService.uploadFiles(files, reservation.getNo());
			
			return new ResponseEntity<Reservation>(res, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{no}")
	@ApiOperation(value = "예약 삭제", notes = "해당 no에 해당하는 예약을 삭제한다.", response = Void.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "no", value = "삭제하고자 하는 상담예약의 번호", dataType = "int", example = "0", required = true),
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
		@ApiImplicitParam(name = "no", value = "상담을 종료하고자 하는 상담예약의 번호", dataType = "int", example = "0", required = true),
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
		@ApiImplicitParam(name = "no", value = "상세 정보를 가져오고 싶은 예약 테이블의 번호", dataType = "int", example = "0", required = true),
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
	@ApiOperation(value = "공개된 상담 기록 보기", notes = "공개된 상담 정보를 모두 가져온다.", response = ReservationPetFinish.class) 
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readOpenReservation() {
		try {
			List<ReservationPetFinish> result = reservationService.getOpenReservation();
			//System.out.println(result);
			return new ResponseEntity<List<ReservationPetFinish>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}	
	
	
	
	@GetMapping("/date-validation/{userId}")
	@ApiOperation(value = "해당 유저의 상담 예정 시간들", notes = "종료되지 않은 예약들의 예정 시간들을 모두 가져온다.", response = String.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "상담 예정 시간을 불러올 현재 사용자", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> readDateValidtaion(@PathVariable String userId) {
		try {
			List<String> result = reservationService.getDateValidation(userId);
			//System.out.println(result);
			return new ResponseEntity<List<String>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	@GetMapping("/pet-list/{userId}")
	@ApiOperation(value = "해당 유저의 펫 목록들", notes = "펫 목록(번호, 이름, 사진)", response = PetSummary.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "펫 목록을 불러올 현재 사용자", required = true),
	})
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	//신규 상담 등록 진입 시 현재 로그인 된 유저 기준으로 불러와서 보여주기 
	public ResponseEntity<?> readPetList(@PathVariable String userId) {
		try {
			List<PetSummary> result = petService.findByUserId(userId);
			
			return new ResponseEntity<List<PetSummary>>(result, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
//	@PostMapping("/save")
//    @ApiOperation(value="업로드",notes = "", response = Void.class)
//	@ApiResponses({
//        @ApiResponse(code = 200, message = "성공"),
//        @ApiResponse(code = 500, message = "서버 오류")
//    })
//    public ResponseEntity<?> save(@RequestPart(value = "reservation") Reservation reservation, @RequestPart(value = "files")  List<MultipartFile> files){	
//		try {
//			System.out.println(reservation);
//			attachService.uploadFiles(files, reservation.getNo());	
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//		
//		return new ResponseEntity<Void>(HttpStatus.OK);
//    }
}
