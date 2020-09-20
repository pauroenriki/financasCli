package com.reis.mb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.reis.model.CategoriaSaida;
import com.reis.service.CategoriaSaidaService;

@ManagedBean
@ViewScoped
public class CategoriaSaidaMB {

	private CategoriaSaida categoriaSaida;
	
private CategoriaSaidaService service;
	
	public CategoriaSaidaMB() {
		categoriaSaida = new CategoriaSaida();
		service = new CategoriaSaidaService();
	}
	
	public List<CategoriaSaida> getCategoriaSaidas() {
	    return service.buscar(null);
	  }
	
	public void excluir() {
		try {
			service.excluir(categoriaSaida);
			categoriaSaida = new CategoriaSaida();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro excluído com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao excluir registro"));
		}
	}
	
	public void salvar() {
		try {
			service.ExecutarReq(categoriaSaida);
			categoriaSaida = new CategoriaSaida();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro salvo com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao salvar registro"));
		}
		
	}

	public CategoriaSaida getCategoriaSaida() {
		return categoriaSaida;
	}

	public void setCategoriaSaida(CategoriaSaida categoriaSaida) {
		this.categoriaSaida = categoriaSaida;
	}
	
	
}
