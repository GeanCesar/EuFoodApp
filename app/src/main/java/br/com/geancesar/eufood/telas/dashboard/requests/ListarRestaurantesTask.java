package br.com.geancesar.eufood.telas.dashboard.requests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import br.com.geancesar.eufood.telas.dashboard.listener.DashboardListener;
import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Classe responsavel por buscar os restaurantes no servidor
 */
public class ListarRestaurantesTask   {
    private final OkHttpClient client = new OkHttpClient();
    private String url = "http://192.168.15.103:8080/restaurante/listar";
    private String tokenUsuario;
    private final DashboardListener listener;

    /**
     * @param listener
     * @param tokenUsuario
     */
    public ListarRestaurantesTask(DashboardListener listener, String tokenUsuario){
        this.listener = listener;
        setTokenUsuario(tokenUsuario);
    }

    /**
     * @return
     */
    public List<Restaurante> executa() {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .method("GET", null)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 200) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Restaurante>>() {}.getType();
                List<Restaurante> resp = gson.fromJson(response.body().string(), listType);
                return resp;
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * @param resp
     */
    public void posExecucao(List<Restaurante> resp) {
        if(resp != null) {
            listener.listaRestaurantes(resp);
        }
    }

    public void setTokenUsuario(String tokenUsuario) {
        this.tokenUsuario = tokenUsuario;
    }
}
