package br.com.alvaro.desafio.entidade;

import java.io.Serializable;
import java.util.Date;
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

@Entity
@Table(name = "CAMPANHAS")
@SequenceGenerator(name = "CAMPANHAENTITY", sequenceName = "SEQCAMPANHA", allocationSize = 1)
public class Campanha implements Serializable{

	private static final long serialVersionUID = 6357743962760227315L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	//Usando está abordagem é necessário ter cautela para verificar que método de geração será utilizado.
	//Muitas vezes uma tabela de controle pode ser utilizada e dependendo da forma de criação, podemos não ter permissão de criar.
	private Long id; 
	
	@Column(name = "NOME", nullable = false)
	@NotBlank(message = "Nome da campanha é obrigatório!")
	private String nomeDaCampanha;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	@Column(name = "DATA_INICIAL", nullable = false)
	@NotNull(message = "Data inicial da campanha é obrigatória!")
	private Date dataInicial;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	@Column(name = "DATA_FINAL", nullable = false)
	@NotNull(message = "Data de vigência da campanha é obrigatória!")
	private Date dataFinal;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "time_id", referencedColumnName = "id")
	private Time time;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
    Set<Cliente> clientes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDaCampanha() {
		return nomeDaCampanha;
	}

	public void setNomeDaCampanha(String nomeDaCampanha) {
		this.nomeDaCampanha = nomeDaCampanha;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Set<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Set<Cliente> clientes) {
		this.clientes = clientes;
	}
	
}
