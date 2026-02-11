package br.com.geancesar.eufood.telas.pedido.listener;

import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoItemRest;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoRest;

public interface DetalhePedidoListener {
    void adicionaItem(CriacaoPedidoItemRest item);
    void removeItem(CriacaoPedidoItemRest item);
    void pedidoCriado(CriacaoPedidoRest pedido);

}
