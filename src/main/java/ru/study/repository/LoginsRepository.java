package ru.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.study.entity.Logins;
public interface LoginsRepository extends JpaRepository<Logins,Integer> {
}
