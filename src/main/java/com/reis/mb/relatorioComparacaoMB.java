package com.reis.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.reis.model.Entrada;
import com.reis.model.Saida;
import com.reis.model.StatusEnum;
import com.reis.model.Usuario;
import com.reis.service.EntradaService;
import com.reis.service.SaidaService;
import com.reis.service.UsuarioService;

@ManagedBean(name = "relatorioComparacaoMB")
@ViewScoped
public class RelatorioComparacaoMB implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date dataIni;
	private Date dataFim;

	private EntradaService entradaService;
	private SaidaService saidaService;
	private List<Saida> listaSaidas;
	private List<Entrada> listaEntradas;
	private Double totalEntrada;
	private Double totalSaida;
	private Double totalDiferenca;
	private Double totalEntradaVR;
	private Double totalSaidaVR;
	private Double totalDiferencaVR;
	Map<String, Double> mapEntradas;
	Map<String, Double> mapSaidas;
	

	public RelatorioComparacaoMB() {
		entradaService = new EntradaService();
		saidaService = new SaidaService();
		listaSaidas = new ArrayList<>();
		listaEntradas = new ArrayList<>();
		totalEntrada = 0.0;
		totalSaida = 0.0;
		totalDiferenca = 0.0;
		totalEntradaVR = 0.0;
		totalSaidaVR = 0.0;
		totalDiferencaVR = 0.0;
		
	}

	public void ValoresEntradaPorUsuario(){
		mapEntradas = new HashMap<String, Double>();
		mapSaidas = new HashMap<String, Double>();
		List<Usuario> users = new ArrayList<>();
		UsuarioService us = new UsuarioService();
		users = us.buscar(null);
		for (Usuario usuario : users) {
			
			Double valorEnt = 0.0;
			for (Entrada entrada : listaEntradas) {
				if(usuario.getNome().contentEquals(entrada.getUsuario().getNome())) {
					valorEnt = valorEnt + entrada.getValor();
				}
			}
			mapEntradas.put(usuario.getNome(), valorEnt);
			
			Double valorSai = 0.0;
			for (Saida saida : listaSaidas) {
				if(usuario.getNome().contentEquals(saida.getUsuario().getNome())) {
					valorSai = valorSai + saida.getValor();
				}
			}
			mapSaidas.put(usuario.getNome(), valorSai);
		}
	}
	
	public void pesquisar() {
		listaSaidas = new ArrayList<>();
		listaEntradas = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String param = "periodo/" + sdf.format(dataIni) + ":" + sdf.format(dataFim);
		List<Saida> listaS = saidaService.buscar(param);
		listaEntradas = entradaService.buscar(param);
		totalEntrada = 0.0;
		totalSaida = 0.0;
		totalDiferenca = 0.0;
		totalEntradaVR = 0.0;
		totalSaidaVR = 0.0;
		totalDiferencaVR = 0.0;
		
		listaS.forEach(x -> {
			if (x.getStatus().equals(StatusEnum.PAGO)) {
				if (x.isVr() == true) {
					totalSaidaVR = totalSaidaVR + x.getValor();
				} else {
					totalSaida = totalSaida + x.getValor();
				}
				listaSaidas.add(x);
			}
		});

		listaEntradas.forEach(x -> {
			if (x.getTipoEntrada().getDescricao().equals("VR")) {
				totalEntradaVR = totalEntradaVR + x.getValor();
			} else {
				totalEntrada = totalEntrada + x.getValor();
			}
		});
		totalDiferenca = totalEntrada - totalSaida;
		totalDiferencaVR = totalEntradaVR - totalSaidaVR;
		ValoresEntradaPorUsuario();

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

	public EntradaService getEntradaService() {
		return entradaService;
	}

	public void setEntradaService(EntradaService entradaService) {
		this.entradaService = entradaService;
	}

	public SaidaService getSaidaService() {
		return saidaService;
	}

	public void setSaidaService(SaidaService saidaService) {
		this.saidaService = saidaService;
	}

	public List<Saida> getListaSaidas() {
		return listaSaidas;
	}

	public void setListaSaidas(List<Saida> listaSaidas) {
		this.listaSaidas = listaSaidas;
	}

	public Double getTotalEntrada() {
		return totalEntrada;
	}

	public void setTotalEntrada(Double totalEntrada) {
		this.totalEntrada = totalEntrada;
	}

	public Double getTotalSaida() {
		return totalSaida;
	}

	public void setTotalSaida(Double totalSaida) {
		this.totalSaida = totalSaida;
	}

	public List<Entrada> getListaEntradas() {
		return listaEntradas;
	}

	public void setListaEntradas(List<Entrada> listaEntradas) {
		this.listaEntradas = listaEntradas;
	}

	public Double getTotalDiferenca() {
		return totalDiferenca;
	}

	public void setTotalDiferenca(Double totalDiferenca) {
		this.totalDiferenca = totalDiferenca;
	}

	public Double getTotalEntradaVR() {
		return totalEntradaVR;
	}

	public void setTotalEntradaVR(Double totalEntradaVR) {
		this.totalEntradaVR = totalEntradaVR;
	}

	public Double getTotalSaidaVR() {
		return totalSaidaVR;
	}

	public void setTotalSaidaVR(Double totalSaidaVR) {
		this.totalSaidaVR = totalSaidaVR;
	}

	public Double getTotalDiferencaVR() {
		return totalDiferencaVR;
	}

	public void setTotalDiferencaVR(Double totalDiferencaVR) {
		this.totalDiferencaVR = totalDiferencaVR;
	}

	public Map<String, Double> getMapEntradas() {
		return mapEntradas;
	}

	public void setMapEntradas(Map<String, Double> mapEntradas) {
		this.mapEntradas = mapEntradas;
	}

	public Map<String, Double> getMapSaidas() {
		return mapSaidas;
	}

	public void setMapSaidas(Map<String, Double> mapSaidas) {
		this.mapSaidas = mapSaidas;
	}
	

}
