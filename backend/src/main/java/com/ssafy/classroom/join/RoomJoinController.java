package com.ssafy.classroom.join;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.classroom.interceptor.RoomValidServiceImpl;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = {"Room"}, description = "회의실 입장  api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RoomJoinController {
	
	@Autowired
	private RoomValidServiceImpl roomValidService;
	
//	@Value("${OPENVIDU_URL}")
//	private String OPENVIDU_URL;
//
//	@Value("${OPENVIDU_SECRET}")
//	private String OPENVIDU_SECRET;
	
	
//	@PostConstruct
//	public void init() {
//		this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
//	}
	
	@Autowired
	private OpenVidu openvidu;
	
	@ApiOperation(value = "회의실 세션생성", notes = "회의실이 없다면 opennvidu server에 생성. 있다면 상태 업데이트."
			+ "intercetor 로 jwt token, 시간 유효성 검사를 완료.", response = String.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	@PostMapping("/api/room/session")
	ResponseEntity<?> joinRoom(@RequestBody(required = false) Map<String, Object> params) {
		log.info("roomjoincontroller ----------------------------------------------------");
		
		//시간유효성 검사 :
		//interceptor에서 처리하는게 깔끔.
		//messageConverter 공부가 필요.
		//customSessionId => roomId 변경해야함.
		
		int roomId = Integer.parseInt((String)params.get("customSessionId"));
		log.info(""+roomId);
		try {
			if(!roomValidService.checkValid(roomId)) {
				return new ResponseEntity<String>("유효한시간이아닙니다. 화상상담 10분전부터 입장 가능합니다.", HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("", HttpStatus.SERVICE_UNAVAILABLE);
		}
		
		
		//세션생성 :
		// ID : uuid형태로 redis관리로 확장해야함.
		SessionProperties sessionProperties = SessionProperties.fromJson(params).build();
//		log.info(params.toString());

		try {
			Session session = openvidu.createSession(sessionProperties);
//			log.info(session.getSessionId());
			
			//connection 생성
			//일단 connectino config값 null 전송
			ConnectionProperties connectionProperties = ConnectionProperties.fromJson(null).build();
			Connection connection = session.createConnection(connectionProperties);
			return new ResponseEntity<String>(connection.getToken(), HttpStatus.OK);
			
		} catch (OpenViduJavaClientException | OpenViduHttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("",HttpStatus.SERVICE_UNAVAILABLE);


		}
		
	}

	
	
	
}
