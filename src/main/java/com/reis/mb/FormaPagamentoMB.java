package com.reis.mb;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.reis.model.FormaPagamento;
import com.reis.service.FormaPagamentoService;

@ManagedBean
@ViewScoped
public class FormaPagamentoMB {

	private FormaPagamento formaPagamento;
	
private FormaPagamentoService service;
	
	public FormaPagamentoMB() {
		formaPagamento = new FormaPagamento();
		service = new FormaPagamentoService();
	}
	
	public List<FormaPagamento> getFormaPagamentos() {
	    return service.buscar(null);
	  }
	
	public void excluir() {
		try {
			service.excluir(formaPagamento);
			formaPagamento = new FormaPagamento();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro excluído com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao excluir registro"));
		}
	}
	
	public void salvar() {
		try {
			service.ExecutarReq(formaPagamento);
			formaPagamento = new FormaPagamento();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro salvo com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao salvar registro"));
		}
		
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	
}
