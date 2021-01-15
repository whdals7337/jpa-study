package jpatest05;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class loadingTest {
    public static void main(String[] args) {
        // persistence.xml 의 persistence-unit 을 받아서 EntityManagerFactory 생성 - 하나만 생성해서 전체 어플리케이션 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test05");

        //EntityManagerFactory 에서 EntityManager 생성 - 쓰레드간에 공유 X (사용하고 버려야함)
        EntityManager em = emf.createEntityManager();

        // EntityManager 의 트랜잭션 받아옴
        EntityTransaction tx = em.getTransaction();

        // 트랜잭션 시작 - 모든 데이터 변경은 이 안에서 처리해야함
        tx.begin();

        try {
            Team5 team = new Team5();
            team.setName("tttt");
            em.persist(team);

            Team5 team2 = new Team5();
            team2.setName("222222");
            em.persist(team2);

            Member5 member = new Member5();
            member.setUsername("aaa");
            member.setTeam(team);
            em.persist(member);

            Member5 member2 = new Member5();
            member2.setUsername("aaa");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

            Member5 findMember = em.find(Member5.class, member.getId());
            System.out.println(findMember.getClass());
            System.out.println(findMember.getTeam().getClass());

            System.out.println("===================");
            System.out.println(findMember.getTeam().getName());

            em.flush();
            em.clear();

            // lazy
            List<Member5> members = em.createQuery("select m from Member5 m" , Member5.class).getResultList();
            
            // 전부 가져오도록 fetch 조인
            List<Member5> members2 = em.createQuery("select m from Member5 m join fetch m.team" , Member5.class).getResultList();

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
}
