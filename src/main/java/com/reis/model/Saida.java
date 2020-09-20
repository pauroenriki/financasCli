package com.reis.model;

import java.io.Serializable;
import java.util.Date;

public class Saida implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Date data;
	private String descricao;
	private Double valor;
	private Date dataVencimento;
	private CategoriaSaida categoriaSaida;
	private Usuario usuario;
	private StatusEnum status;
	private boolean vr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public CategoriaSaida getCategoriaSaida() {
		return categoriaSaida;
	}
	public void setCategoriaSaida(CategoriaSaida categoriaSaida) {
		this.categoriaSaida = categoriaSaida;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public StatusEnum getStatus() {
		return status;
	}
	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	
	public boolean isVr() {
		return vr;
	}
	public void setVr(boolean vr) {
		this.vr = vr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Saida other = (Saida) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
