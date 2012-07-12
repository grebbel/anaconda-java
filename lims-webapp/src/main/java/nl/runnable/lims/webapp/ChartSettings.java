package nl.runnable.lims.webapp;

import java.awt.Color;

import nl.runnable.lims.chart.ChartConfiguration;

class ChartSettings {

	/* Dimensions chosen to fit within Blueprint 950px width. */

	private int width = 948;

	private int height = 711;

	private ChartConfiguration configuration = new ChartConfiguration();

	private Color backgroundColor = null;

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public ChartConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(final ChartConfiguration chartConfiguration) {
		this.configuration = chartConfiguration;
	}

	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

}
