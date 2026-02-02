package br.com.geancesar.eufood.telas.cardapio.requests.model;

import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.model.CategoriaSubItemRest;

public class RespostaListarCategoriasSubitem {

    private boolean ok;
    private String mensagem;
    private List<CategoriaSubItemRest> extra;

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setExtra(List<CategoriaSubItemRest> extra) {
        this.extra = extra;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMensagem() {
        return mensagem;
    }

    public List<CategoriaSubItemRest> getExtra() {
        return extra;
    }
}
