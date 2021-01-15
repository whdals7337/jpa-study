package jpatest04;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class SuperclassTest {
    public static void main(String[] args) {
        // persistence.xml 의 persistence-unit 을 받아서 EntityManagerFactory 생성 - 하나만 생성해서 전체 어플리케이션 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test04");

        //EntityManagerFactory 에서 EntityManager 생성 - 쓰레드간에 공유 X (사용하고 버려야함)
        EntityManager em = emf.createEntityManager();

        // EntityManager 의 트랜잭션 받아옴
        EntityTransaction tx = em.getTransaction();

        // 트랜잭션 시작 - 모든 데이터 변경은 이 안에서 처리해야함
        tx.begin();

        try {
            /*
            // 상속 관계 매핑
            Movie movie = new Movie();
            movie.setDirector("aaaaa");
            movie.setActor("bbb");
            movie.setName("제목");
            movie.setPrice(1234);
            em.persist(movie);

            em.flush();
            em.clear();

            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println(findMovie.getName());
            */

            /*
            // Mapped Superclass
            Movie movie = new Movie();
            movie.setDirector("1111");
            movie.setActor("2222");
            movie.setName("33333");
            movie.setPrice(44444);
            movie.setCreatedBy("관리자");
            movie.setCreateDate(LocalDateTime.now());
            em.persist(movie);
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
