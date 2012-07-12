package nl.runnable.lims.webapp;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import nl.runnable.lims.chart.ChartType;

import org.jfree.data.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * Resolves {@link ChartSettings} arguments against servlet requests.
 * 
 * @author Laurens Fridael
 * 
 */
public class ChartSettingsWebArgumentResolver implements WebArgumentResolver {

	private static final String RANGE_MIN_PARAM = "rangeMin";

	private static final String RANGE_MAX_PARAM = "rangeMax";

	private static final String LEGEND_PARAM = "legend";

	private static final String BACKGROUND_PARAM = "background";

	private static final String LINEAR_TYPE = "linear";

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final Pattern COLOR_PATTERN = Pattern.compile("(\\d+),(\\d+),(\\d+)");

	private static final String TYPE_PARAM = "type";

	private static final String HEIGHT_PARAM = "height";

	private static final String WIDTH_PARAM = "width";

	private static final String REGULAR_FONT_SIZE_PARAM = "fontSize";

	private static final String SMALL_FONT_SIZE_PARAM = "smallFontSize";

	private static final String FONT_PARAM = "font";

	@Override
	public Object resolveArgument(final MethodParameter methodParameter, final NativeWebRequest webRequest)
			throws Exception {
		if (methodParameter.getParameterType().equals(ChartSettings.class)) {
			return createChartSettings((HttpServletRequest) webRequest.getNativeRequest());
		}
		return UNRESOLVED;
	}

	private ChartSettings createChartSettings(final HttpServletRequest request) {
		final ChartSettings chartSettings = new ChartSettings();
		if (StringUtils.hasText(request.getParameter(WIDTH_PARAM))) {
			chartSettings.setWidth(Integer.parseInt(request.getParameter(WIDTH_PARAM)));
		}
		if (StringUtils.hasText(request.getParameter(HEIGHT_PARAM))) {
			chartSettings.setHeight(Integer.parseInt(request.getParameter(HEIGHT_PARAM)));
		}
		if (LINEAR_TYPE.equalsIgnoreCase(request.getParameter(TYPE_PARAM))) {
			chartSettings.getConfiguration().setChartType(ChartType.LINEAR);
		}
		if (StringUtils.hasText(request.getParameter(BACKGROUND_PARAM))) {
			final Matcher matcher = COLOR_PATTERN.matcher(request.getParameter(BACKGROUND_PARAM));
			if (matcher.matches()) {
				final int r = Integer.parseInt(matcher.group(1)), g = Integer.parseInt(matcher.group(2)), b = Integer
						.parseInt(matcher.group(3));
				chartSettings.setBackgroundColor(new Color(r, g, b));
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn("{} parameter does not match expected {} pattern.", BACKGROUND_PARAM,
							COLOR_PATTERN.pattern());
				}
			}
		}
		if (StringUtils.hasText(request.getParameter(REGULAR_FONT_SIZE_PARAM))) {
			final int fontSize = Integer.parseInt(request.getParameter(REGULAR_FONT_SIZE_PARAM));
			chartSettings.getConfiguration().setRegularFontSize(fontSize);
			chartSettings.getConfiguration().setSmallFontSize(fontSize - 2);

		}
		if (StringUtils.hasText(request.getParameter(SMALL_FONT_SIZE_PARAM))) {
			final int fontSize = Integer.parseInt(request.getParameter(SMALL_FONT_SIZE_PARAM));
			chartSettings.getConfiguration().setSmallFontSize(fontSize);

		}
		if (StringUtils.hasText(request.getParameter(FONT_PARAM))) {
			chartSettings.getConfiguration().setFontName(request.getParameter(FONT_PARAM));
		}
		if (StringUtils.hasText(request.getParameter(LEGEND_PARAM))) {
			chartSettings.getConfiguration().setShowLegend(Boolean.valueOf(request.getParameter(LEGEND_PARAM)));
		}
		if (StringUtils.hasText(request.getParameter(RANGE_MIN_PARAM))
				&& StringUtils.hasText(request.getParameter(RANGE_MAX_PARAM))) {
			final double min = Double.parseDouble(request.getParameter(RANGE_MIN_PARAM));
			final double max = Double.parseDouble(request.getParameter(RANGE_MAX_PARAM));
			chartSettings.getConfiguration().setRange(new Range(min, max));
		}
		return chartSettings;
	}
}
