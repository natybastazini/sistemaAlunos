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

        String sql = "INSERT INTO Alunos (nome, email, idade)";
        sql += " VALUES ('José', 'jose@teste.com', 40)";

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
}
