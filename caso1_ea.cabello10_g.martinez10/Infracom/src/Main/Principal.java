package Main;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ClasesNoMain.Buffer;
import ClasesNoMain.ThreadCliente;
import ClasesNoMain.ThreadServidor;

public class Principal {

	public static final String DIRECCION_ARCHIVO="/Users/Eduardo/Documents/Infracom/src/Main/setUp1.txt";

	public static void main(String args[]) {

		Buffer buf;
		ArrayList servidores= new ArrayList();
		ArrayList clientes=new ArrayList();

		int capacidadBuffer=0;
		int numServidores=0;
		int numClientes=0;
		int numConsulPorCli=0;
		int lineaActual=0;
		try{

			// Abrimos el archivo
			FileInputStream fstream = new FileInputStream(DIRECCION_ARCHIVO);
			// Creamos el objeto de entrada
			DataInputStream entrada = new DataInputStream(fstream);
			// Creamos el Buffer de Lectura
			BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
			String strLinea;
			// Leer el archivo linea por linea
			while ((strLinea = buffer.readLine()) != null)   {
				// Obtenemos la información de la cantidad de elementos
				String[] lin = strLinea.split(":");
				if(lineaActual==0&&strLinea.startsWith("Clientes"))
				{
					numClientes=Integer.parseInt(lin[1]);
				}else if(lineaActual==1&&strLinea.startsWith("Servidores"))
				{

					numServidores=Integer.parseInt(lin[1]);
				}else if(lineaActual==2&&strLinea.startsWith("Numero de consultas por cliente"))
				{
					numConsulPorCli=Integer.parseInt(lin[1]);
				}else if(lineaActual==3&&strLinea.startsWith("Capacidad de buffer"))
				{
					capacidadBuffer=Integer.parseInt(lin[1]);	
				}
				System.out.println (strLinea);
				lineaActual++;
			}
			// Cerramos el archivo
			entrada.close();
			buffer.close();
			fstream.close();
		}catch (Exception e){ //Catch de excepciones
			System.err.println("Ocurrio un error: " + e.getMessage());
		}


		buf = new Buffer(capacidadBuffer,numClientes);
		servidores = new ArrayList();
		clientes = new ArrayList();
		for(int i=0;i<numServidores;i++)
		{
			ThreadServidor ts = new ThreadServidor(buf);
			servidores.add(ts);
			ts.start();
		}
		for(int i=0;i<numClientes;i++)
		{
			ThreadCliente tc = new ThreadCliente(i,buf,numConsulPorCli);
			clientes.add(tc);
			tc.start();
		}
		boolean termino = false;
		while(!termino)
		{
			if(buf.getNumCli()==0)
			{
				termino=true;
			}
		}
		if(termino && buf.getNumMensajesAtendidos()==(numClientes*numConsulPorCli)){
		for(int i=0;i<numServidores;i++)
		{
			ThreadServidor ser =(ThreadServidor) servidores.get(i);
			if(ser.isAlive())
				ser.interrupt();}
		for(int i=0;i<numClientes;i++)
		{
			ThreadCliente cli =(ThreadCliente) clientes.get(i);
			if(cli.isAlive()){
				cli.interrupt();
			}

		}
		}
	}
}
