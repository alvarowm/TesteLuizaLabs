package br.com.alvaro.desafio.entidade;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "CLIENTES")
@SequenceGenerator(name = "CLIENTEENTITY", sequenceName = "SEQCLIENTE", allocationSize = 1)
public class Cliente {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//Usando está abordagem é necessário ter cautela para verificar que método de geração será utilizado.
	//Muitas vezes uma tabela de controle pode ser utilizada e dependendo da forma de criação, podemos não ter permissão de criar.
	private Long id; 
	
	@Column(name = "NOME", nullable = false)
	@NotBlank(message = "Nome do cliente é obrigatório!")
	private String nomeDoCliente;
	
	@Column(name = "EMAIL", nullable = false)
	@NotBlank(message = "E-mail é obrigatório!")
	private String email;

	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "DATA_NASCIMENTO", nullable = false)
	@NotNull(message = "Data de nascimento é obrigatória!")
	private LocalDate dataNascimento;
	
	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "TIME_ID", referencedColumnName = "id")
	private Time timeDoCoracao;
	
	@ManyToMany(fetch = FetchType.EAGER)
    Set<Campanha> campanhas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDoCliente() {
		return nomeDoCliente;
	}

	public void setNomeDoCliente(String nomeDoCliente) {
		this.nomeDoCliente = nomeDoCliente;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Time getTimeDoCoracao() {
		return timeDoCoracao;
	}

	public void setTimeDoCoracao(Time timeDoCoracao) {
		this.timeDoCoracao = timeDoCoracao;
	}

	public Set<Campanha> getCampanhas() {
		return campanhas;
	}

	public void setCampanhas(Set<Campanha> campanhas) {
		this.campanhas = campanhas;
	}

}
