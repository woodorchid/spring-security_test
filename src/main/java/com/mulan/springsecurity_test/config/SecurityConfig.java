package com.mulan.springsecurity_test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author 韩志雄
 * @date 2021/4/6 14:55
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	//注入数据源
	@Autowired
	private DataSource dataSource;

	//添加持久化令牌仓库
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().accessDeniedPage("/unauth.html");//访问拒绝跳转的页面
		http.formLogin()//自定义登录
				.loginPage("/login.html")//登录页面
				.loginProcessingUrl("/user/login")//登录访问路径Controller
		.defaultSuccessUrl("/user/login").permitAll()//登陆成功之后跳转路径
		.and().authorizeRequests()
				//设置哪些页面可以直接访问不需要认证
				.antMatchers("/","/index","/test/hello","/user/login").permitAll()
				.antMatchers("/user/login").hasAnyAuthority("manager,admin")
				.anyRequest().authenticated()
				.and().rememberMe().tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(60)//设置有效时长单位秒
				.userDetailsService(userDetailsService)
				.and().csrf().disable();//关闭csrf防护
	}
}
