package com.banco.service;

import com.banco.entity.Cliente;
import com.banco.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    public Cliente criarCliente(Cliente cliente) {
        // valida se já existe cliente com mesmo email
        if (clienteRepository.findByEmail(cliente.email) != null) {
            throw new WebApplicationException("Email já cadastrado.", 400);
        }
        clienteRepository.persist(cliente);
        return cliente;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.listAll();
    }

    public Cliente buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new WebApplicationException("Cliente não encontrado.", 404);
        }
        return cliente;
    }

    public Cliente atualizarCliente(Long id, Cliente dadosAtualizados) {
        Cliente cliente = buscarPorId(id);

        // regra: CPF não pode ser alterado
        if (dadosAtualizados.cpf != null && !dadosAtualizados.cpf.equals(cliente.cpf)) {
            throw new WebApplicationException("CPF não pode ser atualizado.", 400);
        }

        cliente.nome = dadosAtualizados.nome;
        cliente.email = dadosAtualizados.email;
        cliente.senha = dadosAtualizados.senha;

        clienteRepository.persist(cliente