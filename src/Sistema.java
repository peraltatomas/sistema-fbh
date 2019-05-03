import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
/* Se podran realizar cuadros de categorias sub 10, 12, 14, 16 y 18 masculino y femenino. 
 * Estas se codificaran a partir de un valor entero, el cual indicara que categorias son las que se estan procesando
 * Los valores son:
 * Sub 10 M = 1 .... 0
 * Sub 10 F = 2 .... 1
 * Sub 12 M = 4 .... 2
 * Sub 12 F = 8 .... 3
 * Sub 14 M = 16 ... 4 
 * Sub 14 F = 32 ... 5
 * Sub 16 M = 64 ... 6
 * Sub 16 F = 128 .. 7
 * Sub 18 M = 256 .. 8
 * Sub 18 F = 512 .. 9
 */
public class Sistema {

	private Torneo[] torneos;
	private String archivoadm;
	private String Organizador;
	private String grado;
	//private boolean dobles;
	private String ciudad;
	private boolean antesG1;
	
public Sistema (String archivo, String organiz, String Gr, boolean Dobles, String ciu, boolean aG1) {
	try{
		archivoadm = archivo;
		Organizador = organiz;
		//dobles = Dobles;
		grado = Gr;
		torneos = new Torneo[10]; // 10 categorias
		ciudad = ciu;
		antesG1 = aG1;
		
		for (int i=0; i<10;i++)
			torneos[i] = null;
		CargarTorneos();
	}
	catch(IOException e){};
	
}
public Sistema (String archivo, String organiz, String Gr, String ciu, int j, boolean aG1) {
	try{
		archivoadm = archivo;
		Organizador = organiz;
		grado = Gr;
		torneos = new Torneo[10]; // 10 categorias
		ciudad = ciu;
		antesG1 = aG1;
		for (int i=0; i<10;i++)
			torneos[i] = null;
		if (j == 1)
			CargarTorneos();
	}
	catch(IOException e){};
	
}

public Sistema(String archivo, String Gr) {
		archivoadm = archivo;
		Organizador = "";
		grado = Gr;
		torneos = new Torneo[10]; // 10 categorias
		ciudad = "";
}

private void CargarTorneos()  throws FileNotFoundException, IOException {
	String cadena = null;
	String cadena2 = null;
    String Nombre = "";
    String Apellido = "";
    String Categoria = ""; 
    String Federacion = "";
    String Nacionalidad = ""; 
    String TipoDoc = "";
    String Provincia = ""; 
    String Fecha = "";
    char genero = ' ';
    String NroDoc = "";
    String Localidad = "";
    String Direccion = "";
    String CP = "";
    String Telefono = "";
    String email = "";
    String Nrocasa = "";
    String Dpto = "";
    String celular = "";
    String Club = "";
    boolean comillas = false;
    
    int i, ind;
    Jugador j;
  //Elimino el archivo con jugadores rechazados, si es que hay archivo antiguo
  		String ruta = "./Cuadros/JugadoresRechazados.txt";
  		File archivos = new File(ruta);
  		if(!archivos.createNewFile())
  			archivos.delete();
    FileReader f = new FileReader(archivoadm);
    BufferedReader b = new BufferedReader(f);
    cadena = b.readLine();
    if (cadena.charAt(0) == '"')
    	comillas = true;
    while((cadena2 = b.readLine())!=null) { 
    	cadena = "";
    	if (comillas){
    		for (int r = 0; r<cadena2.length();r++)
    			if(cadena2.charAt(r) != '"')
    				cadena+=cadena2.charAt(r);
    		}
    	else
    		cadena = cadena2;
    				
    	i = 0;
    	Nombre = Apellido = Categoria = Federacion = Nacionalidad = TipoDoc = Provincia = Fecha = NroDoc = Localidad =
    			Direccion = CP = Telefono = email = Dpto = Club = Nrocasa = celular = "";
    	genero = ' ';
            	while (cadena.charAt(i) != ',')//Adelanto el tiempo de submit
        		i++;
        	i++;//Salteo la ,
        	if(cadena.charAt(i)!= ',')
        		Nombre += cadena.toUpperCase().charAt(i++);
        	while (cadena.charAt(i) != ','){//Cargo el nombre
            	Nombre += cadena.toLowerCase().charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	if(cadena.charAt(i)!= ',')
        		Apellido += cadena.toUpperCase().charAt(i++);
        	while (cadena.charAt(i) != ','){//Cargo el apellido
            	Apellido += cadena.toLowerCase().charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	if(cadena.charAt(i)!= ',')
        		email += cadena.toUpperCase().charAt(i++);
        	while (cadena.charAt(i) != ','){//Cargo el email
            	email += cadena.toLowerCase().charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo el genero
            	genero = cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	if(cadena.charAt(i)!= ',')
        		TipoDoc += cadena.toUpperCase().charAt(i++);
        	while (cadena.charAt(i) != ','){//Cargo el TipoDoc
            	TipoDoc += cadena.toLowerCase().charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo el DNI
            	if (cadena.charAt(i) != '.') //Elimino los puntos del DNI
            		NroDoc += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo anio
            	Fecha += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	String mes = "";
        	while (cadena.charAt(i) != ','){//Cargo mes
            	mes += cadena.charAt(i);
            	i++;
        	}
        	mes = codificacion(mes);
        	Fecha += mes;
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo dia
            	Fecha += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo la federacion
            	Federacion += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo la Categoria
            	Categoria += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo la Nacionalidad
            	Nacionalidad += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo la Provincia
            	Provincia += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	if(cadena.charAt(i)!= ',')
        		Localidad += cadena.toUpperCase().charAt(i++);
        	while (cadena.charAt(i) != ','){//Cargo la Ciudad
            	Localidad += cadena.toLowerCase().charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo el CP
            	CP += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	if(cadena.charAt(i)!= ',')
        		Direccion += cadena.toUpperCase().charAt(i++);
        	while (cadena.charAt(i) != ','){//Cargo la Direccion
            	Direccion += cadena.toLowerCase().charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	if(cadena.charAt(i)!= ',')
        		Dpto += cadena.toUpperCase().charAt(i++);
        	while (cadena.charAt(i) != ','){//Cargo el Dpto
            	Dpto += cadena.toLowerCase().charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	if(cadena.charAt(i)!= ',')
        		Club += cadena.toUpperCase().charAt(i++);
        	while (cadena.charAt(i) != ','){//Cargo el club
            	Club += cadena.toLowerCase().charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo el Telefono
            	Telefono += cadena.charAt(i);
        		i++;
        	}
        	i++; //Salteo las comillas de ambos lados y la ,
        	while (cadena.charAt(i) != ','){//Cargo el celular
            	celular += cadena.charAt(i);
        		i++;
        	}
        	j = new Jugador(Nombre,Apellido,Federacion, Club, Nacionalidad, TipoDoc,
        			Provincia, genero, Fecha, NroDoc, Localidad, Direccion, CP, Telefono,
        			email, Nrocasa, Dpto, celular);
        	ind = IndiceCat(Categoria);
        	if (torneos[ind] == null){
        		torneos[ind] = new Torneo (grado, Categoria, Organizador, ciudad, antesG1);
        		}
        	torneos[ind].AgregarJugador(j);
        }
    b.close();
}
private String codificaciondia(String dia) {
	String salida = "";
	if (dia.equals("1"))
		salida = "01";
	else if (dia.equals("2"))
			salida = "02";
		else if (dia.equals("3"))
			salida = "03";
			else if (dia.equals("4"))
				salida = "04";
				else if (dia.equals("5"))
					salida = "05";
					else if (dia.equals("6"))
						salida = "06";
						else if (dia.equals("7"))
							salida = "07";
							else if (dia.equals("8"))
								salida = "08";
								else if (dia.equals("9"))
									salida = "09";
									else salida = dia; 
	return salida;
}
private String codificacion(String mes) {
	String salida = "";
	if (mes.equals("Enero"))
		salida = "01";
	else if (mes.equals("Febrero"))
			salida = "02";
		else if (mes.equals("Marzo"))
			salida = "03";
			else if (mes.equals("Abril"))
				salida = "04";
				else if (mes.equals("Mayo"))
					salida = "05";
					else if (mes.equals("Junio"))
						salida = "06";
						else if (mes.equals("Julio"))
							salida = "07";
							else if (mes.equals("Agosto"))
								salida = "08";
								else if (mes.equals("Septiembre"))
									salida = "09";
									else if (mes.equals("Octubre"))
										salida = "10";
										else if (mes.equals("Noviembre"))
											salida = "11";
											else if (mes.equals("Diciembre"))
												salida = "12";
	return salida;
}

private int IndiceCat(String categoria) {
	int salida = -1;
	switch ( categoria ) {
    case "Sub 10 (Varones)":
        salida = 0;
        break;
   case "Sub 10 (Mujeres)":
	   salida = 1;
	   break;
   case "Sub 12 (Varones)":
	   salida = 2;
	   break;
   case "Sub 12 (Mujeres)":
	   salida = 3;
	   break;
   case "Sub 14 (Varones)":
	   salida = 4;
	   break;
   case "Sub 14 (Mujeres)":
	   salida = 5;
	   break;
   case "Sub 16 (Varones)":
	   salida = 6;
	   break;
   case "Sub 16 (Mujeres)":
	   salida = 7;
	   break;
   case "Sub 18 (Varones)":
	   salida = 8;
	   break;
   case "Sub 18 (Mujeres)":
	   salida = 9;
	   break;
   }
	return salida;
}

public void Aceptaciones(){
	for (int i=0; i<10;i++)
		if (torneos[i] != null)
			try {
				torneos[i].GetInscripciones();
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}

public void Cuadros(){
	for (int i=0; i<10;i++)
		if (torneos[i] != null)
			try {
				torneos[i].GetCuadro();
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
}
public void CierreTorneo(int opcion){//Hago todo lo referido al cierre del torneo, PDF hasta donde se cargue, archivos AAT
	File archivo = new File ("./Resultados.txt");
	String cadena;
	Character sexo = 'M';
	String categoria = "";
	String dobles = "";
	String cuadro = "";
	String Jugadores = "";
	String Byes = "";
	String MD = "";
	int encontro = 0;
	String [] participantes = new String [1];
	String [] resultados = new String [1];
	int espacioscuadro;
	int espacios = 0;
	FileReader f;
	String l = "";
	String sexo2 = "";
	int i;
	boolean terminotorneo = false;
	try 
	{
			FileWriter w = new FileWriter(archivo);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);
			wr.write("");
			wr.close();
			f = new FileReader(archivoadm);
			BufferedReader b = new BufferedReader(f);
			cadena = b.readLine();
			while(cadena != null) 
			{
				terminotorneo = false;
				espacios = 0;
				while (!terminotorneo && cadena != null)
				{
					sexo = 'M';
					categoria = "";
					dobles = "";
					cuadro = "";
					Jugadores = "";
					Byes = "";
					MD = "";
					sexo2 = "";
					sexo = cadena.charAt(5);
					cadena = b.readLine();
					categoria += cadena.substring(10, 12);
					cadena = b.readLine();
					i = 19;
					while (cadena.charAt(i) != ',')
					{
						Jugadores += cadena.charAt(i);
						i++;
					}
					cadena = b.readLine();
					i = 14;
					while (cadena.charAt(i) != ',')
					{
						Byes += cadena.charAt(i);
						i++;
					}
					cadena = b.readLine();
					dobles += cadena.substring(7, 9);
					cadena = b.readLine();
					cuadro += cadena.substring(11, 13);
					cadena = b.readLine();
					MD += cadena.substring(14, 16);
					cadena = b.readLine();
					cadena = b.readLine();
					espacioscuadro = Integer.parseInt(Jugadores)+Integer.parseInt(Byes);
					if (cuadro.equalsIgnoreCase("NO") &&(espacioscuadro == 4 || espacioscuadro == 5))
						espacioscuadro = 8;
					int aux = espacioscuadro;
					while (espacioscuadro != 1)
					{
						espacios += espacioscuadro;
						espacioscuadro /= 2;
					}
					espacios++;
					participantes = new String[espacios]; // Hay lugar para todos los nombres del torneo por ej, en cuadro de 16 hay 16+8+4+2+1 lugares, en 32 32+16+8+4+2+1
					for (int u=0;u<espacios;u++)
						participantes[u] = "";
					resultados = new String[espacios - aux];
					for (int u=0;u<espacios-aux;u++)
						resultados[u] = "";
					for (int u=0;u<espacios;u++)
						participantes[u] = "";
					if(cuadro.equalsIgnoreCase("SI"))//es cuadro
					{
						resultados = new String[espacios - aux];
						for (int u=0;u<espacios-aux;u++)
							resultados[u] = "";
					}
					else//es zona
					{
						resultados = new String[espacios];
						for (int u=0;u<espacios;u++)
							resultados[u] = "";	
					}
					espacioscuadro = Integer.parseInt(Jugadores)+Integer.parseInt(Byes);
					if (dobles.equalsIgnoreCase("SI")){
						if (cadena.contains("PAREJAS:"))
							for(int t = 0; t<espacioscuadro;t++){//Agrego todos los equipos en primera ronda
								i = 0;
								cadena = b.readLine();
								encontro = 0;
								if (!cadena.toLowerCase().contains("bye")){
									while (i<cadena.length()){
										if((cadena.charAt(i) != ',' || encontro<2) && cadena.charAt(i) != '"'){	
											if (cadena.charAt(i) != ',' || encontro<2)
												participantes[t] +=cadena.charAt(i);
											if (cadena.charAt(i) == ',')
												encontro++;
										}
										i++;
									}
								}
								else
								{
									while (i<cadena.length()){
										if(cadena.charAt(i) != ',')
											participantes[t] += cadena.charAt(i);
										i++;
									}
								}
							}
						cadena = b.readLine();
						cadena = b.readLine();
					}
    			
					if (cadena.contains("RESULTADOS:"))
					{//Cargo los resultados
						if (cuadro.equalsIgnoreCase("SI"))
						{//es cuadro
							for(int t = 0; t<espacios-aux;t++)
							{
								i = 0;
								cadena = b.readLine();
								while (cadena.charAt(i)!= ',')
								{
									resultados[t] +=cadena.charAt(i);
									i++;
								}
							}
						}
						else
						{//es zona
							int t = 0;
							boolean encontre = false;
							while (!encontre && (cadena = b.readLine()) != null)
							{
								i = 0;
								if(cadena.charAt(0) == ',')
									encontre = true;
								else
									while (cadena.charAt(i)!= ',')
									{
										resultados[t] +=cadena.charAt(i);
										i++;
									}
								t++;
							}
						}
    			}
					if ((cadena = b.readLine()) != null)
					{
						terminotorneo = true;
						if (cuadro.equalsIgnoreCase("SI"))
							cadena = b.readLine();
						cadena = b.readLine(); //Acomodo el cursor al proximo torneo
					}

   		//Completo el arreglo de participantes segun los resultados
    		
					if(dobles.equalsIgnoreCase("SI"))
					{//Es dobles ya tengo cargada la primera ronda
						if (cuadro.equalsIgnoreCase("SI"))
						{
							int p1 = 0;
							int r = 0;
							int p = Integer.parseInt(Jugadores) + Integer.parseInt(Byes); //Posicion en la cual comienzo a agregar (primer equipo 2da ronda)
							while (r < resultados.length)
							{
								if (!participantes[p1].equalsIgnoreCase("Bye") && !participantes[p1+1].equalsIgnoreCase("Bye")) //Son ambos jugadores 
								{
									if (EvaluacionResultado(resultados[r]))
									{
										participantes[p] = participantes [p1];
									}
									else
									{
										participantes[p] = participantes[p1+1];
									}
								}
								else // Alguno de los dos es bye
								{	
								if (participantes[p1].equalsIgnoreCase("Bye")) 
									participantes[p] = participantes[p1+1];
								else
									participantes[p] = participantes[p1];}
								p++;
								p1 += 2;
								r++;
							}	
						}
						else
						{//Es zona de dobles
							;
						}
					}
					else{ //Es cuadro de single tengo que leer el txt que creo el programa
						if (sexo == 'M')
							sexo2 = "(Varones)";
						else
							sexo2 = "(Mujeres)";
						String sdirect = "./Config/AATtxt";
						l = "./Config/AATtxt/";
						File e = new File (sdirect);
						if (e.exists())
						{
							File[] ficheros = e.listFiles();
							for (int x=0;x<ficheros.length;x++){
								if (ficheros[x].getName().contains("Sub "+categoria) && ficheros[x].getName().contains(sexo2))
									l += ficheros[x].getName(); 
							}
						}
						f = new FileReader(l);
						BufferedReader c = new BufferedReader(f);
						c.readLine();
						int primeraronda = 0;
						int charac = 0;
						String jug = "";
						String cadenas;
						int cant = Integer.parseInt(Jugadores) + Integer.parseInt(Byes);
						while (primeraronda < cant)
						{
							charac = 0;
							jug = "";
							cadenas = c.readLine();
							if (!cadenas.contains("BYE")){
								while (cadenas.charAt(charac) != '-')
								{
									jug += cadenas.charAt(charac);
									charac++;
								}
								participantes[primeraronda++] = jug;
							}
							else
								participantes[primeraronda++] = "BYE";
						}
						c.close();
    	    if (cuadro.equalsIgnoreCase("SI")){
        		int p1 = 0;
        		int r = 0;
        		int p = Integer.parseInt(Jugadores) + Integer.parseInt(Byes); //Posicion en la cual comienzo a agregar (primer equipo 2da ronda)
        		while (r < resultados.length){
        		if (!participantes[p1].equalsIgnoreCase("Bye") && !participantes[p1+1].equalsIgnoreCase("Bye")) //Son ambos jugadores 
        			if (EvaluacionResultado(resultados[r]))
        				participantes[p] = participantes [p1];
        			else
        				participantes[p] = participantes[p1+1];
        		else // Alguno de los dos es bye
        			if (participantes[p1].equalsIgnoreCase("Bye")) 
        				participantes[p] = participantes[p1+1];
        			else
        				participantes[p] = participantes[p1];
        		p++;
        		p1 += 2;
        		r++;
    	    }}
    	    else{//es zona
    	    	;
    	    }
    	}
    	if (sexo == 'M')
    		sexo2 = "(Varones)";
    	else
    		sexo2 = "(Mujeres)";
    	String sdirect = "./Config/AATtxt";
    	l = "./Config/AATtxt/";
    	File e = new File (sdirect);
    	if (e.exists()){
    		File[] ficheros = e.listFiles();
    		for (int x=0;x<ficheros.length;x++){
    			if (ficheros[x].getName().contains("Sub "+categoria) && ficheros[x].getName().contains(sexo2))
    				l += ficheros[x].getName(); 
    		}
    	}
    	if (opcion == 1)
    		CrearArchivoAAT(participantes, resultados,sexo,categoria,dobles,cuadro,MD, Integer.parseInt(Jugadores)+Integer.parseInt(Byes),Integer.parseInt(Jugadores),l);
    	else
    		CompletarCuadros(participantes, resultados,sexo,categoria,dobles,cuadro,MD, Integer.parseInt(Jugadores)+Integer.parseInt(Byes),Integer.parseInt(Jugadores));
    }}	
    b.close();
    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    	

}

private boolean EvaluacionResultado(String string) {// analizo el resultado del partido y devuelvo quien gano, true el primero, false el segundo
	if (string.contains("WO 1"))
		return false;
	if (string.contains("WO 2"))
		return true;
	if (string.contains("abandono 1"))
		return false;
	if (string.contains("abandono 2"))
		return true;
	int sets1 = 0;
	int sets2 = 0;
	String games1 = "";
	String games2 = "";
	int i = 0;
	while (i< string.length() && (sets1 != 2 || sets2 != 2)){
		games1 = "";
		games2 = "";
		while (i< string.length() && string.charAt(i) != '-'){
			games1 += string.charAt(i++);
	}
		i++;
		while (i< string.length() && string.charAt(i) != '/'){
			games2 += string.charAt(i++);
	}
		if (Integer.parseInt(games1) > Integer.parseInt(games2))
			sets1++;
		else
			sets2++;
		i++;
		}
	if(sets1 == 2 || sets1 > sets2)
		return true;
	else
		return false;
}
private void CrearArchivoAAT(String[] participantes, String[] resultados,
		char sexo, String categoria, String dobles, String cuadro, String mD, int tamanocuadro, int jugadores, String archivoaat) {
	//A partir del excel armo el archivo a entregar a la AAT, funcionalidad separada de los cuadros y torneos
	File archivo;
	String resultado = "";
	String jugador = "";
	boolean haybye = false;
	int o, o2, u;
	o = 0;
	String cadena = ""; //IR AGREGANDO ESPACIOS ENTRE MEDIO DEPENDE DONDE CORRESPONDA
	archivo = new File ("./Resultados.txt");
	try{
		FileWriter w = new FileWriter(archivo, true);
		BufferedWriter bw = new BufferedWriter(w);
		PrintWriter wr = new PrintWriter(bw);
		int res = 0;
		while (res<resultados.length)
		{
			haybye = false;
			cadena = "";
			if (resultados[res].equalsIgnoreCase("BYE") || resultados[res].equalsIgnoreCase(""))
				haybye = true;
				//Cargo grado 1-50
				cadena += grado;
				if (cadena.length() != 50)
				{
					wr.append(" ");
					for (int i = 49-cadena.length();i>0;i--)
						wr.append(" ");
					wr.append(cadena);
				}
				else
					wr.append(cadena);
				
				//cargo area, siempre 4
				wr.append('4');
				
				//cargo categoria
				if (sexo == 'M')
					cadena = "Varones ";
				else
					cadena = "Damas ";
				cadena += categoria;
				if (dobles.equalsIgnoreCase("SI"))
					cadena += " Dobles";
				if (cadena.length() != 50){
					for (int i = 50-cadena.length();i>0;i--)
						wr.append(" ");
					wr.append(cadena);
				}
				else
					wr.append(cadena);
	
				//cargo sexo
				wr.append(sexo);
	
				//cargo es dobles
				if (dobles.equalsIgnoreCase("SI")){
					wr.append('1');}
				else{
					wr.append('0');}

				//Cargo Sede
				cadena = ciudad ;
				if (cadena.length() != 50){
					for (int i = 50-cadena.length();i>0;i--)
						wr.append(" ");
					wr.append(cadena);
				}
				else
					wr.append(cadena);
	

				//Cargo Torneo
				Calendar fecha = Calendar.getInstance();
		        int anio = fecha.get(Calendar.YEAR);
				cadena = ""+ anio + " - " + grado + " - " + Organizador + " - FBH";
				if (cadena.length() != 50){
					for (int i = 50-cadena.length();i>0;i--)
						wr.append(" ");
					wr.append(cadena);
				}
				else
					wr.append(cadena);
	

				//cargo tipo torneo
				cadena = "U";
				if (cadena.length() != 10){
					for (int i = 10-cadena.length();i>0;i--)
						wr.append(" ");
					wr.append(cadena);
				}
				else
					wr.append(cadena);
				
	

				//cargo zonas
				if (cuadro.equalsIgnoreCase("SI")){
					wr.append('0');}
				else{
					wr.append('1');}

				if(cuadro.equalsIgnoreCase("NO")){ //Completo info de zonas
					int zonas = 0;
					int zonanro = 0;
					int equiposxzona = 0;
					switch (jugadores){
					case 4:{
						zonas = 1;
						equiposxzona = 4;
						break;
					}
					case 5:{
						zonas = 1;
						equiposxzona = 5;
						break;
					}
					case 6:{
						zonas = 2;
						equiposxzona = 3;
						break;
					}
					case 7:{
						zonas = 2;
						equiposxzona = 4;
						break;
					}
					case 8:{
						zonas = 2;
						equiposxzona = 4;
						break;
					}
					case 9:{
						zonas = 3;
						equiposxzona = 3;
						break;
					}
					case 10:{
						zonas = 4;
						equiposxzona = 3;
						break;
					}
					case 11:{
						zonas = 4;
						equiposxzona = 3;
						break;
					}
					case 12:{
						zonas = 4;
						equiposxzona = 4;
						break;
					}
					case 13:{
						zonas = 4;
						equiposxzona = 4;
						break;
					}
					case 14:{
						zonas = 4;
						equiposxzona = 4;
						break;
					}
					case 15:{
						zonas = 4;
						equiposxzona = 4;
						break;
					}
					case 16:{
						zonas = 4;
						equiposxzona = 4;
						break;
					}
					}
					
					//Cargo posicion zona 1
					int posicion = 0;
					int posicion2 = 0;
					int result = res;
					if(equiposxzona == 5){
						result++;
						if (result < 5)
							posicion = 0;
						if (result >= 5 && result<8)
							posicion = 1;
						if (result>=8 && result<10)
							posicion = 2;
						if (result == 10)
							posicion = 3;
					}
					else
						if (equiposxzona == 4){
							zonanro = res / 6;
							result = (result % 6)+1;
							if (result < 4)
								posicion = 0;
							if (result >= 4 && result<6)
								posicion = 1;
							if (result == 6)
								posicion = 2;
						}
						else if (equiposxzona == 3){
							zonanro = res / 3;
							result = (result % 3)+1;
							if (result < 3)
								posicion = 0;
							if (result == 3)
								posicion = 1;
						}
					wr.append(" "+posicion);
					
					//Cargo posicion 2 zona
					result = res;
					if(equiposxzona == 5){
						result++;
						if (result == 1)
							posicion2 = 1;
						if (result == 2 || result == 5)
							posicion2 = 2;
						if (result == 3 || result == 6 || result == 8)
							posicion2 = 3;
						if (result == 4 || result == 7 || result == 9 || result == 10)
							posicion2 = 4;
					}
					else
						if (equiposxzona == 4){
							result = (result % 6)+1;
							if (result == 1)
								posicion2 = 1;
							if (result == 2 || result == 4)
								posicion2 = 2;
							if (result == 3 || result == 5 || result == 6)
								posicion2 = 3;

						}
						else if (equiposxzona == 3){
							result = (result % 3)+1;
							if (result == 1)
								posicion2 = 1;
							if (result == 2 || result == 3)
								posicion2 = 2;

						}
					wr.append(" "+ posicion2);
					//Cargo cantidad zonas
					wr.append(" "+zonas);
					//Cargo numero de zona
					wr.append(" "+zonanro);
					//Cargo equipos por zona
					wr.append("  "+equiposxzona);

					//Info cuadro en blanco
					for(int v = 0; v<10;v++)
						wr.append(" ");
					int aux = 1;
					if(equiposxzona == 4)
						aux = 4;
					else
						if(equiposxzona == 3)
							aux = 3;
					//Cargo jugador 1
					if(!participantes[posicion+(zonanro*aux)].equalsIgnoreCase("BYE")){
					o = 0; jugador = "";
					while (o<participantes[posicion+(zonanro*aux)].length() && participantes[posicion+(zonanro*aux)].charAt(o) != '/'){
						jugador += participantes[posicion+(zonanro*aux)].charAt(o);
						o++;
					}
					cadena = Documento(jugador, archivoaat);
					if (cadena.length() != 8){
						for (int i = 8-cadena.length();i>0;i--)
							wr.append(" ");
						wr.append(cadena);
					}
					else
						wr.append(cadena);

					if(dobles.equalsIgnoreCase("NO")){	
						//Cargo 8 espacios jugador vacio
						for (int t=0;t<8;t++)
							wr.append(" ");
					}
					else{
						//Cargo companiero 1
						jugador = participantes[posicion+(zonanro*aux)].substring(o+1, participantes[posicion+(zonanro*aux)].length());
					cadena = Documento(jugador, archivoaat);
					if (cadena.length() != 8){
						for (int i = 8-cadena.length();i>0;i--)
							wr.append(" ");
						wr.append(cadena);
					}
					else
						wr.append(cadena);
					}
					}
					else //es BYE, cargo 16 espacios en blanco
						for (int i = 0;i<16;i++)
							wr.append(" ");
					//Cargo jugador 2
					o2 = 0; jugador = "";
					if(!participantes[posicion2+(zonanro*aux)].equalsIgnoreCase("BYE")){	
					while (o2<participantes[posicion2+(zonanro*aux)].length() && participantes[posicion2+(zonanro*aux)].charAt(o2) != '/'){
						jugador += participantes[posicion2+(zonanro*aux)].charAt(o2);
						o2++;
					}
					cadena = Documento(jugador, archivoaat);
					if (cadena.length() != 8){
						for (int i = 8-cadena.length();i>0;i--)
							wr.append(" ");
						wr.append(cadena);
					}
					else
						wr.append(cadena);

					if(dobles.equalsIgnoreCase("NO")){	
						//Cargo 8 espacios jugador vacio
						for (int t=0;t<8;t++)
							wr.append(" ");
					}
					else{
						//Cargo companiero 2
						jugador = participantes[posicion2+(zonanro*aux)].substring(o2+1, participantes[posicion2+(zonanro*aux)].length());
					cadena = Documento(jugador, archivoaat);
					if (cadena.length() != 8){
						for (int i = 8-cadena.length();i>0;i--)
							wr.append(" ");
						wr.append(cadena);
					}
					else
						wr.append(cadena);
					}
					}
					else //HAY BYE, cargo 16 espacios en blanco
						for (int i = 0;i<16;i++)
							wr.append(" ");
					
					//Cargo jugador ganador
					if (!haybye){
					if (EvaluacionResultado(resultados[res]))
						if(dobles.equalsIgnoreCase("SI"))
							cadena = Documento(participantes[posicion+(zonanro*aux)].substring(o+1, participantes[posicion+(zonanro*aux)].length()), archivoaat);
						else
							cadena = Documento(participantes[posicion+(zonanro*aux)], archivoaat);
					else
						if(dobles.equalsIgnoreCase("SI"))
							cadena = Documento(participantes[posicion2+(zonanro*aux)].substring(o2+1, participantes[posicion2+(zonanro*aux)].length()), archivoaat);
						else
							cadena = Documento(participantes[posicion2+(zonanro*aux)], archivoaat);

					if (cadena.length() != 8){
						for (int i = 8-cadena.length();i>0;i--)
							wr.append(" ");
						wr.append(cadena);
					}
					else
						wr.append(cadena);
					}
					else // Hay Bye
					{
					if(!participantes[posicion+(zonanro*aux)].equalsIgnoreCase("BYE"))
						if(dobles.equalsIgnoreCase("SI"))
							cadena = Documento(participantes[posicion+(zonanro*aux)].substring(o+1, participantes[posicion+(zonanro*aux)].length()), archivoaat);
						else
							cadena = Documento(participantes[posicion+(zonanro*aux)], archivoaat);
					else
						if(dobles.equalsIgnoreCase("SI"))
							cadena = Documento(participantes[posicion2+(zonanro*aux)].substring(o2+1, participantes[posicion2+(zonanro*aux)].length()), archivoaat);
						else
							cadena = Documento(participantes[posicion2+(zonanro*aux)], archivoaat);

					if (cadena.length() != 8){
						for (int i = 8-cadena.length();i>0;i--)
							wr.append(" ");
						wr.append(cadena);
					}
					else
						wr.append(cadena);
					}

					//Cargo sets ganados
					if(!haybye){
					wr.append(setsganados(resultados[res],1));
					wr.append('-');
					wr.append(setsganados(resultados[res],2));
					}
					else
						wr.append("   ");
					
					//Cargo resultado
					if(!haybye)
					{
					resultado = ""; u = 0;
					cadena = resultados[res];
					if (cadena.contains("WO"))
						cadena = "0-0";
					if (cadena.toLowerCase().contains("abandono")){
						while (u<cadena.length() && cadena.charAt(u) != 'a'){
							resultado += cadena.charAt(u);
							u++;
					}
					cadena = resultado;	
					}
						
						
					if (cadena.length() != 19){
						for (int i = 19-cadena.length();i>0;i--)
							wr.append(" ");
						wr.append(cadena);
					}
					else
						wr.append(cadena);
					}
					else //HAY BYE, NO hay resultado
						for (int i = 0;i<19;i++)
							wr.append(" ");
				}
				else
					if (cuadro.equalsIgnoreCase("SI")){//Es cuadro completo
						//Info zonas en blanco
						for(int v = 0; v<11;v++)
							wr.append(" ");
						
						//cargo tamano cuadro
						cadena =""+tamanocuadro;
						if (cadena.length() != 3){
							for (int i = 3-cadena.length();i>0;i--)
								wr.append(" ");
							wr.append(cadena);
						}
						else
							wr.append(cadena);
						
						//cargo MD
						if (mD.equalsIgnoreCase("SI")){
							wr.append('P');}
						else{
							wr.append('Q');
						}
						//cargo ronda
						int j =1;
						int multip = 2;
						int menor = 0;
						int mayor = tamanocuadro/multip;
						boolean encontre = false;
						while (!encontre){
							if (res<mayor && res>=menor)
								encontre = true;
							else{
								multip *= 2;
								menor = mayor;
								mayor = mayor+ (tamanocuadro/multip);
								j++;
							}
						}
						cadena =""+j;
						if (cadena.length() != 3){
							for (int i = 3-cadena.length();i>0;i--)
								wr.append(" ");
							wr.append(cadena);
						}
						else
							wr.append(cadena);

						//cargo orden cuadro
						int p = res;
						int multip2 = 2;
						for (int y = 1; y<j;y++){
							p -= tamanocuadro/multip2;
							multip2 *=2;
						}
						p = (p*2)+1;	
						cadena =""+p;
						if (cadena.length() != 3){
							for (int i = 3-cadena.length();i>0;i--)
								wr.append(" ");
							wr.append(cadena);
						}
						else
							wr.append(cadena);
						


						//Cargo jugador 1
						if(!participantes[res*2].equalsIgnoreCase("BYE")){
						o = 0; jugador = "";
						while (o< participantes[res*2].length() && participantes[res*2].charAt(o) != '/'){
							jugador += participantes[res*2].charAt(o);
							o++;
						}
						cadena = Documento(jugador, archivoaat);
						if (cadena.length() != 8){
							for (int i = 8-cadena.length();i>0;i--)
								wr.append(" ");
							wr.append(cadena);
						}
						else
							wr.append(cadena);

						if(dobles.equalsIgnoreCase("NO")){	
							//Cargo 8 espacios jugador vacio
							for (int t=0;t<8;t++)
								wr.append(" ");
						}
						else{
							//Cargo companiero 1
							jugador = participantes[res*2].substring(o+1, participantes[res*2].length());
							cadena = Documento(jugador, archivoaat);
							if (cadena.length() != 8){
								for (int i = 8-cadena.length();i>0;i--)
									wr.append(" ");
								wr.append(cadena);
							}
							else
								wr.append(cadena);
						}
						}
						else //Es Bye, cargo 16 espacios en blanco
							for (int i = 0;i<16;i++)
								wr.append(" ");
						//Cargo jugador 2
						o2 = 0; jugador = "";
						if(!participantes[(res*2)+1].equalsIgnoreCase("BYE")){
						while (o2< participantes[(res*2)+1].length() && participantes[(res*2)+1].charAt(o2) != '/'){
							jugador += participantes[(res*2)+1].charAt(o2);
							o2++;
						}
						cadena = Documento(jugador, archivoaat);
						if (cadena.length() != 8){
							for (int i = 8-cadena.length();i>0;i--)
								wr.append(" ");
							wr.append(cadena);
						}
						else
							wr.append(cadena);

						if(dobles.equalsIgnoreCase("NO")){	
							//Cargo 8 espacios jugador vacio
							for (int t=0;t<8;t++)
								wr.append(" ");
						}
						else{
							//Cargo companiero 2
							jugador = participantes[(res*2)+1].substring(o2+1, participantes[(res*2)+1].length());
							cadena = Documento(jugador, archivoaat);
							if (cadena.length() != 8){
								for (int i = 8-cadena.length();i>0;i--)
									wr.append(" ");
								wr.append(cadena);
							}
							else
								wr.append(cadena);
						}
						}
						else //HAY BYE, cargo 16 lugares en blanco
							for (int i = 0;i<16;i++)
								wr.append(" ");
						
						//Cargo jugador ganador
						if(!haybye){
						if (EvaluacionResultado(resultados[res]))
							if(dobles.equalsIgnoreCase("SI"))
								cadena = Documento (participantes[res*2].substring(o+1, participantes[res*2].length()),archivoaat);
							else cadena = Documento(participantes[res*2], archivoaat);
						else
							if(dobles.equalsIgnoreCase("SI"))
								cadena = Documento (participantes[(res*2)+1].substring(o2+1, participantes[(res*2)+1].length()),archivoaat);
							else cadena = Documento(participantes[(res*2)+1], archivoaat);
						if (cadena.length() != 8){
							for (int i = 8-cadena.length();i>0;i--)
								wr.append(" ");
							wr.append(cadena);
						}
						else
							wr.append(cadena);
						}
						else{ //Hay Bye
						if(!participantes[res*2].equalsIgnoreCase("BYE"))	
							if(dobles.equalsIgnoreCase("SI"))
								cadena = Documento (participantes[res*2].substring(o+1, participantes[res*2].length()),archivoaat);
							else 
								cadena = Documento(participantes[res*2], archivoaat);
						else
							if(dobles.equalsIgnoreCase("SI"))
								cadena = Documento (participantes[(res*2)+1].substring(o2+1, participantes[(res*2)+1].length()),archivoaat);
							else
								cadena = Documento(participantes[(res*2)+1], archivoaat);
						if (cadena.length() != 8){
							for (int i = 8-cadena.length();i>0;i--)
								wr.append(" ");
							wr.append(cadena);
						}
						else
							wr.append(cadena);
						}
						
						//Cargo sets ganados
						if(!haybye){
						wr.append(setsganados(resultados[res],1));
						wr.append('-');
						wr.append(setsganados(resultados[res],2));
						}
						else //espacios en blanco porque es bye
							wr.append("   ");
						
						//Cargo resultado
						resultado = ""; u = 0;
						if(!haybye){
						cadena = resultados[res];
						if (cadena.contains("WO"))
							cadena = "0-0";
						if (cadena.toLowerCase().contains("abandono")){
							while (u<cadena.length() && cadena.charAt(u) != 'a'){
								resultado += cadena.charAt(u);
								u++;
						}
						cadena = resultado;
						}
							
							
						if (cadena.length() != 19){
							for (int i = 19-cadena.length();i>0;i--)
								wr.append(" ");
							wr.append(cadena);
						}
						else
							wr.append(cadena);
						}
						else //es bye, cargo 19 espacios en blanco
							for (int i = 0;i<19;i++)
								wr.append(" ");
					}

				wr.append("\n");
			
			res++;
		}
		wr.close();

		bw.close();
	}catch(IOException e){};
}

private String setsganados(String string, int m) {//devuelvo la cantidad de sets ganados por el jugador m en el resultado string
	if (string.contains("WO 1"))
		return "0";
	if (string.contains("WO 2"))
		return "0";
	int sets1 = 0;
	int sets2 = 0;
	String games1 = "";
	String games2 = "";
	int i = 0;
	while (i<string.length() && string.charAt(i)!= 'a' && string.charAt(i) != 'A'){
		games1 = "";
		games2 = "";
		while (string.charAt(i) != '-')
			games1 += string.charAt(i++);
		i++;
		while (i< string.length() && (string.charAt(i) != '/' && string.charAt(i) != 'a' && string.charAt(i) != 'A'))
			games2 += string.charAt(i++);
		if (i < string.length() && (string.charAt(i) == 'a' || string.charAt(i) == 'A')){//Hubo abandono
			if(m == 1)
				return "" + sets1;
			else
				return "" + sets2;
		}
		if (Integer.parseInt(games1) > Integer.parseInt(games2))
			sets1++;
		else
			sets2++;
		i++;
	}
	if(m == 1)
		return "" + sets1;
	else
		return "" + sets2;
}
private String Documento(String string, String l) {//busco el dni del jugador en el archivo ubicado en /Config/AATtxt
	String doc = "";
	String cadena = "";
	boolean encontre = false;
	int i = 0;
	FileReader f;
	try {
		f = new FileReader(l);
	
    BufferedReader c = new BufferedReader(f);
    while (((cadena = c.readLine())!= null) && !encontre){
    	if (cadena.toLowerCase().contains(string.toLowerCase())){
    		encontre = true;
    		while(cadena.charAt(i)!= '-')
    			i++;
    	i = i+2;
    	while (i<cadena.length()){
    		doc += cadena.charAt(i);
    		i++;
    	}
    }
    }
    c.close();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return doc;
}
@SuppressWarnings("unused")
public void CrearJugadorNuevo(){//Creo Archivos AAT Nuevos jugadores para cada uno del archivo
	String cadena = null;
	String cadena2 = null;
    String Nombre = "";
    String Apellido = "";
    String Categoria = ""; 
    String Federacion = "";
    String Nacionalidad = ""; 
    String TipoDoc = "";
    String Provincia = ""; 
    String Fecha = "";
    char genero = ' ';
    String NroDoc = "";
    String Localidad = "";
    String Direccion = "";
    String CP = "";
    String Telefono = "";
    String email = "";
    String Nrocasa = "";
    String Dpto = "";
    String celular = "";
    String Club = "";
    boolean comillas = false;
    int i;
    Jugador j;
    FileReader f;
	try {
		f = new FileReader(archivoadm);
	
    BufferedReader b = new BufferedReader(f);
    cadena = b.readLine();
    if (cadena.charAt(0) == '"')
    	comillas = true;
    while((cadena2 = b.readLine())!=null) { 
    	cadena = "";
    	if (comillas){
    		for (int r = 0; r<cadena2.length();r++)
    			if(cadena2.charAt(r) != '"')
    				cadena+=cadena2.charAt(r);
    		}
    	else
    		cadena = cadena2; 
    	i = 0;
    	Nombre = Apellido = Categoria = Federacion = Nacionalidad = TipoDoc = Provincia = Fecha = NroDoc = Localidad =
    			Direccion = CP = Telefono = email = Dpto = Club = Nrocasa = celular = "";
    	genero = ' ';
    	while (cadena.charAt(i) != ',')//Adelanto el tiempo de submit
    		i++;
    	i++;//Salteo la ,
    	if(cadena.charAt(i)!= ',')
    		Nombre += cadena.toUpperCase().charAt(i++);
    	while (cadena.charAt(i) != ','){//Cargo el nombre
        	Nombre += cadena.toLowerCase().charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	if(cadena.charAt(i)!= ',')
    		Apellido += cadena.toUpperCase().charAt(i++);
    	while (cadena.charAt(i) != ','){//Cargo el apellido
        	Apellido += cadena.toLowerCase().charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	if(cadena.charAt(i)!= ',')
    		email += cadena.toUpperCase().charAt(i++);
    	while (cadena.charAt(i) != ','){//Cargo el email
        	email += cadena.toLowerCase().charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo el genero
        	genero = cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	if(cadena.charAt(i)!= ',')
    		TipoDoc += cadena.toUpperCase().charAt(i++);
    	while (cadena.charAt(i) != ','){//Cargo el TipoDoc
        	TipoDoc += cadena.toLowerCase().charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo el DNI
        	if (cadena.charAt(i) != '.') //Elimino los puntos del DNI
        		NroDoc += cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo anio
        	Fecha += cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	String mes = "";
    	while (cadena.charAt(i) != ','){//Cargo mes
        	mes += cadena.charAt(i);
        	i++;
    	}
    	mes = codificacion(mes);
    	Fecha += mes;
    	i++; //Salteo las comillas de ambos lados y la ,
    	String dia = "";
    	while (cadena.charAt(i) != ','){//Cargo dia
        	dia += cadena.charAt(i);
    		i++;
    	}
    	dia = codificaciondia(dia);
    	Fecha += dia;
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo la federacion
        	Federacion += cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo la Categoria
        	Categoria += cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo la Nacionalidad
        	Nacionalidad += cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo la Provincia
        	Provincia += cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	if(cadena.charAt(i)!= ',')
    		Localidad += cadena.toUpperCase().charAt(i++);
    	while (cadena.charAt(i) != ','){//Cargo la Ciudad
        	Localidad += cadena.toLowerCase().charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo el CP
        	CP += cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	if(cadena.charAt(i)!= ',')
    		Direccion += cadena.toUpperCase().charAt(i++);
    	while (cadena.charAt(i) != ','){//Cargo la Direccion
        	Direccion += cadena.toLowerCase().charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	if(cadena.charAt(i)!= ',')
    		Dpto += cadena.toUpperCase().charAt(i++);
    	while (cadena.charAt(i) != ','){//Cargo el Dpto
        	Dpto += cadena.toLowerCase().charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	if(cadena.charAt(i)!= ',')
    		Club += cadena.toUpperCase().charAt(i++);
    	while (cadena.charAt(i) != ','){//Cargo el club
        	Club += cadena.toLowerCase().charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo el Telefono
        	Telefono += cadena.charAt(i);
    		i++;
    	}
    	i++; //Salteo las comillas de ambos lados y la ,
    	while (cadena.charAt(i) != ','){//Cargo el celular
        	celular += cadena.charAt(i);
    		i++;
    	}
        	j = new Jugador(Nombre,Apellido,Federacion, Club, Nacionalidad, TipoDoc,
        			Provincia, genero, Fecha, NroDoc, Localidad, Direccion, CP, Telefono,
        			email, Nrocasa, Dpto, celular);
        	Torneo tor = new Torneo();
        	tor.CreoArchivoAAT(j);
        	}
    b.close();
    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
}

public void CompletarCuadros(String[] participantes, String[] resultados,
	char sexo, String categoria, String dobles, String cuadro, String mD, int tamanocuadro, int jugadores) {//Completo PDF's en seccion /Cuadros a partir de los datos ingresados en el csv
	int desp = 0; //caso 32 jug, default
	int jug = 0;
	int p = 0;
	String sexo2;
	int [] CoordPDF = null;
	int letra = 7;
	int largo = 16;
	String preclasificadosenpie [] = null;
	String pDFOUT = "./Cuadros/Completo_";
	String pDFIN = "./Cuadros/Cuadros Utilizados/";
	String nombPDF = "";
	boolean encontre = false;
	if (sexo == 'M')
		sexo2 = "Varones";
	else
		sexo2 = "Mujeres";
	String sdirect = "./Cuadros/Cuadros Utilizados";
	File e = new File (sdirect);
	if (e.exists()){
		File[] ficheros = e.listFiles();
		for (int x=0;x<ficheros.length;x++){
			if (ficheros[x].getName().contains("Sub "+categoria) && ficheros[x].getName().contains(sexo2)){
				pDFIN += ficheros[x].getName();
				pDFOUT += ficheros[x].getName();
			}
		}
	}
	if (dobles.equalsIgnoreCase("NO"))
	{
		if (cuadro.equalsIgnoreCase("SI"))
			if(tamanocuadro<32){
				for(int r=0;r<resultados.length;r++){
					if (!resultados[r].equalsIgnoreCase("Bye") && !resultados[r].equalsIgnoreCase(""))
						resultados[r] = darvueltaresult(resultados[r]);
				}
				preclasificadosenpie = new String [4];
				preclasificadosenpie[0] = participantes[0];
				preclasificadosenpie[1] = participantes[15];
				preclasificadosenpie[2] = participantes[11];
				preclasificadosenpie[3] = participantes[4];
				CoordPDF = cargarCuadro16();
				desp = -1;
				largo = 13;
				p = 4;
			}
			else
			{
				for(int r=0;r<resultados.length;r++){
					if (!resultados[r].equalsIgnoreCase("Bye") && !resultados[r].equalsIgnoreCase(""))
						resultados[r] = darvueltaresult(resultados[r]);
				}
				preclasificadosenpie = new String [8];
				preclasificadosenpie[0] = participantes[0];
				preclasificadosenpie[1] = participantes[7];
				preclasificadosenpie[2] = participantes[8];
				preclasificadosenpie[3] = participantes[15];
				preclasificadosenpie[4] = participantes[16];
				preclasificadosenpie[5] = participantes[23];
				preclasificadosenpie[6] = participantes[24];
				preclasificadosenpie[7] = participantes[31];
				CoordPDF = cargarCuadro32();
				largo = 15;
				p = 5;
			}
	else{// son zonas
		switch(jugadores){
		case 4:
		{
			letra = 10;
			CoordPDF = cargarzona(5);
			break;
		}
		case 5:
		{
			letra = 10;
			CoordPDF = cargarzona(5);
			break;
		}
		case 6:
		{	
			CoordPDF = cargarzona(6);
			letra = 11;
			break;
		}
		case 7:
		{
			CoordPDF = cargarzona(8);
			letra = 11;
			break;
		}
		case 8:
		{
			CoordPDF = cargarzona(8);
			letra = 9;
			desp = -1;
			break;
		}
		case 9:
		{
			CoordPDF = cargarzona(9);
			letra = 9;
			desp = -1;
			break;
		}
		case 10:
		{
			CoordPDF = cargarzona(11);
			letra = 9;
			desp = -1;
			break;
		}
		case 11:
		{
			CoordPDF = cargarzona(11);
			letra = 9;
			desp = -1;
			break;
		}
		case 12:
		{
			CoordPDF = cargarzona(12);
			letra = 9;
			desp = -1;
			break;
		}
		case 13:
		{
			CoordPDF = cargarzona(16);
			letra = 9;
			desp = -1;
			break;
		}
		case 14:
		{
			CoordPDF = cargarzona(16);
			letra = 9;
			desp = -1;
			break;
		}
		case 15:
		{
			CoordPDF = cargarzona(16);
			letra = 9;
			desp = -1;
			break;
		}
		case 16:
		{
			CoordPDF = cargarzona(16);
			letra = 9;
			desp = -1;
			break;
		}
		}
	}
	switch(tamanocuadro){
		case 32:{
		try {
			PdfReader reader = new PdfReader(pDFIN);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
			BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
			BaseFont bbf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // Preclasificados
			//loop on pages (1-based)
			for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
					PdfContentByte over = stamper.getOverContent(i);
	      
					over.beginText();
					int res = 0;
						while (res<resultados.length){
							if (!resultados[res].equalsIgnoreCase("") && !resultados[res].equalsIgnoreCase("BYE")){
								//cargo ronda
								jug = 32;
								int j =0;
								int multip = 2;
								int menor = 0;
								int mayor = tamanocuadro/multip;
								encontre = false;
								while (!encontre){
									if (res<mayor && res>=menor)
										encontre = true;
									else{
										multip *= 2;
										menor = mayor;
										mayor = mayor+ (tamanocuadro/multip);
										j++;
									}
								}
								encontre = false;
								for (int n=0;n<preclasificadosenpie.length;n++){
									if(participantes[res+jug].equalsIgnoreCase(preclasificadosenpie[n]))
										encontre = true;
								}
								if(encontre)
								over.setFontAndSize(bbf, letra);    // set font and size
								else
								over.setFontAndSize(bf, letra);
								if ((participantes[res+jug].length()) > largo+4){
									int y = 0;
									nombPDF = "";
									while (y<participantes[res+jug].length() && participantes[res+jug].charAt(y)!= ','){
										nombPDF += participantes[res+jug].charAt(y);
										y++;
									}
									nombPDF += ", ";
									y+= 2;
									nombPDF += participantes[res+jug].charAt(y);
								}
								else
									nombPDF = (participantes[res+jug]);
								if (j != 4)
									over.setTextMatrix(CoordPDF[j+1], CoordPDF[p]); //Resultado
								else
									over.setTextMatrix(CoordPDF[j], CoordPDF[p]); //Resultado
								over.showText(nombPDF);
								over.setFontAndSize(bf,letra);
								if (j != 4)
									over.setTextMatrix(CoordPDF[j+1]+2, CoordPDF[p]-9); //Resultado
								else
									over.setTextMatrix(CoordPDF[j]+2, CoordPDF[p]-9); //Resultado
								over.showText(resultados[res]);
							}
							res++;
							p++;	       	
			
						}
					over.endText();
					}
			stamper.close();
			reader.close();		
	
		
		} catch (IOException | DocumentException m) {
			m.printStackTrace();
		}  //Entrego el file del cuadro o donde se guarda dicho pdf
	break;
		}
		case 16:{
			if(cuadro.equalsIgnoreCase("SI")){
			try {
				PdfReader reader = new PdfReader(pDFIN);
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
				BaseFont bbf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // Preclasificados
				//loop on pages (1-based)
				for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
						PdfContentByte over = stamper.getOverContent(i);
		      
						over.beginText();
						int res = 0;
							while (res<resultados.length){
								if (!resultados[res].equalsIgnoreCase("") && !resultados[res].equalsIgnoreCase("BYE")){
									//cargo ronda
									jug = 16;
									int j =0;
									int multip = 2;
									int menor = 0;
									int mayor = tamanocuadro/multip;
									encontre = false;
									while (!encontre){
										if (res<mayor && res>=menor)
											encontre = true;
										else{
											multip *= 2;
											menor = mayor;
											mayor = mayor+ (tamanocuadro/multip);
											j++;
										}
									}
									encontre = false;
									for (int n=0;n<preclasificadosenpie.length;n++){
										if(participantes[res+jug].equalsIgnoreCase(preclasificadosenpie[n]))
											encontre = true;
									}
									if(encontre)
									over.setFontAndSize(bbf, letra);    // set font and size
									else
									over.setFontAndSize(bf, letra);
									if ((participantes[res+jug].length()) > largo+4){
										int y = 0;
										nombPDF = "";
										while (y<participantes[res+jug].length() && participantes[res+jug].charAt(y)!= ','){
											nombPDF += participantes[res+jug].charAt(y);
											y++;
										}
										nombPDF += ", ";
										y+= 2;
										nombPDF += participantes[res+jug].charAt(y);
									}
									else
										nombPDF = (participantes[res+jug]);
									if (j != 3)
										over.setTextMatrix(CoordPDF[j+1], CoordPDF[p]); //Resultado
									else
										over.setTextMatrix(CoordPDF[j]-4, CoordPDF[p]); //Resultado
									over.showText(nombPDF);
									over.setFontAndSize(bf,letra);
									if (j != 3)
										over.setTextMatrix(CoordPDF[j+1]+2, CoordPDF[p]-9); //Resultado
									else
										over.setTextMatrix(CoordPDF[j]-2, CoordPDF[p]-9); //Resultado
									over.showText(resultados[res]);
								}
								res++;
								p++;	       	
				
							}
						over.endText();
						}
				stamper.close();
				reader.close();		
		
			
			} catch (IOException | DocumentException m) {
				m.printStackTrace();
			}  //Entrego el file del cuadro o donde se guarda dicho pdf
		break;
		}
			else{ // 4 zonas de 4 (14, 15 o 16 jugadores)
				try {
					int zonanro;
					int puntos [] = new int [4];
					int setsganados[] = new int [4];
					int setsperdidos[] = new int [4];
					int gamesganados[] = new int [4];
					int gamesperdidos[] = new int [4];
					boolean resultadoentreambos [] = new boolean [6];
					int puntos2 [] = new int [4];
					int setsganados2[] = new int [4];
					int setsperdidos2[] = new int [4];
					int gamesganados2[] = new int [4];
					int gamesperdidos2[] = new int [4];
					boolean resultadoentreambos2 [] = new boolean [6];
					int puntos3 [] = new int [4];
					int setsganados3[] = new int [4];
					int setsperdidos3[] = new int [4];
					int gamesganados3[] = new int [4];
					int gamesperdidos3[] = new int [4];
					boolean resultadoentreambos3 [] = new boolean [6];
					int puntos4 [] = new int [4];
					int setsganados4[] = new int [4];
					int setsperdidos4[] = new int [4];
					int gamesganados4[] = new int [4];
					int gamesperdidos4[] = new int [4];
					boolean resultadoentreambos4 [] = new boolean [6];
					for (int u=0;u<4;u++){
						puntos[u] = 0;
						setsganados[u] = 0;
						setsperdidos[u] = 0;
						gamesganados[u] = 0;
						gamesperdidos[u] = 0;
						puntos2[u] = 0;
						setsganados2[u] = 0;
						setsperdidos2[u] = 0;
						gamesganados2[u] = 0;
						gamesperdidos2[u] = 0;
						puntos3[u] = 0;
						setsganados3[u] = 0;
						setsperdidos3[u] = 0;
						gamesganados3[u] = 0;
						gamesperdidos3[u] = 0;
						puntos4[u] = 0;
						setsganados4[u] = 0;
						setsperdidos4[u] = 0;
						gamesganados4[u] = 0;
						gamesperdidos4[u] = 0;
					}
					for (int u=0;u<6;u++){
					resultadoentreambos[u] = false;
					resultadoentreambos2[u] = false;
					resultadoentreambos3[u] = false;
					resultadoentreambos4[u] = false;
					}
					int posicion;
					int posicion2;
					int result;
					PdfReader reader = new PdfReader(pDFIN);
					PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
					BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
					//loop on pages (1-based)
					for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
							PdfContentByte over = stamper.getOverContent(i);
			      
							over.beginText();
							int res = 0;
								while (res<resultados.length){
									if (!resultados[res].equalsIgnoreCase("")){
										//Cargo posicion zona 1
										posicion = 0;
										posicion2 = 0;
										result = res;
										zonanro = res / 6;
										result = (result % 6)+1;
										if (result < 4)
											posicion = 0;
										if (result >= 4 && result<6)
											posicion = 1;
										if (result == 6)
											posicion = 2;
										//Cargo posicion 2 zona
										result = res;
										result = (result % 6)+1;
										if (result == 1)
											posicion2 = 1;
										if (result == 2 || result == 4)
											posicion2 = 2;
										if (result == 3 || result == 5 || result == 6)
											posicion2 = 3;
										encontre = false;
										if (!resultados[res].equalsIgnoreCase("BYE"))
										{
											if(EvaluacionResultado(resultados[res])){
												if(zonanro == 0)
												{
													puntos[posicion] +=2;
													resultadoentreambos[res] = true;
													if (!resultados[res].equalsIgnoreCase("WO 2"))
														puntos[posicion2]++;
													puntos[posicion]++;
												}
												else
												{
													if(zonanro == 1)
													{
													puntos2[posicion] +=2;
													resultadoentreambos2[res%6] = true;
													if (!resultados[res].equalsIgnoreCase("WO 2"))
														puntos2[posicion2]++;
													puntos2[posicion]++;
													}
													else
													{
														if(zonanro == 2)
														{
															puntos3[posicion] +=2;
															resultadoentreambos3[res%6] = true;
															if (!resultados[res].equalsIgnoreCase("WO 2"))
																puntos3[posicion2]++;
															puntos3[posicion]++;
														}
														else
														{
															puntos4[posicion] +=2;
															resultadoentreambos4[res%6] = true;
															if (!resultados[res].equalsIgnoreCase("WO 2"))
																puntos4[posicion2]++;
															puntos4[posicion]++;
															
														}
													}
												}
											}
											else
											{
												if (zonanro == 0)
												{
													puntos[posicion2]+=2;
													if (!resultados[res].equalsIgnoreCase("WO 1"))
														puntos[posicion]++;
													puntos[posicion2]++;
												}
												else
												{
													if(zonanro == 1)
													{
													puntos2[posicion2]+=2;
													if (!resultados[res].equalsIgnoreCase("WO 1"))
														puntos2[posicion]++;
													puntos2[posicion2]++;
													}
													else
													{
														if (zonanro == 2)
														{
														puntos3[posicion2]+=2;
														if (!resultados[res].equalsIgnoreCase("WO 1"))
															puntos3[posicion]++;
														puntos3[posicion2]++;
														}
														else
														{
															puntos4[posicion2]+=2;
															if (!resultados[res].equalsIgnoreCase("WO 1"))
																puntos4[posicion]++;
															puntos4[posicion2]++;													
														}		
													}
												}
											}
											if (!resultados[res].contains("WO"))
										{
											if(zonanro == 0){
											setsganados[posicion] += Integer.parseInt(setsganados(resultados[res],1));
											setsperdidos[posicion] += Integer.parseInt(setsganados(resultados[res],2));
											setsganados[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
											setsperdidos[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
											}
											else{
												if(zonanro == 1){
												setsganados2[posicion] += Integer.parseInt(setsganados(resultados[res],1));
												setsperdidos2[posicion] += Integer.parseInt(setsganados(resultados[res],2));
												setsganados2[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
												setsperdidos2[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
												}
												else{
													if(zonanro == 2){
													setsganados3[posicion] += Integer.parseInt(setsganados(resultados[res],1));
													setsperdidos3[posicion] += Integer.parseInt(setsganados(resultados[res],2));
													setsganados3[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
													setsperdidos3[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
													}
													else
													{
														setsganados4[posicion] += Integer.parseInt(setsganados(resultados[res],1));
														setsperdidos4[posicion] += Integer.parseInt(setsganados(resultados[res],2));
														setsganados4[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
														setsperdidos4[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
													}
												}
											}
											}
										}
										over.setFontAndSize(bf, letra);
										over.setTextMatrix(CoordPDF[posicion2], CoordPDF[4+posicion+(4*zonanro)]); //Resultado
										if (resultados[res].equalsIgnoreCase("BYE"))
											over.showText(" -------------");
										else
											if(resultados[res].contains("WO"))
												over.showText("WO.");
											else
												over.showText(resultados[res]);
										//Imprimo resultado en la otra parte de la zona
										over.setTextMatrix(CoordPDF[posicion], CoordPDF[4+posicion2+4*zonanro]); //Resultado
										if (resultados[res].equalsIgnoreCase("BYE"))
											over.showText(" -------------");
										else
											over.showText(darvueltaresult2(resultados[res]));
									}
									res++;
									p++;	       	
					
								}
								for (int l = 0;l<4;l++){
								over.setFontAndSize(bf,letra);
								over.setTextMatrix(CoordPDF[20], CoordPDF[4+l]);
								over.showText(""+puntos[l]);
								over.setTextMatrix(CoordPDF[20], CoordPDF[8+l]);
								over.showText(""+puntos2[l]);
								over.setTextMatrix(CoordPDF[20], CoordPDF[12+l]);
								over.showText(""+puntos3[l]);
								over.setTextMatrix(CoordPDF[20], CoordPDF[16+l]);
								over.showText(""+puntos4[l]);}
								int [] juga = new int [4];
								juga[0] = 0;
								juga[1] = 1;
								juga[2] = 2;
								juga[3] = 3;
								int [] juga2 = new int [4];
								juga2[0] = 0;
								juga2[1] = 1;
								juga2[2] = 2;
								juga2[3] = 3;
								int [] juga3 = new int [4];
								juga3[0] = 0;
								juga3[1] = 1;
								juga3[2] = 2;
								juga3[3] = 3;
								int [] juga4 = new int [4];
								juga4[0] = 0;
								juga4[1] = 1;
								juga4[2] = 2;
								juga4[3] = 3;
								int aux;
								int aux2;
							    //Ordeno de manera estricta
								for (int n = 0; n < 4 ; n++) {
							        for (int g = n + 1; g < 4; g++) {
							            if (puntos[g] > puntos[n]) {
							                aux = puntos[n];
							                aux2 = juga[n];
							                puntos[n] = puntos[g];
							                juga[n] = juga[g];
							                puntos[g] = aux;
							                juga[g] = aux2;
							            }
							            if (puntos2[g] > puntos2[n]) {
							                aux = puntos2[n];
							                aux2 = juga2[n];
							                puntos2[n] = puntos2[g];
							                juga2[n] = juga2[g];
							                puntos2[g] = aux;
							                juga2[g] = aux2;
							            }
							            if (puntos3[g] > puntos3[n]) {
							                aux = puntos3[n];
							                aux2 = juga2[n];
							                puntos3[n] = puntos3[g];
							                juga3[n] = juga3[g];
							                puntos3[g] = aux;
							                juga3[g] = aux2;
							            }
							            if (puntos4[g] > puntos4[n]) {
							                aux = puntos4[n];
							                aux2 = juga4[n];
							                puntos4[n] = puntos4[g];
							                juga4[n] = juga4[g];
							                puntos4[g] = aux;
							                juga4[g] = aux2;
							            }
							        }
							    }	
								if (puntos[0] == puntos[1]){//Hay empate
									if(puntos[1] == puntos[2]){//Hay triple empate
										if (puntos[2] == puntos[3]){//Todos empatados
											for (int n = 0; n < 4 ; n++) {
										        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
										                aux = puntos[n];
										                aux2 = juga[n];
										                puntos[n] = puntos[g];
										                juga[n] = juga[g];
										                puntos[g] = aux;
										                juga[g] = aux2;
										            }
										        }
										    }
										}
										for (int n = 0; n < 3 ; n++) {
									        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
									                aux = puntos[n];
									                aux2 = juga[n];
									                puntos[n] = puntos[g];
									                juga[n] = juga[g];
									                puntos[g] = aux;
									                juga[g] = aux2;
									            }
									}
								}
							}
									
								if(juga[0]>juga[1]){//0 tiene un orden menor
									if (juga[0] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) || (juga[1] == 3 && !resultadoentreambos[2]))
										{
											aux = puntos[0];
							                aux2 = juga[0];
							                puntos[0] = puntos[1];
							                juga[0] = juga[1];
							                puntos[1] = aux;
							                juga[1] = aux2;
										}
									}
									else
										if(juga[0] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[1] == 2 && !resultadoentreambos[3]) || (juga[1] == 3 && !resultadoentreambos[4]))
											{
												aux = puntos[0];
								                aux2 = juga[0];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
											}
										}
										else
											if(juga[0] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[1] == 3 && !resultadoentreambos[5]))
												{
													aux = puntos[0];
									                aux2 = juga[0];
									                puntos[0] = puntos[1];
									                juga[0] = juga[1];
									                puntos[1] = aux;
									                juga[1] = aux2;
												}
											}
											
								}
								else{
									if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[0] == 1 && resultadoentreambos[0]) || (juga[0] == 2 && resultadoentreambos[1]) || (juga[0] == 3 && resultadoentreambos[2]))
										{
											aux = puntos[0];
							                aux2 = juga[1];
							                puntos[0] = puntos[1];
							                juga[0] = juga[1];
							                puntos[1] = aux;
							                juga[1] = aux2;
										}
									}
									else
										if(juga[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[0] == 2 && resultadoentreambos[3]) || (juga[0] == 3 && resultadoentreambos[4]))
											{
												aux = puntos[0];
												aux2 = juga[1];
												puntos[0] = puntos[1];
												juga[0] = juga[1];
												puntos[1] = aux;
												juga[1] = aux2;}
										}
										else
											if(juga[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[0] == 3 && resultadoentreambos[5]))
												{
													aux = puntos[0];
									                aux2 = juga[1];
									                puntos[0] = puntos[1];
									                juga[0] = juga[1];
									                puntos[1] = aux;
									                juga[1] = aux2;
									                }
											}
											
								}
	}

								else
									if(puntos[1] == puntos[2]){//Hay  empate
										if (puntos[2] == puntos[3]){//Triple empate
											for (int n = 1; n < 4 ; n++) {
										        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
										                aux = puntos[n];
										                aux2 = juga[n];
										                puntos[n] = puntos[g];
										                juga[n] = juga[g];
										                puntos[g] = aux;
										                juga[g] = aux2;
										            }
										        }
										    }
										}	
								if(juga[1]>juga[2]){//0 tiene un orden menor
									if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[2] == 1 && !resultadoentreambos[0]) || (juga[2] == 2 && !resultadoentreambos[1]) || (juga[2] == 3 && !resultadoentreambos[2]))
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[2];
							                juga[1] = juga[2];
							                puntos[2] = aux;
							                juga[2] = aux2;
										}
									}
									else
										if(juga[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[2] == 2 && !resultadoentreambos[3]) || (juga[2] == 3 && !resultadoentreambos[4]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[2];
								                juga[1] = juga[2];
								                puntos[2] = aux;
								                juga[2] = aux2;
											}
										}
										else
											if(juga[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[2] == 3 && !resultadoentreambos[5]))
												{
													aux = puntos[1];
									                aux2 = juga[1];
									                puntos[1] = puntos[2];
									                juga[1] = juga[2];
									                puntos[2] = aux;
									                juga[2] = aux2;
												}
											}
											
								}
								else{
									if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[1] == 1 && resultadoentreambos[0]) || (juga[1] == 2 && resultadoentreambos[1]) || (juga[1] == 3 && resultadoentreambos[2]))
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[2];
							                juga[1] = juga[2];
							                puntos[2] = aux;
							                juga[2] = aux2;
										}
									}
									else
										if(juga[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[1] == 2 && resultadoentreambos[3]) || (juga[1] == 3 && resultadoentreambos[4]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[2];
								                juga[1] = juga[2];
								                puntos[2] = aux;
								                juga[2] = aux2;
											}
										else
											if(juga[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[1] == 3 && resultadoentreambos[5]))
												{
													aux = puntos[1];
									                aux2 = juga[1];
									                puntos[1] = puntos[2];
									                juga[1] = juga[2];
									                puntos[2] = aux;
									                juga[2] = aux2;
												}
											}
											
								}
								}
								}
									else
										if (puntos[2] == puntos[3]){//Hay empate
											
								if(juga[2]>juga[3]){//0 tiene un orden menor
									if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[3] == 1 && !resultadoentreambos[0]) || (juga[3] == 2 && !resultadoentreambos[1]) || (juga[3] == 3 && !resultadoentreambos[2]))
										{
											aux = puntos[2];
							                aux2 = juga[2];
							                puntos[2] = puntos[3];
							                juga[2] = juga[3];
							                puntos[3] = aux;
							                juga[3] = aux2;
										}
									}
									else
										if(juga[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[3] == 2 && !resultadoentreambos[3]) || (juga[3] == 3 && !resultadoentreambos[4]))
											{
												aux = puntos[2];
								                aux2 = juga[2];
								                puntos[2] = puntos[3];
								                juga[2] = juga[3];
								                puntos[3] = aux;
								                juga[3] = aux2;
											}
										}
										else
											if(juga[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[3] == 3 && !resultadoentreambos[5]))
												{
													aux = puntos[2];
									                aux2 = juga[2];
									                puntos[2] = puntos[3];
									                juga[2] = juga[3];
									                puntos[3] = aux;
									                juga[3] = aux2;
												}
											}
								}
								else{
									if (juga[3] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[2] == 1 && resultadoentreambos[0]) || (juga[2] == 2 && resultadoentreambos[1]) || (juga[2] == 3 && resultadoentreambos[2]))
										{
											aux = puntos[2];
							                aux2 = juga[2];
							                puntos[2] = puntos[3];
							                juga[2] = juga[3];
							                puntos[3] = aux;
							                juga[3] = aux2;
										}
									}
									else
										if(juga[3] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[2] == 2 && resultadoentreambos[3]) || (juga[2] == 3 && resultadoentreambos[4]))
											{
												aux = puntos[2];
								                aux2 = juga[2];
								                puntos[2] = puntos[3];
								                juga[2] = juga[3];
								                puntos[3] = aux;
								                juga[3] = aux2;
											}
										else
											if(juga[3] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[2] == 3 && resultadoentreambos[5]))
												{
													aux = puntos[2];
									                aux2 = juga[2];
									                puntos[2] = puntos[3];
									                juga[2] = juga[3];
									                puntos[3] = aux;
									                juga[3] = aux2;
												}
											}
								}
								}
										}
								if (puntos2[0] == puntos2[1]){//Hay empate
									if(puntos2[1] == puntos2[2]){//Hay triple empate
										if (puntos2[2] == puntos2[3]){//Todos empatados
											for (int n = 0; n < 4 ; n++) {
										        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
										            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
										            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
										            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
										                aux = puntos2[n];
										                aux2 = juga2[n];
										                puntos2[n] = puntos2[g];
										                juga2[n] = juga2[g];
										                puntos2[g] = aux;
										                juga2[g] = aux2;
										            }
										        }
										    }
										}
										for (int n = 0; n < 3 ; n++) {
									        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
										            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
										            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
										            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
									                aux = puntos2[n];
									                aux2 = juga2[n];
									                puntos2[n] = puntos2[g];
									                juga2[n] = juga2[g];
									                puntos2[g] = aux;
									                juga2[g] = aux2;
									            }
									}
								}
							}
									
								if(juga2[0]>juga2[1]){//0 tiene un orden menor
									if (juga2[0] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) || (juga2[1] == 3 && !resultadoentreambos2[2]))
										{
											aux = puntos2[0];
							                aux2 = juga2[0];
							                puntos2[0] = puntos2[1];
							                juga2[0] = juga2[1];
							                puntos2[1] = aux;
							                juga2[1] = aux2;
										}
									}
									else
										if(juga2[0] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[1] == 2 && !resultadoentreambos2[3]) || (juga2[1] == 3 && !resultadoentreambos2[4]))
											{
												aux = puntos2[0];
								                aux2 = juga2[0];
								                puntos2[0] = puntos2[1];
								                juga2[0] = juga2[1];
								                puntos2[1] = aux;
								                juga2[1] = aux2;
											}
										}
										else
											if(juga2[0] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga2[1] == 3 && !resultadoentreambos2[5]))
												{
													aux = puntos2[0];
									                aux2 = juga2[0];
									                puntos2[0] = puntos2[1];
									                juga2[0] = juga2[1];
									                puntos2[1] = aux;
									                juga2[1] = aux2;
												}
											}
											
								}
								else{
									if (juga2[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga2[0] == 1 && resultadoentreambos2[0]) || (juga2[0] == 2 && resultadoentreambos2[1]) || (juga2[0] == 3 && resultadoentreambos2[2]))
										{
											aux = puntos2[0];
							                aux2 = juga2[1];
							                puntos2[0] = puntos2[1];
							                juga2[0] = juga2[1];
							                puntos2[1] = aux;
							                juga2[1] = aux2;
										}
									}
									else
										if(juga2[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[0] == 2 && resultadoentreambos2[3]) || (juga2[0] == 3 && resultadoentreambos2[4]))
											{
												aux = puntos2[0];
												aux2 = juga2[1];
												puntos2[0] = puntos2[1];
												juga2[0] = juga2[1];
												puntos2[1] = aux;
												juga2[1] = aux2;}
										}
										else
											if(juga2[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga2[0] == 3 && resultadoentreambos2[5]))
												{
													aux = puntos2[0];
									                aux2 = juga2[1];
									                puntos2[0] = puntos2[1];
									                juga2[0] = juga2[1];
									                puntos2[1] = aux;
									                juga2[1] = aux2;
									                }
											}
											
								}
	}

								else
									if(puntos2[1] == puntos2[2]){//Hay  empate
										if (puntos2[2] == puntos2[3]){//Triple empate
											for (int n = 1; n < 4 ; n++) {
										        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
										            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
										            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
										            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
										                aux = puntos2[n];
										                aux2 = juga2[n];
										                puntos2[n] = puntos2[g];
										                juga2[n] = juga2[g];
										                puntos2[g] = aux;
										                juga2[g] = aux2;
										            }
										        }
										    }
										}	
								if(juga2[1]>juga2[2]){//0 tiene un orden menor
									if (juga2[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga2[2] == 1 && !resultadoentreambos2[0]) || (juga2[2] == 2 && !resultadoentreambos2[1]) || (juga2[2] == 3 && !resultadoentreambos2[2]))
										{
											aux = puntos2[1];
							                aux2 = juga2[1];
							                puntos2[1] = puntos2[2];
							                juga2[1] = juga2[2];
							                puntos2[2] = aux;
							                juga2[2] = aux2;
										}
									}
									else
										if(juga2[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[2] == 2 && !resultadoentreambos2[3]) || (juga2[2] == 3 && !resultadoentreambos2[4]))
											{
												aux = puntos2[1];
								                aux2 = juga2[1];
								                puntos2[1] = puntos2[2];
								                juga2[1] = juga2[2];
								                puntos2[2] = aux;
								                juga2[2] = aux2;
											}
										}
										else
											if(juga2[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga2[2] == 3 && !resultadoentreambos2[5]))
												{
													aux = puntos2[1];
									                aux2 = juga2[1];
									                puntos2[1] = puntos2[2];
									                juga2[1] = juga2[2];
									                puntos2[2] = aux;
									                juga2[2] = aux2;
												}
											}
											
								}
								else{
									if (juga2[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga2[1] == 1 && resultadoentreambos2[0]) || (juga2[1] == 2 && resultadoentreambos2[1]) || (juga2[1] == 3 && resultadoentreambos2[2]))
										{
											aux = puntos2[1];
							                aux2 = juga2[1];
							                puntos2[1] = puntos2[2];
							                juga2[1] = juga2[2];
							                puntos2[2] = aux;
							                juga2[2] = aux2;
										}
									}
									else
										if(juga2[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[1] == 2 && resultadoentreambos2[3]) || (juga2[1] == 3 && resultadoentreambos2[4]))
											{
												aux = puntos2[1];
								                aux2 = juga2[1];
								                puntos2[1] = puntos2[2];
								                juga2[1] = juga2[2];
								                puntos2[2] = aux;
								                juga2[2] = aux2;
											}
										else
											if(juga2[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga2[1] == 3 && resultadoentreambos2[5]))
												{
													aux = puntos2[1];
									                aux2 = juga2[1];
									                puntos2[1] = puntos2[2];
									                juga2[1] = juga2[2];
									                puntos2[2] = aux;
									                juga2[2] = aux2;
												}
											}
											
								}
								}
								}
									else
										if (puntos2[2] == puntos2[3]){//Hay empate
											
								if(juga2[2]>juga2[3]){//0 tiene un orden menor
									if (juga2[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga2[3] == 1 && !resultadoentreambos2[0]) || (juga2[3] == 2 && !resultadoentreambos2[1]) || (juga2[3] == 3 && !resultadoentreambos2[2]))
										{
											aux = puntos2[2];
							                aux2 = juga2[2];
							                puntos2[2] = puntos2[3];
							                juga2[2] = juga2[3];
							                puntos2[3] = aux;
							                juga2[3] = aux2;
										}
									}
									else
										if(juga2[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[3] == 2 && !resultadoentreambos2[3]) || (juga2[3] == 3 && !resultadoentreambos2[4]))
											{
												aux = puntos2[2];
								                aux2 = juga2[2];
								                puntos2[2] = puntos2[3];
								                juga2[2] = juga2[3];
								                puntos2[3] = aux;
								                juga2[3] = aux2;
											}
										}
										else
											if(juga2[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga2[3] == 3 && !resultadoentreambos2[5]))
												{
													aux = puntos2[2];
									                aux2 = juga2[2];
									                puntos2[2] = puntos2[3];
									                juga2[2] = juga2[3];
									                puntos2[3] = aux;
									                juga2[3] = aux2;
												}
											}
								}
								else{
									if (juga2[3] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga2[2] == 1 && resultadoentreambos2[0]) || (juga2[2] == 2 && resultadoentreambos2[1]) || (juga2[2] == 3 && resultadoentreambos2[2]))
										{
											aux = puntos2[2];
							                aux2 = juga2[2];
							                puntos2[2] = puntos2[3];
							                juga2[2] = juga2[3];
							                puntos2[3] = aux;
							                juga2[3] = aux2;
										}
									}
									else
										if(juga2[3] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[2] == 2 && resultadoentreambos2[3]) || (juga2[2] == 3 && resultadoentreambos2[4]))
											{
												aux = puntos2[2];
								                aux2 = juga2[2];
								                puntos2[2] = puntos2[3];
								                juga2[2] = juga2[3];
								                puntos2[3] = aux;
								                juga2[3] = aux2;
											}
										else
											if(juga2[3] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga2[2] == 3 && resultadoentreambos2[5]))
												{
													aux = puntos2[2];
									                aux2 = juga2[2];
									                puntos2[2] = puntos2[3];
									                juga2[2] = juga2[3];
									                puntos2[3] = aux;
									                juga2[3] = aux2;
												}
											}
								}
								}
										}
								if (puntos3[0] == puntos3[1]){//Hay empate
									if(puntos3[1] == puntos3[2]){//Hay triple empate
										if (puntos3[2] == puntos3[3]){//Todos empatados
											for (int n = 0; n < 4 ; n++) {
										        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga3[n]]+setsperdidos[juga3[n]])== 0)
										            	|| (((setsganados[juga3[g]]+setsperdidos[juga3[g]])!= 0) && (setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]]) > (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))))
										            	|| ((setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]])) == (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))) && (((gamesganados[juga3[n]]+gamesperdidos[juga3[n]])== 0)
										            	|| (((gamesganados[juga3[g]]+gamesperdidos[juga3[g]])!= 0) && (gamesganados[juga3[g]]/(gamesganados[juga3[g]]+gamesperdidos[juga3[g]]) > (gamesganados[juga3[n]]/(gamesganados[juga3[n]]+gamesperdidos[juga3[n]])))))) {
										                aux = puntos3[n];
										                aux2 = juga3[n];
										                puntos3[n] = puntos3[g];
										                juga3[n] = juga3[g];
										                puntos3[g] = aux;
										                juga3[g] = aux2;
										            }
										        }
										    }
										}
										for (int n = 0; n < 3 ; n++) {
									        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga3[n]]+setsperdidos[juga3[n]])== 0)
										            	|| (((setsganados[juga3[g]]+setsperdidos[juga3[g]])!= 0) && (setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]]) > (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))))
										            	|| ((setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]])) == (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))) && (((gamesganados[juga3[n]]+gamesperdidos[juga3[n]])== 0)
										            	|| (((gamesganados[juga3[g]]+gamesperdidos[juga3[g]])!= 0) && (gamesganados[juga3[g]]/(gamesganados[juga3[g]]+gamesperdidos[juga3[g]]) > (gamesganados[juga3[n]]/(gamesganados[juga3[n]]+gamesperdidos[juga3[n]])))))) {
									                aux = puntos3[n];
									                aux2 = juga3[n];
									                puntos3[n] = puntos3[g];
									                juga3[n] = juga3[g];
									                puntos3[g] = aux;
									                juga3[g] = aux2;
									            }
									}
								}
							}
									
								if(juga3[0]>juga3[1]){//0 tiene un orden menor
									if (juga3[0] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga3[1] == 1 && !resultadoentreambos3[0]) || (juga3[1] == 2 && !resultadoentreambos3[1]) || (juga3[1] == 3 && !resultadoentreambos3[2]))
										{
											aux = puntos3[0];
							                aux2 = juga3[0];
							                puntos3[0] = puntos3[1];
							                juga3[0] = juga3[1];
							                puntos3[1] = aux;
							                juga3[1] = aux2;
										}
									}
									else
										if(juga3[0] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[1] == 2 && !resultadoentreambos3[3]) || (juga3[1] == 3 && !resultadoentreambos3[4]))
											{
												aux = puntos3[0];
								                aux2 = juga3[0];
								                puntos3[0] = puntos3[1];
								                juga3[0] = juga3[1];
								                puntos3[1] = aux;
								                juga3[1] = aux2;
											}
										}
										else
											if(juga3[0] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga3[1] == 3 && !resultadoentreambos3[5]))
												{
													aux = puntos3[0];
									                aux2 = juga3[0];
									                puntos3[0] = puntos3[1];
									                juga3[0] = juga3[1];
									                puntos3[1] = aux;
									                juga3[1] = aux2;
												}
											}
											
								}
								else{
									if (juga3[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga3[0] == 1 && resultadoentreambos3[0]) || (juga3[0] == 2 && resultadoentreambos3[1]) || (juga3[0] == 3 && resultadoentreambos3[2]))
										{
											aux = puntos3[0];
							                aux2 = juga3[1];
							                puntos3[0] = puntos3[1];
							                juga3[0] = juga3[1];
							                puntos3[1] = aux;
							                juga3[1] = aux2;
										}
									}
									else
										if(juga3[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[0] == 2 && resultadoentreambos3[3]) || (juga3[0] == 3 && resultadoentreambos3[4]))
											{
												aux = puntos3[0];
												aux2 = juga3[1];
												puntos3[0] = puntos3[1];
												juga3[0] = juga3[1];
												puntos3[1] = aux;
												juga3[1] = aux2;}
										}
										else
											if(juga3[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga3[0] == 3 && resultadoentreambos3[5]))
												{
													aux = puntos3[0];
									                aux2 = juga3[1];
									                puntos3[0] = puntos3[1];
									                juga3[0] = juga3[1];
									                puntos3[1] = aux;
									                juga3[1] = aux2;
									                }
											}
											
								}
	}

								else
									if(puntos3[1] == puntos3[2]){//Hay  empate
										if (puntos3[2] == puntos3[3]){//Triple empate
											for (int n = 1; n < 4 ; n++) {
										        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga3[n]]+setsperdidos[juga3[n]])== 0)
										            	|| (((setsganados[juga3[g]]+setsperdidos[juga3[g]])!= 0) && (setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]]) > (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))))
										            	|| ((setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]])) == (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))) && (((gamesganados[juga3[n]]+gamesperdidos[juga3[n]])== 0)
										            	|| (((gamesganados[juga3[g]]+gamesperdidos[juga3[g]])!= 0) && (gamesganados[juga3[g]]/(gamesganados[juga3[g]]+gamesperdidos[juga3[g]]) > (gamesganados[juga3[n]]/(gamesganados[juga3[n]]+gamesperdidos[juga3[n]])))))) {
										                aux = puntos3[n];
										                aux2 = juga3[n];
										                puntos3[n] = puntos3[g];
										                juga3[n] = juga3[g];
										                puntos3[g] = aux;
										                juga3[g] = aux2;
										            }
										        }
										    }
										}	
								if(juga3[1]>juga3[2]){//0 tiene un orden menor
									if (juga3[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga3[2] == 1 && !resultadoentreambos3[0]) || (juga3[2] == 2 && !resultadoentreambos3[1]) || (juga3[2] == 3 && !resultadoentreambos3[2]))
										{
											aux = puntos3[1];
							                aux2 = juga3[1];
							                puntos3[1] = puntos3[2];
							                juga3[1] = juga3[2];
							                puntos3[2] = aux;
							                juga3[2] = aux2;
										}
									}
									else
										if(juga3[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[2] == 2 && !resultadoentreambos3[3]) || (juga3[2] == 3 && !resultadoentreambos3[4]))
											{
												aux = puntos3[1];
								                aux2 = juga3[1];
								                puntos3[1] = puntos3[2];
								                juga3[1] = juga3[2];
								                puntos3[2] = aux;
								                juga3[2] = aux2;
											}
										}
										else
											if(juga3[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga3[2] == 3 && !resultadoentreambos3[5]))
												{
													aux = puntos3[1];
									                aux2 = juga3[1];
									                puntos3[1] = puntos3[2];
									                juga3[1] = juga3[2];
									                puntos3[2] = aux;
									                juga3[2] = aux2;
												}
											}
											
								}
								else{
									if (juga3[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga3[1] == 1 && resultadoentreambos3[0]) || (juga3[1] == 2 && resultadoentreambos3[1]) || (juga3[1] == 3 && resultadoentreambos3[2]))
										{
											aux = puntos3[1];
							                aux2 = juga3[1];
							                puntos3[1] = puntos3[2];
							                juga3[1] = juga3[2];
							                puntos3[2] = aux;
							                juga3[2] = aux2;
										}
									}
									else
										if(juga3[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[1] == 2 && resultadoentreambos3[3]) || (juga3[1] == 3 && resultadoentreambos3[4]))
											{
												aux = puntos3[1];
								                aux2 = juga3[1];
								                puntos3[1] = puntos3[2];
								                juga3[1] = juga3[2];
								                puntos3[2] = aux;
								                juga3[2] = aux2;
											}
										else
											if(juga3[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga3[1] == 3 && resultadoentreambos3[5]))
												{
													aux = puntos3[1];
									                aux2 = juga3[1];
									                puntos3[1] = puntos3[2];
									                juga3[1] = juga3[2];
									                puntos3[2] = aux;
									                juga3[2] = aux2;
												}
											}
											
								}
								}
								}
									else
										if (puntos3[2] == puntos3[3]){//Hay empate
											
								if(juga3[2]>juga3[3]){//0 tiene un orden menor
									if (juga3[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga3[3] == 1 && !resultadoentreambos3[0]) || (juga3[3] == 2 && !resultadoentreambos3[1]) || (juga3[3] == 3 && !resultadoentreambos3[2]))
										{
											aux = puntos3[2];
							                aux2 = juga3[2];
							                puntos3[2] = puntos3[3];
							                juga3[2] = juga3[3];
							                puntos3[3] = aux;
							                juga3[3] = aux2;
										}
									}
									else
										if(juga3[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[3] == 2 && !resultadoentreambos3[3]) || (juga3[3] == 3 && !resultadoentreambos3[4]))
											{
												aux = puntos3[2];
								                aux2 = juga3[2];
								                puntos3[2] = puntos3[3];
								                juga3[2] = juga3[3];
								                puntos3[3] = aux;
								                juga3[3] = aux2;
											}
										}
										else
											if(juga3[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga3[3] == 3 && !resultadoentreambos3[5]))
												{
													aux = puntos3[2];
									                aux2 = juga3[2];
									                puntos3[2] = puntos3[3];
									                juga3[2] = juga3[3];
									                puntos3[3] = aux;
									                juga3[3] = aux2;
												}
											}
								}
								else{
									if (juga3[3] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga3[2] == 1 && resultadoentreambos3[0]) || (juga3[2] == 2 && resultadoentreambos3[1]) || (juga3[2] == 3 && resultadoentreambos3[2]))
										{
											aux = puntos3[2];
							                aux2 = juga3[2];
							                puntos3[2] = puntos3[3];
							                juga3[2] = juga3[3];
							                puntos3[3] = aux;
							                juga3[3] = aux2;
										}
									}
									else
										if(juga3[3] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[2] == 2 && resultadoentreambos3[3]) || (juga3[2] == 3 && resultadoentreambos3[4]))
											{
												aux = puntos3[2];
								                aux2 = juga3[2];
								                puntos3[2] = puntos3[3];
								                juga3[2] = juga3[3];
								                puntos3[3] = aux;
								                juga3[3] = aux2;
											}
										else
											if(juga3[3] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga3[2] == 3 && resultadoentreambos3[5]))
												{
													aux = puntos3[2];
									                aux2 = juga3[2];
									                puntos3[2] = puntos3[3];
									                juga3[2] = juga3[3];
									                puntos3[3] = aux;
									                juga3[3] = aux2;
												}
											}
								}
								}
										}
								if (puntos4[0] == puntos4[1]){//Hay empate
									if(puntos4[1] == puntos4[2]){//Hay triple empate
										if (puntos4[2] == puntos4[3]){//Todos empatados
											for (int n = 0; n < 4 ; n++) {
										        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga4[n]]+setsperdidos[juga4[n]])== 0)
										            	|| (((setsganados[juga4[g]]+setsperdidos[juga4[g]])!= 0) && (setsganados[juga4[g]]/(setsganados[juga4[g]]+setsperdidos[juga4[g]]) > (setsganados[juga4[n]]/(setsganados[juga4[n]]+setsperdidos[juga4[n]]))))
										            	|| ((setsganados[juga4[g]]/(setsganados[juga4[g]]+setsperdidos[juga4[g]])) == (setsganados[juga4[n]]/(setsganados[juga4[n]]+setsperdidos[juga4[n]]))) && (((gamesganados[juga4[n]]+gamesperdidos[juga4[n]])== 0)
										            	|| (((gamesganados[juga4[g]]+gamesperdidos[juga4[g]])!= 0) && (gamesganados[juga4[g]]/(gamesganados[juga4[g]]+gamesperdidos[juga4[g]]) > (gamesganados[juga4[n]]/(gamesganados[juga4[n]]+gamesperdidos[juga4[n]])))))) {
										                aux = puntos4[n];
										                aux2 = juga4[n];
										                puntos4[n] = puntos4[g];
										                juga4[n] = juga4[g];
										                puntos4[g] = aux;
										                juga4[g] = aux2;
										            }
										        }
										    }
										}
										for (int n = 0; n < 3 ; n++) {
									        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga4[n]]+setsperdidos[juga4[n]])== 0)
										            	|| (((setsganados[juga4[g]]+setsperdidos[juga4[g]])!= 0) && (setsganados[juga4[g]]/(setsganados[juga4[g]]+setsperdidos[juga4[g]]) > (setsganados[juga4[n]]/(setsganados[juga4[n]]+setsperdidos[juga4[n]]))))
										            	|| ((setsganados[juga4[g]]/(setsganados[juga4[g]]+setsperdidos[juga4[g]])) == (setsganados[juga4[n]]/(setsganados[juga4[n]]+setsperdidos[juga4[n]]))) && (((gamesganados[juga4[n]]+gamesperdidos[juga4[n]])== 0)
										            	|| (((gamesganados[juga4[g]]+gamesperdidos[juga4[g]])!= 0) && (gamesganados[juga4[g]]/(gamesganados[juga4[g]]+gamesperdidos[juga4[g]]) > (gamesganados[juga4[n]]/(gamesganados[juga4[n]]+gamesperdidos[juga4[n]])))))) {
									                aux = puntos4[n];
									                aux2 = juga4[n];
									                puntos4[n] = puntos4[g];
									                juga4[n] = juga4[g];
									                puntos4[g] = aux;
									                juga4[g] = aux2;
									            }
									}
								}
							}
									
								if(juga4[0]>juga4[1]){//0 tiene un orden menor
									if (juga4[0] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga4[1] == 1 && !resultadoentreambos4[0]) || (juga4[1] == 2 && !resultadoentreambos4[1]) || (juga4[1] == 3 && !resultadoentreambos4[2]))
										{
											aux = puntos4[0];
							                aux2 = juga4[0];
							                puntos4[0] = puntos4[1];
							                juga4[0] = juga4[1];
							                puntos4[1] = aux;
							                juga4[1] = aux2;
										}
									}
									else
										if(juga4[0] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga4[1] == 2 && !resultadoentreambos4[3]) || (juga4[1] == 3 && !resultadoentreambos4[4]))
											{
												aux = puntos4[0];
								                aux2 = juga4[0];
								                puntos4[0] = puntos4[1];
								                juga4[0] = juga4[1];
								                puntos4[1] = aux;
								                juga4[1] = aux2;
											}
										}
										else
											if(juga4[0] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga4[1] == 3 && !resultadoentreambos4[5]))
												{
													aux = puntos4[0];
									                aux2 = juga4[0];
									                puntos4[0] = puntos4[1];
									                juga4[0] = juga4[1];
									                puntos4[1] = aux;
									                juga4[1] = aux2;
												}
											}
											
								}
								else{
									if (juga4[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga4[0] == 1 && resultadoentreambos4[0]) || (juga4[0] == 2 && resultadoentreambos4[1]) || (juga4[0] == 3 && resultadoentreambos4[2]))
										{
											aux = puntos4[0];
							                aux2 = juga4[1];
							                puntos4[0] = puntos4[1];
							                juga4[0] = juga4[1];
							                puntos4[1] = aux;
							                juga4[1] = aux2;
										}
									}
									else
										if(juga4[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga4[0] == 2 && resultadoentreambos4[3]) || (juga4[0] == 3 && resultadoentreambos4[4]))
											{
												aux = puntos4[0];
												aux2 = juga4[1];
												puntos4[0] = puntos4[1];
												juga4[0] = juga4[1];
												puntos4[1] = aux;
												juga4[1] = aux2;}
										}
										else
											if(juga4[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga4[0] == 3 && resultadoentreambos4[5]))
												{
													aux = puntos4[0];
									                aux2 = juga4[1];
									                puntos4[0] = puntos4[1];
									                juga4[0] = juga4[1];
									                puntos4[1] = aux;
									                juga4[1] = aux2;
									                }
											}
											
								}
	}

								else
									if(puntos4[1] == puntos4[2]){//Hay  empate
										if (puntos4[2] == puntos4[3]){//Triple empate
											for (int n = 1; n < 4 ; n++) {
										        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga4[n]]+setsperdidos[juga4[n]])== 0)
										            	|| (((setsganados[juga4[g]]+setsperdidos[juga4[g]])!= 0) && (setsganados[juga4[g]]/(setsganados[juga4[g]]+setsperdidos[juga4[g]]) > (setsganados[juga4[n]]/(setsganados[juga4[n]]+setsperdidos[juga4[n]]))))
										            	|| ((setsganados[juga4[g]]/(setsganados[juga4[g]]+setsperdidos[juga4[g]])) == (setsganados[juga4[n]]/(setsganados[juga4[n]]+setsperdidos[juga4[n]]))) && (((gamesganados[juga4[n]]+gamesperdidos[juga4[n]])== 0)
										            	|| (((gamesganados[juga4[g]]+gamesperdidos[juga4[g]])!= 0) && (gamesganados[juga4[g]]/(gamesganados[juga4[g]]+gamesperdidos[juga4[g]]) > (gamesganados[juga4[n]]/(gamesganados[juga4[n]]+gamesperdidos[juga4[n]])))))) {
										                aux = puntos4[n];
										                aux2 = juga4[n];
										                puntos4[n] = puntos4[g];
										                juga4[n] = juga4[g];
										                puntos4[g] = aux;
										                juga4[g] = aux2;
										            }
										        }
										    }
										}	
								if(juga4[1]>juga4[2]){//0 tiene un orden menor
									if (juga4[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga4[2] == 1 && !resultadoentreambos4[0]) || (juga4[2] == 2 && !resultadoentreambos4[1]) || (juga4[2] == 3 && !resultadoentreambos4[2]))
										{
											aux = puntos4[1];
							                aux2 = juga4[1];
							                puntos4[1] = puntos4[2];
							                juga4[1] = juga4[2];
							                puntos4[2] = aux;
							                juga4[2] = aux2;
										}
									}
									else
										if(juga4[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga4[2] == 2 && !resultadoentreambos4[3]) || (juga4[2] == 3 && !resultadoentreambos4[4]))
											{
												aux = puntos4[1];
								                aux2 = juga4[1];
								                puntos4[1] = puntos4[2];
								                juga4[1] = juga4[2];
								                puntos4[2] = aux;
								                juga4[2] = aux2;
											}
										}
										else
											if(juga4[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga4[2] == 3 && !resultadoentreambos4[5]))
												{
													aux = puntos4[1];
									                aux2 = juga4[1];
									                puntos4[1] = puntos4[2];
									                juga4[1] = juga4[2];
									                puntos4[2] = aux;
									                juga4[2] = aux2;
												}
											}
											
								}
								else{
									if (juga4[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga4[1] == 1 && resultadoentreambos4[0]) || (juga4[1] == 2 && resultadoentreambos4[1]) || (juga4[1] == 3 && resultadoentreambos4[2]))
										{
											aux = puntos4[1];
							                aux2 = juga4[1];
							                puntos4[1] = puntos4[2];
							                juga4[1] = juga4[2];
							                puntos4[2] = aux;
							                juga4[2] = aux2;
										}
									}
									else
										if(juga4[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga4[1] == 2 && resultadoentreambos4[3]) || (juga4[1] == 3 && resultadoentreambos4[4]))
											{
												aux = puntos4[1];
								                aux2 = juga4[1];
								                puntos4[1] = puntos4[2];
								                juga4[1] = juga4[2];
								                puntos4[2] = aux;
								                juga4[2] = aux2;
											}
										else
											if(juga4[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga4[1] == 3 && resultadoentreambos4[5]))
												{
													aux = puntos4[1];
									                aux2 = juga4[1];
									                puntos4[1] = puntos4[2];
									                juga4[1] = juga4[2];
									                puntos4[2] = aux;
									                juga4[2] = aux2;
												}
											}
											
								}
								}
								}
									else
										if (puntos4[2] == puntos4[3]){//Hay empate
											
								if(juga4[2]>juga4[3]){//0 tiene un orden menor
									if (juga4[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga4[3] == 1 && !resultadoentreambos4[0]) || (juga4[3] == 2 && !resultadoentreambos4[1]) || (juga4[3] == 3 && !resultadoentreambos4[2]))
										{
											aux = puntos4[2];
							                aux2 = juga4[2];
							                puntos4[2] = puntos4[3];
							                juga4[2] = juga4[3];
							                puntos4[3] = aux;
							                juga4[3] = aux2;
										}
									}
									else
										if(juga4[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga4[3] == 2 && !resultadoentreambos4[3]) || (juga4[3] == 3 && !resultadoentreambos4[4]))
											{
												aux = puntos4[2];
								                aux2 = juga4[2];
								                puntos4[2] = puntos4[3];
								                juga4[2] = juga4[3];
								                puntos4[3] = aux;
								                juga4[3] = aux2;
											}
										}
										else
											if(juga4[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga4[3] == 3 && !resultadoentreambos4[5]))
												{
													aux = puntos4[2];
									                aux2 = juga4[2];
									                puntos4[2] = puntos4[3];
									                juga4[2] = juga4[3];
									                puntos4[3] = aux;
									                juga4[3] = aux2;
												}
											}
								}
								else{
									if (juga4[3] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga4[2] == 1 && resultadoentreambos4[0]) || (juga4[2] == 2 && resultadoentreambos4[1]) || (juga4[2] == 3 && resultadoentreambos4[2]))
										{
											aux = puntos4[2];
							                aux2 = juga4[2];
							                puntos4[2] = puntos4[3];
							                juga4[2] = juga4[3];
							                puntos4[3] = aux;
							                juga4[3] = aux2;
										}
									}
									else
										if(juga4[3] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga4[2] == 2 && resultadoentreambos4[3]) || (juga4[2] == 3 && resultadoentreambos4[4]))
											{
												aux = puntos4[2];
								                aux2 = juga4[2];
								                puntos4[2] = puntos4[3];
								                juga4[2] = juga4[3];
								                puntos4[3] = aux;
								                juga4[3] = aux2;
											}
										else
											if(juga4[3] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga4[2] == 3 && resultadoentreambos4[5]))
												{
													aux = puntos4[2];
									                aux2 = juga4[2];
									                puntos4[2] = puntos4[3];
									                juga4[2] = juga4[3];
									                puntos4[3] = aux;
									                juga4[3] = aux2;
												}
											}
								}
								}
										}

									for (int n = 0;n<4;n++){
									over.setFontAndSize(bf, letra);
									over.setTextMatrix(CoordPDF[21], CoordPDF[juga[n]+4]);
									over.showText(""+(n+1));
									over.setTextMatrix(CoordPDF[21], CoordPDF[juga2[n]+8]);
									over.showText(""+(n+1));
									over.setTextMatrix(CoordPDF[21], CoordPDF[juga3[n]+12]);
									over.showText(""+(n+1));
									over.setTextMatrix(CoordPDF[21], CoordPDF[juga4[n]+16]);
									over.showText(""+(n+1));
									}
							over.endText();
							}
					stamper.close();
					reader.close();		
			
				
				} catch (IOException | DocumentException m) {
					m.printStackTrace();
				}  //Entrego el file del cuadro o donde se guarda dicho pdf
			break;
			}

			}

