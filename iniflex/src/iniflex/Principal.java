package iniflex;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

class Pessoa {
    String nome;
    LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
}

class Funcionario extends Pessoa {
    BigDecimal salario;
    String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("#,###.00");
        return "Nome: " + nome + ", Data Nascimento: " + dataNascimento.format(dtf) + ", Salário: " + df.format(salario) + ", Função: " + funcao;
    }
}

public class Principal {
    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
                new Funcionario("Maria", LocalDate.parse("18/10/2000", dtf), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.parse("12/05/1990", dtf), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.parse("02/05/1961", dtf), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.parse("14/10/1988", dtf), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.parse("05/01/1995", dtf), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.parse("19/11/1999", dtf), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.parse("31/03/1993", dtf), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.parse("08/07/1994", dtf), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.parse("24/05/2003", dtf), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.parse("02/09/1996", dtf), new BigDecimal("2799.93"), "Gerente")
        ));

        funcionarios.removeIf(f -> f.getNome().equals("João"));

  
        System.out.println("Lista de funcionários:");
        funcionarios.forEach(System.out::println);

        //10% no salário
        funcionarios.forEach(f -> f.salario = f.salario.multiply(BigDecimal.valueOf(1.10)));

        //funcionários por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nFuncionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ": " + lista);
        });

        //aniversário no mês 10 e 12
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        funcionarios.stream()
                .filter(f -> {
                    int mes = f.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(System.out::println);

        //maior idade
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        long idade = ChronoUnit.YEARS.between(maisVelho.getDataNascimento(), LocalDate.now());
        System.out.println("\nFuncionário com a maior idade: " + maisVelho.getNome() + ", Idade: " + idade + " anos");

        //ordem alfabética
        System.out.println("\nFuncionários em ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);

        //salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários: " + new DecimalFormat("#,###.00").format(totalSalarios));

        //salários mínimos por funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários em termos de salários mínimos:");
        funcionarios.forEach(f -> {
            System.out.println(f.getNome() + ": " + new DecimalFormat("#.##").format(f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP)) + " salários mínimos");
        });
    }
}