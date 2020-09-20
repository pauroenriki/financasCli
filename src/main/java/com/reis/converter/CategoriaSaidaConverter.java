package com.reis.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.reis.model.CategoriaSaida;
import com.reis.service.CategoriaSaidaService;

@FacesConverter(forClass=CategoriaSaida.class)
public class CategoriaSaidaConverter  
implements Converter {
	
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        CategoriaSaidaService service = new CategoriaSaidaService();
        String id = String.valueOf(value);
        return service.buscarPorId(id);
    }

    public String getAsString(FacesContext context, UIComponent component, Object object) {
    	CategoriaSaida categoria = (CategoriaSaida)object;
        if (categoria == null || categoria.getId() == null) {
            return null;
        }
        return String.valueOf(categoria.getId());
    }

}
