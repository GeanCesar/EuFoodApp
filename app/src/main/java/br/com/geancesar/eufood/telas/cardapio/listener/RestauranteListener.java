package br.com.geancesar.eufood.telas.cardapio.listener;

import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoItemRest;

public interface RestauranteListener {
    void listaItens(List<ItemCardapio> itens);
    void detalheItem(ItemCardapio item);
    void fecharDetalheItem();
    void fecharSacola();
    void adicionarItemSacola(CriacaoPedidoItemRest item);
    void pedidoGerado();

}
