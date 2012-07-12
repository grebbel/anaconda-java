package nl.runnable.lims.domain.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import nl.runnable.lims.domain.RequestImport;
import nl.runnable.lims.domain.Request;

import org.junit.Before;
import org.junit.Test;

public class DefaultExcelImportServiceTest {

	private DefaultExcelImportService excelImportService;

	@Before
	public void setup() {
		excelImportService = new DefaultExcelImportService();
	}

	@Test
	public void testImportAnalysisData() throws IOException {
		final List<RequestImport> analysisImports = excelImportService.importRequests(getClass().getResourceAsStream(
				"DefaultExcelImportServiceTest-fixture.xls"));
		assertEquals(2, analysisImports.size());
		{
			final RequestImport analysisImport = analysisImports.get(0);
			Request request = analysisImport.getRequest();
			assertEquals("12345", request.getExternalId());
			assertEquals("A. van der AA (1901-01-01)", request.getDescription());
			// assertEquals("URN", analysisImport.getAnalysis().getSampleTypeId());
			final List<String> targets = analysisImport.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains("CRYPTO"));
			assertTrue(targets.contains("PIFPCR"));
		}
		{
			final RequestImport analysisImport = analysisImports.get(1);
			Request request = analysisImport.getRequest();
			assertEquals("67890", request.getExternalId());
			assertEquals("B. Berends (1902-02-02)", request.getDescription());
			// assertEquals("BLD", analysisImport.getAnalysis().getSampleTypeId());
			final List<String> targets = analysisImport.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains("CHLPCR"));
			assertTrue(targets.contains("TRIPCR"));
			assertTrue(targets.contains("VZV-DNA"));
		}
	}

}
