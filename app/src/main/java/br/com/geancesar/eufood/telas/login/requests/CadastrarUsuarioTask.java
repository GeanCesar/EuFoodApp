package br.com.geancesar.eufood.telas.login.requests;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.geancesar.eufood.telas.login.listener.LoginUsuarioListener;
import br.com.geancesar.eufood.telas.login.model.Usuario;
import br.com.geancesar.eufood.request.model.RespostaRequisicao;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CadastrarUsuarioTask extends AsyncTask  {

    public static final MediaType mediaType = MediaType.get("application/json");
    OkHttpClient client = new OkHttpClient();
    String json;
    String url = "http://192.168.15.103:8080/usuario/cadastrar";

    ProgressDialog dialog;
    LoginUsuarioListener listener;

    public CadastrarUsuarioTask(Usuario usuario, ProgressDialog dialog, LoginUsuarioListener listener){
        Gson gson = new Gson();
        json = gson.toJson(usuario);

        this.dialog = dialog;
        this.listener = listener;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            RespostaRequisicao resp = new RespostaRequisicao();
            resp.setOk(response.code() == 201);
            resp.setMensagem(response.body().string());
            return resp;
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
        dialog.dismiss();

        RespostaRequisicao resp = (RespostaRequisicao) o;
        if(!resp.isOk()) {
            Toast.makeText(dialog.getContext(), resp.getMensagem(), Toast.LENGTH_SHORT).show();
        } else {
            listener.cadastradoSucesso(resp);
        }
    }
}
