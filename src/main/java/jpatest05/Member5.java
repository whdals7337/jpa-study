package jpatest05;

import javax.persistence.*;

@Entity
public class Member5 {

    @Id @GeneratedValue
    private Long id;

    private String username;

    // member랑 team을 거의 항상 같이 쓰는경우는 EAGER
    // member랑 team을 같이 쓰는경우가 업서나 적은경우 LAZY
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team5 team;

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

    public Team5 getTeam() {
        return team;
    }

    public void setTeam(Team5 team) {
        this.team = team;
    }
}
