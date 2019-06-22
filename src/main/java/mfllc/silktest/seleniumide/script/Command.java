package mfllc.silktest.seleniumide.script;

public class Command {
	private String name;
	private String target;
	private String value;
	
	public Command(String[] args) {
		name = args[0];
		target = args[1];
		value = args[2];
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
