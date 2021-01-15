package jpatest05;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ProxyTest {

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
            /* 프로식 getReference 값 참조 시점 - 필요로 하는 값이 없을때
            Member5 member = new Member5();
            member.setUsername("hello");
            em.persist(member);
            em.flush();
            em.clear();

            Member5 findMember = em.getReference(Member5.class, member.getId());
            System.out.println(findMember.getId());
            System.out.println("==================");
            System.out.println(findMember.getUsername());
            */

            /*
            // 프록시객체와 일반객체 간의 class 처리
            Member5 member1 = new Member5();
            member1.setUsername("aaaa");
            em.persist(member1);

            Member5 member2 = new Member5();
            member2.setUsername("bbb");
            em.persist(member2);

            em.flush();
            em.clear();

            Member5 m1 = em.find(Member5.class, member1.getId());
            Member5 m2 = em.getReference(Member5.class, member2.getId());
            logic(m1, m2);

            Member5 m3 = em.getReference(Member5.class, member1.getId());
            System.out.println("m3.getClass " + m3.getClass());

            Member5 m4 = em.find(Member5.class, member2.getId());
            System.out.println("m4.getClass " + m4.getClass());
            System.out.println("m4 == m2 " + (m4 == m2));
            */

            // 프로식 준영속 상태 테스트
            Member5 member = new Member5();
            member.setUsername("aaa");
            em.persist(member);

            em.flush();
            em.clear();

            Member5 refMember = em.getReference(Member5.class, member.getId());
            System.out.println("refMember: " + refMember.getClass());

            em.detach(refMember);
            //em.close();
            //em.clear();

            refMember.getUsername(); // 준영속상태에서 에러발생 LazyInitializationException: could not initialize proxy - no Session

            //로딩여부
            // emf.getPersistenceUnitUtil().isLoaded(refMember);

            // 강제 초기화
            //Hibernate.initialize(refMember);

            // 트랜잭션 커밋
            tx.commit();
        } catch (Exception e){
            // 롤백
            tx.rollback();
            e.printStackTrace();
        } finally {
            // EntityManager 닫음
            em.close();
        }

        // EntityManagerFactory 닫음
        emf.close();
    }

    private static void logic(Member5 m1, Member5 m2) {
        System.out.println(m1.getClass() == m2.getClass());
        System.out.println(m1 instanceof Member5 && m2 instanceof Member5);
    }
}
