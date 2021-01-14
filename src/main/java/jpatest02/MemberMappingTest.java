package jpatest02;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class MemberMappingTest {

    public static void main(String[] args) {
        // persistence.xml 의 persistence-unit 을 받아서 EntityManagerFactory 생성 - 하나만 생성해서 전체 어플리케이션 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //EntityManagerFactory 에서 EntityManager 생성 - 쓰레드간에 공유 X (사용하고 버려야함)
        EntityManager em = emf.createEntityManager();

        // EntityManager 의 트랜잭션 받아옴
        EntityTransaction tx = em.getTransaction();

        // 트랜잭션 시작 - 모든 데이터 변경은 이 안에서 처리해야함
        tx.begin();

        try {
            /*
            // pk 전략이 IDENTITY 인 경우: 비영속 -> 영속이 되면 DB 에 Insert 를 하고 id 값을 가져옴
            // pk 전략이 SEQUENCE 인 경우: 비영속 -> 영속이 되면 SEQUENCE 에서 id 값(next value)을 가져옴
            Member2 member = new Member2();
            System.out.println("======= before =======");
            em.persist(member);
            System.out.println(member.getId());
            System.out.println("======= After =======");
            */

            /*
            // pk 전략이 SEQUENCE allocationSize 에 맞춰서 시퀀스를 미리 땡겨 옴
            Member2 member1 = new Member2();
            Member2 member2 = new Member2();
            Member2 member3 = new Member2();
            System.out.println("======= before =======");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            System.out.println(member1.getId());
            System.out.println(member2.getId());
            System.out.println(member3.getId());
            System.out.println("======= After =======");
            */


            // 트랜잭션 커밋
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
