package com.reis.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reis.model.FormaPagamento;
import com.reis.service.util.LinkUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class FormaPagamentoService {

	public List<FormaPagamento> buscar(String param){
		if(param != null) {
			param = "/"+param;
		}else {
			param = "";
		}
		try {
			 Client c = Client.create();
			    WebResource wr = c.resource(LinkUtils.LINK_API +"formaPagamento"+param);
			    String json = wr.get(String.class);
			    Gson gson = new Gson();
			    return gson.fromJson(json, new TypeToken<List<FormaPagamento>>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void ExecutarReq(FormaPagamento formaPagamento) {
		try {
			if(formaPagamento.getId() != null) {
				update(formaPagamento);
				return;
			}
			String urlStr = LinkUtils.LINK_API +"formaPagamento";
			String metodo = "POST";
			Gson gson = new Gson();
			String json = gson.toJson(formaPagamento);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir(FormaPagamento formaPagamento) {
		try {
			String urlStr = LinkUtils.LINK_API +"formaPagamento/" + formaPagamento.getId();
			String metodo = "DELETE";
			Gson gson = new Gson();
			String json = gson.toJson(formaPagamento);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(FormaPagamento formaPagamento) {
		try {
			String urlStr = LinkUtils.LINK_API +"formaPagamento/" + formaPagamento.getId();
			String metodo = "PUT";
			Gson gson = new Gson();
			String json = gson.toJson(formaPagamento);
			ServiceUtil su = new ServiceUtil();
			su.executarReq(json, urlStr, metodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FormaPagamento buscarPorId(String id) {
		try {
			 Client c = Client.create();
			    WebResource wr = c.resource(LinkUtils.LINK_API +"formaPagamento/"+id);
			    String json = wr.get(String.class);
			    Gson gson = new Gson();
			    return gson.fromJson(json, new TypeToken<FormaPagamento>(){}.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
