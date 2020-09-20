package com.reis.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.reis.model.Entrada;
import com.reis.model.Saida;
import com.reis.model.StatusEnum;
import com.reis.service.EntradaService;
import com.reis.service.SaidaService;

@ManagedBean
@ViewScoped
public class relatorioComparacaoMB implements Serializable{
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
	private LineChartModel dateModel;
	
	public relatorioComparacaoMB() {
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
		dateModel = new LineChartModel();
		}
	
	public void pesquisar() {
		listaSaidas = new ArrayList<>();
		listaEntradas = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String param = "periodo/" +sdf.format(dataIni) + ":" + sdf.format(dataFim);
		List<Saida> listaS = saidaService.buscar(param);
		listaEntradas = entradaService.buscar(param);
		totalEntrada = 0.0;
		totalSaida = 0.0;
		totalDiferenca = 0.0;
		totalEntradaVR = 0.0;
		totalSaidaVR = 0.0;
		totalDiferencaVR = 0.0;
		listaS.forEach(x ->{
			if(x.getStatus().equals(StatusEnum.PAGO)) {
				if(x.isVr() == true) {
					totalSaidaVR = totalSaidaVR + x.getValor();
				}else {
					totalSaida = totalSaida + x.getValor();
				}
				listaSaidas.add(x);
			}
		});
		
		listaEntradas.forEach(x ->{
				if(x.getTipoEntrada().getDescricao().equals("VR")) {
					totalEntradaVR = totalEntradaVR + x.getValor();
				}else {
					totalEntrada = totalEntrada + x.getValor();
				}
		});
		totalDiferenca = totalEntrada - totalSaida;
		totalDiferencaVR = totalEntradaVR - totalSaidaVR;
		
	  }
	
	 private void createDateModel() {
	        dateModel = new LineChartModel();
	        LineChartSeries series1 = new LineChartSeries();
	        series1.setLabel("Series 1");
	 
	        series1.set("2014-01-01", 51);
	        series1.set("2014-01-06", 22);
	        series1.set("2014-01-12", 65);
	        series1.set("2014-01-18", 74);
	        series1.set("2014-01-24", 24);
	        series1.set("2014-01-30", 51);
	 
	        LineChartSeries series2 = new LineChartSeries();
	        series2.setLabel("Series 2");
	 
	        series2.set("2014-01-01", 32);
	        series2.set("2014-01-06", 73);
	        series2.set("2014-01-12", 24);
	        series2.set("2014-01-18", 12);
	        series2.set("2014-01-24", 74);
	        series2.set("2014-01-30", 62);
	 
	        dateModel.addSeries(series1);
	        dateModel.addSeries(series2);
	 
	        dateModel.setTitle("Zoom for Details");
	        dateModel.setZoom(true);
	        dateModel.getAxis(AxisType.Y).setLabel("Values");
	        DateAxis axis = new DateAxis("Dates");
	        axis.setTickAngle(-50);
	        axis.setMax("2014-02-01");
	        axis.setTickFormat("%b %#d, %y");
	 
	        dateModel.getAxes().put(AxisType.X, axis);
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

	public LineChartModel getDateModel() {
		return dateModel;
	}

	public void setDateModel(LineChartModel dateModel) {
		this.dateModel = dateModel;
	}
	
	
	
}
