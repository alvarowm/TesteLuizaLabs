package br.com.alvaro.desafio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alvaro.desafio.entidade.Campanha;
import br.com.alvaro.desafio.entidade.Cliente;
import br.com.alvaro.desafio.repository.CampanhaRepository;

@Service
public class CampanhaService {

	@Autowired
	CampanhaRepository campanhaRepository;
	
	@Autowired
	ClienteService clienteService;


	public Campanha cadastrarCampanha(@Valid Campanha campanha) {
		if (campanha.getClientes() == null)
			campanha.setClientes(new HashSet<Cliente>());
		
		Optional<Campanha> campanhaDaBase = campanhaRepository.findById(campanha.getId()); 
		if (campanhaDaBase.isPresent()) {			
			campanha.getClientes().addAll(campanhaDaBase.get().getClientes());
			campanha.setId(campanhaDaBase.get().getId());
		}
		
		List<Cliente> clientesPorTime = clienteService.findByTime(campanha.getTime().getId());
		campanha.getClientes().addAll(clientesPorTime);
		
		campanha.getClientes().stream().forEach(cl -> {
			cl.getCampanhas().add(campanha);
		});
		
		return campanhaRepository.save(campanha);
	}


	public List<Campanha> buscarCampanhasPorTime(Long idTime) {
		return (List<Campanha>) campanhaRepository.findByTime(idTime);
	}

	public Campanha buscarCampanha(long id) {
		Optional<Campanha> campanha = campanhaRepository.findById(id);
		
		if (!campanha.isPresent())
			throw new IllegalArgumentException("Identificador inválido:" + id);
		return campanha.get();
	}
	
	public List<Campanha> findByName(String name){
		return campanhaRepository.findByName(name);
	}

}
