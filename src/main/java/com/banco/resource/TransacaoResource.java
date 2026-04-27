package com.banco.resource;

import com.banco.entity.Transacao;
import com.banco.service.TransacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/transacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransacoesResource {

    @Inject
    TransacaoService transacaoService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"GERENTE","CLIENTE"})
    public Transacao buscar(@PathParam("id") Long id) {
        return transacaoService.buscarPorId(id);
    }

    @GET
    @RolesAllowed({"GERENTE","CLIENTE"})
    public List<Transacao> listarPorConta(@QueryParam("contaId") Long contaId) {
        return transacaoService.listarPorConta(contaId);
    }
}
