package jpatest05;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class CascadeOrphanTest {
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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);
            em.persist(parent);
            
            // cascade 적용시 parent 만 persist 해도 child 모두 persist 됨
            // em.persist(child1);
            // em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);
            // 부모 삭제시 자식까지 삭제
            // em.remove(findParent);

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
