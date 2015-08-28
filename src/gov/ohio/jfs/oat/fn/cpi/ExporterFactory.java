package gov.ohio.jfs.oat.fn.cpi;

public class ExporterFactory {

	public static final String EXPORTER_TYPE_CSV = "CSV";
	public static final String EXPORTER_TYPE_XML = "XML";
	public static final String EXPORTER_TYPE_CONSOLE = "CONSOLE";

	public static Exportable getExtractor(AppConfig app) {
		Exportable exporter = null;
		String exporterType = app.getProperty(AppConfig.EXPORTER_TYPE);

		if (exporterType.equals(EXPORTER_TYPE_CSV)) {
			exporter = new CSVExporter(app);
		}
		if (exporterType.equals(EXPORTER_TYPE_XML)) {
			exporter = new XMLExtporter(app);
		}
		if (exporterType.equals(EXPORTER_TYPE_CONSOLE)) {
			exporter = new ConsoleExtporter(app);
		}
		return exporter;
	}
}
