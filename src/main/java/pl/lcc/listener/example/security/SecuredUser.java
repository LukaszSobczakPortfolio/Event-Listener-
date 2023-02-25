/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.lcc.listener.example.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Nauczyciel
 */
@Slf4j
public class SecuredUser implements UserDetails, Cloneable {

    private String userName;
    private String password;
    private Set<GrantedAuthority> auths;
    private boolean nonLocked;

    public SecuredUser(String userName, String password) {
        log.info("SU -> create " + userName);
        this.userName = userName;
        this.password = password;
        nonLocked = true;
        auths = new HashSet<>();
    }

    public SecuredUser setAuthorities(List<GrantedAuthority> auths) {
        this.auths.addAll(auths);
        return this;
    }

    public SecuredUser lock() {
        nonLocked = false;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new LinkedList<>(auths);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public SecuredUser clone() {
        try {
           var clone = (SecuredUser) super.clone();
           clone.auths = new HashSet<>(clone.getAuthorities());
           return clone; }
        catch (CloneNotSupportedException ex){
            log.info("clone Faile for SecuredUser " + userName);
            throw new RuntimeException("Should definitely not happen. clone Faile for SecuredUser " + userName);
        }
    }
}
