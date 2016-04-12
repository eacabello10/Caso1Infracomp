package ClasesNoMain;
import java.util.ArrayList;

public class ThreadCliente extends Thread
{
	private int mensajes;
	private int id;
	private Buffer buff;
	
	public ThreadCliente(int nId, Buffer nBuff, int nMensajes)
	{
		id = nId;
		buff = nBuff;
		mensajes = nMensajes;
	}
	
	public void run()
	{
		boolean termino = false;
		boolean terMensaje = false;
		
		while(!termino)
		{
			while(!terMensaje)
			{
				
				Mensaje men = new Mensaje(id, mensajes);
				if(buff.puedeEnviaryEnvia(men))
				{
					mensajes--;
					men.esperar();
					terMensaje=true;
				}
				if(terMensaje == false)
				{
					yield();
				}
			}
			terMensaje=false;
			if(mensajes==0)
			{
				termino = true;
			}
		}
		buff.retiraCliente();
		System.out.println(buff.getNumMensajesAtendidos());
	}
}
