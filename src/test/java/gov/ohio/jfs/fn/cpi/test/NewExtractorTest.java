package gov.ohio.jfs.fn.cpi.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import gov.ohio.jfs.fn.cpi.CPILog;
import gov.ohio.jfs.fn.cpi.NewExtractor;

public class NewExtractorTest {

	@Test
	public void testCreation() {
		NewExtractor extractor = new DummyNewExtractor("FileNet");
		assertEquals(extractor.getName(), "FileNet");
	}
	
	@Test
	public void testExtract() throws Exception {
		NewExtractor extractor = new DummyNewExtractor("FileNet");
		ArrayList<CPILog> logList = extractor.extract(100);
		assertEquals(extractor.getMax().intValue(), 100);
		assertEquals(logList.size(), 100);
	}
}

class DummyNewExtractor extends NewExtractor {
	private Integer max = Integer.MAX_VALUE;
	
	public DummyNewExtractor(String name) {
		super(name);
	}


	@Override
	public void setMax(Integer max) {
		this.max = max;
	}


	@Override
	public Integer getMax() {
		return this.max;
	}


	@Override
	public ArrayList<CPILog> extract(Integer max) throws Exception {
		setMax(max);
		return extract();
	}

	@Override
	public ArrayList<CPILog> extract() throws Exception {
		ArrayList<CPILog>logList = new ArrayList<CPILog>();
		for(int i=0; i<this.max; i++){
			CPILog log = new CPILog();
			logList.add(log);
		}
		return logList;
	}
}
