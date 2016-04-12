package ClasesNoMain;
import ClaseConcurrencia.Semaforo;


public class Mensaje 
{
	private int id;
	private int contenido;
	private int respuesta;
	private Semaforo sem;
	
	public Mensaje(int id, int contenido)
	{
		this.id = id;
		this.contenido = contenido;
		this.respuesta = 0;
		sem = new Semaforo(0);
	}
	
	
	public int getId() {
		return id;
	}

	public int getContenido() {
		return contenido;
	}

	public int getRespuesta() {
		return respuesta;
	}
	
	public void setRespuesta(int resp)
	{
		respuesta = resp;
	}
	public Semaforo getSem() {
		return sem;
	}
	
	public void esperar()
	{
		sem.P(sem);
	}

	public void despertar()
	{
		sem.V(sem);
	}
}
