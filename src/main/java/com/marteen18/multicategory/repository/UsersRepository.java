package com.marteen18.multicategory.repository;

import com.marteen18.multicategory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("usersRepository")
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    int countByEmail(String email);
}
