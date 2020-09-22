package com.reis.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.reis.model.Usuario;
import com.reis.service.UsuarioService;

@ManagedBean
@ViewScoped
public class LoginMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String senha;

	public String logar() {
		UsuarioService service = new UsuarioService();
		List<Usuario> usus = new ArrayList<>();
		try {
		usus = service.buscar("login/" + this.login);
		}catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERRO", e.getMessage()));
			PrimeFaces.current().executeScript("PF('dlgExplosao').show();");
			return "";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		if (usus.size() != 0) {
			Usuario usu = usus.get(0);
			if(usu.getLogin().equals(this.login) && usu.getSenha().equals(this.senha)) {
			session.setAttribute("usuario", usu );
			return "index.xhtml?faces-redirect=true";
			}
		}
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Credenciais incorretas."));
		return null;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
