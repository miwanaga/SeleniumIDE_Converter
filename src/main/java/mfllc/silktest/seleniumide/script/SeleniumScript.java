package mfllc.silktest.seleniumide.script;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mfllc.silktest.util.XMLReader;

public abstract class SeleniumScript {
	private String name;
	private String title;

	public SeleniumScript(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public void loadContent(XMLReader reader) {
		reader.read("/head/title", values->title = values[0]);
	}

	public void constructContext(Map<String, Object> context) {
		context.put("name", getName());
		context.put("title", getTitle());
	}

	public static List<SeleniumScript> load(String sourceFile) {
		List<SeleniumScript> scripts = new ArrayList<>();
		load(scripts, new File(sourceFile));
		return scripts;
	}

	private static void load(List<SeleniumScript> scripts, File file) {
		if (file.isDirectory()) {
			Arrays.stream(file.listFiles()).forEach(f->load(scripts, f));
			return;
		}
		XMLReader reader = XMLReader.open(file);
		reader.read("/body/table/@id", values->{
			SeleniumScript script = null;
			if (values[0].equals("suiteTable"))
				script = new SeleniumTestSuite(file.getName());
			else
				script = new SeleniumTestCase(file.getName());
			script.loadContent(reader);
			scripts.add(script);
		});
	}

}
