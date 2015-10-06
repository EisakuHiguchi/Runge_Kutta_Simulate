package ex03;

import java.io.FileWriter;

public class ex03 {

	/*
	 * ホジキンハクスレー
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
	
	
	public static void main(String[] args) {
		double n,m,h,Gn,Gk,gl,Cm;
		double Vm,El,En,Ek;
		double gn, gk;
//		double I;
		double dt, tmax, t;
		double Vrest;
		
//		int length = 4;
//		double[] k0 = new double[length];
//		double[] k1 = new double[length];
//		double[] k2 = new double[length];
//		double[] k3 = new double[length];
		
		dt = 0.025;
		tmax = 100.0;
		
		Vrest = -65.0;
		En = 50.0;
		Ek = -77.0;
		El = -54.387;
		
		Gn = 120.0;
		Gk = 36.0;
		gl = 0.3;
		Cm = 1.0;
		
		
		m = 0.0;
		h = 0.0;
		n = 0.0;
		
		Vm = 0.0;
		
		
		try {
			FileWriter fw = new FileWriter("output.data");
			
			// reset parameters
			Vm = 0.0;
			double An = 0.01*(10.0-Vm)/(((10.0-Vm)/10.0)-1.0);
			double Bn = 0.125*(-1*Vm/80.0);
			double Am = 0.1*(25.0-Vm)/(((25.0-Vm)/10.0)-1.0);
			double Bm = 4.0*(-1*Vm/18.0);
			double Ah = 0.07*(-1*Vm/20.0);
			double Bh = 1.0*((30.0-Vm)/10.0+1.0);
			n = An/(An+Bn);
			m = Am/(Am+Bm);
			h = Ah/(Ah+Bh);
			
			double[] Vms = new double[(int)(tmax/dt) + 1];
			double[] Istims = new double[(int)(tmax/dt) + 1];
			double[] Inas = new double[(int)(tmax/dt) + 1];
			double[] Iks = new double[(int)(tmax/dt) + 1];
			double[] Gnas = new double[(int)(tmax/dt) + 1];
			double[] Gks = new double[(int)(tmax/dt) + 1];
			
			double Istim;
			double sdelay = 25.0;
			double sduration = 50.0;
			
			double sampl = 10;
			
			for(double j = 0; j < tmax; j = j + dt) { //time sequence loop
				if( ((sdelay/dt)<=j) && (j<=((sdelay+sduration) / dt)) ) { Istim = sampl; }
				else { Istim = 0.0; }

				An = 0.01  * (10.0-Vm) / (((10.0-Vm)/10.0)-1.0);
				Bn = 0.125 * (-1*Vm/80.0);
				Am = 0.1   * (25.0-Vm) / (((25.0-Vm)/10.0)-1.0);
				Bm = 4.0   * (-1*Vm/18.0);
				Ah = 0.07  * (-1*Vm/20.0);
				Bh = 1.0 / (((30.0-Vm)/10.0)+1.0);
				n  = n + (An*(1.0-n)-Bn*n) * dt;
				m  = m + (Am*(1.0-m)-Bm*m) * dt;
				h  = h + (Ah*(1.0-h)-Bh*h) * dt;

				gk  = Gk  * (n*n*n*n);
				gn = Gn * (m*m*m) * h;
//				gl  = Gl;

				double Ik  = gk  * (Vm + Vrest - Ek);
				double Ina = gn * (Vm + Vrest - En);
				double Il  = gl  * (Vm + Vrest - El);
				double Ichannel = Ik+Ina+Il;

				double Im = Istim - Ichannel;
				Vm = Vm + (Im / Cm)*dt;

				Vms[(int)j]    = Vm + Vrest;
				Istims[(int)j] = Istim;
				Inas[(int)j]   = Ina;
				Iks[(int)j]    = Ik;
				Gnas[(int)j]   = gn;
				Gks[(int)j]    = gk;
			} //time sequence loop			
			
			
			for(int i = 0; i < Vms.length; i++) {
				fw.write(
				Vms[i] + " " +
				Istims[i] + " " +
				Inas[i] + " " +
				Iks[i] + " " +
				Gnas[i] + " " +
				Gks[i] + "\n");
			}
			
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("finish");
		
		
	}	
}
