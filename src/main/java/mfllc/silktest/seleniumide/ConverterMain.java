package mfllc.silktest.seleniumide;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;
import com.mitchellbosecke.pebble.loader.DelegatingLoader;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mitchellbosecke.pebble.loader.Loader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import mfllc.silktest.seleniumide.extension.ConverterExtension;
import mfllc.silktest.seleniumide.script.SeleniumScript;
import mfllc.silktest.util.Utils;

public class ConverterMain {
	
	public ConverterMain() {
		
	}
	
	public void convert(ConverterOptions options) {
		List<Loader<?>> loaders = new ArrayList<>();
		FileLoader fileLoader = new FileLoader();
//		fileLoader.setPrefix("templates");
		fileLoader.setPrefix("src/main/resources/templates");
		loaders.add(fileLoader);
		loaders.add(new FileLoader());
		loaders.add(new ClasspathLoader());
		PebbleEngine engine = new PebbleEngine.Builder().autoEscaping(false).extension(new ConverterExtension(options)).loader(new DelegatingLoader(loaders)).build();
		PebbleTemplate compiledTemplate = engine.getTemplate(options.templateFile);
		
		List<SeleniumScript> scripts = SeleniumScript.load(options.sourceFile);
		scripts.forEach(s->{
			Writer writer = new StringWriter();
			Map<String, Object> context = new HashMap<>();
			String className = Utils.makeClassName(s.getName());
			context.put("className", className);
			context.put("methodName", "test" + Utils.makeIdentifier(s.getTitle(), true));
			s.constructContext(context);
			try {
				compiledTemplate.evaluate(writer, context);
			} catch (IOException e) {
				throw new RuntimeException(String.format("IOException while evaluating a template for file '%1$s': %2$s", s.getName(), e.getMessage()));
			}
			System.out.println(writer.toString());
		});
	}
	

	public static void main(String[] args) {
		ConverterOptions options = new ConverterOptions();
		try {
			options.processOptions(args);
			ConverterMain main = new ConverterMain();
			main.convert(options);
		}  catch(RuntimeException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println();
			options.printUsage(System.out, ConverterMain.class);
		}
		options.printOptionValues(System.out);
	}

}
