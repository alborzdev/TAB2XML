package test;

public class LineErrorException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private int line;
	private String lineActual;
	public LineErrorException(String message, int line) {
		super(message);
		this.line = line;
		this.lineActual = "";
	}
	
	public LineErrorException(String message, int line, String lineActual) {
		super(message);
		this.line = line;
		this.lineActual = lineActual;
	}
	
	public int getLine() {
		return this.line;
	}
	
	public String getString() {
		return this.lineActual;
	}
}
