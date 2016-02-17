package com.taxholic.core.authority;


import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taxholic.core.web.dao.CommonDao;

@Service
public class AuthService  implements UserDetailsService {
	
	@Autowired
	private CommonDao commonDao;

    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException, DataAccessException {
    	
    	
		//로그인 정보
		Map<String, String> user = new HashMap<String, String>();
		user.put("userId", userId);
		user.put("useYn", "Y");
		AuthDto dto = (AuthDto)this.commonDao.getObject("auth.getUserInfo",user);
		
		//회원 권한
		if(dto != null){
			
			List<String> roleList = new ArrayList<String>();
			@SuppressWarnings("unchecked")
			List<AuthDto> authList = (List<AuthDto>)this.commonDao.getList("auth.getUserAuthList",userId);
			
			for(AuthDto lst :  authList ){
				roleList.add( lst.getRole());
			}
			
			dto.setRoleList(roleList);
		}

		return new AuthInfo(dto);
    }
    
}