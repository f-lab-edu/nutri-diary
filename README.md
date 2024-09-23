# Nutri-Diary
다이어트 음식을 기록하고 공유하는 서비스입니다.
# 서비스 아키텍처
<a href="link"><img src="https://github.com/user-attachments/assets/79f1340e-415e-4b27-9c2c-e8a589139b93" width="70%"></a>
* NCP(Naver Cloud Platform)을 이용하여 아케틱처를 구성했습니다.
* AutoScaling Group을 활용하여 트래픽이 증가할 경우 Scale out되어 트래픽을 분산 처리하도록 구성했습니다.
* CI/CD 와 무중단 배포를 위해 젠킨스와 NCP의 SourceDeploy 을 이용했습니다.
  * GitHub의 webhook 을 이용하여 젠킨스 서버에서 테스트와 빌드를 진행합니다.
  * 그 후 빌드된 파일을 NCP의 Object Storage 에 저장합니다.
  * 젠킨스에서 SourceDeploy 을 실행시켜 무중단 배포가 진행되도록 하였습니다.
# 프로토타입 화면
![nutri-diary-prototype](https://github.com/user-attachments/assets/ccbbc96a-027e-46e2-829e-f724056f5dff)
# skills
- Spring Boot3, Java21, Spring Data JDBC, MySQL, NCP(Naver Cloud Platform), Jenkins
# Git 브랜치 전략
* Git flow 전략을 사용하고 있습니다.
  * master : 제품으로 출시될 수 있는 브랜치
  * develop : 다음 출시 버전을 개발하는 브랜치
  * feature : 기능을 개발하는 브랜치
  * release : 이번 출시 버전을 준비하는 브랜치
  * hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치
