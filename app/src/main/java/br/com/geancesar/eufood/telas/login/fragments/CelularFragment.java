package br.com.geancesar.eufood.telas.login.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.com.geancesar.eufood.R;
import br.com.geancesar.eufood.telas.login.listener.LoginUsuarioListener;
import br.com.geancesar.eufood.telas.login.model.Usuario;
import br.com.geancesar.eufood.telas.login.requests.ConsultarCelularUsuarioTask;

public class CelularFragment extends Fragment {

    EditText etTelefone;

    Button btCadastrar;

    Button btLogin;

    LoginUsuarioListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_celular, container, false);

        etTelefone = view.findViewById(R.id.etCelular);
        btCadastrar = view.findViewById(R.id.btCadastrar);
        btLogin = view.findViewById(R.id.btEntrar);

        new Thread(() -> etTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("BR"))).start();

        btCadastrar.setOnClickListener(l-> {
            ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Consultando...", true);

            Usuario usuario = new Usuario();
            usuario.setTelefone(etTelefone.getText().toString());
            ConsultarCelularUsuarioTask task = new ConsultarCelularUsuarioTask(getActivity(), usuario, dialog, listener, false);
            task.execute();
        });

        btLogin.setOnClickListener( l -> {
            ProgressDialog dialog = ProgressDialog.show(getActivity(), "", "Consultando...", true);

            Usuario usuario = new Usuario();
            usuario.setTelefone(etTelefone.getText().toString());
            ConsultarCelularUsuarioTask task = new ConsultarCelularUsuarioTask(getActivity(), usuario, dialog, listener, true);
            task.execute();
        });
        return view;
    }

    public void setListener(LoginUsuarioListener listener) {
        this.listener = listener;
    }
}
