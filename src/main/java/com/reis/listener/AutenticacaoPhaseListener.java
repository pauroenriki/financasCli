package com.reis.listener;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.reis.model.Usuario;

public class AutenticacaoPhaseListener implements PhaseListener {
	private static final long serialVersionUID = 1;
	public static final String loginPage = "login.xhtml";

	public void afterPhase(PhaseEvent event) {
		FacesContext fc = event.getFacesContext();
		String page = fc.getViewRoot().getViewId();
		if (page.equals("/login.xhtml")) {
			return;
		}
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		Usuario funcionario = (Usuario) session.getAttribute("usuario");
		if (funcionario == null ) {
			FacesMessage message = new FacesMessage("Verifique seu acesso!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage("login", message);
			try {
				context.getExternalContext().redirect("/financasCli/login.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void beforePhase(PhaseEvent arg0) {
	}

	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
}
