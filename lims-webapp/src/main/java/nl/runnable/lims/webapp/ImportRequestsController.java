package nl.runnable.lims.webapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import nl.runnable.lims.domain.ExcelImportService;
import nl.runnable.lims.domain.RequestImport;
import nl.runnable.lims.domain.RequestImportService;
import nl.runnable.lims.domain.RequestRepository;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/upload")
public class ImportRequestsController implements MessageSourceAware {

	/* Dependencies */

	@Inject
	private ExcelImportService excelImportService;

	@Inject
	private RequestRepository requestRepository;

	@Inject
	private RequestImportService requestImportService;

	private MessageSource messageSource;

	/* Main operations */

	@RequestMapping(method = RequestMethod.GET)
	public String show() {
		return "upload";
	}

	@RequestMapping(value = "/requests", method = RequestMethod.POST)
	public String importRequests(@RequestParam("file") final MultipartFile file,
			@RequestParam(value = "deleteExisting", defaultValue = "false") final boolean deleteExisting,
			final RedirectAttributes attributes) throws IOException {
		if (file.isEmpty() == false) {
			try {
				if (deleteExisting) {
					requestRepository.deleteAll();
				}
				importRequestsFromExcel(file, attributes);
			} catch (final Exception e) {
				final String message = messageSource.getMessage("import-requests.error",
						new Object[] { file.getOriginalFilename(), e.getMessage() }, null);
				attributes.addFlashAttribute(FlashAttributes.FLASH_MESSAGE,
						FlashMessage.forMessageAndCssClass(message, "error"));
			}
		}
		return "redirect:/upload";
	}

	private void importRequestsFromExcel(final MultipartFile file, final RedirectAttributes attributes)
			throws IOException {
		final InputStream excelWorksheet = file.getInputStream();
		final List<RequestImport> requestImports = excelImportService.importRequests(excelWorksheet);
		for (final RequestImport requestImport : requestImports) {
			requestImportService.importRequest(requestImport);
		}
		final String message = messageSource.getMessage("import-requests.confirmation",
				new Object[] { requestImports.size(), file.getOriginalFilename() }, null);
		attributes
				.addFlashAttribute(FlashAttributes.FLASH_MESSAGE, FlashMessage.forMessageAndCssClass(message, "info"));
	}

	/* Dependencies */

	@Override
	public void setMessageSource(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
