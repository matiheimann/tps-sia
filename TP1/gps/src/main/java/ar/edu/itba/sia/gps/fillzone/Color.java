package ar.edu.itba.sia.gps.fillzone;

public enum Color {
	
	WHITE(0), RED(1), BLUE(2), YELLOW(3), PINK(4), GREEN(5);
	
	private int value;
	
	private Color(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
