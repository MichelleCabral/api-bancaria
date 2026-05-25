package com.banco.resource;

import com.banco.entity.Transacao;
import com.banco.service.ContaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;

@Path("/contas")
public class ContasResource {

    @Inject
    ContaService contaService;

    @POST
    @Path("/{id}/deposito")
    @Transactional
    @RolesAllowed({"GERENTE","CLIENTE"})
    public Response deposito(@PathParam("id") Long id, BigDecimal valor) {
        Transacao transacao = contaService.depositar(id, valor);
        return Response.ok(transacao).build();
    }

    @POST
    @Path("/{id}/saque")
    @Transactional
    @RolesAllowed({"GERENTE","CLIENTE"})
    public Response saque(@PathParam("id") Long id, BigDecimal valor) {
        Transacao transacao = contaService.sacar(id, valor);
        return Response.ok(transacao).build();
    }

    @POST
    @Path("/{origemId}/transferencia/{destinoId}")
    @Transactional
    @RolesAllowed({"GERENTE","CLIENTE"})
    public Response transferir(@PathParam("origemId") Long origemId,
                               @PathParam("destinoId") Long destinoId,
                               BigDecimal valor) {
        Transacao transacao = contaService.transferir(origemId, destinoId, valor);
        return Response.ok(transacao).build();
    }
}
