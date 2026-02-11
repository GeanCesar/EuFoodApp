package br.com.geancesar.eufood.telas.dashboard.listener;

import java.util.List;

import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.telas.dashboard.model.rest.ConsultaPedidoRest;

public interface PedidosListener {
    void pedidoSelecionado(int position);
    void pedidosConsultados(List<ConsultaPedidoRest> pedidos);
    void restauranteConsultado(Restaurante restaurante);
}
