package com.ssafy.user.phone;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.common.util.BasicResponse;
import com.ssafy.user.phone.request.Message;
import com.ssafy.user.phone.request.MessageApiRequest;
import com.ssafy.user.phone.response.MessageApiResponse;

@Service("UserPhoneService")
public class UserPhoneServiceImpl implements UserPhoneService {
	
	@Value("${naverCloudPlatform.serviceId}")
	String serviceId;
	
	@Value("${naverCloudPlatform.accessKeyId}")
	String accessKeyId;
	
	@Value("${naverCloudPlatform.secretKey}")
	String secretKey;
	
	@Value("${ssueb.phone}")
	String ssuebPhone;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public ResponseEntity<BasicResponse> sendSMS(String userPhone, String authNumber) throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException, JsonProcessingException, URISyntaxException {
		
		// 요청을 보낼 URL
		String path = "/sms/v2/services/"+serviceId+"/messages";
		URI uri = new URI("https://sens.apigw.ntruss.com" + path);
		
		// Request 요청 값 생성
		MessageApiRequest messageApiRequest = new MessageApiRequest();
		messageApiRequest.setType("SMS");
		messageApiRequest.setContentType("COMM");
		messageApiRequest.setCountryCode("82");
		messageApiRequest.setFrom(ssuebPhone);
		messageApiRequest.setContent("[SSUEB] 인증번호 ["+authNumber+"]를 입력해 주세요.");
		List<Message> messages = new ArrayList<>();
		messages.add(new Message(userPhone));
		messageApiRequest.setMessages(messages);
		
		// 생성한 Request를 json형식으로 변경
		ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(messageApiRequest);
		
        // header 생성
        HttpHeaders header = makeHeader("POST", path);
        
		// header와 request를 넣어 요청 body 생성
		HttpEntity<String> body = new HttpEntity<>(jsonBody, header);
		
		// Api 요청 보내기
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		ResponseEntity<MessageApiResponse> response = restTemplate.postForEntity(uri, body, MessageApiResponse.class);
		
		// 요청에 대한 응답을 받아서 성공, 실패 확인
		String result = response.getBody().getStatusName();
		
		// 메세지 전송에 성공한 경우
		if (result.equals("success")) {
			return new ResponseEntity<BasicResponse>(new BasicResponse("success", "인증번호를 전송했습니다."), HttpStatus.OK);
		} 
		
		// 메세지 전송에 실패한 경우
		return new ResponseEntity<BasicResponse>(new BasicResponse("failure", "인증번호를 전송에 실패했습니다. 전화번호를 다시 확인해 주세요."), HttpStatus.OK);
		
	}
	
	@Override
	public String makeAuthNumber(String userPhone) {
		
		// 6자리 인증번호 생성
		Random random = new Random();
		
		// 6자리로 형식 맞추기
		DecimalFormat df = new DecimalFormat("000000");
		String authNumber = String.valueOf(df.format(random.nextInt(1000000)));
		
		// Redis에 인증번호 저장, 4분이 지나면 없어진다.
		ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
		valueOperations.set(userPhone, authNumber, 4, TimeUnit.MINUTES);
		
		return authNumber;
	}
	
	@Override
	public HttpHeaders makeHeader(String method, String url) throws InvalidKeyException, IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException {
		
		// 새로운 헤더 객체 생성
		HttpHeaders headers = new HttpHeaders();
		
		// 현재 밀리초, API Gateway 서버와 시간 차가 5분 이상 나는 경우 유효하지 않은 요청으로 간주
		String timestamp = String.valueOf(System.currentTimeMillis());
        headers.set("x-ncp-apigw-timestamp", timestamp);
        
        // 네이버 클라우드 플랫폼 포털이나 Sub Account에서 발급받은 Access Key ID
        headers.set("x-ncp-iam-access-key", accessKeyId);
        
        // Body를 Access Key ID와 맵핑되는 Secret Key로 암호화한 서명값
        headers.set("x-ncp-apigw-signature-v2", makeSignature(method, url, timestamp));
        
        // 형식을 JSON으로 지정
        headers.setContentType(MediaType.APPLICATION_JSON);
        
		return headers;
		
	}
	
	@Override
	public String makeSignature(String method, String url, String timestamp) throws IllegalStateException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		
		// 네이버 클라우드의 signatureKey 암호화 코드
		String message = new StringBuilder().append(method).append(" ").append(url)
											.append("\n").append(timestamp).append("\n")
											.append(accessKeyId).toString();
		
		// StringToSign을 생성하고 SecretKey로 HmacSHA256 알고리즘으로 암호화
		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		
		// Base64로 인코딩
		String encodeBase64String = Base64.encodeBase64String(rawHmac);
		
		return encodeBase64String;

	}
}
