package mfllc.silktest.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLReader {
	public interface Callback {
		void apply(String... values);
	}
	
	private Document document;
	
	public XMLReader(Document document) {
		this.document = document;
	}
	
	public void read(String path, Callback callback) {
		Element element = document.getDocumentElement();
		String result = read(element, path.split("/"), callback);
		if (result != null) callback.apply(result);
	}
	
	private String read(Element element, String[] path, Callback callback) {
		if (path.length == 0) return element.getTextContent();
		if (path[0].startsWith("@")) {
			return element.getAttribute(path[0].substring(1));
		}
		String[] subpath = Arrays.copyOfRange(path, 1, path.length);
		if (path[0].length() == 0) return read(element, subpath, callback);
		NodeList nodes = element.getElementsByTagName(path[0]);
		List<String> result = new ArrayList<>();
		for (int i = 0; i < nodes.getLength(); i++) {
			if (!(nodes.item(i) instanceof Element)) continue;
			String value = read((Element)nodes.item(i), subpath, callback);
			if (value != null) result.add(value);
		}
		if (!result.isEmpty()) callback.apply(result.stream().toArray(String[]::new));
		return null;
	}
	
	public static XMLReader open(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			DocumentBuilder builder = factory.newDocumentBuilder();
			return new XMLReader(builder.parse(file));
		} catch (Exception e) {
			throw new RuntimeException(String.format("Error while opening XML file '%1$s': %2$s", file.getName(), e.getMessage()));
		}
	}
}
