package ClasesNoMain;
import java.util.ArrayList;

import ClaseConcurrencia.Semaforo;

public class Buffer 
{
	public static final String SE_PUEDE_RETIRAR="Puede retirar";
	public static final String NOSE_PUEDE_RETIRAR="No se puede retirar";
	public static final String TERMINO="Termino";
	private int maximo;
	private ArrayList listaMensajes;
	private Semaforo semServidor;
	private int numClientes;
	private int numMensajesAtendidos;
	public Buffer(int nMax, int numCli)
	{
		maximo = nMax;
		numClientes = numCli;
		listaMensajes = new ArrayList();
		semServidor=new Semaforo(1);
		numMensajesAtendidos=0;
	}

	public synchronized boolean puedeEnviaryEnvia(Mensaje m)
	{
		if(listaMensajes.size() < maximo)
		{
			listaMensajes.add(m);
			numMensajesAtendidos++;
			return true;
		}
		return false;
	}


	public synchronized String puedeRetirar()
	{
		String resp = NOSE_PUEDE_RETIRAR;
		if(listaMensajes.size()>0)
		{
			resp = SE_PUEDE_RETIRAR;
		}else if(numClientes==0)
		{
			resp=TERMINO;
		}
		return resp;
	}

	public synchronized Mensaje retirar()
	{
		Mensaje m = (Mensaje) listaMensajes.get(0);
		listaMensajes.remove(m);
		return m;
	}
	public void retiraCliente()
	{
		numClientes--;
	}
	public Semaforo getSemServ()
	{
		return semServidor;
	}
	public int getNumCli(){
		return numClientes;
	}

	public int getNumMensajesAtendidos()
	{
		return numMensajesAtendidos;
	}

}
