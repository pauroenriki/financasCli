package com.reis.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartSeries;

import com.reis.model.Entrada;
import com.reis.model.Saida;
import com.reis.model.StatusEnum;
import com.reis.service.EntradaService;
import com.reis.service.SaidaService;

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
	private BarChartModel dateModel;

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
		dateModel = new BarChartModel();
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
		createDateModel();

	}

	private void createDateModel() {
		dateModel = new BarChartModel();
		ChartSeries series1 = new ChartSeries();
		series1.setLabel("Entradas");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> datas = trataDatas(listaEntradas, listaSaidas);
		ChartSeries series2 = new ChartSeries();
		ChartSeries series3 = new ChartSeries();
		series2.setLabel("Saídas");
		series3.setLabel("Diferença");
		Double total = totalEntrada + totalEntradaVR;
		for(Date data :datas){
			series1.set(sdf.format(data), temDataEntrada(data));
			Double vlsaida = temDataSaida(data);
			series2.set(sdf.format(data), vlsaida);
			total = total - vlsaida;
			series3.set(sdf.format(data), total );
		}

		dateModel.addSeries(series1);
		dateModel.addSeries(series2);
		dateModel.addSeries(series3);

		Axis axis = dateModel.getAxis(AxisType.Y);
		dateModel.setAnimate(true);
		dateModel.setLegendPosition("ne");

	}

	private Double temDataEntrada(Date data) {
		Double valor = 0.0;
		for (Entrada ent : listaEntradas) {
			if (ent.getData().equals(data)) {
				valor = valor + ent.getValor();
			}
		}
		return valor;
	}

	private Double temDataSaida(Date data) {
		Double valor = 0.0;
		for (Saida sai : listaSaidas) {
			if (sai.getDataVencimento().equals(data)) {
				valor = valor + sai.getValor();
			}
		}
		return valor;
	}

	private List<Date> trataDatas(List<Entrada> entradas, List<Saida> saidas) {
		List<Date> datas = new ArrayList<>();
		entradas.forEach(x -> {
			if (!datas.contains(x.getData())) {
				datas.add(x.getData());
			}
		});
		saidas.forEach(x -> {
			if (!datas.contains(x.getDataVencimento())) {
				datas.add(x.getDataVencimento());
			}
		});

		return datas;
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

	public BarChartModel getDateModel() {
		return dateModel;
	}

	public void setDateModel(BarChartModel dateModel) {
		this.dateModel = dateModel;
	}

}
