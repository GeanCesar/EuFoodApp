package br.com.geancesar.eufood.telas.dashboard.model;

import java.io.Serializable;

import kotlin.jvm.Transient;

public class Restaurante implements Serializable {

	private String uuid;

	private String nome;

	private String categoria;

	private String imagemPerfil;

    private String imagemBaixada;
    @Transient
    private boolean buscouImagem;
    private String imagemCapa;
    private String imagemCapaBaixada;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public String getImagemBaixada() {
        return imagemBaixada;
    }

    public void setImagemBaixada(String imagemBaixada) {
        this.imagemBaixada = imagemBaixada;
    }

    public boolean isBuscouImagem() {
        return buscouImagem;
    }

    public void setBuscouImagem(boolean buscouImagem) {
        this.buscouImagem = buscouImagem;
    }

    public String getImagemCapa() {
        return imagemCapa;
    }

    public void setImagemCapa(String imagemCapa) {
        this.imagemCapa = imagemCapa;
    }

    public String getImagemCapaBaixada() {
        return imagemCapaBaixada;
    }

    public void setImagemCapaBaixada(String imagemCapaBaixada) {
        this.imagemCapaBaixada = imagemCapaBaixada;
    }
}
