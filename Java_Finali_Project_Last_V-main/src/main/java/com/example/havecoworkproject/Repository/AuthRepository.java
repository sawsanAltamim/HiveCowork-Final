package com.example.havecoworkproject.Repository;


import com.example.havecoworkproject.Table.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {

    User findUserById(Integer id_user);
    User findUsersByUsername(String username);

}
