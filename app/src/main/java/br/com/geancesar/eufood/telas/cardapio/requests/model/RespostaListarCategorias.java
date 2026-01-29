package br.com.geancesar.eufood.telas.cardapio.requests.model;

import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.model.CategoriaItemCardapio;

public class RespostaListarCategorias {

    private boolean ok;
    private String mensagem;
    private List<CategoriaItemCardapio> extra;

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setExtra(List<CategoriaItemCardapio> extra) {
        this.extra = extra;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMensagem() {
        return mensagem;
    }

    public List<CategoriaItemCardapio> getExtra() {
        return extra;
    }
}
