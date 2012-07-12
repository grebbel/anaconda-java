package nl.runnable.lims.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import nl.runnable.lims.domain.AmplificationImport;
import nl.runnable.lims.sds.AmplificationDataHandler;
import nl.runnable.lims.sds.ExperimentMetadata;
import nl.runnable.lims.sds.excel.ExcelAmplificationDataReader;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ImportAmplificationsController implements MessageSourceAware {

	/* Dependencies */

	@Inject
	private ExcelAmplificationDataReader amplificationDataReader;

	private MessageSource messageSource;

	/* Main operations */

	/*
	 * Adding HttpSession as an argument resolves the Flash attributes problem. This can be removed once Session-based
	 * login is in place.
	 */
	@RequestMapping(value = "/import/amplifications", method = RequestMethod.POST)
	public String importAnalyses(@RequestParam("file") final MultipartFile file,
			@RequestParam("target") final String target, final RedirectAttributes attributes, final HttpSession session)
			throws IOException {
		if (file.isEmpty() == false) {
			try {
				importAmplificationsFromExcel(file, target, attributes);
			} catch (final Exception e) {
				final String message = messageSource.getMessage("import-amplifications.error",
						new Object[] { file.getOriginalFilename(), e.getMessage() }, null);
				attributes.addFlashAttribute(FlashAttributes.FLASH_MESSAGE,
						FlashMessage.forMessageAndCssClass(message, "error"));
			}
		}
		return "redirect:/";
	}

	/* Utility operations */

	private void importAmplificationsFromExcel(final MultipartFile file, final String target,
			final RedirectAttributes attributes) throws IOException {
		final InputStream excelWorksheet = file.getInputStream();
		final List<AmplificationImport> amplificationImports = importAmplifications(excelWorksheet);
		final int count = saveAmplifications(target, amplificationImports);
		final String message = messageSource.getMessage("import-amplifications.confirmation", new Object[] { count,
				target, file.getOriginalFilename() }, null);
		attributes
				.addFlashAttribute(FlashAttributes.FLASH_MESSAGE, FlashMessage.forMessageAndCssClass(message, "info"));
	}

	private int saveAmplifications(final String target, final List<AmplificationImport> amplificationImports) {
		final int insertCount = 0;
		// for (final AnalysisTarget analysisTarget : analysisTargetMapper.selectWithoutAmplifications(target)) {
		// for (final AmplificationImport amplificationImport : amplificationImports) {
		// final Amplification amplification = amplificationImport.getAmplification();
		// amplification.setAnalysisTargetId(analysisTarget.getId());
		// amplificationMapper.insert(amplification);
		// insertCount++;
		// }
		// }
		return insertCount;
	}

	private List<AmplificationImport> importAmplifications(final InputStream excelWorksheet) throws IOException {
		final List<AmplificationImport> amplificationImports = new ArrayList<AmplificationImport>();
		amplificationDataReader.readExcelWorkbook(excelWorksheet, new AmplificationDataHandler() {

			@Override
			public void handleExperimentMetadata(final ExperimentMetadata metadata) {
			}

			@Override
			public void handleAmplification(final AmplificationImport amplificationImport) {
				amplificationImports.add(amplificationImport);
			}
		});
		return amplificationImports;
	}

	/* Dependencies */

	@Override
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
