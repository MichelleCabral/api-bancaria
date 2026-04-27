ppackage com.banco.resource;

import com.banco.entity.Conta;
import com.banco.entity.Transacao;
import com.banco.service.ContaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/contas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContasResource {

    @Inject
    ContaService contaService;

    @POST
    @Transactional
    @RolesAllowed("GERENTE")
    public Response criar(Conta conta) {
        Conta nova = contaService.criarConta(conta);
        return Response.status(Response.Status.CREATED).entity(nova).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"GERENTE","CLIENTE"})
    public Conta buscar(@PathParam("id") Long id) {
        return contaService.buscarPorId(id);
    }

    @POST
    @Path("/{id}/deposito")
    @Transactional
    @RolesAllowed({"GERENTE","CLIENTE"})
    public Response deposito(@PathParam("id") Long id, BigDecimal valor) {
        Transacao