package br.com.geancesar.eufood;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.geancesar.eufood.databinding.ActivityMainBinding;
import br.com.geancesar.eufood.request.model.RespostaRequisicao;
import br.com.geancesar.eufood.telas.dashboard.PrincipalActivity;
import br.com.geancesar.eufood.telas.login.fragments.CelularFragment;
import br.com.geancesar.eufood.telas.login.fragments.NomeUsuarioFragment;
import br.com.geancesar.eufood.telas.login.fragments.SenhaFragment;
import br.com.geancesar.eufood.telas.login.listener.LoginUsuarioListener;
import br.com.geancesar.eufood.telas.login.model.Usuario;
import br.com.geancesar.eufood.telas.login.requests.CadastrarUsuarioTask;
import br.com.geancesar.eufood.telas.login.requests.LoginUsuarioTask;

public class MainActivity extends AppCompatActivity implements LoginUsuarioListener {

    private ActivityMainBinding binding;
    private int telaAtualCadastro = 0;
    Usuario usuarioCadastro = new Usuario();

    private boolean cadastrando = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        verificaLogin();
    }

    private void verificaLogin() {
        AccountManager manager = AccountManager.get(this);
        Account[] contas = manager.getAccounts();
        for (Account conta : contas) {
            usuarioCadastro.setTelefone(conta.name);
            usuarioCadastro.setSenha(manager.getPassword(conta));
            efetuarLogin();
            return;
        }
        avancar("");
    }

    @SuppressLint("ResourceType")
    @Override
    public void avancar(String... valorCampo) {
        telaAtualCadastro++;
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if(cadastrando) {
            if(telaAtualCadastro == 1) {
                CelularFragment fragment = new CelularFragment();
                fragment.setListener(this);
                transaction.replace(R.id.fvCamposLogin,fragment,"Celular");
            } else if(telaAtualCadastro == 2) {
                NomeUsuarioFragment fragment = new NomeUsuarioFragment();
                fragment.setListener(this);
                usuarioCadastro.setTelefone(valorCampo[0]);
                transaction.replace(R.id.fvCamposLogin,fragment,"Nome");
            } else if(telaAtualCadastro == 3) {
                SenhaFragment fragment = new SenhaFragment();
                fragment.setListener(this);
                usuarioCadastro.setNome(valorCampo[0]);
                transaction.replace(R.id.fvCamposLogin,fragment,"Senha");
            } else {
                usuarioCadastro.setSenha(valorCampo[0]);
                efetuarCadastro();
            }
        } else {
            if(telaAtualCadastro == 1) {
                CelularFragment fragment = new CelularFragment();
                fragment.setListener(this);
                transaction.replace(R.id.fvCamposLogin,fragment,"Celular");
            } else if(telaAtualCadastro == 2) {
                SenhaFragment fragment = new SenhaFragment();
                fragment.setListener(this);
                fragment.setLogando(true);
                usuarioCadastro.setTelefone(valorCampo[0]);
                usuarioCadastro.setUuid(valorCampo[1]);
                transaction.replace(R.id.fvCamposLogin,fragment,"Senha");
            } else {
                usuarioCadastro.setSenha(valorCampo[0]);
                efetuarLogin();
            }
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void cadastrar(String valorCampo) {
        cadastrando = true;
        avancar(valorCampo);
    }

    @Override
    public void logar(String... valorCampo) {
        cadastrando = false;
        avancar(valorCampo);
    }

    @Override
    public void cadastradoSucesso(RespostaRequisicao respostaRequisicao) {
        Toast.makeText(this, "Cadastrado com sucesso!", Toast.LENGTH_LONG).show();
        efetuarLogin();
    }

    @Override
    public void logadoSucesso(RespostaRequisicao respostaRequisicao, String uuid) {
        AccountManager manager = AccountManager.get(this);

        Account conta = null;
        if(manager.getAccounts().length > 0) {
            conta = manager.getAccounts()[0];
        } else {
            conta = new Account(usuarioCadastro.getTelefone(), "EuFood");
        }

        usuarioCadastro.setUuid(uuid);

        manager.addAccountExplicitly(conta, usuarioCadastro.getSenha(), null);
        manager.setUserData(conta, "uuid", usuarioCadastro.getUuid());
        manager.setAuthToken(conta, "token", (String) respostaRequisicao.getExtra());

        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    public void efetuarCadastro() {
        ProgressDialog dialog = ProgressDialog.show(this, "", "Efetuando cadastro...", true);

        CadastrarUsuarioTask task = new CadastrarUsuarioTask(usuarioCadastro, dialog, this);
        task.execute();
    }

    public void efetuarLogin() {
        ProgressDialog dialog = ProgressDialog.show(this, "", "Efetuando login...", true);

        LoginUsuarioTask task = new LoginUsuarioTask(usuarioCadastro, dialog, this);
        try (ExecutorService executor = Executors.newSingleThreadExecutor()) {
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                RespostaRequisicao resp = task.executa();
                handler.post(() -> task.posExecucao(resp));
            });
        }
    }
}