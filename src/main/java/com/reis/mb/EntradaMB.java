
package com.reis.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.reis.model.Entrada;
import com.reis.model.TipoEntrada;
import com.reis.model.Usuario;
import com.reis.service.EntradaService;
import com.reis.service.TipoEntradaService;
@ManagedBean
@ViewScoped
public class EntradaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entrada entrada;
	private EntradaService service;
	private List<TipoEntrada> tipoEntradas;
	private Usuario usuario;
	
	
	public EntradaMB() {
		entrada = new Entrada();
		service = new EntradaService();
		tipoEntradas = new ArrayList<>();
	}
	@PostConstruct
	public void init() {
		TipoEntradaService tes = new TipoEntradaService();
		tipoEntradas = tes.buscar(null);
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		if(session.getAttribute("usuario") != null) {
		usuario = (Usuario) session.getAttribute("usuario");
		}
	}
	
	public List<Entrada> getEntradas() {
		List<Entrada> res = service.buscar(null);
	    return res;
	  }
	
	
	public void excluir() {
		try {
			service.excluir(entrada);
			entrada = new Entrada();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro excluído com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao excluir registro"));
		}
	}
	
	public void salvar() {
		try {
			entrada.setData(Calendar.getInstance().getTime());
			entrada.setUsuario(usuario);
			service.salvar(entrada);
			entrada = new Entrada();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro salvo com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao salvar registro"));
		}
		
	}

	public Entrada getEntrada() {
		return entrada;
	}

	public void setEntrada(Entrada entrada) {
		this.entrada = entrada;
	}
	public List<TipoEntrada> getTipoEntradas() {
		return tipoEntradas;
	}
	public void setTipoEntradas(List<TipoEntrada> tipoEntradas) {
		this.tipoEntradas = tipoEntradas;
	}
	
	
	
}
