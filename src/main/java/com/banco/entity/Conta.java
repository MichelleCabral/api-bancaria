package com.banco.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Conta extends PanacheEntity {

    @Column(unique = true)
    public String numero;

    @Enumerated(EnumType.STRING)
    public TipoConta tipo;

    public BigDecimal saldo = BigDecimal.ZERO;

    @ManyToOne
    public Cliente cliente;
}
