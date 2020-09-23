package com.reis.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.reis.model.Entrada;
import com.reis.service.util.LinkUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class EntradaService {

	public List<Entrada> buscar(String param){
		if(param != null) {
			param = "/"+param;
		}else {
			param = "";
		}
		try {
			 Client c = Client.create();
			    WebResource wr = c.resource(LinkUtils.LINK_API +"entrada"+param);
			    String json = wr.get(String.class);
			    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
			    		.create();
			    List<Entrada> res = gson.fromJson(json, new TypeToken<List<Entrada>>(){}.getType());
			    return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Entrada buscarPorId(String id) {
		return buscar(id).get(0);
	}
	
	public void salvar(Entrada entrada) {
		try {
			if(entrada.getId() != null) {
				update(entrada);
				return;
			}
			String urlStr = LinkUtils.LINK_API +"entrada";
			String metodo = "POST";
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String json = gson.toJson(entrada);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(Entrada entrada) {
		try {
			String urlStr = LinkUtils.LINK_API +"entrada/" + entrada.getId();
			String metodo = "DELETE";
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String json = gson.toJson(entrada);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Entrada entrada) {
		try {
			String urlStr = LinkUtils.LINK_API +"entrada/" + entrada.getId();
			String metodo = "PUT";
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String json = gson.toJson(entrada);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
