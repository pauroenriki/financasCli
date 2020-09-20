package com.reis.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/usuario"})
public class UsuarioServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	 protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	        req.getRequestDispatcher("/usuario.xhtml").forward((ServletRequest)req, (ServletResponse)resp);
	    }
	
}
