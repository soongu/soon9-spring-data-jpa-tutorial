# Spring Data JPA tutorial

### 스프링 부트 라이브러리 살펴보기
- spring-boot-starter-web
  - spring-boot-starter-tomcat: 톰캣 (웹서버)
  - spring-webmvc: 스프링 웹 MVC
- spring-boot-starter-data-jpa
  - spring-boot-starter-aop
  - spring-boot-starter-jdbc
    - HikariCP 커넥션 풀 (부트 2.0 기본)
  - hibernate + JPA: 하이버네이트 + JPA
  - spring-data-jpa: 스프링 데이터 JPA
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
  - spring-boot
    - spring-core
  - spring-boot-starter-logging
    - logback, slf4j
### 테스트 라이브러리
- spring-boot-starter-test
  - junit: 테스트 프레임워크, 스프링 부트 2.2부터 junit5( jupiter ) 사용
    - 과거 버전은 vintage
  - mockito: 목 라이브러리
  - assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
    - https://joel-costigliola.github.io/assertj/index.html
  - spring-test: 스프링 통합 테스트 지원


## H2 Database 설치
- [h2 database 홈페이지](https://www.h2database.com)
- 다운로드 및 설치
- h2 데이터베이스 버전은 스프링 부트 버전에 맞춘다.
- 권한 주기: chmod 755 h2.sh
- 데이터베이스 파일 생성 방법
  - jdbc:h2:~/datajpa (최소 한번)
  - ~/datajpa.mv.db 파일 생성 확인
  - 이후 부터는 jdbc:h2:tcp://localhost/~/datajpa 이렇게 접속
> 참고: H2 데이터베이스의 MVCC 옵션은 H2 1.4.198 버전부터 제거되었습니다. 사용 버전이 1.4.199이
므로 옵션 없이 사용하면 됩니다.


### 스프링 데이터 JPA와 DB 설정, 동작 확인
```yaml
spring:
  datasource:
    url: jdbc:h2:tcp://localhost/~/datajpa
    username: sa
    password:
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create
    properties:
      hibernate:
#        show_sql: true
        format_sql: true
logging.level:
  org.hibernate.SQL: debug
#  org.hibernate.type: trace
```