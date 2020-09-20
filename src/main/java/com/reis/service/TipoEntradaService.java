package com.reis.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reis.model.Entrada;
import com.reis.model.TipoEntrada;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class TipoEntradaService {
	
	public List<TipoEntrada> buscar(String param){
		if(param != null) {
			param = "/"+param;
		}else {
			param = "";
		}
		try {
			 Client c = Client.create();
			    WebResource wr = c.resource("http://localhost:8080/financas/api/v1/tipoEntrada"+param);
			    String json = wr.get(String.class);
			    Gson gson = new Gson();
			    return gson.fromJson(json, new TypeToken<List<TipoEntrada>>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TipoEntrada buscarPorId(String id) {
				try {
			 Client c = Client.create();
			    WebResource wr = c.resource("http://localhost:8080/financas/api/v1/tipoEntrada/"+id);
			    String json = wr.get(String.class);
			    Gson gson = new Gson();
			    return gson.fromJson(json, new TypeToken<TipoEntrada>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void executarReq(TipoEntrada tipoEntrada) {
		try {
			if(tipoEntrada.getId() != null) {
				update(tipoEntrada);
				return;
			}
			String urlStr = "http://localhost:8080/financas/api/v1/tipoEntrada";
			String metodo = "POST";
			Gson gson = new Gson();
			String json = gson.toJson(tipoEntrada);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(TipoEntrada tipoEntrada) {
		try {
			String urlStr = "http://localhost:8080/financas/api/v1/tipoEntrada/" + tipoEntrada.getId();
			String metodo = "DELETE";
			Gson gson = new Gson();
			String json = gson.toJson(tipoEntrada);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(TipoEntrada tipoEntrada) {
		try {
			String urlStr = "http://localhost:8080/financas/api/v1/tipoEntrada/" + tipoEntrada.getId();
			String metodo = "PUT";
			Gson gson = new Gson();
			String json = gson.toJson(tipoEntrada);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
