package br.com.geancesar.eufood.telas.dashboard.requests;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.geancesar.eufood.telas.dashboard.list_item.ListItemRestauranteAdapter;
import br.com.geancesar.eufood.telas.dashboard.listener.DashboardListener;
import br.com.geancesar.eufood.request.model.RespostaRequisicao;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CarregarImagemCapaRestauranteTask extends AsyncTask  {
    OkHttpClient client = new OkHttpClient();
    String url = "http://192.168.15.103:8080/restaurante/imagem_capa?uuid-restaurante={uuid}";

    DashboardListener listener;

    String uuid;

    ListItemRestauranteAdapter.ViewHolder holder;

    String tokenUsuario;

    public CarregarImagemCapaRestauranteTask(DashboardListener listener, String uuid, ListItemRestauranteAdapter.ViewHolder holder, String tokenUsuario){
        this.listener = listener;
        this.uuid = uuid;
        this.holder = holder;
        this.tokenUsuario = tokenUsuario;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        url = url.replace("{uuid}", uuid);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .method("GET", null)
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

    @Override
    protected void onPostExecute(Object o) {
        if(o != null) {
            RespostaRequisicao resp = (RespostaRequisicao) o;
            listener.getImagemCapaRestaurante((String) resp.getExtra(), uuid);
        }
    }
}
