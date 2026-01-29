package br.com.geancesar.eufood.telas.dashboard.listener;

import java.util.List;

import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;

public interface DashboardListener {

    void listaRestaurantes(List<Restaurante> restaurantes);
    void getImagemRestaurante(String imagemBase64, String uuid);
    void getImagemCapaRestaurante(String imagemBase64, String uuid);
    void onItemClick(int adapterPosition);
}
