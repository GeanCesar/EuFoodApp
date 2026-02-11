package br.com.geancesar.eufood.telas.pedido.model;

import java.util.List;

public class CriacaoPedidoRest {

	private List<CriacaoPedidoItemRest> items;

	private String uuidRestaurante;

	private String uuidUsuario;

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

	public List<CriacaoPedidoItemRest> getItems() {
		return items;
	}

	public void setItems(List<CriacaoPedidoItemRest> items) {
		this.items = items;
	}


}
