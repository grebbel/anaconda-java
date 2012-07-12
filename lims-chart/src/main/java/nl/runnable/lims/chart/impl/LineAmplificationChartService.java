package nl.runnable.lims.chart.impl;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Paint;
import java.awt.geom.Line2D;
import java.util.Collection;
import java.util.List;

import javax.annotation.ManagedBean;

import nl.runnable.lims.chart.AmplificationChartService;
import nl.runnable.lims.chart.ChartConfiguration;
import nl.runnable.lims.chart.ChartType;
import nl.runnable.lims.domain.Amplification;
import nl.runnable.lims.domain.Analysis;
import nl.runnable.lims.domain.AssayTarget;
import nl.runnable.lims.domain.Color;
import nl.runnable.lims.domain.Ct;
import nl.runnable.lims.domain.Target;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@ManagedBean
public class LineAmplificationChartService implements AmplificationChartService {

	/* Operations */

	@Override
	public JFreeChart createAnalysisChart(final Collection<Analysis> analyses,
			final ChartConfiguration chartConfiguration) {
		final XYPlot plot = createPlot(analyses, chartConfiguration);
		final JFreeChart chart = new JFreeChart(plot);
		if (chartConfiguration.isShowLegend() == false) {
			chart.removeLegend();
		}
		return chart;
	}

	private XYPlot createPlot(final Collection<Analysis> analyses, final ChartConfiguration cfg) {
		final Font regularFont = new Font(cfg.getFontName(), 0, cfg.getRegularFontSize());
		final Font smallFont = new Font(cfg.getFontName(), 0, cfg.getSmallFontSize());

		final XYPlot plot = new XYPlot();
		final XYSplineRenderer renderer = new XYSplineRenderer();
		renderer.setPrecision(cfg.getSplinePrecision());
		renderer.setAutoPopulateSeriesShape(false);
		plot.setRenderer(renderer);

		int maxCycle = 1;
		final XYSeriesCollection dataSet = new XYSeriesCollection();
		for (final Analysis analysis : analyses) {
			final List<Amplification> amplifications = analysis.getAmplifications();
			if (amplifications.isEmpty()) {
				continue;
			}
			final AssayTarget assayTarget = analysis.getAssayTarget();
			final Target target = assayTarget.getTarget();
			final Ct ct = analysis.getCt();
			final String seriesName;
			if (ct != null) {
				seriesName = String.format("%s (%.2f) ", target.getName(), ct.getValue());
			} else {
				seriesName = target.getName();
			}

			final XYSeries series = new XYSeries(seriesName);
			for (final Amplification amplification : amplifications) {
				final double drn = amplification.getDrn();
				if (cfg.getChartType() != ChartType.LOGARITHMIC || drn > 0) {
					series.add(amplification.getCycle(), drn);
					if (amplification.getCycle() > maxCycle) {
						maxCycle = amplification.getCycle();
					}
				}
			}
			dataSet.addSeries(series);
			final int index = dataSet.getSeriesCount() - 1;
			final Color color = target.getColor();
			final Paint targetColor = (color != null ? color.toAwtColor() : java.awt.Color.BLACK);
			renderer.setSeriesPaint(index, targetColor);
			renderer.setSeriesStroke(index, new BasicStroke(cfg.getAmplificationLineThickness()));
			renderer.setSeriesShapesVisible(index, false);
			renderer.setLegendTextFont(index, regularFont);
			renderer.setLegendLine(new Line2D.Double(0, 0, 10, 0));

			if (analysis.getTreshold() != null) {
				plot.addRangeMarker(new ValueMarker(analysis.getTreshold(), cfg.getTresholdLineColor(),
						new BasicStroke(cfg.getTresholdLineThickness(), BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER,
								1.5f, new float[] { 3f }, 0.0f)));
			}

			if (ct != null) {
				final BasicStroke stroke = new BasicStroke(cfg.getCtMarkerLineThickness(), BasicStroke.CAP_BUTT,
						BasicStroke.JOIN_MITER, 1.5f, new float[] { 3f }, 0.0f);
				plot.addDomainMarker(new ValueMarker(ct.getValue(), cfg.getTresholdLineColor(), stroke));
				final XYTextAnnotation annotation = new XYTextAnnotation(String.format(" %.2f ", ct.getValue()),
						ct.getValue(), ct.getTreshold());
				annotation.setFont(smallFont);
				switch (analysis.getStatus()) {
				case POSITIVE:
					annotation.setBackgroundPaint(java.awt.Color.GREEN);
					break;
				case NEGATIVE:
					annotation.setBackgroundPaint(java.awt.Color.RED);
					annotation.setPaint(java.awt.Color.WHITE);
					break;
				case INDETERMINATE:
					// Fall through
				default:
					annotation.setBackgroundPaint(java.awt.Color.WHITE);
					break;
				}
				annotation.setOutlineStroke(stroke);
				// annotation.setOutlinePaint(targetColor);
				annotation.setOutlineVisible(true);
				plot.addAnnotation(annotation);

			}
		}
		plot.setDataset(dataSet);

		final NumberAxis rnAxis = (cfg.getChartType() == ChartType.LOGARITHMIC ? new LogarithmicAxis(null)
				: new NumberAxis());
		if (cfg.getRange() != null) {
			rnAxis.setAutoRange(false);
			rnAxis.setRange(cfg.getRange());
		}
		// rnAxis.setLabel("dRN");
		rnAxis.setLabelFont(regularFont);
		rnAxis.setTickLabelFont(smallFont);
		plot.setRangeAxis(rnAxis);
		final NumberAxis cycleAxis = new NumberAxis();
		cycleAxis.setLabelFont(regularFont);
		// cycleAxis.setLabel("Cycle");
		cycleAxis.setRange(1, maxCycle);
		cycleAxis.setTickUnit(new NumberTickUnit(1));
		cycleAxis.setTickLabelFont(smallFont);
		plot.setDomainAxis(cycleAxis);

		return plot;
	}
}
