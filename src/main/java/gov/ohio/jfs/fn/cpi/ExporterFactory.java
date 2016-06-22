package gov.ohio.jfs.fn.cpi;

public class ExporterFactory {
	public static final String EXPORTER_TYPE_CSV = "CSV";
	public static final String EXPORTER_TYPE_XML = "XML";
	public static final String EXPORTER_TYPE_DB = "DB";
	public static final String EXPORTER_TYPE_CONSOLE = "CONSOLE";

	public static Exportable getExporter(AppConfig appConfig) {
		String exporterType = appConfig.getProperty(AppConfig.EXPORTER_TYPE);
		if (exporterType.equals(EXPORTER_TYPE_CSV)) {
			CSVExporter exporter = new CSVExporter();
			exporter.setAppend(
					appConfig.getProperty(AppConfig.MODE).equals(AppConfig.MODE_APPEND) );
			exporter.setDateFormat(appConfig.getProperty(AppConfig.DATE_FORMAT));
			exporter.setOutputFileName(appConfig.getApplicationName() + ".csv");
			return exporter;
			
		}
		if (exporterType.equals(EXPORTER_TYPE_XML)) {
			XMLExporter exporter = new XMLExporter();
			return exporter;
		}
		else{
			ConsoleExtporter exporter = new ConsoleExtporter();
			return exporter;
		}
	}
}
