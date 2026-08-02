package com.alura.catalago.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idusuario;

    private String nome;

    @Column(name = "nivel_acesso")
    private String nivelAcesso;

    private String login;

    private String senha;
}
