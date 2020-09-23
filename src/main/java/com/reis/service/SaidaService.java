package com.reis.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.reis.model.Saida;
import com.reis.service.util.LinkUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class SaidaService {

	public List<Saida> buscar(String param){
		if(param != null) {
			param = "/"+param;
		}else {
			param = "";
		}
		try {
			 Client c = Client.create();
			    WebResource wr = c.resource(LinkUtils.LINK_API +"saida"+param);
			    String json = wr.get(String.class);
			    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
			    		.create();
			    List<Saida> res = gson.fromJson(json, new TypeToken<List<Saida>>(){}.getType());
			    return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Saida buscarPorId(String id) {
		return buscar(id).get(0);
	}
	
	public void salvar(Saida saida) {
		try {
			if(saida.getId() != null) {
				update(saida);
				return;
			}
			String urlStr = LinkUtils.LINK_API +"saida";
			String metodo = "POST";
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String json = gson.toJson(saida);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(Saida saida) {
		try {
			String urlStr = LinkUtils.LINK_API +"saida/" + saida.getId();
			String metodo = "DELETE";
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String json = gson.toJson(saida);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(Saida saida) {
		try {
			String urlStr = LinkUtils.LINK_API +"saida/" + saida.getId();
			String metodo = "PUT";
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			String json = gson.toJson(saida);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
