package com.zte.os.oncall24.bean;

public class AbnormalKpi {
	private String cell;
	private String kpi;
	private String abnormalMessage;
	private String realData;
	private String forecastData;

	public AbnormalKpi(String cell, String kpi, String abnormalMessage, String realData, String forecastData) {
		this.cell = cell;
		this.kpi = kpi;
		this.abnormalMessage = abnormalMessage;
		this.realData = realData;
		this.forecastData = forecastData;
	}

	public String getKpi() {
		return kpi;
	}

	public void setKpi(String kpi) {
		this.kpi = kpi;
	}

	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String getAbnormalMessage() {
		return abnormalMessage;
	}
	public void setAbnormalMessage(String abnormalMessage) {
		this.abnormalMessage = abnormalMessage;
	}
	public String getRealData() {
		return realData;
	}
	public void setRealData(String realData) {
		this.realData = realData;
	}
	public String getForecastData() {
		return forecastData;
	}
	public void setForecastData(String forecastData) {
		this.forecastData = forecastData;
	}

}
