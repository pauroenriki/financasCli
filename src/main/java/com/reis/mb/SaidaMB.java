package com.reis.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.reis.model.CategoriaSaida;
import com.reis.model.Saida;
import com.reis.model.StatusEnum;
import com.reis.model.Usuario;
import com.reis.service.CategoriaSaidaService;
import com.reis.service.SaidaService;

@ManagedBean
@ViewScoped
public class SaidaMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private Saida saida;
	private SaidaService service;
	private List<CategoriaSaida> categoriaSaidas;
	private Usuario usuario;
	private Date dataIni;
	private Date dataFim;
	private List<Saida> listaSaidas;
	private Double valorTotal;
	
	
	public SaidaMB() {
		saida = new Saida();
		service = new SaidaService();
		categoriaSaidas = new ArrayList<>();
		listaSaidas = new ArrayList<>();
		
	}
	@PostConstruct
	public void init() {
		CategoriaSaidaService tes = new CategoriaSaidaService();
		categoriaSaidas = tes.buscar(null);
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(true);
		if(session.getAttribute("usuario") != null) {
		usuario = (Usuario) session.getAttribute("usuario");
		}
		valorTotal = 0.0;
	}
	
	public StatusEnum[] getStatus(){
		return StatusEnum.values();
	}
	
	public void pesquisar() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String param = "periodo/" +sdf.format(dataIni) + ":" + sdf.format(dataFim);
		listaSaidas = service.buscar(param);
		valorTotal = 0.0;
		listaSaidas.forEach(x ->{
			valorTotal += x.getValor();
		});
	  }
	
	
	public void excluir() {
		try {
			service.excluir(saida);
			saida = new Saida();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro excluído com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao excluir registro"));
		}
	}
	
	public void salvar() {
		try {
			Calendar c = Calendar.getInstance(new Locale("BR"));
			saida.setData(c.getTime());
			saida.setUsuario(usuario);
			service.salvar(saida);
			saida = new Saida();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Registro salvo com sucesso"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("resenha", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Erro ao salvar registro"));
		}
		
	}
	public Saida getSaida() {
		return saida;
	}
	public void setSaida(Saida saida) {
		this.saida = saida;
	}
	public List<CategoriaSaida> getCategoriaSaidas() {
		return categoriaSaidas;
	}
	public void setCategoriaSaidas(List<CategoriaSaida> categoriaSaidas) {
		this.categoriaSaidas = categoriaSaidas;
	}
	public Date getDataIni() {
		return dataIni;
	}
	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public List<Saida> getListaSaidas() {
		return listaSaidas;
	}
	public void setListaSaidas(List<Saida> listaSaidas) {
		this.listaSaidas = listaSaidas;
	}
	public Double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
}
