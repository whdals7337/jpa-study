인프런 
자바 ORM 표준 JPA 프로그래밍 기본편 - 김영한님

study project

jpa 사용시 유의사항
1. 로컬을 제외한 환경에서 hibernate.hbm2ddl.auto 사용하지 말아라
2. 우선 단방향으로 설계하고 필요할 경우에 양방향을 추가
3. 관계를 가지는 엔티티 중 하나의 엔티티가 수정될 경우 양쪽에 값을 셋팅해줘라
4. 일대다 보다 다대일 관계를 사용하자
5. 다대다는 사용하지말고 일대다 다대일 관계로 풀어라
6. 구현 클래스 마다 테이블 전략 쓰지 마라
