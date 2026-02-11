package br.com.geancesar.eufood.telas.pedido.requests;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.geancesar.eufood.request.model.RespostaRequisicao;
import br.com.geancesar.eufood.telas.pedido.listener.DetalhePedidoListener;
import br.com.geancesar.eufood.telas.pedido.model.CriacaoPedidoRest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CriarPedidoTask extends AsyncTask {
    private OkHttpClient client = new OkHttpClient();
    private String url = "http://192.168.15.103:8080/pedido/criar";
    private CriacaoPedidoRest pedido;

    private String tokenUsuario;

    DetalhePedidoListener listener;

    public CriarPedidoTask(CriacaoPedidoRest pedido, String tokenUsuario, DetalhePedidoListener listener) {
        this.pedido = pedido;
        this.tokenUsuario = tokenUsuario;
        this.listener = listener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Gson gson = new Gson();
        String json = gson.toJson(pedido);

        Request request = new Request.Builder()
                .url(url)
                .method("POST", RequestBody.create(json, MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 201) {
                return gson.fromJson(response.body().string(), RespostaRequisicao.class);
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

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        RespostaRequisicao resp = ((RespostaRequisicao) o);
        if(resp.isOk() && listener != null) {
            listener.pedidoCriado(pedido);
        }
    }
}
