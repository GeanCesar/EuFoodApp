package br.com.geancesar.eufood.telas.dashboard.requests.model;

import java.util.List;

import br.com.geancesar.eufood.telas.dashboard.model.rest.ConsultaPedidoRest;

public class RespostaListarPedidos {

    private String mensagem;
    private List<ConsultaPedidoRest> extra;
    private boolean ok;

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<ConsultaPedidoRest> getExtra() {
        return extra;
    }

    public void setExtra(List<ConsultaPedidoRest> extra) {
        this.extra = extra;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
