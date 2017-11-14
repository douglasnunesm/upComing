package br.ucb.upcoming.entity;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by dougl on 07/11/2017.
 */

public class Filme extends SugarRecord<Filme> implements Comparable<Filme>,Serializable {
    private String idFilme;
    private String titulo;
    private String popularidade;
    private String caminhoPoster;
    private String linguaOriginal;
    private String tituloOriginal;
    private String sinopse;
    private Date dataLancamento;

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(String idFilme) {
        this.idFilme = idFilme;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPopularidade() {
        return popularidade;
    }

    public void setPopularidade(String popularidade) {
        this.popularidade = popularidade;
    }

    public String getCaminhoPoster() {
        return caminhoPoster;
    }

    public void setCaminhoPoster(String caminhoPoster) {
        this.caminhoPoster = caminhoPoster;
    }

    public String getLinguaOriginal() {
        return linguaOriginal;
    }

    public void setLinguaOriginal(String linguaOriginal) {
        this.linguaOriginal = linguaOriginal;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public int compareTo(@NonNull Filme o) {
        return o.getDataLancamento().compareTo(getDataLancamento());
    }
}
