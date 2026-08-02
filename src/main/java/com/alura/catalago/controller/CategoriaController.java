package com.alura.catalago.controller;

import com.alura.catalago.model.Categoria;
import com.alura.catalago.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "listagem_categoria";
    }

    @GetMapping("/adicionar")
    public String adicionar() {
        return "formulario_categoria";
    }

    @PostMapping("/salvar")
    public String salvar(Categoria categoria) {
        categoriaRepository.save(categoria);
        return "redirect:/categoria";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("categoria", categoriaRepository.findById(id).orElse(null));
        return "formulario_categoria";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam("id") Integer id) {
        categoriaRepository.deleteById(id);
        return "redirect:/categoria";
    }
}
