package com.all4pet.config;
//import com.all4pet.controller.UserDetailsServiceImp;
//import com.all4pet.controller.SecurityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.all4pet.controller.CustomAuthenticationProvider;


@Configuration
@EnableWebSecurity
@ComponentScan({"com.all4pet.controller.CustomAuthenticationProvider", "\"com.all4pet.controller.UserDetailsServiceImp"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired 
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
//	@Autowired
//	AuthenticationSuccessHandler authenticationSuccessHandler;
//	AuthenticationSuccessHandlerImpl successHandler = new AuthenticationSuccessHandlerImpl();

	 @Bean
	    public JWTAuthenticationFilter jwtAuthenticationFilter() {
	        return new JWTAuthenticationFilter();
	    }
	@Bean
	public BCryptPasswordEncoder BCryptEncoder(){
		return new BCryptPasswordEncoder(10);
	}
	  @Autowired
	  UserDetailsService userDetailsService;

	
	  
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
              registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/**");
              registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/mybatis/**");
              registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/templates/**");

      }
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception        
    {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http
        .cors().disable();
    	http.authorizeRequests()
        		.antMatchers("/register","login","/","home").permitAll()
//        		.antMatchers("/admin","/admin/**").hasRole("ADMIN")
//        		.antMatchers("/user","/user/**").hasRole("CUSTOMER")
        		.and()
        		
    		  .authorizeRequests()
              .antMatchers("/login").permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
              .and()
              .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      // Thêm một lớp Filter kiểm tra jwt
      http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);//                .and()


        http.csrf().disable();
        
        http.authorizeRequests()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        .antMatchers("/resources/**","/static/**","/templates/**", "/css/**", "/js/**","/fonts/**").permitAll();
        
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/resources/**"); // #3
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider();
        provider.setUserDetailService(userDetailsService);
        auth.authenticationProvider(provider);
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
 
}