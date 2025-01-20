# Nutri-Diary(개인 프로젝트)
> 기술 스택: Spring Boot, Spring Data JDBC, JdbcTemplate, MySQL, Elasticsearch, NCP(Naver Cloud Platform), Jenkins, Grafana, Prometheus, nGrinder

## 2024.07 ~ 2024.12
현재 개인적으로 진행 중이며, 백엔드 부분을 전담하고 있습니다. 2025년 1월부터는 팀원들과 함께 기획, 디자인, 클라이언트 부분을 재구성해 나갈 예정입니다.

## 프로젝트 소개
오늘 하루 섭취한 음식을 다이어리에 기록하고, 유용한 다이어트 식품에 대한 정보를 공유하는 서비스입니다.

사용자는 날짜별로 섭취한 식품을 기록하여 하루 전체 또는 각 식사별 영양 정보를 체크할 수 있습니다. 사용자들은 새로운 식품 정보를 등록하고 기존 식품에 대한 후기를 댓글로 공유하여 다이어터들의 정보 불균형을 해소할 수 있습니다. 또한 자신이 선호하는 식단에 해당하는 식품들을 탐색할 수 있습니다.

## 성과
프로젝트 시작 전에는 객체지향 프로그래밍에 대한 실질적인 이해가 부족했습니다. 하지만, 프로젝트를 진행하면서 코드 리뷰를 통해 이전에는 알지 못했던 문제점들을 발견할 수 있었습니다. 

코드를 이해하기 어렵다는 평가를 받았고, 요구사항이 변경되는 경우 유연하게 수정하기 어렵다는 점 등이 있었습니다.

이러한 피드백을 통해, 코드는 단순히 기능을 구현하거나 개인의 기록을 남기는 수단이 아니라, 다른 개발자들과 협업을 고려해야 하는 공동의 작업물임을 깨달았습니다.

특히, 객체지향 원칙을 이해하고 이를 코드에 적용하는 것이 이러한 목표를 달성하는 핵심이라는 점을 인식하게 되었으며, 이후 코드 작성 시 이러한 원칙을 적용하기 위해 고민하고 개선해 나갔습니다.

## Architecture
<img src="https://github.com/user-attachments/assets/4f8a65e4-8c32-40e6-9735-8d8345152b6f" width="90%">

## 개요
* 프로젝트 구성 과정

  프로젝트 초기 단계에서 Figma를 활용해 화면 정의서를 작성하고 서비스를 구상했습니다. 이를 바탕으로 필요한 요구사항들을 정의해 나갔으며, 비즈니스 흐름에 따라 필요한 정보들을 저장할 DB 스키마를 정의하고 중복을 최소화하는 방향으로 설계를 진행했습니다.

* HTTP 통신의 보안 취약점을 해소할 필요가 존재

  이를 위해 ALB(Application Load Balancer)에 SSL 인증서를 적용하여 HTTPS 프로토콜을 통한 안전한 통신을 적용했습니다.

* 리소스별 외부 접근 제한 필요

  리소스별 외부 접근을 제한하기 위해 Public Subnet과 Private Subnet을 분리했습니다.

  DB 서버와 애플리케이션 서버는 보안상 중요한 자원이므로 Private Subnet에 배치하였고, 이후 필요한 경우에만 Bastion 서버를 통해 애플리케이션 서버에 접근할 수 있도록 설정했습니다.

* 애플리케이션의 가용성을 확보하고자 함

  가용성 확보를 위해 트래픽 증가에 대응하는 ALB와 Auto Scaling을 적용했습니다.

  애플리케이션 서버가 상태를 저장하지 않는 특성을 고려해 라운드 로빈 방식의 ALB를 선택하였고, 메모리나 CPU 사용량이 60% 이상을 5분 이상 유지하면 Scale-out을, 그 미만으로 내려가면 Scale-in을 적용하도록 설정했습니다.

* CI/CD와 무중단 배포 인프라 구성

  Jenkins 서버를 별도로 구축하여 개발 과정의 CI/CD를 수행하고, NCP의 SourceDeploy(AWS의 CodeDeploy와 유사)를 활용해 Auto Scaling Group에 Blue/Green 방식의 무중단 배포 인프라를 구성하여 배포과정을 자동화했습니다. 

