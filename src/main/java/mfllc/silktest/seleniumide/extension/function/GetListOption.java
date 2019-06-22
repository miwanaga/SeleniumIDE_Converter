package mfllc.silktest.seleniumide.extension.function;

import com.mitchellbosecke.pebble.template.EvaluationContext;

public class GetListOption extends ConverterFunction {
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
		if (type != null && type.equals("silk")) result = convertToXPath(result);
		result.setLocator(escapeCharacters(result.getLocator()));
		return result;
	}

	private Locator convertToXPath(Locator locator) {
		switch(locator.getType()) {
		case "label":
			break;
		case "value":
		case "id":
			locator.setLocator(String.format("/*[@%1$s='%2$s']", locator.getType(), locator.getLocator()));
			locator.setType("xpath");
			break;
		case "index":
			break;
		default:
			break;
		}
		return locator;
	}

}
