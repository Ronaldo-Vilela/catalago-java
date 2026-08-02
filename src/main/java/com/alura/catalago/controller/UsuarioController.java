package com.alura.catalago.controller;

import com.alura.catalago.model.Usuario;
import com.alura.catalago.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "listagem_usuario";
    }

    @GetMapping("/adicionar")
    public String adicionar() {
        return "formulario_usuario";
    }

    @PostMapping("/salvar")
    public String salvar(Usuario usuario, @RequestParam("confirma_senha") String confirmaSenha, @RequestParam(value = "senha", required = false) String senha, RedirectAttributes redirectAttributes) {
        
        if (senha != null && !senha.isEmpty()) {
            if (!senha.equals(confirmaSenha)) {
                redirectAttributes.addFlashAttribute("erros_validacao", "As senhas não correspondem<br>");
                return "redirect:/usuario/adicionar";
            }
            usuario.setSenha(md5(senha));
        } else {
            if (usuario.getIdusuario() != null) {
                Usuario existente = usuarioRepository.findById(usuario.getIdusuario()).orElse(null);
                if (existente != null) {
                    usuario.setSenha(existente.getSenha());
                }
            } else {
                redirectAttributes.addFlashAttribute("erros_validacao", "O campo senha é obrigatório para novos usuários<br>");
                return "redirect:/usuario/adicionar";
            }
        }
        
        usuarioRepository.save(usuario);
        return "redirect:/usuario";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("usuario", usuarioRepository.findById(id).orElse(null));
        return "formulario_usuario";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam("id") Integer id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuario";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/autenticar")
    public String autenticar(@RequestParam("login") String login, @RequestParam("senha") String senha, HttpSession session, RedirectAttributes redirectAttributes) {
        Optional<Usuario> usuario = usuarioRepository.findByLoginAndSenha(login, md5(senha));
        
        if (usuario.isPresent()) {
            session.setAttribute("usuario_logado", true);
            session.setAttribute("usuario_id", usuario.get().getIdusuario());
            session.setAttribute("usuario_nome", usuario.get().getNome());
            return "redirect:/produto";
        } else {
            redirectAttributes.addFlashAttribute("erro", "Login ou senha incorretos!");
            return "redirect:/usuario/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }
    
    private String md5(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
