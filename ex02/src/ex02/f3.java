package ex02;

public class f3 extends f{

	public f3(int length) {
		super(length);
	}

	@Override
	public double f(double Vm, double m, double h, double n, double t) {
		double An;
		double Bn;
		
//		An = -0.01 * (Vm + 65);
//		An = An / (Math.exp(-1.0 * (Vm + 65) / 10) - 1);
//		
//		Bn = 0.125 * Math.exp(-1.0 * (Vm + 75) / 80);
		
		An = 0.01*(10.0-Vm)/(Math.exp((10.0-Vm)/10.0)-1.0);
		Bn = 0.125*Math.exp(-1*Vm/80.0);
		
		return An * (1.0 - n) - Bn * n;
	}
	
}
