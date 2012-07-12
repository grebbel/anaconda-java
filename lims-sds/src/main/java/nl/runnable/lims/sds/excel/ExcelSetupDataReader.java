package nl.runnable.lims.sds.excel;

import javax.annotation.ManagedBean;

import nl.runnable.lims.sds.SetupDataHandler;

import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * Reads sample setup data from SDS Excel exports.
 * 
 * @author Laurens Fridael
 * @see ExcelDataReader
 */
@ManagedBean
public class ExcelSetupDataReader extends AbstractExcelDataReader<SetupDataHandler> {

	/* Operations */

	@Override
	protected void parseDataRow(final HSSFRow row, final SetupDataHandler handler) {
		/* TODO: Implement reading of sample setup data. */
	}

}
