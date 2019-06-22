package mfllc.silktest.seleniumide.extension.function;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public abstract class ConverterFunction implements Function {
	private List<String> argments = null;
	private String escapeTargets ="\"";
	private String escapeChar = "\\";

	@Override
	public final List<String> getArgumentNames() {
		if (argments == null)
			argments = Arrays.stream(this.getClass().getDeclaredFields()).map(f->f.getName()).collect(Collectors.toList());
		return argments;
	}

	@Override
	public final Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context,
			int lineNumber) {
		getArgumentNames().forEach(name->{
			Object value = args.get(name);
			if (value != null)
				try {
					this.getClass().getField(name).set(this, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
		return execute(context);
	}
	
	public abstract Object execute(EvaluationContext context);

	protected String escapeCharacters(String locator) {
		StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(locator);
		for (char c = iterator.first(); c != CharacterIterator.DONE; c = iterator.next()) {
			if (escapeChar.indexOf(c) != -1) {
				result.append(c);
				c = iterator.next();
				if (c == CharacterIterator.DONE) break;
			} else if (escapeTargets.indexOf(c) != -1) {
				result.append(escapeChar);
			}
			result.append(c);
		}
		return result.toString();
	}

};
