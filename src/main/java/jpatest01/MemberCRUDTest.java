package jpatest01;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MemberCRUDTest {

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
            // INSERT
            Member1 member = new Member1();
            member.setId(1L);
            member.setName("helloB");
            em.persist(member);*/


            /*
            // SELECT One
            Member1 findMember = em.find(Member1.class, 1L);
            System.out.println(findMember.getId());
            System.out.println(findMember.getName());
            */


            /*
            // SELECT List - JPQL 사용
            List<Member1> result = em.createQuery("select m from Member1 as m", Member1.class)
                    .setFirstResult(0)
                    .setMaxResults(8)
                    .getResultList();

            for (Member1 member : result){
                System.out.println(member.getName() + " " + member.getId());
            }
            */


            /*
            // UPDATE - 엔티티 변경 감지(Dirty Checking)
            Member1 findMember = em.find(Member1.class, 1L);
            findMember.setName("HelloJpa");
            */


            /*
            // DELETE
            Member1 findMember = em.find(Member1.class, 1L);
            em.remove(findMember);
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
