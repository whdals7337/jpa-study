package jpatest08;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team8 {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member8> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member8> getMembers() {
        return members;
    }

    public void setMembers(List<Member8> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Team8{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
