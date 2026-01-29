package br.com.geancesar.eufood.telas.cardapio.listener;

import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;

public interface RestauranteListener {

    void getImagemItem(String imagemBase64, String uuid);

    void listaItens(List<ItemCardapio> itens);

}
