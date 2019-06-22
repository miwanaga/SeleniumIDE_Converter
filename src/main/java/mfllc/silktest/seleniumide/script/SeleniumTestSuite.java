package mfllc.silktest.seleniumide.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mfllc.silktest.util.XMLReader;

public class SeleniumTestSuite extends SeleniumScript {
	private List<String> testcases = new ArrayList<>();
	
	public SeleniumTestSuite(String name) {
		super(name);
	}
	
	@Override
	public void constructContext(Map<String, Object> context) {
		super.constructContext(context);
	}

	public void loadContent(XMLReader reader) {
		super.loadContent(reader);
		reader.read("/body/table/tbody/tr/td/a/@href", values->testcases.add(values[0]));
		testcases.forEach(t->System.out.println(t));
	}

}
