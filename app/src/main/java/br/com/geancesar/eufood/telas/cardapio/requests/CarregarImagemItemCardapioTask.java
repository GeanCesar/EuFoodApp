package br.com.geancesar.eufood.telas.cardapio.requests;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.geancesar.eufood.telas.cardapio.listener.RestauranteListener;
import br.com.geancesar.eufood.request.model.RespostaRequisicao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Task responsavel por buscar a imagem no servidor, descriptografar e enviar ao listener
 */
public class CarregarImagemItemCardapioTask  {
    private OkHttpClient client = new OkHttpClient();
    private String url = "http://192.168.15.103:8080/restaurante/item_cardapio/imagem_perfil?uuid-item-cardapio={uuid}";
    private final RestauranteListener listener;
    private final String uuid;
    private final String tokenUsuario;

    /**
     * @param listener
     * @param uuid
     * @param tokenUsuario
     */
    public CarregarImagemItemCardapioTask(RestauranteListener listener, String uuid, String tokenUsuario){
        this.listener = listener;
        this.uuid = uuid;
        this.tokenUsuario = tokenUsuario;
    }

    /**
     * @return
     */
    public RespostaRequisicao executa() {
        url = url.replace("{uuid}", uuid);
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 200) {
                Gson gson = new Gson();
                RespostaRequisicao resp = gson.fromJson(response.body().string(), RespostaRequisicao.class);
                return resp;
            } else {
                RespostaRequisicao resp = new RespostaRequisicao();
                resp.setOk(false);
                return resp;
            }
        } catch (IOException e) {
            RespostaRequisicao resp = new RespostaRequisicao();
            resp.setOk(false);
            resp.setMensagem(e.getMessage());
            return resp;
        }
    }

    /**
     * @param resp
     */
    public void posExecucao(RespostaRequisicao resp) {
        if(resp != null) {
            listener.getImagemItem((String) resp.getExtra(), uuid);
        }
    }
}
