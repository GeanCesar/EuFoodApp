package br.com.geancesar.eufood.telas.dashboard.requests;

import com.google.gson.Gson;

import java.io.IOException;
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
    public RespostaListarRestaurantes executa() {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + tokenUsuario)
                .method("GET", null)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if(response.code() == 302) {
                Gson gson = new Gson();
                RespostaListarRestaurantes resp = gson.fromJson(response.body().string(), RespostaListarRestaurantes.class);
                return resp;
            } else {
                RespostaListarRestaurantes resp = new RespostaListarRestaurantes();
                resp.setOk(false);
                return resp;
            }
        } catch (IOException e) {
            RespostaListarRestaurantes resp = new RespostaListarRestaurantes();
            resp.setOk(false);
            resp.setMensagem(e.getMessage());
            return resp;
        }
    }

    /**
     * @param resp
     */
    public void posExecucao(RespostaListarRestaurantes resp) {
        if(resp != null) {
            List<Restaurante> restaurates = resp.getExtra();
            if(restaurates != null) {
                listener.listaRestaurantes(restaurates);
            }
        }
    }

    public void setTokenUsuario(String tokenUsuario) {
        this.tokenUsuario = tokenUsuario;
    }
}
