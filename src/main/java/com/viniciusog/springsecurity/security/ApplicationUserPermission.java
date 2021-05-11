package com.viniciusog.springsecurity.security;

public enum ApplicationUserPermission {
    //Contém as permissões de um determinado usuário
    STUDENT_READ("student:read"),   //Consegue ler os dados de estudante
    STUDENT_WRITE("student:write"), //Consegue atualizar os dados de estudante
    COURSE_READ("course:read"),     //Consegue ler os dados de curso
    COURSE_WRITE("course:write");   //Consegue atualizar os dados de curso

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}