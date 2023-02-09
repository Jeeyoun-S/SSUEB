package com.ssafy.user.info.consultant;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.common.util.BasicResponse;
import com.ssafy.common.util.ImageFile;
import com.ssafy.common.util.ParameterCheck;
import com.ssafy.db.entity.Consultant;
import com.ssafy.db.entity.User;
import com.ssafy.user.info.consultant.repository.GraphMapping;
import com.ssafy.user.info.consultant.repository.StarMapping;
import com.ssafy.user.info.consultant.repository.UserInfoConsultantQueryRepository;
import com.ssafy.user.info.consultant.repository.UserInfoConsultantRepository;
import com.ssafy.user.info.consultant.repository.UserInfoConsultantUserRepository;
import com.ssafy.user.info.consultant.request.ConsultantInfoRequest;
import com.ssafy.user.info.consultant.response.Star;
import com.ssafy.user.info.consultant.response.UserInfoResponse;
import com.ssafy.user.info.consultant.response.UserInfoResponseData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(tags = {"User/Info"}, description = "회원정보 API")
@RestController
@RequestMapping("/api/user/info")
public class UserInfoConsultantController {
	
	// 유효성 검사
	ParameterCheck parameterCheck = new ParameterCheck();
	ImageFile imageFile = new ImageFile();
	
	@Autowired
	UserInfoConsultantUserRepository userInfoConsultantUserRepository;
	
	@Autowired
	UserInfoConsultantRepository userInfoConsultantRepository;
	
	@Autowired
	UserInfoConsultantQueryRepository userInfoConsultantQueryRepository;
	
	@Value("${file.image.path.profile}")
	String profileImagePath;
	
	@Autowired
	UserInfoConsultantService userInfoConsultantService;
	
