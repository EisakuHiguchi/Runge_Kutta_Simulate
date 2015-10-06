package ex01;

import java.io.FileWriter;

public class ex01 {

	/*
	 * ラングフォードの方程式
	 * dx/dt = (z - 0.7)x - 3.5y
	 * dy/dt = 3.5x + (z - 0.7)y
	 * dz/dt = a + z - z^3/3 - (x^2 + y^2)(1 + 0.25z)
	 * 
	 */
	
	
	public static void main(String[] args) {
		double t,x,y,z,dt,tmax;
		double k0[] = new double[3];
		
		f[] k = new f[3];
		k[0] = new f1();
		k[1] = new f2();
		k[2] = new f3();
		
		dt = 0.01;
		tmax = 100.0;
		x = 1.0;
		y = 1.0;
		z = 1.0;
		
		
		try {
			FileWriter fw = new FileWriter("output.data");
			
			for(t = 0; t < tmax; t += dt) {
				for(int i = 0; i < 3; i++) {
					k0[i] = dt * k[i].f(t, x, y, z);
				}
				
				for(int i = 0; i < 3; i++) {
					k[0].k[i] = dt * k[i].f(t + dt / 2.0, x + k0[0] / 2.0
							, y + k0[1] / 2.0, z + k0[2] / 2.0);
				}
				
				for(int i = 0; i < 3; i++) {
					k[1].k[i] = dt * k[i].f(t + dt / 2.0, x + k[0].k[0] / 2.0
							, y + k[0].k[1] / 2.0, z + k[0].k[2] / 2.0);
				}
				
				for(int i = 0; i < 3; i++) {
					k[2].k[i] = dt * k[i].f(t + dt, x + k[1].k[0]
							, y + k[1].k[1], z + k[1].k[2]);
				}
				
				x = x + (k0[0] + 2.0 * k[0].k[0] + 2.0 * k[1].k[0] + k[2].k[0]) / 6.0; 
				y = y + (k0[1] + 2.0 * k[0].k[1] + 2.0 * k[1].k[1] + k[2].k[1]) / 6.0;
				z = z + (k0[2] + 2.0 * k[0].k[2] + 2.0 * k[1].k[2] + k[2].k[2]) / 6.0;
				
				fw.write(x + " " + y + " " + z + "\n");
			}
			fw.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println("finish");
		
		
	}
	
}
