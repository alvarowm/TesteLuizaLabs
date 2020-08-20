package br.com.alvaro;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.alvaro.desafio.Application;
import io.restassured.response.ValidatableResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	private int porta;

	@Test
	public void contextLoads() {
	}

	//cliente inserido na subida do sistema deve estar ok.
	@Test
	public void cliente200ComID1() {

		given().port(porta).when().get("v1/clientes/{id}", 1).then().statusCode(200).body("nomeDoCliente",
				equalTo("Alberto Andrade"));

	}

	@Test
	public void testRequisitarTodosOsclientes() {
		ValidatableResponse response = given().port(porta).when().get("v1/clientes/").then();
		assertThat(response.extract().jsonPath().getList("$").size(), equalTo(3));
	}

	@Test
	public void testDeleteEInsert() throws JSONException {

		ValidatableResponse response = requisitarTodosOsRegistros();

		int numeroDeRegistros = response.extract().jsonPath().getList("$").size();

		given().port(porta).delete("v1/clientes/1");

		response = requisitarTodosOsRegistros();

		int numeroDeRegistrosAposDelecao = response.extract().jsonPath().getList("$").size();

		assertEquals(numeroDeRegistrosAposDelecao, numeroDeRegistros - 1);

		Map<String, String> map = new HashMap<String, String>();		

		map.put("nomeDoCliente", "Cliente do Teste.");
		map.put("email", "alberto@abc.com");
		map.put("dataNascimento", "1950-10-01");

		given().contentType("application/json").body(map).port(porta).post("v1/clientes/").then();
		
		response = requisitarTodosOsRegistros();
		
		int numeroAposInsercao = response.extract().jsonPath().getList("$").size();

		assertEquals(numeroAposInsercao, numeroDeRegistros);
	}

	private ValidatableResponse requisitarTodosOsRegistros() {
		ValidatableResponse response;
		response = given().port(porta).when().get("v1/clientes/").then();
		return response;
	}
	
	//caso se tente inserir duaz vezes o mesmo cliente, não deve deixar adicionar
	@Test
	public void testAdicionarDuasVezes() {
		
		Map<String, String> map = new HashMap<String, String>();		

		map.put("nomeDoCliente", "Cliente do Teste.");
		map.put("email", "emailDeTeste@abc.com");
		map.put("dataNascimento", "1950-10-01");
		
		given().contentType("application/json").body(map).port(porta).post("v1/clientes/").then();
		
		int numeroDeRegistros = requisitarTodosOsRegistros().extract().jsonPath().getList("$").size();
		
		given().contentType("application/json").body(map).port(porta).post("v1/clientes/").then();
		
		int numeroDeRegistrosAposSegundaInsercao = requisitarTodosOsRegistros().extract().jsonPath().getList("$").size();
		
		assertEquals(numeroDeRegistros, numeroDeRegistrosAposSegundaInsercao);
		
		given().port(porta).delete("v1/clientes/" + numeroDeRegistros);
		
	}

}
