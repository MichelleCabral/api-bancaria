package com.banco.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transacao extends PanacheEntity {

    @Enumerated(EnumType.STRING)
    public TipoTransacao tipo;

    public Double valor;

    public LocalDateTime dataHora;

    @ManyToOne
    public Conta contaOrigem;

    @ManyToOne
    public Conta contaDestino;
}
