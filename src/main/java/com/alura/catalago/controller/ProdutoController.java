package com.alura.catalago.controller;

import com.alura.catalago.model.Produto;
import com.alura.catalago.model.Categoria;
import com.alura.catalago.repository.ProdutoRepository;
import com.alura.catalago.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Use absolute path or relative to project root for static assets in development. 
    // Usually, in a real app, this is externalized.
    private static final String UPLOAD_DIR = "src/main/resources/static/fotos/";

    @GetMapping
    public String index(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        return "listagem_produto";
    }

    @GetMapping("/adicionar")
    public String adicionar(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formulario_produto";
    }

    @PostMapping("/salvar")
    public String salvar(Produto produto, @RequestParam("categoriaId") Integer categoriaId, 
                         @RequestParam(value = "fotoFile", required = false) MultipartFile fotoFile,
                         @RequestParam("name_foto") String nameFoto) {
        
        Categoria cat = new Categoria();
        cat.setIdcategoria(categoriaId);
        produto.setCategoria(cat);

        if (fotoFile != null && !fotoFile.isEmpty()) {
            try {
                // Ensure directory exists
                File dir = new File(UPLOAD_DIR);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filename = System.currentTimeMillis() + "_" + fotoFile.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + filename);
                Files.write(path, fotoFile.getBytes());
                produto.setFoto(filename);
            } catch (IOException e) {
                e.printStackTrace();
                produto.setFoto(nameFoto);
            }
        } else {
            produto.setFoto(nameFoto);
        }

        produtoRepository.save(produto);
        return "redirect:/produto";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("produto", produtoRepository.findById(id).orElse(null));
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "formulario_produto";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam("id") Integer id) {
        produtoRepository.deleteById(id);
        return "redirect:/produto";
    }
}