* JUnit을 활용한 테스트 코드 작성

  리팩터링 과정에서 생산성 향상과 애플리케이션의 구조의 안정성을 위해 테스트 코드를 작성했습니다.

  JaCoCo 기준으로 테스트 커버리지 80% 이상을 유지했으며, 테스트 코드 작성이 어려운 경우에는 기존 애플리케이션 코드 구조의 문제점을 파악하고 리팩터링을 진행했습니다.

## 주요 딥다이브 이슈
* MySQL의 전문 검색(FullText Search)을 사용하는 쿼리의 실행 계획을 분석하고 구조를 개선하여 쿼리의 전체 수행 시간 30배 단축
  > [자세히 보기: [https://github.com/koo995/portfolio/blob/main/nutri-diary/fulltext-search.md](https://github.com/koo995/portfolio/blob/main/nutri-diary/fulltext-search.md)]
  
  MySQL의 전문 검색(Full-Text Search)을 사용하는 API에서 인덱스가 적용되었지만, 응답 시간이 5초 이상 소요되었습니다.

  인덱스 적용 여부를 확인했지만 인덱스는 잘 적용되었고 그렇다면 다른 문제가 있을 것이라 판단해 쿼리의 실행 계획을 분석했습니다.

  이후 쿼리를 최적화하여 쿼리의 수행 시간을 3~4초에서 0.08초로 40배 단축했습니다.

  * 최적화한 쿼리를 사용한 API의 문제점을 발견하고 리팩터링과 Elasticsearch 도입으로 평균 응답 시간을 1.67분에서 1초, TPS 1.9에서 185.6으로 100배 개선
    > [자세히 보기: [https://github.com/koo995/portfolio/blob/main/nutri-diary/elasticsearch.md](https://github.com/koo995/portfolio/blob/main/nutri-diary/elasticsearch.md)]

    쿼리를 최적화하였지만 비즈니스 로직이 쿼리안에 녹아있는 문제가 있었습니다. 이 문제는 추후 비즈니스 로직이 변경될 경우 데이터 접근 기술까지 변경 해야하는 문제가 있습니다.

    이 문제를 해결하고자 리팩터링을 진행하여 비즈니스 로직과 데이터 접근 기술의 강결합을 분리하여 더욱 객체지향적인 코드로 작성했습니다. 또한, 이 과정에서 비즈니스 로직과 DB테이블 일부를 변경하여 예상되는 누적 데이터량을 3억건에서 1천만건으로 최적화했습니다.

    하지만 리팩터링 후 쿼리의 수행 시간을 비교하려 했으나, 테이블 구조 변경과 새로운 데이터 삽입으로 인해 신뢰할 수 있는 비교가 어려웠습니다. 더 나은 기준으로 비교하기 위해 Grafana, Prometheus와 같은 모니터링 도구와 트래픽 생성을 위한 nGrinder를 도입했습니다.

    이후 성능 테스트를 진행하는 과정에서 문제를 쿼리 개선에만 집중하여 API의 성능 저하가 여전히 존재함을 발견했습니다. 결국 기존에 도입을 미뤘던 Elasticsearch를 활용하여 평균 응답 시간을 1.67분에서 1초, TPS를 1.9에서 179.6으로 100배 개선했습니다.

    * Tomcat의 스레드풀 사이즈와 HikariCP의 커넥션 풀 사이즈를 조절하여 API의 평균 응답시간을 1초에서 200ms로 5배 개선

      Elasticsearch를 도입했지만 1초라는 API의 평균 응답 시간이 체감상 빠르지 않다고 느껴졌습니다.

      이를 개선하고자 모니터링 지표를 확인하니 TIMED-WAITING 상태의 스레드 비율이 너무 높은 것을 확인하고 스레드 덤프를 분석하여 HikariCP의 커넥션을 얻기 위해 대기하는 스레드가 매우 많음을 발견했습니다.

      이후 여러번의 테스트에서 Tomcat의 스레드 풀 사이즈와 HikariCP의 커넥션 풀 사이즈를 조절하여 평균 응답 시간을 1초에서 200ms로 5배 단축했습니다.

      > [자세히 보기: [https://github.com/koo995/portfolio/blob/main/nutri-diary/threadPool-hikariPool.md](https://github.com/koo995/portfolio/blob/main/nutri-diary/threadPool-hikariPool.md)]

## 주요 이슈
* Spring Data JDBC(또는 JPA)에서 DB 엔진에 따른 데이터 타입의 변환 차이 이슈
  >[자세히 보기: [https://github.com/koo995/portfolio/blob/main/nutri-diary/json-converter.md](https://github.com/koo995/portfolio/blob/main/nutri-diary/json-converter.md)]
    
  테스트 코드 실행 시 JSON 타입 컬럼값을 객체로 변환하지 못하는 문제가 발생했습니다. 상황을 구체적으로 파악하기 위해 여러 케이스를 테스트하는 도중 JPA에서도 유사한 문제가 발생했습니다.
  
  문제의 원인을 파악하기 위해 디버깅을 진행하며 추적한 결과, 이는 H2가 JSON 타입의 컬럼을 MySQL과 다르게 처리해서 발생하는 문제로 확인되었습니다.

  여러 방안을 검토하며 테스트용 컨버터를 만들기, TestContainers를 도입, JSON 타입 대신 TEXT 타입을 사용하는 방법 중에서 마지막 방법을 최종적으로 선택했습니다.

  또한, JPA를 사용하는 경우 hypersistence-utils 라이브러리가 문제를 해결할 수 있었습니다.
    
* 단순한 비즈니스 로직의 과도한 복잡성을 개선하여 간결한 구조로 리팩터링
  > [자세히 보기: [https://github.com/f-lab-edu/nutri-diary/wiki/영양성분-계산-로직-리팩터링](https://github.com/f-lab-edu/nutri-diary/wiki/%EC%98%81%EC%96%91%EC%84%B1%EB%B6%84-%EA%B3%84%EC%82%B0-%EB%A1%9C%EC%A7%81-%EB%A6%AC%ED%8C%A9%ED%84%B0%EB%A7%81)]
  
  기존에는 비즈니스 로직을 전략 패턴과 팩토리 패턴을 적용하여 구현했습니다. 하지만 코드 리뷰를 통해 이 접근 방식이 비즈니스 로직을 이해하기 어렵게 만들고, 요구사항에 비해 복잡하다는 피드백을 받았습니다.
  
  3번의 리팩터링을 거쳐, 공통 부분을 추상화하고 Value Object를 생성했고, 이를 통해 11개의 클래스와 340줄의 코드를 5개의 클래스와 235줄의 코드로 줄여 더 간결하고 이해하기 쉬운 구조로 개선했습니다.

## NCP를 이용한 인프라 구성 과정
  * [Auto Scaling구성과 LoadBalance연결](https://medium.com/@gunhong951/ncp-naver-cloud-platform-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%9D%B8%ED%94%84%EB%9D%BC-%EA%B5%AC%EC%84%B1%ED%95%98%EA%B8%B0-1%ED%8E%B8-auto-scaling%EA%B3%BC-load-balancer-%EA%B5%AC%EC%84%B1-117de2df73c2)
  * [Jenkins와 SourceDeploy을 이용한 CI/CD와 무중단 배포](https://medium.com/@gunhong951/ncp-naver-cloud-platform-%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-%EC%9D%B8%ED%94%84%EB%9D%BC-%EA%B5%AC%EC%84%B1%ED%95%98%EA%B8%B0-2%ED%8E%B8-jenkins%EC%99%80-sourcedeploy%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-ci-cd-%EB%AC%B4%EC%A4%91%EB%8B%A8-%EB%B0%B0%ED%8F%AC-%EA%B5%AC%EC%84%B1-ca1926e56c34)
## 프로토타입 화면
![nutri-diary-prototype](https://github.com/user-attachments/assets/ccbbc96a-027e-46e2-829e-f724056f5dff)

