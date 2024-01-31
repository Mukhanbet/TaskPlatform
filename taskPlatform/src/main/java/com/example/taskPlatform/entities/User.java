package com.example.taskPlatform.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users_db")
public class User implements UserDetails {

    // http://localhost:8099/swagger-ui/index.html

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String role;
    private String password;

    // todo In the user table should show the name of tasks

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) //todo here is the problem when deleted the user the task shouldn't disappear
    private List<Task> tasks;






    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == null) {
            return Collections.singletonList(new SimpleGrantedAuthority("ROLE_DEFAULT"));
        }
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
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
        return true;
    }
}
