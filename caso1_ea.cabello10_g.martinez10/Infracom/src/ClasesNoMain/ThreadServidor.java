package ClasesNoMain;

public class ThreadServidor extends Thread{
	
	private boolean status;
	private Buffer buf;
	
	public ThreadServidor(Buffer bu)
	{
		buf=bu;
		status=false;
	}
	
	public void run()
	{
		boolean termino = false;
		boolean terMensaje = false;
		while(!termino)
		{
			buf.getSemServ().P(buf.getSemServ());
			String re = buf.puedeRetirar();
			if(re.equals(buf.SE_PUEDE_RETIRAR))
			{
				Mensaje m = buf.retirar();
				m.setRespuesta(1);
				m.despertar();	
			}else if(re.equals(buf.TERMINO))
			{
				termino=true;
			}
			buf.getSemServ().V(buf.getSemServ());;
		}
	}
}
