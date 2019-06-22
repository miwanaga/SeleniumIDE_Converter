package mfllc.silktest.seleniumide.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mfllc.silktest.util.XMLReader;

public class SeleniumTestCase extends SeleniumScript {
	private String baseUrl;
	private List<Command> commands = new ArrayList<>();
	
	public SeleniumTestCase(String name) {
		super(name);
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public List<Command> getCommands() {
		return commands;
	}
	
	@Override
	public void constructContext(Map<String, Object> context) {
		super.constructContext(context);
		context.put("baseUrl", getBaseUrl());
		context.put("commands", getCommands());
	}

	public void loadContent(XMLReader reader) {
		super.loadContent(reader);
		reader.read("/head/link/@href", values->baseUrl = values[0]);
		reader.read("/body/table/tbody/tr/td", values->commands.add(new Command(values)));
		System.out.println("baseUrl: " + baseUrl);
		commands.forEach(c->System.out.println(String.format("%1$s\t\t%2$s\t%3$s", c.getName(), c.getTarget(), c.getValue())));
	}

}
