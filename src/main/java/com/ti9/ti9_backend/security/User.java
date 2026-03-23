package com.ti9.ti9_backend.security;

import com.ti9.ti9_backend.security.enums.EnumUserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name="USUARIOS")
@Table(name="tab_usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "login",nullable = false,unique = true)
    private String login;
    @Column(name = "password",nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable = false)
    private EnumUserRole role;
    @Column(name="ativo")
    private Boolean enabled;

    public User(String login, String password, EnumUserRole role, Boolean enabled) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == EnumUserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                           new SimpleGrantedAuthority("ROLE_VIEWER"),
                           new SimpleGrantedAuthority("ROLE_AUDITOR"));
        } else if (this.role == EnumUserRole.VIEWER){
            return List.of(new SimpleGrantedAuthority("ROLE_VIEWER"));
        } else{
            return List.of(new SimpleGrantedAuthority("ROLE_AUDITOR"));
        }
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
