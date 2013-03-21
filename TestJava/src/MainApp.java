

public class MainApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		double alpha = Math.toRadians(270);
		int c = 5;
		
		double x = Math.sin(alpha);
		double a = x*c;
		System.out.println("a>>>>" + a);
		
		x = Math.cos(alpha);
		double b = x*c;
		System.out.println("b>>>>" + b);
		

	}

}
