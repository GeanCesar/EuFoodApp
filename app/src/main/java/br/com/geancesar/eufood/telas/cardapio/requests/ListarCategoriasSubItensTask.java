package br.com.geancesar.eufood.telas.cardapio.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.listener.DetalheItemListener;
import br.com.geancesar.eufood.telas.cardapio.model.CategoriaSubItemRest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListarCategoriasSubItensTask {
    private OkHttpClient client = new OkHttpClient();
    private String url = "http://192.168.15.103:8080/restaurante/categoria/listar/item?uuid-restaurante={uuid}&uuid-item={uuid-item}";

    private String uuidRestaurante;
    private String tokenUsuario;
    private String uuidItem;
    private DetalheItemListener listener;

    public ListarCategoriasSubItensTask(String uuidRestaurante, String uuidItem, String tokenUsuario, DetalheItemListener listener) {
        this.uuidRestaurante = uuidRestaurante;
        this.tokenUsuario = tokenUsuario;
        this.uuidItem = uuidItem;
        this.listener = listener;
    }

    public List<CategoriaSubItemRest> executa() {
        url = url.replace("{uuid}", uuidRestaurante);
        url = url.replace("{uuid-item}", uuidItem);

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 200) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CategoriaSubItemRest>>() {}.getType();
                return gson.fromJson(response.body().string(), listType);
            }
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return null;
    }

    public void posExecucao(List<CategoriaSubItemRest> resp) {
        if(resp != null) {
            listener.buscarCategoriaSubitem(resp);
        }
    }

}