/*----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------*/
		case 5:{
			try {
				int puntos [] = new int [5];
				int setsganados[] = new int [5];
				int setsperdidos[] = new int [5];
				int gamesganados[] = new int [5];
				int gamesperdidos[] = new int [5];
				boolean resultadoentreambos [] = new boolean [10];
				for (int u=0;u<5;u++){
					puntos[u] = 0;
					setsganados[u] = 0;
					setsperdidos[u] = 0;
					gamesganados[u] = 0;
					gamesperdidos[u] = 0;
				}
				for (int u=0;u<10;u++){
					resultadoentreambos[u] = false;
				}	
				int posicion;
				int posicion2;
				int result;
				PdfReader reader = new PdfReader(pDFIN);
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
				//loop on pages (1-based)
				for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
						PdfContentByte over = stamper.getOverContent(i);
		      
						over.beginText();
						int res = 0;
							while (res<resultados.length){
								if (!resultados[res].equalsIgnoreCase("")){
									//Cargo posicion zona 1
									posicion = 0;
									posicion2 = 0;
									result = res;
									result++;
										if (result < 5)
											posicion = 0;
										if (result >= 5 && result<8)
											posicion = 1;
										if (result>=8 && result<10)
											posicion = 2;
										if (result == 10)
											posicion = 3;
									//Cargo posicion 2 zona
									result = res;
									result++;
										if (result == 1)
											posicion2 = 1;
										if (result == 2 || result == 5)
											posicion2 = 2;
										if (result == 3 || result == 6 || result == 8)
											posicion2 = 3;
										if (result == 4 || result == 7 || result == 9 || result == 10)
											posicion2 = 4;
									encontre = false;
									if (!resultados[res].equalsIgnoreCase("BYE")){
										if(EvaluacionResultado(resultados[res])){
											puntos[posicion] +=2;
											resultadoentreambos[res] = true;
											if (!resultados[res].equalsIgnoreCase("WO 2"))
												puntos[posicion2]++;
											puntos[posicion]++;
										}
										else
										{
											puntos[posicion2]+=2;
											if (!resultados[res].equalsIgnoreCase("WO 1"))
												puntos[posicion]++;
											puntos[posicion2]++;
										}
										if (!resultados[res].contains("WO"))
									{
										setsganados[posicion] += Integer.parseInt(setsganados(resultados[res],1));
										setsperdidos[posicion] += Integer.parseInt(setsganados(resultados[res],2));
										setsganados[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
										setsperdidos[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
									}
									}
									over.setFontAndSize(bf, letra);
									over.setTextMatrix(CoordPDF[posicion2], CoordPDF[5+posicion]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										if(resultados[res].contains("WO"))
											over.showText("WO.");
										else
											over.showText(resultados[res]);
									//Imprimo resultado en la otra parte de la zona
									over.setTextMatrix(CoordPDF[posicion], CoordPDF[5+posicion2]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										over.showText(darvueltaresult2(resultados[res]));
								}
								res++;
								p++;	       	
				
							}
							for (int l = 0;l<5;l++){
							over.setFontAndSize(bf,letra);
							over.setTextMatrix(CoordPDF[10], CoordPDF[5+l]);
							over.showText(""+puntos[l]);
							}
							int [] juga = new int [5];
							juga[0] = 0;
							juga[1] = 1;
							juga[2] = 2;
							juga[3] = 3;
							juga[4] = 4;
							int aux;
							int aux2;
						    //Ordeno de manera estricta
							for (int n = 0; n < 5 ; n++) {
						        for (int g = n + 1; g < 5; g++) {
						            if (puntos[g] > puntos[n]) {
						                aux = puntos[n];
						                aux2 = juga[n];
						                puntos[n] = puntos[g];
						                juga[n] = juga[g];
						                puntos[g] = aux;
						                juga[g] = aux2;
						            }
						        }
						    }	
							//Verifico si no hay igualdades
							//Igualdades desde el primero
							if (puntos[0] == puntos [1]){//Hay empate en entre los primeros
								if (puntos[1] == puntos[2]){//Hay triple empate
									if(puntos[2] == puntos[3]){//Hay cuadruple empate
										if (puntos[3] == puntos[4]){//Todos empatados
											for (int n = 0; n < 5 ; n++) {
										        for (int g = n + 1; g < 5; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
										                aux = puntos[n];
										                aux2 = juga[n];
										                puntos[n] = puntos[g];
										                juga[n] = juga[g];
										                puntos[g] = aux;
										                juga[g] = aux2;
										            }
										        }
										    }
										}
										for (int n = 0; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
									                aux = puntos[n];
									                aux2 = juga[n];
									                puntos[n] = puntos[g];
									                juga[n] = juga[g];
									                puntos[g] = aux;
									                juga[g] = aux2;
									            }
									}
								}
							}
									for (int n = 0; n < 3 ; n++) {
								        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
								            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
									            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
									            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
									            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
								                aux = puntos[n];
								                aux2 = juga[n];
								                puntos[n] = puntos[g];
								                juga[n] = juga[g];
								                puntos[g] = aux;
								                juga[g] = aux2;
								            }
								        }
								        }
									}
								if(juga[0]>juga[1]){//0 tiene un orden menor
									if (juga[0] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) || (juga[1] == 3 && !resultadoentreambos[2])||(juga[1] == 4 && !resultadoentreambos[3]))
										{
											aux = puntos[0];
							                aux2 = juga[0];
							                puntos[0] = puntos[1];
							                juga[0] = juga[1];
							                puntos[1] = aux;
							                juga[1] = aux2;
										}
									}
									else
										if(juga[0] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[1] == 2 && !resultadoentreambos[4]) || (juga[1] == 3 && !resultadoentreambos[5]) || (juga[1] == 4 && !resultadoentreambos[6]))
											{
												aux = puntos[0];
								                aux2 = juga[0];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
											}
										}
										else
											if(juga[0] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[1] == 3 && !resultadoentreambos[7]) || (juga[1] == 4 && !resultadoentreambos[8]))
												{
													aux = puntos[0];
									                aux2 = juga[0];
									                puntos[0] = puntos[1];
									                juga[0] = juga[1];
									                puntos[1] = aux;
									                juga[1] = aux2;
												}
											}
											else
												if(juga[0] == 3){//es el 2do jugador, resultado entre 4 y 6
													if ((juga[1] == 4 && !resultadoentreambos[9]))
													{
														aux = puntos[0];
										                aux2 = juga[0];
										                puntos[0] = puntos[1];
										                juga[0] = juga[1];
										                puntos[1] = aux;
										                juga[1] = aux2;
													}
												}
								}
								else{
									if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[0] == 1 && resultadoentreambos[0]) || (juga[0] == 2 && resultadoentreambos[1]) || (juga[0] == 3 && resultadoentreambos[2])||(juga[0] == 4 && resultadoentreambos[3]))
										{
											aux = puntos[0];
							                aux2 = juga[0];
							                puntos[0] = puntos[1];
							                juga[0] = juga[1];
							                puntos[1] = aux;
							                juga[1] = aux2;
										}
									}
									else
										if(juga[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[0] == 2 && resultadoentreambos[4]) || (juga[0] == 3 && resultadoentreambos[5]) || (juga[0] == 4 && resultadoentreambos[6]))
											{
												aux = puntos[0];
								                aux2 = juga[0];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
											}
										}
										else
											if(juga[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[0] == 3 && resultadoentreambos[7]) || (juga[0] == 4 && resultadoentreambos[8]))
												{
													aux = puntos[0];
									                aux2 = juga[0];
									                puntos[0] = puntos[1];
									                juga[0] = juga[1];
									                puntos[1] = aux;
									                juga[1] = aux2;
												}
											}
											else
												if(juga[1] == 3){//es el 2do jugador, resultado entre 4 y 6
													if ((juga[0] == 4 && resultadoentreambos[9]))
													{
														aux = puntos[0];
										                aux2 = juga[0];
										                puntos[0] = puntos[1];
										                juga[0] = juga[1];
										                puntos[1] = aux;
										                juga[1] = aux2;
													}
												}
								}
							}
							else
								if (puntos[1] == puntos[2]){//Hay empate
									if(puntos[2] == puntos[3]){//Hay triple empate
										if (puntos[3] == puntos[4]){//Todos empatados
											for (int n = 1; n < 5 ; n++) {
										        for (int g = n + 1; g < 5; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
										                aux = puntos[n];
										                aux2 = juga[n];
										                puntos[n] = puntos[g];
										                juga[n] = juga[g];
										                puntos[g] = aux;
										                juga[g] = aux2;
										            }
										        }
										    }
										}
										for (int n = 1; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
									                aux = puntos[n];
									                aux2 = juga[n];
									                puntos[n] = puntos[g];
									                juga[n] = juga[g];
									                puntos[g] = aux;
									                juga[g] = aux2;
									            }
									}
								}
							}
									
								if(juga[1]>juga[2]){//0 tiene un orden menor
									if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[2] == 1 && !resultadoentreambos[0]) || (juga[2] == 2 && !resultadoentreambos[1]) || (juga[2] == 3 && !resultadoentreambos[2])||(juga[2] == 4 && !resultadoentreambos[3]))
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[2];
							                juga[1] = juga[2];
							                puntos[2] = aux;
							                juga[2] = aux2;
										}
									}
									else
										if(juga[1] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[2] == 2 && !resultadoentreambos[4]) || (juga[2] == 3 && !resultadoentreambos[5]) || (juga[2] == 4 && !resultadoentreambos[6]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[2];
								                juga[1] = juga[2];
								                puntos[2] = aux;
								                juga[2] = aux2;
											}
										}
										else
											if(juga[1] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[2] == 3 && !resultadoentreambos[7]) || (juga[2] == 4 && !resultadoentreambos[8]))
												{
													aux = puntos[1];
									                aux2 = juga[1];
									                puntos[1] = puntos[2];
									                juga[1] = juga[2];
									                puntos[2] = aux;
									                juga[2] = aux2;
												}
											}
											else
												if(juga[1] == 3){//es el 2do jugador, resultado entre 4 y 6
													if ((juga[2] == 4 && !resultadoentreambos[9]))
													{
														aux = puntos[1];
										                aux2 = juga[2];
										                puntos[1] = puntos[2];
										                juga[1] = juga[2];
										                puntos[2] = aux;
										                juga[2] = aux2;
													}
												}
								}
								else{
									if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[1] == 1 && resultadoentreambos[0]) || (juga[1] == 2 && resultadoentreambos[1]) || (juga[1] == 3 && resultadoentreambos[2])||(juga[1] == 4 && resultadoentreambos[3]))
										{
											aux = puntos[1];
							                aux2 = juga[2];
							                puntos[1] = puntos[2];
							                juga[1] = juga[2];
							                puntos[2] = aux;
							                juga[2] = aux2;
										}
									}
									else
										if(juga[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[1] == 2 && resultadoentreambos[4]) || (juga[1] == 3 && resultadoentreambos[5]) || (juga[1] == 4 && resultadoentreambos[6]))
											{
												aux = puntos[1];
												aux2 = juga[2];
												puntos[1] = puntos[2];
												juga[1] = juga[2];
												puntos[2] = aux;
												juga[2] = aux2;}
										}
										else
											if(juga[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[1] == 3 && resultadoentreambos[7]) || (juga[1] == 4 && resultadoentreambos[8]))
												{
													aux = puntos[1];
									                aux2 = juga[2];
									                puntos[1] = puntos[2];
									                juga[1] = juga[2];
									                puntos[2] = aux;
									                juga[2] = aux2;
									                }
											}
											else
												if(juga[2] == 3){//es el 2do jugador, resultado entre 4 y 6
													if ((juga[1] == 4 && resultadoentreambos[9]))
													{
														aux = puntos[1];
										                aux2 = juga[2];
										                puntos[1] = puntos[2];
										                juga[1] = juga[2];
										                puntos[2] = aux;
										                juga[2] = aux2;
													}
												}
								}
							}
								else
									if(puntos[2] == puntos[3]){//Hay  empate
										if (puntos[3] == puntos[4]){//Triple empate
											for (int n = 2; n < 5 ; n++) {
										        for (int g = n + 1; g < 5; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
										                aux = puntos[n];
										                aux2 = juga[n];
										                puntos[n] = puntos[g];
										                juga[n] = juga[g];
										                puntos[g] = aux;
										                juga[g] = aux2;
										            }
										        }
										    }
										}	
								if(juga[2]>juga[3]){//0 tiene un orden menor
									if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[3] == 1 && !resultadoentreambos[0]) || (juga[3] == 2 && !resultadoentreambos[1]) || (juga[3] == 3 && !resultadoentreambos[2])||(juga[3] == 4 && !resultadoentreambos[3]))
										{
											aux = puntos[2];
							                aux2 = juga[2];
							                puntos[2] = puntos[3];
							                juga[2] = juga[3];
							                puntos[3] = aux;
							                juga[3] = aux2;
										}
									}
									else
										if(juga[2] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[3] == 2 && !resultadoentreambos[4]) || (juga[3] == 3 && !resultadoentreambos[5]) || (juga[3] == 4 && !resultadoentreambos[6]))
											{
												aux = puntos[2];
								                aux2 = juga[2];
								                puntos[2] = puntos[3];
								                juga[2] = juga[3];
								                puntos[3] = aux;
								                juga[3] = aux2;
											}
										}
										else
											if(juga[2] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[3] == 3 && !resultadoentreambos[7]) || (juga[3] == 4 && !resultadoentreambos[8]))
												{
													aux = puntos[2];
									                aux2 = juga[2];
									                puntos[2] = puntos[3];
									                juga[2] = juga[3];
									                puntos[3] = aux;
									                juga[3] = aux2;
												}
											}
											else
												if(juga[2] == 3){//es el 2do jugador, resultado entre 4 y 6
													if ((juga[3] == 4 && !resultadoentreambos[9]))
													{
														aux = puntos[2];
										                aux2 = juga[2];
										                puntos[2] = puntos[3];
										                juga[2] = juga[3];
										                puntos[3] = aux;
										                juga[3] = aux2;
													}
												}
								}
								else{
									if (juga[3] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[2] == 1 && resultadoentreambos[0]) || (juga[2] == 2 && resultadoentreambos[1]) || (juga[2] == 3 && resultadoentreambos[2])||(juga[2] == 4 && resultadoentreambos[3]))
										{
											aux = puntos[2];
							                aux2 = juga[2];
							                puntos[2] = puntos[3];
							                juga[2] = juga[3];
							                puntos[3] = aux;
							                juga[3] = aux2;
										}
									}
									else
										if(juga[3] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[2] == 2 && resultadoentreambos[4]) || (juga[2] == 3 && resultadoentreambos[5]) || (juga[2] == 4 && resultadoentreambos[6]))
											{
												aux = puntos[2];
								                aux2 = juga[2];
								                puntos[2] = puntos[3];
								                juga[2] = juga[3];
								                puntos[3] = aux;
								                juga[3] = aux2;
											}
										else
											if(juga[3] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[2] == 3 && resultadoentreambos[7]) || (juga[2] == 4 && resultadoentreambos[8]))
												{
													aux = puntos[2];
									                aux2 = juga[2];
									                puntos[2] = puntos[3];
									                juga[2] = juga[3];
									                puntos[3] = aux;
									                juga[3] = aux2;
												}
											}
											else
												if(juga[3] == 3){//es el 2do jugador, resultado entre 4 y 6
													if ((juga[2] == 4 && resultadoentreambos[9]))
													{
														aux = puntos[2];
										                aux2 = juga[2];
										                puntos[2] = puntos[3];
										                juga[2] = juga[3];
										                puntos[3] = aux;
										                juga[3] = aux2;
													}
												}
								}
								}
								}
									else
										if (puntos[3] == puntos[4]){//Hay empate
											
								if(juga[3]>juga[4]){//0 tiene un orden menor
									if (juga[3] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[4] == 1 && !resultadoentreambos[0]) || (juga[4] == 2 && !resultadoentreambos[1]) || (juga[4] == 3 && !resultadoentreambos[2])||(juga[4] == 4 && !resultadoentreambos[3]))
										{
											aux = puntos[3];
							                aux2 = juga[3];
							                puntos[3] = puntos[4];
							                juga[3] = juga[4];
							                puntos[4] = aux;
							                juga[4] = aux2;
										}
									}
									else
										if(juga[3] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[4] == 2 && !resultadoentreambos[4]) || (juga[4] == 3 && !resultadoentreambos[5]) || (juga[4] == 4 && !resultadoentreambos[6]))
											{
												aux = puntos[3];
								                aux2 = juga[3];
								                puntos[3] = puntos[4];
								                juga[3] = juga[4];
								                puntos[4] = aux;
								                juga[4] = aux2;
											}
										}
										else
											if(juga[3] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[4] == 3 && !resultadoentreambos[7]) || (juga[4] == 4 && !resultadoentreambos[8]))
												{
													aux = puntos[3];
									                aux2 = juga[3];
									                puntos[3] = puntos[4];
									                juga[3] = juga[4];
									                puntos[4] = aux;
									                juga[4] = aux2;
												}
											}
											else
												if(juga[3] == 3){//es el 2do jugador, resultado entre 4 y 6
													if ((juga[4] == 4 && !resultadoentreambos[9]))
													{
														aux = puntos[3];
										                aux2 = juga[3];
										                puntos[3] = puntos[4];
										                juga[3] = juga[4];
										                puntos[4] = aux;
										                juga[4] = aux2;
													}
												}
								}
								else{
									if (juga[4] == 0){ //es el primer jugador, resultado entre 0 y 3
										if ((juga[3] == 1 && resultadoentreambos[0]) || (juga[3] == 2 && resultadoentreambos[1]) || (juga[3] == 3 && resultadoentreambos[2])||(juga[3] == 4 && resultadoentreambos[3]))
										{
											aux = puntos[3];
							                aux2 = juga[3];
							                puntos[3] = puntos[4];
							                juga[3] = juga[4];
							                puntos[4] = aux;
							                juga[4] = aux2;
										}
									}
									else
										if(juga[4] == 1){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[3] == 2 && resultadoentreambos[4]) || (juga[3] == 3 && resultadoentreambos[5]) || (juga[3] == 4 && resultadoentreambos[6]))
											{
												aux = puntos[3];
								                aux2 = juga[3];
								                puntos[3] = puntos[4];
								                juga[3] = juga[4];
								                puntos[4] = aux;
								                juga[4] = aux2;
											}
										else
											if(juga[4] == 2){//es el 2do jugador, resultado entre 4 y 6
												if ((juga[3] == 3 && resultadoentreambos[7]) || (juga[3] == 4 && resultadoentreambos[8]))
												{
													aux = puntos[3];
									                aux2 = juga[3];
									                puntos[3] = puntos[4];
									                juga[3] = juga[4];
									                puntos[4] = aux;
									                juga[4] = aux2;
												}
											}
											else
												if(juga[4] == 3){//es el 2do jugador, resultado entre 4 y 6
													if ((juga[3] == 4 && resultadoentreambos[9]))
													{
														aux = puntos[3];
										                aux2 = juga[3];
										                puntos[3] = puntos[4];
										                juga[3] = juga[4];
										                puntos[4] = aux;
										                juga[4] = aux2;
													}
												}
								}
								}
										}
							for (int n = 0;n<5;n++){
								over.setFontAndSize(bf, letra);
								over.setTextMatrix(CoordPDF[11], CoordPDF[juga[n]+5]);
								over.showText(""+(n+1));
								over.setTextMatrix(CoordPDF[12], CoordPDF[14+n]);
								over.showText(""+(n+1));
								over.setTextMatrix(CoordPDF[13], CoordPDF[14+n]);
								if(!participantes[juga[n]].equalsIgnoreCase("BYE"))
								over.showText(participantes[juga[n]]);
						    }
						over.endText();
						}
				stamper.close();
				reader.close();		
		
			
			} catch (IOException | DocumentException m) {
				m.printStackTrace();
			}  //Entrego el file del cuadro o donde se guarda dicho pdf
		break;
		}
