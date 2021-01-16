package jpatest07;

import jpatest06.Address;
import jpatest06.Member6;
import jpatest06.Period;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BasicTest {
    public static void main(String[] args) {
        // persistence.xml 의 persistence-unit 을 받아서 EntityManagerFactory 생성 - 하나만 생성해서 전체 어플리케이션 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test07");

        //EntityManagerFactory 에서 EntityManager 생성 - 쓰레드간에 공유 X (사용하고 버려야함)
        EntityManager em = emf.createEntityManager();

        // EntityManager 의 트랜잭션 받아옴
        EntityTransaction tx = em.getTransaction();

        // 트랜잭션 시작 - 모든 데이터 변경은 이 안에서 처리해야함
        tx.begin();

        try {
            /*
            // JPQL
            List<Member7> result = em.createQuery("select m from Member7 m where m.username like '%kim%'"
                    , Member7.class).getResultList();
            for (Member7 member : result) {
                System.out.println(member.getUsername());
            }*/
            
            /*
            // Criteria
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member7> query = cb.createQuery(Member7.class);

            Root<Member7> m = query.from(Member7.class);

            CriteriaQuery<Member7> cq = query.select(m);

            String username ="불일치";
            if(username != null){
                cq = cq.where(cb.equal(m.get("username"), "kim"));    
            }
            List<Member7> resultList = em.createQuery(cq).getResultList();
            */

            /*
            // Native query
            em.createNativeQuery("select MEMBER_ID, USERNAME from MEMBER7 WHERE USERNAME = 'kim'").getResultList();
            */

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
