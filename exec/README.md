# SSUEB 포팅 매뉴얼

## 목차
- [배포 가이드](#배포-가이드)
- [외부 서비스 정리 문서](#외부-서비스-정리-문서)
- [DB 덤프 파일](https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801/-/tree/main/exec/file/A801_ssafy_common_DB_dump.zip)
- [시연 시나리오](https://mercury-sole-13c.notion.site/SSUEB-2a19a3c7ef9c41149ab8edcb374b7f99)

## 배포 가이드
### 0. 개요
- **JVM** openjdk 11
- **IDE** eclipse-jee-2020-06-R-win32-x86_64
  - Spring Tools 3 (Standalone Edition) 3.9.14.RELEASE 추가 설치 필요
- **기타 정보(WAS, 사용된 모듈)** backend/build.gradle, frontend/package.json에서 확인 가능

### 1. Java
- openvidu 2.25.0은 openjdk 11부터 사용 가능
- openjdk 11 설치
- [설치 가이드 웹사이트](https://codechacha.com/ko/ubuntu-install-open-jdk11/)

### 2. Openvidu on premises
- **설명**
  - openvidu 2.25.0 이용
- **실행 방법**
  - [설치 가이드 웹사이트](https://docs.openvidu.io/en/stable/deployment/ce/on-premises/#domain-and-ssl-configuration-examples)
  - 위 사이트에서 1) Prerequisites, 2) Deployment, 3) Configuration, 4) Execution 순차적으로 실행
  - 실행된 컨테이너중 사용하지 않는 `openvidu/openvidu-call:2.25.0`, `openvidu/openvidu-proxy:2.25.0` 종료

### 3. Nginx Docker Image
- **설명**
  - SSUEB 프로젝트 전용 nginx 도커 이미지 생성
  - openvidu/openvidu-proxy:SSEUP.1.2
  - openvidu/openvidu-proxy:2.25.0을 기반으로 프로젝트에 맞는 nginx 도커 이미지 생성하는 단계
- **실행 방법**
  1. [openvidu에서 제공하는 openvidu-proxy 이미지를 만드는 파일](https://github.com/OpenVidu/openvidu/tree/master/openvidu-server/docker/openvidu-proxy)을 커스텀해서 이용
  2. 커스텀한 zip파일 다운로드 후 압축 풀기 ([다운로드 하기](https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801/-/tree/main/exec/file/openvidu-proxy.zip))
    ![openvidu-proxy 디렉토리](/exec/image/openvidu_proxy_directory.png)
  3. openvidu-proxy 디렉토리에 들어가 아래와 같이 create_image.sh 실행
      ```
      ./create_image.sh SSEUP.1.2
      ```
  4. 이미지 생성 확인
    ![openvidu-proxy 이미지 확인](/exec/image/openvidu_proxy_image.png)

### 4. openvidu/openvidu-proxy:SSEUP.1.2 실행
- **설명**
  - openvidu에서 제공한 docker-compose.yml를 기반으로 nginx : openvidu-proxy에 대한 설정만 커스텀
  - [관련 링크]((https://github.com/OpenVidu/openvidu/blob/master/openvidu-server/deployments/ce/docker-compose/docker-compose.yml))
- **실행 방법**
  1. [docker-compose.zip](https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801/-/tree/main/exec/file/docker-compose.zip) 다운로드 후 압축 해제
  2. 두 파일(`.env`, `docker-compose.yml`)을 `/home/ubuntu/myproject` 디렉토리 안에 놓기
  3. `.env`에서 PUBLIC_IP, MY_SECRET 입력
  ![env 파일 예시](/exec/image/openvidu_env.png)
  4. docker-compose.yml
  ![docker-compose.yml](/exec/image/docker_compose_yml.png)
  5. 이후 프론트앤드 빌드 시, `/home/ubuntu/mydocker/frontend`에 배포 파일이 만들어지고, 이는 nginx 컨테이너의 `/var/www/dist`에 빌드파일이 연동됩니다.
  6. docker-compose로 실행
      ```
      docker-compose up -d
      ```

### 5. MySQL Docker 이미지 다운로드 및 실행
- 버전정보 mysql 5.7.35
  ```jsx
  docker pull mysql:5.7.35
  docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=ssafy -d -p 3307:3306 mysql:latest
  ```
- DB 접속
  - 비밀번호 ssafy 입력
    ```jsx
    docker exec -it mysql-container bash
    mysql -u root -p
    ```

### 6. Redis Docker 이미지 다운로드 및 실행
- 버전정보 redis 3.0.5
  ```jsx
  docker pull redis:3.0.5
  docker run --name redis -d -p 6379:6379 redis:3.0.5 redis-server --requirepass "ssueb2301256a"
  ```

### 7. git clone 및 설정 파일 변경
- **git clone 받기**
    ```
    git clone https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801.git
    ```
    ![git clone](/exec/image/git_clone.png)
- **backend 설정 파일 수정**
  - backend/src/main/resources 경로에 [backend_properties.zip](https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801/-/tree/main/exec/file/backend_properties.zip) 압축 해제 후 추가, 덮어쓰기
    - `env.properties` 파일 수정 필수
      1. [Naver Cloud Flatform 수정 가이드](#5-naver-cloud-platform) 외부 서비스 정리 문서 > 5. Naver Cloud Platform
      2. Openvidu의 {PUBLIC IP}는 사용자 기기의 pubilc IP또는 domain 입력
    - `application.yml` 파일 수정 필수
      1. [Kakao Login 수정 가이드](#1-kakao-login) 외부 서비스 정리 문서 > 1. Kakao Login
      2. [Google Login 수정 가이드](#2-google-login) 외부 서비스 정리 문서 > 2. Google Login
    - `application-mail.yml` 파일 수정 필수
      1. [Java E-mail 수정 가이드](#4-java-e-mail) 외부 서비스 정리 문서 > 4. Java E-mail
  - backend/src/main/java/com/ssfay/config 경로에 [config.zip](https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801/-/tree/main/exec/file/backend_config.zip) 압축 해제 후 덮어쓰기

- **frontend 설정 파일 수정**
  - frontend 경로에 [frontend_properties.zip](https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801/-/tree/main/exec/file/frontend_properties.zip) 압축 해제 후 추가, 덮어쓰기
    - `.env` 파일 수정 필수
      1. {PUBLIC IP}는 사용자 기기의 pubilc IP또는 domain 입력
      2. [Kakao Login 수정 가이드](#1-kakao-login) 외부 서비스 정리 문서 > 1. Kakao Login
      2. [Google Login 수정 가이드](#2-google-login) 외부 서비스 정리 문서 > 2. Google Login
      4. [Bootpay 수정 가이드](#3-bootpay) 외부 서비스 정리 문서 > 3. Bootpay

### 8. Frontend 빌드
- 루트 디렉토리에서 하위 frontend 디렉토리로 이동
  ```
  npm i
  npm run build
  ```

### 9. Backend 실행
1. gradlew 최초 한번 권한 부여 후 실행
    ```
    chmod +x gradlew
    ```
2. 실행
    ```
    ./gradlew clean bootRun
    ```

### 10. 동작 확인
`http://PUBLIC_DOMAIN`으로 접근해 잘 실행되는지 확인

## 외부 서비스 정리 문서
### 1. Kakao Login
1. [kakao developers](https://developers.kakao.com/) 접속
2. 내 애플리케이션에 프로젝트 SSUEB 추가
  ![project SSUEB](/exec/image/kakao/kakao_project_ssueb.png)
3. Rest API Key 사용 예정
  ![Rest API Key 사용 예정](/exec/image/kakao/kakao_app_key.png)
4. 사이트 도메인 등록
  ![사이트 도메인 등록](/exec/image/kakao/kakao_domain.png)
5. 카카오 로그인 활성화 ON
  ![카카오 로그인 활성화 ON](/exec/image/kakao/kakao_login_activate.png)
6. REDIRECT_URI 등록
  ![REDIRECT_URI 등록](/exec/image/kakao/kakao_redirect.png)
7. 동의 항목 설정
  ![동의 항목 설정](/exec/image/kakao/kakao_redirect.png)
8. Client Secret 코드 발급
  ![Client Secret 코드 발급](/exec/image/kakao/kakao_client.png)
9. Frontend `.env` 파일 내 Kakao Oauth를 위한 설정 추가
    ```
    VUE_APP_OAUTH_KAKAO_CLIENT={REST API 키}
    VUE_APP_OAUTH_KAKAO_CLIENT_SECRET={Client Secret 코드}
    VUE_APP_OAUTH_KAKAO=socialKakao

    VUE_APP_OAUTH_REDIRECT_URI=https://i8a801.p.ssafy.io

    VUE_APP_API_BASE_URL=https://i8a801.p.ssafy.io/api
    VUE_APP_BASE_URL=https://i8a801.p.ssafy.io
    ```
10. Backend `application.yml` 내에 Kakao Oauth를 위한 설정 추가
    ```
    # for. Kakao 
    kakao:
      secret: {Client Secret 코드}
    ```
    ![application.yml](/exec/image/kakao/kakao_final.png)

### 2. Google Login
1. [Google Cloud Platform](https://console.cloud.google.com/) 접속
2. 프로젝트 등록
  ![프로젝트 등록](/exec/image/google/google_regist_project.png)
  - [설정 참고 링크](https://velog.io/@tkdfo93/%EA%B5%AC%EA%B8%80-OAuth2.0-Final-Project)
3. 사용자 인증 정보 생성
  ![사용자 인증 정보 생성](/exec/image/google/google_create_user.png)
  ![승인된 리디렉션 URI](/exec/image/google/google_accept_redirection.png)
4. OAuth 동의 화면 생성
  ![OAuth 동의 화면 생성](/exec/image/google/google_oauth.png)
5. Frontend `.env` 내에 Google Oauth를 위한 설정 추가
    ```
    VUE_APP_OAUTH_GOOGLE_CLIENT={Client ID}
    VUE_APP_OAUTH_GOOGLE_CLIENT_SECRET={Client 보안 비밀}
    VUE_APP_OAUTH_GOOGLE=socialGoogle

    VUE_APP_OAUTH_REDIRECT_URI=https://i8a801.p.ssafy.io (카카오 때 추가했다면 안해도 됨)

    VUE_APP_API_BASE_URL=https://i8a801.p.ssafy.io/api  (카카오 때 추가했다면 안해도 됨)
    VUE_APP_BASE_URL=https://i8a801.p.ssafy.io (카카오 때 추가했다면 안해도 됨)
    ```
6. Backend `application.yml` 내에 Google Oauth를 위한 설정 추가
    ```
    # for. Google
    google:
      secret: {Client 보안 비밀}
    ```
    ![application.yml](/exec/image/google/google_final.png)

### 3. Bootpay
1. Bootpay [회원가입](https://admin.bootpay.co.kr/join) 및 [로그인](https://admin.bootpay.co.kr/login)
2. [새 프로젝트 생성](https://admin.bootpay.co.kr/join)하기 https://admin.bootpay.co.kr/join
4. [링크](https://admin.bootpay.co.kr/payment/app_key)에서 Javascript 키, REST API 키, Private Key를 복사.
5. Frontend `.env` 내에 Bootpay를 위한 설정 추가
    ```
    VUE_APP_BOOTPAY_JS={부트페이 Javascript KEY}
    VUE_APP_BOOTPAY_REST={부트페이 REST API KEY}
    VUE_APP_BOOTPAY_PRIVATE={부트페이 Private KEY}
    ```
6. [링크](https://admin.bootpay.co.kr/payment/setting)에서 결제수단 설정 (KCP, ISP, 샌드박스 모드)

### 4. Java E-mail
1. 메일을 전송할 Google 계정 환경설정 [참고 링크](https://ajdahrdl.tistory.com/236)
2. Backend `application-mail.yml` 내 설정 작성
    ```
    spring:
    mail:
      default-encoding: utf-8
      host: smtp.gmail.com
      port: 587
      username: "{메일을 발송하는 계정 id(email)}"
      password: "{1번에서 생성한 앱 비밀번호}"
      properties:
        mail:
          smtp:
            auth: true
            starttls:
              enable: true
    ```
3. Backend `application.yml` 내 추가
    ```
    spring:
      # for. mail 정보 가져오기
      profiles:
        include: mail
    ```
  ![이메일](/exec/image/email_final.png)
4. Naver 계정으로 메일을 받는 경우 SMTP 허용 필요
  ![네이버 이메일](/exec/image/naver_email.png)

### 5. Naver Cloud Platform
1. [Naver Cloud Platform](https://www.ncloud.com/) 접속
2. 회원가입 및 로그인 진행
3. 사이트 상단 우측 Console 접속
4. 왼쪽 사이드바 **Services**에서 **Simple & Easy Notification Service** 검색
5. **Simple & Easy Notification Service**에 Project에서 Project 추가 (서비스 타입 SMS 선택)
6. 추가된 프로젝트의 SMS의 service ID를 Backend `env.properties`에 추가
    ```
    naverCloudPlatform.serviceId={service ID}
    ```
7. 마이페이지 > 계정 관리 > 인증키 관리에서 Access Key ID와 Secret Key 복사
    ```
    naverCloudPlatform.accessKeyId={Access Key ID}
    naverCloudPlatform.secretKey={Secret Key}
    ```
8. Console > Simple & Easy Notification Service > SMS > Calling Number에 휴대폰 번호 추가
9. 추가한 휴대폰 번호 Backend `env.properties`에 추가
    ```
    # Phone Number
    ssueb.phone={휴대폰 번호}
    ```

## DB 덤프 파일
- [DB 덤프 파일 다운로드](https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801/-/tree/main/exec/file/A801_ssafy_common_DB_dump.zip)

## 시연 시나리오
- [시연 시나리오](https://mercury-sole-13c.notion.site/SSUEB-2a19a3c7ef9c41149ab8edcb374b7f99)
- [서비스 화면 링크 참고](https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801#%EC%84%9C%EB%B9%84%EC%8A%A4-%ED%99%94%EB%A9%B4)