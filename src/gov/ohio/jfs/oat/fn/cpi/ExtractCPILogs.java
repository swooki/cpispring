package gov.ohio.jfs.oat.fn.cpi;

import java.util.ArrayList;

public class ExtractCPILogs {

	Extractable extractor;
	Exportable exporter;

	public Extractable getExtractor() {
		return extractor;
	}

	public Exportable getExporter() {
		return exporter;
	}


	public ExtractCPILogs(String extractorType, String exporterType) {
		super();
		extractor = ExtractorFactory.getExtractor(extractorType);
		exporter = ExporterFactory.getExtractor(exporterType);
	}

	public static void main(String[] args) {
		ExtractCPILogs ecl = new ExtractCPILogs(
				ExtractorFactory.EXTRACTOR_TYPE_TEST,
				ExporterFactory.EXPORTER_TYPE_CSV);
		ArrayList<CPILog> logs = ecl.extractor.extract();
		ecl.exporter.export(logs);
	}

	void showLogs(ArrayList<CPILog> logs) {
		for (CPILog log : logs) {
			System.out.println(log.toString());
		}
	}
}
