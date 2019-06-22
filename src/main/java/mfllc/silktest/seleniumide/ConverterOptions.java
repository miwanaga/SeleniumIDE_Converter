package mfllc.silktest.seleniumide;

import mfllc.silktest.util.Option;
import mfllc.silktest.util.OptionHandler;

public class ConverterOptions extends OptionHandler {
	
	@Option(name="-extension", description="File extension for generated file", required=false)
	public String extension = "java";
	
	@Option(name="-output", description="Output folder name", required=false)
	public String outputFolder = ".";

	@Option(name="-receiver", description="Variable name for WebDriver instance", required=false)
	public String receiver = "driver";

	@Option(name="-package", description="Package name", required=false)
	public String packageName = "sample";

	@Option(name="-template", description="Pebble template file", required=false)
	public String templateFile = "html2silk4j.peb";

	@Option(name="-html-script", description="Selenium IDE script file", required=true)
	public String sourceFile;
	
}
