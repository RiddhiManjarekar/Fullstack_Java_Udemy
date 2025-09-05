package org.studyeasy.SprinRestDemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.studyeasy.SprinRestDemo.model.Account;
import org.studyeasy.SprinRestDemo.payload.auth.AccountDTO;
import org.studyeasy.SprinRestDemo.payload.auth.AccountViewDTO;
import org.studyeasy.SprinRestDemo.payload.auth.AuthoritiesDTO;
import org.studyeasy.SprinRestDemo.payload.auth.PasswordDTO;
import org.studyeasy.SprinRestDemo.payload.auth.ProfileDTO;
import org.studyeasy.SprinRestDemo.payload.auth.TokenDTO;
import org.studyeasy.SprinRestDemo.payload.auth.UserLoginDTO;
import org.studyeasy.SprinRestDemo.service.AccountService;
import org.studyeasy.SprinRestDemo.service.TokenService;
import org.studyeasy.SprinRestDemo.util.constants.AccountError;
import org.studyeasy.SprinRestDemo.util.constants.AccountSuccess;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Comtroller", description = "Controller for Account Managemnent")
@Slf4j
public class AuthController {

    // private final AuthenticationManager authenticationManager;
    // private final TokenService tokenService;
    // public AuthController(TokenService tokenService, AuthenticationManager
    // authenticationManager){
    // this.tokenService=tokenService;
    // this.authenticationManager=authenticationManager;
    // }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDTO> token(@Valid @RequestBody UserLoginDTO userLogin) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword()));
            return ResponseEntity.ok(new TokenDTO(tokenService.generateToken(authentication)));
        } catch (BadCredentialsException ex) {
            log.error("Invalid credentials for user {}", userLogin.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenDTO(null));
        } catch (UsernameNotFoundException ex) {
            log.error("User not found: {}", userLogin.getEmail());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new TokenDTO(null));
        } catch (Exception e) {
            log.error("Token generation error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TokenDTO(null));
        }

    }

    @PostMapping(value = "/users/add", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "400", description = "Please enter a valid email and password length between 6 to 20 characters")
    @ApiResponse(responseCode = "200", description = "Account added")
    @Operation(summary = "Add a new User")
    public ResponseEntity<String> addUser(@Valid @RequestBody AccountDTO accountDTO) {
        try {
            Account account = new Account();
            account.setEmail(accountDTO.getEmail());
            account.setPassword(accountDTO.getPassword());
            // account.setRole("ROLE_USER");
            accountService.save(account);
            return ResponseEntity.ok(AccountSuccess.ACCOUNT_ADDED.toString());
        } catch (Exception e) {
            log.debug(AccountError.ADD_ACCOUNT_ERROR.toString() + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @GetMapping(value = "/users", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "List of Users")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "List users API")
    @SecurityRequirement(name = "studyeasy-demo-api")
    public List<AccountViewDTO> Users() {
        List<AccountViewDTO> accounts = new ArrayList<>();
        for (Account account : accountService.findAll()) {
            accounts.add(new AccountViewDTO(account.getId(), account.getEmail(), account.getAuthorities()));
        }
        return accounts;
    }

    @PutMapping(value = "/users/{user_id}/update-authorities/", consumes = "application/json", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Update authorities")
    @ApiResponse(responseCode = "400", description = "Invalid user")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "Update authorities")
    @SecurityRequirement(name = "studyeasy-demo-api")
    public ResponseEntity<AccountViewDTO> update_auth(@Valid @RequestBody AuthoritiesDTO authoritiesDTO,@PathVariable long user_id) {
        Optional<Account> optionalAccount = accountService.findById(user_id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setAuthorities(authoritiesDTO.getAuthorities());
            accountService.save(account);
            AccountViewDTO accountViewDTO = new AccountViewDTO(account.getId(), account.getEmail(),
                    account.getAuthorities());
            return ResponseEntity.ok(accountViewDTO);
        }
        return new ResponseEntity<AccountViewDTO>(new AccountViewDTO(),HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/profile", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "View profile")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "View profile")
    @SecurityRequirement(name = "studyeasy-demo-api")
    public ProfileDTO profile(Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        
            Account account = optionalAccount.get();
            ProfileDTO profileDTO = new ProfileDTO(account.getId(), account.getEmail(), account.getAuthorities());
            return profileDTO;
        
    }

    @PutMapping(value = "/profile/update-password", consumes = "application/json", produces = "application/json")
    @ApiResponse(responseCode = "200", description = "Update profile")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "Update profile")
    @SecurityRequirement(name = "studyeasy-demo-api")
    public AccountViewDTO profile(@Valid @RequestBody PasswordDTO passwordDTO, Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
       
            Account account = optionalAccount.get();
            account.setPassword(passwordDTO.getPassword());
            accountService.save(account);
            AccountViewDTO accountViewDTO = new AccountViewDTO(account.getId(), account.getEmail(),
                    account.getAuthorities());
            return accountViewDTO;
       
    }

    @DeleteMapping(value = "/profile/delete")
    @ApiResponse(responseCode = "200", description = "Delete profile")
    @ApiResponse(responseCode = "401", description = "Token missing")
    @ApiResponse(responseCode = "403", description = "Token Error")
    @Operation(summary = "Delete profile")
    @SecurityRequirement(name = "studyeasy-demo-api")
    public ResponseEntity<String> delete_profile( Authentication authentication) {
        String email = authentication.getName();
        Optional<Account> optionalAccount = accountService.findByEmail(email);
        if (optionalAccount.isPresent()) {
            accountService.deleteById(optionalAccount.get().getId());
            return ResponseEntity.ok("User delete");
        }
        return new ResponseEntity<String>("Bad Request",HttpStatus.BAD_REQUEST);
    }
}

