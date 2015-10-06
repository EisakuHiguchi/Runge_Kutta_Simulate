package ex02;

import java.io.FileWriter;

public class ex02 {

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
	 * Cm * (dVm/dt) = I(t) - gl(Vm - El)
	 * - gn(Vm - En) - gk(Vm - Ek)
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
		
		double k0[] = new double[4];
		

		dt = 0.025;
		tmax = 100.0;
		
		
		m = 0.0;
		h = 0.0;
		n = 0.0;
		
		Vm = 0.0;
		
		f[] k = new f[4];
		k[0] = new f1(k.length);
		k[1] = new f2(k.length);
		k[2] = new f3(k.length);
		k[3] = new f4(k.length, dt);
		

		
		// reset parameter
		Vm = 0.0;
		double An = 0.01*(10.0-Vm)/(Math.exp((10.0-Vm)/10.0)-1.0);
		double Bn = 0.125*Math.exp(-1*Vm/80.0);
		double Am = 0.1*(25.0-Vm)/(Math.exp((25.0-Vm)/10.0)-1.0);
		double Bm = 4.0*Math.exp(-1*Vm/18.0);
		double Ah = 0.07*Math.exp(-1*Vm/20.0);
		double Bh = 1.0*Math.exp((30.0-Vm)/10.0+1.0);
		n = An/(An+Bn);
		m = Am/(Am+Bm);
		h = Ah/(Ah+Bh);
		
		
		try {
			FileWriter fw = new FileWriter("output.data");
			FileWriter fw1 = new FileWriter("output1.data");
			
			for(t = 0; t < tmax; t += dt) {
				
				for(int i = 0; i < k.length; i++) {
					k0[i] = dt * k[i].f(Vm, m, h, n, t);
				}
				
				for(int i = 0; i < k.length; i++) {
					k[0].k[i] = dt * k[i].f(
							Vm + k0[3] / 2.0
							, m + k0[0] / 2.0
							, h + k0[1] / 2.0
							, n + k0[2] / 2.0
							, t);
				}
				
				for(int i = 0; i < k.length; i++) {
					k[1].k[i] = dt * k[i].f(
							Vm + k[0].k[3] / 2.0
							, m + k[0].k[0] / 2.0
							, h + k[0].k[1] / 2.0
							, n + k[0].k[2] / 2.0
							, t);
				}
				
				for(int i = 0; i < k.length; i++) {
					k[2].k[i] = dt * k[i].f(
							Vm + k[1].k[3]
							, m + k[1].k[0]
							, h + k[1].k[1]
							, n + k[1].k[2]
							, t);
				}
				
				m = m + (k0[0] + 2.0 * k[0].k[0] + 2.0 * k[1].k[0] + k[2].k[0]) / 6.0; 
				h = h + (k0[1] + 2.0 * k[0].k[1] + 2.0 * k[1].k[1] + k[2].k[1]) / 6.0;
				n = n + (k0[2] + 2.0 * k[0].k[2] + 2.0 * k[1].k[2] + k[2].k[2]) / 6.0;
				Vm = Vm + (k0[3] + 2.0 * k[0].k[3] + 2.0 * k[1].k[3] + k[2].k[3]) / 6.0;

				

				
				fw.write(m + " " + h + " " + n + "\n");
				fw1.write((Vm) + "\n");
			}
			fw.close();
			fw1.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("finish");
		
		
	}
		
}
