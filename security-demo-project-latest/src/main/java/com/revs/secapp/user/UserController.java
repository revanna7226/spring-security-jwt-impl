package com.revs.secapp.user;

import com.revs.secapp.user.request.ChangePasswordRequest;
import com.revs.secapp.user.request.ProfileUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API")
public class UserController {

    private final IUserService userService;

    @PatchMapping("/me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateProfileInfo(
        @RequestBody
        @Valid
        final ProfileUpdateRequest request,
        final Authentication principal
        ) {
        this.userService.updateProfileInfo(request, getUserId(principal));
    }

    @PostMapping("/me/password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void changePassword(
        @RequestBody
        @Valid
        final ChangePasswordRequest request,
        final Authentication principal
        ) {
        this.userService.changePassword(request, getUserId(principal));
    }

    @PatchMapping("/me/deactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deActivate(final Authentication principal) {
        this.userService.deactivateAccount(getUserId(principal));
    }

    @PatchMapping("/me/reactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void reActivate(final Authentication principal) {
        this.userService.reactivateAccount(getUserId(principal));
    }

    @DeleteMapping("/me/deleteAccount")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteAccount(
        final Authentication principal
        ) {
        this.userService.deleteAccount(getUserId(principal));
    }

    private String getUserId(Authentication principal) {
        return ((User)principal.getPrincipal()).getId();
    }

}