/*----------------------------------------------------------------------------------------
 *-----------------------------------------------------------------------------------------*/
		case 6:{
			try {
				int zonanro;
				int puntos [] = new int [3];
				int setsganados[] = new int [3];
				int setsperdidos[] = new int [3];
				int gamesganados[] = new int [3];
				int gamesperdidos[] = new int [3];
				boolean resultadoentreambos [] = new boolean [3];
				int puntos2 [] = new int [3];
				int setsganados2[] = new int [3];
				int setsperdidos2[] = new int [3];
				int gamesganados2[] = new int [3];
				int gamesperdidos2[] = new int [3];
				boolean resultadoentreambos2 [] = new boolean [3];
				for (int u=0;u<3;u++){
					puntos[u] = 0;
					setsganados[u] = 0;
					setsperdidos[u] = 0;
					gamesganados[u] = 0;
					resultadoentreambos[u] = false;
					gamesperdidos[u] = 0;
					puntos2[u] = 0;
					setsganados2[u] = 0;
					setsperdidos2[u] = 0;
					gamesganados2[u] = 0;
					resultadoentreambos2[u] = false;
					gamesperdidos2[u] = 0;
				}
				int posicion;
				int posicion2;
				int result;
				PdfReader reader = new PdfReader(pDFIN);
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
				//loop on pages (1-based)
				for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
						PdfContentByte over = stamper.getOverContent(i);
		      
						over.beginText();
						int res = 0;
							while (res<resultados.length){
								if (!resultados[res].equalsIgnoreCase("")){
									//Cargo posicion zona 1
									posicion = 0;
									posicion2 = 0;
									result = res;
									zonanro = res / 3;
									result = (result % 3)+1;
									if (result < 3)
										posicion = 0;
									if (result == 3)
										posicion = 1;
										//Cargo posicion 2 zona
									result = res;
									result = (result % 3)+1;
									if (result == 1)
										posicion2 = 1;
									if (result == 2 || result == 3)
										posicion2 = 2;
									encontre = false;
									if (!resultados[res].equalsIgnoreCase("BYE")){
										if(EvaluacionResultado(resultados[res])){
											if(zonanro == 0){
											puntos[posicion] +=2;
											resultadoentreambos[res] = true;
											if (!resultados[res].equalsIgnoreCase("WO 2"))
												puntos[posicion2]++;
											puntos[posicion]++;
											}
											else{
												puntos2[posicion] +=2;
												resultadoentreambos2[res%3] = true;
												if (!resultados[res].equalsIgnoreCase("WO 2"))
													puntos2[posicion2]++;
												puntos2[posicion]++;
											}
										}
										else
										{
											if (zonanro == 0){
											puntos[posicion2]+=2;
											if (!resultados[res].equalsIgnoreCase("WO 1"))
												puntos[posicion]++;
											puntos[posicion2]++;
											}
											else{
												puntos2[posicion2]+=2;
												if (!resultados[res].equalsIgnoreCase("WO 1"))
													puntos2[posicion]++;
												puntos2[posicion2]++;
													
											}
										}
										if (!resultados[res].contains("WO"))
									{
										if(zonanro == 0){
										setsganados[posicion] += Integer.parseInt(setsganados(resultados[res],1));
										setsperdidos[posicion] += Integer.parseInt(setsganados(resultados[res],2));
										setsganados[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
										setsperdidos[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
										}
										else{
											setsganados2[posicion] += Integer.parseInt(setsganados(resultados[res],1));
											setsperdidos2[posicion] += Integer.parseInt(setsganados(resultados[res],2));
											setsganados2[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
											setsperdidos2[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
												
										}
										}
									}
									over.setFontAndSize(bf, letra);
									over.setTextMatrix(CoordPDF[posicion2], CoordPDF[3+posicion+(3*zonanro)]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										if(resultados[res].contains("WO"))
											over.showText("WO.");
										else
											over.showText(resultados[res]);
									//Imprimo resultado en la otra parte de la zona
									over.setTextMatrix(CoordPDF[posicion], CoordPDF[3+posicion2+3*zonanro]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										over.showText(darvueltaresult2(resultados[res]));
								}
								res++;
								p++;	       	
				
							}
							for (int l = 0;l<3;l++){
							over.setFontAndSize(bf,letra);
							over.setTextMatrix(CoordPDF[9], CoordPDF[3+l]);
							over.showText(""+puntos[l]);
							over.setTextMatrix(CoordPDF[9], CoordPDF[6+l]);
							over.showText(""+puntos2[l]);}
							int [] juga = new int [3];
							juga[0] = 0;
							juga[1] = 1;
							juga[2] = 2;
							int [] juga2 = new int [3];
							juga2[0] = 0;
							juga2[1] = 1;
							juga2[2] = 2;
							int aux;
							int aux2;
						    //Ordeno de manera estricta
							for (int n = 0; n < 3 ; n++) {
						        for (int g = n + 1; g < 3; g++) {
						            if (puntos[g] > puntos[n]) {
						                aux = puntos[n];
						                aux2 = juga[n];
						                puntos[n] = puntos[g];
						                juga[n] = juga[g];
						                puntos[g] = aux;
						                juga[g] = aux2;
						            }
						            if (puntos2[g] > puntos2[n]) {
						                aux = puntos2[n];
						                aux2 = juga2[n];
						                puntos2[n] = puntos2[g];
						                juga2[n] = juga2[g];
						                puntos2[g] = aux;
						                juga2[g] = aux2;
						            }
						        }
						    }	
							//Verifico si no hay igualdades
							//Igualdades desde el primero
									if(puntos[0] == puntos[1]){//Hay  empate
										if (puntos[1] == puntos[2]){//Triple empate
											for (int n = 0; n < 3 ; n++) {
										        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
										                aux = puntos[n];
										                aux2 = juga[n];
										                puntos[n] = puntos[g];
										                juga[n] = juga[g];
										                puntos[g] = aux;
										                juga[g] = aux2;
										            }
										        }
										    }
										}	
								if(juga[0]>juga[1]){//0 tiene un orden menor
									if (juga[0] == 0){ //es el primer jugador, resultado entre 0 y 1
										if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) )
										{
											aux = puntos[0];
							                aux2 = juga[0];
							                puntos[0] = puntos[1];
							                juga[0] = juga[1];
							                puntos[1] = aux;
							                juga[1] = aux2;
										}
									}
									else
										if(juga[0] == 1){//es el 2do jugador, resultado 2
											if ((juga[1] == 2 && !resultadoentreambos[2]))
											{
												aux = puntos[0];
								                aux2 = juga[0];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
											}
										}
										
								}
								else{
									if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 1
										if ((juga[0] == 1 && !resultadoentreambos[0]) || (juga[0] == 2 && !resultadoentreambos[1]) )
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[0];
							                juga[1] = juga[0];
							                puntos[0] = aux;
							                juga[0] = aux2;
										}
									}
									else
										if(juga[1] == 1){//es el 2do jugador, resultado 2
											if ((juga[0] == 2 && !resultadoentreambos[2]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[0];
								                juga[1] = juga[0];
								                puntos[0] = aux;
								                juga[0] = aux2;
											}
										}
										
								}
									}
									else
										if (puntos[1] == puntos[2]){//Hay empate
											if(juga[1]>juga[2]){//0 tiene un orden menor
												if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 1
													if ((juga[2] == 1 && !resultadoentreambos[0]) || (juga[2] == 2 && !resultadoentreambos[1]) )
													{
														aux = puntos[1];
										                aux2 = juga[1];
										                puntos[1] = puntos[2];
										                juga[1] = juga[2];
										                puntos[2] = aux;
										                juga[2] = aux2;
													}
												}
												else
													if(juga[1] == 1){//es el 2do jugador, resultado 2
														if ((juga[2] == 2 && !resultadoentreambos[2]))
														{
															aux = puntos[1];
											                aux2 = juga[1];
											                puntos[1] = puntos[2];
											                juga[1] = juga[2];
											                puntos[2] = aux;
											                juga[2] = aux2;
														}
													}
													
											}
											else{
												if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 1
													if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) )
													{
														aux = puntos[2];
										                aux2 = juga[2];
										                puntos[2] = puntos[1];
										                juga[2] = juga[1];
										                puntos[1] = aux;
										                juga[1] = aux2;
													}
												}
												else
													if(juga[2] == 1){//es el 2do jugador, resultado 2
														if ((juga[1] == 2 && !resultadoentreambos[2]))
														{
															aux = puntos[2];
											                aux2 = juga[2];
											                puntos[2] = puntos[1];
											                juga[2] = juga[1];
											                puntos[1] = aux;
											                juga[1] = aux2;
														}
													}
													
											}	
								
										}
									//Verifico si no hay igualdades en zona 2
									//Igualdades desde el primero
											if(puntos2[0] == puntos2[1]){//Hay  empate
												if (puntos2[1] == puntos2[2]){//Triple empate
													for (int n = 0; n < 3 ; n++) {
												        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
												            if (((setsganados2[juga2[n]]+setsperdidos2[juga2[n]])== 0)
												            	|| (((setsganados2[juga2[g]]+setsperdidos2[juga2[g]])!= 0) && (setsganados2[juga2[g]]/(setsganados2[juga2[g]]+setsperdidos2[juga2[g]]) > (setsganados2[juga2[n]]/(setsganados2[juga2[n]]+setsperdidos2[juga2[n]]))))
												            	|| ((setsganados2[juga2[g]]/(setsganados2[juga2[g]]+setsperdidos2[juga2[g]])) == (setsganados2[juga2[n]]/(setsganados2[juga2[n]]+setsperdidos2[juga2[n]]))) && (((gamesganados2[juga2[n]]+gamesperdidos2[juga2[n]])== 0)
												            	|| (((gamesganados2[juga2[g]]+gamesperdidos2[juga2[g]])!= 0) && (gamesganados2[juga2[g]]/(gamesganados2[juga2[g]]+gamesperdidos2[juga2[g]]) > (gamesganados2[juga2[n]]/(gamesganados2[juga2[n]]+gamesperdidos2[juga2[n]])))))) {
												                aux = puntos2[n];
												                aux2 = juga2[n];
												                puntos2[n] = puntos2[g];
												                juga2[n] = juga2[g];
												                puntos2[g] = aux;
												                juga2[g] = aux2;
												            }
												        }
												    }
												}	
										if(juga2[0]>juga2[1]){//0 tiene un orden menor
											if (juga2[0] == 0){ //es el primer juga2dor, resultado entre 0 y 1
												if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) )
												{
													aux = puntos2[0];
									                aux2 = juga2[0];
									                puntos2[0] = puntos2[1];
									                juga2[0] = juga2[1];
									                puntos2[1] = aux;
									                juga2[1] = aux2;
												}
											}
											else
												if(juga2[0] == 1){//es el 2do juga2dor, resultado 2
													if ((juga2[1] == 2 && !resultadoentreambos2[2]))
													{
														aux = puntos2[0];
										                aux2 = juga2[0];
										                puntos2[0] = puntos2[1];
										                juga2[0] = juga2[1];
										                puntos2[1] = aux;
										                juga2[1] = aux2;
													}
												}
												
										}
										else{
											if (juga2[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
												if ((juga2[0] == 1 && !resultadoentreambos2[0]) || (juga2[0] == 2 && !resultadoentreambos2[1]) )
												{
													aux = puntos2[1];
									                aux2 = juga2[1];
									                puntos2[1] = puntos2[0];
									                juga2[1] = juga2[0];
									                puntos2[0] = aux;
									                juga2[0] = aux2;
												}
											}
											else
												if(juga2[1] == 1){//es el 2do juga2dor, resultado 2
													if ((juga2[0] == 2 && !resultadoentreambos2[2]))
													{
														aux = puntos2[1];
										                aux2 = juga2[1];
										                puntos2[1] = puntos2[0];
										                juga2[1] = juga2[0];
										                puntos2[0] = aux;
										                juga2[0] = aux2;
													}
												}
												
										}
											}
											else
												if (puntos2[1] == puntos2[2]){//Hay empate
													if(juga2[1]>juga2[2]){//0 tiene un orden menor
														if (juga2[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
															if ((juga2[2] == 1 && !resultadoentreambos2[0]) || (juga2[2] == 2 && !resultadoentreambos2[1]) )
															{
																aux = puntos2[1];
												                aux2 = juga2[1];
												                puntos2[1] = puntos2[2];
												                juga2[1] = juga2[2];
												                puntos2[2] = aux;
												                juga2[2] = aux2;
															}
														}
														else
															if(juga2[1] == 1){//es el 2do juga2dor, resultado 2
																if ((juga2[2] == 2 && !resultadoentreambos2[2]))
																{
																	aux = puntos2[1];
													                aux2 = juga2[1];
													                puntos2[1] = puntos2[2];
													                juga2[1] = juga2[2];
													                puntos2[2] = aux;
													                juga2[2] = aux2;
																}
															}
															
													}
													else{
														if (juga2[2] == 0){ //es el primer juga2dor, resultado entre 0 y 1
															if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) )
															{
																aux = puntos2[2];
												                aux2 = juga2[2];
												                puntos2[2] = puntos2[1];
												                juga2[2] = juga2[1];
												                puntos2[1] = aux;
												                juga2[1] = aux2;
															}
														}
														else
															if(juga2[2] == 1){//es el 2do juga2dor, resultado 2
																if ((juga2[1] == 2 && !resultadoentreambos2[2]))
																{
																	aux = puntos2[2];
													                aux2 = juga2[2];
													                puntos2[2] = puntos2[1];
													                juga2[2] = juga2[1];
													                puntos2[1] = aux;
													                juga2[1] = aux2;
																}
															}
															
													}	
										
												}
							for (int n = 0;n<3;n++){
								over.setFontAndSize(bf, letra);
								over.setTextMatrix(CoordPDF[10], CoordPDF[juga[n]+3]);
								over.showText(""+(n+1));
								over.setTextMatrix(CoordPDF[10], CoordPDF[juga2[n]+6]);
								over.showText(""+(n+1));
								}
						over.endText();
						}
				stamper.close();
				reader.close();		
		
			
			} catch (IOException | DocumentException m) {
				m.printStackTrace();
			}  //Entrego el file del cuadro o donde se guarda dicho pdf
		break;
		}
		
