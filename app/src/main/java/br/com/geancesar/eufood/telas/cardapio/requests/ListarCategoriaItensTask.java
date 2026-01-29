package br.com.geancesar.eufood.telas.cardapio.requests;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.geancesar.eufood.telas.cardapio.requests.model.RespostaListarCategorias;
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

    public RespostaListarCategorias executa() {
        url = url.replace("{uuid}", uuidRestaurante);
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 302) {
                Gson gson = new Gson();
                return gson.fromJson(response.body().string(), RespostaListarCategorias.class);
            } else {
                RespostaListarCategorias resp = new RespostaListarCategorias();
                resp.setOk(false);
                return resp;
            }
        } catch (IOException e) {
            RespostaListarCategorias resp = new RespostaListarCategorias();
            resp.setOk(false);
            resp.setMensagem(e.getMessage());
            return resp;
        }
    }

}
