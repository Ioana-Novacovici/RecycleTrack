package com.GreenCycleSolutions.gcsbackend.entity;

import com.GreenCycleSolutions.gcsbackend.entity.enumtype.Gender;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private String cnp;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column
    @Nullable
    private String username;

    @Column
    @Nullable
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<TokenEntity> tokens;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role.equals(Role.ADMIN)) {
            return List.of(new SimpleGrantedAuthority(role.name()), new SimpleGrantedAuthority(Role.USER.name()), new SimpleGrantedAuthority(Role.AGENT.name()));
        } else if(role.equals(Role.AGENT)) {
            return List.of(new SimpleGrantedAuthority(role.name()), new SimpleGrantedAuthority(Role.USER.name()));
        }
        return List.of(new SimpleGrantedAuthority(role.name()));
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
