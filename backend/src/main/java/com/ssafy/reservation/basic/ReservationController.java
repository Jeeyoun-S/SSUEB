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

import com.google.common.io.Files;
import com.ssafy.db.entity.Reservation;
import com.ssafy.reservation.basic.request.ReservationDignosis;
import com.ssafy.reservation.basic.request.ReservationReivew;

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
	
	
	
	@GetMapping("/date-validation/{userId}")
	@ApiOperation(value = "해당 유저의 상담 예정 시간들", notes = "종료되지 않은 예약들의 예정 시간들을 모두 가져온다.", response = String.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "userId", value = "해당 유저의 상담 예약이 있는 시간대", required = true),
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
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/save")
    @ApiOperation(value="업로드",notes = "", response = Void.class)
	@ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> save(@RequestPart(value = "files")  MultipartFile file){
		IVCompressor compressor=new IVCompressor();
		String uuid = UUID.randomUUID().toString();//랜덤한 코드명 ex)49eec5bf-dce3-43b2-8ff8-c041c792ed0a를 넣어준다
		String savefileName = File.separator + uuid + "_" + file.getOriginalFilename();
		//for(MultipartFile file : Files) {
//			System.out.println(file.getContentType());
//			System.out.println(file.getName());
//			System.out.println(file.getOriginalFilename());
//			System.out.println(file.getSize());
		
			try {
				if(file.getContentType().startsWith("image")) {
					IVSize customRes=new IVSize();
					//커스텀 크기 설정
					customRes.setWidth(800);
					customRes.setHeight(300);
									
					//설정한 값으로 byte저장 -> return값이 byte[]라서 Files.write(byte[], 경로)로 저장
					byte[] resizeFile = compressor.resizeImageWithCustomRes(file.getBytes(), ImageFormats.JPEG, customRes);
					Files.write(resizeFile, new File("C:/Temp"+savefileName));
					
					//저장한 byte[]값을 저장 -> R720P 480P등설정 가능 -> 파일이나 byte가지고 바로 저장 가능
					//compressor.resizeAndSaveImageToAPath(file.getBytes(), savefileName, ImageFormats.JPEG, "C:/Temp", ResizeResolution.SMALL_THUMBNAIL);
				}
				else if(file.getContentType().startsWith("video")) {
					compressor.reduceVideoSizeAndSaveToAPath(file.getBytes(), savefileName, VideoFormats.MP4, ResizeResolution.R480P, "C:/Temp");
				}
				else {//그 외 파일 거름망 -> 어차피 front에서 할 거긴 한데
					
				}
				
				return new ResponseEntity<Void>(HttpStatus.OK);
			} catch (Exception e) {
				return exceptionHandling(e);
			}
    }
}
