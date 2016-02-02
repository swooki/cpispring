package gov.ohio.jfs.fn.cpi;

public abstract class NewExtractor implements NewExtractable{
	private String name;

	@SuppressWarnings("unused")
	private NewExtractor(){}
	
	public NewExtractor(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
