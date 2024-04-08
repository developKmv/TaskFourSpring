package ru.study.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
public class Logins {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "access_date")
    private Timestamp accessDate;
    private String application;
    @ManyToOne
    @JoinColumn(name = "user_id")
    protected User user;

    public Logins(Integer id, Timestamp accessDate, String application, User user) {
        this.id = id;
        this.accessDate = accessDate;
        this.application = application;
        this.user = user;
    }

    public Logins(){};
}
