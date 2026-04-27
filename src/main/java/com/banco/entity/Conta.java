package com.banco.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class Conta extends PanacheEntity {

    @Column(unique = true)
    public String numero;

    @Enumerated(EnumType.STRING)
    public TipoConta tipo;

    public Double saldo = 0.0;

    @ManyToOne
    public Cliente cliente;
}

