package br.com.alvaro.desafio.rmq;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alvaro.desafio.entidade.Campanha;
import br.com.alvaro.desafio.service.CampanhaService;

@Component
public class CampanhaConsumer {
	
	@Autowired
	CampanhaService campanhaService;
	
	@JmsListener(destination = "CAMPANHA_QUEUE")
    public void recievedMessage(String msg) throws JsonParseException, JsonMappingException, IOException {
        Campanha campanha = converterParaComapanhas(msg);
        campanhaService.cadastrarCampanha(campanha);
    }
	
	private Campanha converterParaComapanhas (String msg) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper objectMapper = new ObjectMapper();
		Campanha campanha = objectMapper.readValue(msg, Campanha.class); 
		return campanha;
	}

}
