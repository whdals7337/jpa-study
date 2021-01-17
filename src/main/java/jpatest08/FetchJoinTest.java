package jpatest08;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLOutput;
import java.util.Collection;
import java.util.List;

public class FetchJoinTest {
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
            Team8 teamA = new Team8();
            teamA.setName("팀A");
            em.persist(teamA);

            Team8 teamB = new Team8();
            teamB.setName("팀B");
            em.persist(teamB);

            Team8 teamC = new Team8();
            teamC.setName("팀C");
            em.persist(teamC);

            Member8 member1 = new Member8();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member8 member2 = new Member8();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member8 member3 = new Member8();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            Member8 member4 = new Member8();
            member4.setUsername("회원4");
            em.persist(member4);

            em.flush();
            em.clear();

            /*
            // 기본 조회
            String query1 = "select m from Member8 m";
            List<Member8> resultList1 = em.createQuery(query1, Member8.class).getResultList();
            // SQL 이 3번 날가람 (N+1 발생)
            // 회원 1 팀A -SQL 에서 가져오고 영속성 컨택스트(1차캐시)에 셋팅
            // 회원 2 팀A - 1차캐쉬
            // 회원 3 팀B - SQL

            for (Member8 mem : resultList1) {
                System.out.println(mem + " : " + mem.getTeam());
            }
            */

            /*
            // fetch 조인
            String query2 = "select m from Member8 m join fetch m.team";
            List<Member8> resultList2 = em.createQuery(query2, Member8.class).getResultList();
            for (Member8 mem : resultList2) {
                System.out.println(mem + " : " + mem.getTeam());
            }
            */

            /*
            // 컬렉션 페치 조인 - 데이터 뻥튀기 됨
            String query3 = "select t from Team8 t join fetch t.members";
            List<Team8> resultList3 = em.createQuery(query3, Team8.class).getResultList();
            for (Team8 team : resultList3) {
                System.out.println(team);
                for (Member8 member : team.getMembers()) {
                    System.out.println(member);
                }
                System.out.println();
            }
            */

            /*
            // 컬렉션 페치 조인 with distinct
            String query4 = "select distinct t from Team8 t join fetch t.members";
            List<Team8> resultList4 = em.createQuery(query3, Team8.class).getResultList();
            for (Team8 team : resultList4) {
                System.out.println(team);
                for (Member8 member : team.getMembers()) {
                    System.out.println(member);
                }
                System.out.println();
            }
            */

            /*
            //  엔티티 직접사용
            String query5 = "select m from Member8 m where m = :member";
            List<Member8> member = em.createQuery(query5, Member8.class).setParameter("member", member1).getResultList();


            String query6 = "select m from Member8 m where m.team = :team";
            List<Team8> team = em.createQuery(query6, Team8.class).setParameter("team", teamB).getResultList();
            */

            /*
            // namedQuery
            List<Member8> resultList = em.createNamedQuery("Member8.findByUsername", Member8.class)
                    .setParameter("username", "회원1")
                    .getResultList();

            for (Member8 mem : resultList) {
                System.out.println(mem);
            }
            */

            // 벌크 연산
            int resultCount = em.createQuery("update Member8 m set m.age = 20").executeUpdate();
            System.out.println(resultCount);
            em.clear();
            
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
