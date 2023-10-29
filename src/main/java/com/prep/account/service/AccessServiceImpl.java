package com.prep.account.service;

import com.prep.account.entity.Account;
import com.prep.account.entity.Role;
import com.prep.account.exception.AccessDeniedException;
import com.prep.account.exception.AccountNotFoundException;
import com.prep.account.exception.PasswordMismatchException;
import com.prep.account.iservice.AccessService;
import com.prep.account.model.LoginRequestDTO;
import com.prep.account.model.LoginResponseDTO;
import com.prep.account.repository.AccountRepository;
import com.prep.account.utilities.network.EmailSender;
import com.prep.account.utilities.token.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class AccessServiceImpl implements AccessService, UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    @Override
    public Account findByIdentity(String identity) {
        Optional<Account> accountOp = accountRepository.findByEmail(identity);
        if (accountOp.isEmpty()) accountOp = accountRepository.findByUserName(identity);
        try {
            accountOp = accountRepository.findByUserId(UUID.fromString(identity));
        }
        catch (Exception ignored){}

        if(accountOp.isEmpty()) throw new AccountNotFoundException(identity);
        return accountOp.get();
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginDTO) throws AccountNotFoundException, PasswordMismatchException {
        Account account = findByIdentity(loginDTO.getIdentity());
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUsername(account.getUserName());
        response.setUserId(account.getUserId().toString());
        List<String> roles = account.getRoles().stream().map(Role::getRoleName).toList();
        response.setRoles(roles);
        String token = JWTUtils.generateToken(account.getUserId().toString(), roles);
        response.setBearerToken(token);

        return response;
    }

    @Override
    public void generateOTP(String identity) throws AccountNotFoundException {
        Account account = findByIdentity(identity);

        Random random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        account.setOtp(otp);
        account.setOtpGenerationTime(LocalDateTime.now());
        accountRepository.save(account);

        emailSender.send(account.getEmail(), "Health app OTP Token", "Your OTP is " + otp + ". Use it to gain access to your account.");
    }

    @Override
    public String generateInternalToken() throws AccessDeniedException {
        return JWTUtils.generateInternalToken();
    }

    @Override
    public Boolean checkEmailAvailability(String email) {
        return accountRepository.findByEmail(email).isEmpty();
    }

    @Override
    public Boolean checkUsernameAvailability(String username) {
        return accountRepository.findByUserName(username).isEmpty();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByIdentity(username);
        List<GrantedAuthority> roles = account.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
        return new User(account.getUserName(),
                account.getPassword(),
                account.isAccountEnabled() && ! account.isAccountDeactivated(),
                ! account.isAccountBanned() && ! account.isAccountSuspended(),
                true,
                ! account.isAccountLocked(),
                roles);
    }
}
