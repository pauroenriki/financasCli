package com.reis.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.reis.model.FormaPagamento;
import com.reis.service.FormaPagamentoService;

@FacesConverter(forClass=FormaPagamento.class)
public class FormaPagamentoConverter  
implements Converter {
	
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        FormaPagamentoService service = new FormaPagamentoService();
        String id = String.valueOf(value);
        return service.buscarPorId(id);
    }

    public String getAsString(FacesContext context, UIComponent component, Object object) {
    	FormaPagamento formaPgto = (FormaPagamento)object;
        if (formaPgto == null || formaPgto.getId() == null) {
            return null;
        }
        return String.valueOf(formaPgto.getId());
    }

}
