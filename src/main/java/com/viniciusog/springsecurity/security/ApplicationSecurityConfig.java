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

               //Autorizando qualquer request, desde que seja autenticado
               .anyRequest()
               .authenticated()
               .and()
               .httpBasic();
    }
}
