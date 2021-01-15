package jpatest06;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;

public class CollectionTest {
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
            // 등록
            Member6 member = new Member6();
            member.setUsername("member1");
            member.setAddress(new Address("city" , "street", "zipcode"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("oldcity" , "oldstreet", "oldzipcode"));
            member.getAddressHistory().add(new Address("oldoldcity" , "oldoldstreet", "oldoldzipcode"));

            em.persist(member);

            em.flush();
            em.clear();

            // 조회
            System.out.println("==========조회===========");
            Member6 findMember = em.find(Member6.class, member.getId());

            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println(address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println(favoriteFood);
            }

            // 수정
            System.out.println("==========수정===========");

            // 주소 값 통으로 변경
            Address a = findMember.getAddress();
            findMember.setAddress(new Address("newCity", a.getStreet(), a.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //등록된 값 지우고 새로 끼워 넣음 - equals 와 hashcode 를 제대로 구현해야 정상 작동
            findMember.getAddressHistory().remove(new Address("oldcity" , "oldstreet", "oldzipcode"));
            findMember.getAddressHistory().add(new Address("newCity" , "oldstreet", "oldzipcode"));

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
