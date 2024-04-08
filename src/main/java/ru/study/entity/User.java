package ru.study.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@ToString
@Entity
@Table(name = "APPUSER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String fio;

    public User(){};
    public User(Integer id, String username, String fio) {
        this.id = id;
        this.username = username;
        this.fio = fio;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFio() {
        return fio;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
