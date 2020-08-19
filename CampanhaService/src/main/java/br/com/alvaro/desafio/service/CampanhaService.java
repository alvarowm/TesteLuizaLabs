package br.com.alvaro.desafio.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.alvaro.desafio.amq.CampanhaProducer;
import br.com.alvaro.desafio.entidade.Campanha;
import br.com.alvaro.desafio.repository.CampanhaRepository;
import br.com.alvaro.desafio.utils.DateUtils;

@Service
public class CampanhaService {

	@Autowired
	CampanhaRepository repository;
	
	@Autowired
	CampanhaProducer producer;
	
	public Campanha cadastrarCampanha(@Valid Campanha campanha) {
		List<Campanha> campanhas = repository.findByDataInicialEDataFinal(campanha.getDataInicial(), campanha.getDataFinal());
		Map<Long, Campanha> campanhasParaFila = new HashMap<>();
		
		campanhas.stream().forEach(c -> {
			c.setDataFinal(DateUtils.adicionarDia(c.getDataFinal()));
			c = repository.save(c);
			campanhasParaFila.put(c.getId(), c);
			
			while (!repository.findByDataFinal(c.getDataFinal(),c.getId()).isEmpty() || c.getDataFinal().equals(campanha.getDataFinal())) {
				c.setDataFinal(DateUtils.adicionarDia(c.getDataFinal()));
				repository.save(c);
				campanhasParaFila.put(c.getId(), c);
			}
		});
		
		campanhasParaFila.put(campanha.getId(), campanha);
		enviarCampanhas(campanhas);
		return repository.save(campanha);
	}

	public List<Campanha> buscarCampanhas() {
		return (List<Campanha>) repository.findByDate(new Date());
	}

	public Campanha buscarCampanha(long id) {
		Optional<Campanha> campanha = repository.findById(id);
		
		if (!campanha.isPresent())
			throw new IllegalArgumentException("Identificador inválido:" + id);
		return campanha.get();
	}

	public void apagarCampanha(long id) {
		repository.deleteById(id);
	}

	private void enviarCampanhas(Collection<Campanha> campanhas){
		campanhas.stream().forEach(c -> {
				try {
					producer.enviarCampanha(c);
				} catch (JmsException | JsonProcessingException e) {
					e.printStackTrace();
				}
		});
	}

}