/*----------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------*/
		case 8:{
			try {
				int zonanro;
				int puntos [] = new int [4];
				int setsganados[] = new int [4];
				int setsperdidos[] = new int [4];
				int gamesganados[] = new int [4];
				int gamesperdidos[] = new int [4];
				boolean resultadoentreambos [] = new boolean [6];
				int puntos2 [] = new int [4];
				int setsganados2[] = new int [4];
				int setsperdidos2[] = new int [4];
				int gamesganados2[] = new int [4];
				int gamesperdidos2[] = new int [4];
				boolean resultadoentreambos2 [] = new boolean [6];
				for (int u=0;u<4;u++){
					puntos[u] = 0;
					setsganados[u] = 0;
					setsperdidos[u] = 0;
					gamesganados[u] = 0;
					gamesperdidos[u] = 0;
					puntos[u] = 0;
					setsganados2[u] = 0;
					setsperdidos2[u] = 0;
					gamesganados2[u] = 0;
					gamesperdidos2[u] = 0;
				}
				for (int u=0;u<6;u++){
				resultadoentreambos[u] = false;
				resultadoentreambos2[u] = false;
				}
				int posicion;
				int posicion2;
				int result;
				PdfReader reader = new PdfReader(pDFIN);
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
				//loop on pages (1-based)
				for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
						PdfContentByte over = stamper.getOverContent(i);
		      
						over.beginText();
						int res = 0;
							while (res<resultados.length){
								if (!resultados[res].equalsIgnoreCase("") && !resultados[res].equalsIgnoreCase("BYE")){
									//Cargo posicion zona 1
									posicion = 0;
									posicion2 = 0;
									result = res;
									zonanro = res / 6;
									result = (result % 6)+1;
									if (result < 4)
										posicion = 0;
									if (result >= 4 && result<6)
										posicion = 1;
									if (result == 6)
										posicion = 2;
									//Cargo posicion 2 zona
									result = res;
									result = (result % 6)+1;
									if (result == 1)
										posicion2 = 1;
									if (result == 2 || result == 4)
										posicion2 = 2;
									if (result == 3 || result == 5 || result == 6)
										posicion2 = 3;
									encontre = false;
									if (!resultados[res].equalsIgnoreCase("BYE")){
										if(EvaluacionResultado(resultados[res])){
											if(zonanro == 0){
											puntos[posicion] +=2;
											resultadoentreambos[res] = true;
											if (!resultados[res].equalsIgnoreCase("WO 2"))
												puntos[posicion2]++;
											puntos[posicion]++;
											}
											else{
												puntos2[posicion] +=2;
												resultadoentreambos2[res%6] = true;
												if (!resultados[res].equalsIgnoreCase("WO 2"))
													puntos2[posicion2]++;
												puntos2[posicion]++;
											}
										}
										else
										{
											if (zonanro == 0){
											puntos[posicion2]+=2;
											if (!resultados[res].equalsIgnoreCase("WO 1"))
												puntos[posicion]++;
											puntos[posicion2]++;
											}
											else{
												puntos2[posicion2]+=2;
												if (!resultados[res].equalsIgnoreCase("WO 1"))
													puntos2[posicion]++;
												puntos2[posicion2]++;
													
											}
										}
										if (!resultados[res].contains("WO"))
									{
										if(zonanro == 0){
										setsganados[posicion] += Integer.parseInt(setsganados(resultados[res],1));
										setsperdidos[posicion] += Integer.parseInt(setsganados(resultados[res],2));
										setsganados[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
										setsperdidos[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
										}
										else{
											setsganados2[posicion] += Integer.parseInt(setsganados(resultados[res],1));
											setsperdidos2[posicion] += Integer.parseInt(setsganados(resultados[res],2));
											setsganados2[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
											setsperdidos2[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
												
										}
										}
									}
									over.setFontAndSize(bf, letra);
									over.setTextMatrix(CoordPDF[posicion2], CoordPDF[4+posicion+(4*zonanro)]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										if(resultados[res].contains("WO"))
											over.showText("WO.");
										else
											over.showText(resultados[res]);
									//Imprimo resultado en la otra parte de la zona
									over.setTextMatrix(CoordPDF[posicion], CoordPDF[4+posicion2+4*zonanro]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										over.showText(darvueltaresult2(resultados[res]));
								}
								res++;
								p++;	       	
				
							}
							for (int l = 0;l<4;l++){
							over.setFontAndSize(bf,letra);
							over.setTextMatrix(CoordPDF[12], CoordPDF[4+l]);
							over.showText(""+puntos[l]);
							over.setTextMatrix(CoordPDF[12], CoordPDF[8+l]);
							over.showText(""+puntos2[l]);}
							int [] juga = new int [4];
							juga[0] = 0;
							juga[1] = 1;
							juga[2] = 2;
							juga[3] = 3;
							int [] juga2 = new int [4];
							juga2[0] = 0;
							juga2[1] = 1;
							juga2[2] = 2;
							juga2[3] = 3;
							int aux;
							int aux2;
						    //Ordeno de manera estricta
							for (int n = 0; n < 4 ; n++) {
						        for (int g = n + 1; g < 4; g++) {
						            if (puntos[g] > puntos[n]) {
						                aux = puntos[n];
						                aux2 = juga[n];
						                puntos[n] = puntos[g];
						                juga[n] = juga[g];
						                puntos[g] = aux;
						                juga[g] = aux2;
						            }
						            if (puntos2[g] > puntos2[n]) {
						                aux = puntos2[n];
						                aux2 = juga2[n];
						                puntos2[n] = puntos2[g];
						                juga2[n] = juga2[g];
						                puntos2[g] = aux;
						                juga2[g] = aux2;
						            }
						        }
						    }	
							if (puntos[0] == puntos[1]){//Hay empate
								if(puntos[1] == puntos[2]){//Hay triple empate
									if (puntos[2] == puntos[3]){//Todos empatados
										for (int n = 0; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
									            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
									            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
									            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
									                aux = puntos[n];
									                aux2 = juga[n];
									                puntos[n] = puntos[g];
									                juga[n] = juga[g];
									                puntos[g] = aux;
									                juga[g] = aux2;
									            }
									        }
									    }
									}
									for (int n = 0; n < 3 ; n++) {
								        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
								            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
									            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
									            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
									            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
								                aux = puntos[n];
								                aux2 = juga[n];
								                puntos[n] = puntos[g];
								                juga[n] = juga[g];
								                puntos[g] = aux;
								                juga[g] = aux2;
								            }
								}
							}
						}
								
							if(juga[0]>juga[1]){//0 tiene un orden menor
								if (juga[0] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) || (juga[1] == 3 && !resultadoentreambos[2]))
									{
										aux = puntos[0];
						                aux2 = juga[0];
						                puntos[0] = puntos[1];
						                juga[0] = juga[1];
						                puntos[1] = aux;
						                juga[1] = aux2;
									}
								}
								else
									if(juga[0] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[1] == 2 && !resultadoentreambos[3]) || (juga[1] == 3 && !resultadoentreambos[4]))
										{
											aux = puntos[0];
							                aux2 = juga[0];
							                puntos[0] = puntos[1];
							                juga[0] = juga[1];
							                puntos[1] = aux;
							                juga[1] = aux2;
										}
									}
									else
										if(juga[0] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[1] == 3 && !resultadoentreambos[5]))
											{
												aux = puntos[0];
								                aux2 = juga[0];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
											}
										}
										
							}
							else{
								if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[0] == 1 && resultadoentreambos[0]) || (juga[0] == 2 && resultadoentreambos[1]) || (juga[0] == 3 && resultadoentreambos[2]))
									{
										aux = puntos[0];
						                aux2 = juga[1];
						                puntos[0] = puntos[1];
						                juga[0] = juga[1];
						                puntos[1] = aux;
						                juga[1] = aux2;
									}
								}
								else
									if(juga[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[0] == 2 && resultadoentreambos[3]) || (juga[0] == 3 && resultadoentreambos[4]))
										{
											aux = puntos[0];
											aux2 = juga[1];
											puntos[0] = puntos[1];
											juga[0] = juga[1];
											puntos[1] = aux;
											juga[1] = aux2;}
									}
									else
										if(juga[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[0] == 3 && resultadoentreambos[5]))
											{
												aux = puntos[0];
								                aux2 = juga[1];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
								                }
										}
										
							}
}

							else
								if(puntos[1] == puntos[2]){//Hay  empate
									if (puntos[2] == puntos[3]){//Triple empate
										for (int n = 1; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
									            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
									            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
									            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
									                aux = puntos[n];
									                aux2 = juga[n];
									                puntos[n] = puntos[g];
									                juga[n] = juga[g];
									                puntos[g] = aux;
									                juga[g] = aux2;
									            }
									        }
									    }
									}	
							if(juga[1]>juga[2]){//0 tiene un orden menor
								if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[2] == 1 && !resultadoentreambos[0]) || (juga[2] == 2 && !resultadoentreambos[1]) || (juga[2] == 3 && !resultadoentreambos[2]))
									{
										aux = puntos[1];
						                aux2 = juga[1];
						                puntos[1] = puntos[2];
						                juga[1] = juga[2];
						                puntos[2] = aux;
						                juga[2] = aux2;
									}
								}
								else
									if(juga[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[2] == 2 && !resultadoentreambos[3]) || (juga[2] == 3 && !resultadoentreambos[4]))
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[2];
							                juga[1] = juga[2];
							                puntos[2] = aux;
							                juga[2] = aux2;
										}
									}
									else
										if(juga[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[2] == 3 && !resultadoentreambos[5]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[2];
								                juga[1] = juga[2];
								                puntos[2] = aux;
								                juga[2] = aux2;
											}
										}
										
							}
							else{
								if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[1] == 1 && resultadoentreambos[0]) || (juga[1] == 2 && resultadoentreambos[1]) || (juga[1] == 3 && resultadoentreambos[2]))
									{
										aux = puntos[1];
						                aux2 = juga[1];
						                puntos[1] = puntos[2];
						                juga[1] = juga[2];
						                puntos[2] = aux;
						                juga[2] = aux2;
									}
								}
								else
									if(juga[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[1] == 2 && resultadoentreambos[3]) || (juga[1] == 3 && resultadoentreambos[4]))
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[2];
							                juga[1] = juga[2];
							                puntos[2] = aux;
							                juga[2] = aux2;
										}
									else
										if(juga[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[1] == 3 && resultadoentreambos[5]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[2];
								                juga[1] = juga[2];
								                puntos[2] = aux;
								                juga[2] = aux2;
											}
										}
										
							}
							}
							}
								else
									if (puntos[2] == puntos[3]){//Hay empate
										
							if(juga[2]>juga[3]){//0 tiene un orden menor
								if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[3] == 1 && !resultadoentreambos[0]) || (juga[3] == 2 && !resultadoentreambos[1]) || (juga[3] == 3 && !resultadoentreambos[2]))
									{
										aux = puntos[2];
						                aux2 = juga[2];
						                puntos[2] = puntos[3];
						                juga[2] = juga[3];
						                puntos[3] = aux;
						                juga[3] = aux2;
									}
								}
								else
									if(juga[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[3] == 2 && !resultadoentreambos[3]) || (juga[3] == 3 && !resultadoentreambos[4]))
										{
											aux = puntos[2];
							                aux2 = juga[2];
							                puntos[2] = puntos[3];
							                juga[2] = juga[3];
							                puntos[3] = aux;
							                juga[3] = aux2;
										}
									}
									else
										if(juga[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[3] == 3 && !resultadoentreambos[5]))
											{
												aux = puntos[2];
								                aux2 = juga[2];
								                puntos[2] = puntos[3];
								                juga[2] = juga[3];
								                puntos[3] = aux;
								                juga[3] = aux2;
											}
										}
							}
							else{
								if (juga[3] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[2] == 1 && resultadoentreambos[0]) || (juga[2] == 2 && resultadoentreambos[1]) || (juga[2] == 3 && resultadoentreambos[2]))
									{
										aux = puntos[2];
						                aux2 = juga[2];
						                puntos[2] = puntos[3];
						                juga[2] = juga[3];
						                puntos[3] = aux;
						                juga[3] = aux2;
									}
								}
								else
									if(juga[3] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[2] == 2 && resultadoentreambos[3]) || (juga[2] == 3 && resultadoentreambos[4]))
										{
											aux = puntos[2];
							                aux2 = juga[2];
							                puntos[2] = puntos[3];
							                juga[2] = juga[3];
							                puntos[3] = aux;
							                juga[3] = aux2;
										}
									else
										if(juga[3] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[2] == 3 && resultadoentreambos[5]))
											{
												aux = puntos[2];
								                aux2 = juga[2];
								                puntos[2] = puntos[3];
								                juga[2] = juga[3];
								                puntos[3] = aux;
								                juga[3] = aux2;
											}
										}
							}
							}
									}
							if (puntos2[0] == puntos2[1]){//Hay empate
								if(puntos2[1] == puntos2[2]){//Hay triple empate
									if (puntos2[2] == puntos2[3]){//Todos empatados
										for (int n = 0; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
									            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
									            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
									            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
									                aux = puntos2[n];
									                aux2 = juga2[n];
									                puntos2[n] = puntos2[g];
									                juga2[n] = juga2[g];
									                puntos2[g] = aux;
									                juga2[g] = aux2;
									            }
									        }
									    }
									}
									for (int n = 0; n < 3 ; n++) {
								        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
								            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
									            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
									            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
									            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
								                aux = puntos2[n];
								                aux2 = juga2[n];
								                puntos2[n] = puntos2[g];
								                juga2[n] = juga2[g];
								                puntos2[g] = aux;
								                juga2[g] = aux2;
								            }
								}
							}
						}
								
							if(juga2[0]>juga2[1]){//0 tiene un orden menor
								if (juga2[0] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) || (juga2[1] == 3 && !resultadoentreambos2[2]))
									{
										aux = puntos2[0];
						                aux2 = juga2[0];
						                puntos2[0] = puntos2[1];
						                juga2[0] = juga2[1];
						                puntos2[1] = aux;
						                juga2[1] = aux2;
									}
								}
								else
									if(juga2[0] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[1] == 2 && !resultadoentreambos2[3]) || (juga2[1] == 3 && !resultadoentreambos2[4]))
										{
											aux = puntos2[0];
							                aux2 = juga2[0];
							                puntos2[0] = puntos2[1];
							                juga2[0] = juga2[1];
							                puntos2[1] = aux;
							                juga2[1] = aux2;
										}
									}
									else
										if(juga2[0] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[1] == 3 && !resultadoentreambos2[5]))
											{
												aux = puntos2[0];
								                aux2 = juga2[0];
								                puntos2[0] = puntos2[1];
								                juga2[0] = juga2[1];
								                puntos2[1] = aux;
								                juga2[1] = aux2;
											}
										}
										
							}
							else{
								if (juga2[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[0] == 1 && resultadoentreambos2[0]) || (juga2[0] == 2 && resultadoentreambos2[1]) || (juga2[0] == 3 && resultadoentreambos2[2]))
									{
										aux = puntos2[0];
						                aux2 = juga2[1];
						                puntos2[0] = puntos2[1];
						                juga2[0] = juga2[1];
						                puntos2[1] = aux;
						                juga2[1] = aux2;
									}
								}
								else
									if(juga2[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[0] == 2 && resultadoentreambos2[3]) || (juga2[0] == 3 && resultadoentreambos2[4]))
										{
											aux = puntos2[0];
											aux2 = juga2[1];
											puntos2[0] = puntos2[1];
											juga2[0] = juga2[1];
											puntos2[1] = aux;
											juga2[1] = aux2;}
									}
									else
										if(juga2[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[0] == 3 && resultadoentreambos2[5]))
											{
												aux = puntos2[0];
								                aux2 = juga2[1];
								                puntos2[0] = puntos2[1];
								                juga2[0] = juga2[1];
								                puntos2[1] = aux;
								                juga2[1] = aux2;
								                }
										}
										
							}
}

							else
								if(puntos2[1] == puntos2[2]){//Hay  empate
									if (puntos2[2] == puntos2[3]){//Triple empate
										for (int n = 1; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
									            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
									            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
									            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
									                aux = puntos2[n];
									                aux2 = juga2[n];
									                puntos2[n] = puntos2[g];
									                juga2[n] = juga2[g];
									                puntos2[g] = aux;
									                juga2[g] = aux2;
									            }
									        }
									    }
									}	
							if(juga2[1]>juga2[2]){//0 tiene un orden menor
								if (juga2[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[2] == 1 && !resultadoentreambos2[0]) || (juga2[2] == 2 && !resultadoentreambos2[1]) || (juga2[2] == 3 && !resultadoentreambos2[2]))
									{
										aux = puntos2[1];
						                aux2 = juga2[1];
						                puntos2[1] = puntos2[2];
						                juga2[1] = juga2[2];
						                puntos2[2] = aux;
						                juga2[2] = aux2;
									}
								}
								else
									if(juga2[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[2] == 2 && !resultadoentreambos2[3]) || (juga2[2] == 3 && !resultadoentreambos2[4]))
										{
											aux = puntos2[1];
							                aux2 = juga2[1];
							                puntos2[1] = puntos2[2];
							                juga2[1] = juga2[2];
							                puntos2[2] = aux;
							                juga2[2] = aux2;
										}
									}
									else
										if(juga2[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[2] == 3 && !resultadoentreambos2[5]))
											{
												aux = puntos2[1];
								                aux2 = juga2[1];
								                puntos2[1] = puntos2[2];
								                juga2[1] = juga2[2];
								                puntos2[2] = aux;
								                juga2[2] = aux2;
											}
										}
										
							}
							else{
								if (juga2[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[1] == 1 && resultadoentreambos2[0]) || (juga2[1] == 2 && resultadoentreambos2[1]) || (juga2[1] == 3 && resultadoentreambos2[2]))
									{
										aux = puntos2[1];
						                aux2 = juga2[1];
						                puntos2[1] = puntos2[2];
						                juga2[1] = juga2[2];
						                puntos2[2] = aux;
						                juga2[2] = aux2;
									}
								}
								else
									if(juga2[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[1] == 2 && resultadoentreambos2[3]) || (juga2[1] == 3 && resultadoentreambos2[4]))
										{
											aux = puntos2[1];
							                aux2 = juga2[1];
							                puntos2[1] = puntos2[2];
							                juga2[1] = juga2[2];
							                puntos2[2] = aux;
							                juga2[2] = aux2;
										}
									else
										if(juga2[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[1] == 3 && resultadoentreambos2[5]))
											{
												aux = puntos2[1];
								                aux2 = juga2[1];
								                puntos2[1] = puntos2[2];
								                juga2[1] = juga2[2];
								                puntos2[2] = aux;
								                juga2[2] = aux2;
											}
										}
										
							}
							}
							}
								else
									if (puntos2[2] == puntos2[3]){//Hay empate
										
							if(juga2[2]>juga2[3]){//0 tiene un orden menor
								if (juga2[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[3] == 1 && !resultadoentreambos2[0]) || (juga2[3] == 2 && !resultadoentreambos2[1]) || (juga2[3] == 3 && !resultadoentreambos2[2]))
									{
										aux = puntos2[2];
						                aux2 = juga2[2];
						                puntos2[2] = puntos2[3];
						                juga2[2] = juga2[3];
						                puntos2[3] = aux;
						                juga2[3] = aux2;
									}
								}
								else
									if(juga2[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[3] == 2 && !resultadoentreambos2[3]) || (juga2[3] == 3 && !resultadoentreambos2[4]))
										{
											aux = puntos2[2];
							                aux2 = juga2[2];
							                puntos2[2] = puntos2[3];
							                juga2[2] = juga2[3];
							                puntos2[3] = aux;
							                juga2[3] = aux2;
										}
									}
									else
										if(juga2[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[3] == 3 && !resultadoentreambos2[5]))
											{
												aux = puntos2[2];
								                aux2 = juga2[2];
								                puntos2[2] = puntos2[3];
								                juga2[2] = juga2[3];
								                puntos2[3] = aux;
								                juga2[3] = aux2;
											}
										}
							}
							else{
								if (juga2[3] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[2] == 1 && resultadoentreambos2[0]) || (juga2[2] == 2 && resultadoentreambos2[1]) || (juga2[2] == 3 && resultadoentreambos2[2]))
									{
										aux = puntos2[2];
						                aux2 = juga2[2];
						                puntos2[2] = puntos2[3];
						                juga2[2] = juga2[3];
						                puntos2[3] = aux;
						                juga2[3] = aux2;
									}
								}
								else
									if(juga2[3] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[2] == 2 && resultadoentreambos2[3]) || (juga2[2] == 3 && resultadoentreambos2[4]))
										{
											aux = puntos2[2];
							                aux2 = juga2[2];
							                puntos2[2] = puntos2[3];
							                juga2[2] = juga2[3];
							                puntos2[3] = aux;
							                juga2[3] = aux2;
										}
									else
										if(juga2[3] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[2] == 3 && resultadoentreambos2[5]))
											{
												aux = puntos2[2];
								                aux2 = juga2[2];
								                puntos2[2] = puntos2[3];
								                juga2[2] = juga2[3];
								                puntos2[3] = aux;
								                juga2[3] = aux2;
											}
										}
							}
							}
									}
								for (int n = 0;n<4;n++){
								over.setFontAndSize(bf, letra);
								over.setTextMatrix(CoordPDF[13], CoordPDF[juga[n]+4]);
								over.showText(""+(n+1));
								over.setTextMatrix(CoordPDF[13], CoordPDF[juga2[n]+8]);
								over.showText(""+(n+1));
								}
						over.endText();
						}
				stamper.close();
				reader.close();		
		
			
			} catch (IOException | DocumentException m) {
				m.printStackTrace();
			}  //Entrego el file del cuadro o donde se guarda dicho pdf
		break;
		}
