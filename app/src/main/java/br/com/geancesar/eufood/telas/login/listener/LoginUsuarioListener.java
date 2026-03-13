package br.com.geancesar.eufood.telas.login.listener;

public interface LoginUsuarioListener {

    void avancar(String... valorCampo);
    void cadastrar(String valorCampo);
    void logar(String... valorCampo);

    void cadastradoSucesso(String uuid);
    void logadoSucesso(String token);


}
