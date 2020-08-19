package br.com.alvaro.desafio.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alvaro.desafio.entidade.Campanha;
import br.com.alvaro.desafio.entidade.Cliente;
import br.com.alvaro.desafio.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	@Autowired
	CampanhaService campanhaService;
	
	public void relacionarCampanhasPorTime(@Valid Cliente cliente) {
		if (cliente.getTimeDoCoracao() != null) {			
			List<Campanha> campanhasPorTime = campanhaService.buscarCampanhasPorTime(cliente.getTimeDoCoracao().getId());
			
			if (cliente.getCampanhas() == null)
				cliente.setCampanhas(new HashSet<Campanha>());
			
			cliente.getCampanhas().addAll(campanhasPorTime);
		}
	}
	
	public Cliente buscarCliente(long id) {
		Optional<Cliente> cliente = repository.findById(id);
		
		if (!cliente.isPresent())
			throw new IllegalArgumentException("Identificador inválido:" + id);
		return cliente.get();
	}

	
	public void apagarCliente(long id) {
		repository.deleteById(id);
	}
	
	public List<Cliente> findByName(String name){
		return repository.findByName(name);
	}
	
	public Cliente findByEmail(String email){
		return repository.findByEmail(email);
	}

	
	public List<Cliente> buscarClientes() {
		return (List<Cliente>) repository.findAll();
	}
	
	public boolean haCampanhasCadastradas(Long id) {
		List<Campanha> campanhas = repository.findCampanhasDoClienteById(id);
		return campanhas != null & !campanhas.isEmpty();
	}
	
	public List<Cliente> findByTime(Long idTimeDoCoracao) {
		return repository.findByTime(idTimeDoCoracao);
	}

	public Cliente atualizarCliente(@Valid Cliente cliente, long id) {
		Cliente clienteDaBase = buscarCliente(id);

		if (clienteDaBase == null)
			return null;

		//vira-casaca 
		if (!clienteDaBase.getTimeDoCoracao().getId().equals(cliente.getTimeDoCoracao().getId())) {
			relacionarCampanhasPorTime(clienteDaBase);
			cliente.setCampanhas(null);
		}
		
		cliente.setCampanhas(clienteDaBase.getCampanhas());
			
		cliente.setId(id);
		
		
		return repository.save(cliente);
	}
	
	public Cliente save (Cliente cliente) {
		return  repository.save(cliente);
	}

}
