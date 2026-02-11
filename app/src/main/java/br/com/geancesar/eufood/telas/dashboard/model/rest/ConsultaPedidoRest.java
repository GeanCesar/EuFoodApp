package br.com.geancesar.eufood.telas.dashboard.model.rest;

import android.graphics.drawable.Drawable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import kotlin.jvm.Transient;

public class ConsultaPedidoRest {

	private List<ConsultaPedidoItemRest> items;
	private String uuidRestaurante;
	private String uuidUsuario;
	private String uuidPedido;
	private BigDecimal valorTotal;
	private BigDecimal valorFrete;
	private Date dataCriacao;
    @Transient
    private transient String nomeRestaurante;
    @Transient
    private transient Drawable imagemRestauranteBaixada;

	public String getUuidUsuario() {
		return uuidUsuario;
	}

	public void setUuidUsuario(String uuidUsuario) {
		this.uuidUsuario = uuidUsuario;
	}

	public void setUuidRestaurante(String uuidRestaurante) {
		this.uuidRestaurante = uuidRestaurante;
	}

	public String getUuidRestaurante() {
		return uuidRestaurante;
	}

	public List<ConsultaPedidoItemRest> getItems() {
		return items;
	}

	public void setItems(List<ConsultaPedidoItemRest> items) {
		this.items = items;
	}

	public String getUuidPedido() {
		return uuidPedido;
	}

	public void setUuidPedido(String uuidPedido) {
		this.uuidPedido = uuidPedido;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }
    public void setNomeRestaurante(String nomeRestaurante) {
        this.nomeRestaurante = nomeRestaurante;
    }
    public Drawable getImagemRestauranteBaixada() {
        return imagemRestauranteBaixada;
    }
    public void setImagemRestauranteBaixada(Drawable imagemRestauranteBaixada) {
        this.imagemRestauranteBaixada = imagemRestauranteBaixada;
    }
}
