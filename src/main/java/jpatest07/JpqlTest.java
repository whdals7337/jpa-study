package jpatest07;

import practice.domain.Address;
import practice.domain.Member;

import javax.persistence.*;
import java.util.List;

public class JpqlTest {
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
            Member7 member = new Member7();
            member.setUsername("aaa");
            member.setAge(10);
            em.persist(member);

            // 반환 타입이 명확 : TypedQuery , 불명 : Query
            TypedQuery<Member7> typedQuery1 = em.createQuery("select m from Member7 m", Member7.class);
            TypedQuery<String> typedQuery2 = em.createQuery("select m.username from Member7 m where m.username = :username", String.class)
                    .setParameter("username", "aaa");
            Query query1 = em.createQuery("select m.username, m.age from Member7 m");
            List<Member7> resultList1 = typedQuery1.getResultList();
            String singleResult1 = typedQuery2.getSingleResult(); // 한개가 아니면 에러
            List resultList2 = query1.getResultList();
            Object o = resultList2.get(0);
            Object[] result = (Object[]) o;
            System.out.println(result[0]); // username
            System.out.println(result[1]); // age
            */

            /*
            // 엔티티 프로젝션
            TypedQuery<Member7> typedQuery3 = em.createQuery("select m from Member7 m", Member7.class);
            TypedQuery<Team7> typedQuery4 = em.createQuery("select m.team from Member7 m", Team7.class);
            TypedQuery<Team7> typedQuery5 = em.createQuery("select t from Member7 m join m.team t", Team7.class);

            // 임베디드 타입 프로젝션
            TypedQuery<Address7> typedQuery6 = em.createQuery("select o.address from Order7 o", Address7.class);

            // 스칼라 타입 프로젝션
            Query query2 = em.createQuery("select m.username, m.age from Member7 m");
            List<Object[]> resultList3 = query2.getResultList();
            TypedQuery<MemberDTO7> typedQuery7 = em.createQuery("select new jpql.MemberDT07(m.username, m.age) from Member7 m", MemberDTO7.class);
            List<MemberDTO7> dtoList = typedQuery7.getResultList();
            */

            /*
            // 페이징
            for(int i = 0; i < 10; i++) {
                Member7 member = new Member7();
                member.setUsername("name" + i);
                member.setAge(10 + i);
                em.persist(member);
            }
            em.flush();
            em.clear();

            List<Member7> resultList = em.createQuery("select m from Member7 m order by m.age desc", Member7.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member7 result : resultList) {
                System.out.println(result);
            }
            */

            /*
            // case 문
            Member7 member = new Member7();
            member.setAge(20);
            member.setUsername("aaaa");
            em.persist(member);

            String query =
                        "select " +
                                "case when m.age <= 10 then '학생요금' " +
                                "     when m.age >= 60 then '경로요금' " +
                                "     else '일반요금' " +
                                "end " +
                        "from Member7 m";
            System.out.println("jpql 전에 영속석 컨텍스트 내용 flush 됨");
            List<String> result = em.createQuery(query, String.class)
                    .getResultList();
            for (String s : result) {
                System.out.println(s);
            }
            */

            /*
            // COALESCE
            Member7 member = new Member7();
            member.setAge(20);
            em.persist(member);

            String query = "select coalesce(m.username, '이름 없음') from Member7 m";
            List<String> result = em.createQuery(query, String.class).getResultList();

            for (String s : result) {
                System.out.println(s);
            }
            */

            // NULLIF
            Member7 member = new Member7();
            member.setAge(20);
            member.setUsername("관리자");
            em.persist(member);

            String query = "select nullif(m.username, '관리자') from Member7 m";
            List<String> result = em.createQuery(query, String.class).getResultList();

            for (String s : result) {
                System.out.println(s);
            }



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
