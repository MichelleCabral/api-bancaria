package com.banco.repository;

import com.banco.entity.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
    public Cliente findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
