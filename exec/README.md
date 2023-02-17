# SSUEB 포팅 매뉴얼

## 목차
- [배포 가이드](#배포-가이드)
  - [1. Java](#1.-Java)

## 배포 가이드
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

### 5. MySQL Dokcer 이미지 다운로드 및 실행
- 버전정보 mysql 5.7.35
```jsx
docker pull mysql:5.7.35
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=ssafy -d -p 3307:3306 mysql:latest
```

### 6. Redis Docker 이미지 다운로드 및 실행
- 버전정보 redis 3.0.5
```jsx
docker pull redis:3.0.5
docker run --name redis -d -p 6379:6379 redis:3.0.5 redis-server --requirepass "ssueb2301256a"
```

### 7. git clone 및 설정 파일 변경
1. git clone 받기
    ```
    git clone https://lab.ssafy.com/s08-webmobile1-sub2/S08P12A801.git
    ```
    ![git clone](/exec/image/git_clone.png)
2. backend 설정 파일 수정
  - backend/src/main/resources 경로에 아래 설정파일로 추가, 덮어쓰기

  - backend/src/main/java/com/ssafy/config 경로에 아래 java파일로 덮어쓰기

3. frontend 설정 파일 수정
  - frontend 경로에 아래 설정파일 추가, 덮어쓰기

### 8. Frontend 빌드
루트 디렉토리에서 하위 frontend 디렉토리로 이동
```
npm i
npm run build
```

### 9. Backend 실행
gradlew 최초 한번 권한 부여 후 실행
```
./gradlew clean bootRun
```

### 10. 동작 확인
`http://PUBLIC_DOMAIN`으로 접근해 잘 실행되는지 확인