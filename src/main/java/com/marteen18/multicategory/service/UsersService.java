package com.marteen18.multicategory.service;

import com.marteen18.multicategory.common.Auth;
import com.marteen18.multicategory.model.User;
import com.marteen18.multicategory.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("usersService")
public class UsersService {
    private UsersRepository usersRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UsersService(@Qualifier("usersRepository") UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public void saveUser(User user, boolean updatePassword) {
        if (updatePassword) {
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }

        usersRepository.save(user);

        if (Auth.currentUserDetails().getId() == user.getId()) {
            Auth.updateCurrentAuthentication(user);
        }
    }
}
