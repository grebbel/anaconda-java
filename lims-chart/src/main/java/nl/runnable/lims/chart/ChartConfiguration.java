package nl.runnable.lims.chart;

import org.jfree.data.Range;

public class ChartConfiguration {

	private ChartType chartType = ChartType.LOGARITHMIC;

	private String fontName = "Tahoma";

	private int regularFontSize = 14;

	private int smallFontSize = 12;

	private float tresholdLineThickness = 1.0f;

	private java.awt.Color tresholdLineColor = java.awt.Color.BLACK;

	private float ctMarkerLineThickness = 1.0f;

	private float amplificationLineThickness = 1.5f;

	private int splinePrecision = 100;

	private boolean showLegend = true;

	private Range range;

	public ChartType getChartType() {
		return chartType;
	}

	public void setChartType(final ChartType chartType) {
		this.chartType = chartType;
	}

	public String getFontName() {
		return fontName;
	}

	public void setFontName(final String fontName) {
		this.fontName = fontName;
	}

	public int getRegularFontSize() {
		return regularFontSize;
	}

	public void setRegularFontSize(final int baseFontSize) {
		this.regularFontSize = baseFontSize;
	}

	public int getSmallFontSize() {
		return smallFontSize;
	}

	public void setSmallFontSize(final int smallFontSize) {
		this.smallFontSize = smallFontSize;
	}

	public float getTresholdLineThickness() {
		return tresholdLineThickness;
	}

	public void setTresholdLineThickness(final float tresholdLineThickness) {
		this.tresholdLineThickness = tresholdLineThickness;
	}

	public java.awt.Color getTresholdLineColor() {
		return tresholdLineColor;
	}

	public void setTresholdLineColor(final java.awt.Color tresholdLineColor) {
		this.tresholdLineColor = tresholdLineColor;
	}

	public float getCtMarkerLineThickness() {
		return ctMarkerLineThickness;
	}

	public void setCtMarkerLineThickness(final float ctMarkerLineThickness) {
		this.ctMarkerLineThickness = ctMarkerLineThickness;
	}

	public float getAmplificationLineThickness() {
		return amplificationLineThickness;
	}

	public void setAmplificationLineThickness(final float amplificationLineThickness) {
		this.amplificationLineThickness = amplificationLineThickness;
	}

	public int getSplinePrecision() {
		return splinePrecision;
	}

	public void setSplinePrecision(final int splinePrecision) {
		this.splinePrecision = splinePrecision;
	}

	public boolean isShowLegend() {
		return showLegend;
	}

	public void setShowLegend(final boolean showLegend) {
		this.showLegend = showLegend;
	}

	public void setRange(final Range range) {
		this.range = range;
	}

	public Range getRange() {
		return range;
	}
}
