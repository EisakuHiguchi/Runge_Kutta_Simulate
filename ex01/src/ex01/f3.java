package ex01;

public class f3 extends f{

	@Override
	public double f(double t, double x, double y, double z) {
		
		double a = 0.8; // この数値を変えることによって多用な解が得られる
		return a + z - (z*z*z / 3.0) - (x*x + y*y) * (1.0 + 0.25 * z);
	}
	
}
