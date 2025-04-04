package com.example.financeOperations.repositories;

import com.example.financeOperations.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Query("SELECT u from User u")
    List<User> findAllByEmail();
}
