package com.alura.catalago.controller;

import com.alura.catalago.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/loja"})
public class LojaController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "vitrine";
    }
}
