package br.com.geancesar.eufood.telas.cardapio.model;

import java.math.BigDecimal;

import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import kotlin.jvm.Transient;

public class ItemCardapio {

    private String uuid;
    private Restaurante restaurante;

    private String nome;

    private BigDecimal valor;

    private String descricao;
    private int quantidadeMinimaSubItem;
    private String imagem;

    private CategoriaItemCardapio categoria;

    @Transient
    private String imagemBaixada;

    private boolean buscouImagem;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidadeMinimaSubItem() {
        return quantidadeMinimaSubItem;
    }

    public void setQuantidadeMinimaSubItem(int quantidadeMinimaSubItem) {
        this.quantidadeMinimaSubItem = quantidadeMinimaSubItem;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public boolean isBuscouImagem() {
        return buscouImagem;
    }

    public void setBuscouImagem(boolean buscouImagem) {
        this.buscouImagem = buscouImagem;
    }

    public String getImagemBaixada() {
        return imagemBaixada;
    }

    public void setImagemBaixada(String imagemBaixada) {
        this.imagemBaixada = imagemBaixada;
    }

    public void setCategoria(CategoriaItemCardapio categoria) {
        this.categoria = categoria;
    }

    public CategoriaItemCardapio getCategoria() {
        return categoria;
    }
}
