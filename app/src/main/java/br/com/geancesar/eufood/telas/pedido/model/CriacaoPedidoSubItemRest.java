package br.com.geancesar.eufood.telas.pedido.model;

import java.math.BigDecimal;

import kotlin.jvm.Transient;

public class CriacaoPedidoSubItemRest {

	private String uuid;
	private BigDecimal quantidade;

    @Transient
    private transient BigDecimal valorTotal;
    @Transient
    private transient String nome;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
