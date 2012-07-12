package nl.runnable.lims.chart;

import java.util.Collection;

import nl.runnable.lims.domain.Analysis;

import org.jfree.chart.JFreeChart;

/**
 * Defines operations for obtaining {@link JFreeChart} charts.
 * 
 * @author Laurens Fridael
 * 
 */
public interface AmplificationChartService {

	JFreeChart createAnalysisChart(Collection<Analysis> analyses, ChartConfiguration chartConfiguration);

}
