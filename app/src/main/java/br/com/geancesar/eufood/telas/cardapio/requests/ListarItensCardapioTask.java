package br.com.geancesar.eufood.telas.cardapio.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;
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

    public List<ItemCardapio> executa() {
        url = url.replace("{uuid-restaurante}", uuidRestaurante);
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 200) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<ItemCardapio>>() {}.getType();
                return gson.fromJson(response.body().string(), listType);
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public void posExecucao(List<ItemCardapio> resp) {
        if(resp != null) {
            listener.listaItens(resp);
        }
    }
}
