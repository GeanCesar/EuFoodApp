package br.com.geancesar.eufood.telas.login.requests;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

public class ConsultarCelularUsuarioTask extends AsyncTask  {

    public static final MediaType mediaType = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();
    String json;
    String url = "http://192.168.15.103:8080/usuario/cadastrar/consultar_telefone";

    LoginUsuarioListener listener;
    Context context;
    ProgressDialog dialog;
    Usuario usuario;
    boolean logar;

    public ConsultarCelularUsuarioTask(Context context, Usuario usuario, ProgressDialog dialog, LoginUsuarioListener listener, boolean logar){
        this.usuario = usuario;
        this.context = context;
        this.dialog = dialog;
        this.listener = listener;
        this.logar = logar;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Gson gson = new Gson();
        json = gson.toJson(usuario);

        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            gson = new Gson();
            RespostaRequisicao resp = gson.fromJson(response.body().string(), RespostaRequisicao.class);
            return resp;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        dialog.dismiss();

        if(o != null) {
            RespostaRequisicao response = (RespostaRequisicao) o;

            if(!response.isOk()) {
                if(logar){
                    Toast.makeText(context, "Telefone não localizado", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.cadastrar(usuario.getTelefone());
            } else {
                if(logar) {
                    listener.logar(usuario.getTelefone());
                    return;
                }
                Toast.makeText(context, "Telefone já útilizado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
