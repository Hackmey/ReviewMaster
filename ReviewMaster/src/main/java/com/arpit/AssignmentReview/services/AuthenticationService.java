package com.arpit.AssignmentReview.services;
import com.arpit.AssignmentReview.dtos.LoginUserDto;
import com.arpit.AssignmentReview.dtos.RegisterUserDto;
import com.arpit.AssignmentReview.entities.Authority;
import com.arpit.AssignmentReview.entities.User;
import com.arpit.AssignmentReview.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepo userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getEmail());
        user.setRoles(input.getRole());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        return userRepo.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepo.findByUsername(input.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));


    }


}
