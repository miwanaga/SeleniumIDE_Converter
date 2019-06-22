package mfllc.silktest.seleniumide.extension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.attributes.AttributeResolver;
import com.mitchellbosecke.pebble.extension.Extension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.extension.NodeVisitorFactory;
import com.mitchellbosecke.pebble.extension.Test;
import com.mitchellbosecke.pebble.operator.BinaryOperator;
import com.mitchellbosecke.pebble.operator.UnaryOperator;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;

import mfllc.silktest.seleniumide.ConverterOptions;
import mfllc.silktest.seleniumide.extension.function.GetListOption;
import mfllc.silktest.seleniumide.extension.function.GetLocator;

public class ConverterExtension implements Extension {	
	private Map<String, Object> variables = new HashMap<>();
	private Map<String, Function> functions = new HashMap<>();
	
	public ConverterExtension(ConverterOptions options) {
		variables.put("packageName", options.packageName);
		variables.put("receiver", options.receiver);
		functions.put("getLocator", new GetLocator());
		functions.put("getListOption", new GetListOption());
	}
	
	@Override
	public Map<String, Filter> getFilters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Test> getTests() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Function> getFunctions() {
		return functions;
	}

	@Override
	public List<TokenParser> getTokenParsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BinaryOperator> getBinaryOperators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnaryOperator> getUnaryOperators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getGlobalVariables() {
		return variables;
	}

	@Override
	public List<NodeVisitorFactory> getNodeVisitors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AttributeResolver> getAttributeResolver() {
		// TODO Auto-generated method stub
		return null;
	}

}
