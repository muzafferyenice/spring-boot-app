package com.ynv.service;

import com.ynv.domain.Role;
import com.ynv.domain.User;
import com.ynv.domain.enums.RoleType;
import com.ynv.dto.request.RegisterRequest;
import com.ynv.exception.ConflictException;
import com.ynv.exception.ResourceNotFoundException;
import com.ynv.exception.message.ErrorMessage;
import com.ynv.repository.RoleRepository;
import com.ynv.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new
                    ConflictException(
                    String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));

        }

        if (userRepository.existsByEmail(registerRequest.getUsername())) {
            throw new
                    ConflictException(
                    String.format(ErrorMessage.USERNAME_ALREADY_EXIST, registerRequest.getUsername()));

        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        Role role = roleRepository.findByName(RoleType.CUSTOMER).
                orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE
                                , RoleType.CUSTOMER.name())));


        Set<Role> roles = new HashSet<>();
        roles.add(role);

        User user = new User();

        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setLocation(registerRequest.getLocation());
        user.setCreateDate(LocalDateTime.now());
        user.setBuiltIn(false);
        user.setRoles(roles);

        roleRepository.save(role);
        userRepository.save(user);


    }

    public User getOneUserByUsername(String username){
        User user =userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException(
                        String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE,username)         ));

        return user;


    }
}
