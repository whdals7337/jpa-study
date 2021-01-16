package jpatest07;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlFunctionTest {
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
            Member7 member1 = new Member7();
            member1.setUsername("abcd");
            member1.setAge(15);
            em.persist(member1);

            Member7 member2 = new Member7();
            member2.setUsername("efg");
            member2.setAge(20);
            em.persist(member2);
            em.flush();
            em.clear();

            // CONCAT
            String query1 = "select 'a' || 'b' from Member7 m";
            List<String> result1 =  em.createQuery(query1, String.class).getResultList();
            for (String s : result1) {
                System.out.println("CONCAT: " + s);
            }

            // SUBSTRING
            String query2 = "select substring(m.username, 2, 3) from Member7 m";
            List<String> result2 =  em.createQuery(query2, String.class).getResultList();
            for (String s : result2) {
                System.out.println("SUBSTRING: " + s);
            }

            // TRIM
            String query3 = "select trim('abc      ') from Member7 m";
            List<String> result3 =  em.createQuery(query3, String.class).getResultList();
            for (String s : result3) {
                System.out.println("TRIM: " + s);
            }

            // LOWER
            String query4 = "select lower('ABC') from Member7 m";
            List<String> result4 =  em.createQuery(query4, String.class).getResultList();
            for (String s : result4) {
                System.out.println("LOWER: " + s);
            }

            // UPPER
            String query5 = "select upper('abc') from Member7 m";
            List<String> result5 =  em.createQuery(query5, String.class).getResultList();
            for (String s : result5) {
                System.out.println("UPPER: " + s);
            }

            // LOCATE
            String query6 = "select locate('ab', 'ddabcd') from Member7 m";
            List<Integer> result6 =  em.createQuery(query6, Integer.class).getResultList();
            for (Integer integer : result6) {
                System.out.println("LOCATE: " + integer);
            }

            // ABS
            String query7 = "select abs(-1) from Member7 m";
            List<Integer> result7 =  em.createQuery(query7, Integer.class).getResultList();
            for (Integer integer : result7) {
                System.out.println("ABS: " + integer);
            }
            
            //LENGTH, SORT, SIZE 등 더 있음


            //사용자 함수 (CUSTOM Dialect 을 만들고 persistence.xml 에 설정 후 사용)
            String query8 = "select group_concat(m.username) from Member7 m";
            List<String> result8 =  em.createQuery(query8, String.class).getResultList();
            for (String s : result8) {
                System.out.println("사용자 함수: " + s);
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
