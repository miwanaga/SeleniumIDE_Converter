package mfllc.silktest.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class OptionHandler {

	@Option(name="-option-file", description="Loading options from specified file (json format)", required=false)
	private String optionFile = null;

	public void processOptions(String[] args) {
		List<String> options = new ArrayList<String>();
		Arrays.stream(args).forEach(a -> options.add(a));
		for (Field f: getOptionFields()) {
			Option option = f.getAnnotation(Option.class);
			boolean found = false;
			for (int i = 0; i < options.size(); i++) {
				if (options.get(i).equals(option.name())) {
					i++;
					if (i == args.length) throw new RuntimeException(String.format("Value for option %1$s is missing.", option.name()));
					try {
						f.set(this, options.get(i));
					} catch (Exception e) {
						throw new RuntimeException(String.format("System Error: Failed to set a %1$s option value to the field %2$s.", option.name(), f.getName()));
					}
					found = true;
					break;
				}
			}
			if (option.required() && !found) throw new RuntimeException(String.format("Please specify a required option %1$s.", option.name()));
			if (optionFile != null) {
				options.addAll(loadOptionFile());
				optionFile = null;
			}
		}
	}
	
	private List<String> loadOptionFile() {
		List<String> result = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(new File(optionFile));
			node.fieldNames().forEachRemaining(name -> { result.add(name); result.add(node.get(name).textValue());});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		} catch (IOException e) {
			throw new RuntimeException(String.format("IOException while opening an option file '%1$s': %2$s", optionFile, e.getMessage()));
		}
		return result;
	}

	private List<Field> getOptionFields() {
		Class<?> clazz = this.getClass();
		ArrayList<Field> fields = new ArrayList<Field>();
		while (!clazz.equals(Object.class)) {
			for (Field f: clazz.getDeclaredFields()) {
				Option option = f.getAnnotation(Option.class);
				if (option != null) fields.add(0, f);
			}
			clazz = clazz.getSuperclass();
		}
		return fields;
	}
	
	public void printUsage(PrintStream out, Class<?> main) {
		out.println(String.format("Usage: java.exe %1$s <options> (*: required)", main.getName()));
		for (Field f: getOptionFields()) {
			Option option = f.getAnnotation(Option.class);
			out.println(String.format(" %1$s%2$s\t%3$s", option.name(), option.required() ? "*" : "", option.description()));
		}
	}
	
	public void printOptionValues(PrintStream out) {
		for (Field f: getOptionFields()) {
			Option option = f.getAnnotation(Option.class);
			try {
				out.println(String.format(" %1$s\t%2$s", option.name(), f.get(this)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
