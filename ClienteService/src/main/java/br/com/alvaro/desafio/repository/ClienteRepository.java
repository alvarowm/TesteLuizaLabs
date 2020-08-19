package br.com.alvaro.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alvaro.desafio.entidade.Campanha;
import br.com.alvaro.desafio.entidade.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long>{

	@Query("from Cliente entity where entity.nomeDoCliente = ?1")
	List<Cliente> findByName(String name);
	
	@Query("from Cliente entity where entity.email = ?1")
	Cliente findByEmail(String email);
	
	@Query("select campanhas from Cliente entity where entity.id = ?1")
	List<Campanha> findCampanhasDoClienteById(Long id);
	
	@Query("from Cliente entity where entity.timeDoCoracao.id = ?1")
	List<Cliente> findByTime(Long idTimeDoCoracao);

}
