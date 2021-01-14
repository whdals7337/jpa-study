package jpatest02;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@SequenceGenerator(name="MEMBER2_SEQ_GENERATOR", sequenceName = "MEMBER_SEQ", initialValue = 1, allocationSize = 50)
public class Member2 {

    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO) // AUTO : DB에 위임
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY : MySQL auto_increment
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER2_SEQ_GENERATOR")  // SEQUENCE : Oracle 시퀀스 생성
    private Long id;

    //@Column(name = "name", columnDefinition = "varchar(100) default 'EMPTY'", length = 300)
    @Column(name = "name")
    private String username;

    //@Column(name = "age", updatable = false, insertable = false, nullable = false, unique = true)
    private Integer age;

    // @Enumerated(EnumType.ORDINAL) - enum 의 순서를 저장 : enum 순서가 바뀌면 문제 생김 사용 x
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    // LocalDate, LocalDateTime 의 경우 자동 매핑됨
    private LocalDate testLocalDate;
    private LocalDateTime testLocalDateTime;

    @Lob
    private String description;

    @Transient
    private int temp;

    public Member2() {}

    public Member2(Long id, String username, Integer age, RoleType roleType, Date createDate, Date lastModifiedDate, String description, int temp) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.roleType = roleType;
        this.createDate = createDate;
        this.lastModifiedDate = lastModifiedDate;
        this.description = description;
        this.temp = temp;
    }

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
