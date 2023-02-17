![SSUEB](/docs/image/logo.png)

# SSUEB 포팅 매뉴얼

## 목차

## 빌드 및 배포 가이드
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
  1. [docker-compose.zip]() 다운로드 후 압축 해제
  2. 두 파일(`.env`, `docker-compose.yml`)을 `/home/ubuntu/myproject` 디렉토리 안에 놓기