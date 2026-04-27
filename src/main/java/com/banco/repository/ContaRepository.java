package com.banco.repository;

import com.banco.entity.Conta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ContaRepository implements PanacheRepository<Conta> {
    // Aqui você pode adicionar consultas específicas, se precisar.
    // Exemplo: buscar conta por número
    public Conta findByNumero(String numero) {
        return find("numero", numero).firstResult();
    }
}
