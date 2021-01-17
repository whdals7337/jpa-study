package jpatest08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class JpqlRouteTest {
    public static void main(String[] args) {
        // persistence.xml 의 persistence-unit 을 받아서 EntityManagerFactory 생성 - 하나만 생성해서 전체 어플리케이션 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test08");

        //EntityManagerFactory 에서 EntityManager 생성 - 쓰레드간에 공유 X (사용하고 버려야함)
        EntityManager em = emf.createEntityManager();

        // EntityManager 의 트랜잭션 받아옴
        EntityTransaction tx = em.getTransaction();

        // 트랜잭션 시작 - 모든 데이터 변경은 이 안에서 처리해야함
        tx.begin();

        try {
            Team8 team = new Team8();
            team.setName("teamName");
            em.persist(team);

            Member8 member = new Member8();
            member.setUsername("name");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            String query = "select t.members from Team8 t";
            List<Collection> resultList = em.createQuery(query, Collection.class).getResultList();
            System.out.println(resultList);

            String query2 = "select m.username from Team8 t join t.members m";
            List<String> resultList2 = em.createQuery(query2, String.class).getResultList();
            System.out.println(resultList2);

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
