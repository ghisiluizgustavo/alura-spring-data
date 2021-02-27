package br.com.alura.spring.data.repository;

import java.time.LocalDate;
import java.util.List;

import br.com.alura.spring.data.orm.FuncionarioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,
        JpaSpecificationExecutor<Funcionario> {

  List<Funcionario> findByNome(String nome);

  List<Funcionario> findByNome(String nome, Pageable pageable);

  @Query("SELECT f FROM Funcionario f WHERE f.nome = :nome "
        + "AND f.salario >= :salario AND f.dataContratacao = :data")
  List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);

  @Query(value = "select * from funcionarios f where f.data_contratacao >= :data", nativeQuery = true)
  List<Funcionario> findDataContratacaoMaior(LocalDate data);

  @Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
  Page<FuncionarioProjection> findFuncionarioSalario(Pageable pageable);
}
