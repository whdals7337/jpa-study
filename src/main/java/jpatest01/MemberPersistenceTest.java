package jpatest01;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MemberPersistenceTest {

    public static void main(String[] args) {
        // persistence.xml 의 persistence-unit 을 받아서 EntityManagerFactory 생성 - 하나만 생성해서 전체 어플리케이션 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test01");

        //EntityManagerFactory 에서 EntityManager 생성 - 쓰레드간에 공유 X (사용하고 버려야함)
        EntityManager em = emf.createEntityManager();

        // EntityManager 의 트랜잭션 받아옴
        EntityTransaction tx = em.getTransaction();

        // 트랜잭션 시작 - 모든 데이터 변경은 이 안에서 처리해야함
        tx.begin();
        try {
            /*
            // 비영속, 영속, 준영속
            Member1 member = new Member1(102L, "helloC");
            em.persist(member); // 비영속 -> 영속
            em.detach(member); // 영속 -> 준영속
            */


            /*
            // 1차 캐시, 영속성 엔티티의 동일성
            Member1 member = new Member1(103L, "helloC");
            em.persist(member);
            Member1 findMember = em.find(Member1.class, 103L); // 1차 캐시에 있는 값
            System.out.println("result: " + (member == findMember)); // true
            */


            /*
            // 엔티티 등록 지연 쓰기 - insert 문이 영속된 객체를 모아서 처리
            Member1 member1 = new Member1(104L, "hello1");
            Member1 member2 = new Member1(105L, "hello2");

            em.persist(member1);
            em.persist(member2);
            System.out.println("=== 영속 시점 ===");
            */


            /*
            // flush - 플러쉬 살행 시점 (1. flush() 메서드, 2. 트랜잭션 commit, 3. JPQL 쿼리 실행)
            Member1 member = new Member1(105L, "helloFlush");
            em.persist(member);
            em.flush();
            System.out.println("flush 할 경우 더티 체크 및 지연 쓰기가 실행됩니다.");
            */

             /*
             // 영속성 컨텍스트 초기화
            Member1 member = new Member1(106L, "helloC");
            em.persist(member);
            em.clear();
            */


            tx.commit();
        } catch (Exception e){
            // 롤백
            tx.rollback();
        } finally {
            // EntityManager 닫음
            em.close();
        }

        // EntityManagerFactory 닫음
        emf.close();
    }
}
