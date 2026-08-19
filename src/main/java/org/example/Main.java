package org.example;

import javax.swing.table.TableRowSorter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String connectionString = "jdbc:sqlite:banco.db";
    static void main(String[] args) throws Exception {

        // Criar a tabela alunos, caso ela não exista.
        criarTabela();

        int opcao = 0;

        Scanner scanner = new Scanner(System.in);

        do {

            exibirMenu();
            System.out.println("Digite a opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> inserir();
                case 2 -> consultarTodos();
                case 3 -> buscar();
                case 4 -> atualizar();
                case 5 -> excluir();
            }

        } while (opcao != 0);

    }

    public static void exibirMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("         SISTEMA DE ALUNOS        ");
        System.out.println("========================================");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Listar alunos");
        System.out.println("3 - Buscar aluno");
        System.out.println("4 - Atualizar aluno");
        System.out.println("5 - Excluir aluno");
        System.out.println("0 - Sair");
        System.out.println("========================================");

    }

    private static void criarTabela() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Alunos(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    email TEXT NOT NULL,
                    idade INTEGER
                    )
                """;

        try (var connection = DriverManager.getConnection(connectionString)){
            var statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e){
            System.out.println("Erro ao abrir a conexão: " + e.getMessage());
        }
    }

    private static void inserir(){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = scanner.nextInt();


        String sql = "INSERT INTO Alunos (nome, email, idade)";
        sql += " VALUES ('" + nome +"', '" + email + "', " + idade + ")";

        try (var connection = DriverManager.getConnection(connectionString)){
            var statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao executar a inserção: " + e.getMessage());
        }
    }

    private static void consultarTodos() {

        String sql = """
                    SELECT id, nome, email, idade from Alunos;
                """;
        try (var connection = DriverManager.getConnection(connectionString)){

            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(sql);

            // Ler todos os dados da tabela
            while (resultSet.next()){
                var id = resultSet.getInt("id");
                var nome = resultSet.getString("nome");
                var email = resultSet.getString("email");
                var idade = resultSet.getInt("idade");

                System.out.printf("Dados do Aluno: %s %s %s %s \n", id, nome, email, idade);
            }
        } catch (SQLException e){
            System.out.println("Erro ao consultar a tabela: " + e.getMessage());
        }
    }

    private static void buscar(){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o id, nome ou email do aluno que deseja buscar: ");
        String busca = scanner.nextLine();

        String sql = "SELECT id, nome, email, idade FROM Alunos WHERE nome LIKE '%" + busca + "%' OR email LIKE '%" + busca + "%'";

        if (busca.matches("\\d+")) {
            sql += " OR id = " + busca;
        }

        try (var connection = DriverManager.getConnection(connectionString)){
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery(sql);

            boolean encontrou = false;

            while (resultSet.next()) {
                encontrou = true;
                var id = resultSet.getInt("id");
                var nome = resultSet.getString("nome");
                var email = resultSet.getString("email");
                var idade = resultSet.getInt("idade");

                System.out.printf("\nDados do Aluno: %s %s %s %s \n", id, nome, email, idade);
            }

            if (!encontrou) {
                System.out.println("Aluno não encontrado.");
            }

        } catch (SQLException e){
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }
    }

    private static void atualizar(){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do aluno que deseja atualizar: ");
        int id = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();

        System.out.print("Novo email: ");
        String email = scanner.nextLine();

        System.out.print("Nova idade: ");
        int idade = scanner.nextInt();

        String sql = "UPDATE Alunos SET nome = '" + nome + "', email = '" + email + "', idade = " + idade;
        sql += " WHERE id = " + id;

        try (var connection = DriverManager.getConnection(connectionString)){
            var statement = connection.createStatement();
            int resultSet = statement.executeUpdate(sql);

            if (resultSet > 0) {
                System.out.println("Aluno atualizado com sucesso!");
            } else {
                System.out.println("Aluno não encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
        }

    }

    private static void excluir(){

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o ID do aluno que deseja excluir: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM Alunos WHERE id = " + id;

        try (var connection = DriverManager.getConnection(connectionString)){
            var statement = connection.createStatement();
            int resultSet = statement.executeUpdate(sql);

            if (resultSet > 0) {
                System.out.println("Aluno excluído com sucesso!");
            } else {
                System.out.println("Aluno não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
        }

    }
}
