package com.banco.resource;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientesResource {

    @Inject
    ClienteService clienteService;

    @POST
    @Transactional
    @RolesAllowed("GERENTE")
    public Response criar(@Valid Cliente cliente) {
        Cliente novo = clienteService.criarCliente(cliente);
        return Response.status(Response.Status.CREATED).entity(novo).build();
    }

    @GET
    @RolesAllowed("GERENTE")
    public List<Cliente> listar() {
        return clienteService.listarClientes();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("GERENTE")
    public Cliente buscar(@PathParam("id") Long id) {
        return clienteService.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("GERENTE")
    public Response atualizar(@PathParam("id") Long id, @Valid Cliente dados) {
        Cliente atualizado = clienteService.atualizarCliente(id, dados);
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("GERENTE")
    public Response deletar(@PathParam("id") Long id) {
        clienteService.deletarCliente(id);
        return Response.noContent().build();
    }
}
