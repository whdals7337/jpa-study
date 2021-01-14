package jpatest01;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member1 {

    @Id
    private Long id;
    private String name;

    public Member1(){}

    public Member1(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
