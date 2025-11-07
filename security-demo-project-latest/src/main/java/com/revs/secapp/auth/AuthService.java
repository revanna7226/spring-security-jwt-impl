package com.revs.secapp.auth;

import com.revs.secapp.auth.request.AuthRequest;
import com.revs.secapp.auth.request.RefreshRequest;
import com.revs.secapp.auth.request.RegistrationRequest;
import com.revs.secapp.auth.response.AuthResponse;
import com.revs.secapp.exception.BusinessException;
import com.revs.secapp.exception.ErrorCode;
import com.revs.secapp.role.Role;
import com.revs.secapp.role.RoleRepository;
import com.revs.secapp.security.JWTService;
import com.revs.secapp.user.User;
import com.revs.secapp.user.UserMapper;
import com.revs.secapp.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {

    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public AuthResponse login(AuthRequest request) {
        final Authentication auth = this.authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            ));

        final User user = (User) auth.getPrincipal();
        final String token = this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";

        return AuthResponse.builder()
            .accessToken(token)
            .refreshToken(refreshToken)
            .tokenType(tokenType)
            .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkPassword(request.getPassword(), request.getConfirmPassword());

        final Role userRole = this.roleRepository.findByName("ROLE_USER")
            .orElseThrow(() -> new EntityNotFoundException("Role user does not exists"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user = this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving user {}", user);
        this.userRepository.save(user);

        // Creating User Roles, In case of not cascading rules set
        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);
        this.roleRepository.save(userRole);
    }

    @Override
    public AuthResponse refreshToken(RefreshRequest request) {
        final String newAccessToken = this.jwtService.refreshAccessToken(request.getRefreshToken());
        return AuthResponse.builder()
            .accessToken(newAccessToken)
            .refreshToken(request.getRefreshToken())
            .tokenType("Bearer")
            .build();
    }

    private void checkPassword(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCHING);
        }
    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = this.userRepository.existsByPhoneNumber(phoneNumber);
        if(phoneNumberExists) {
            throw new BusinessException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }

    private void checkUserEmail(String email) {
        final boolean emailExists = this.userRepository.existsByEmailIgnoreCase(email);
        if(emailExists) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }
}