/*-------------------------------------------------------------------------------------------------------
 --------------------------------------------------------------------------------------------------------* */
		case 9:{
			try {
				int zonanro;
				int puntos [] = new int [3];
				int setsganados[] = new int [3];
				int setsperdidos[] = new int [3];
				int gamesganados[] = new int [3];
				int gamesperdidos[] = new int [3];
				boolean resultadoentreambos [] = new boolean [3];
				int puntos2 [] = new int [3];
				int setsganados2[] = new int [3];
				int setsperdidos2[] = new int [3];
				int gamesganados2[] = new int [3];
				int gamesperdidos2[] = new int [3];
				boolean resultadoentreambos2 [] = new boolean [3];
				int puntos3 [] = new int [3];
				int setsganados3[] = new int [3];
				int setsperdidos3[] = new int [3];
				int gamesganados3[] = new int [3];
				int gamesperdidos3[] = new int [3];
				boolean resultadoentreambos3 [] = new boolean [3];
				for (int u=0;u<3;u++){
					puntos[u] = 0;
					setsganados[u] = 0;
					setsperdidos[u] = 0;
					gamesganados[u] = 0;
					resultadoentreambos[u] = false;
					gamesperdidos[u] = 0;
					puntos2[u] = 0;
					setsganados2[u] = 0;
					setsperdidos2[u] = 0;
					gamesganados2[u] = 0;
					resultadoentreambos2[u] = false;
					gamesperdidos2[u] = 0;
					puntos3[u] = 0;
					setsganados3[u] = 0;
					setsperdidos3[u] = 0;
					gamesganados3[u] = 0;
					resultadoentreambos3[u] = false;
					gamesperdidos3[u] = 0;
				}
				int posicion;
				int posicion2;
				int result;
				PdfReader reader = new PdfReader(pDFIN);
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
				//loop on pages (1-based)
				for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
						PdfContentByte over = stamper.getOverContent(i);
		      
						over.beginText();
						int res = 0;
							while (res<resultados.length){
								if (!resultados[res].equalsIgnoreCase("")){
									//Cargo posicion zona 1
									posicion = 0;
									posicion2 = 0;
									result = res;
									zonanro = res / 3;
									result = (result % 3)+1;
									if (result < 3)
										posicion = 0;
									if (result == 3)
										posicion = 1;
										//Cargo posicion 2 zona
									result = res;
									result = (result % 3)+1;
									if (result == 1)
										posicion2 = 1;
									if (result == 2 || result == 3)
										posicion2 = 2;
									encontre = false;
									if (!resultados[res].equalsIgnoreCase("BYE")){
										if(EvaluacionResultado(resultados[res])){
											if(zonanro == 0){
											puntos[posicion] +=2;
											resultadoentreambos[res] = true;
											if (!resultados[res].equalsIgnoreCase("WO 2"))
												puntos[posicion2]++;
											puntos[posicion]++;
											}
											else{
												if(zonanro == 1){puntos2[posicion] +=2;
												resultadoentreambos2[res%3] = true;
												if (!resultados[res].equalsIgnoreCase("WO 2"))
													puntos2[posicion2]++;
												puntos2[posicion]++;
											}
											else {puntos3[posicion]+=2;
												resultadoentreambos3[res%3] = true;
												if (!resultados[res].equalsIgnoreCase("WO 2"))
													puntos3[posicion2]++;
												puntos3[posicion]++;
												}
											}
											
										}
										else
										{
											if (zonanro == 0){
											puntos[posicion2]+=2;
											if (!resultados[res].equalsIgnoreCase("WO 1"))
												puntos[posicion]++;
											puntos[posicion2]++;
											}
											else{
												if(zonanro == 1){puntos2[posicion2] +=2;
												resultadoentreambos2[res%3] = true;
												if (!resultados[res].equalsIgnoreCase("WO 1"))
													puntos2[posicion]++;
												puntos2[posicion2]++;
											}
											else {puntos3[posicion2]+=2;
												resultadoentreambos3[res%3] = true;
												if (!resultados[res].equalsIgnoreCase("WO 1"))
													puntos3[posicion]++;
												puntos3[posicion2]++;
												}
											}
										}
										if (!resultados[res].contains("WO"))
									{
										if(zonanro == 0){
										setsganados[posicion] += Integer.parseInt(setsganados(resultados[res],1));
										setsperdidos[posicion] += Integer.parseInt(setsganados(resultados[res],2));
										setsganados[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
										setsperdidos[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
										}
										else{
											if (zonanro == 1){
											setsganados2[posicion] += Integer.parseInt(setsganados(resultados[res],1));
											setsperdidos2[posicion] += Integer.parseInt(setsganados(resultados[res],2));
											setsganados2[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
											setsperdidos2[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
											}
											else{
												setsganados3[posicion] += Integer.parseInt(setsganados(resultados[res],1));
												setsperdidos3[posicion] += Integer.parseInt(setsganados(resultados[res],2));
												setsganados3[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
												setsperdidos3[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
											}
										}
										}
									}
									over.setFontAndSize(bf, letra);
									over.setTextMatrix(CoordPDF[posicion2], CoordPDF[3+posicion+(3*zonanro)]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										if(resultados[res].contains("WO"))
											over.showText("WO.");
										else
											over.showText(resultados[res]);
									//Imprimo resultado en la otra parte de la zona
									over.setTextMatrix(CoordPDF[posicion], CoordPDF[3+posicion2+3*zonanro]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										over.showText(darvueltaresult2(resultados[res]));
								}
								res++;
								p++;	       	
				
							}
							for (int l = 0;l<3;l++){
							over.setFontAndSize(bf,letra);
							over.setTextMatrix(CoordPDF[12], CoordPDF[3+l]);
							over.showText(""+puntos[l]);
							over.setTextMatrix(CoordPDF[12], CoordPDF[6+l]);
							over.showText(""+puntos2[l]);
							over.setTextMatrix(CoordPDF[12], CoordPDF[9+l]);
							over.showText(""+puntos3[l]);}
							int [] juga = new int [3];
							juga[0] = 0;
							juga[1] = 1;
							juga[2] = 2;
							int [] juga2 = new int [3];
							juga2[0] = 0;
							juga2[1] = 1;
							juga2[2] = 2;
							int [] juga3 = new int [3];
							juga3[0] = 0;
							juga3[1] = 1;
							juga3[2] = 2;
							int aux;
							int aux2;
						    //Ordeno de manera estricta
							for (int n = 0; n < 3 ; n++) {
						        for (int g = n + 1; g < 3; g++) {
						            if (puntos[g] > puntos[n]) {
						                aux = puntos[n];
						                aux2 = juga[n];
						                puntos[n] = puntos[g];
						                juga[n] = juga[g];
						                puntos[g] = aux;
						                juga[g] = aux2;
						            }
						            if (puntos2[g] > puntos2[n]) {
						                aux = puntos2[n];
						                aux2 = juga2[n];
						                puntos2[n] = puntos2[g];
						                juga2[n] = juga2[g];
						                puntos2[g] = aux;
						                juga2[g] = aux2;
						            }
						            if (puntos3[g] > puntos3[n]) {
						                aux = puntos3[n];
						                aux2 = juga3[n];
						                puntos3[n] = puntos3[g];
						                juga3[n] = juga3[g];
						                puntos3[g] = aux;
						                juga3[g] = aux2;
						            }
						        }
						    }	
							//Verifico si no hay igualdades
							//Igualdades desde el primero
									if(puntos[0] == puntos[1]){//Hay  empate
										if (puntos[1] == puntos[2]){//Triple empate
											for (int n = 0; n < 3 ; n++) {
										        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
										            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
										            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
										            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
										            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
										                aux = puntos[n];
										                aux2 = juga[n];
										                puntos[n] = puntos[g];
										                juga[n] = juga[g];
										                puntos[g] = aux;
										                juga[g] = aux2;
										            }
										        }
										    }
										}	
								if(juga[0]>juga[1]){//0 tiene un orden menor
									if (juga[0] == 0){ //es el primer jugador, resultado entre 0 y 1
										if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) )
										{
											aux = puntos[0];
							                aux2 = juga[0];
							                puntos[0] = puntos[1];
							                juga[0] = juga[1];
							                puntos[1] = aux;
							                juga[1] = aux2;
										}
									}
									else
										if(juga[0] == 1){//es el 2do jugador, resultado 2
											if ((juga[1] == 2 && !resultadoentreambos[2]))
											{
												aux = puntos[0];
								                aux2 = juga[0];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
											}
										}
										
								}
								else{
									if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 1
										if ((juga[0] == 1 && !resultadoentreambos[0]) || (juga[0] == 2 && !resultadoentreambos[1]) )
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[0];
							                juga[1] = juga[0];
							                puntos[0] = aux;
							                juga[0] = aux2;
										}
									}
									else
										if(juga[1] == 1){//es el 2do jugador, resultado 2
											if ((juga[0] == 2 && !resultadoentreambos[2]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[0];
								                juga[1] = juga[0];
								                puntos[0] = aux;
								                juga[0] = aux2;
											}
										}
										
								}
									}
									else
										if (puntos[1] == puntos[2]){//Hay empate
											if(juga[1]>juga[2]){//0 tiene un orden menor
												if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 1
													if ((juga[2] == 1 && !resultadoentreambos[0]) || (juga[2] == 2 && !resultadoentreambos[1]) )
													{
														aux = puntos[1];
										                aux2 = juga[1];
										                puntos[1] = puntos[2];
										                juga[1] = juga[2];
										                puntos[2] = aux;
										                juga[2] = aux2;
													}
												}
												else
													if(juga[1] == 1){//es el 2do jugador, resultado 2
														if ((juga[2] == 2 && !resultadoentreambos[2]))
														{
															aux = puntos[1];
											                aux2 = juga[1];
											                puntos[1] = puntos[2];
											                juga[1] = juga[2];
											                puntos[2] = aux;
											                juga[2] = aux2;
														}
													}
													
											}
											else{
												if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 1
													if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) )
													{
														aux = puntos[2];
										                aux2 = juga[2];
										                puntos[2] = puntos[1];
										                juga[2] = juga[1];
										                puntos[1] = aux;
										                juga[1] = aux2;
													}
												}
												else
													if(juga[2] == 1){//es el 2do jugador, resultado 2
														if ((juga[1] == 2 && !resultadoentreambos[2]))
														{
															aux = puntos[2];
											                aux2 = juga[2];
											                puntos[2] = puntos[1];
											                juga[2] = juga[1];
											                puntos[1] = aux;
											                juga[1] = aux2;
														}
													}
													
											}	
								
										}
									//Verifico si no hay igualdades en zona 2
									//Igualdades desde el primero
											if(puntos2[0] == puntos2[1]){//Hay  empate
												if (puntos2[1] == puntos2[2]){//Triple empate
													for (int n = 0; n < 3 ; n++) {
												        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
												            if (((setsganados2[juga2[n]]+setsperdidos2[juga2[n]])== 0)
												            	|| (((setsganados2[juga2[g]]+setsperdidos2[juga2[g]])!= 0) && (setsganados2[juga2[g]]/(setsganados2[juga2[g]]+setsperdidos2[juga2[g]]) > (setsganados2[juga2[n]]/(setsganados2[juga2[n]]+setsperdidos2[juga2[n]]))))
												            	|| ((setsganados2[juga2[g]]/(setsganados2[juga2[g]]+setsperdidos2[juga2[g]])) == (setsganados2[juga2[n]]/(setsganados2[juga2[n]]+setsperdidos2[juga2[n]]))) && (((gamesganados2[juga2[n]]+gamesperdidos2[juga2[n]])== 0)
												            	|| (((gamesganados2[juga2[g]]+gamesperdidos2[juga2[g]])!= 0) && (gamesganados2[juga2[g]]/(gamesganados2[juga2[g]]+gamesperdidos2[juga2[g]]) > (gamesganados2[juga2[n]]/(gamesganados2[juga2[n]]+gamesperdidos2[juga2[n]])))))) {
												                aux = puntos2[n];
												                aux2 = juga2[n];
												                puntos2[n] = puntos2[g];
												                juga2[n] = juga2[g];
												                puntos2[g] = aux;
												                juga2[g] = aux2;
												            }
												        }
												    }
												}	
										if(juga2[0]>juga2[1]){//0 tiene un orden menor
											if (juga2[0] == 0){ //es el primer juga2dor, resultado entre 0 y 1
												if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) )
												{
													aux = puntos2[0];
									                aux2 = juga2[0];
									                puntos2[0] = puntos2[1];
									                juga2[0] = juga2[1];
									                puntos2[1] = aux;
									                juga2[1] = aux2;
												}
											}
											else
												if(juga2[0] == 1){//es el 2do juga2dor, resultado 2
													if ((juga2[1] == 2 && !resultadoentreambos2[2]))
													{
														aux = puntos2[0];
										                aux2 = juga2[0];
										                puntos2[0] = puntos2[1];
										                juga2[0] = juga2[1];
										                puntos2[1] = aux;
										                juga2[1] = aux2;
													}
												}
												
										}
										else{
											if (juga2[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
												if ((juga2[0] == 1 && !resultadoentreambos2[0]) || (juga2[0] == 2 && !resultadoentreambos2[1]) )
												{
													aux = puntos2[1];
									                aux2 = juga2[1];
									                puntos2[1] = puntos2[0];
									                juga2[1] = juga2[0];
									                puntos2[0] = aux;
									                juga2[0] = aux2;
												}
											}
											else
												if(juga2[1] == 1){//es el 2do juga2dor, resultado 2
													if ((juga2[0] == 2 && !resultadoentreambos2[2]))
													{
														aux = puntos2[1];
										                aux2 = juga2[1];
										                puntos2[1] = puntos2[0];
										                juga2[1] = juga2[0];
										                puntos2[0] = aux;
										                juga2[0] = aux2;
													}
												}
												
										}
											}
											else
												if (puntos2[1] == puntos2[2]){//Hay empate
													if(juga2[1]>juga2[2]){//0 tiene un orden menor
														if (juga2[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
															if ((juga2[2] == 1 && !resultadoentreambos2[0]) || (juga2[2] == 2 && !resultadoentreambos2[1]) )
															{
																aux = puntos2[1];
												                aux2 = juga2[1];
												                puntos2[1] = puntos2[2];
												                juga2[1] = juga2[2];
												                puntos2[2] = aux;
												                juga2[2] = aux2;
															}
														}
														else
															if(juga2[1] == 1){//es el 2do juga2dor, resultado 2
																if ((juga2[2] == 2 && !resultadoentreambos2[2]))
																{
																	aux = puntos2[1];
													                aux2 = juga2[1];
													                puntos2[1] = puntos2[2];
													                juga2[1] = juga2[2];
													                puntos2[2] = aux;
													                juga2[2] = aux2;
																}
															}
															
													}
													else{
														if (juga2[2] == 0){ //es el primer juga2dor, resultado entre 0 y 1
															if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) )
															{
																aux = puntos2[2];
												                aux2 = juga2[2];
												                puntos2[2] = puntos2[1];
												                juga2[2] = juga2[1];
												                puntos2[1] = aux;
												                juga2[1] = aux2;
															}
														}
														else
															if(juga2[2] == 1){//es el 2do juga2dor, resultado 2
																if ((juga2[1] == 2 && !resultadoentreambos2[2]))
																{
																	aux = puntos2[2];
													                aux2 = juga2[2];
													                puntos2[2] = puntos2[1];
													                juga2[2] = juga2[1];
													                puntos2[1] = aux;
													                juga2[1] = aux2;
																}
															}
															
													}	
										
												}
											//Verifico si no hay igualdades en zona 2
											//Igualdades desde el primero
													if(puntos3[0] == puntos3[1]){//Hay  empate
														if (puntos3[1] == puntos3[2]){//Triple empate
															for (int n = 0; n < 3 ; n++) {
														        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
														            if (((setsganados2[juga3[n]]+setsperdidos2[juga3[n]])== 0)
														            	|| (((setsganados2[juga3[g]]+setsperdidos2[juga3[g]])!= 0) && (setsganados2[juga3[g]]/(setsganados2[juga3[g]]+setsperdidos2[juga3[g]]) > (setsganados2[juga3[n]]/(setsganados2[juga3[n]]+setsperdidos2[juga3[n]]))))
														            	|| ((setsganados2[juga3[g]]/(setsganados2[juga3[g]]+setsperdidos2[juga3[g]])) == (setsganados2[juga3[n]]/(setsganados2[juga3[n]]+setsperdidos2[juga3[n]]))) && (((gamesganados2[juga3[n]]+gamesperdidos2[juga3[n]])== 0)
														            	|| (((gamesganados2[juga3[g]]+gamesperdidos2[juga3[g]])!= 0) && (gamesganados2[juga3[g]]/(gamesganados2[juga3[g]]+gamesperdidos2[juga3[g]]) > (gamesganados2[juga3[n]]/(gamesganados2[juga3[n]]+gamesperdidos2[juga3[n]])))))) {
														                aux = puntos3[n];
														                aux2 = juga3[n];
														                puntos3[n] = puntos3[g];
														                juga3[n] = juga3[g];
														                puntos3[g] = aux;
														                juga3[g] = aux2;
														            }
														        }
														    }
														}	
												if(juga3[0]>juga3[1]){//0 tiene un orden menor
													if (juga3[0] == 0){ //es el primer juga2dor, resultado entre 0 y 1
														if ((juga3[1] == 1 && !resultadoentreambos3[0]) || (juga3[1] == 2 && !resultadoentreambos3[1]) )
														{
															aux = puntos3[0];
											                aux2 = juga3[0];
											                puntos3[0] = puntos3[1];
											                juga3[0] = juga3[1];
											                puntos3[1] = aux;
											                juga3[1] = aux2;
														}
													}
													else
														if(juga3[0] == 1){//es el 2do juga2dor, resultado 2
															if ((juga3[1] == 2 && !resultadoentreambos3[2]))
															{
																aux = puntos3[0];
												                aux2 = juga3[0];
												                puntos3[0] = puntos3[1];
												                juga3[0] = juga3[1];
												                puntos3[1] = aux;
												                juga3[1] = aux2;
															}
														}
														
												}
												else{
													if (juga3[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
														if ((juga3[0] == 1 && !resultadoentreambos3[0]) || (juga3[0] == 2 && !resultadoentreambos3[1]) )
														{
															aux = puntos3[1];
											                aux2 = juga3[1];
											                puntos3[1] = puntos3[0];
											                juga3[1] = juga3[0];
											                puntos3[0] = aux;
											                juga3[0] = aux2;
														}
													}
													else
														if(juga3[1] == 1){//es el 2do juga2dor, resultado 2
															if ((juga3[0] == 2 && !resultadoentreambos3[2]))
															{
																aux = puntos3[1];
												                aux2 = juga3[1];
												                puntos3[1] = puntos3[0];
												                juga3[1] = juga3[0];
												                puntos3[0] = aux;
												                juga3[0] = aux2;
															}
														}
														
												}
													}
													else
														if (puntos3[1] == puntos3[2]){//Hay empate
															if(juga3[1]>juga3[2]){//0 tiene un orden menor
																if (juga3[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																	if ((juga3[2] == 1 && !resultadoentreambos3[0]) || (juga3[2] == 2 && !resultadoentreambos3[1]) )
																	{
																		aux = puntos3[1];
														                aux2 = juga3[1];
														                puntos3[1] = puntos3[2];
														                juga3[1] = juga3[2];
														                puntos3[2] = aux;
														                juga3[2] = aux2;
																	}
																}
																else
																	if(juga3[1] == 1){//es el 2do juga2dor, resultado 2
																		if ((juga3[2] == 2 && !resultadoentreambos3[2]))
																		{
																			aux = puntos3[1];
															                aux2 = juga3[1];
															                puntos3[1] = puntos3[2];
															                juga3[1] = juga3[2];
															                puntos3[2] = aux;
															                juga3[2] = aux2;
																		}
																	}
																	
															}
															else{
																if (juga3[2] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																	if ((juga3[1] == 1 && !resultadoentreambos3[0]) || (juga3[1] == 2 && !resultadoentreambos3[1]) )
																	{
																		aux = puntos3[2];
														                aux2 = juga3[2];
														                puntos3[2] = puntos3[1];
														                juga3[2] = juga3[1];
														                puntos3[1] = aux;
														                juga3[1] = aux2;
																	}
																}
																else
																	if(juga3[2] == 1){//es el 2do juga2dor, resultado 2
																		if ((juga3[1] == 2 && !resultadoentreambos3[2]))
																		{
																			aux = puntos3[2];
															                aux2 = juga3[2];
															                puntos3[2] = puntos3[1];
															                juga3[2] = juga3[1];
															                puntos3[1] = aux;
															                juga3[1] = aux2;
																		}
																	}
																	
															}	
												
														}
							for (int n = 0;n<3;n++){
								over.setFontAndSize(bf, letra);
								over.setTextMatrix(CoordPDF[13], CoordPDF[juga[n]+3]);
								over.showText(""+(n+1));
								over.setTextMatrix(CoordPDF[13], CoordPDF[juga2[n]+6]);
								over.showText(""+(n+1));
								over.setTextMatrix(CoordPDF[13], CoordPDF[juga3[n]+9]);
								over.showText(""+(n+1));
								}
						over.endText();
						}
				stamper.close();
				reader.close();		
		
			
			} catch (IOException | DocumentException m) {
				m.printStackTrace();
			}  //Entrego el file del cuadro o donde se guarda dicho pdf
		break;
		}
		/*-------------------------------------------------------------------------------------------------------
		 --------------------------------------------------------------------------------------------------------* */
		case 12:{ //3 zonas de 4 (10 y 11 jugadores) y 4 zonas de 3 (12 jugadores)
			if(jugadores == 10 || jugadores == 11){//3 zonas de 4
			try {
				int zonanro;
				int puntos [] = new int [4];
				int setsganados[] = new int [4];
				int setsperdidos[] = new int [4];
				int gamesganados[] = new int [4];
				int gamesperdidos[] = new int [4];
				boolean resultadoentreambos [] = new boolean [6];
				int puntos2 [] = new int [4];
				int setsganados2[] = new int [4];
				int setsperdidos2[] = new int [4];
				int gamesganados2[] = new int [4];
				int gamesperdidos2[] = new int [4];
				boolean resultadoentreambos2 [] = new boolean [6];
				int puntos3 [] = new int [4];
				int setsganados3[] = new int [4];
				int setsperdidos3[] = new int [4];
				int gamesganados3[] = new int [4];
				int gamesperdidos3[] = new int [4];
				boolean resultadoentreambos3 [] = new boolean [6];
				for (int u=0;u<4;u++){
					puntos[u] = 0;
					setsganados[u] = 0;
					setsperdidos[u] = 0;
					gamesganados[u] = 0;
					gamesperdidos[u] = 0;
					puntos2[u] = 0;
					setsganados2[u] = 0;
					setsperdidos2[u] = 0;
					gamesganados2[u] = 0;
					gamesperdidos2[u] = 0;
					puntos3[u] = 0;
					setsganados3[u] = 0;
					setsperdidos3[u] = 0;
					gamesganados3[u] = 0;
					gamesperdidos3[u] = 0;
				}
				for (int u=0;u<6;u++){
				resultadoentreambos[u] = false;
				resultadoentreambos2[u] = false;
				resultadoentreambos3[u] = false;
				}
				int posicion;
				int posicion2;
				int result;
				PdfReader reader = new PdfReader(pDFIN);
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
				BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
				//loop on pages (1-based)
				for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
						PdfContentByte over = stamper.getOverContent(i);
		      
						over.beginText();
						int res = 0;
							while (res<resultados.length){
								if (!resultados[res].equalsIgnoreCase("")){
									//Cargo posicion zona 1
									posicion = 0;
									posicion2 = 0;
									result = res;
									zonanro = res / 6;
									result = (result % 6)+1;
									if (result < 4)
										posicion = 0;
									if (result >= 4 && result<6)
										posicion = 1;
									if (result == 6)
										posicion = 2;
									//Cargo posicion 2 zona
									result = res;
									result = (result % 6)+1;
									if (result == 1)
										posicion2 = 1;
									if (result == 2 || result == 4)
										posicion2 = 2;
									if (result == 3 || result == 5 || result == 6)
										posicion2 = 3;
									encontre = false;
									if (!resultados[res].equalsIgnoreCase("BYE")){
										if(EvaluacionResultado(resultados[res])){
											if(zonanro == 0){
											puntos[posicion] +=2;
											resultadoentreambos[res] = true;
											if (!resultados[res].equalsIgnoreCase("WO 2"))
												puntos[posicion2]++;
											puntos[posicion]++;
											}
											else{
												if(zonanro == 1){
												puntos2[posicion] +=2;
												resultadoentreambos2[res%6] = true;
												if (!resultados[res].equalsIgnoreCase("WO 2"))
													puntos2[posicion2]++;
												puntos2[posicion]++;
											}
												else{
													puntos3[posicion] +=2;
													resultadoentreambos3[res%6] = true;
													if (!resultados[res].equalsIgnoreCase("WO 2"))
														puntos3[posicion2]++;
													puntos3[posicion]++;
												}
													}
										}
										else
										{
											if (zonanro == 0){
											puntos[posicion2]+=2;
											if (!resultados[res].equalsIgnoreCase("WO 1"))
												puntos[posicion]++;
											puntos[posicion2]++;
											}
											else{
												if(zonanro == 1){
												puntos2[posicion2]+=2;
												if (!resultados[res].equalsIgnoreCase("WO 1"))
													puntos2[posicion]++;
												puntos2[posicion2]++;
												}
												else{
														puntos3[posicion2]+=2;
														if (!resultados[res].equalsIgnoreCase("WO 1"))
															puntos3[posicion]++;
														puntos3[posicion2]++;
														
												}
											}
										}
										if (!resultados[res].contains("WO"))
									{
										if(zonanro == 0){
										setsganados[posicion] += Integer.parseInt(setsganados(resultados[res],1));
										setsperdidos[posicion] += Integer.parseInt(setsganados(resultados[res],2));
										setsganados[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
										setsperdidos[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
										}
										else{
											if(zonanro == 1){
											setsganados2[posicion] += Integer.parseInt(setsganados(resultados[res],1));
											setsperdidos2[posicion] += Integer.parseInt(setsganados(resultados[res],2));
											setsganados2[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
											setsperdidos2[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
											}
											else{
												setsganados3[posicion] += Integer.parseInt(setsganados(resultados[res],1));
												setsperdidos3[posicion] += Integer.parseInt(setsganados(resultados[res],2));
												setsganados3[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
												setsperdidos3[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
													
											}
										}
										}
									}
									over.setFontAndSize(bf, letra);
									over.setTextMatrix(CoordPDF[posicion2], CoordPDF[4+posicion+(4*zonanro)]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										if(resultados[res].contains("WO"))
											over.showText("WO.");
										else
											over.showText(resultados[res]);
									//Imprimo resultado en la otra parte de la zona
									over.setTextMatrix(CoordPDF[posicion], CoordPDF[4+posicion2+4*zonanro]); //Resultado
									if (resultados[res].equalsIgnoreCase("BYE"))
										over.showText("---------");
									else
										over.showText(darvueltaresult2(resultados[res]));
								}
								res++;
								p++;	       	
				
							}
							for (int l = 0;l<4;l++){
							over.setFontAndSize(bf,letra);
							over.setTextMatrix(CoordPDF[16], CoordPDF[4+l]);
							over.showText(""+puntos[l]);
							over.setTextMatrix(CoordPDF[16], CoordPDF[8+l]);
							over.showText(""+puntos2[l]);
							over.setTextMatrix(CoordPDF[16], CoordPDF[12+l]);
							over.showText(""+puntos3[l]);}
							int [] juga = new int [4];
							juga[0] = 0;
							juga[1] = 1;
							juga[2] = 2;
							juga[3] = 3;
							int [] juga2 = new int [4];
							juga2[0] = 0;
							juga2[1] = 1;
							juga2[2] = 2;
							juga2[3] = 3;
							int [] juga3 = new int [4];
							juga3[0] = 0;
							juga3[1] = 1;
							juga3[2] = 2;
							juga3[3] = 3;
							int aux;
							int aux2;
						    //Ordeno de manera estricta
							for (int n = 0; n < 4 ; n++) {
						        for (int g = n + 1; g < 4; g++) {
						            if (puntos[g] > puntos[n]) {
						                aux = puntos[n];
						                aux2 = juga[n];
						                puntos[n] = puntos[g];
						                juga[n] = juga[g];
						                puntos[g] = aux;
						                juga[g] = aux2;
						            }
						            if (puntos2[g] > puntos2[n]) {
						                aux = puntos2[n];
						                aux2 = juga2[n];
						                puntos2[n] = puntos2[g];
						                juga2[n] = juga2[g];
						                puntos2[g] = aux;
						                juga2[g] = aux2;
						            }
						            if (puntos3[g] > puntos3[n]) {
						                aux = puntos3[n];
						                aux2 = juga3[n];
						                puntos3[n] = puntos3[g];
						                juga3[n] = juga3[g];
						                puntos3[g] = aux;
						                juga3[g] = aux2;
						            }
						        }
						    }	
							if (puntos[0] == puntos[1]){//Hay empate
								if(puntos[1] == puntos[2]){//Hay triple empate
									if (puntos[2] == puntos[3]){//Todos empatados
										for (int n = 0; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
									            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
									            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
									            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
									                aux = puntos[n];
									                aux2 = juga[n];
									                puntos[n] = puntos[g];
									                juga[n] = juga[g];
									                puntos[g] = aux;
									                juga[g] = aux2;
									            }
									        }
									    }
									}
									for (int n = 0; n < 3 ; n++) {
								        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
								            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
									            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
									            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
									            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
								                aux = puntos[n];
								                aux2 = juga[n];
								                puntos[n] = puntos[g];
								                juga[n] = juga[g];
								                puntos[g] = aux;
								                juga[g] = aux2;
								            }
								}
							}
						}
								
							if(juga[0]>juga[1]){//0 tiene un orden menor
								if (juga[0] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) || (juga[1] == 3 && !resultadoentreambos[2]))
									{
										aux = puntos[0];
						                aux2 = juga[0];
						                puntos[0] = puntos[1];
						                juga[0] = juga[1];
						                puntos[1] = aux;
						                juga[1] = aux2;
									}
								}
								else
									if(juga[0] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[1] == 2 && !resultadoentreambos[3]) || (juga[1] == 3 && !resultadoentreambos[4]))
										{
											aux = puntos[0];
							                aux2 = juga[0];
							                puntos[0] = puntos[1];
							                juga[0] = juga[1];
							                puntos[1] = aux;
							                juga[1] = aux2;
										}
									}
									else
										if(juga[0] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[1] == 3 && !resultadoentreambos[5]))
											{
												aux = puntos[0];
								                aux2 = juga[0];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
											}
										}
										
							}
							else{
								if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[0] == 1 && resultadoentreambos[0]) || (juga[0] == 2 && resultadoentreambos[1]) || (juga[0] == 3 && resultadoentreambos[2]))
									{
										aux = puntos[0];
						                aux2 = juga[1];
						                puntos[0] = puntos[1];
						                juga[0] = juga[1];
						                puntos[1] = aux;
						                juga[1] = aux2;
									}
								}
								else
									if(juga[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[0] == 2 && resultadoentreambos[3]) || (juga[0] == 3 && resultadoentreambos[4]))
										{
											aux = puntos[0];
											aux2 = juga[1];
											puntos[0] = puntos[1];
											juga[0] = juga[1];
											puntos[1] = aux;
											juga[1] = aux2;}
									}
									else
										if(juga[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[0] == 3 && resultadoentreambos[5]))
											{
												aux = puntos[0];
								                aux2 = juga[1];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
								                }
										}
										
							}
}

							else
								if(puntos[1] == puntos[2]){//Hay  empate
									if (puntos[2] == puntos[3]){//Triple empate
										for (int n = 1; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
									            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
									            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
									            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
									                aux = puntos[n];
									                aux2 = juga[n];
									                puntos[n] = puntos[g];
									                juga[n] = juga[g];
									                puntos[g] = aux;
									                juga[g] = aux2;
									            }
									        }
									    }
									}	
							if(juga[1]>juga[2]){//0 tiene un orden menor
								if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[2] == 1 && !resultadoentreambos[0]) || (juga[2] == 2 && !resultadoentreambos[1]) || (juga[2] == 3 && !resultadoentreambos[2]))
									{
										aux = puntos[1];
						                aux2 = juga[1];
						                puntos[1] = puntos[2];
						                juga[1] = juga[2];
						                puntos[2] = aux;
						                juga[2] = aux2;
									}
								}
								else
									if(juga[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[2] == 2 && !resultadoentreambos[3]) || (juga[2] == 3 && !resultadoentreambos[4]))
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[2];
							                juga[1] = juga[2];
							                puntos[2] = aux;
							                juga[2] = aux2;
										}
									}
									else
										if(juga[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[2] == 3 && !resultadoentreambos[5]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[2];
								                juga[1] = juga[2];
								                puntos[2] = aux;
								                juga[2] = aux2;
											}
										}
										
							}
							else{
								if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[1] == 1 && resultadoentreambos[0]) || (juga[1] == 2 && resultadoentreambos[1]) || (juga[1] == 3 && resultadoentreambos[2]))
									{
										aux = puntos[1];
						                aux2 = juga[1];
						                puntos[1] = puntos[2];
						                juga[1] = juga[2];
						                puntos[2] = aux;
						                juga[2] = aux2;
									}
								}
								else
									if(juga[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[1] == 2 && resultadoentreambos[3]) || (juga[1] == 3 && resultadoentreambos[4]))
										{
											aux = puntos[1];
							                aux2 = juga[1];
							                puntos[1] = puntos[2];
							                juga[1] = juga[2];
							                puntos[2] = aux;
							                juga[2] = aux2;
										}
									else
										if(juga[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[1] == 3 && resultadoentreambos[5]))
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[2];
								                juga[1] = juga[2];
								                puntos[2] = aux;
								                juga[2] = aux2;
											}
										}
										
							}
							}
							}
								else
									if (puntos[2] == puntos[3]){//Hay empate
										
							if(juga[2]>juga[3]){//0 tiene un orden menor
								if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[3] == 1 && !resultadoentreambos[0]) || (juga[3] == 2 && !resultadoentreambos[1]) || (juga[3] == 3 && !resultadoentreambos[2]))
									{
										aux = puntos[2];
						                aux2 = juga[2];
						                puntos[2] = puntos[3];
						                juga[2] = juga[3];
						                puntos[3] = aux;
						                juga[3] = aux2;
									}
								}
								else
									if(juga[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[3] == 2 && !resultadoentreambos[3]) || (juga[3] == 3 && !resultadoentreambos[4]))
										{
											aux = puntos[2];
							                aux2 = juga[2];
							                puntos[2] = puntos[3];
							                juga[2] = juga[3];
							                puntos[3] = aux;
							                juga[3] = aux2;
										}
									}
									else
										if(juga[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[3] == 3 && !resultadoentreambos[5]))
											{
												aux = puntos[2];
								                aux2 = juga[2];
								                puntos[2] = puntos[3];
								                juga[2] = juga[3];
								                puntos[3] = aux;
								                juga[3] = aux2;
											}
										}
							}
							else{
								if (juga[3] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga[2] == 1 && resultadoentreambos[0]) || (juga[2] == 2 && resultadoentreambos[1]) || (juga[2] == 3 && resultadoentreambos[2]))
									{
										aux = puntos[2];
						                aux2 = juga[2];
						                puntos[2] = puntos[3];
						                juga[2] = juga[3];
						                puntos[3] = aux;
						                juga[3] = aux2;
									}
								}
								else
									if(juga[3] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga[2] == 2 && resultadoentreambos[3]) || (juga[2] == 3 && resultadoentreambos[4]))
										{
											aux = puntos[2];
							                aux2 = juga[2];
							                puntos[2] = puntos[3];
							                juga[2] = juga[3];
							                puntos[3] = aux;
							                juga[3] = aux2;
										}
									else
										if(juga[3] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga[2] == 3 && resultadoentreambos[5]))
											{
												aux = puntos[2];
								                aux2 = juga[2];
								                puntos[2] = puntos[3];
								                juga[2] = juga[3];
								                puntos[3] = aux;
								                juga[3] = aux2;
											}
										}
							}
							}
									}
							if (puntos2[0] == puntos2[1]){//Hay empate
								if(puntos2[1] == puntos2[2]){//Hay triple empate
									if (puntos2[2] == puntos2[3]){//Todos empatados
										for (int n = 0; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
									            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
									            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
									            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
									                aux = puntos2[n];
									                aux2 = juga2[n];
									                puntos2[n] = puntos2[g];
									                juga2[n] = juga2[g];
									                puntos2[g] = aux;
									                juga2[g] = aux2;
									            }
									        }
									    }
									}
									for (int n = 0; n < 3 ; n++) {
								        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
								            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
									            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
									            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
									            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
								                aux = puntos2[n];
								                aux2 = juga2[n];
								                puntos2[n] = puntos2[g];
								                juga2[n] = juga2[g];
								                puntos2[g] = aux;
								                juga2[g] = aux2;
								            }
								}
							}
						}
								
							if(juga2[0]>juga2[1]){//0 tiene un orden menor
								if (juga2[0] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) || (juga2[1] == 3 && !resultadoentreambos2[2]))
									{
										aux = puntos2[0];
						                aux2 = juga2[0];
						                puntos2[0] = puntos2[1];
						                juga2[0] = juga2[1];
						                puntos2[1] = aux;
						                juga2[1] = aux2;
									}
								}
								else
									if(juga2[0] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[1] == 2 && !resultadoentreambos2[3]) || (juga2[1] == 3 && !resultadoentreambos2[4]))
										{
											aux = puntos2[0];
							                aux2 = juga2[0];
							                puntos2[0] = puntos2[1];
							                juga2[0] = juga2[1];
							                puntos2[1] = aux;
							                juga2[1] = aux2;
										}
									}
									else
										if(juga2[0] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[1] == 3 && !resultadoentreambos2[5]))
											{
												aux = puntos2[0];
								                aux2 = juga2[0];
								                puntos2[0] = puntos2[1];
								                juga2[0] = juga2[1];
								                puntos2[1] = aux;
								                juga2[1] = aux2;
											}
										}
										
							}
							else{
								if (juga2[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[0] == 1 && resultadoentreambos2[0]) || (juga2[0] == 2 && resultadoentreambos2[1]) || (juga2[0] == 3 && resultadoentreambos2[2]))
									{
										aux = puntos2[0];
						                aux2 = juga2[1];
						                puntos2[0] = puntos2[1];
						                juga2[0] = juga2[1];
						                puntos2[1] = aux;
						                juga2[1] = aux2;
									}
								}
								else
									if(juga2[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[0] == 2 && resultadoentreambos2[3]) || (juga2[0] == 3 && resultadoentreambos2[4]))
										{
											aux = puntos2[0];
											aux2 = juga2[1];
											puntos2[0] = puntos2[1];
											juga2[0] = juga2[1];
											puntos2[1] = aux;
											juga2[1] = aux2;}
									}
									else
										if(juga2[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[0] == 3 && resultadoentreambos2[5]))
											{
												aux = puntos2[0];
								                aux2 = juga2[1];
								                puntos2[0] = puntos2[1];
								                juga2[0] = juga2[1];
								                puntos2[1] = aux;
								                juga2[1] = aux2;
								                }
										}
										
							}
}

							else
								if(puntos2[1] == puntos2[2]){//Hay  empate
									if (puntos2[2] == puntos2[3]){//Triple empate
										for (int n = 1; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga2[n]]+setsperdidos[juga2[n]])== 0)
									            	|| (((setsganados[juga2[g]]+setsperdidos[juga2[g]])!= 0) && (setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]]) > (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))))
									            	|| ((setsganados[juga2[g]]/(setsganados[juga2[g]]+setsperdidos[juga2[g]])) == (setsganados[juga2[n]]/(setsganados[juga2[n]]+setsperdidos[juga2[n]]))) && (((gamesganados[juga2[n]]+gamesperdidos[juga2[n]])== 0)
									            	|| (((gamesganados[juga2[g]]+gamesperdidos[juga2[g]])!= 0) && (gamesganados[juga2[g]]/(gamesganados[juga2[g]]+gamesperdidos[juga2[g]]) > (gamesganados[juga2[n]]/(gamesganados[juga2[n]]+gamesperdidos[juga2[n]])))))) {
									                aux = puntos2[n];
									                aux2 = juga2[n];
									                puntos2[n] = puntos2[g];
									                juga2[n] = juga2[g];
									                puntos2[g] = aux;
									                juga2[g] = aux2;
									            }
									        }
									    }
									}	
							if(juga2[1]>juga2[2]){//0 tiene un orden menor
								if (juga2[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[2] == 1 && !resultadoentreambos2[0]) || (juga2[2] == 2 && !resultadoentreambos2[1]) || (juga2[2] == 3 && !resultadoentreambos2[2]))
									{
										aux = puntos2[1];
						                aux2 = juga2[1];
						                puntos2[1] = puntos2[2];
						                juga2[1] = juga2[2];
						                puntos2[2] = aux;
						                juga2[2] = aux2;
									}
								}
								else
									if(juga2[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[2] == 2 && !resultadoentreambos2[3]) || (juga2[2] == 3 && !resultadoentreambos2[4]))
										{
											aux = puntos2[1];
							                aux2 = juga2[1];
							                puntos2[1] = puntos2[2];
							                juga2[1] = juga2[2];
							                puntos2[2] = aux;
							                juga2[2] = aux2;
										}
									}
									else
										if(juga2[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[2] == 3 && !resultadoentreambos2[5]))
											{
												aux = puntos2[1];
								                aux2 = juga2[1];
								                puntos2[1] = puntos2[2];
								                juga2[1] = juga2[2];
								                puntos2[2] = aux;
								                juga2[2] = aux2;
											}
										}
										
							}
							else{
								if (juga2[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[1] == 1 && resultadoentreambos2[0]) || (juga2[1] == 2 && resultadoentreambos2[1]) || (juga2[1] == 3 && resultadoentreambos2[2]))
									{
										aux = puntos2[1];
						                aux2 = juga2[1];
						                puntos2[1] = puntos2[2];
						                juga2[1] = juga2[2];
						                puntos2[2] = aux;
						                juga2[2] = aux2;
									}
								}
								else
									if(juga2[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[1] == 2 && resultadoentreambos2[3]) || (juga2[1] == 3 && resultadoentreambos2[4]))
										{
											aux = puntos2[1];
							                aux2 = juga2[1];
							                puntos2[1] = puntos2[2];
							                juga2[1] = juga2[2];
							                puntos2[2] = aux;
							                juga2[2] = aux2;
										}
									else
										if(juga2[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[1] == 3 && resultadoentreambos2[5]))
											{
												aux = puntos2[1];
								                aux2 = juga2[1];
								                puntos2[1] = puntos2[2];
								                juga2[1] = juga2[2];
								                puntos2[2] = aux;
								                juga2[2] = aux2;
											}
										}
										
							}
							}
							}
								else
									if (puntos2[2] == puntos2[3]){//Hay empate
										
							if(juga2[2]>juga2[3]){//0 tiene un orden menor
								if (juga2[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[3] == 1 && !resultadoentreambos2[0]) || (juga2[3] == 2 && !resultadoentreambos2[1]) || (juga2[3] == 3 && !resultadoentreambos2[2]))
									{
										aux = puntos2[2];
						                aux2 = juga2[2];
						                puntos2[2] = puntos2[3];
						                juga2[2] = juga2[3];
						                puntos2[3] = aux;
						                juga2[3] = aux2;
									}
								}
								else
									if(juga2[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[3] == 2 && !resultadoentreambos2[3]) || (juga2[3] == 3 && !resultadoentreambos2[4]))
										{
											aux = puntos2[2];
							                aux2 = juga2[2];
							                puntos2[2] = puntos2[3];
							                juga2[2] = juga2[3];
							                puntos2[3] = aux;
							                juga2[3] = aux2;
										}
									}
									else
										if(juga2[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[3] == 3 && !resultadoentreambos2[5]))
											{
												aux = puntos2[2];
								                aux2 = juga2[2];
								                puntos2[2] = puntos2[3];
								                juga2[2] = juga2[3];
								                puntos2[3] = aux;
								                juga2[3] = aux2;
											}
										}
							}
							else{
								if (juga2[3] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga2[2] == 1 && resultadoentreambos2[0]) || (juga2[2] == 2 && resultadoentreambos2[1]) || (juga2[2] == 3 && resultadoentreambos2[2]))
									{
										aux = puntos2[2];
						                aux2 = juga2[2];
						                puntos2[2] = puntos2[3];
						                juga2[2] = juga2[3];
						                puntos2[3] = aux;
						                juga2[3] = aux2;
									}
								}
								else
									if(juga2[3] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga2[2] == 2 && resultadoentreambos2[3]) || (juga2[2] == 3 && resultadoentreambos2[4]))
										{
											aux = puntos2[2];
							                aux2 = juga2[2];
							                puntos2[2] = puntos2[3];
							                juga2[2] = juga2[3];
							                puntos2[3] = aux;
							                juga2[3] = aux2;
										}
									else
										if(juga2[3] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga2[2] == 3 && resultadoentreambos2[5]))
											{
												aux = puntos2[2];
								                aux2 = juga2[2];
								                puntos2[2] = puntos2[3];
								                juga2[2] = juga2[3];
								                puntos2[3] = aux;
								                juga2[3] = aux2;
											}
										}
							}
							}
									}
							if (puntos3[0] == puntos3[1]){//Hay empate
								if(puntos3[1] == puntos3[2]){//Hay triple empate
									if (puntos3[2] == puntos3[3]){//Todos empatados
										for (int n = 0; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga3[n]]+setsperdidos[juga3[n]])== 0)
									            	|| (((setsganados[juga3[g]]+setsperdidos[juga3[g]])!= 0) && (setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]]) > (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))))
									            	|| ((setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]])) == (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))) && (((gamesganados[juga3[n]]+gamesperdidos[juga3[n]])== 0)
									            	|| (((gamesganados[juga3[g]]+gamesperdidos[juga3[g]])!= 0) && (gamesganados[juga3[g]]/(gamesganados[juga3[g]]+gamesperdidos[juga3[g]]) > (gamesganados[juga3[n]]/(gamesganados[juga3[n]]+gamesperdidos[juga3[n]])))))) {
									                aux = puntos3[n];
									                aux2 = juga3[n];
									                puntos3[n] = puntos3[g];
									                juga3[n] = juga3[g];
									                puntos3[g] = aux;
									                juga3[g] = aux2;
									            }
									        }
									    }
									}
									for (int n = 0; n < 3 ; n++) {
								        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
								            if (((setsganados[juga3[n]]+setsperdidos[juga3[n]])== 0)
									            	|| (((setsganados[juga3[g]]+setsperdidos[juga3[g]])!= 0) && (setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]]) > (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))))
									            	|| ((setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]])) == (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))) && (((gamesganados[juga3[n]]+gamesperdidos[juga3[n]])== 0)
									            	|| (((gamesganados[juga3[g]]+gamesperdidos[juga3[g]])!= 0) && (gamesganados[juga3[g]]/(gamesganados[juga3[g]]+gamesperdidos[juga3[g]]) > (gamesganados[juga3[n]]/(gamesganados[juga3[n]]+gamesperdidos[juga3[n]])))))) {
								                aux = puntos3[n];
								                aux2 = juga3[n];
								                puntos3[n] = puntos3[g];
								                juga3[n] = juga3[g];
								                puntos3[g] = aux;
								                juga3[g] = aux2;
								            }
								}
							}
						}
								
							if(juga3[0]>juga3[1]){//0 tiene un orden menor
								if (juga3[0] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga3[1] == 1 && !resultadoentreambos3[0]) || (juga3[1] == 2 && !resultadoentreambos3[1]) || (juga3[1] == 3 && !resultadoentreambos3[2]))
									{
										aux = puntos3[0];
						                aux2 = juga3[0];
						                puntos3[0] = puntos3[1];
						                juga3[0] = juga3[1];
						                puntos3[1] = aux;
						                juga3[1] = aux2;
									}
								}
								else
									if(juga3[0] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga3[1] == 2 && !resultadoentreambos3[3]) || (juga3[1] == 3 && !resultadoentreambos3[4]))
										{
											aux = puntos3[0];
							                aux2 = juga3[0];
							                puntos3[0] = puntos3[1];
							                juga3[0] = juga3[1];
							                puntos3[1] = aux;
							                juga3[1] = aux2;
										}
									}
									else
										if(juga3[0] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[1] == 3 && !resultadoentreambos3[5]))
											{
												aux = puntos3[0];
								                aux2 = juga3[0];
								                puntos3[0] = puntos3[1];
								                juga3[0] = juga3[1];
								                puntos3[1] = aux;
								                juga3[1] = aux2;
											}
										}
										
							}
							else{
								if (juga3[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga3[0] == 1 && resultadoentreambos3[0]) || (juga3[0] == 2 && resultadoentreambos3[1]) || (juga3[0] == 3 && resultadoentreambos3[2]))
									{
										aux = puntos3[0];
						                aux2 = juga3[1];
						                puntos3[0] = puntos3[1];
						                juga3[0] = juga3[1];
						                puntos3[1] = aux;
						                juga3[1] = aux2;
									}
								}
								else
									if(juga3[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga3[0] == 2 && resultadoentreambos3[3]) || (juga3[0] == 3 && resultadoentreambos3[4]))
										{
											aux = puntos3[0];
											aux2 = juga3[1];
											puntos3[0] = puntos3[1];
											juga3[0] = juga3[1];
											puntos3[1] = aux;
											juga3[1] = aux2;}
									}
									else
										if(juga3[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[0] == 3 && resultadoentreambos3[5]))
											{
												aux = puntos3[0];
								                aux2 = juga3[1];
								                puntos3[0] = puntos3[1];
								                juga3[0] = juga3[1];
								                puntos3[1] = aux;
								                juga3[1] = aux2;
								                }
										}
										
							}
}

							else
								if(puntos3[1] == puntos3[2]){//Hay  empate
									if (puntos3[2] == puntos3[3]){//Triple empate
										for (int n = 1; n < 4 ; n++) {
									        for (int g = n + 1; g < 4; g++) {//Verifico por porcentaje de sets
									            if (((setsganados[juga3[n]]+setsperdidos[juga3[n]])== 0)
									            	|| (((setsganados[juga3[g]]+setsperdidos[juga3[g]])!= 0) && (setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]]) > (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))))
									            	|| ((setsganados[juga3[g]]/(setsganados[juga3[g]]+setsperdidos[juga3[g]])) == (setsganados[juga3[n]]/(setsganados[juga3[n]]+setsperdidos[juga3[n]]))) && (((gamesganados[juga3[n]]+gamesperdidos[juga3[n]])== 0)
									            	|| (((gamesganados[juga3[g]]+gamesperdidos[juga3[g]])!= 0) && (gamesganados[juga3[g]]/(gamesganados[juga3[g]]+gamesperdidos[juga3[g]]) > (gamesganados[juga3[n]]/(gamesganados[juga3[n]]+gamesperdidos[juga3[n]])))))) {
									                aux = puntos3[n];
									                aux2 = juga3[n];
									                puntos3[n] = puntos3[g];
									                juga3[n] = juga3[g];
									                puntos3[g] = aux;
									                juga3[g] = aux2;
									            }
									        }
									    }
									}	
							if(juga3[1]>juga3[2]){//0 tiene un orden menor
								if (juga3[1] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga3[2] == 1 && !resultadoentreambos3[0]) || (juga3[2] == 2 && !resultadoentreambos3[1]) || (juga3[2] == 3 && !resultadoentreambos3[2]))
									{
										aux = puntos3[1];
						                aux2 = juga3[1];
						                puntos3[1] = puntos3[2];
						                juga3[1] = juga3[2];
						                puntos3[2] = aux;
						                juga3[2] = aux2;
									}
								}
								else
									if(juga3[1] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga3[2] == 2 && !resultadoentreambos3[3]) || (juga3[2] == 3 && !resultadoentreambos3[4]))
										{
											aux = puntos3[1];
							                aux2 = juga3[1];
							                puntos3[1] = puntos3[2];
							                juga3[1] = juga3[2];
							                puntos3[2] = aux;
							                juga3[2] = aux2;
										}
									}
									else
										if(juga3[1] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[2] == 3 && !resultadoentreambos3[5]))
											{
												aux = puntos3[1];
								                aux2 = juga3[1];
								                puntos3[1] = puntos3[2];
								                juga3[1] = juga3[2];
								                puntos3[2] = aux;
								                juga3[2] = aux2;
											}
										}
										
							}
							else{
								if (juga3[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga3[1] == 1 && resultadoentreambos3[0]) || (juga3[1] == 2 && resultadoentreambos3[1]) || (juga3[1] == 3 && resultadoentreambos3[2]))
									{
										aux = puntos3[1];
						                aux2 = juga3[1];
						                puntos3[1] = puntos3[2];
						                juga3[1] = juga3[2];
						                puntos3[2] = aux;
						                juga3[2] = aux2;
									}
								}
								else
									if(juga3[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga3[1] == 2 && resultadoentreambos3[3]) || (juga3[1] == 3 && resultadoentreambos3[4]))
										{
											aux = puntos3[1];
							                aux2 = juga3[1];
							                puntos3[1] = puntos3[2];
							                juga3[1] = juga3[2];
							                puntos3[2] = aux;
							                juga3[2] = aux2;
										}
									else
										if(juga3[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[1] == 3 && resultadoentreambos3[5]))
											{
												aux = puntos3[1];
								                aux2 = juga3[1];
								                puntos3[1] = puntos3[2];
								                juga3[1] = juga3[2];
								                puntos3[2] = aux;
								                juga3[2] = aux2;
											}
										}
										
							}
							}
							}
								else
									if (puntos3[2] == puntos3[3]){//Hay empate
										
							if(juga3[2]>juga3[3]){//0 tiene un orden menor
								if (juga3[2] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga3[3] == 1 && !resultadoentreambos3[0]) || (juga3[3] == 2 && !resultadoentreambos3[1]) || (juga3[3] == 3 && !resultadoentreambos3[2]))
									{
										aux = puntos3[2];
						                aux2 = juga3[2];
						                puntos3[2] = puntos3[3];
						                juga3[2] = juga3[3];
						                puntos3[3] = aux;
						                juga3[3] = aux2;
									}
								}
								else
									if(juga3[2] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga3[3] == 2 && !resultadoentreambos3[3]) || (juga3[3] == 3 && !resultadoentreambos3[4]))
										{
											aux = puntos3[2];
							                aux2 = juga3[2];
							                puntos3[2] = puntos3[3];
							                juga3[2] = juga3[3];
							                puntos3[3] = aux;
							                juga3[3] = aux2;
										}
									}
									else
										if(juga3[2] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[3] == 3 && !resultadoentreambos3[5]))
											{
												aux = puntos3[2];
								                aux2 = juga3[2];
								                puntos3[2] = puntos3[3];
								                juga3[2] = juga3[3];
								                puntos3[3] = aux;
								                juga3[3] = aux2;
											}
										}
							}
							else{
								if (juga3[3] == 0){ //es el primer jugador, resultado entre 0 y 3
									if ((juga3[2] == 1 && resultadoentreambos3[0]) || (juga3[2] == 2 && resultadoentreambos3[1]) || (juga3[2] == 3 && resultadoentreambos3[2]))
									{
										aux = puntos3[2];
						                aux2 = juga3[2];
						                puntos3[2] = puntos3[3];
						                juga3[2] = juga3[3];
						                puntos3[3] = aux;
						                juga3[3] = aux2;
									}
								}
								else
									if(juga3[3] == 1){//es el 2do jugador, resultado entre 4 y 6
										if ((juga3[2] == 2 && resultadoentreambos3[3]) || (juga3[2] == 3 && resultadoentreambos3[4]))
										{
											aux = puntos3[2];
							                aux2 = juga3[2];
							                puntos3[2] = puntos3[3];
							                juga3[2] = juga3[3];
							                puntos3[3] = aux;
							                juga3[3] = aux2;
										}
									else
										if(juga3[3] == 2){//es el 2do jugador, resultado entre 4 y 6
											if ((juga3[2] == 3 && resultadoentreambos3[5]))
											{
												aux = puntos3[2];
								                aux2 = juga3[2];
								                puntos3[2] = puntos3[3];
								                juga3[2] = juga3[3];
								                puntos3[3] = aux;
								                juga3[3] = aux2;
											}
										}
							}
							}
									}

								for (int n = 0;n<4;n++){
								over.setFontAndSize(bf, letra);
								over.setTextMatrix(CoordPDF[17], CoordPDF[juga[n]+4]);
								over.showText(""+(n+1));
								over.setTextMatrix(CoordPDF[17], CoordPDF[juga2[n]+8]);
								over.showText(""+(n+1));
								over.setTextMatrix(CoordPDF[17], CoordPDF[juga3[n]+12]);
								over.showText(""+(n+1));
								}
						over.endText();
						}
				stamper.close();
				reader.close();		
		
			
			} catch (IOException | DocumentException m) {
				m.printStackTrace();
			}  //Entrego el file del cuadro o donde se guarda dicho pdf
		break;
		}
			/*-------------------------------------------------------------------------------------------------------
			 --------------------------------------------------------------------------------------------------------* */
			else{//4 zonas de 3
				try {
					int zonanro;
					int puntos [] = new int [3];
					int setsganados[] = new int [3];
					int setsperdidos[] = new int [3];
					int gamesganados[] = new int [3];
					int gamesperdidos[] = new int [3];
					boolean resultadoentreambos [] = new boolean [3];
					int puntos2 [] = new int [3];
					int setsganados2[] = new int [3];
					int setsperdidos2[] = new int [3];
					int gamesganados2[] = new int [3];
					int gamesperdidos2[] = new int [3];
					boolean resultadoentreambos2 [] = new boolean [3];
					int puntos3 [] = new int [3];
					int setsganados3[] = new int [3];
					int setsperdidos3[] = new int [3];
					int gamesganados3[] = new int [3];
					int gamesperdidos3[] = new int [3];
					boolean resultadoentreambos3 [] = new boolean [3];
					int puntos4 [] = new int [3];
					int setsganados4[] = new int [3];
					int setsperdidos4[] = new int [3];
					int gamesganados4[] = new int [3];
					int gamesperdidos4[] = new int [3];
					boolean resultadoentreambos4 [] = new boolean [3];
					for (int u=0;u<3;u++){
						puntos[u] = 0;
						setsganados[u] = 0;
						setsperdidos[u] = 0;
						gamesganados[u] = 0;
						resultadoentreambos[u] = false;
						gamesperdidos[u] = 0;
						puntos2[u] = 0;
						setsganados2[u] = 0;
						setsperdidos2[u] = 0;
						gamesganados2[u] = 0;
						resultadoentreambos2[u] = false;
						gamesperdidos2[u] = 0;
						puntos3[u] = 0;
						setsganados3[u] = 0;
						setsperdidos3[u] = 0;
						gamesganados3[u] = 0;
						resultadoentreambos3[u] = false;
						gamesperdidos3[u] = 0;
						puntos4[u] = 0;
						setsganados4[u] = 0;
						setsperdidos4[u] = 0;
						gamesganados4[u] = 0;
						resultadoentreambos4[u] = false;
						gamesperdidos4[u] = 0;
					}
					int posicion;
					int posicion2;
					int result;
					PdfReader reader = new PdfReader(pDFIN);
					PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
					BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // No Preclasificados
					//loop on pages (1-based)
					for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
							PdfContentByte over = stamper.getOverContent(i);
			      
							over.beginText();
							int res = 0;
								while (res<resultados.length){
									if (!resultados[res].equalsIgnoreCase("")){
										//Cargo posicion zona 1
										posicion = 0;
										posicion2 = 0;
										result = res;
										zonanro = res / 3;
										result = (result % 3)+1;
										if (result < 3)
											posicion = 0;
										if (result == 3)
											posicion = 1;
											//Cargo posicion 2 zona
										result = res;
										result = (result % 3)+1;
										if (result == 1)
											posicion2 = 1;
										if (result == 2 || result == 3)
											posicion2 = 2;
										encontre = false;
										if (!resultados[res].equalsIgnoreCase("BYE")){
											if(EvaluacionResultado(resultados[res])){
												if(zonanro == 0){
												puntos[posicion] +=2;
												resultadoentreambos[res] = true;
												if (!resultados[res].equalsIgnoreCase("WO 2"))
													puntos[posicion2]++;
												puntos[posicion]++;
												}
												else{
													if(zonanro == 1){puntos2[posicion] +=2;
													resultadoentreambos2[res%3] = true;
													if (!resultados[res].equalsIgnoreCase("WO 2"))
														puntos2[posicion2]++;
													puntos2[posicion]++;
												}
												else {if (zonanro == 2){puntos3[posicion] +=2;
													resultadoentreambos3[res%3] = true;
													if (!resultados[res].equalsIgnoreCase("WO 2"))
														puntos3[posicion2]++;
													puntos3[posicion]++;
													}
												else{puntos4[posicion] +=2;
													resultadoentreambos4[res%3] = true;
													if (!resultados[res].equalsIgnoreCase("WO 2"))
														puntos4[posicion2]++;
													puntos4[posicion]++;
												}
												}
												
											}
											}
											else
											{
												if (zonanro == 0){
												puntos[posicion2]+=2;
												if (!resultados[res].equalsIgnoreCase("WO 1"))
													puntos[posicion]++;
												puntos[posicion2]++;
												}
												else{
													if(zonanro == 1){puntos2[posicion2] +=2;
													resultadoentreambos2[res%3] = true;
													if (!resultados[res].equalsIgnoreCase("WO 1"))
														puntos2[posicion]++;
													puntos2[posicion2]++;
												}
												else {if (zonanro == 2){puntos3[posicion2] +=2;
													resultadoentreambos3[res%3] = true;
													if (!resultados[res].equalsIgnoreCase("WO 1"))
														puntos3[posicion]++;
													puntos3[posicion2]++;
													}
												else{puntos4[posicion2] +=2;
													resultadoentreambos4[res%3] = true;
													if (!resultados[res].equalsIgnoreCase("WO 1"))
														puntos4[posicion]++;
													puntos4[posicion2]++;
												}
												}
												}
											}
											if (!resultados[res].contains("WO"))
										{
											if(zonanro == 0){
											setsganados[posicion] += Integer.parseInt(setsganados(resultados[res],1));
											setsperdidos[posicion] += Integer.parseInt(setsganados(resultados[res],2));
											setsganados[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
											setsperdidos[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
											}
											else{
												if (zonanro == 1){
												setsganados2[posicion] += Integer.parseInt(setsganados(resultados[res],1));
												setsperdidos2[posicion] += Integer.parseInt(setsganados(resultados[res],2));
												setsganados2[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
												setsperdidos2[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
												}
												else{if (zonanro == 2){
													setsganados3[posicion] += Integer.parseInt(setsganados(resultados[res],1));
													setsperdidos3[posicion] += Integer.parseInt(setsganados(resultados[res],2));
													setsganados3[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
													setsperdidos3[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
												}
												else{
													setsganados4[posicion] += Integer.parseInt(setsganados(resultados[res],1));
													setsperdidos4[posicion] += Integer.parseInt(setsganados(resultados[res],2));
													setsganados4[posicion2] += Integer.parseInt(setsganados(resultados[res],2));
													setsperdidos4[posicion2] += Integer.parseInt(setsganados(resultados[res],1));
											
													}
												}
											}
											}
										}
										over.setFontAndSize(bf, letra);
										over.setTextMatrix(CoordPDF[posicion2], CoordPDF[3+posicion+(3*zonanro)]); //Resultado
										if (resultados[res].equalsIgnoreCase("BYE"))
											over.showText("---------");
										else
											if(resultados[res].contains("WO"))
												over.showText("WO.");
											else
												over.showText(resultados[res]);
										//Imprimo resultado en la otra parte de la zona
										over.setTextMatrix(CoordPDF[posicion], CoordPDF[3+posicion2+3*zonanro]); //Resultado
										if (resultados[res].equalsIgnoreCase("BYE"))
											over.showText("---------");
										else
											over.showText(darvueltaresult2(resultados[res]));
									}
									res++;
									p++;	       	
					
								}
								for (int l = 0;l<3;l++){
								over.setFontAndSize(bf,letra);
								over.setTextMatrix(CoordPDF[15], CoordPDF[3+l]);
								over.showText(""+puntos[l]);
								over.setTextMatrix(CoordPDF[15], CoordPDF[6+l]);
								over.showText(""+puntos2[l]);
								over.setTextMatrix(CoordPDF[15], CoordPDF[9+l]);
								over.showText(""+puntos3[l]);
								over.setTextMatrix(CoordPDF[15], CoordPDF[12+l]);
								over.showText(""+puntos4[l]);}
								int [] juga = new int [3];
								juga[0] = 0;
								juga[1] = 1;
								juga[2] = 2;
								int [] juga2 = new int [3];
								juga2[0] = 0;
								juga2[1] = 1;
								juga2[2] = 2;
								int [] juga3 = new int [3];
								juga3[0] = 0;
								juga3[1] = 1;
								juga3[2] = 2;
								int [] juga4 = new int [3];
								juga4[0] = 0;
								juga4[1] = 1;
								juga4[2] = 2;
								int aux;
								int aux2;
							    //Ordeno de manera estricta
								for (int n = 0; n < 3 ; n++) {
							        for (int g = n + 1; g < 3; g++) {
							            if (puntos[g] > puntos[n]) {
							                aux = puntos[n];
							                aux2 = juga[n];
							                puntos[n] = puntos[g];
							                juga[n] = juga[g];
							                puntos[g] = aux;
							                juga[g] = aux2;
							            }
							            if (puntos2[g] > puntos2[n]) {
							                aux = puntos2[n];
							                aux2 = juga2[n];
							                puntos2[n] = puntos2[g];
							                juga2[n] = juga2[g];
							                puntos2[g] = aux;
							                juga2[g] = aux2;
							            }
							            if (puntos3[g] > puntos3[n]) {
							                aux = puntos3[n];
							                aux2 = juga3[n];
							                puntos3[n] = puntos3[g];
							                juga3[n] = juga3[g];
							                puntos3[g] = aux;
							                juga3[g] = aux2;
							            }
							            if (puntos4[g] > puntos4[n]) {
							                aux = puntos4[n];
							                aux2 = juga4[n];
							                puntos4[n] = puntos4[g];
							                juga4[n] = juga4[g];
							                puntos4[g] = aux;
							                juga4[g] = aux2;
							            }
							        }
							    }	
								//Verifico si no hay igualdades
								//Igualdades desde el primero
										if(puntos[0] == puntos[1]){//Hay  empate
											if (puntos[1] == puntos[2]){//Triple empate
												for (int n = 0; n < 3 ; n++) {
											        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
											            if (((setsganados[juga[n]]+setsperdidos[juga[n]])== 0)
											            	|| (((setsganados[juga[g]]+setsperdidos[juga[g]])!= 0) && (setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]]) > (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))))
											            	|| ((setsganados[juga[g]]/(setsganados[juga[g]]+setsperdidos[juga[g]])) == (setsganados[juga[n]]/(setsganados[juga[n]]+setsperdidos[juga[n]]))) && (((gamesganados[juga[n]]+gamesperdidos[juga[n]])== 0)
											            	|| (((gamesganados[juga[g]]+gamesperdidos[juga[g]])!= 0) && (gamesganados[juga[g]]/(gamesganados[juga[g]]+gamesperdidos[juga[g]]) > (gamesganados[juga[n]]/(gamesganados[juga[n]]+gamesperdidos[juga[n]])))))) {
											                aux = puntos[n];
											                aux2 = juga[n];
											                puntos[n] = puntos[g];
											                juga[n] = juga[g];
											                puntos[g] = aux;
											                juga[g] = aux2;
											            }
											        }
											    }
											}	
									if(juga[0]>juga[1]){//0 tiene un orden menor
										if (juga[0] == 0){ //es el primer jugador, resultado entre 0 y 1
											if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) )
											{
												aux = puntos[0];
								                aux2 = juga[0];
								                puntos[0] = puntos[1];
								                juga[0] = juga[1];
								                puntos[1] = aux;
								                juga[1] = aux2;
											}
										}
										else
											if(juga[0] == 1){//es el 2do jugador, resultado 2
												if ((juga[1] == 2 && !resultadoentreambos[2]))
												{
													aux = puntos[0];
									                aux2 = juga[0];
									                puntos[0] = puntos[1];
									                juga[0] = juga[1];
									                puntos[1] = aux;
									                juga[1] = aux2;
												}
											}
											
									}
									else{
										if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 1
											if ((juga[0] == 1 && !resultadoentreambos[0]) || (juga[0] == 2 && !resultadoentreambos[1]) )
											{
												aux = puntos[1];
								                aux2 = juga[1];
								                puntos[1] = puntos[0];
								                juga[1] = juga[0];
								                puntos[0] = aux;
								                juga[0] = aux2;
											}
										}
										else
											if(juga[1] == 1){//es el 2do jugador, resultado 2
												if ((juga[0] == 2 && !resultadoentreambos[2]))
												{
													aux = puntos[1];
									                aux2 = juga[1];
									                puntos[1] = puntos[0];
									                juga[1] = juga[0];
									                puntos[0] = aux;
									                juga[0] = aux2;
												}
											}
											
									}
										}
										else
											if (puntos[1] == puntos[2]){//Hay empate
												if(juga[1]>juga[2]){//0 tiene un orden menor
													if (juga[1] == 0){ //es el primer jugador, resultado entre 0 y 1
														if ((juga[2] == 1 && !resultadoentreambos[0]) || (juga[2] == 2 && !resultadoentreambos[1]) )
														{
															aux = puntos[1];
											                aux2 = juga[1];
											                puntos[1] = puntos[2];
											                juga[1] = juga[2];
											                puntos[2] = aux;
											                juga[2] = aux2;
														}
													}
													else
														if(juga[1] == 1){//es el 2do jugador, resultado 2
															if ((juga[2] == 2 && !resultadoentreambos[2]))
															{
																aux = puntos[1];
												                aux2 = juga[1];
												                puntos[1] = puntos[2];
												                juga[1] = juga[2];
												                puntos[2] = aux;
												                juga[2] = aux2;
															}
														}
														
												}
												else{
													if (juga[2] == 0){ //es el primer jugador, resultado entre 0 y 1
														if ((juga[1] == 1 && !resultadoentreambos[0]) || (juga[1] == 2 && !resultadoentreambos[1]) )
														{
															aux = puntos[2];
											                aux2 = juga[2];
											                puntos[2] = puntos[1];
											                juga[2] = juga[1];
											                puntos[1] = aux;
											                juga[1] = aux2;
														}
													}
													else
														if(juga[2] == 1){//es el 2do jugador, resultado 2
															if ((juga[1] == 2 && !resultadoentreambos[2]))
															{
																aux = puntos[2];
												                aux2 = juga[2];
												                puntos[2] = puntos[1];
												                juga[2] = juga[1];
												                puntos[1] = aux;
												                juga[1] = aux2;
															}
														}
														
												}	
									
											}
										//Verifico si no hay igualdades en zona 2
										//Igualdades desde el primero
												if(puntos2[0] == puntos2[1]){//Hay  empate
													if (puntos2[1] == puntos2[2]){//Triple empate
														for (int n = 0; n < 3 ; n++) {
													        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
													            if (((setsganados2[juga2[n]]+setsperdidos2[juga2[n]])== 0)
													            	|| (((setsganados2[juga2[g]]+setsperdidos2[juga2[g]])!= 0) && (setsganados2[juga2[g]]/(setsganados2[juga2[g]]+setsperdidos2[juga2[g]]) > (setsganados2[juga2[n]]/(setsganados2[juga2[n]]+setsperdidos2[juga2[n]]))))
													            	|| ((setsganados2[juga2[g]]/(setsganados2[juga2[g]]+setsperdidos2[juga2[g]])) == (setsganados2[juga2[n]]/(setsganados2[juga2[n]]+setsperdidos2[juga2[n]]))) && (((gamesganados2[juga2[n]]+gamesperdidos2[juga2[n]])== 0)
													            	|| (((gamesganados2[juga2[g]]+gamesperdidos2[juga2[g]])!= 0) && (gamesganados2[juga2[g]]/(gamesganados2[juga2[g]]+gamesperdidos2[juga2[g]]) > (gamesganados2[juga2[n]]/(gamesganados2[juga2[n]]+gamesperdidos2[juga2[n]])))))) {
													                aux = puntos2[n];
													                aux2 = juga2[n];
													                puntos2[n] = puntos2[g];
													                juga2[n] = juga2[g];
													                puntos2[g] = aux;
													                juga2[g] = aux2;
													            }
													        }
													    }
													}	
											if(juga2[0]>juga2[1]){//0 tiene un orden menor
												if (juga2[0] == 0){ //es el primer juga2dor, resultado entre 0 y 1
													if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) )
													{
														aux = puntos2[0];
										                aux2 = juga2[0];
										                puntos2[0] = puntos2[1];
										                juga2[0] = juga2[1];
										                puntos2[1] = aux;
										                juga2[1] = aux2;
													}
												}
												else
													if(juga2[0] == 1){//es el 2do juga2dor, resultado 2
														if ((juga2[1] == 2 && !resultadoentreambos2[2]))
														{
															aux = puntos2[0];
											                aux2 = juga2[0];
											                puntos2[0] = puntos2[1];
											                juga2[0] = juga2[1];
											                puntos2[1] = aux;
											                juga2[1] = aux2;
														}
													}
													
											}
											else{
												if (juga2[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
													if ((juga2[0] == 1 && !resultadoentreambos2[0]) || (juga2[0] == 2 && !resultadoentreambos2[1]) )
													{
														aux = puntos2[1];
										                aux2 = juga2[1];
										                puntos2[1] = puntos2[0];
										                juga2[1] = juga2[0];
										                puntos2[0] = aux;
										                juga2[0] = aux2;
													}
												}
												else
													if(juga2[1] == 1){//es el 2do juga2dor, resultado 2
														if ((juga2[0] == 2 && !resultadoentreambos2[2]))
														{
															aux = puntos2[1];
											                aux2 = juga2[1];
											                puntos2[1] = puntos2[0];
											                juga2[1] = juga2[0];
											                puntos2[0] = aux;
											                juga2[0] = aux2;
														}
													}
													
											}
												}
												else
													if (puntos2[1] == puntos2[2]){//Hay empate
														if(juga2[1]>juga2[2]){//0 tiene un orden menor
															if (juga2[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																if ((juga2[2] == 1 && !resultadoentreambos2[0]) || (juga2[2] == 2 && !resultadoentreambos2[1]) )
																{
																	aux = puntos2[1];
													                aux2 = juga2[1];
													                puntos2[1] = puntos2[2];
													                juga2[1] = juga2[2];
													                puntos2[2] = aux;
													                juga2[2] = aux2;
																}
															}
															else
																if(juga2[1] == 1){//es el 2do juga2dor, resultado 2
																	if ((juga2[2] == 2 && !resultadoentreambos2[2]))
																	{
																		aux = puntos2[1];
														                aux2 = juga2[1];
														                puntos2[1] = puntos2[2];
														                juga2[1] = juga2[2];
														                puntos2[2] = aux;
														                juga2[2] = aux2;
																	}
																}
																
														}
														else{
															if (juga2[2] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																if ((juga2[1] == 1 && !resultadoentreambos2[0]) || (juga2[1] == 2 && !resultadoentreambos2[1]) )
																{
																	aux = puntos2[2];
													                aux2 = juga2[2];
													                puntos2[2] = puntos2[1];
													                juga2[2] = juga2[1];
													                puntos2[1] = aux;
													                juga2[1] = aux2;
																}
															}
															else
																if(juga2[2] == 1){//es el 2do juga2dor, resultado 2
																	if ((juga2[1] == 2 && !resultadoentreambos2[2]))
																	{
																		aux = puntos2[2];
														                aux2 = juga2[2];
														                puntos2[2] = puntos2[1];
														                juga2[2] = juga2[1];
														                puntos2[1] = aux;
														                juga2[1] = aux2;
																	}
																}
																
														}	
											
													}
												//Verifico si no hay igualdades en zona 3
												//Igualdades desde el primero
														if(puntos3[0] == puntos3[1]){//Hay  empate
															if (puntos3[1] == puntos3[2]){//Triple empate
																for (int n = 0; n < 3 ; n++) {
															        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
															            if (((setsganados2[juga3[n]]+setsperdidos2[juga3[n]])== 0)
															            	|| (((setsganados2[juga3[g]]+setsperdidos2[juga3[g]])!= 0) && (setsganados2[juga3[g]]/(setsganados2[juga3[g]]+setsperdidos2[juga3[g]]) > (setsganados2[juga3[n]]/(setsganados2[juga3[n]]+setsperdidos2[juga3[n]]))))
															            	|| ((setsganados2[juga3[g]]/(setsganados2[juga3[g]]+setsperdidos2[juga3[g]])) == (setsganados2[juga3[n]]/(setsganados2[juga3[n]]+setsperdidos2[juga3[n]]))) && (((gamesganados2[juga3[n]]+gamesperdidos2[juga3[n]])== 0)
															            	|| (((gamesganados2[juga3[g]]+gamesperdidos2[juga3[g]])!= 0) && (gamesganados2[juga3[g]]/(gamesganados2[juga3[g]]+gamesperdidos2[juga3[g]]) > (gamesganados2[juga3[n]]/(gamesganados2[juga3[n]]+gamesperdidos2[juga3[n]])))))) {
															                aux = puntos3[n];
															                aux2 = juga3[n];
															                puntos3[n] = puntos3[g];
															                juga3[n] = juga3[g];
															                puntos3[g] = aux;
															                juga3[g] = aux2;
															            }
															        }
															    }
															}	
													if(juga3[0]>juga3[1]){//0 tiene un orden menor
														if (juga3[0] == 0){ //es el primer juga2dor, resultado entre 0 y 1
															if ((juga3[1] == 1 && !resultadoentreambos3[0]) || (juga3[1] == 2 && !resultadoentreambos3[1]) )
															{
																aux = puntos3[0];
												                aux2 = juga3[0];
												                puntos3[0] = puntos3[1];
												                juga3[0] = juga3[1];
												                puntos3[1] = aux;
												                juga3[1] = aux2;
															}
														}
														else
															if(juga3[0] == 1){//es el 2do juga2dor, resultado 2
																if ((juga3[1] == 2 && !resultadoentreambos3[2]))
																{
																	aux = puntos3[0];
													                aux2 = juga3[0];
													                puntos3[0] = puntos3[1];
													                juga3[0] = juga3[1];
													                puntos3[1] = aux;
													                juga3[1] = aux2;
																}
															}
															
													}
													else{
														if (juga3[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
															if ((juga3[0] == 1 && !resultadoentreambos3[0]) || (juga3[0] == 2 && !resultadoentreambos3[1]) )
															{
																aux = puntos3[1];
												                aux2 = juga3[1];
												                puntos3[1] = puntos3[0];
												                juga3[1] = juga3[0];
												                puntos3[0] = aux;
												                juga3[0] = aux2;
															}
														}
														else
															if(juga3[1] == 1){//es el 2do juga2dor, resultado 2
																if ((juga3[0] == 2 && !resultadoentreambos3[2]))
																{
																	aux = puntos3[1];
													                aux2 = juga3[1];
													                puntos3[1] = puntos3[0];
													                juga3[1] = juga3[0];
													                puntos3[0] = aux;
													                juga3[0] = aux2;
																}
															}
															
													}
														}
														else
															if (puntos3[1] == puntos3[2]){//Hay empate
																if(juga3[1]>juga3[2]){//0 tiene un orden menor
																	if (juga3[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																		if ((juga3[2] == 1 && !resultadoentreambos3[0]) || (juga3[2] == 2 && !resultadoentreambos3[1]) )
																		{
																			aux = puntos3[1];
															                aux2 = juga3[1];
															                puntos3[1] = puntos3[2];
															                juga3[1] = juga3[2];
															                puntos3[2] = aux;
															                juga3[2] = aux2;
																		}
																	}
																	else
																		if(juga3[1] == 1){//es el 2do juga2dor, resultado 2
																			if ((juga3[2] == 2 && !resultadoentreambos3[2]))
																			{
																				aux = puntos3[1];
																                aux2 = juga3[1];
																                puntos3[1] = puntos3[2];
																                juga3[1] = juga3[2];
																                puntos3[2] = aux;
																                juga3[2] = aux2;
																			}
																		}
																		
																}
																else{
																	if (juga3[2] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																		if ((juga3[1] == 1 && !resultadoentreambos3[0]) || (juga3[1] == 2 && !resultadoentreambos3[1]) )
																		{
																			aux = puntos3[2];
															                aux2 = juga3[2];
															                puntos3[2] = puntos3[1];
															                juga3[2] = juga3[1];
															                puntos3[1] = aux;
															                juga3[1] = aux2;
																		}
																	}
																	else
																		if(juga3[2] == 1){//es el 2do juga2dor, resultado 2
																			if ((juga3[1] == 2 && !resultadoentreambos3[2]))
																			{
																				aux = puntos3[2];
																                aux2 = juga3[2];
																                puntos3[2] = puntos3[1];
																                juga3[2] = juga3[1];
																                puntos3[1] = aux;
																                juga3[1] = aux2;
																			}
																		}
																		
																}	
													
															}
														//Verifico si no hay igualdades en zona 3
														//Igualdades desde el primero
																if(puntos4[0] == puntos4[1]){//Hay  empate
																	if (puntos4[1] == puntos4[2]){//Triple empate
																		for (int n = 0; n < 3 ; n++) {
																	        for (int g = n + 1; g < 3; g++) {//Verifico por porcentaje de sets
																	            if (((setsganados2[juga4[n]]+setsperdidos2[juga4[n]])== 0)
																	            	|| (((setsganados2[juga4[g]]+setsperdidos2[juga4[g]])!= 0) && (setsganados2[juga4[g]]/(setsganados2[juga4[g]]+setsperdidos2[juga4[g]]) > (setsganados2[juga4[n]]/(setsganados2[juga4[n]]+setsperdidos2[juga4[n]]))))
																	            	|| ((setsganados2[juga4[g]]/(setsganados2[juga4[g]]+setsperdidos2[juga4[g]])) == (setsganados2[juga4[n]]/(setsganados2[juga4[n]]+setsperdidos2[juga4[n]]))) && (((gamesganados2[juga4[n]]+gamesperdidos2[juga4[n]])== 0)
																	            	|| (((gamesganados2[juga4[g]]+gamesperdidos2[juga4[g]])!= 0) && (gamesganados2[juga4[g]]/(gamesganados2[juga4[g]]+gamesperdidos2[juga4[g]]) > (gamesganados2[juga4[n]]/(gamesganados2[juga4[n]]+gamesperdidos2[juga4[n]])))))) {
																	                aux = puntos4[n];
																	                aux2 = juga4[n];
																	                puntos4[n] = puntos4[g];
																	                juga4[n] = juga4[g];
																	                puntos4[g] = aux;
																	                juga4[g] = aux2;
																	            }
																	        }
																	    }
																	}	
															if(juga4[0]>juga4[1]){//0 tiene un orden menor
																if (juga4[0] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																	if ((juga4[1] == 1 && !resultadoentreambos4[0]) || (juga4[1] == 2 && !resultadoentreambos4[1]) )
																	{
																		aux = puntos4[0];
														                aux2 = juga4[0];
														                puntos4[0] = puntos4[1];
														                juga4[0] = juga4[1];
														                puntos4[1] = aux;
														                juga4[1] = aux2;
																	}
																}
																else
																	if(juga4[0] == 1){//es el 2do juga2dor, resultado 2
																		if ((juga4[1] == 2 && !resultadoentreambos4[2]))
																		{
																			aux = puntos4[0];
															                aux2 = juga4[0];
															                puntos4[0] = puntos4[1];
															                juga4[0] = juga4[1];
															                puntos4[1] = aux;
															                juga4[1] = aux2;
																		}
																	}
																	
															}
															else{
																if (juga4[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																	if ((juga4[0] == 1 && !resultadoentreambos4[0]) || (juga4[0] == 2 && !resultadoentreambos4[1]) )
																	{
																		aux = puntos4[1];
														                aux2 = juga4[1];
														                puntos4[1] = puntos4[0];
														                juga4[1] = juga4[0];
														                puntos4[0] = aux;
														                juga4[0] = aux2;
																	}
																}
																else
																	if(juga4[1] == 1){//es el 2do juga2dor, resultado 2
																		if ((juga4[0] == 2 && !resultadoentreambos4[2]))
																		{
																			aux = puntos4[1];
															                aux2 = juga4[1];
															                puntos4[1] = puntos4[0];
															                juga4[1] = juga4[0];
															                puntos4[0] = aux;
															                juga4[0] = aux2;
																		}
																	}
																	
															}
																}
																else
																	if (puntos4[1] == puntos4[2]){//Hay empate
																		if(juga4[1]>juga4[2]){//0 tiene un orden menor
																			if (juga4[1] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																				if ((juga4[2] == 1 && !resultadoentreambos4[0]) || (juga4[2] == 2 && !resultadoentreambos4[1]) )
																				{
																					aux = puntos4[1];
																	                aux2 = juga4[1];
																	                puntos4[1] = puntos4[2];
																	                juga4[1] = juga4[2];
																	                puntos4[2] = aux;
																	                juga4[2] = aux2;
																				}
																			}
																			else
																				if(juga4[1] == 1){//es el 2do juga2dor, resultado 2
																					if ((juga4[2] == 2 && !resultadoentreambos4[2]))
																					{
																						aux = puntos4[1];
																		                aux2 = juga4[1];
																		                puntos4[1] = puntos4[2];
																		                juga4[1] = juga4[2];
																		                puntos4[2] = aux;
																		                juga4[2] = aux2;
																					}
																				}
																				
																		}
																		else{
																			if (juga4[2] == 0){ //es el primer juga2dor, resultado entre 0 y 1
																				if ((juga4[1] == 1 && !resultadoentreambos4[0]) || (juga4[1] == 2 && !resultadoentreambos4[1]) )
																				{
																					aux = puntos4[2];
																	                aux2 = juga4[2];
																	                puntos4[2] = puntos4[1];
																	                juga4[2] = juga4[1];
																	                puntos4[1] = aux;
																	                juga4[1] = aux2;
																				}
																			}
																			else
																				if(juga4[2] == 1){//es el 2do juga2dor, resultado 2
																					if ((juga4[1] == 2 && !resultadoentreambos4[2]))
																					{
																						aux = puntos4[2];
																		                aux2 = juga4[2];
																		                puntos4[2] = puntos4[1];
																		                juga4[2] = juga4[1];
																		                puntos4[1] = aux;
																		                juga4[1] = aux2;
																					}
																				}
																				
																		}	
															
																	}
								for (int n = 0;n<3;n++){
									over.setFontAndSize(bf, letra);
									over.setTextMatrix(CoordPDF[16], CoordPDF[juga[n]+3]);
									over.showText(""+(n+1));
									over.setTextMatrix(CoordPDF[16], CoordPDF[juga2[n]+6]);
									over.showText(""+(n+1));
									over.setTextMatrix(CoordPDF[16], CoordPDF[juga3[n]+9]);
									over.showText(""+(n+1));
									over.setTextMatrix(CoordPDF[16], CoordPDF[juga4[n]+12]);
									over.showText(""+(n+1));
									}
							over.endText();
							}
					stamper.close();
					reader.close();		
			
				
				} catch (IOException | DocumentException m) {
					m.printStackTrace();
				}  //Entrego el file del cuadro o donde se guarda dicho pdf
			break;
			}
		}
						}
	}	
	}
