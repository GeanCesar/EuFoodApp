package br.com.geancesar.eufood.telas.dashboard.requests;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.geancesar.eufood.telas.dashboard.listener.PedidosListener;
import br.com.geancesar.eufood.telas.dashboard.model.rest.ConsultaPedidoRest;
import br.com.geancesar.eufood.telas.dashboard.requests.model.RespostaListarPedidos;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListarPedidosTask extends AsyncTask {

    String tokenUsuario;
    private final OkHttpClient client = new OkHttpClient();
    private String url = "http://192.168.15.103:8080/pedido/listar";

    PedidosListener listener;

    public ListarPedidosTask(String tokenUsuario, PedidosListener listener) {
        this.tokenUsuario = tokenUsuario;
        this.listener = listener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .method("GET", null)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 302) {
                Gson gson = new Gson();
                RespostaListarPedidos resp = gson.fromJson(response.body().string(), RespostaListarPedidos.class);
                return resp;
            } else {
                RespostaListarPedidos resp = new RespostaListarPedidos();
                resp.setOk(false);
                return resp;
            }
        } catch (IOException e) {
            RespostaListarPedidos resp = new RespostaListarPedidos();
            resp.setOk(false);
            resp.setMensagem(e.getMessage());
            return resp;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        RespostaListarPedidos resp = (RespostaListarPedidos) o;
        if(resp != null) {
            List<ConsultaPedidoRest> pedidos = resp.getExtra();
            if(pedidos != null) {
                listener.pedidosConsultados(pedidos);
            }
        }
    }
}
