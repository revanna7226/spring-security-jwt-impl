package com.revs.secapp.user;

import com.revs.secapp.exception.BusinessException;
import com.revs.secapp.exception.ErrorCode;
import com.revs.secapp.user.request.ChangePasswordRequest;
import com.revs.secapp.user.request.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmailIgnoreCase(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("User not Found with username: " + username));
    }

    @Override
    public void updateProfileInfo(ProfileUpdateRequest request, String userId) {
        final User savedUser = this.getUser(userId);

        this.userMapper.mergeUserInfo(savedUser, request);
        this.userRepository.save(savedUser);
    }

    @Override
    public void changePassword(ChangePasswordRequest request, String userId) {
        if(!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BusinessException(ErrorCode.CHANGE_PASSWORD_MISMATCH);
        }

        final User savedUser = this.getUser(userId);

        if(!this.passwordEncoder.matches(request.getCurrentPassword(), savedUser.getPassword())) {
            throw new BusinessException(ErrorCode.INCORRECT_CURRENT_PASSWORD);
        }

        final String encodedPassword = this.passwordEncoder.encode(request.getNewPassword());
        savedUser.setPassword(encodedPassword);
        this.userRepository.save(savedUser);
    }

    @Override
    public void deactivateAccount(String userId) {
        final User user = this.getUser(userId);

        if(!user.isEnabled()) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }
        user.setEnabled(false);
        this.userRepository.save(user);
    }

    @Override
    public void reactivateAccount(String userId) {
        final User user = this.getUser(userId);

        if(user.isEnabled()) {
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_ACTIVATED);
        }
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void deleteAccount(String userId) {
        // TODO
        // this method need the rest of the entities
        // the logic is just to schedule a profile for deletion
        // and then a scheduled job will pick up the profiles and delete everything
    }

    private User getUser(String userId) {
        final User user = this.userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));
        return user;
    }
}