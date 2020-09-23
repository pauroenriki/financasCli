package com.reis.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reis.model.CategoriaSaida;
import com.reis.service.util.LinkUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class CategoriaSaidaService {

	public List<CategoriaSaida> buscar(String param){
		if(param != null) {
			param = "/"+param;
		}else {
			param = "";
		}
		try {
			 Client c = Client.create();
			    WebResource wr = c.resource(LinkUtils.LINK_API +"categoriaSaida"+param);
			    String json = wr.get(String.class);
			    Gson gson = new Gson();
			    return gson.fromJson(json, new TypeToken<List<CategoriaSaida>>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void ExecutarReq(CategoriaSaida categoriaSaida) {
		try {
			if(categoriaSaida.getId() != null) {
				update(categoriaSaida);
				return;
			}
			String urlStr = LinkUtils.LINK_API +"categoriaSaida";
			String metodo = "POST";
			Gson gson = new Gson();
			String json = gson.toJson(categoriaSaida);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(CategoriaSaida categoriaSaida) {
		try {
			String urlStr = LinkUtils.LINK_API +"categoriaSaida/" + categoriaSaida.getId();
			String metodo = "DELETE";
			Gson gson = new Gson();
			String json = gson.toJson(categoriaSaida);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(CategoriaSaida categoriaSaida) {
		try {
			String urlStr = LinkUtils.LINK_API +"categoriaSaida/" + categoriaSaida.getId();
			String metodo = "PUT";
			Gson gson = new Gson();
			String json = gson.toJson(categoriaSaida);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CategoriaSaida buscarPorId(String id) {
		try {
			 Client c = Client.create();
			    WebResource wr = c.resource(LinkUtils.LINK_API +"categoriaSaida/"+id);
			    String json = wr.get(String.class);
			    Gson gson = new Gson();
			    return gson.fromJson(json, new TypeToken<CategoriaSaida>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
