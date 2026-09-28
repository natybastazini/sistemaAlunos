package br.edu.biblioteca.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Livro {

    private Long id;

    @NotBlank(message = "Informe o título")
    @Size(min = 3, max = 100, message = "O título deve possuir entre 3 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "Informe o autor")
    @Size(min = 3, max = 100, message = "O autor deve possuir entre 3 e 100 caracteres")
    private String autor;

    @NotBlank(message = "Informe o isbn")
    private String isbn;

    @NotNull(message = "Informe o ano de Publicação")
    private Integer anoPublicacao;

    @NotNull(message = "Informe a categoria")
    private Categoria categoria;

    private StatusLivro status;

    public Livro() {
        this.status = StatusLivro.DISPONIVEL;
    }

    public void emprestar() {

        if (status == StatusLivro.DISPONIVEL){
            status = StatusLivro.EMPRESTADO;
        }
    }

    public void devolver() {
        if (status == StatusLivro.EMPRESTADO){
            status = StatusLivro.DISPONIVEL;
        }
    }



}
