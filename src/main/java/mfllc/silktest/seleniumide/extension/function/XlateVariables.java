package mfllc.silktest.seleniumide.extension.function;

import com.mitchellbosecke.pebble.template.EvaluationContext;

public class XlateVariables extends ConverterFunction {
	public String value;
	public String type;
	public String escapeTargets ="\"";
	public String escapeChar = "\\";
	
	@Override
	public Object execute(EvaluationContext context) {
		if (value == null) return null;
		return escapeCharacters(value);
	}
	
}
