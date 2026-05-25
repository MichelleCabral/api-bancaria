package com.banco.service;

import com.banco.entity.Conta;
import com.banco.entity.TipoConta;
import com.banco.entity.Transacao;
import com.banco.entity.TipoTransacao;
import com.banco.repository.ContaRepository;
import com.banco.repository.TransacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class ContaService {

    @Inject
    ContaRepository contaRepository;

    @Inject
    TransacaoRepository transacaoRepository;

    public Conta criarConta(Conta conta) {
        contaRepository.persist(conta);
        return conta;
    }

    public Conta buscarPorId(Long id) {
        Conta conta = contaRepository.findById(id);
        if (conta == null) {
            throw new WebApplicationException("Conta não encontrada.", 404);
        }
        return conta;
    }

    public Transacao depositar(Long contaId, BigDecimal valor) {
        Conta conta = buscarPorId(contaId);
        if (conta.tipo.equals(TipoConta.ELETRONICA)) {
            throw new WebApplicationException("Conta ELETRONICA não permite depósitos.", 422);
        }
        conta.saldo = conta.saldo.add(valor);

        Transacao transacao = new Transacao();
        transacao.tipo = TipoTransacao.DEPOSITO;
        transacao.valor = valor;
        transacao.dataHora = LocalDateTime.now();
        transacao.contaDestino = conta;

        transacaoRepository.persist(transacao);
        contaRepository.persist(conta);

        return transacao;
    }

    public Transacao sacar(Long contaId, BigDecimal valor) {
        Conta conta = buscarPorId(contaId);
        if (conta.tipo.equals(TipoConta.ELETRONICA)) {
            throw new WebApplicationException("Conta ELETRONICA não permite saques.", 422);
        }
        if (conta.saldo.compareTo(valor) < 0) {
            throw new WebApplicationException("Saldo insuficiente.", 422);
        }
        conta.saldo = conta.saldo.subtract(valor);

        Transacao transacao = new Transacao();
        transacao.tipo = TipoTransacao.SAQUE;
        transacao.valor = valor;
        transacao.dataHora = LocalDateTime.now();
        transacao.contaOrigem = conta;

        transacaoRepository.persist(transacao);
        contaRepository.persist(conta);

        return transacao;
    }

    public Transacao transferir(Long origemId, Long destinoId, BigDecimal valor) {
        Conta origem = buscarPorId(origemId);
        Conta destino = buscarPorId(destinoId);

        if (origem.saldo.compareTo(valor) < 0) {
            throw new WebApplicationException("Saldo insuficiente.", 422);
        }

        origem.saldo = origem.saldo.subtract(valor);
        destino.saldo = destino.saldo.add(valor);

        Transacao transacao = new Transacao();
        transacao.tipo = TipoTransacao.TRANSFERENCIA;
        transacao.valor = valor;
        transacao.dataHora = LocalDateTime.now();
        transacao.contaOrigem = origem;
        transacao.contaDestino = destino;

        transacaoRepository.persist(transacao);
        contaRepository.persist(origem);
        contaRepository.persist(destino);

        return transacao;
    }
}

