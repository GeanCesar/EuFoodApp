package br.com.geancesar.eufood.telas.cardapio.requests;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.listener.DetalheItemListener;
import br.com.geancesar.eufood.telas.cardapio.model.CategoriaSubItemRest;
import br.com.geancesar.eufood.telas.cardapio.requests.model.RespostaListarCategoriasSubitem;
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

    public RespostaListarCategoriasSubitem executa() {
        url = url.replace("{uuid}", uuidRestaurante);
        url = url.replace("{uuid-item}", uuidItem);

        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 302) {
                Gson gson = new Gson();
                return gson.fromJson(response.body().string(), RespostaListarCategoriasSubitem.class);
            } else {
                RespostaListarCategoriasSubitem resp = new RespostaListarCategoriasSubitem();
                resp.setOk(false);
                return resp;
            }
        } catch (IOException e) {
            RespostaListarCategoriasSubitem resp = new RespostaListarCategoriasSubitem();
            resp.setOk(false);
            resp.setMensagem(e.getMessage());
            return resp;
        }
    }

    public void posExecucao(RespostaListarCategoriasSubitem resp) {
        if(resp != null) {
            List<CategoriaSubItemRest> itens = resp.getExtra();
            if(itens != null) {
                listener.buscarCategoriaSubitem(itens);
            }
        }
    }

}
