package br.com.geancesar.eufood.telas.cardapio.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.model.CategoriaItemCardapio;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListarCategoriaItensTask {
    private OkHttpClient client = new OkHttpClient();
    private String url = "http://192.168.15.103:8080/restaurante/categoria/listar?uuid-restaurante={uuid}";

    private String uuidRestaurante;
    private String tokenUsuario;

    public ListarCategoriaItensTask(String uuidRestaurante, String tokenUsuario) {
        this.uuidRestaurante = uuidRestaurante;
        this.tokenUsuario = tokenUsuario;
    }

    public List<CategoriaItemCardapio> executa() {
        url = url.replace("{uuid}", uuidRestaurante);
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 200) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CategoriaItemCardapio>>() {}.getType();
                return gson.fromJson(response.body().string(), listType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
