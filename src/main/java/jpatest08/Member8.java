package jpatest08;

import javax.persistence.*;

@Entity
@NamedQuery(
        name="Member8.findByUsername",
        query = "select m from Member8 m where m.username = :username"
)
public class Member8 {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team8 team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team8 getTeam() {
        return team;
    }

    public void setTeam(Team8 team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member8{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
