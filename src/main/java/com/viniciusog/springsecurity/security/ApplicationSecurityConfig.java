package com.viniciusog.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    //Aqui é a nossa classe de configuração da segurança da aplicação Spring

    private final PasswordEncoder passwordEncoder;

    //Irá identificar automaticamente o nosso BCryptEncoder definido na classe PasswordConfig
    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TIPOS DE AUTENTICAÇÃO

        //BASIC AUTH: (autenticação básica)
        //Requer apenas username e password
        //Requer autenticação todas as vezes que fizermos um request
        //Quando eu estiver na página index e reiniciar a API, quando eu atualizar a página, o spring
        //security pede o login novamente

       http
               .authorizeRequests()
               //Estamos autorizando automaticamente os requests que vierem de algum dos locais abaixo
               //Ou seja, não precisará de login para acessar as páginas abaixo
               .antMatchers("/", "index", "/css/*", "/js/*")
               .permitAll()
               //Apenas os usuários que tiverem perfil de estudante, poderão acessar o caminho '/api/**'
               .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())

               //Autorizando qualquer request, desde que seja autenticado
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();
    }

    //É o local onde pegamos os nossos usuários do banco de dados
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        //Estamos criando um usuário do spring security
        UserDetails annaSmithUser =  User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.STUDENT.name()) //Pega os valores referentes ao perfil 'STUDENT'
                .build(); //ROLE_STUDENT


        UserDetails lindaUser = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password123"))
                //Podemos guardar o perfil do usuário, STUDENT ou ADMIN, no banco de dados, na FORMA DE STRING
                //e depois passar para ENUM com ENUM.valueOf("STUDENT ou ADMIN")
                .roles(ApplicationUserRole.ADMIN.name()) //Pega os valores referentes ao perfil 'ADMIN' //ROLE_ADMIN
                .build();

        //Permitirar logar com esse usuário na página de login
        return new InMemoryUserDetailsManager (
                annaSmithUser,
                lindaUser
        );
    }
}
