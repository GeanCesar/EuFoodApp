package br.com.geancesar.eufood.telas.login.requests;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.geancesar.eufood.request.model.RespostaRequisicao;
import br.com.geancesar.eufood.telas.login.listener.LoginUsuarioListener;
import br.com.geancesar.eufood.telas.login.model.Usuario;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Classe responsavel por comunicar com o servidor e efetuar o login retornando o token
 */
public class LoginUsuarioTask  {
    private MediaType mediaType = MediaType.get("application/json");
    private OkHttpClient client = new OkHttpClient();
    private String json;
    private String url = "http://192.168.15.103:8080/usuario_login/login";
    private ProgressDialog dialog;
    private LoginUsuarioListener listener;
    Usuario usuario;

    public LoginUsuarioTask(Usuario usuario, ProgressDialog dialog, LoginUsuarioListener listener){
        this.usuario = usuario;
        Gson gson = new Gson();
        json = gson.toJson(usuario);

        this.dialog = dialog;
        this.listener = listener;
    }

    public RespostaRequisicao executa() {
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            return gson.fromJson(response.body().string(), RespostaRequisicao.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void posExecucao(RespostaRequisicao resp) {
        dialog.dismiss();
        if(resp == null || !resp.isOk()) {
            Toast.makeText(dialog.getContext(), "Telefone / senha incorretos", Toast.LENGTH_SHORT).show();
        } else {
            listener.logadoSucesso(resp);
        }
    }
}
