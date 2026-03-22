package com.ti9.ti9_backend.security.controller;

import com.ti9.ti9_backend.exceptions.OperacaoNaoRealizadaException;
import com.ti9.ti9_backend.exceptions.ValorNaoPermitidoException;
import com.ti9.ti9_backend.security.User;
import com.ti9.ti9_backend.security.records.AuthenticationDto;
import com.ti9.ti9_backend.security.records.LoginResponseDto;
import com.ti9.ti9_backend.security.records.RegisterDto;
import com.ti9.ti9_backend.security.records.UserRegisteredDto;
import com.ti9.ti9_backend.security.repositories.UserRepository;
import com.ti9.ti9_backend.security.services.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(authenticationDto.login(), authenticationDto.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }
    @PostMapping("/refresh")
    public ResponseEntity refresh(@RequestBody @Valid AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(authenticationDto.login(), authenticationDto.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto registerDto){
        if(userRepository.findByLogin(registerDto.login()) != null){
            throw new ValorNaoPermitidoException("Login já registrado entre os usuários");
        }

        try{
            User user = new User(registerDto.login(),
                    new BCryptPasswordEncoder().encode(registerDto.password())
                    ,registerDto.role());
            User savedUser = userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new UserRegisteredDto(savedUser.getLogin(),savedUser.getRole()));
        } catch (Exception e){
            throw new OperacaoNaoRealizadaException("Falha ao registrar o usuário\n" + e.getMessage());
        }

    }
}