private String darvueltaresult(String string) {// si gano el 2do jugador el resultado esta dado vuelta por lo que hay que ordenarlo
	String result = "";
	char g1 = ' ';
	char g2 = ' ';
	int y = 0;
	int ultimocaract = string.length()-1;
	if(string.contains("WO"))
		return "WO.";
	if (string.contains("abandono 2")){
		while (string.charAt(y)!= 'a'){
			result += string.charAt(y);
			y++;
		}
		result += " ab.";
		return result;
	}
	
	if(string.contains("abandono 1") || ((string.charAt(ultimocaract) == '7') && (string.charAt(ultimocaract-2) != '9')) || (string.charAt(ultimocaract) == '9') || ((string.charAt(ultimocaract) == '6') && (string.charAt(ultimocaract-2) != '9') && (string.charAt(ultimocaract-2) != '7')))
	{
		while (y<string.length() && string.charAt(y) != 'a'){
			if(string.charAt(y) != ' '){
			g1 = string.charAt(y);
			y+= 2;
			g2 = string.charAt(y);
			result += g2 + "-" + g1;
			y++;
			if(y<string.length() && string.charAt(y) == '/')
				result += '/';
			}
			y++;
			
		}
		if (string.contains("abandono"))
			result += " ab.";
		return result;
	}
	else
		return string;
}
private String darvueltaresult2(String string) {// si gano el 2do jugador el resultado esta dado vuelta por lo que hay que ordenarlo
	String result = "";
	char g1 = ' ';
	char g2 = ' ';
	int y = 0;
	int ultimocaract = string.length()-1;
	if(string.contains("WO"))
		return "WO.";
	if (string.contains("abandono 2")){
		while (string.charAt(y)!= 'a'){
			result += string.charAt(y);
			y++;
		}
		result += " ab.";
		return result;
	}
	
		while (y<string.length() && string.charAt(y) != 'a'){
			if(string.charAt(y) != ' '){
			g1 = string.charAt(y);
			y+= 2;
			g2 = string.charAt(y);
			result += g2 + "-" + g1;
			y++;
			if(y<string.length() && string.charAt(y) == '/')
				result += '/';
			}
			y++;
			
		}
		if (string.contains("abandono"))
			result += " ab.";
		return result;
	}
