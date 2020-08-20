package br.com.alvaro.desafio.controller;

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
import br.com.alvaro.desafio.service.CampanhaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

//anotação mais moderna
@RestController
@Api(value = "Campanha")
@RequestMapping(value="/v1")
public class CampanhaRestController {

	private static final String CAMPANHA_ATUALIZADA = "Campanha atualizada.";

	private static final String CAMPANHA_APAGADA = "Campanha apagada!";

	private static final String NAO_HA_CAMPANHA_CADASTRADA = "Não há campanha cadastrada.";
	
	@Autowired
	CampanhaService service;

	@ApiOperation(value = "Retorna campanhas.", httpMethod = "GET")
	@ApiResponse(code = 200, message = "Retorna as campanhas cadastradas")
	@GetMapping("/campanhas")
	public ResponseEntity<Object> listarCampanhas(Model model) {
		return new ResponseEntity<Object>(service.buscarCampanhas(), HttpStatus.OK);
	}

	@ApiOperation(value = "Adiciona uma campanha.", httpMethod = "POST")
	@ApiResponse(code = 201, message = "Adiciona uma campanha.")
	@PostMapping("/campanhas")
	public ResponseEntity<Object> adicionarCampanha(@Valid @RequestBody Campanha campanha) {
		return new ResponseEntity<Object>(service.cadastrarCampanha(campanha), HttpStatus.CREATED);
	}

	@ApiOperation(value = "Exibe uma campanha.", httpMethod = "GET")
	@ApiResponse(code = 200, message = "Campanha")
	@GetMapping("campanhas/{id}")
	public ResponseEntity<Object> exibirCampanha(@PathVariable(value="id") Long id, Model model) {
		return new ResponseEntity<Object>(service.buscarCampanha(id), HttpStatus.OK);
	}

	@ApiOperation(value = "Atualiza uma campanha.", httpMethod = "PUT")
	@ApiResponse(code = 200, message = CAMPANHA_ATUALIZADA)
	@PutMapping("/campanhas/{id}")
	public ResponseEntity<Object> atualizarCampanha(@PathVariable("id") long id, @Valid  @RequestBody Campanha campanha) {
		Campanha campanhaDaBase = service.buscarCampanha(id);

		if (campanhaDaBase == null)
			return new ResponseEntity<Object>(NAO_HA_CAMPANHA_CADASTRADA, HttpStatus.NOT_FOUND);

		campanha.setId(id);
		
		service.cadastrarCampanha(campanha);
		return new ResponseEntity<Object>(CAMPANHA_ATUALIZADA, HttpStatus.OK);
	}

	@ApiOperation(value = "Campanha excluída", httpMethod = "DELETE")
	@ApiResponse(code = 200, message = CAMPANHA_APAGADA)
	@DeleteMapping("/campanhas/{id}")
	public ResponseEntity<Object> apagarCampanha(@PathVariable("id") long id, Model model) {
		Campanha campanhaDaBase = service.buscarCampanha(id);

		if (campanhaDaBase == null)
			return new ResponseEntity<Object>(NAO_HA_CAMPANHA_CADASTRADA, HttpStatus.NOT_FOUND);
		
		service.apagarCampanha(id);
		return new ResponseEntity<Object>(CAMPANHA_APAGADA, HttpStatus.OK);
	}
}
