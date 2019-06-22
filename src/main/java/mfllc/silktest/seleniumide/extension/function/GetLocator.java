package mfllc.silktest.seleniumide.extension.function;

import com.mitchellbosecke.pebble.template.EvaluationContext;

public class GetLocator extends ConverterFunction {
	public String locator;
	public String type = null;
	
	@Override
	public Object execute(EvaluationContext context) {
		if (locator == null) return null;
		int index = locator.indexOf("=");
		Locator result = new Locator();
		result.setType("");
		if (index != -1) {
			result.setType(locator.substring(0, index));
			result.setLocator(locator.substring(index + 1));
		}
		switch(result.getType()) {
		case "xpath":
		case "css":
		case "id":
		case "link":
		case "name":
		case "tag_name":
			break;
		default:
			result.setType("xpath");
			result.setLocator(String.format("//*[@id='%1$s' or @name='%1$s']", locator));
			break;
		}
		if (type != null && type.equals("silk")) result = convertToXPath(result);
		result.setLocator(escapeCharacters(result.getLocator()));
		return result;
	}

	private Locator convertToXPath(Locator locator) {
		switch(locator.getType()) {
		case "xpath":
			break;
		case "css":
			break;
		case "id":
			locator.setType("xpath");
			locator.setLocator(String.format("//*[@id='%1$s']", locator.getLocator()));
			break;
		case "link":
			locator.setType("xpath");
			locator.setLocator(String.format("//A[@textContents='%1$s']", locator.getLocator()));
			break;
		case "name":
			locator.setType("xpath");
			locator.setLocator(String.format("//*[@name='%1$s']", locator.getLocator()));
			break;
		case "tag_name":
			locator.setType("xpath");
			locator.setLocator(String.format("//%1$s", locator.getLocator()));
			break;
		}
		return locator;
	}

}
