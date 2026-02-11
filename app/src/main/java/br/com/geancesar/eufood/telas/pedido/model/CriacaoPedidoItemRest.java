package br.com.geancesar.eufood.telas.pedido.model;

import android.graphics.drawable.Drawable;

import java.math.BigDecimal;
import java.util.List;

import kotlin.jvm.Transient;

public class CriacaoPedidoItemRest {

	private String uuid;
	private BigDecimal quantidade;
	private List<CriacaoPedidoSubItemRest> subItems;

    @Transient
    private transient BigDecimal preco;
    @Transient
    private transient BigDecimal valorTotal;
    @Transient
    private transient String descricao;
    @Transient
    private transient String nome;
    @Transient
    private transient Drawable imagemBaixada;

	public String getUuid() {
		return uuid;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<CriacaoPedidoSubItemRest> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<CriacaoPedidoSubItemRest> subItems) {
		this.subItems = subItems;
	}

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Drawable getImagemBaixada() {
        return imagemBaixada;
    }

    public void setImagemBaixada(Drawable imagemBaixada) {
        this.imagemBaixada = imagemBaixada;
    }
}
