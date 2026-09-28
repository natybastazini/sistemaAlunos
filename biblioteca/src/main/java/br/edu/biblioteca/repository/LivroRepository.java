package br.edu.biblioteca.repository;

import br.edu.biblioteca.model.Livro;
import br.edu.biblioteca.model.StatusLivro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class LivroRepository {

    private final List<Livro> livros = new ArrayList<>();

    private Long contador = 1L;
    public List<Livro> listarTodos() {
        return livros;
    }

    public void salvar(Livro livro) {
        livro.setId(contador++);
        livros.add(livro);
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livros.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst();
    }

    public List<Livro> listarDisponiveis() {

        return livros.stream().filter(l -> l.getStatus() == StatusLivro.DISPONIVEL).toList();
    }


    // função de teste
//    public Optional<Livro> buscarPorTitulo(String titulo) {

//        return livros.stream().filter(l -> l.getTitulo().equals(titulo)).findFirst();

//    }


}
