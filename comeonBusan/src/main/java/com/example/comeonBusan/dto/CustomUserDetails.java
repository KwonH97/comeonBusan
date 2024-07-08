package com.example.comeonBusan.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.comeonBusan.entity.UserEntity;

public class CustomUserDetails implements UserDetails {

	private final UserEntity userEntity;
	
    public CustomUserDetails(UserEntity userEntity) {
		
		this.userEntity = userEntity;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 반환 로직을 구현해야 합니다.
		Collection<GrantedAuthority> collection = new ArrayList<>();
		
		collection.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return userEntity.getRole();
			}
		});
		
        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }
    
    public String getRole() {
    	
    	return userEntity.getRole();
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
