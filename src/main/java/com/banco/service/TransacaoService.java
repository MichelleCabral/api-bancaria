package com.banco.service;

import com.banco.entity.Conta;
import com.banco.entity.Transacao;
import com.banco.entity.TipoTransacao;
import com.banco.repository.ContaRepository;
import com.banco.repository.TransacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class TransacaoService {

    @Inject
    ContaRepository contaRepository;

    @Inject
    TransacaoRepository transacaoRepository;

    public Transacao transferir(Long origemId, Long destinoId, BigDecimal valor) {
        Conta origem = contaRepository.findById(origemId);
        Conta destino = contaRepository.findById(destinoId);

        if (origem == null || destino == null) {
            throw new WebApplicationException("Conta origem ou destino não encontrada.", 404);
        }
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

    public Transacao buscarPorId(Long id) {
        Transacao transacao = transacaoRepository.findById(id);
        if (transacao == null) {
            throw new WebApplicationException("Transação não encontrada.", 404);
        }
        return transacao;
    }

    public List<Transacao> listarPorConta(Long contaId) {
        return transacaoRepository.find("contaOrigem.id = ?1 or contaDestino.id = ?1", contaId).list();
    }
}
