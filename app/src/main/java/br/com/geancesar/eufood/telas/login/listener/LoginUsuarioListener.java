package br.com.geancesar.eufood.telas.login.listener;

import br.com.geancesar.eufood.request.model.RespostaRequisicao;

public interface LoginUsuarioListener {

    void avancar(String... valorCampo);
    void cadastrar(String valorCampo);
    void logar(String... valorCampo);

    void cadastradoSucesso(RespostaRequisicao respostaRequisicao);
    void logadoSucesso(RespostaRequisicao respostaRequisicao);


}
