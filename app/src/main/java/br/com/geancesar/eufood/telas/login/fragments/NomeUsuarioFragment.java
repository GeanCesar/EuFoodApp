package br.com.geancesar.eufood.telas.login.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.login.listener.LoginUsuarioListener;

public class NomeUsuarioFragment extends Fragment {

    Button btAvancar;

    LoginUsuarioListener listener;

    EditText etNome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nome, container, false);

        btAvancar = view.findViewById(R.id.btAvancar);
        etNome = view.findViewById(R.id.etNomeUsuario);

        btAvancar.setOnClickListener(l-> listener.avancar(etNome.getText().toString()));

        return view;

    }

    public void setListener(LoginUsuarioListener listener) {
        this.listener = listener;
    }
}
