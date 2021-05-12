package com.viniciusog.springsecurity.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.viniciusog.springsecurity.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    //Cada tipo de usuário tem as suas permissões, que são criadas no enum ApplicationUserPermission
    STUDENT(Sets.newHashSet()), //Estudante não tem nenhuma permissão

    ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE,
            COURSE_READ, COURSE_WRITE)), //Admim tem todas as permissões

    //Tem permissões apenas para ler o conteúdo
    ADMINTRAINEE(Sets.newHashSet(STUDENT_READ,COURSE_READ)); //ROLE_ADMINTRAINEE

    //Sets.newHashset(STUDENT_READ, COURSE_READ), etc são passados por padrão como parâmetro do nosso
    //construtor na hora da instanciação.

    //É o conjunto de permission definido dentro de cada tipo ENUM ROLE
    //É identificado automaticamente pelo Java, através do construtor
    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        //Percorre todas as permission da nossa role
        //Para cada permission, faz getPermission() para pegar o valor da permission em String (ex: course:read)
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        //Teremos um set de string de permissões, porém o nosso Spring não sabe para que ROLE são essas permissões,
        //para isso, temos que adicionar a ROLE dentro do mesmo set.
        //Portanto, basta pegarmos a ROLE atual através de this.name()
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
