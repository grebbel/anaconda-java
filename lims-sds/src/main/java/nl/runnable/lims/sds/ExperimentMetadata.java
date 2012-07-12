package nl.runnable.lims.sds;

import java.io.Serializable;

/**
 * Provides metadata on the experiment as passed to {@link SetupDataHandler#handleExperimentMetadata(ExperimentMetadata)}.
 * 
 * @author Laurens Fridael
 * 
 */
public class ExperimentMetadata implements Serializable {

	private static final long serialVersionUID = 510701806286826615L;

	private String blockType;

	private String chemistry;

	private String experimentFilename;

	private long endRuntime;

	private String instrumentType;

	private String passiveReference;

	public String getBlockType() {
		return blockType;
	}

	public void setBlockType(final String blockType) {
		this.blockType = blockType;
	}

	public String getChemistry() {
		return chemistry;
	}

	public void setChemistry(final String chemistry) {
		this.chemistry = chemistry;
	}

	public String getExperimentFilename() {
		return experimentFilename;
	}

	public void setExperimentFilename(final String experimentFilename) {
		this.experimentFilename = experimentFilename;
	}

	public long getEndRuntime() {
		return endRuntime;
	}

	public void setEndRuntime(final long endRuntime) {
		this.endRuntime = endRuntime;
	}

	public String getInstrumentType() {
		return instrumentType;
	}

	public void setInstrumentType(final String instrumentType) {
		this.instrumentType = instrumentType;
	}

	public String getPassiveReference() {
		return passiveReference;
	}

	public void setPassiveReference(final String passiveReference) {
		this.passiveReference = passiveReference;
	}

}
