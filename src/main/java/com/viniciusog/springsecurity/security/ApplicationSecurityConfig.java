package com.viniciusog.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.viniciusog.springsecurity.security.ApplicationUserPermission;

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


        //ESCREVI UM COMENTÁRIO SOBRE BANCO DE DADOS E ENUM, em baixo
       http
               //TODO: irá explicar nos próximos minutos
               //- O spring está, por padrão, bloqueando os métodos DELETE, POST e PUT
               //para usuários que não tem permissão explícita configurada para determinado caminho da API

               //- Temos que desabilitar o csrf para que os usuários consigam realizar métodos DELETE, POST e PUT
               //em caminhos que não tem definido quem pode acessar
               .csrf().disable()
               .authorizeRequests()
               //Estamos autorizando automaticamente os requests que vierem de algum dos locais abaixo
               //Ou seja, não precisará de login para acessar as páginas abaixo
               .antMatchers("/", "index", "/css/*", "/js/*")
               .permitAll()
               //Apenas os usuários que tiverem perfil de STUDENT, poderão acessar o caminho '/api/**'
               .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())

               //Apenas habilita o método de DELETE para o caminho a seguir, quem tiver permissão de COURSE_WRITE
               //Verifica se a role do nosso usuário, que estará acessando, possui a PERMISSION COURSE-WRITE
               .antMatchers(HttpMethod.DELETE, "/management/api/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
               //Apenas habilita o método de POST para o caminho a seguir, quem tiver permissão de COURSE_WRITE (course:write)
               .antMatchers(HttpMethod.POST, "/management/api/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
               //Apenas habilita o método de PUT para o caminho a seguir, quem tiver permissão String de COURSE_WRITE (course:write)
               .antMatchers(HttpMethod.PUT, "/management/api/v1/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.getPermission())
               //Terão acesso ao método GET no caminho a seguir, quem tiver qualquer uma das ROLES, ADMIN ou ADMINTRAINEE
               .antMatchers(HttpMethod.GET, "/management/api/v1/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name())


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
                //.roles(ApplicationUserRole.STUDENT.name()) //Pega os valores referentes ao perfil 'STUDENT'
                .authorities(ApplicationUserRole.STUDENT.getGrantedAuthorities())
                .build(); //ROLE_STUDENT


        UserDetails lindaUser = User.builder()
                .username("linda")
                .password(passwordEncoder.encode("password123"))
                //Podemos guardar o perfil do usuário, STUDENT ou ADMIN, no banco de dados, na FORMA DE STRING
                //e depois passar para ENUM com ENUM.valueOf("STUDENT ou ADMIN")
                //.roles(ApplicationUserRole.ADMIN.name()) //Pega os valores referentes ao perfil 'ADMIN' //ROLE_ADMIN
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();


        System.out.println("AQUI: " + ApplicationUserRole.ADMIN.name());

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password123"))
                //.roles(ApplicationUserRole.ADMINTRAINEE.name())
                .authorities(ApplicationUserRole.ADMINTRAINEE.getGrantedAuthorities())
                .build();

        //Permitirar logar com esse usuário na página de login
        return new InMemoryUserDetailsManager (
                annaSmithUser,
                lindaUser,
                tomUser
        );
    }

    /*
        Nós temos um arquivo ENUM que contém todas as permissões existentes no nosso sistema.

        Nós também temos um outro arquivo ENUM que contém os cargos (ADMIN, STUDENT, ADMINTRAINEE),
        em que cada cargo possui um SET de permissões

        Depois, temos como permitir que apenas os usuários que possuem um determinado cargo possam
        acessar uma parte da nossa API, além de poder limitar para apenas ter como LER/ESCREVER
     */
}
