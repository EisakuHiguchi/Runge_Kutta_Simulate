package ex05;

import java.io.FileWriter;

public class main_ex05 {

	public static void main(String[] args) {
		double t,dt,tmax;

		dt = 0.025;
		tmax = 100.0;
		t = 0.0;

		Hodgkin_Huxley hh = new Hodgkin_Huxley(dt);


		try {

			FileWriter fw = new FileWriter("output.data");
			
			double iin = 3.0;
			
			double temp = 0.0;
			double Vm;
			double dVm = 0.0;
			double dtemp = 0.0;
			
			hh.setI(iin);
			
			for(t = 0.0; t < tmax; t += dt) {
				hh.setI(iin);
				
				Vm = hh.getImpulse(t);
				dVm = (Vm - temp)/dt;
				fw.write(Vm + " ");
				fw.write(dVm + " ");
				fw.write((dVm - dtemp) / dt + "\n");
				temp = Vm;
				dtemp = dVm;
				
				
				if((t > 50) && (t < 80)) {
					iin = 3.0;
				} else {
//					iin = 0.0;
				}
				
//				iin++;
			}
			fw.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("fin");

	}
}
