package gov.ohio.jfs.oat.fn.cpi;

public class ExporterFactory {

	public static final String EXPORTER_TYPE_CSV = "CSV"; 
	public static final String EXPORTER_TYPE_XML = "XML";
	public static final String EXPORTER_TYPE_CONSOLE = "CONSOLE";

	public static Exportable getExtractor(String type){
		Exportable exporter = null;
		
		if(type.equals(EXPORTER_TYPE_CSV)) {
			exporter = new CSVExtporter(); 
		}
		if(type.equals(EXPORTER_TYPE_XML)) {
			exporter = new XMLExtporter(); 
		}
		if(type.equals(EXPORTER_TYPE_CONSOLE)) {
			exporter = new ConsoleExtporter(); 
		}
		return exporter; 
	}
	}
