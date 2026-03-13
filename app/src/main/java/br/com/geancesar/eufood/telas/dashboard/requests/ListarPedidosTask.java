package br.com.geancesar.eufood.telas.dashboard.requests;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import br.com.geancesar.eufood.telas.dashboard.listener.PedidosListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import br.com.geancesar.eufood.telas.dashboard.model.rest.ConsultaPedidoRest;
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
            if(response.code() == 200) {
                Gson gson = new Gson();

                Type type = new TypeToken<List<ConsultaPedidoRest>>() {}.getType();
                return gson.fromJson(response.body().string(), type);
            } else {
               return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        if(o != null) {
            List<ConsultaPedidoRest> pedidos =  (List<ConsultaPedidoRest>) o;
            if(pedidos != null) {
                listener.pedidosConsultados(pedidos);
            }
        }
    }
}
