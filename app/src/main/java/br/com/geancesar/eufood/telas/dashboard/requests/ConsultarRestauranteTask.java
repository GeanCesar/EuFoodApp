package br.com.geancesar.eufood.telas.dashboard.requests;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import br.com.geancesar.eufood.telas.dashboard.listener.PedidosListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ConsultarRestauranteTask extends AsyncTask {
    private final OkHttpClient client = new OkHttpClient();
    private String url = "http://192.168.15.103:8080/restaurante/consultar?uuid-restaurante={uuid}";
    private String tokenUsuario;
    private final PedidosListener listener;
    private String uuidRestaurante;

    public ConsultarRestauranteTask(PedidosListener listener, String tokenUsuario, String uuidRestaurante){
        this.listener = listener;
        setTokenUsuario(tokenUsuario);
        setUuidRestaurante(uuidRestaurante);
    }

    @Override
    protected void onPostExecute(Object o) {
        Restaurante resp = (Restaurante) o;
        if(resp != null) {
            listener.restauranteConsultado(resp);
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        url = url.replace("{uuid}", uuidRestaurante);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .method("GET", null)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 200) {
                Gson gson = new Gson();

                Type type = new TypeToken<Restaurante>() {}.getType();
                return gson.fromJson(response.body().string(), type);
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public void setUuidRestaurante(String uuidRestaurante) {
        this.uuidRestaurante = uuidRestaurante;
    }

    public void setTokenUsuario(String tokenUsuario) {
        this.tokenUsuario = tokenUsuario;
    }
}
