package br.com.geancesar.eufood.telas.dashboard.requests.model;

import java.util.List;

import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;

public class RespostaListarRestaurantes {

    private boolean ok;
    private String mensagem;
    private List<Restaurante> extra;

    public String getMensagem() {
        return mensagem;
    }

    public List<Restaurante> getExtra() {
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

    public void setExtra(List<Restaurante> extra) {
        this.extra = extra;
    }
}
