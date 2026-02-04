package br.com.geancesar.eufood.telas.cardapio.listener;

import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;

public interface RestauranteListener {
    void listaItens(List<ItemCardapio> itens);
    void detalheItem(ItemCardapio item);
    void fecharDetalheItem();

}
