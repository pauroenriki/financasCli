package com.reis.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reis.model.Usuario;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;

public class UsuarioService {
	
	public List<Usuario> buscar(String param){
		if(param != null) {
			param = "/"+param;
		}else {
			param = "";
		}
		try {
			 Client c = Client.create();
			    WebResource wr = c.resource("http://localhost:8080/financas/api/v1/usuario"+param);
			    String json = wr.get(String.class);
			    Gson gson = new Gson();
			    return gson.fromJson(json, new TypeToken<List<Usuario>>(){}.getType());
		} catch (ClientHandlerException e) {
			e.printStackTrace();
			throw new ClientHandlerException("Conexão Recusada. Verifique se a API está em execução.");
		}
	}
	
	public void salvar(Usuario usuario) {
		try {
			if(usuario.getId() != null) {
				update(usuario);
				return;
			}
			String urlStr = "http://localhost:8080/financas/api/v1/usuario";
			String metodo = "POST";
			Gson gson = new Gson();
			String json = gson.toJson(usuario);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluir(Usuario usuario) {
		try {
			String urlStr = "http://localhost:8080/financas/api/v1/usuario/" + usuario.getId();
			String metodo = "DELETE";
			Gson gson = new Gson();
			String json = gson.toJson(usuario);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Usuario usuario) {
		try {
			String urlStr = "http://localhost:8080/financas/api/v1/usuario/" + usuario.getId();
			String metodo = "PUT";
			Gson gson = new Gson();
			String json = gson.toJson(usuario);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
