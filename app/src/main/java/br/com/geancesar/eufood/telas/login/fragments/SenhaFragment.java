package br.com.geancesar.eufood.telas.login.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.login.listener.LoginUsuarioListener;

public class SenhaFragment extends Fragment {

    LoginUsuarioListener listener;

    EditText etSenha;
    EditText etSenha2;

    Button btCadastrar;
    TextView tvVermelho;
    TextView tvAmarelo;
    TextView tvVerde;

    boolean logando = false;

    LinearLayout llForcaSenha;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_senha, container, false);

        llForcaSenha = view.findViewById(R.id.llForcaSenha);
        btCadastrar = view.findViewById(R.id.btConcluirCadastro);
        etSenha = view.findViewById(R.id.etSenha);

        tvVermelho = view.findViewById(R.id.tvVermelhoForca);
        tvAmarelo = view.findViewById(R.id.tvAmareloForca);
        tvVerde = view.findViewById(R.id.tvVerdeForca);

        btCadastrar.setOnClickListener(l -> listener.avancar(etSenha.getText().toString()));

        etSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int forca = validaForcaSenha(etSenha.getText().toString());
                mostraForca(forca);
            }
        });

        if(logando) {
            llForcaSenha.setVisibility(View.GONE);
            btCadastrar.setText("Entrar");
        } else {
            llForcaSenha.setVisibility(View.VISIBLE);
            btCadastrar.setText("Cadastrar");
        }

        return view;
    }

    private void mostraForca (int forca) {
        tvVermelho.setAlpha(0.2F);
        tvAmarelo.setAlpha(0.2F);
        tvVerde.setAlpha(0.2F);

        if(forca >= 1) {
            tvVermelho.setAlpha(1);
        }
        if(forca >= 2) {
            tvAmarelo.setAlpha(1);
        }
        if(forca >= 3) {
            tvVerde.setAlpha(1);
        }
    }

    private int validaForcaSenha(String senha) {
        int forca = 0;

        if(senha.isEmpty()) {
            return forca;
        }

        if(senha.length() > 7){
            forca++;
        }

        if(!StringUtils.getDigits(senha).isEmpty()){
            forca++;
        }

        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(senha);
        if (m.find()) {
            forca++;
        }

        return forca;
    }

    public void setListener(LoginUsuarioListener listener) {
        this.listener = listener;
    }

    public void setLogando(boolean logando) {
        this.logando = logando;
    }
}
