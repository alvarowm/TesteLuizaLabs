package br.com.alvaro.desafio.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	CampanhaService service;

	@ApiOperation(value = "Retorna campanhas.", httpMethod = "GET")
	@ApiResponse(code = 200, message = "Retorna as campanhas cadastradas")
	@GetMapping("/campanhas")
	public List<Campanha> listarCampanhas(Model model) {
		return service.buscarCampanhas();
	}

	@ApiOperation(value = "Adiciona uma campanha.", httpMethod = "POST")
	@ApiResponse(code = 201, message = "Adiciona uma campanha.")
	@PostMapping("/campanhas")
	public Campanha adicionarCampanha(@Valid @RequestBody Campanha campanha) {
		return service.cadastrarCampanha(campanha);
	}

	@ApiOperation(value = "Exibe uma campanha.", httpMethod = "GET")
	@ApiResponse(code = 200, message = "Campanha")
	@GetMapping("campanhas/{id}")
	public Campanha exibirCampanha(@PathVariable(value="id") Long id, Model model) {
		return service.buscarCampanha(id);
	}

	@ApiOperation(value = "Atualiza uma campanha.", httpMethod = "PUT")
	@ApiResponse(code = 201, message = "Campanha atualizada.")
	@PutMapping("/campanhas/{id}")
	public Campanha atualizarCampanha(@PathVariable("id") long id, @Valid  @RequestBody Campanha campanha) {
		Campanha campanhaDaBase = service.buscarCampanha(id);

		if (campanhaDaBase == null)
			return null;

		campanha.setId(id);
		
		return service.cadastrarCampanha(campanha);
	}

	@ApiOperation(value = "Campanha excluída", httpMethod = "DELETE")
	@ApiResponse(code = 200, message = "Campanha excluída.")
	@DeleteMapping("/campanhas/{id}")
	public void apagarCampanha(@PathVariable("id") long id, Model model) {
		service.apagarCampanha(id);
	}
}
