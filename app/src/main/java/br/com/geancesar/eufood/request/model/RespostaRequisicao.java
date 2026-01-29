package br.com.geancesar.eufood.request.model;

public class RespostaRequisicao {

    private boolean ok;
    private String mensagem;
    private Object extra;

    public boolean isOk() {
        return ok;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Object getExtra() {
        return extra;
    }

    public String getMensagem() {
        return mensagem;
    }
}