	@GetMapping("/consultant/{id}")
	@ApiOperation(value = "전문가 회원정보 조회", notes = "전문가의 이력, 회원정보, 그래프 데이터를 조회한다.")
	@ApiImplicitParam(name = "id", value = "전문가 아이디", required = true)
	@ApiResponse(code = 200, response = UserInfoResponse.class, message = "전문가 회원정보 조회 진행")
	public ResponseEntity<UserInfoResponse> getConsultantInfo(@PathVariable String id) {
		
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && parameterCheck.isValidId(id)) {
			
			// DB에서 사용자 정보 불러오기
			Optional<User> optionUser = userInfoConsultantUserRepository.findById(id);
			if (optionUser.isPresent()) {
				
				Optional<Consultant> optionConsultant = userInfoConsultantRepository.findById(id);
				
				if (optionConsultant.isPresent()) {
					
					User user = optionUser.get();
					Consultant consultant = optionConsultant.get();
					
					UserInfoResponseData userInfoResponseData = new UserInfoResponseData();
					userInfoResponseData.setId(id);
					userInfoResponseData.setConsultantIntro(consultant.getConsultantIntro());
					userInfoResponseData.setConsultantProfile(consultant.getConsultantProfile());
					userInfoResponseData.setConsultantPetType(consultant.getConsultantPetType());
					userInfoResponseData.setConsultantReservationCount(consultant.getConsultantReservationCount());
					userInfoResponseData.setUserName(user.getUserName());
					userInfoResponseData.setUserPhone(user.getUserPhone());
					userInfoResponseData.setUserAlertFlag(user.getUserAlertFlag());
					
					// 별점 정보
					List<StarMapping> rate = userInfoConsultantQueryRepository.findReviewGradeById(id);
					List<Star> starList = new ArrayList<>();
					for (StarMapping sm : rate) {
						starList.add(new Star(sm.getStarNum(), sm.getStarCnt()));
					}
					userInfoResponseData.setRate(starList);
					
					// 별점 평균
					double starAverage = 0; int starCount = 0;
					for (StarMapping s : rate) {
						starAverage += (s.getStarNum() * s.getStarCnt());
						starCount += s.getStarCnt();
					}
					
					if (starCount != 0) userInfoResponseData.setConsultantRate(starAverage / starCount);
					else userInfoResponseData.setConsultantRate((double) 0);
					
					// 전문가 예약 확정 횟수
					Optional<Integer> totalConfirm = userInfoConsultantQueryRepository.findTotalConfirmById(id);
					if (totalConfirm.isPresent()) {
						userInfoResponseData.setConfirmCount(totalConfirm.get());
					} else {
						userInfoResponseData.setConfirmCount(0);
					}
					
					// 전문가 예약 제안 횟수 전체 평균
					Optional<Double>  averageCnt = userInfoConsultantQueryRepository.findAverageCnt();
					if (averageCnt.isPresent()) {
						userInfoResponseData.setReservationAverage(averageCnt.get());
					} else {
						userInfoResponseData.setReservationAverage(0);
					}
					
					// 전문가 예약 확정 횟수 전체 평균
					Optional<Double>  averageConfirm = userInfoConsultantQueryRepository.findAverageConfirm();
					if (averageConfirm.isPresent()) {
						userInfoResponseData.setConfirmAverage(averageConfirm.get());
					} else {
						userInfoResponseData.setConfirmAverage(0);
					}
					
					// 그래프 데이터
					// 1. 예약 확정 수
					// 1.1 현재 날짜 YYYY-MM으로 구하기
					LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
			        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM");
			        String nowMonth = today.format(dateFormat);
			        
			        // 1.2 6개월전 날짜 YYYY-MM으로 구하기
			        LocalDate todayBefore6Month = today.minusMonths(6);
			        String beforeMonth = todayBefore6Month.format(dateFormat);
					
			        List<GraphMapping> graphList = userInfoConsultantQueryRepository.findAverage(id, beforeMonth, nowMonth);
					
					List<Map<String, Integer>> reservationNumber = new ArrayList<>();
					List<Map<String, Double>> starNumber = new ArrayList<>();
					
					for (GraphMapping graph : graphList) {
						Map<String, Integer> rn = new HashMap<>();
						rn.put(graph.getReservationDate(), graph.getTotal());
						
						Map<String, Double> sn = new HashMap<>();
						sn.put(graph.getReservationDate(), graph.getGrade());
						
						reservationNumber.add(rn);
						starNumber.add(sn);
					}
					
					userInfoResponseData.setReservationNumber(reservationNumber);
					userInfoResponseData.setStarNumber(starNumber);
					
					return ResponseEntity.status(200).body(new UserInfoResponse("success", "전문가 회원정보를 조회했습니다.", userInfoResponseData));
				}
			}
		}
		
		
		return ResponseEntity.status(200).body(new UserInfoResponse("failure", "전문가 회원정보를 조회에 실패했습니다.", null));
	}
	
	@Transactional
	@PostMapping(value = "/consultant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "전문가 회원정보 수정", notes = "전문가의 이력, 회원정보를 수정한다.")
	@ApiResponse(code = 200, response = BasicResponse.class, message = "전문가 회원정보 수정 진행")
	public ResponseEntity<BasicResponse> modifyConsultantInfo(ConsultantInfoRequest consultantInfoRequest) {
		
		String id = consultantInfoRequest.getId();
		
		// 아이디 유효성 검사
		if (!parameterCheck.isEmpty(id) && parameterCheck.isValidId(id)) {
			
			if (userInfoConsultantService.isValidConsultantInfo(consultantInfoRequest)) {
				
				// DB에서 사용자 정보 불러오기
				Optional<User> optionUser = userInfoConsultantUserRepository.findById(id);
				if (optionUser.isPresent()) {
					
					Optional<Consultant> optionConsultant = userInfoConsultantRepository.findById(id);
					
					if (optionConsultant.isPresent()) {
						
						User user = optionUser.get();
						Consultant consultant = optionConsultant.get();
						
						user.setUserNickname(consultantInfoRequest.getUserName());
						if (consultantInfoRequest.getUserPassword() != null) user.setUserPassword(consultantInfoRequest.getUserPassword());
						user.setUserPhone(consultantInfoRequest.getUserPhone());
						
						consultant.setConsultantPetType(consultantInfoRequest.getConsultantPetType());
						consultant.setConsultantIntro(consultantInfoRequest.getConsultantIntro());
						
						// 기존 파일 가져오기
						String beforeProfile = consultant.getConsultantProfile();
						String filename = beforeProfile;
						
						if (consultantInfoRequest.getConsultantProfile() != null) {
							
							// 기존 파일 삭제하기
							imageFile.deleteFile(beforeProfile, profileImagePath);
							
							// 파일
							MultipartFile profileImage = consultantInfoRequest.getConsultantProfile();
							
							// 파일 이름 생성
							String imageName = imageFile.makeFilename(profileImage.getOriginalFilename());
							
							// 이미지 크기 300px:300px로 조절해서 저장하기
							boolean result = imageFile.saveImage300(profileImage, imageName, profileImagePath);
							
							if (result) consultant.setConsultantProfile(imageName);
							filename = imageName;
						}
						
						else if (consultantInfoRequest.isDeleteProfile()) {
							
							// 기존 파일 삭제하기
							imageFile.deleteFile(beforeProfile, profileImagePath);
							
							consultant.setConsultantProfile(null);
							filename = null;
						}
						
						return ResponseEntity.status(200).body(new BasicResponse("success", filename));
					}
				}
			}
		}
		
		return ResponseEntity.status(200).body(new BasicResponse("failure", null));
	}
	
}
