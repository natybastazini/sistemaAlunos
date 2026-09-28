package br.edu.biblioteca.controller;

import br.edu.biblioteca.model.Categoria;
import br.edu.biblioteca.model.Livro;
import br.edu.biblioteca.repository.LivroRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/livros")
public class LivroController {

    private final LivroRepository repository;

    public LivroController(LivroRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", repository.listarTodos());
        return "livros/lista";
    }

    @GetMapping("/novo")
    public String criarLivro(Model model) {

        model.addAttribute("livro", new Livro());
        model.addAttribute("categorias", Categoria.values());

        return "livros/formulario";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("livro") Livro livro, BindingResult result, Model model){

        if (result.hasErrors()) {
            model.addAttribute("categorias", Categoria.values());
            return "livros/formulario";
        }

        repository.salvar(livro);
        return "redirect:/livros";

    }

    @GetMapping("/{id}")
    public String listarId (@PathVariable Long id, Model model){

        model.addAttribute("livroId", repository.buscarPorId(id).orElseThrow());

        return "livros/detalhes";

    }

    @GetMapping("/disponiveis")
    public String listarDisponiveis (Model model) {

        model.addAttribute("livros", repository.listarDisponiveis());

        return "livros/lista";
    }


    @PostMapping("/{id}/emprestar")
    public String emprestar(@PathVariable Long id) {
        Livro livro = repository.buscarPorId(id).orElseThrow();
        livro.emprestar();
        return "redirect:/livros";
    }

    @PostMapping("/{id}/devolver")
    public String devolver(@PathVariable Long id) {
        Livro livro = repository.buscarPorId(id).orElseThrow();
        livro.devolver();
        return "redirect:/livros";
    }

}
