package com.reis.mb;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.reis.model.CategoriaSaida;
import com.reis.model.Entrada;
import com.reis.model.Saida;
import com.reis.model.StatusEnum;
import com.reis.service.CategoriaSaidaService;
import com.reis.service.EntradaService;
import com.reis.service.SaidaService;

@ManagedBean
@ViewScoped
public class GraficoComparacaoMB implements Serializable{
	private static final long serialVersionUID = 1L;
	private BarChartModel dateModel;
	private BarChartModel dateModelCategoriaPorDia;
	private BarChartModel dateModelCategoriaTotais;
	private Date dataIni;
	private Date dataFim;
	private EntradaService entradaService;
	private SaidaService saidaService;
	
	public GraficoComparacaoMB() {
		dateModel = new BarChartModel();
		dateModelCategoriaPorDia = new BarChartModel();
		dateModelCategoriaTotais = new BarChartModel();
		entradaService = new EntradaService();
		saidaService = new SaidaService();
	}
	
	public void pesquisar(){
		List<Entrada> listaEntradas = new ArrayList<>();
		List<Saida> listaSaidas = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String param = "periodo/" + sdf.format(dataIni) + ":" + sdf.format(dataFim);
		List<Saida> listaS = saidaService.buscar(param);
		listaEntradas = entradaService.buscar(param);
		listaS.forEach(x -> {
			if (x.getStatus().equals(StatusEnum.PAGO)) {
				listaSaidas.add(x);
			}
		});
		List<Date> datas = trataDatas(listaEntradas, listaSaidas);
		createDateModel(listaEntradas,listaSaidas,datas);
		createDateModelCategoriaPorDatas(listaSaidas, datas);
		createDateModelCategoria(listaSaidas);
	}
	
	
	public void createDateModel(List<Entrada> listaEntradas, List<Saida> listaSaidas, List<Date> datas) {
		dateModel = new BarChartModel();
		ChartSeries series1 = new ChartSeries();
		series1.setLabel("Entradas");
		ChartSeries series2 = new ChartSeries();
		ChartSeries series3 = new ChartSeries();
		series2.setLabel("Saídas");
		series3.setLabel("Entrada Total (diferença)");
		Double totalEntrada = 0.0;
		
		for(Entrada e :listaEntradas){
			totalEntrada = totalEntrada + e.getValor();
		}
		SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yy");
		Double total = totalEntrada;
		for(Date data :datas){
			series1.set(sdff.format(data), temDataEntrada(data, listaEntradas));
			Double vlsaida = temDataSaida(data, listaSaidas);
			series2.set(sdff.format(data), vlsaida);
			total = total - vlsaida;
			series3.set(sdff.format(data), total );
		}

		dateModel.addSeries(series1);
		dateModel.addSeries(series2);
		dateModel.addSeries(series3);
		
		dateModel.setZoom(true);
		dateModel.setAnimate(true);
		dateModel.setLegendPosition("ne");
		dateModelCategoriaPorDia.setShowPointLabels(true);

	}
	public void createDateModelCategoriaPorDatas(List<Saida> listaSaidas, List<Date> datas) {
		dateModelCategoriaPorDia = new BarChartModel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		CategoriaSaidaService css = new CategoriaSaidaService();
		List<CategoriaSaida> categorias = css.buscar(null);
		for(CategoriaSaida categoria : categorias) {
			ChartSeries catSerie = new ChartSeries();
			catSerie.setLabel(categoria.getDescricao());
			for(Date data :datas){
				Double valor = temDataSaidaCategoria(data, listaSaidas,categoria);
					catSerie.set(sdf.format(data), valor);
			}
			dateModelCategoriaPorDia.addSeries(catSerie);
		}
		dateModelCategoriaPorDia.setZoom(true);
		dateModelCategoriaPorDia.setAnimate(true);
		dateModelCategoriaPorDia.setShowPointLabels(true);
		dateModelCategoriaPorDia.setExtender("extLegend");
		
	}
	
	public void createDateModelCategoria(List<Saida> listaSaidas) {
		dateModelCategoriaTotais = new BarChartModel();
		
		CategoriaSaidaService css = new CategoriaSaidaService();
		List<CategoriaSaida> categorias = css.buscar(null);
		for(CategoriaSaida categoria : categorias) {
			ChartSeries catSerie = new ChartSeries();
			catSerie.setLabel(categoria.getDescricao());
				Double valor = pegaTotalSaidaCategoria(listaSaidas,categoria);
				catSerie.set(categoria.getDescricao(), valor );
				if(valor != null && valor >  0.0) {
					dateModelCategoriaTotais.addSeries(catSerie);
				}
		}
		
		dateModelCategoriaTotais.setZoom(true);
		dateModelCategoriaTotais.setAnimate(true);
		dateModelCategoriaTotais.setShowPointLabels(true);
		dateModelCategoriaTotais.setExtender("extLegend");
	}

	private Double pegaTotalSaidaCategoria(List<Saida> listaSaidas, CategoriaSaida categoria) {
		Double total = 0.0;
		for (Saida saida : listaSaidas) {
			if(categoria.getDescricao().equals(saida.getCategoriaSaida().getDescricao())) {
				total = total + saida.getValor();
			}
		}
		return total;
	}

	private Double temDataSaidaCategoria(Date data, List<Saida> listaSaidas, CategoriaSaida categoria) {
		Double valor = 0.0;
		for (Saida sai : listaSaidas) {
			if (sai.getDataVencimento().equals(data)) {
				if(sai.getCategoriaSaida().getDescricao().equals(categoria.getDescricao())) {
					valor = valor + sai.getValor();
				}
			}
		}
		return valor;
	}

	private Double temDataEntrada(Date data, List<Entrada> listaEntradas) {
		Double valor = 0.0;
		for (Entrada ent : listaEntradas) {
			if (ent.getData().equals(data)) {
				valor = valor + ent.getValor();
			}
		}
		return valor;
	}

	private Double temDataSaida(Date data, List<Saida> listaSaidas) {
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

	public BarChartModel getDateModel() {
		return dateModel;
	}

	public void setDateModel(BarChartModel dateModel) {
		this.dateModel = dateModel;
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

	public BarChartModel getDateModelCategoriaPorDia() {
		return dateModelCategoriaPorDia;
	}

	public void setDateModelCategoriaPorDia(BarChartModel dateModelCategoria) {
		this.dateModelCategoriaPorDia = dateModelCategoria;
	}

	public BarChartModel getDateModelCategoriaTotais() {
		return dateModelCategoriaTotais;
	}

	public void setDateModelCategoriaTotais(BarChartModel dateModelCategoriaTotais) {
		this.dateModelCategoriaTotais = dateModelCategoriaTotais;
	}
	
	
	
}
