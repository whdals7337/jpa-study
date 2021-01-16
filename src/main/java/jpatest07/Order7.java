package jpatest07;

import javax.persistence.*;

@Entity
public class Order7 {

    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;
    private int orderAmount;

    @Enumerated
    private Address7 address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product7 product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Address7 getAddress() {
        return address;
    }

    public void setAddress(Address7 address) {
        this.address = address;
    }
}
