package com.mulan.springsecurity_test.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mulan.springsecurity_test.daos.AccountMapper;
import com.mulan.springsecurity_test.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 韩志雄
 * @date 2021/4/6 14:58
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountMapper accountMapper;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

		QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("userName",s);
		Account account = accountMapper.selectOne(queryWrapper);
		if(account == null){
			throw new UsernameNotFoundException("用户不存在！");
		}

		List<GrantedAuthority> list = AuthorityUtils.commaSeparatedStringToAuthorityList("manager");
		return new User(s,new BCryptPasswordEncoder().encode(account.getPassword()),list);
	}
}
