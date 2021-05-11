package com.viniciusog.springsecurity.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    //Aqui é a nossa classe de configuração da segurança da aplicação Spring


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TIPOS DE AUTENTICAÇÃO

        //BASIC AUTH:
        //Requer apenas username e password
        //Requer autenticação todas as vezes que fizermos um request

        //Temos aqui a configuração de autenticação básica
        //Toda vez que fizermos um request, pediremos o usuário e senha novamente
       http
               .authorizeRequests()
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();
    }
}
