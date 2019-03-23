package ar.edu.itba.sia.gps.fillzone;

public enum Color {
	WHITE {
		public String getRGB() {
			return "255 255 255";
		}
	}, 
	RED {
		public String getRGB() {
			return "255 0 0";
		}
	}, 
	BLUE {
		public String getRGB() {
			return "0 0 255";
		}
	}, 
	YELLOW {
		public String getRGB() {
			return "255 255 0";
		}
	}, 
	PINK {
		public String getRGB() {
			return "255 0 255";
		}
	},
	GREEN {
		public String getRGB() {
			return "0 255 0";
		}
	};
	
	public abstract String getRGB();
}
