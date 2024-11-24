package com.VEMS.vems.auth.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.VEMS.vems.auth.entity.user.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    HR(Set.of(HR_CREATE,HR_READ,HR_UPDATE,HR_DELETE)),
    OTHER(Set.of(OTHER_CREATE,OTHER_READ,OTHER_UPDATE,OTHER_DELETE)),
    SECURITY(Set.of(SECURITY_CREATE,SECURITY_READ,SECURITY_UPDATE,SECURITY_DELETE)),
    MANAGER(Set.of(MANAGER_CREATE,MANAGER_READ,MANAGER_UPDATE,MANAGER_DELETE));

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
