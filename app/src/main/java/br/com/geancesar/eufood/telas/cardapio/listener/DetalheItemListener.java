package br.com.geancesar.eufood.telas.cardapio.listener;

import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.model.CategoriaSubItemRest;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;

public interface DetalheItemListener {

    void buscarCategoriaSubitem(List<CategoriaSubItemRest> categorias);

    void adicionouSubItem(ItemCardapio item);

    void removeuSubItem(ItemCardapio item);

}
