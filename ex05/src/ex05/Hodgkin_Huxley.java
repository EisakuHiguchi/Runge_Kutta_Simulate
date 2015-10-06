package ex05;


public class Hodgkin_Huxley {
	/*
	 * ホジキンハクスレー
	 * ルンゲクッタ法
	 * 
	 * dm/dt = Am (1 - m) - Bm*m
	 * Am = -0.1(Vm + 50) / (exp(- (Vm + 50) / 10 ) -1)
	 * Bm = 4exp(- (Vm + 75) / 18)
	 * 
	 * dh/dt = Ah (1 - h) - Bm*h
	 * Ah = 0.07 exp(-(Vm + 75) / 20)
	 * Bh = 1 / (exp(-(Vm + 45) / 10) + 1)
	 * 
	 * dn/dt = An(1 - n) - Bn*n
	 * An = -0.01(Vm + 65) / (exp(-(Vm + 65) / 10) - 1)
	 * Bn = 0.125exp(-(Vm + 75) / 80)
	 * 
	 * I(t) = Cm * (dVm/dt) + gl(Vm - El)
	 * + gn(Vm - En) + gk(Vm - Ek)
	 * 
	 * gn = m*m*m*h*Gn
	 * gk = n*n*n*n*Gk
	 * 
	 */

	protected double Gn, Gk, 
	En, El, Ek, Vm, 
	Cm, gl,	n, m, h;
	
	protected double dt, tmax, t;
	
	protected double iin;
	
	int length = 4;
	protected double[] k0;
	protected double[] k1;
	protected double[] k2;
	protected double[] k3;

	public Hodgkin_Huxley(double dt) {
		k0 = new double[length];
		k1 = new double[length];
		k2 = new double[length];
		k3 = new double[length];

		this.dt = 0.025;

		m = 0.0;
		h = 0.0;
		n = 0.0;
		Vm = 0.0;

		En = 40.0;
		Ek = -87.0;
		El = -64.387;

		Gn = 120.0;
		Gk = 36.0;
		gl = 0.3;
		Cm = 1.0;
		
		iin = 0.0;
	}

	public double getImpulse(double t) {

		k0[0] = dt * f1(t, m, h, n, Vm);
		k0[1] = dt * f2(t, m, h, n, Vm);
		k0[2] = dt * f3(t, m, h, n, Vm);
		k0[3] = dt * f4(t, m, h, n, Vm);

		k1[0] = dt * f1(t + dt / 2.0
				, m + k0[0] / 2.0
				, h + k0[1] / 2.0
				, n + k0[2] / 2.0
				, Vm + k0[3] / 2.0
				);
		k1[1] = dt * f2(t + dt / 2.0
				, m + k0[0] / 2.0
				, h + k0[1] / 2.0
				, n + k0[2] / 2.0
				, Vm + k0[3] / 2.0
				);
		k1[2] = dt * f3(t + dt / 2.0
				, m + k0[0] / 2.0
				, h + k0[1] / 2.0
				, n + k0[2] / 2.0
				, Vm + k0[3] / 2.0
				);
		k1[3] = dt * f4(t + dt / 2.0
				, m + k0[0] / 2.0
				, h + k0[1] / 2.0
				, n + k0[2] / 2.0
				, Vm + k0[3] / 2.0
				);


		k2[0] = dt * f1(t + dt / 2.0
				, m + k1[0] / 2.0
				, h + k1[1] / 2.0
				, n + k1[2] / 2.0
				, Vm + k1[3] / 2.0
				);
		k2[1] = dt * f2(t + dt / 2.0
				, m + k1[0] / 2.0
				, h + k1[1] / 2.0
				, n + k1[2] / 2.0
				, Vm + k1[3] / 2.0
				);
		k2[2] = dt * f3(t + dt / 2.0
				, m + k1[0] / 2.0
				, h + k1[1] / 2.0
				, n + k1[2] / 2.0
				, Vm + k1[3] / 2.0
				);
		k2[3] = dt * f4(t + dt / 2.0
				, m + k1[0] / 2.0
				, h + k1[1] / 2.0
				, n + k1[2] / 2.0
				, Vm + k1[3] / 2.0
				);

		k3[0] = dt * f1(t + dt
				, m + k2[0]
				, h + k2[1]
				, n + k2[2]
				, Vm + k2[3]
				);
		k3[1] = dt * f2(t + dt
				, m + k2[0]
				, h + k2[1]
				, n + k2[2]
				, Vm + k2[3]
				);
		k3[2] = dt * f3(t + dt
				, m + k2[0]
				, h + k2[1]
				, n + k2[2]
				, Vm + k2[3]
				);
		k3[3] = dt * f4(t + dt
				, m + k2[0]
				, h + k2[1]
				, n + k2[2]
				, Vm + k2[3]
				);

		m = m + (k0[0] + 2.0 * k1[0] + 2.0 * k2[0] + k3[0]) / 6.0;
		h = h + (k0[1] + 2.0 * k1[1] + 2.0 * k2[1] + k3[1]) / 6.0;
		n = n + (k0[2] + 2.0 * k1[2] + 2.0 * k2[2] + k3[2]) / 6.0;
		Vm = Vm + (k0[3] + 2.0 * k1[3] + 2.0 * k2[3] + k3[3]) / 6.0;

		return Vm;
	}

	protected double f1(double t, double m, double h, double n, double Vm) {
		double r;
		double Am = -0.1 * (Vm + 50) / (Math.exp(- (Vm + 50) / 10 ) -1);
		double Bm = 4 * Math.exp(- (Vm + 75) / 18);
		r = Am * (1 - m) - Bm*m;
		return r;
	}

	protected double f2(double t, double m, double h, double n, double Vm) {
		double r;
		double Ah = 0.07 * Math.exp(-(Vm + 75) / 20);
		double Bh = 1 / (Math.exp(-(Vm + 45) / 10) + 1);
		r = Ah * (1 - h) - Bh*h;
		return r;
	}

	protected double f3(double t, double m, double h, double n, double Vm) {
		double r;
		double An = -0.01 * (Vm + 65) / (Math.exp(-(Vm + 65) / 10) - 1);
		double Bn = 0.125 * Math.exp(-(Vm + 75) / 80); 
		r = An * (1 - n) - Bn*n;
		return r;
	}

	protected double f4(double t, double m, double h, double n, double Vm) {
		double r;
		double gn = m*m*m*h*Gn;
		double gk = n*n*n*n*Gk;
		r = (I(t) -( gl * (Vm - El) + gn * (Vm - En) + gk * (Vm - Ek))) / Cm;
		return r;
	}

	protected double I(double t) {
		return iin;
	}
	
	public void setI(double iin) { this.iin = iin;}
}
