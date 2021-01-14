package jpatest03;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaRelationMappingTest {
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
            Team team = new Team();
            team.setName("TemaA");
            em.persist(team);

            Member3 member = new Member3();
            member.setUsername("이름");
            member.changeTeam(team);
            em.persist(member);

            System.out.println("===========");
            for (Member3 member3 : member.getTeam().getMembers()) {
                System.out.println(member3.getUsername());
            }
            System.out.println("===========");

            Member3 findMember = em.find(Member3.class, member.getId());

            Team findTeam = findMember.getTeam();

            List<Member3> members = findTeam.getMembers();
            System.out.println("===========");
            for(Member3 obj : members) {
                System.out.println(obj.getUsername());
            }
            System.out.println("===========");

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
