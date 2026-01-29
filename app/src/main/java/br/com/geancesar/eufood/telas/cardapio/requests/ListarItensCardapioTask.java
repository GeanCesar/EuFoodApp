package br.com.geancesar.eufood.telas.cardapio.requests;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
import br.com.geancesar.eufood.telas.cardapio.requests.model.RespostaListarItensRestaurante;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListarItensCardapioTask {
    OkHttpClient client = new OkHttpClient();
    String url = "http://192.168.15.103:8080/restaurante/item_cardapio/listar?uuid-restaurante={uuid-restaurante}";

    RestauranteListener listener;

    String uuidRestaurante;

    String tokenUsuario;

    public ListarItensCardapioTask(RestauranteListener listener, String uuidRestaurante, String tokenUsuario){
        this.listener = listener;
        this.uuidRestaurante = uuidRestaurante;
        this.tokenUsuario = tokenUsuario;
    }

    public RespostaListarItensRestaurante executa() {
        url = url.replace("{uuid-restaurante}", uuidRestaurante);
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 302) {
                Gson gson = new Gson();
                return gson.fromJson(response.body().string(), RespostaListarItensRestaurante.class);
            } else {
                RespostaListarItensRestaurante resp = new RespostaListarItensRestaurante();
                resp.setOk(false);
                return resp;
            }
        } catch (IOException e) {
            RespostaListarItensRestaurante resp = new RespostaListarItensRestaurante();
            resp.setOk(false);
            resp.setMensagem(e.getMessage());
            return resp;
        }
    }

    public void posExecucao(RespostaListarItensRestaurante resp) {
        if(resp != null) {
            List<ItemCardapio> itens = resp.getExtra();
            if(itens != null) {
                listener.listaItens(itens);
            }
        }
    }
}
