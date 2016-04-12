package ClaseConcurrencia;


import java.util.ArrayList;

public class Semaforo {

	private int contador;

	public Semaforo(int cont)
	{
		contador=cont;
	}

	public synchronized void P ( Semaforo s ) {
		s.contador--;
		if ( s.contador < 0 ){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public synchronized void V ( Semaforo s ) {
		s.contador++;
		if ( s.contador <= 0 ){
			notify();
		}
	} 
}