private int[] cargarCuadro32() {//ARMO COORDS CUADRO 32, VER TEMA Q y CUADRO 64/128!!!
	
		/* Coord X de izq a derecha (Estado, Rank, Federacion, Primera ronda, cuartos, semi y final,
		 *  semana, ciudad, seededs, LL y Replacing, Last direct y signature)
		 *  Luego Coord Y arriba a abajo (primera ronda, cuartos, semis, final y campeon,ciudad/fecha, seedes, LL/Replaicing,
		 *  Last direct y signature)
		 */
		int[] CoordPDF = new int [36];
		//Coordenadas en X
		CoordPDF[0] = 133; //Nombre 1ra ronda
		CoordPDF[1] = 208; //2da ronda, resultados +2
		CoordPDF[2] = 283; //cuartos, resultados +2
		CoordPDF[3] = 359; //semis, resultados +2
		CoordPDF[4] = 434; //Final y campeon, resultados +2
		
		//Coordenadas Y resultado score a partir de 2da ronda Y-9
		//2da ronda
		CoordPDF[5] = 610;//1
		CoordPDF[6] = 579;//2
		CoordPDF[7] = 548;//3
		CoordPDF[8] = 518;//4
		CoordPDF[9] = 487;//5
		CoordPDF[10] = 456;//6
		CoordPDF[11] = 425;//7
		CoordPDF[12] = 394;//8
		CoordPDF[13] = 363;//9 
		CoordPDF[14] = 332;//10
		CoordPDF[15] = 301;//11
		CoordPDF[16] = 270;//12 
		CoordPDF[17] = 239;//13
		CoordPDF[18] = 208;//14
		CoordPDF[19] = 177;//15
		CoordPDF[20] = 146;//16
		
	    //cuartos
		CoordPDF[21] = 595;//1
		CoordPDF[22] = 533;//2
		CoordPDF[23] = 471;//3
		CoordPDF[24] = 409;//4
		CoordPDF[25] = 348;//5
		CoordPDF[26] = 286;//6
		CoordPDF[27] = 224;//7
		CoordPDF[28] = 162;//8 
		
		//semis
		CoordPDF[29] = 564;//1
		CoordPDF[30] = 440;//2
		CoordPDF[31] = 316;//3
		CoordPDF[32] = 192;//4
		
		//Final
		CoordPDF[33] = 502;//1
		CoordPDF[34] = 254;//2
		
		//Campeon
		CoordPDF[35] = 379;//1
		
			return CoordPDF;
	}
