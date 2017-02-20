package appsine.com.br.sinemaps;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thoma on 19/02/2017.
 */

public class DataReceive {
    private String codPosto;
    private String nome;
    private String entidadeConveniada;
    private String municipio;
    private String uf;
    private String cep;
    private String bairro;
    private String endereco;
    private String telefone;

    @SerializedName("lat")
    private String latitude;

    @SerializedName("long")
    private String longitude;

    public DataReceive(String codPosto, String nome, String entidadeConveniada, String endereco, String bairro, String cep,
                String telefone, String municipio, String uf, String latitude, String longitude){

        this.codPosto = codPosto;
        this.nome = nome;
        this.entidadeConveniada = entidadeConveniada;
        this.municipio = municipio;
        this.uf = uf;
        this.cep = cep;
        this.bairro = bairro;
        this.endereco = endereco;
        this.telefone = telefone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DataReceive(){}

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodPosto() {
        return codPosto;
    }
    public void setCodPosto(String codPosto) {
        this.codPosto = codPosto;
    }

    public String getEntidadeConveniada() {
        return entidadeConveniada;
    }
    public void setEntidadeConveniada(String entidadeConveniada) {
        this.entidadeConveniada = entidadeConveniada;}

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getMunicipio() {
        return municipio;
    }
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }



}


