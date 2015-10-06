package ex02;

public class f2 extends f{

	public f2(int length) {
		super(length);
	}

	@Override
	public double f(double Vm, double m, double h, double n, double t) {
		double Ah;
		double Bh;
		
//		Ah = 0.07 * Math.exp(-1.0 * (Vm + 75) / 20);
//		Bh = 1 / (Math.exp(-1.0 * (Vm + 45) / 10) + 1);
		
		Ah = 0.07*Math.exp(-1*Vm/20.0);
		Bh = 1.0*Math.exp((30.0-Vm)/10.0+1.0);
		
		return Ah * (1.0 - h) - Bh * h;
	}
	
}
