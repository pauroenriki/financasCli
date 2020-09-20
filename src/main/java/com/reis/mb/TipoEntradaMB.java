package com.reis.mb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.reis.model.TipoEntrada;
import com.reis.service.TipoEntradaService;

@ManagedBean
@ViewScoped
public class TipoEntradaMB {
	private TipoEntrada tipoEntrada;
	
private TipoEntradaService service;
	
	public TipoEntradaMB() {
		tipoEntrada = new TipoEntrada();
		service = new TipoEntradaService();
	}
	
	public List<TipoEntrada> getTipoEntradas() {
	    return service.buscar(null);
	  }
	
	
	public void excluir() {
		try {
			service.excluir(tipoEntrada);
			tipoEntrada = new TipoEntrada();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro excluído com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao excluir registro"));
		}
	}
	
	public void salvar() {
		try {
			service.executarReq(tipoEntrada);
			tipoEntrada = new TipoEntrada();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro salvo com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao salvar registro"));
		}
		
	}

	public TipoEntrada getTipoEntrada() {
		return tipoEntrada;
	}

	public void setTipoEntrada(TipoEntrada tipoEntrada) {
		this.tipoEntrada = tipoEntrada;
	}
	
	
	
}
