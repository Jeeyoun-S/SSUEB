package com.ssafy.user.logout;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.Pet;
import com.ssafy.db.entity.Reservation;
import com.ssafy.user.login.UserLoginController;
import com.ssafy.user.logout.response.UserLogoutReservationAlertResponse;
import com.ssafy.user.logout.response.UserLogoutPostResponse;
import com.ssafy.user.logout.service.UserLogoutService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(tags = {"User/Logout"}, description = "(메인 페이지에서)금일 예약 건 수 조회  API")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/user/logout")
public class UserLogoutController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);
	
	@Autowired
	UserLogoutService userLogoutService;
	
	/** 
	 * 해당 id의 금일 예약 건 수 조회
	 * @param String 
	 * @return UserLogoutPostResponse
	 */
	@GetMapping("/alert/{id}")
	@ApiOperation(value = "금일 예약 건 수 조회")
	@ApiResponse(code = 200, response = UserLogoutPostResponse.class, message = "금일 예약 건 수 조회")
	public ResponseEntity<UserLogoutPostResponse> getTodayReservationCount(@PathVariable("id") String id) {
		
		try {
			logger.info("## [Controller]: getTodayReservationCount - 금일 예약 건 수 조회 실행  / 조회할 id: {}", id);
			
			// # 예약 건 수 조회
			int count = userLogoutService.getTodayReservationCount(id).size(); 
//			logger.info("#21# 실행 결과: {}", count);
			return ResponseEntity.ok(UserLogoutPostResponse.of(200, "금일 예약 상담 건 수 조회 성공", "success", count));
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(UserLogoutPostResponse.of(401, "금일 예약 상담 건 수 조회 실패", "failure", 100000));
		}
	}
	
	/** 
	 * 해당 id 가장 최근 예약 내용 조회
	 * @param String 
	 * @return UserLogoutPostResponse
	 */
	@GetMapping("/alert/reservation/{id}")
	@ApiOperation(value = "가장 최근 예약 내용 조회")
	@ApiResponse(code = 200, response = UserLogoutReservationAlertResponse.class, message = "가장 최근 예약 내용 조회")
	public ResponseEntity<UserLogoutReservationAlertResponse> getRecentlyReservation(@PathVariable("id") String id) {
		
		try {
			logger.info("## [Controller]: getRecentlyReservation - 가장 최근 예약 내용 조회 실행  / 조회할 id: {}", id);
			
			// # 최근 예약 상담 내역 조회
			Reservation reservation = userLogoutService.getRecentlyReservation(id);
			if (reservation == null) return ResponseEntity.ok(UserLogoutReservationAlertResponse.of(200, "예약된 상담 내역이 없습니다.", " success", null, null, null, null));
//			logger.info("#21# 최근 예약 상담 내역 조회 - 실행 결과: {}", reservation);
			String consultant = userLogoutService.getRecentlyReservationConsultant(reservation.getConsultantId());
//			logger.info("#21# 최근 예약 상담을 진행하는 전문가 이름 조회 - 실행 결과: {}", consultant);
			Pet pet = userLogoutService.getRecentlyReservationPet(reservation.getReservationPetNo());
//			logger.info("#21# 최근 예약 상담을 진행하는 반려동물 정보 조회 - 실행 결과: {}", pet);
			
			
//			return ResponseEntity.ok(UserLogoutReservationAlertResponse.of(200, "가장 최근 예약 상담 내용 조회 성공", "success", reservationPetName, reservationPetType, reservation.getReservationDate(), reservationConsultName));
			return ResponseEntity.ok(UserLogoutReservationAlertResponse.of(200, "예약된 상담 내역입니다.", "success", pet.getPetName(), pet.getPetType(), reservation.getReservationDate(), consultant));
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(UserLogoutReservationAlertResponse.of(401, "가장 최근 예약 상담 내용 조회 실패", " failure", null, null, null, null));
		}
	}

}
