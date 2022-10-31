package com.bugTracker.Bug.Tracker.service;

import com.bugTracker.Bug.Tracker.entity.User;
import com.bugTracker.Bug.Tracker.repository.UserRepository;
import com.bugTracker.Bug.Tracker.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsImpl userDetails;

    public UserServiceImpl() {
        // TODO document why this constructor is empty
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(s);
        }
        userDetails.user = user;
        return userDetails;
    }

    @Override
    public void updateLastLoginDate(String userId) {
        User u = userRepository.findByUserId(userId);
        u.setLastLogin(Utils.getCurrentServerTime());
        userRepository.save(u);
    }
}
