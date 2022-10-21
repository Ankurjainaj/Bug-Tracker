package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImpl() {
        // TODO document why this constructor is empty
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        return new UserDetailsImpl(user);
    }

    @Override
    public void updateLastLoginDate(String userId) {
        User u = userRepository.findByUserId(userId);
        u.setLastLogin(Utils.getCurrentServerTime());
        userRepository.save(u);
    }
}