private int[] cargarCuadro16() {//ARMO COORDS CUADRO 16
	/* Coord X de izq a derecha (Estado, Rank, Federacion, Primera ronda, cuartos, semi y final,
	 *  semana, ciudad, seededs, LL y Replacing, Last direct y signature)
	 *  Luego Coord Y arriba a abajo (primera ronda, cuartos, semis, final y campeon,ciudad/fecha, seedes, LL/Replaicing,
	 *  Last direct y signature)
	 */
	int[] CoordPDF = new int [19];
	
	//Coordenadas en X
	CoordPDF[0] = 150; //Nombre 1ra ronda
	CoordPDF[1] = 219; //Cuartos, resultados +2
	CoordPDF[2] = 287; //Semis, resultados +2
	CoordPDF[3] = 355; //Final y campeon es -4, resultados +2, result final -2
	
	//Coordenadas Y resultado score a partir de 2da ronda Y-9
	//2da ronda
	CoordPDF[4] = 582;//1
	CoordPDF[5] = 526;//2
	CoordPDF[6] = 470;//3
	CoordPDF[7] = 414;//4
	CoordPDF[8] = 358;//5
	CoordPDF[9] = 303;//6
	CoordPDF[10] = 247;//7
	CoordPDF[11] = 192;//8
	//semis
	CoordPDF[12] = 554;//1
	CoordPDF[13] = 443;//2
	CoordPDF[14] = 331;//3
	CoordPDF[15] = 220;//4
	//Final
	CoordPDF[16] = 499;//1
	CoordPDF[17] = 276;//2
	//Campeon
	CoordPDF[18] = 387;//1
		return CoordPDF;
}
private int[] cargarzona(int i) {//ARMO COORDS ZONAS
	/* Coord X de izq a derecha (Estado, Rank, Federacion, Primera ronda, cuartos, semi y final,
	 *  semana, ciudad, seededs, LL y Replacing, Last direct y signature)
	 *  Luego Coord Y arriba a abajo (primera ronda, cuartos, semis, final y campeon,ciudad/fecha, seedes, LL/Replaicing,
	 *  Last direct y signature)
	 */
	int[] CoordPDF = new int [30];
	switch (i){
	case 5:
	{
		/* Coord X de izq a derecha (Siembra, Rank, Nombre jugador, ciudad, fecha/grado)
		 *  Luego Coord Y arriba a abajo (los jugadores y despues ciudad/grado y fecha
		 */
		//Coordenadas en X
		CoordPDF[0] = 224; //1
		CoordPDF[1] = 275; //2
		CoordPDF[2] = 328; //3
		CoordPDF[3] = 379; //4
		CoordPDF[4] = 430; //5
		
		//Coordenadas en Y
		CoordPDF[5] = 500; //1
		CoordPDF[6] = 483; //2
		CoordPDF[7] = 465; //3
		CoordPDF[8] = 449; //4
		CoordPDF[9] = 431; //5
		//X de puntos
		CoordPDF[10] = 523;
		//X de posicion final
		CoordPDF[11] = 567;
		//Posiciones finales
		//X posiciones finales
		CoordPDF[12] = 25;
		//X Numeros pos finales
		CoordPDF[13] = 60;
		//Y posiciones finales
		CoordPDF[14] = 115;
		CoordPDF[15] = 95;
		CoordPDF[16] = 75;
		CoordPDF[17] = 55;
		CoordPDF[18] = 35;

 		break;
	}
	case 6:
	{
		//Coordenadas en X
		CoordPDF[0] = 295; //1
		CoordPDF[1] = 349; //2
		CoordPDF[2] = 404; //3
		
		//Coordenadas en Y
		CoordPDF[3] = 574; //1
		CoordPDF[4] = 550; //2
		CoordPDF[5] = 528; //3
		CoordPDF[6] = 465; //4
		CoordPDF[7] = 443; //5
		CoordPDF[8] = 420; //6
		//X de puntos
		CoordPDF[9] = 524;
		//X de posicion final
		CoordPDF[10] = 566;
		//Posiciones finales
		//X posiciones finales
		CoordPDF[11] = 25;
		//X Numeros semifinales
		CoordPDF[12] = 60;
		//X de final
		CoordPDF[13] = 115;
		//X de campeon
		CoordPDF[14] = 95;
		//Y semifinales
		CoordPDF[15] = 75;
		CoordPDF[16] = 55;
		CoordPDF[17] = 35;
		CoordPDF[18] = 35;
		//Y de finalistas
		CoordPDF[19] = 35;
		CoordPDF[20] = 35;
		//Y de campeon
		CoordPDF[21] = 35;
		
		 		break;
	}
	case 8:
	{
		//Coordenadas en X
		CoordPDF[0] = 269; //1
		CoordPDF[1] = 317; //2
		CoordPDF[2] = 367; //3
		CoordPDF[3] = 412; //4
		
		//Coordenadas en Y
		CoordPDF[4] = 582; //1
		CoordPDF[5] = 566; //2
		CoordPDF[6] = 550; //3
		CoordPDF[7] = 533; //4
		CoordPDF[8] = 477; //5
		CoordPDF[9] = 461; //6
		CoordPDF[10] = 444; //7
		CoordPDF[11] = 428; //8
		//X de puntos
		CoordPDF[12] = 532;
		//X de posicion final
		CoordPDF[13] = 575;
		//Posiciones finales
		//X posiciones finales
		CoordPDF[14] = 25;
		//X Numeros semifinales
		CoordPDF[15] = 60;
		//X de final
		CoordPDF[16] = 115;
		//X de campeon
		CoordPDF[17] = 95;
		//Y semifinales
		CoordPDF[18] = 75;
		CoordPDF[19] = 55;
		CoordPDF[20] = 35;
		CoordPDF[21] = 35;
		//Y de finalistas
		CoordPDF[22] = 35;
		CoordPDF[23] = 35;
		//Y de campeon
		CoordPDF[24] = 35;
		

		 		break;
	}
	case 9:
	{
		//Coordenadas en X
		CoordPDF[0] = 277; //1
		CoordPDF[1] = 330; //2
		CoordPDF[2] = 387; //3
		
		//Coordenadas en Y
		CoordPDF[3] = 570; //1
		CoordPDF[4] = 549; //2
		CoordPDF[5] = 527; //3
		CoordPDF[6] = 470; //4
		CoordPDF[7] = 449; //5
		CoordPDF[8] = 427; //6
		CoordPDF[9] = 370; //7
		CoordPDF[10] = 348; //8
		CoordPDF[11] = 326; //9
		//X de puntos
		CoordPDF[12] = 503;
		//X de posicion final
		CoordPDF[13] = 544;
		//Posiciones finales
		//X posiciones finales
		CoordPDF[14] = 25;
		//X Numeros semifinales
		CoordPDF[15] = 60;
		//X de final
		CoordPDF[16] = 115;
		//X de campeon
		CoordPDF[17] = 95;
		//Y semifinales
		CoordPDF[18] = 75;
		CoordPDF[19] = 55;
		CoordPDF[20] = 35;
		CoordPDF[21] = 35;
		//Y de finalistas
		CoordPDF[22] = 35;
		CoordPDF[23] = 35;
		//Y de campeon
		CoordPDF[24] = 35;
		

		 		break;
	}
	case 11:{// 3 zonas de 4
		//Coordenadas en X
				CoordPDF[0] = 223; //1
				CoordPDF[1] = 274; //2
				CoordPDF[2] = 329; //3
				CoordPDF[3] = 380; //4
				
				//Coordenadas en Y
				CoordPDF[4] = 584; //1
				CoordPDF[5] = 559; //2
				CoordPDF[6] = 543; //3
				CoordPDF[7] = 528; //4
				CoordPDF[8] = 476; //5
				CoordPDF[9] = 460; //6
				CoordPDF[10] = 445; //7
				CoordPDF[11] = 429; //8
				CoordPDF[12] = 375; //9
				CoordPDF[13] = 359; //10
				CoordPDF[14] = 343; //11
				CoordPDF[15] = 328; //12
				//X de puntos
				CoordPDF[16] = 502;
				//X de posicion final
				CoordPDF[17] = 542;
				//Posiciones finales
				//X posiciones finales
				CoordPDF[18] = 25;
				//X Numeros semifinales
				CoordPDF[19] = 60;
				//X de final
				CoordPDF[20] = 115;
				//X de campeon
				CoordPDF[21] = 95;
				//Y semifinales
				CoordPDF[22] = 75;
				CoordPDF[23] = 55;
				CoordPDF[24] = 35;
				CoordPDF[25] = 35;
				//Y de finalistas
				CoordPDF[26] = 35;
				CoordPDF[27] = 35;
				//Y de campeon
				CoordPDF[28] = 35;
				

		 		break;

	}
	case 12:// 4 zonas de 3
	{
		//Coordenadas en X
				CoordPDF[0] = 275; //1
				CoordPDF[1] = 331; //2
				CoordPDF[2] = 392; //3
				
				//Coordenadas en Y
				CoordPDF[3] = 576; //1
				CoordPDF[4] = 554; //2
				CoordPDF[5] = 532; //3
				CoordPDF[6] = 475; //4
				CoordPDF[7] = 454; //5
				CoordPDF[8] = 432; //6
				CoordPDF[9] = 375; //7
				CoordPDF[10] = 353; //8
				CoordPDF[11] = 331; //9
				CoordPDF[12] = 268; //10
				CoordPDF[13] = 246; //11
				CoordPDF[14] = 225; //12
				//X de puntos
				CoordPDF[15] = 502;
				//X de posicion final
				CoordPDF[16] = 547;
				
		 		break;
}
	case 16:
	{
		//Coordenadas en X
				CoordPDF[0] = 267; //1
				CoordPDF[1] = 318; //2
				CoordPDF[2] = 372; //3
				CoordPDF[3] = 422; //4
				
				//Coordenadas en Y
				CoordPDF[4] = 576; //1
				CoordPDF[5] = 560; //2
				CoordPDF[6] = 544; //3
				CoordPDF[7] = 529; //4
				CoordPDF[8] = 481; //5
				CoordPDF[9] = 465; //6
				CoordPDF[10] = 450; //7
				CoordPDF[11] = 434; //8
				CoordPDF[12] = 381; //9
				CoordPDF[13] = 365; //10
				CoordPDF[14] = 349; //11
				CoordPDF[15] = 334; //12
				CoordPDF[16] = 273; //13
				CoordPDF[17] = 257; //14
				CoordPDF[18] = 241; //15
				CoordPDF[19] = 226; //16
				//X de puntos
				CoordPDF[20] = 502;
				//X de posicion final
				CoordPDF[21] = 542;
				
		 		break;
	}
	}
	return CoordPDF;
}

}


