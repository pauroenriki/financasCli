package com.reis.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.reis.model.TipoEntrada;
import com.reis.service.TipoEntradaService;
@FacesConverter(forClass=TipoEntrada.class)
public class TipoEntradaConverter 
implements Converter {
	
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        TipoEntradaService service = new TipoEntradaService();
        String id = String.valueOf(value);
        return service.buscarPorId(id);
    }

    public String getAsString(FacesContext context, UIComponent component, Object object) {
        TipoEntrada tipo = (TipoEntrada)object;
        if (tipo == null || tipo.getId() == null) {
            return null;
        }
        return String.valueOf(tipo.getId());
    }
}
