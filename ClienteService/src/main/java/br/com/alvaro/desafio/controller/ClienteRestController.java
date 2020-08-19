package br.com.alvaro.desafio.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alvaro.desafio.entidade.Campanha;
import br.com.alvaro.desafio.entidade.Cliente;
import br.com.alvaro.desafio.service.CampanhaService;
import br.com.alvaro.desafio.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@RestController
@Api(value = "Cliente")
@RequestMapping(value="/v1")
public class ClienteRestController {

	@Autowired
	ClienteService service;
	
	@Autowired
	CampanhaService campanhaService;
	
	private static final String JAH_CADASTRADO = "Usuário já cadastrado.";

	@ApiOperation(value = "Retorna clientes.", httpMethod = "GET")
	@ApiResponse(code = 200, message = "Retorna os clientes cadastrados")
	@GetMapping("/clientes")
	public List<Cliente> listarClientes() {
		return service.buscarClientes();
	}

	@ApiOperation(value = "Adiciona um cliente.", httpMethod = "POST")
	@ApiResponse(code = 201, message = "Adiciona um cliente.")
	@PostMapping("/clientes")
	public ResponseEntity<Object> adicionarCliente(@Valid @RequestBody Cliente cliente) {
		Cliente clienteDaBase = service.findByEmail(cliente.getEmail());
		if (clienteDaBase != null) {
			if (!service.haCampanhasCadastradas(clienteDaBase.getId())) {
				List<Campanha> campanhas = campanhaService.buscarCampanhasPorTime(cliente.getTimeDoCoracao().getId());
				return new ResponseEntity<Object>(campanhas, HttpStatus.OK) ;
			} 
			else
				return new ResponseEntity<Object>(JAH_CADASTRADO, HttpStatus.OK) ;
		}
		service.relacionarCampanhasPorTime(cliente);
		return new ResponseEntity<Object>(service.save(cliente), HttpStatus.CREATED) ;
	}

	@ApiOperation(value = "Exibe um ciente.", httpMethod = "GET")
	@ApiResponse(code = 200, message = "Cliente")
	@GetMapping("clientes/{id}")
	public Cliente exibirCliente(@PathVariable(value="id") Long id, Model model) {
		return service.buscarCliente(id);
	}

	@ApiOperation(value = "Atualiza um cliente.", httpMethod = "PUT")
	@ApiResponse(code = 201, message = "Cliente atualizado.")
	@PutMapping("/clientes/{id}")
	public Cliente atualizarCampanha(@PathVariable("id") long id, @Valid  @RequestBody Cliente cliente) {
		return service.atualizarCliente(cliente, id);
	}

	@ApiOperation(value = "Cliente excluído", httpMethod = "DELETE")
	@ApiResponse(code = 200, message = "Cliente excluído.")
	@DeleteMapping("/clientes/{id}")
	public void apagarCliente(@PathVariable("id") long id, Model model) {
		service.apagarCliente(id);
	}
}
