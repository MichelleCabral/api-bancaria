package com.banco.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cliente extends PanacheEntity {

    @NotBlank
    public String nome;

    @NotBlank
    public String cpf;

    @Email
    public String email;

    @NotBlank
    public String senha;
}

