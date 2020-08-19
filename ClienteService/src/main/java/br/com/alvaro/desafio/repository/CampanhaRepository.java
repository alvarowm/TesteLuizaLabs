package br.com.alvaro.desafio.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alvaro.desafio.entidade.Campanha;

@Repository
public interface CampanhaRepository extends CrudRepository<Campanha, Long>{

	@Query("from Campanha entity where entity.nomeDaCampanha = ?1")
	List<Campanha> findByName(String name);
	
	@Query("from Campanha entity where entity.dataFinal >= ?1")
	List<Campanha> findByDate(Date dataFinal);
	
	@Query("from Campanha entity where entity.time.id = ?1")
	List<Campanha> findByTime(Long idTime);
	
	@Query("from Campanha entity where entity.dataInicial >= ?1 "
			+ "and entity.dataFinal <= ?2 or entity.dataInicial between ?1 and ?2 or entity.dataFinal between ?1 and ?2")
	List<Campanha> findByDataInicialEDataFinal (Date dataInicial, Date dateFinal);
	
	@Query("from Campanha entity where entity.dataFinal = ?1 and entity.id != ?2")
	List<Campanha> findByDataFinal(Date dataFinal, Long id);

}
