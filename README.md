# Nutri-Diary(개인 프로젝트)

## Stack: Spring Boot, Spring Data JDBC, JdbcTemplate, MySQL, NCP(Naver Cloud Platform), Jenkins

## 2024.07 ~ 진행중
현재 개인적으로 진행 중이며, 백엔드 부분을 전담하고 있습니다. 2025년 1월부터는 팀원들과 함께 기획, 디자인, 클라이언트 부분을 재구성해 나갈 예정입니다.

## 프로젝트 소개
오늘 하루 섭취한 음식을 다이어리에 기록하고, 유용한 다이어트 식품에 대한 정보를 공유하는 서비스입니다.

사용자는 날짜별로 섭취한 식품을 기록하여 하루 전체 또는 각 식사별 영양 정보를 체크할 수 있습니다. 사용자들은 새로운 식품 정보를 등록하고 기존 식품에 대한 후기를 댓글로 공유하여 다이어터들의 정보 불균형을 해소할 수 있습니다. 또한 자신이 선호하는 식단에 해당하는 식품들을 탐색할 수 있습니다.

## 성과
프로젝트 시작 전에는 객체지향 프로그래밍에 대한 실질적인 이해가 부족했습니다. 하지만, 프로젝트를 진행하면서 코드 리뷰를 통해 이전에는 알지 못했던 문제점들을 발견할 수 있었습니다. 코드를 이해하기 어렵다는 평가를 받았고, 요구사항이 변경되는 경우 유연하게 수정하기 어렵다는 점 등이 있었습니다. 이를 통해 코드가 단순히 기능 구현이나 자신만의 기록이 아니라, 다른 개발자들과 함께 코드를 쉽게 이해할 수 있도록 작성하는 방법에 대해 깊이 고민하게 되었습니다.

## 서비스 아키텍처
<a href="link"><img src="https://github.com/user-attachments/assets/4f8a65e4-8c32-40e6-9735-8d8345152b6f" width="70%"></a>

## About
프로젝트 초기 단계에서 Figma를 활용해 화면 정의서를 작성하고 서비스를 구상했습니다. 이를 바탕으로 필요한 요구사항들을 정의해 나갔습니다. 그다음, 비즈니스 흐름에 따라 필요한 정보들을 저장할 DB 스키마를 구현했으며, 중복을 최소화하는 방향으로 설계를 진행했습니다.

HTTP 통신의 보안 취약점을 해소하기 위해 ALB(Application Load Balancer)에 SSL 인증서를 적용하여 HTTPS 프로토콜을 통한 안전한 통신을 적용했습니다.

보안을 위한 망분리로 Public Subnet과 Private Subnet을 분리했습니다. DB 서버는 보안상 매우 중요하므로 외부에서의 접근을 차단해야 합니다. 애플리케이션 서버 역시 중요한 자원이므로, 두 자원을 Private Subnet에 배치했습니다. 이후 필요한 경우에만 Bastion 서버를 통해 애플리케이션 서버에 접근할 수 있도록 설정했습니다.

가용성 확보를 위해 트래픽 증가에 대응하는 ALB와 Auto Scaling을 적용했습니다. 애플리케이션 서버가 상태를 저장하지 않는 특성을 고려해 라운드 로빈 방식의 ALB를 선택했습니다. 메모리나 CPU 사용량이 60% 이상을 5분 이상 유지하면 Scale-out을, 그 미만으로 내려가면 Scale-in을 적용하도록 설정했습니다.

CI/CD와 무중단 배포 인프라 구성. Jenkins 서버를 별도로 구축하여 개발 과정의 CI/CD를 수행하고, NCP의 SourceDeploy(AWS의 CodeDeploy와 유사)를 활용해 Auto Scaling Group에 Blue/Green 방식의 무중단 배포 인프라를 구성했습니다. 

리팩토링 과정에서 생산성 향상과 애플리케이션의 구조의 안정성을 위해 테스트 코드를 작성했습니다. JaCoCo 기준으로 테스트 커버리지 80% 이상을 유지했으며, 테스트 코드 작성이 어려운 경우에는 기존 애플리케이션 코드 구조의 문제점을 파악하고 리팩토링을 진행했습니다.

영속성 계층의 의존성을 역전시켜 구현했습니다. 향후 JPA로 변경을 고려하여 서비스 레이어가 영속성 문제에 독립적으로 동작하도록 인터페이스 기반의 간접 계층을 추가했습니다. 이를 통해 영속성 계층의 리팩터링이 서비스 레이어에 영향을 주지 않도록 설계했습니다.

TestContainers를 활용한 서버 환경과 동일한 테스트 환경 구축. H2와 MySQL의 DB 엔진 차이 때문에 여러 이슈가 발생했습니다. 특히 MySQL의 전문 검색 기능을 활용한 테스트 코드는 H2에서 실행할 수 없어, TestContainers를 도입했습니다.

## NCP를 이용한 인프라 구성 과정
  * [Auto Scaling구성과 LoadBalance연결](https://medium.com/@gunhong951/ncp-naver-cloud-platform-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%9D%B8%ED%94%84%EB%9D%BC-%EA%B5%AC%EC%84%B1%ED%95%98%EA%B8%B0-1%ED%8E%B8-auto-scaling%EA%B3%BC-load-balancer-%EA%B5%AC%EC%84%B1-117de2df73c2)
  * [Jenkins와 SourceDeploy을 이용한 CI/CD와 무중단 배포](https://medium.com/@gunhong951/ncp-naver-cloud-platform-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%9D%B8%ED%94%84%EB%9D%BC-%EA%B5%AC%EC%84%B1%ED%95%98%EA%B8%B0-2%ED%8E%B8-jenkins%EC%99%80-sourcedeploy%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-ci-cd-%EB%AC%B4%EC%A4%91%EB%8B%A8-%EB%B0%B0%ED%8F%AC-%EA%B5%AC%EC%84%B1-ca1926e56c34)
## 프로토타입 화면
![nutri-diary-prototype](https://github.com/user-attachments/assets/ccbbc96a-027e-46e2-829e-f724056f5dff)

