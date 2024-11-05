# Nutri-Diary
* 오늘 하루 섭취한 음식을 다이어리에 기록하고, 유용한 다이어트 음식을 공유하는 서비스입니다.
# 서비스 아키텍처
<a href="link"><img src="https://github.com/user-attachments/assets/323f0689-1acb-468a-a5e0-19c70399023c" width="70%"></a>
* NCP(Naver Cloud Platform)을 이용하여 아케틱처를 구성했습니다.
* AutoScaling Group을 활용하여 트래픽이 증가할 경우 Scale out되어 트래픽을 분산 처리하도록 구성했습니다.
* CI/CD 와 무중단 배포를 위해 Jenkins와 NCP의 SourceDeploy을 이용했습니다.
  * GitHub의 Webhook 을 이용하여 Jenkins서버에서 테스트와 빌드를 진행합니다.
  * 그 후 빌드된 파일을 NCP의 ObjectStorage에 저장합니다.
  * 젠킨스에서 SourceDeploy을 실행시켜 무중단 배포가 진행되도록 하였습니다.
* 로드밸런스에 SSL을 적용하여 HTTPS 프로토콜을 이용한 통신을 합니다.
* 보안상 중요하게 다뤄야할 리소스를 안전하게 보호하기위해 DB서버와 애플리케이션 서버를 private subnet에 배치했습니다.
# 프로토타입 화면
![nutri-diary-prototype](https://github.com/user-attachments/assets/ccbbc96a-027e-46e2-829e-f724056f5dff)
# skills
- Spring Boot3, Java17, Spring Data JDBC, MySQL, NCP(Naver Cloud Platform), Jenkins
# Git 브랜치 전략
* Git flow 전략을 사용하고 있습니다.
  * master : 제품으로 출시될 수 있는 브랜치
  * develop : 다음 출시 버전을 개발하는 브랜치
  * feature : 기능을 개발하는 브랜치
  * release : 이번 출시 버전을 준비하는 브랜치
  * hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치
