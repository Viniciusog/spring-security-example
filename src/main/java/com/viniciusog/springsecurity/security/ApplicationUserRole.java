package com.viniciusog.springsecurity.security;

import com.google.common.collect.Sets;

import java.util.Set;
import static com.viniciusog.springsecurity.security.ApplicationUserPermission.*;

public enum ApplicationUserRole {

    //Cada tipo de usuário tem as suas permissões, que são criadas no enum ApplicationUserPermission
    STUDENT(Sets.newHashSet()), //Estudante não tem nenhuma permissão
    ADMIN(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE,
            COURSE_READ, COURSE_WRITE)); //Admim tem todas as permissões

    //Conjunto único de permissões
    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() {
        return permissions;
    }
}
