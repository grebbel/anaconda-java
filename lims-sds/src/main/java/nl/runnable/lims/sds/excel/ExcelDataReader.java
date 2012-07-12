package nl.runnable.lims.sds.excel;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import nl.runnable.lims.UnexpectedDataFormatException;
import nl.runnable.lims.sds.AmplificationDataHandler;
import nl.runnable.lims.sds.DataReader;
import nl.runnable.lims.sds.SetupDataHandler;

import org.springframework.util.Assert;

/**
 * {@link DataReader} implementation that handles Excel-based data exported by SDS 7500 Software version 2.
 * 
 * @author Laurens Fridael
 * @see ExcelAmplificationDataReader
 */
@ManagedBean
public class ExcelDataReader implements DataReader {

	/* Dependencies */

	private ExcelSetupDataReader excelSetupDataReader;

	private ExcelAmplificationDataReader excelAmplificationDataReader;

	/* Operations */

	@Override
	public void readSetupData(final InputStream data, final SetupDataHandler handler) throws IOException,
			UnexpectedDataFormatException {
		getExcelSetupDataReader().readExcelWorkbook(data, handler);
	}

	@Override
	public void readAmplificationData(final InputStream data, final AmplificationDataHandler handler)
			throws IOException, UnexpectedDataFormatException {
		getExcelAmplificationDataReader().readExcelWorkbook(data, handler);
	}

	/* Dependencies */

	@Inject
	public void setExcelSetupDataReader(final ExcelSetupDataReader setupDataReader) {
		Assert.notNull(setupDataReader);
		this.excelSetupDataReader = setupDataReader;
	}

	protected ExcelSetupDataReader getExcelSetupDataReader() {
		return excelSetupDataReader;
	}

	@Inject
	public void setExcelAmplificationDataReader(final ExcelAmplificationDataReader excelAmplificationDataReader) {
		this.excelAmplificationDataReader = excelAmplificationDataReader;
	}

	protected ExcelAmplificationDataReader getExcelAmplificationDataReader() {
		return excelAmplificationDataReader;
	}

}
