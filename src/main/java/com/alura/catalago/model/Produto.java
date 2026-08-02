package com.alura.catalago.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idproduto;

    private String nome;

    private String descricao;

    private String marca;

    private BigDecimal preco;

    private String foto;

    @ManyToOne
    @JoinColumn(name = "categoria_idcategoria")
    private Categoria categoria;
}
