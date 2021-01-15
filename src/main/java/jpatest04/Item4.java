package jpatest04;

import practice.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 조인 전략
// @Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 단일 테이블 전략
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // 구현 클래스 마다 테이블 전략
@DiscriminatorColumn // DTYPE 설정
public abstract class Item4 extends BaseEntity4 {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
