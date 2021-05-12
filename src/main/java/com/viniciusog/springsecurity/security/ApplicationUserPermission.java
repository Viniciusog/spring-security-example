package com.viniciusog.springsecurity.security;

public enum ApplicationUserPermission {
    //Contém as permissões de um determinado usuário
    STUDENT_READ("student:read"),   //Consegue ler os dados de estudante
    STUDENT_WRITE("student:write"), //Consegue atualizar os dados de estudante
    COURSE_READ("course:read"),     //Consegue ler os dados de curso
    COURSE_WRITE("course:write");   //Consegue atualizar os dados de curso

    //'student:read', 'course:write', etc são passados automaticamente por parâmetro do construtor,
    //na hora da instanciação

    //É o valor da permissão em string definido em cada tipo ENUM PERMISSION (STUDENT_READ, STUDENT_WRITE, etc)
    //É identificado automaticamente pelo Java, por meio do construtor
    private final String permission; //É student:read, course:write, etc

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}