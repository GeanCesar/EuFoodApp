package br.com.geancesar.eufood.telas.cardapio.requests.model;

import java.util.List;

import br.com.geancesar.eufood.telas.cardapio.model.ItemCardapio;

public class RespostaListarItensRestaurante {

    private boolean ok;
    private String mensagem;
    private List<ItemCardapio> extra;

    public String getMensagem() {
        return mensagem;
    }

    public List<ItemCardapio> getExtra() {
        return extra;
    }

    public boolean isOk() {
        return ok;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setExtra(List<ItemCardapio> extra) {
        this.extra = extra;
    }
}
