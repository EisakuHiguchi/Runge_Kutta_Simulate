package ex02;

public class f1 extends f{

	
	
	public f1(int length) {
		super(length);
	}

	@Override
	public double f(double Vm, double m, double h, double n, double t) {
		double Am;
		double Bm;
		
//		Am = -0.1 * (Vm + 50);
//		Am = Am / (Math.exp(-1.0 * (Vm + 50) / 10) - 1);
//		
//		Bm = 4 * Math.exp(-1.0 * (Vm + 75) / 18);
//		
		Am = 0.1*(25.0-Vm)/(Math.exp((25.0-Vm)/10.0)-1.0);
		Bm = 4.0*Math.exp(-1*Vm/18.0);
		
		
		
		return Am * (1.0 - m) - Bm * m;
	}
}
