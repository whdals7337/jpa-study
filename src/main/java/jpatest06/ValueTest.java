package jpatest06;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ValueTest {
    public static void main(String[] args) {
    // persistence.xml 의 persistence-unit 을 받아서 EntityManagerFactory 생성 - 하나만 생성해서 전체 어플리케이션 공유
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("test06");

    //EntityManagerFactory 에서 EntityManager 생성 - 쓰레드간에 공유 X (사용하고 버려야함)
    EntityManager em = emf.createEntityManager();

    // EntityManager 의 트랜잭션 받아옴
    EntityTransaction tx = em.getTransaction();

    // 트랜잭션 시작 - 모든 데이터 변경은 이 안에서 처리해야함
        tx.begin();

        try {
            Address address = new Address("city", "street", "zipcode");

            Member6 member = new Member6();
            member.setUsername("aaa");
            member.setAddress(address);
            member.setPeriod(new Period());
            em.persist(member);

            Address newAddress = new Address("newCity", address.getStreet(), address.getZipcode());
            member.setAddress(newAddress);

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
