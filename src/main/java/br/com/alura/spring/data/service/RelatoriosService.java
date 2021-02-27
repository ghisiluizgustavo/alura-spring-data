package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import br.com.alura.spring.data.orm.FuncionarioProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {

  private boolean system = true;
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private final FuncionarioRepository funcionarioRepository;

  public RelatoriosService(FuncionarioRepository funcionarioRepository){
    this.funcionarioRepository = funcionarioRepository;
  }

  public void inicial(Scanner scanner){
    while(system) {
      System.out.println("Qual acao de cargo deseja executar");
      System.out.println("0 - Sair");
      System.out.println("1 - Busca funcionario nome");
      System.out.println("2 - Busca funcionario nome, data contratacao e salario maior");
      System.out.println("3 - Busca funcionario data contratacao");
      System.out.println("4 - Busca funcionario pelo salario");

      int action = scanner.nextInt();
      
      switch (action) {
      case 1:
        this.buscaFuncionarioNome(scanner);
        break;
      case 2:
        this.buscaFuncionarioNomeSalarioMaiorData(scanner);
        break;
      case 3:
        this.buscaFuncionarioDataContratacao(scanner);
        break;
      case 4:
        this.buscaFuncionarioSalario(scanner);
        break;
      default:
        system = false;
        break;
      }
      
    }
  }

  private void buscaFuncionarioNome(Scanner scanner){
    System.out.println("Qual nome deseja pesquisar?");
    String nome = scanner.next();
    List<Funcionario> list = funcionarioRepository.findByNome(nome); 
    list.forEach(System.out::println);
		
  }

  private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner){
    System.out.println("Qual nome deseja pesquisar?");
    String nome = scanner.next();

    System.out.println("Qual data de contratacao deseja pesquisar?");
    String data = scanner.next();
    LocalDate localDateTime = LocalDate.parse(data, formatter);

    System.out.println("Qual salario deseja pesquisar?");
    Double salario = scanner.nextDouble();

    List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDateTime);

    list.forEach(System.out::println);
  }

  private void buscaFuncionarioDataContratacao(Scanner scanner){
    System.out.println("Qual data de contratacao deseja pesquisar?");
    String data = scanner.next();
    LocalDate localDateTime = LocalDate.parse(data, formatter);

    List<Funcionario> list = funcionarioRepository.findDataContratacaoMaior(localDateTime);

    list.forEach(System.out::println);
  }

  private void buscaFuncionarioSalario(Scanner scanner){
    System.out.println("Insira qual a pagina deseja buscar: ");
    int page = scanner.nextInt();

    Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "salario"));

    Page<FuncionarioProjection> list = funcionarioRepository.findFuncionarioSalario(pageable);
    list.forEach(f -> System.out.println("Funcionario = Id: " + f.getId() +
            " | Nome: " + f.getNome() + " | Salario: " + f.getSalario()));
  }
  
}
