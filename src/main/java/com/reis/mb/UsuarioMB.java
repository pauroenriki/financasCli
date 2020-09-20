package com.reis.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reis.model.Usuario;
import com.reis.service.UsuarioService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

@ManagedBean(name = "usuarioMB")
@ViewScoped
public class UsuarioMB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private String resenha;
	
	private UsuarioService service;
	
	public UsuarioMB() {
		usuario = new Usuario();
		service = new UsuarioService();
	}
	
	public List<Usuario> getUsuarios() {
	    return service.buscar(null);
	  }
	
	public void validarSenha() {
		if(!this.resenha.equals(usuario.getSenha())) {
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Os campos de Senha devem ser iguais"));
		}
	}
	
	public void excluir() {
		try {
			service.excluir(usuario);
			usuario = new Usuario();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro salvo com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao salvar registro"));
		}
	}
	
	public void salvar() {
		try {
			service.salvar(usuario);
			usuario = new Usuario();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro salvo com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao salvar registro"));
		}
		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getResenha() {
		return resenha;
	}

	public void setResenha(String resenha) {
		this.resenha = resenha;
	}
	
	
}
