@Test
void deveCriarContaValida() {
    given()
      .contentType("application/json")
      .body("{\"clienteId\":1, \"tipo\":\"CORRENTE\", \"saldo\":100}")
    .when()
      .post("/contas")
    .then()
      .statusCode(201)
      .body("saldo", equalTo(100));
}

