package br.com.geancesar.eufood.telas.dashboard.requests.model;

import br.com.geancesar.eufood.telas.dashboard.model.Restaurante;

public class RespostaConsultarRestaurante {

    private boolean ok;
    private String mensagem;
    private Restaurante extra;

    public String getMensagem() {
        return mensagem;
    }

    public Restaurante getExtra() {
        return extra;
    }

    public boolean isOk() {
        return ok;
    }

    public void setExtra(Restaurante extra) {
        this.extra = extra;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
