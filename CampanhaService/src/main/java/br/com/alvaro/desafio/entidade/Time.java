package br.com.alvaro.desafio.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "TIMES")
@SequenceGenerator(name = "TIMEENTITY", sequenceName = "SEQTIME", allocationSize = 1)
public class Time {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//Usando está abordagem é necessário ter cautela para verificar que método de geração será utilizado.
	//Muitas vezes uma tabela de controle pode ser utilizada e dependendo da forma de criação, podemos não ter permissão de criar.
	private Long id; 
	
	@Column(name = "NOME", nullable = false)
	@NotBlank(message = "Nome do time é obrigatório!")
	private String nomeDoTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDoTime() {
		return nomeDoTime;
	}

	public void setNomeDoTime(String nomeDoTime) {
		this.nomeDoTime = nomeDoTime;
	}
	
}
