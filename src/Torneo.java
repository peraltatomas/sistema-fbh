import java.io.*;
import java.util.*;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class Torneo {

	private String Grado;
	private String Nombre;
	private ArrayList<Jugador> jugadores;
	private int[] CoordPDF;
	private String Profesor;
	//private boolean Dobles;
	private String Ciudad;
	private ArrayList<Jugador> preclasificados;
	private boolean antesG1;

public Torneo (String g, String nom, boolean dob, String prof, String ciu, boolean aG1)
{ //Los jugadores no estan ordenados, necesito del rank y ordeno
	Grado = g;
	Nombre = nom;
	jugadores = new ArrayList<Jugador>();
	preclasificados = new ArrayList<Jugador>();
	CoordPDF = new int[100];
	Profesor = prof;
	//Dobles = dob;
	Ciudad = ciu;
	antesG1 = aG1;
	CrearTXTdePDF();
}
public Torneo (String g, String nom, String prof, String ciu, boolean aG1)
{ //Los jugadores no estan ordenados, necesito del rank y ordeno
	Grado = g;
	Nombre = nom;
	jugadores = new ArrayList<Jugador>();
	preclasificados = new ArrayList<Jugador>();
	CoordPDF = new int[100];
	Profesor = prof;
	//Dobles = dob;
	Ciudad = ciu;
	antesG1 = aG1;
	CrearTXTdePDF();
}
public Torneo() {//Creo variable torneo para usar metodo de jugadores nuevos

}
public void AgregarJugador(Jugador j){//Agrega jugador ordenado luego metodo ordenar ordena, faltan limitaciones
	try {
	Jugador agregar = j;
	if (!jugadores.contains(j))
	{
		agregar.setRanking(BuscarRank(j.getNrodoc()));
		int r = Limitar(agregar);
		if (r == 0 || r == 3 || r == 4){//Veo si puede jugar
			if(r == 4){
				String ruta = "./Cuadros/JugadoresRechazados.txt";
				File archivo = new File(ruta);
				BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
				if (archivo.createNewFile())
				{
					bw.write("Jugadores rechazados en la creacion del las listas/Cuadros \n\n");
					bw.write("El jugador " + j.getApellido() + ", " + j.getNombre() + " ha sido aceptado en la categoria " +
							Nombre + "\n pero debera efectuar pedido especial a la AAT para poder participar del mismo \n\n");
					bw.close();
				}
				else
				{
					bw.write("El jugador " + j.getApellido() + ", " + j.getNombre() + " ha sido aceptado en la categoria " +
							Nombre + "\n pero debera efectuar pedido especial a la AAT para poder participar del mismo \n");
					bw.close();
				}
			}
			if (r == 3){//No hay informacion
			if (esdelacategoria(j)){
				String ruta = "./Aceptaciones/Observaciones.txt";
				File archivo = new File(ruta);
				BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
				if (archivo.createNewFile())
				{
					bw.write("Jugadores rechazados en la creacion del las listas/Cuadros \n\n");
					bw.write("El jugador " + j.getApellido() + ", " + j.getNombre() + " ha sido aceptado en la categoria " +
							Nombre + "\n pero se debera tener cuidado debido a la falta de informacion en la edad del jugador \n\n");
					bw.close();
				}
				else
				{
					bw.write("El jugador " + j.getApellido() + ", " + j.getNombre() + " ha sido aceptado en la categoria " +
							Nombre + "\n pero se debera tener cuidado debido a la falta de informacion en la edad del jugador \n");
					bw.close();
				}
			}
				if (agregar.getRanking() == 9999) //Me fijo si esta en el ranking
				{
					CreoArchivoAAT(agregar);
				}
			}
			jugadores.add(agregar);
		}
		else //No puede jugar por limitaciones de la AAT
		{

			String razon = "";

			if(r == 1)
				razon = "Edad no permitida para la participacion en el torneo " + Grado + " " + Nombre + ".";
			else
				if(r == 2)
					razon = "El ranking del jugador es demasiado elevado para participar en el torneo. El ranking del jugador es " + j.getRanking();
			String ruta = "./Cuadros/JugadoresRechazados.txt";
			File archivo = new File(ruta);
			BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
			if(archivo.exists())
			{
				bw.write("El jugador " + j.getApellido() + ", " + j.getNombre() + " ha sido rechazado de la categoria " +
						Nombre + "\n");
						bw.write("El motivo es " + razon + "\n\n");
						bw.close();
			}
			else
			{
				bw.write("Jugadores rechazados en la creacion del las listas/Cuadros \n\n");
				bw.write("El jugador " + j.getApellido() + ", " + j.getNombre() + " ha sido rechazado de la categoria " +
				Nombre + "\n");
				bw.write("El motivo es " + razon + "\n\n");
				bw.close();
			}

		}
	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
@SuppressWarnings("deprecation")
private boolean esdelacategoria(Jugador j) {
	boolean es = false;
	int edad = 0;
	Date fecha = new Date();
	int anio = fecha.getYear()+1900; //Considera el ano actual - 1900
	if(!j.getFechaNac().equals(""))
		edad = anio - Integer.parseInt(j.getFechaNac().substring(0, 4));
	//SEdad con la que comienza el anio
	if (edad != 0)//No tengo informacion de la edad
	{
	if (Nombre.equals("Sub 10 (Varones)") || Nombre.equals("Sub 10 (Mujeres)"))
		if (edad == 10 || edad == 9)
			es = true;
	if (Nombre.equals("Sub 12 (Varones)") || Nombre.equals("Sub 12 (Mujeres)"))
		if (edad == 12 || edad == 11)
			es = true;
	if (Nombre.equals("Sub 14 (Varones)") || Nombre.equals("Sub 14 (Mujeres)"))
		if (edad == 14 || edad == 13)
			es = true;
	if (Nombre.equals("Sub 16 (Varones)") || Nombre.equals("Sub 16 (Mujeres)"))
		if (edad == 16 || edad == 15)
			es = true;
	if (Nombre.equals("Sub 18 (Varones)") || Nombre.equals("Sub 18 (Mujeres)"))
		if (edad == 18 || edad == 17 || edad == 19)
			es = true;
	}
	return es;
}
public void CreoArchivoAAT(Jugador agregar) {//Creo archivo jugador nuevo en codif de aat
	File archivo;
	String cadena = ""; //IR AGREGANDO ESPACIOS ENTRE MEDIO DEPENDE DONDE CORRESPONDA
	archivo = new File ("./JugadorNuevo/" + agregar.getApellido() + agregar.getNombre() + ".txt");
	try{
		FileWriter w = new FileWriter(archivo);
		BufferedWriter bw = new BufferedWriter(w);
		PrintWriter wr = new PrintWriter(bw);

		//Cargo federacion 1-3
		cadena = federacion(agregar.getFederacion());
		if (cadena.length() != 3){
			wr.write(" ");
			for (int i = 2-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.write(cadena);

		//cargo club 4-6
		cadena = nroclub(agregar.getClub());
		if (cadena.length() != 3){
			for (int i = 3-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo nacionalidad 7-9
		cadena = nronacionalidad(agregar.getNacionalidad());
		if (cadena.length() != 3){
			for (int i = 3-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo tipo doc
		cadena = nrotipodoc(agregar.getTipoDoc());
		if (cadena.length() != 3){
			for (int i = 3-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo prov
		cadena = nroprovincia(agregar.getProvincia());
		if (cadena.length() != 3){
			for (int i = 3-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo nombre
		cadena = (agregar.getApellido() + ", " + agregar.getNombre());
		if (cadena.length() != 63){
			for (int i = 63-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo genero
			wr.append(agregar.getGenero());

		//cargo fecha Nacimiento
		cadena = agregar.getFechaNac();
		if (cadena.length() != 8){
			for (int i = 8-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo nro doc
		if (agregar.getNrodoc().equalsIgnoreCase("-1"))
			cadena = "";
		else
			cadena = agregar.getNrodoc();
		if (cadena.length() != 50){
			for (int i = 50-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo localidad
		cadena = agregar.getLocalidad();
		if (cadena.length() != 80){
			for (int i = 80-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo direccion
		cadena = agregar.getDireccion();
		if (cadena.length() != 80){
			for (int i = 80-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo CP
		cadena = agregar.getCP();
		if (cadena.length() != 10){
			for (int i = 10-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo telefono
		cadena = agregar.getTelefono();
		if (cadena.length() != 30){
			for (int i = 30-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo email
		cadena = agregar.getEmail();
		if (cadena.length() != 80){
			for (int i = 80-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo nrocasa
		cadena = agregar.getNrocasa();
		if (cadena.length() != 50){
			for (int i = 50-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo depto
		cadena = agregar.getDepto();
		if (cadena.length() != 50){
			for (int i = 50-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

		//cargo celular
		cadena = agregar.getCelular();
		if (cadena.length() != 50){
			for (int i = 50-cadena.length();i>0;i--)
				wr.append(" ");
			wr.append(cadena);
		}
		else
			wr.append(cadena);

	wr.close();

	bw.close();

	}catch(IOException e){};

}
//Metodos realizados a partir de tabla maestra AAT---- HACER SWITCH CON TABLAS MAESTRAS
private String nroprovincia(String provincia) {
	String salida = "";
	if (provincia.equals("Capital Federal"))
		salida +="38";
	if (provincia.equals("Buenos Aires"))
		salida +="39";
	if (provincia.equals("Catamarca"))
		salida +="40";
	if (provincia.equals("Cordoba"))
		salida +="41";
	if (provincia.equals("Corrientes"))
		salida +="42";
	if (provincia.equals("Chaco"))
		salida +="43";
	if (provincia.equals("Chubut"))
		salida +="44";
	if (provincia.equals("Entre Rios"))
		salida +="45";
	if (provincia.equals("Formosa"))
		salida +="46";
	if (provincia.equals("Jujuy"))
		salida +="47";
	if (provincia.equals("La Pampa"))
		salida +="48";
	if (provincia.equals("La Rioja"))
		salida +="49";
	if (provincia.equals("Mendoza"))
		salida +="50";
	if (provincia.equals("Misiones"))
		salida +="51";
	if (provincia.equals("Neuquen"))
		salida +="52";
	if (provincia.equals("Rio Negro"))
		salida +="53";
	if (provincia.equals("Salta"))
		salida +="54";
	if (provincia.equals("San Juan"))
		salida +="55";
	if (provincia.equals("San Luis"))
		salida +="56";
	if (provincia.equals("Santa Cruz"))
		salida +="57";
	if (provincia.equals("Santa Fe"))
		salida +="58";
	if (provincia.equals("Sgo. del Estero"))
		salida +="59";
	if (provincia.equals("Tucuman"))
		salida +="60";
	if (provincia.equals("Tierra del Fuego"))
		salida +="61";
	return salida;
}
private String nrotipodoc(String tipoDoc) {
	String salida = "";
	if (tipoDoc.contains("Desconoc."))
		salida +="1";
	if (tipoDoc.contains("Ci"))
		salida +="2";
	if (tipoDoc.contains("Dni"))
		salida +="3";
	if (tipoDoc.contains("Lc"))
		salida +="4";
	if (tipoDoc.contains("Le"))
		salida +="5";
	if (tipoDoc.contains("Pas"))
		salida +="6";
	if (tipoDoc.contains("Clu"))
		salida +="7";
	if (tipoDoc.contains("Otr"))
		salida +="8";

	return salida;
}
private String nronacionalidad(String nacionalidad) {
	String salida = "";
	if (nacionalidad.equals("HUN"))
		salida +="26";
	if (nacionalidad.equals("ESP"))
		salida +="27";
	if (nacionalidad.equals("MEX"))
		salida +="28";
	if (nacionalidad.equals("GER"))
		salida +="29";
	if (nacionalidad.equals("JAM"))
		salida +="30";
	if (nacionalidad.equals("SUI"))
		salida +="31";
	if (nacionalidad.equals("ISR"))
		salida +="32";
	if (nacionalidad.equals("NED"))
		salida +="33";
	if (nacionalidad.equals("ARG"))
		salida +="34";
	if (nacionalidad.equals("URU"))
		salida +="35";
	if (nacionalidad.equals("BRA"))
		salida +="36";
	if (nacionalidad.equals("ECU"))
		salida +="37";
	if (nacionalidad.equals("PAR"))
		salida +="38";
	if (nacionalidad.equals("PER"))
		salida +="39";
	if (nacionalidad.equals("USA"))
		salida +="40";
	if (nacionalidad.equals("GBR"))
		salida +="41";
	if (nacionalidad.equals("FRA"))
		salida +="42";
	if (nacionalidad.equals("CUB"))
		salida +="43";
	if (nacionalidad.equals("CHI"))
		salida +="44";
	if (nacionalidad.equals("BOL"))
		salida +="45";
	if (nacionalidad.equals("COL"))
		salida +="46";
	if (nacionalidad.equals("VEN"))
		salida +="47";
	if (nacionalidad.equals("ITA"))
		salida +="48";
	if (nacionalidad.equals("RUS"))
		salida +="49";
	if (nacionalidad.equals("CHN"))
		salida +="50";
	if (nacionalidad.equals("SLO"))
		salida +="51";
	if (nacionalidad.equals("AUT"))
		salida +="52";
	if (nacionalidad.equals("TTO"))
		salida +="53";
	if (nacionalidad.equals("SVK"))
		salida +="54";
	if (nacionalidad.equals("MAR"))
		salida +="55";
	return salida;
}
private String nroclub(String club) {
	String salida = "178";
	return salida;
}
private String federacion(String federacion) {
	String salida="";
	if (federacion.equals("FNC"))
		salida +='1';
	if (federacion.equals("FTC"))
		salida +='2';
	if (federacion.equals("FMT"))
		salida +='3';
	if (federacion.equals("AST"))
		salida +='4';
	if (federacion.equals("FMI"))
		salida +='5';
	if (federacion.equals("LTL"))
		salida +='6';
	if (federacion.equals("FSF"))
		salida +='7';
	if (federacion.equals("ACT"))
		salida +='8';
	if (federacion.equals("FBH"))
		salida +='9';
	if (federacion.equals("ATL"))
		salida +="10";
	if (federacion.equals("FCT"))
		salida +="11";
	if (federacion.equals("ATO"))
		salida +="12";
	if (federacion.equals("ATT"))
		salida +="13";
	if (federacion.equals("FCS"))
		salida +="14";
	if (federacion.equals("FET"))
		salida +="15";
	if (federacion.equals("FCH"))
		salida +="16";
	if (federacion.equals("FNT"))
		salida +="17";
	if (federacion.equals("NBA"))
		salida +="18";
	if (federacion.equals("FAT"))
		salida +="19";
	if (federacion.equals("AJT"))
		salida +="20";
	if (federacion.equals("FPT"))
		salida +="21";
	if (federacion.equals("FSC"))
		salida +="22";
	if (federacion.equals("ASG"))
		salida +="23";
	if (federacion.equals("FCO"))
		salida +="24";
	if (federacion.equals("FRT"))
		salida +="25";
	if (federacion.equals("FSJ"))
		salida +="26";
	if (federacion.equals("FSL"))
		salida +="27";
	if (federacion.equals("FFT"))
		salida +="28";
	if (federacion.equals("TCO"))
		salida +="29";
	if (federacion.equals("ARN"))
		salida +="30";
	if (federacion.equals("BAJ"))
		salida +="31";
	if (federacion.equals("FCA"))
		salida +="32";
	if (federacion.equals("BUE"))
		salida +="33";
	return salida;
}
private void ordenar(){
	//Ordeno los jugadores por ranking
		jugadores.sort(new Comparator<Jugador>(){
			@Override
			public int compare(Jugador j1, Jugador j2){
				return new Integer(j1.getRanking().compareTo(j2.getRanking()));
			}
		});
}
public void GetInscripciones()throws IOException, DocumentException{
	ordenar();
	CargarAcept();
	Iterator<Jugador> it = jugadores.iterator();
    Jugador jug;
    int resta = jugadores.size()-42;
    if (resta<0)
    	resta = 0;
	int hojas = (int) Math.floorDiv(resta,37);
	if ((resta%37) != 0)
		hojas++; //Indica cuantas hojas extras se necesitan
	String PDF = "Aceptaciones1.pdf";
	PdfReader reader;
	//primera hoja 42, 2da 37 (todos +67 menos 6,7,8,14,15,18,20,21,25,30,31,35 +1.. cartel g3 +30
	try {
		reader = new PdfReader("./Config/" + PDF);
	PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("./Aceptaciones/Aceptaciones_" + Grado + "_" + Nombre + ".pdf"));
	BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // Preclasificados
	BaseFont bbf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // Preclasificados
	//loop on pages (1-based)
	for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
    PdfContentByte over = stamper.getOverContent(i);

    over.beginText();

    int x = 12;
    int orden = 1;
    String nombre = "";
    over.setFontAndSize(bf, 16);
    over.setCMYKColorFill(255, 255, 0, 0);
    over.setTextMatrix(CoordPDF[0], CoordPDF[10]);
    over.showText(Grado + " - " + Nombre);
    over.setFontAndSize(bbf, 9);
    over.setCMYKColorFill(255, 255, 255, 255);
    over.setTextMatrix(CoordPDF[1], CoordPDF[11]);
    over.showText("Page 1 of " + (hojas+1));
    while (it.hasNext() && orden<43){
    	nombre = "";
    	jug = it.next();
    	if (orden < 10)
    		over.setTextMatrix(CoordPDF[2], CoordPDF[x]);
    	else
    		over.setTextMatrix(CoordPDF[2]-5, CoordPDF[x]);
    	over.showText("" + orden++); //Nro orden
        over.setTextMatrix(CoordPDF[3], CoordPDF[x]);
        if(!jug.getFederacion().equals("NO"))
        	over.showText(jug.getFederacion()); //Federacion
        over.setTextMatrix(CoordPDF[4], CoordPDF[x]);
        if(!jug.getNrodoc().equals("-1"))
        	over.showText(jug.getNrodoc()); // DNI
        over.setTextMatrix(CoordPDF[5], CoordPDF[x]);// Apellido, Nombre
        if ((jug.getApellido().length() + jug.getNombre().length()) > 19)
        	nombre += jug.getApellido() + ", " + jug.getNombre().charAt(0);
        else
        	nombre += jug.getApellido() + ", " + jug.getNombre();
        over.showText(nombre.toUpperCase());
        over.setTextMatrix(CoordPDF[6], CoordPDF[x]);
        if (jug.getFechaNac().length()==8)
        	over.showText(jug.getFechaNac().substring(6, 8) + "/" + jug.getFechaNac().substring(4, 6) + "/" + jug.getFechaNac().substring(0, 4));
        else
        	if(jug.getFechaNac().length()==7)
        		over.showText(jug.getFechaNac().substring(6, 7) + "/" + jug.getFechaNac().substring(4, 6) + "/" + jug.getFechaNac().substring(0, 4));
        if (jug.getRanking() == 9999)
        	x++;
        else{
        if (jug.getRanking()<10)
        	over.setTextMatrix(CoordPDF[7], CoordPDF[x++]);
        else if (jug.getRanking()<100)
        	over.setTextMatrix(CoordPDF[8], CoordPDF[x++]);
        	else over.setTextMatrix(CoordPDF[8], CoordPDF[x++]);
        over.showText("" + jug.getRanking());
        }
    }


            over.endText();
    }

    stamper.close();
	int j = 2;
	hojas += 2;//ordeno indices
    while (hojas != j)
    {
    	PDF = "Aceptaciones2.pdf";
    	reader = new PdfReader("./Config/" + PDF);
    	stamper = new PdfStamper(reader, new FileOutputStream("./Aceptaciones/Aceptaciones_" + Grado + "_" + Nombre + "hoja_" + j +".pdf"));
    	//loop on pages (1-based)
    	for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
    		PdfContentByte over = stamper.getOverContent(i);

    		over.beginText();
    int x = 12;
    int orden = 43;
    int adicion; // adicion otro pdf
    String nombre = "";
    over.setFontAndSize(bf, 16);
    over.setCMYKColorFill(255, 255, 0, 0);
    over.setTextMatrix(CoordPDF[0], CoordPDF[10]+30);
    over.showText(Grado + " - " + Nombre);
    over.setFontAndSize(bbf, 9);
    over.setCMYKColorFill(255, 255, 255, 255);
    over.setTextMatrix(CoordPDF[1], CoordPDF[11]);
    over.showText("Page " + j + " of " + (hojas-1));
    while (it.hasNext() && orden<(43+27*(j-1))){
    	adicion = 67;
    	nombre = "";
    	jug = it.next();
    	if (x == 16 || x == 17 || x == 18 || x == 19 || x == 20 || x == 22 || x == 23 || x == 24 || x == 29 || x == 31 || x == 32 || x == 36 || x == 41 || x == 42 || x == 46)
    		adicion++;
    	over.setTextMatrix(CoordPDF[2]-5, CoordPDF[x] + adicion);
        over.showText("" + orden++); //Nro orden
        over.setTextMatrix(CoordPDF[3], CoordPDF[x] + adicion);
        over.showText(jug.getFederacion()); //Federacion
        over.setTextMatrix(CoordPDF[4], CoordPDF[x] + adicion);
        over.showText(jug.getNrodoc()); // DNI
        over.setTextMatrix(CoordPDF[5], CoordPDF[x] + adicion);// Apellido, Nombre
        if ((jug.getApellido().length() + jug.getNombre().length()) > 19)
        	nombre += jug.getApellido() + ", " + jug.getNombre().charAt(0);
        else
        	nombre += jug.getApellido() + ", " + jug.getNombre();
        over.showText(nombre.toUpperCase());
        over.setTextMatrix(CoordPDF[6], CoordPDF[x] + adicion);
        over.showText(jug.getFechaNac().substring(6, 8) + "/" + jug.getFechaNac().substring(4, 6) + "/" + jug.getFechaNac().substring(0, 4));
        if (jug.getRanking() == 9999)
        	x++;
        else{
        if (jug.getRanking()<10)
        	over.setTextMatrix(CoordPDF[7], CoordPDF[x++] + adicion);
        else if (jug.getRanking()<100)
        	over.setTextMatrix(CoordPDF[8], CoordPDF[x++] + adicion);
        	else over.setTextMatrix(CoordPDF[8], CoordPDF[x++] + adicion);
        over.showText("" + jug.getRanking());
        }
    }
            over.endText();
    }

    stamper.close();
    j++;
    }

	} catch (IOException | DocumentException e) {
		e.printStackTrace();
	}	}

public void GetCuadro() throws IOException, DocumentException{

	int indice;
	int largo = 16;
	int x = 0; //delimito Coord X de Y
	int y = 0; //Comienza la info extra
	int s = 0; //Cant de seededs
	int l = 0; //Cant de LL y Replaicing
	String PDFIN;
	String PDFOUT;
	ordenar();
	if (jugadores.size()<4)
	{
		indice = 0;//Creo mensaje error (pocos jugadores)
	}
	else
		if (jugadores.size()<6)
		{
			if (Nombre.contains("Mujeres"))
				indice = 1;
			else
				if (Grado.equals("Grado 4"))
				indice = 1;
				else
					indice = 0;
		}
		else
			if(jugadores.size()<8)
				if(Nombre.contains("Mujeres") || Nombre.contains("Grado 4") || (Nombre.contains("Varones") && Nombre.contains("18")))
					indice = 2;
				else
					indice = 0;
			else
				if(jugadores.size()<=16) //Cuadro 16 o zonas
					indice = 3;
				else
					if (jugadores.size()<=32) //Cuadro de 32
						indice = 4;
					else indice = 5; //Cuadro de 64 o 32 con qualy
	switch (indice){
		case 0:	{ //Entrego el informe de Error
					String ruta = "./Cuadros/CategoriasNoRealizadas.txt";
					File archivo = new File(ruta);
					BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
					bw.write("El cuadro de la categoria " + Grado + " " + Nombre + " no ha sido creado debido a que la cantidad de jugadores es menor a la minima permitida.\nLa cantidad de jugadores inscriptos aceptados es de " + jugadores.size() +  "\n\n");
					bw.close();
					break;
		}
		case 1:{ // 1 zona de 4/5 jugadores
			PDFIN = "./Config/1zonade5.pdf";
			PDFOUT = "./Cuadros/" + Grado + "_" + Nombre + ".pdf";
			cargarZona(indice);
			Jugador[] orden = new Jugador[5];
			for (int i = 0; i<jugadores.size();i++){
				orden[i] = jugadores.get(i);
			}
			if (jugadores.size() == 4)
				orden[4] = null;
			orden[0].setEspreclasificado(true);
			orden[0].setPreclasific(1);
			preclasificados.add(orden[0]);
			armartxtadm(orden, PDFOUT);
			imprimirPDF(PDFIN,PDFOUT,orden,indice,x,y,s,l,largo, false);
			break;
		}
		case 2:{ // 6/7 jugadores 2 zonas, 2 preclasificados
			if (jugadores.size() == 6)
				PDFIN = "./Config/2zonasde3.pdf";
			else
				PDFIN = "./Config/2zonasde4.pdf";
			PDFOUT = "./Cuadros/" + Grado + "_" + Nombre + ".pdf";
			cargarZona(indice);
			preclasificados.add(jugadores.get(0));
			preclasificados.add(jugadores.get(1));
			jugadores.get(0).setEspreclasificado(true);
			jugadores.get(1).setEspreclasificado(true);
			jugadores.get(0).setPreclasific(1);
			jugadores.get(1).setPreclasific(2);
			Jugador[] orden = sortear(jugadores.size());
			armartxtadm(orden, PDFOUT);
			imprimirPDF(PDFIN,PDFOUT,orden,indice,x,y,s,l,largo, false);
			break;
		}
		case 3:{ // Cuadro 16 o zonas
			if (jugadores.size() == 8)
				PDFIN = "./Config/2zonasde4.pdf";
			else
				if(jugadores.size() == 9)
					PDFIN = "./Config/3zonasde3.pdf";
				else
					if(jugadores.size() == 10 || jugadores.size() == 11)
						PDFIN = "./Config/3zonasde4.pdf";
					else
						if (jugadores.size() == 12)
							PDFIN = "./Config/4zonasde3.pdf";
						else
							PDFIN = "./Config/4zonasde4.pdf";
			PDFOUT = "./Cuadros/" + Grado + "_" + Nombre + "_opcionZonas.pdf";
			cargarZona(indice);
			preclasificados.add(jugadores.get(0));
			preclasificados.add(jugadores.get(1));
			jugadores.get(0).setEspreclasificado(true);
			jugadores.get(0).setPreclasific(1);
			jugadores.get(1).setEspreclasificado(true);
			jugadores.get(1).setPreclasific(2);
			if (jugadores.size() > 8){
				preclasificados.add(jugadores.get(2));
				jugadores.get(2).setEspreclasificado(true);
				jugadores.get(2).setPreclasific(3);
				}
			if (jugadores.size() > 11){
				preclasificados.add(jugadores.get(3));
				jugadores.get(3).setEspreclasificado(true);
				jugadores.get(3).setPreclasific(4);
				}
			Jugador[] orden = sortear(jugadores.size());
			armartxtadm(orden, PDFOUT);
			imprimirPDF(PDFIN,PDFOUT,orden,indice,x,y,s,l,largo, false);
			PDFIN = "./Config/MainDrawS16.pdf";
			PDFOUT = "./Cuadros/" + Grado + "_" + Nombre + "_opcionCuadro16.pdf";
			orden = sortear(17);
			armartxtadm(orden, PDFOUT);
			cargarCuadro16();
			if (jugadores.size() == 8){
				preclasificados.add(jugadores.get(2));
				jugadores.get(2).setEspreclasificado(true);
				jugadores.get(2).setPreclasific(3);
			}
			if (jugadores.size() <= 11){
				preclasificados.add(jugadores.get(3));
				jugadores.get(3).setEspreclasificado(true);
				jugadores.get(3).setPreclasific(4);
			}
			x = 13;
			y = 40;
			s = 4;
			l = 2;
			largo = 13;
			imprimirPDF(PDFIN,PDFOUT,orden,indice,x,y,s,l,largo, true);
			break;
		}
		case 4:{ // cuadro 32
			PDFIN = "./Config/MainDrawS32.pdf";
			PDFOUT = "./Cuadros/" + Grado + "_" + Nombre + ".pdf";
			cargarCuadro32();
			preclasificados.add(jugadores.get(0));
			preclasificados.add(jugadores.get(1));
			preclasificados.add(jugadores.get(2));
			preclasificados.add(jugadores.get(3));
			preclasificados.add(jugadores.get(4));
			preclasificados.add(jugadores.get(5));
			preclasificados.add(jugadores.get(6));
			preclasificados.add(jugadores.get(7));
			jugadores.get(0).setEspreclasificado(true);
			jugadores.get(0).setPreclasific(1);
			jugadores.get(1).setEspreclasificado(true);
			jugadores.get(1).setPreclasific(2);
			jugadores.get(2).setEspreclasificado(true);
			jugadores.get(2).setPreclasific(3);
			jugadores.get(3).setEspreclasificado(true);
			jugadores.get(3).setPreclasific(4);
			jugadores.get(4).setEspreclasificado(true);
			jugadores.get(4).setPreclasific(5);
			jugadores.get(5).setEspreclasificado(true);
			jugadores.get(5).setPreclasific(6);
			jugadores.get(6).setEspreclasificado(true);
			jugadores.get(6).setPreclasific(7);
			jugadores.get(7).setEspreclasificado(true);
			jugadores.get(7).setPreclasific(8);
			x = 14;
			y = 77;
			s = 8;
			l = 4;
			largo = 15;
			Jugador[] orden = sortear(32);
			armartxtadm(orden, PDFOUT);
			imprimirPDF(PDFIN,PDFOUT,orden,indice,x,y,s,l,largo, true);
			break;
		}
		case 5:{ // cuadro 64 o mas -- Armar un repetitivo con cada uno de los subcuadros de 32.
			PDFIN = "./Config/MainDrawS32.pdf";
			PDFOUT = "./Cuadros/" + Grado + "_" + Nombre + ".pdf";
			Jugador[] orden = sortear (jugadores.size());
			cargarCuadro32();
			imprimirPDF(PDFIN,PDFOUT,orden,indice,x,y,s,l,largo, true);
			break;
		}

	}
}

private void armartxtadm(Jugador[] orden, String direcc) {//Creo archivo con Nombre, apellido jugador y DNI
	File archivo;
	Jugador jug = null;
	String direccion = "./Config/AATtxt/";
	for (int i=10;i<direcc.length()-3;i++)
		direccion += direcc.charAt(i);
	direccion += "txt";
	archivo = new File (direccion);
	try{
		FileWriter w = new FileWriter(archivo);
		BufferedWriter bw = new BufferedWriter(w);
		PrintWriter wr = new PrintWriter(bw);
		wr.write(Grado + " - " + Ciudad+ "\n");
		for (int j=0;j<orden.length;j++)
		{
			jug = orden[j];
			if (jug != null)
				wr.append(jug.getApellido() + ", " + jug.getNombre() + " - " + jug.getNrodoc() + "\n");
			else
				wr.append("BYE\n");
		}
		wr.close();

		bw.close();

		}catch(IOException e){};

}
private void imprimirPDF(String pDFIN, String pDFOUT, Jugador[] orden, int indice, int x, int y, int s, int l, int largo, boolean Cuadro) {
	// Si es indice 1, letra 13, indice 2 letra 11, indice 3 para 8 jug letra 11, para resto letra 9
	Calendar dia = new GregorianCalendar();
	Jugador jug;
	boolean bye = false; //Info para last direct
	Jugador menor = null;
	int rankingmen = 9999;
	String nombPDF;
	int desp = 0; //caso 32 jug, default
	if (indice == 3)
		desp = -1;
	int letra;
	try {
		PdfReader reader = new PdfReader(pDFIN);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(pDFOUT));
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // Preclasificados
		BaseFont bbf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED); // Preclasificados
		//loop on pages (1-based)
		for (int i=1; i<=reader.getNumberOfPages(); i++){    // VER LOOP PARA LOS JUGADORES Y UBICACIONES X Y
				PdfContentByte over = stamper.getOverContent(i);

				over.beginText();
				if (Cuadro)
				{
					for (int n = 0; n<(orden.length*2/3);n++) //Imprimo primera ronda
					{
						jug = orden[n];
						over.setFontAndSize(bf, 7);    // set font and size
						if (jug != null){
							over.setFontAndSize(bf, 7);    // set font and size
							if (!jug.getEstado().equals(""))
							{
								over.setTextMatrix(CoordPDF[0], CoordPDF[x]);
								over.showText(jug.getEstado());  // Estado
							}
							if (!(jug.getRanking() == 9999)){
								if (jug.getRanking()<10)
									over.setTextMatrix(CoordPDF[1]+7, CoordPDF[x]); //Ranking
								else
									if(jug.getRanking()<100)
										over.setTextMatrix(CoordPDF[1]+4, CoordPDF[x]); //Ranking
									else
										over.setTextMatrix(CoordPDF[1], CoordPDF[x]); //Ranking
								over.showText("" + jug.getRanking());
								if (rankingmen < jug.getRanking())
								{
									rankingmen = jug.getRanking();
									menor = jug;
								}
							}
							else {
								rankingmen = 9999;
								menor = jug;
							}
							over.setTextMatrix(CoordPDF[2], CoordPDF[x]); //Federacion
							over.showText(jug.getFederacion());
							if (jug.isEspreclasificado())
							{
								over.setFontAndSize(bbf, 7);
								if ((jug.getNombre().length() + jug.getApellido().length()) > largo)
									nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre().charAt(0) + "." + " [" + jug.getPreclasific() + "]");
								else
									nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre() + " [" + jug.getPreclasific() + "]");
							}
							else
								if ((jug.getNombre().length() + jug.getApellido().length()) > largo+4)
									nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre().charAt(0) + ".");
								else
									nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre());
							over.setTextMatrix(CoordPDF[3], CoordPDF[x++]); //Nombre
							over.showText(nombPDF);
						}
						else { //si es nulo, resulta que es un bye
							over.setTextMatrix(CoordPDF[3], CoordPDF[x++]); //Nombre
							over.showText("Bye");
							bye = true;
						}
					}
					//impresiones para cuadro si es que hay byes, en zonas no vale
					for (int n = orden.length*2/3; n<orden.length;n++)
					{
						jug = orden[n];
						over.setFontAndSize(bf, 7);
						if (jug != null)
						{
							if (jug.isEspreclasificado())
							{
								over.setFontAndSize(bbf, 7);
								if ((jug.getNombre().length() + jug.getApellido().length()) > largo)
									nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre().charAt(0) + "." + " [" + jug.getPreclasific() + "]");
								else
									nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre() + " [" + jug.getPreclasific() + "]");
							}
							else
								if ((jug.getNombre().length() + jug.getApellido().length()) > largo+4)
									nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre().charAt(0) + ".");
								else
									nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre());
							over.setTextMatrix(CoordPDF[4], CoordPDF[x++]); //Nombre
							over.showText(nombPDF);
						}
						else
							x++;
					}
					//Imprimo resto de la info (siempre igual en los cuadros)
					over.setFontAndSize(bf, 5);
					over.setTextMatrix(CoordPDF[8 + desp], CoordPDF[y]);
					over.showText(""+ dia.get(Calendar.DATE) + "/" + dia.get(Calendar.MONTH) + "/" + dia.get(Calendar.YEAR));
					over.setTextMatrix(CoordPDF[9 + desp], CoordPDF[y++]);
					over.showText(Ciudad);
					int p = s;
					while (s != 0){
						over.setTextMatrix(CoordPDF[10 + desp], CoordPDF[y++]);
						over.showText("" + preclasificados.get(p-s).getApellido() + " " + preclasificados.get(p-s).getNombre());
						s--;
					}
					//Queda para hacer los LL
					y = y + l;
					over.setTextMatrix(CoordPDF[13 + desp], CoordPDF[y++]);   // Last Direct
					if (bye)
						over.showText("Bye");
					else
						over.showText("" + menor.getApellido() + " " + menor.getNombre());
					over.setTextMatrix(CoordPDF[13 + desp]-2, CoordPDF[y]);   // Director
					over.showText(Profesor);

				}
				else // es zona
				{
					int p = 0;
					if(indice == 1)
						{
						letra = 8; largo = 20;
						}
					else
						if (indice == 2 || ((indice ==3) && (jugadores.size() == 8)))
							{
							letra = 9;
							largo = 18;
							}
						else
							{
							letra = 9;
							if (jugadores.size()==9)
								largo = 24;
							else
								if (jugadores.size()==10 || jugadores.size()==11)
									largo = 15;
								else
									if (jugadores.size()==12)
										largo = 26;
									else
										largo = 23;
							}
					for (int n = 0; n<(orden.length);n++) //Imprimo jugadores
					{
						p = n;
						jug = orden[n];
						over.setFontAndSize(bf, letra);    // set font and size
						if (jug != null){
							over.setFontAndSize(bf, letra);    // set font and size
							if (jug.isEspreclasificado())
							{
								over.setTextMatrix(CoordPDF[0], CoordPDF[n + 5]);
								over.showText("" + jug.getPreclasific());  // Siembra
							}
							if (!(jug.getRanking() == 9999)){
								over.setTextMatrix(CoordPDF[1], CoordPDF[n + 5]); //Ranking
								over.showText("" + jug.getRanking());
							}
							if (jug.isEspreclasificado())
								over.setFontAndSize(bbf, letra);
							if ((jug.getNombre().length() + jug.getApellido().length()) > largo)
								nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre().charAt(0) + ".");
							else
								nombPDF = ("" + jug.getApellido() + ", " + jug.getNombre());over.setTextMatrix(CoordPDF[2], CoordPDF[n + 5]); //Nombre
							over.showText(nombPDF);
						}
						else { //si es nulo, resulta que es un bye
							over.setTextMatrix(CoordPDF[2], CoordPDF[n + 5]); //Nombre
							over.showText("Bye");
						}
					}
				//Imprimo resto de la info (siempre igual en los cuadros)
				over.setFontAndSize(bf, letra);
				over.setTextMatrix(CoordPDF[3], CoordPDF[p + 6]);   // Ciudad
				over.showText(Ciudad);
				over.setTextMatrix(CoordPDF[4], CoordPDF[p + 6]); // Grado
				over.showText(Grado);
				over.setTextMatrix(CoordPDF[4], CoordPDF[p + 7]);   // Fecha
				over.showText(""+ dia.get(Calendar.DATE) + "/" + (dia.get(Calendar.MONTH)+1) + "/" + dia.get(Calendar.YEAR));

				}
				over.endText();
		}
		stamper.close();
		reader.close();

	} catch (IOException | DocumentException e) {
		e.printStackTrace();
	}  //Entrego el file del cuadro o donde se guarda dicho pdf
}

private int Limitar(Jugador j) {//Limitaciones de ranking y categorias
	int edad = 0;
	int resultado = 0;
	 /* G3--------
	 * misma categoria (excepto sub 18 mujeres) no pueden jugar top 20,
	 * en sub 10 no pueden jugar sub 12 top 20
	 *
	 * G4------------
	 * en sub 18 varones no pueden top 100
	 * en 12 14 y 16 varones no pueden top 260
	 * en mujeres no pueden top 60
	 * G1 G2 y G4 no pueden sin permiso aat
	 * */
	Date fecha = new Date();
	@SuppressWarnings("deprecation")
	int anio = fecha.getYear()+1900; //Considera el ano actual - 1900
	if(!j.getFechaNac().equals(""))
		edad = anio - Integer.parseInt(j.getFechaNac().substring(0, 4));
	if (edad == 0)
		resultado = 3;
	//Edad con la que comienza el anio
	if (Nombre.equals("Sub 10(Varones)") || Nombre.equals("Sub 10(Mujeres)"))
		if (edad > 10)
			resultado = 1;
	if (Nombre.equals("Sub 12(Varones)") || Nombre.equals("Sub 12(Mujeres)"))
		if (edad > 12)
			resultado = 1;
	if (Nombre.equals("Sub 14(Varones)") || Nombre.equals("Sub 14(Mujeres)"))
		if (edad > 14)
			resultado = 1;
	if (Nombre.equals("Sub 16(Varones)") || Nombre.equals("Sub 16(Mujeres)"))
		if (edad > 16)
			resultado = 1;
	if (Nombre.equals("Sub 18(Varones)") || Nombre.equals("Sub 18(Mujeres)"))
		if (edad > 19)
			resultado = 1;

	if (resultado == 0) // No tiene problemas de edad
	{
		if (Grado.equals("Grado 4")){
			if (esdelacategoria(j)){
				if(!antesG1)
				{
					if(Nombre.equals("Sub 18(Varones)") && j.getRanking()<=100)
						resultado = 2;
					else
						if((Nombre.equals("Sub 12(Varones)") || Nombre.equals("Sub 14(Varones)") || Nombre.equals("Sub 16(Varones)")) &&  j.getRanking()<=100)
							resultado = 2;
						else
							if(Nombre.contains("Mujeres") && j.getRanking() <=60)
								resultado = 2;
				}
			}
			else
				resultado = 4;
		}
		else{
			if(Grado.equals("Grado 3"))
			{
				if(esdelacategoria(j) && !antesG1){
					if(!(Nombre.equals("Sub 18(Mujeres)")) && j.getRanking() <= 20)
						resultado = 2;
				}
			}
		}
	}
	return resultado;
}
private void CargarAcept() {//Cargo coordenadas PDF aceptaciones
			//Coordenadas en X, misma en pag 1 y 2
			CoordPDF[0] = 140; //Titulo
			CoordPDF[1] = 293; //Page
			CoordPDF[2] = 25; //Nro orden
			CoordPDF[3] = 37; //Federacion
			CoordPDF[4] = 65; //DNI
			CoordPDF[5] = 112; //Nombre
			CoordPDF[6] = 235; //Fecha nac
			CoordPDF[7] = 299; //Ranking <10
			CoordPDF[8] = 296;	//Ranking <100
			CoordPDF[9] = 293;	//Ranking <1000
			//Coordenadas Y,
			//primera hoja 42, 2da 37 (todos +67 menos 6,7,8,14,15,18,20,21,25,30,31,35 +1.. cartel g3 +30
			CoordPDF[10] = 780; //Titulo
			CoordPDF[11] = 59; //Pagina
			CoordPDF[12] = 699; //1
			CoordPDF[13] = 683;	//2
			CoordPDF[14] = 669; //3
			CoordPDF[15] = 654; //4
			CoordPDF[16] = 639; //5
			CoordPDF[17] = 625; //6
			CoordPDF[18] = 610; //7
			CoordPDF[19] = 596; //8
			CoordPDF[20] = 581; //9
			CoordPDF[21] = 567; //10
			CoordPDF[22] = 552; //11
			CoordPDF[23] = 537; //12
			CoordPDF[24] = 523; //13
			CoordPDF[25] = 508; //14
			CoordPDF[26] = 493; //15
			CoordPDF[27] = 479; //16
			CoordPDF[28] = 464; //17
			CoordPDF[29] = 449; //18
			CoordPDF[30] = 435; //19
			CoordPDF[31] = 420; //20
			CoordPDF[32] = 405; //21
			CoordPDF[33] = 391; //22
			CoordPDF[34] = 377; //23
			CoordPDF[35] = 362; //24
			CoordPDF[36] = 347; //25
			CoordPDF[37] = 333; //26
			CoordPDF[38] = 318; //27
			CoordPDF[39] = 303; //28
			CoordPDF[40] = 289; //29
			CoordPDF[41] = 274; //30
			CoordPDF[42] = 259; //31
			CoordPDF[43] = 245; //32
			CoordPDF[44] = 230; //33
			CoordPDF[45] = 216; //34
			CoordPDF[46] = 201; //35
			CoordPDF[47] = 187; //36
			CoordPDF[48] = 172; //37
			CoordPDF[49] = 157; //38
			CoordPDF[50] = 143; //39
			CoordPDF[51] = 128; //40
			CoordPDF[52] = 114; //41
			CoordPDF[53] = 99;  //42

}
private void cargarCuadro32() {//ARMO COORDS CUADRO 32, VER TEMA Q y CUADRO 64/128!!!
	//Ver tema cuadro >32
	if (jugadores.size()<=32)
		/* Coord X de izq a derecha (Estado, Rank, Federacion, Primera ronda, cuartos, semi y final,
		 *  semana, ciudad, seededs, LL y Replacing, Last direct y signature)
		 *  Luego Coord Y arriba a abajo (primera ronda, cuartos, semis, final y campeon,ciudad/fecha, seedes, LL/Replaicing,
		 *  Last direct y signature)
		 */

		//Coordenadas en X
		CoordPDF[0] = 80; //Estado
		CoordPDF[1] = 95; //Rank
		CoordPDF[2] = 114; //Federacion
		CoordPDF[3] = 133; //Nombre 1ra ronda
		CoordPDF[4] = 208; //2da ronda, resultados +2
		CoordPDF[5] = 283; //cuartos, resultados +2
		CoordPDF[6] = 359; //semis, resultados +2
		CoordPDF[7] = 434; //Final y campeon, resultados +2
		CoordPDF[8] = 62;  //Semana
		CoordPDF[9] = 121; //Ciudad
		CoordPDF[10] = 124; //Preclasificados
		CoordPDF[11] = 303;//Lucky Loosers
		CoordPDF[12] = 355;//Replacing
		CoordPDF[13] = 420;//Last Direct y Signature +1

		//Coordenadas Y resultado score a partir de 2da ronda Y-9
		//Primera ronda
		CoordPDF[14] = 618;//1
		CoordPDF[15] = 603;//2
		CoordPDF[16] = 587;//3
		CoordPDF[17] = 572;//4
		CoordPDF[18] = 557;//5
		CoordPDF[19] = 541;//6
		CoordPDF[20] = 525;//7
		CoordPDF[21] = 510;//8
		CoordPDF[22] = 495;//9
		CoordPDF[23] = 479;//10
		CoordPDF[24] = 463;//11
		CoordPDF[25] = 448;//12
		CoordPDF[26] = 433;//13
		CoordPDF[27] = 417;//14
		CoordPDF[28] = 401;//15
		CoordPDF[29] = 386;//16
		CoordPDF[30] = 371;//17
		CoordPDF[31] = 356;//18
		CoordPDF[32] = 340;//19
		CoordPDF[33] = 324;//20
		CoordPDF[34] = 309;//21
		CoordPDF[35] = 294;//22
		CoordPDF[36] = 278;//23
		CoordPDF[37] = 262;//24
		CoordPDF[38] = 247;//25
		CoordPDF[39] = 232;//26
		CoordPDF[40] = 216;//27
		CoordPDF[41] = 201;//28
		CoordPDF[42] = 185;//29
		CoordPDF[43] = 170;//30
		CoordPDF[44] = 154;//31
		CoordPDF[45] = 138;//32

		//2da ronda
		CoordPDF[46] = 610;//1
		CoordPDF[47] = 579;//2
		CoordPDF[48] = 548;//3
		CoordPDF[49] = 518;//4
		CoordPDF[50] = 487;//5
		CoordPDF[51] = 456;//6
		CoordPDF[52] = 425;//7
		CoordPDF[53] = 394;//8
		CoordPDF[54] = 363;//9
		CoordPDF[55] = 332;//10
		CoordPDF[56] = 301;//11
		CoordPDF[57] = 270;//12
		CoordPDF[58] = 239;//13
		CoordPDF[59] = 208;//14
		CoordPDF[60] = 177;//15
		CoordPDF[61] = 146;//16

	    //cuartos
		CoordPDF[62] = 595;//1
		CoordPDF[63] = 533;//2
		CoordPDF[64] = 471;//3
		CoordPDF[65] = 409;//4
		CoordPDF[66] = 348;//5
		CoordPDF[67] = 286;//6
		CoordPDF[68] = 224;//7
		CoordPDF[69] = 162;//8

		//semis
		CoordPDF[70] = 564;//1
		CoordPDF[71] = 440;//2
		CoordPDF[72] = 316;//3
		CoordPDF[73] = 192;//4

		//Final
		CoordPDF[74] = 502;//1
		CoordPDF[75] = 254;//2

		//Campeon
		CoordPDF[76] = 379;//1

		//Fecha y Lugar
		CoordPDF[77] = 652;//Misma

		//Seeded players
		CoordPDF[78] = 106;//1
		CoordPDF[79] = 99;//2
		CoordPDF[80] = 91;//3
		CoordPDF[81] = 84;//4
		CoordPDF[82] = 77;//5
		CoordPDF[83] = 70;//6
		CoordPDF[84] = 63;//7
		CoordPDF[85] = 56;//8

		//LL y replacing
		CoordPDF[86] = 105;//1
		CoordPDF[87] = 96;//2
		CoordPDF[88] = 87;//3
		CoordPDF[89] = 78;//4
		//Last Direct
		CoordPDF[90] = 100;

		//Signature
		CoordPDF[91] = 63;
	}
private void cargarCuadro16() {//ARMO COORDS CUADRO 16
	/* Coord X de izq a derecha (Estado, Rank, Federacion, Primera ronda, cuartos, semi y final,
	 *  semana, ciudad, seededs, LL y Replacing, Last direct y signature)
	 *  Luego Coord Y arriba a abajo (primera ronda, cuartos, semis, final y campeon,ciudad/fecha, seedes, LL/Replaicing,
	 *  Last direct y signature)
	 */

	//Coordenadas en X
	CoordPDF[0] = 100; //Estado
	CoordPDF[1] = 114; //Rank
	CoordPDF[2] = 133; //Federacion
	CoordPDF[3] = 150; //Nombre 1ra ronda
	CoordPDF[4] = 219; //Cuartos, resultados +2
	CoordPDF[5] = 287; //Semis, resultados +2
	CoordPDF[6] = 355; //Final y campeon es -4, resultados +2, result final -2
	CoordPDF[7] = 85;  //Semana
	CoordPDF[8] = 140; //Ciudad
	CoordPDF[9] = 145; //Preclasificados
	CoordPDF[10] = 300;//Lucky Loosers
	CoordPDF[11] = 353;//Replacing
	CoordPDF[12] = 410;//Last Direct y Signature -2

	//Coordenadas Y resultado score a partir de 2da ronda Y-9
	//Primera ronda
	CoordPDF[13] = 596;//1
	CoordPDF[14] = 568;//2
	CoordPDF[15] = 540;//3
	CoordPDF[16] = 512;//4
	CoordPDF[17] = 485;//5
	CoordPDF[18] = 456;//6
	CoordPDF[19] = 429;//7
	CoordPDF[20] = 401;//8
	CoordPDF[21] = 373;//9
	CoordPDF[22] = 345;//10
	CoordPDF[23] = 318;//11
	CoordPDF[24] = 289;//12
	CoordPDF[25] = 262;//13
	CoordPDF[26] = 234;//14
	CoordPDF[27] = 206;//15
	CoordPDF[28] = 178;//16
    //2da ronda
	CoordPDF[29] = 582;//1
	CoordPDF[30] = 526;//2
	CoordPDF[31] = 470;//3
	CoordPDF[32] = 414;//4
	CoordPDF[33] = 358;//5
	CoordPDF[34] = 303;//6
	CoordPDF[35] = 247;//7
	CoordPDF[36] = 192;//8
	//semis
	CoordPDF[37] = 554;//1
	CoordPDF[38] = 443;//2
	CoordPDF[39] = 331;//3
	CoordPDF[40] = 220;//4
	//Final
	CoordPDF[37] = 499;//1
	CoordPDF[38] = 276;//2
	//Campeon
	CoordPDF[39] = 387;//1
	//Fecha y Lugar
	CoordPDF[40] = 626;//Misma
	//Seeded players
	CoordPDF[41] = 135;//1
	CoordPDF[42] = 128;//2
	CoordPDF[43] = 121;//3
	CoordPDF[44] = 115;//4
	//LL y replacing
	CoordPDF[45] = 135;//1
	CoordPDF[46] = 129;//2
	//Last Direct
	CoordPDF[47] = 129;
	//Signature
	CoordPDF[48] = 115;
}

private void cargarZona(int i) {
	switch (i){
	case 1:
	{
		/* Coord X de izq a derecha (Siembra, Rank, Nombre jugador, ciudad, fecha/grado)
		 *  Luego Coord Y arriba a abajo (los jugadores y despues ciudad/grado y fecha
		 */

		//Coordenadas en X
		CoordPDF[0] = 71; //Siembra
		CoordPDF[1] = 102; //Rank
		CoordPDF[2] = 135; //Nombre
		CoordPDF[3] = 215; //Ciudad
		CoordPDF[4] = 430; //Fecha y grado

		//Coordenadas en Y
		CoordPDF[5] = 500; //1
		CoordPDF[6] = 483; //2
		CoordPDF[7] = 465; //3
		CoordPDF[8] = 449; //4
		CoordPDF[9] = 431; //5
		CoordPDF[10] = 588;//Ciudad y grado
		CoordPDF[11] = 563;//Fecha
		break;
	}
	case 2:
	{
		if (jugadores.size() == 6)//2 zonas de 3
		{
			//Coordenadas en X
			CoordPDF[0] = 71; //Siembra
			CoordPDF[1] = 102; //Rank
			CoordPDF[2] = 150; //Nombre
			CoordPDF[3] = 215; //Ciudad
			CoordPDF[4] = 430; //Fecha y grado

			//Coordenadas en Y
			CoordPDF[5] = 574; //1
			CoordPDF[6] = 550; //2
			CoordPDF[7] = 528; //3
			CoordPDF[8] = 465; //4
			CoordPDF[9] = 443; //5
			CoordPDF[10] = 420;//6
			CoordPDF[11] = 670;//Ciudad y grado
			CoordPDF[12] = 644;//Fecha
		}
		else //2 zonas de 4 y 1 bye
		{
			//Coordenadas en X
			CoordPDF[0] = 98; //Siembra
			CoordPDF[1] = 127; //Rank
			CoordPDF[2] = 175; //Nombre
			CoordPDF[3] = 230; //Ciudad
			CoordPDF[4] = 450; //Fecha y grado

			//Coordenadas en Y
			CoordPDF[5] = 582; //1
			CoordPDF[6] = 566; //2
			CoordPDF[7] = 550; //3
			CoordPDF[8] = 533; //4
			CoordPDF[9] = 477; //5
			CoordPDF[10] = 461;//6
			CoordPDF[11] = 444;//7
			CoordPDF[12] = 428;//8
			CoordPDF[13] = 669;//Ciudad y grado
			CoordPDF[14] = 642;//Fecha
		}
		break;
	}
	case 3:
	{
		if (jugadores.size() == 8) // 2 zonas de 4
		{
			//Coordenadas en X
			CoordPDF[0] = 98; //Siembra
			CoordPDF[1] = 127; //Rank
			CoordPDF[2] = 175; //Nombre
			CoordPDF[3] = 232; //Ciudad
			CoordPDF[4] = 450; //Fecha y grado

			//Coordenadas en Y
			CoordPDF[5] = 582; //1
			CoordPDF[6] = 566; //2
			CoordPDF[7] = 550; //3
			CoordPDF[8] = 533; //4
			CoordPDF[9] = 477; //5
			CoordPDF[10] = 461;//6
			CoordPDF[11] = 444;//7
			CoordPDF[12] = 428;//8
			CoordPDF[13] = 669;//Ciudad y grado
			CoordPDF[14] = 642;//Fecha
		}
		else
			if(jugadores.size() == 9) // 3 zonas de 3
			{
				//Coordenadas en X
				CoordPDF[0] = 82; //Siembra
				CoordPDF[1] = 110; //Rank
				CoordPDF[2] = 160; //Nombre
				CoordPDF[3] = 216; //Ciudad
				CoordPDF[4] = 415; //Fecha y grado

				//Coordenadas en Y
				CoordPDF[5] = 570; //1
				CoordPDF[6] = 549; //2
				CoordPDF[7] = 527; //3
				CoordPDF[8] = 470; //4
				CoordPDF[9] = 449; //5
				CoordPDF[10] = 427;//6
				CoordPDF[11] = 370;//7
				CoordPDF[12] = 348 ;//8
				CoordPDF[13] = 326 ;//9
				CoordPDF[14] = 657;//Ciudad y grado
				CoordPDF[15] = 635;//Fecha
			}
			else
				if(jugadores.size() == 10 || jugadores.size() == 11) //3 zonas de 4
				{
					//Coordenadas en X
					CoordPDF[0] = 82; //Siembra
					CoordPDF[1] = 110; //Rank
					CoordPDF[2] = 150; //Nombre
					CoordPDF[3] = 210; //Ciudad
					CoordPDF[4] = 423; //Fecha y grado

					//Coordenadas en Y
					CoordPDF[5] = 574; //1
					CoordPDF[6] = 559; //2
					CoordPDF[7] = 543; //3
					CoordPDF[8] = 528; //4
					CoordPDF[9] = 478; //5
					CoordPDF[10] = 462;//6
					CoordPDF[11] = 447;//7
					CoordPDF[12] = 431;//8
					CoordPDF[13] = 373;//9
					CoordPDF[14] = 357;//10
					CoordPDF[15] = 341;//11
					CoordPDF[16] = 326;//12
					CoordPDF[17] = 657;//Ciudad y grado
					CoordPDF[18] = 635;//Fecha
				}
				else
					if (jugadores.size() == 12)// 4 zonas de 3
					{
						//Coordenadas en X
						CoordPDF[0] = 74; //Siembra
						CoordPDF[1] = 102; //Rank
						CoordPDF[2] = 150; //Nombre
						CoordPDF[3] = 210; //Ciudad
						CoordPDF[4] = 423; //Fecha y grado

						//Coordenadas en Y
						CoordPDF[5] = 576; //1
						CoordPDF[6] = 554; //2
						CoordPDF[7] = 532; //3
						CoordPDF[8] = 475; //4
						CoordPDF[9] = 454; //5
						CoordPDF[10] = 432;//6
						CoordPDF[11] = 375;//7
						CoordPDF[12] = 353;//8
						CoordPDF[13] = 331;//9
						CoordPDF[14] = 268;//10
						CoordPDF[15] = 246;//11
						CoordPDF[16] = 225;//12
						CoordPDF[17] = 663;//Ciudad y grado
						CoordPDF[18] = 639;//Fecha
					}
					else
					{
						//Coordenadas en X
						CoordPDF[0] = 80; //Siembra
						CoordPDF[1] = 108; //Rank
						CoordPDF[2] = 158; //Nombre
						CoordPDF[3] = 215; //Ciudad
						CoordPDF[4] = 420; //Fecha y grado

						//Coordenadas en Y
						CoordPDF[5] = 576; //1
						CoordPDF[6] = 560; //2
						CoordPDF[7] = 544; //3
						CoordPDF[8] = 529; //4
						CoordPDF[9] = 481; //5
						CoordPDF[10] = 465;//6
						CoordPDF[11] = 450;//7
						CoordPDF[12] = 434;//8
						CoordPDF[13] = 381;//9
						CoordPDF[14] = 365;//10
						CoordPDF[15] = 349;//11
						CoordPDF[16] = 334;//12
						CoordPDF[17] = 273;//13
						CoordPDF[18] = 257;//14
						CoordPDF[19] = 241;//15
						CoordPDF[20] = 226;//16
						CoordPDF[21] = 663;//Ciudad y grado
						CoordPDF[22] = 640;//Fecha
					}
		break;
	}
	}
}
private int BuscarRank(String nro) {
	 int rank = 9999;
	 boolean encontre = false;
	 FileReader fr = null;
     BufferedReader br = null;
     String rankingtxt = "./Rankings/" + Nombre + ".txt";
 	 File f;
     f = new File (rankingtxt);
     try {
    	 fr = new FileReader (f);
    	 br = new BufferedReader(fr);

        // Lectura del fichero
        String linea = "";
       if(!nro.equals("-1"))
        	while((!encontre && (linea=br.readLine())!=null)){
        		if (linea.contains(nro))
        			encontre = true;

        	}
        if(encontre){
        	int y = 0;
        	String rankS = "";
        	int barras = 0;
        	int espacios = 0;
        	/*ESTO ES PARA LOS RANKINGS VIEJOS, VER PROXIMA SALIDA DE RANKINGS SI MANTIENE ESTRUCTURA NUEVA
        	 * while (barras != 2){
        		if (linea.charAt(y) == '/')
        			barras++;
        		y++;
        	}
        	while (espacios != 7){
        		if (linea.charAt(y) == ' ')
        			espacios++;
        		y++;
        	}
        	while (espacios != 8)
        	{
        		if (linea.charAt(y) == ' ')
        			espacios++;
        		else
        			if(linea.charAt(y) != '.')
        				rankS += linea.charAt(y);
        		y++;
        	}*/
        	//PARA ORDENAMIENTO
        	while (espacios != 1)
        	{
        		if (linea.charAt(y) == ' ')
        			espacios++;
        		else
        			if(linea.charAt(y) != '.')
        				rankS += linea.charAt(y);
        		y++;
        	}
        	rank = Integer.parseInt(rankS);
        }
     }
     catch(Exception e){
        e.printStackTrace();
     }finally{
        // En el finally cerramos el fichero, para asegurarnos
        // que se cierra tanto si todo va bien como si salta
        // una excepcion.
        try{
           if( null != fr ){
              fr.close();
           }
        }catch (Exception e2){
           e2.printStackTrace();
        }
     }
	 return rank;
}
private void CrearTXTdePDF() {
	LeerPDF Lector = new LeerPDF();
	String direccion = "./Rankings/";
	int x = 0;
	boolean varones = Nombre.contains("Varones");
	int cat = 0;
	if (Nombre.contains("10"))
		cat = 10;
	if (Nombre.contains("12"))
		cat = 12;
	if (Nombre.contains("14"))
		cat = 14;
	if (Nombre.contains("16"))
		cat = 16;
	if (Nombre.contains("18"))
		cat = 18;

	boolean encontre = false;
	File dir = new File("./Rankings");
	String[] ficheros = dir.list();
	if (ficheros != null)
	{
		while (!encontre && x<ficheros.length)
		{
			if (varones){
				if (ficheros[x].contains("varones") && ficheros[x].contains(""+ cat) && ficheros[x].contains(".pdf"))
				{
					direccion += ficheros[x];
					encontre = true;
				}}
			else
				if (ficheros[x].contains("mujeres") && ficheros[x].contains(""+ cat) && ficheros[x].contains(".pdf"))
				{
					direccion += ficheros[x];
					encontre = true;
				}
			x++;
		}
	}
	if (encontre)
		Lector.pdftoText(direccion, Nombre);
}
private Jugador[] sortear(int i){// A partir de la cantidad de jugadores armo cabezas de serie y partidos.. HACER!!!!
/*
 * Varones.........................
 * de 16 a 32 jugadores, cuadro 32 normal y dobles cuadro 16
 * del minimo hasta 16 puede ser con zonas y dobles cuadro de 8
 * Zonas: Misma cantidad de preclasificados que de zonas, sembrados ordenados por zonas y luego sorteo (luego del sorteo ordeno de forma descendente)
 * byes menor cantidad que zonas, max un bye por zona (ultima linea).
 * no se pueden misma federacion (club si es posible).
 * 16 jugadores 4 zonas: 4 zonas de 4
 * 15 jugadores 4 zonas: 4 zonas de 4 (1 bye)
 * 14 jugadores 4 zonas: 4 zonas de 4 (2 byes)
 * 13 jugadores 4 zonas: 4 zonas de 4 (3 byes)
 * 12 jugadores 4 zonas: 4 zonas de 3
 * 11 jugadores 3 zonas: 3 zonas de 4 (1 bye)
 * 10 jugadores 3 zonas: 3 zonas de 4 (2 byes)
 * 9 jugadores 3 zonas: 3 zonas de 3
 * 8 jugadores 2 zonas: 2 zonas de 4
 * 7 jugadoras 2 zonas: 2 zonas de 4 (1 bye)
 * 6 jugadoras 2 zonas: 2 zonas de 3
 *
 * Mujeres.......................
 * 16 o menos --> Mismo formato zonas varones
 * 16 a 32 --> cuadro 32 sin qualy y con ronda consuelo(entro cuadro vacio, solo si hay 18 jugadoras o mas)
 *
 */
	Jugador[] orden = null;
	boolean ubique = false;
	boolean[] ubicados = null;
	int r1;
	int byes;
	Random r = new Random(System.currentTimeMillis());
 	if (i == 32){
 		orden = new Jugador[48]; //32 primera ronda + 16 2da ronda (jugadores que pasaron los byes)
 		for (int l=0;l<32;l++)
 			orden[l] = null;
 		ubicados = new boolean[32];
 		for (int l=0;l<32;l++)
 			ubicados[l] = false;
 		byes = 32 - jugadores.size();
 		orden[0] = jugadores.get(0);
 		ubicados[0] = true;
 		orden[31] = jugadores.get(1);
 		ubicados[31] = true;
 		if (r.nextInt(2) == 0){
 			orden[8] = jugadores.get(2);
 			ubicados[8] = true;
 			orden[23] = jugadores.get(3);
 			ubicados[23] = true;
 		}
 		else {
 			orden[8] = jugadores.get(3);
 			ubicados[8] = true;
 			orden[23] = jugadores.get(2);
 			ubicados[23] = true;
 		}
 		for (int j = 4; j<8;j++){
 			ubique = false;
 			while (!ubique){
 				r1 = r.nextInt(4);
 				if (r1 == 0)
 					if (ubicados[7] == false){
 						orden[7] = jugadores.get(j);
 						ubique = true;
 						ubicados[7] = true;
 					}
 				if (r1 == 1)
 					if (ubicados[15] == false){
 						orden[15] = jugadores.get(j);
 						ubique = true;
 						ubicados[15] = true;
 					}
 				if (r1 == 2)
 					if (ubicados[16] == false){
 						orden[16] = jugadores.get(j);
 						ubique = true;
 						ubicados[16] = true;
 					}
 				if (r1 == 3)
 					if (ubicados[24] == false){
 						orden[24] = jugadores.get(j);
 						ubique = true;
 						ubicados[24] = true;
 					}
 			}
 		}
 			//Termine de ubicar los preclasficiados
 			//ubico los byes, los primeros 8 van con los preclasif
 			if (byes > 0){
 				ubicados[1] = true;
 				byes--;
 			}
 			if (byes > 0){
 				ubicados[30] = true;
 				byes--;
 			}
 			if (byes > 0){
 				ubicados[9] = true;
 				byes--;
 			}
 			if (byes > 0){
 				ubicados[22] = true;
 				byes--;
 			}
 			if (byes > 0){
 				ubicados[6] = true;
 				byes--;
 			}
 			if (byes > 0){
 				ubicados[25] = true;
 				byes--;
 			}
 			if (byes > 0){
 				ubicados[14] = true;
 				byes--;
 			}
 			if (byes > 0){
 				ubicados[17] = true;
 				byes--;
 			}
 			while(byes>1){
 				ubique = false;
 				while(!ubique){
 					r1 = r.nextInt(16);
 					if (ubicados[r1] == false)
 						if(r1%2 == 0){
 							if(ubicados[r1+1] == false || orden[r1+1] != null){
 								ubicados [r1] = true;
 								byes--;
 								ubique = true;
 							}
 						}
 						else
 							if(ubicados[r1-1] == false || orden[r1-1] != null){
 								ubicados [r1] = true;
 								byes--;
 								ubique = true;
 							}
 				}
 				ubique = false;
 				while(!ubique){
 					r1 = r.nextInt(16)+16;
 					if (ubicados[r1] == false)
 						if(r1%2 == 0){
 							if(ubicados[r1+1] == false || orden[r1+1] != null){
 								ubicados [r1] = true;
 								byes--;
 								ubique = true;
 							}
 						}
 						else
 							if(ubicados[r1-1] == false || orden[r1-1] != null){
 								ubicados [r1] = true;
 								byes--;
 								ubique = true;
 							}
 				}
 			}
 			if (byes == 1)
 				while(!ubique){
 					r1 = r.nextInt(32);
 					if (ubicados[r1] == false)
 						if(r1%2 == 0){
 							if(ubicados[r1+1] == false || orden[r1+1] != null){
 								ubicados [r1] = true;
 								byes--;
 								ubique = true;
 							}
 						}
 						else
 							if(ubicados[r1-1] == false || orden[r1-1] != null){
 								ubicados [r1] = true;
 								byes--;
 								ubique = true;
 							}
 				}
 			//termine de ubicar los byes
 			//ubico el resto de jugadores
 			for (int t = 8; t<jugadores.size();t++){
 				ubique = false;
 				int errores = 0;
 				while(!ubique){
 					r1 = r.nextInt(32);
 					if (ubicados[r1] == false)
 						if(r1%2 == 0){
 							if(ubicados[r1+1] == false || orden[r1+1] == null || !(orden[r1+1].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 								ubicados [r1] = true;
 								orden[r1] = jugadores.get(t);
 								ubique = true;
 							}
 							else
 								errores++;
 						}
 						else
 							if(ubicados[r1-1] == false || orden[r1-1] == null || !(orden[r1-1].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 								ubicados [r1] = true;
 								orden[r1] = jugadores.get(t);
 								ubique = true;
 							}
 							else
 								errores++;
 				}
 			}
 			//Termine de ubicar a todos los jugadores
 			// Hago la 2da ronda por los byes
 			for (int t = 0; t<32; t = t + 2)
 			{
 				if (orden[t] == null)
 					orden[(t/2)+32] = orden [t+1];
 				else
 					if (orden[t+1] == null)
 						orden[(t/2)+32] = orden [t];
 					else
 						orden[(t/2)+32] = null;
 			}
 	}
 	else
 		if (i == 17){ //Cuadro de 16
 			orden = new Jugador[24]; //16 primera ronda + 8 2da ronda (jugadores que pasaron los byes)
 	 		for (int l=0;l<16;l++)
 	 			orden[l] = null;
 	 		ubicados = new boolean[16];
 	 		for (int l=0;l<16;l++)
 	 			ubicados[l] = false;
 	 		byes = 16 - jugadores.size();
 	 		orden[0] = jugadores.get(0);
 	 		ubicados[0] = true;
 	 		orden[15] = jugadores.get(1);
 	 		ubicados[15] = true;
 	 		if (r.nextInt(2) == 0){
 	 			orden[4] = jugadores.get(2);
 	 			ubicados[4] = true;
 	 			orden[11] = jugadores.get(3);
 	 			ubicados[11] = true;
 	 		}
 	 		else {
 	 			orden[4] = jugadores.get(3);
 	 			ubicados[4] = true;
 	 			orden[11] = jugadores.get(2);
 	 			ubicados[11] = true;
 	 		}

 	 			//Termine de ubicar los preclasficiados
 	 			//ubico los byes, los primeros 4 van con los preclasif
 	 			if (byes > 0){
 	 				ubicados[1] = true;
 	 				byes--;
 	 			}
 	 			if (byes > 0){
 	 				ubicados[14] = true;
 	 				byes--;
 	 			}
 	 			if (byes > 0){
 	 				ubicados[5] = true;
 	 				byes--;
 	 			}
 	 			if (byes > 0){
 	 				ubicados[10] = true;
 	 				byes--;
 	 			}
 	 			while(byes>1){
 	 				ubique = false;
 	 				while(!ubique){
 	 					r1 = r.nextInt(8);
 	 					if (ubicados[r1] == false)
 	 						if(r1%2 == 0){
 	 							if(ubicados[r1+1] == false || orden[r1+1] != null){
 	 								ubicados [r1] = true;
 	 								byes--;
 	 								ubique = true;
 	 							}
 	 						}
 	 						else
 	 							if(ubicados[r1-1] == false || orden[r1-1] != null){
 	 								ubicados [r1] = true;
 	 								byes--;
 	 								ubique = true;
 	 							}
 	 				}
 	 				ubique = false;
 	 				while(!ubique){
 	 					r1 = r.nextInt(8)+8;
 	 					if (ubicados[r1] == false)
 	 						if(r1%2 == 0){
 	 							if(ubicados[r1+1] == false || orden[r1+1] != null){
 	 								ubicados [r1] = true;
 	 								byes--;
 	 								ubique = true;
 	 							}
 	 						}
 	 						else
 	 							if(ubicados[r1-1] == false || orden[r1-1] != null){
 	 								ubicados [r1] = true;
 	 								byes--;
 	 								ubique = true;
 	 							}
 	 				}
 	 			}
 	 			if (byes == 1)
 	 				while(!ubique){
 	 					r1 = r.nextInt(16);
 	 					if (ubicados[r1] == false)
 	 						if(r1%2 == 0){
 	 							if(ubicados[r1+1] == false || orden[r1+1] != null){
 	 								ubicados [r1] = true;
 	 								byes--;
 	 								ubique = true;
 	 							}
 	 						}
 	 						else
 	 							if(ubicados[r1-1] == false || orden[r1-1] != null){
 	 								ubicados [r1] = true;
 	 								byes--;
 	 								ubique = true;
 	 							}
 	 				}
 	 			//termine de ubicar los byes
 	 			//ubico el resto de jugadores
 	 			for (int t = 4; t<jugadores.size();t++){
 	 				ubique = false;
 	 				int errores = 0;
 	 				while(!ubique){
 	 					r1 = r.nextInt(16);
 	 					if (ubicados[r1] == false)
 	 						if(r1%2 == 0){
 	 							if(ubicados[r1+1] == false || orden[r1+1] == null || !(orden[r1+1].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 								ubicados [r1] = true;
 	 								orden[r1] = jugadores.get(t);
 	 								ubique = true;
 	 							}
 	 							else
 	 								errores++;
 	 						}
 	 						else
 	 							if(ubicados[r1-1] == false || orden[r1-1] == null || !(orden[r1-1].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 								ubicados [r1] = true;
 	 								orden[r1] = jugadores.get(t);
 	 								ubique = true;
 	 							}
 	 							else
 	 								errores++;
 	 				}
 	 			}
 	 			//Termine de ubicar a todos los jugadores
 	 			// Hago la 2da ronda por los byes
 	 			for (int t = 0; t<16; t = t + 2)
 	 			{
 	 				if (orden[t] == null)
 	 					orden[(t/2)+16] = orden [t+1];
 	 				else
 	 					if (orden[t+1] == null)
 	 						orden[(t/2)+16] = orden [t];
 	 					else
 	 						orden[(t/2)+16] = null;
 	 			}
 	 	}
 		else
 			if(i == 6){// son dos zonas de 3
 				orden = new Jugador[6]; //6 jugadores (sin byes)
 	 	 		for (int l=0;l<6;l++)
 	 	 			orden[l] = null;
 	 	 		ubicados = new boolean[6];
 	 	 		for (int l=0;l<6;l++)
 	 	 			ubicados[l] = false;
 	 	 		orden[0] = jugadores.get(0);
 	 	 		ubicados[0] = true;
 	 	 		orden[3] = jugadores.get(1);
 	 	 		ubicados[3] = true;
 	 	 		//ubico el resto de jugadores
 	 			for (int t = 2; t<jugadores.size();t++){
 	 				ubique = false;
 	 				int errores = 0;
 	 				while(!ubique){
 	 					r1 = r.nextInt(6);
 	 					if (ubicados[r1] == false)
 	 						if(r1 == 1){
 	 							if(ubicados[2] == false)
	 									if(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
	 									ubicados [r1] = true;
	 									orden[r1] = jugadores.get(t);
	 									ubique = true;
	 									}
	 									else
	 										errores++;
	 								else
	 									if (!(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
	 										ubicados [r1] = true;
	 	 									orden[r1] = jugadores.get(t);
	 	 									ubique = true;
	 	 									}
	 	 								else
	 	 									errores++;
 	 						}
 	 						else
 	 							if(r1 == 2){
 	 								if(ubicados[1] == false)
 	 									if(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 	 									ubicados [r1] = true;
 	 									orden[r1] = jugadores.get(t);
 	 									ubique = true;
 	 									}
 	 									else
 	 										errores++;
 	 								else
 	 									if (!(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 	 										ubicados [r1] = true;
 	 	 									orden[r1] = jugadores.get(t);
 	 	 									ubique = true;
 	 	 									}
 	 	 								else
 	 	 									errores++;

 	 							}
 	 							else
 	 								if(r1 == 4){
 	 									if(ubicados[5] == false)
 	 	 									if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 	 	 									ubicados [r1] = true;
 	 	 									orden[r1] = jugadores.get(t);
 	 	 									ubique = true;
 	 	 									}
 	 	 									else
 	 	 										errores++;
 	 	 								else
 	 	 									if (!(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 	 	 										ubicados [r1] = true;
 	 	 	 									orden[r1] = jugadores.get(t);
 	 	 	 									ubique = true;
 	 	 	 									}
 	 	 	 								else
 	 	 	 									errores++;
 	 	 	 						}
 	 								else
 	 									if(ubicados[4] == false)
 	 	 									if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 	 	 									ubicados [r1] = true;
 	 	 									orden[r1] = jugadores.get(t);
 	 	 									ubique = true;
 	 	 									}
 	 	 									else
 	 	 										errores++;
 	 	 								else
 	 	 									if (!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 	 	 										ubicados [r1] = true;
 	 	 	 									orden[r1] = jugadores.get(t);
 	 	 	 									ubique = true;
 	 	 	 									}
 	 	 	 								else
 	 	 	 									errores++;
 	 				}
 	 			}
 	 			//Ordeno por rank
	 	 		Jugador jug;
	 	 		if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
	 	 			jug = orden[1];
					orden[1] = orden[2];
	  				orden[2] = jug;
				}
	 	 		if(orden[4] == null || (orden[5] != null && orden[4].getRanking()>orden[5].getRanking())){
	 	 			jug = orden[4];
	 	 			orden[4] = orden[5];
	 				orden[5] = jug;
	  			}
 			}
 			else
 				if(i == 7 || i == 8){
 					orden = new Jugador[8]; //2 zonas de 4 jugadores. 8 jugadores, con 0/1 bye
 	 	 	 		for (int l=0;l<8;l++)
 	 	 	 			orden[l] = null;
 	 	 	 		ubicados = new boolean[8];
 	 	 	 		for (int l=0;l<8;l++)
 	 	 	 			ubicados[l] = false;
 	 	 	 		orden[0] = jugadores.get(0);
 	 	 	 		ubicados[0] = true;
 	 	 	 		orden[4] = jugadores.get(1);
 	 	 	 		ubicados[4] = true;
 	 	 	 		//ubico el resto de jugadores
 	 	 			for (int t = 2; t<jugadores.size();t++){
 	 	 				ubique = false;
 	 	 				int errores = 0;
 	 	 				while(!ubique){
 	 	 					r1 = r.nextInt(8);
 	 	 					if (ubicados[r1] == false)
 	 	 						if(r1 == 1){
 	 	 							if(ubicados[2] == false)
 	 	 		 					{
 	 	 								if(ubicados[3] == false)
 	 	 								{
 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 									{
 	 	 										ubicados [r1] = true;
 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 								ubique = true;
 	 	 									}
 	 	 									else
 	 	 										errores++;
 	 	 								}
 	 	 								else
 	 	 									if(orden[3] == null)
 	 	 									{
 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 										{
 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 								ubique = true;
 	 	 	 									}
 	 	 	 									else
 	 	 	 										errores++;
 	 	 									}
 	 	 									else
 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 										{
 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 											{
 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 									}
 	 	 	 	 									else
 	 	 	 	 										errores++;
 	 	 										}
 	 	 										else
 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 											{
 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 									}
 	 	 	 	 									else
 	 	 	 	 										errores++;
 	 	 		 					}
 	 	 							else
 	 	 								if(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 	 									if(ubicados[3] == false)
 	 	 									{
 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 									{
 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 								ubique = true;
 	 	 	 									}
 	 	 	 									else
 	 	 	 										errores++;
 	 	 									}
 	 	 	 								else
 	 	 	 									if(orden[3] == null)
 	 	 	 									{
 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 										{
 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 									}
 	 	 	 	 									else
 	 	 	 	 										errores++;
 	 	 	 									}
 	 	 	 									else
 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 	 	 										{
 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 											{
 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 									}
 	 	 	 	 	 									else
 	 	 	 	 	 										errores++;
 	 	 	 										}
 	 	 	 										else
 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 											{
 	 	 	 												ubicados [r1] = true;
 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 	 												ubique = true;
 	 	 	 											}
 	 	 	 											else
 	 	 	 												errores++;
 	 	 								else
 	 	 									errores++;
 	 	 						}
 	 	 						else
 	 	 							if(r1 == 2){
 	 	 	 							if(ubicados[1] == false)
 	 	 	 								if(ubicados[3] == false)
 	 	 	 								{
 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 									{
 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 								ubique = true;
 	 	 	 									}
 	 	 	 									else
 	 	 	 										errores++;
 	 	 	 								}
 	 	 	 								else
 	 	 	 									if(orden[3] == null)
 	 	 	 									{
 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 										{
 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 									}
 	 	 	 	 									else
 	 	 	 	 										errores++;
 	 	 	 									}
 	 	 	 									else
 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 	 										{
 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 											{
 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 									}
 	 	 	 	 	 									else
 	 	 	 	 	 										errores++;
 	 	 	 										}
 	 	 	 										else
 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 											{
 	 	 	 												ubicados [r1] = true;
 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 	 												ubique = true;
 	 	 	 											}
 	 	 	 											else
 	 	 	 												errores++;
 	 	 	 							else
 	 	 	 								if(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 	 	 									if(ubicados[3] == false)
 	 	 	 									{
 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 										{
 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 									}
 	 	 	 	 									else
 	 	 	 	 										errores++;
 	 	 	 									}
 	 	 	 	 								else
 	 	 	 	 									if(orden[3] == null)
 	 	 	 	 									{
 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 	 										{
 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 									}
 	 	 	 	 	 									else
 	 	 	 	 	 										errores++;
 	 	 	 	 									}
 	 	 	 	 									else
 	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 	 	 										{	if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 	 										{
 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 									}
 	 	 	 	 	 	 									else
 	 	 	 	 	 	 										errores++;
 	 	 	 	 										}
 	 	 	 	 										else
 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 	 											{
 	 	 	 	 												ubicados [r1] = true;
 	 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 	 	 												ubique = true;
 	 	 	 	 											}
 	 	 	 	 											else
 	 	 	 	 												errores++;
 	 	 	 								else errores++;
 	 	 						}
 	 	 							else
 	 	 								if(r1 == 3){
 	 	 	 	 							if(ubicados[2] == false)
 	 	 	 	 								if(ubicados[1] == false)
 	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 									}
 	 	 	 	 									else
 	 	 	 	 										errores++;
 	 	 	 	 								else
 	 	 	 	 									if(orden[1] == null)
 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 									}
 	 	 	 	 	 									else
 	 	 	 	 	 										errores++;
 	 	 	 	 									else
 	 	 	 	 										if(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 									}
 	 	 	 	 	 	 									else
 	 	 	 	 	 	 										errores++;
 	 	 	 	 										else
 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 	 											{
 	 	 	 	 												ubicados [r1] = true;
 	 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 	 	 												ubique = true;
 	 	 	 	 											}
 	 	 	 	 											else
 	 	 	 	 												errores++;
 	 	 	 	 							else
 	 	 	 	 								if(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 	 	 	 									if(ubicados[1] == false)
 	 	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 									}
 	 	 	 	 	 									else
 	 	 	 	 	 										errores++;
 	 	 	 	 	 								else
 	 	 	 	 	 									if(orden[1] == null)
 	 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 									}
 	 	 	 	 	 	 									else
 	 	 	 	 	 	 										errores++;
 	 	 	 	 	 									else
 	 	 	 	 	 										if(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 	 									}
 	 	 	 	 	 	 	 									else
 	 	 	 	 	 	 	 										errores++;
 	 	 	 	 	 										else
 	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 	 	 											{
 	 	 	 	 	 												ubicados [r1] = true;
 	 	 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 	 	 	 												ubique = true;
 	 	 	 	 	 											}
 	 	 	 	 	 											else
 	 	 	 	 	 												errores++;
 	 	 	 	 								else
 	 	 	 	 									errores++;
 	 	 	 	 						}
 	 	 								else
 	 	 									if(r1 == 5){
 	 	 	 	 	 							if(ubicados[6] == false)
 	 	 	 	 	 								if(ubicados[7] == false)
 	 	 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 									}
 	 	 	 	 	 									else
 	 	 	 	 	 										errores++;
 	 	 	 	 	 								else
 	 	 	 	 	 									if(orden[7] == null)
 	 	 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 									}
 	 	 	 	 	 	 									else
 	 	 	 	 	 	 										errores++;
 	 	 	 	 	 									else
 	 	 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 	 									}
 	 	 	 	 	 	 	 									else
 	 	 	 	 	 	 	 										errores++;
 	 	 	 	 	 										else
 	 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 	 	 											{
 	 	 	 	 	 												ubicados [r1] = true;
 	 	 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 	 	 	 												ubique = true;
 	 	 	 	 	 											}
 	 	 	 	 	 											else
 	 	 	 	 	 												errores++;
 	 	 	 	 	 							else
 	 	 	 	 	 								if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 	 	 	 	 									if(ubicados[7] == false)
 	 	 	 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 									}
 	 	 	 	 	 	 									else
 	 	 	 	 	 	 										errores++;
 	 	 	 	 	 	 								else
 	 	 	 	 	 	 									if(orden[7] == null)
 	 	 	 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 	 									}
 	 	 	 	 	 	 	 									else
 	 	 	 	 	 	 	 										errores++;
 	 	 	 	 	 	 									else
 	 	 	 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 	 	 	 	 	 	 										ubicados [r1] = true;
 	 	 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 	 	 								ubique = true;
 	 	 	 	 	 	 	 	 									}
 	 	 	 	 	 	 	 	 									else
 	 	 	 	 	 	 	 	 										errores++;
 	 	 	 	 	 	 										else
 	 	 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 	 	 	 	 											{
 	 	 	 	 	 	 												ubicados [r1] = true;
 	 	 	 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 												ubique = true;
 	 	 	 	 	 	 											}
 	 	 	 	 	 	 											else
 	 	 	 	 	 	 												errores++;
 	 	 	 	 	 								else
 	 	 	 	 	 									errores++;
 	 	 	 	 	 						}
 	 	 									else
 	 	 										if(r1 == 6){
	 	 		 	 	 							if(ubicados[5] == false)
 	 	 		 	 	 								if(ubicados[7] == false)
 	 	 		 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 								ubique = true;
 	 	 		 	 	 									}
 	 	 		 	 	 									else
 	 	 		 	 	 										errores++;
 	 	 		 	 	 								else
 	 	 		 	 	 									if(orden[7] == null)
 	 	 		 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 									}
 	 	 		 	 	 	 									else
 	 	 		 	 	 	 										errores++;
 	 	 		 	 	 									else
 	 	 		 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 		 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 	 									}
 	 	 		 	 	 	 	 									else
 	 	 		 	 	 	 	 										errores++;
 	 	 		 	 	 										else
 	 	 		 	 	 											errores++;
 	 	 		 	 	 							else
 	 	 		 	 	 								if(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 	 		 	 	 									if(ubicados[7] == false)
 	 	 		 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 									}
 	 	 		 	 	 	 									else
 	 	 		 	 	 	 										errores++;
 	 	 		 	 	 	 								else
 	 	 		 	 	 	 									if(orden[7] == null)
 	 	 		 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 	 									}
 	 	 		 	 	 	 	 									else
 	 	 		 	 	 	 	 										errores++;
 	 	 		 	 	 	 									else
 	 	 		 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 		 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 	 	 									}
 	 	 		 	 	 	 	 	 									else
 	 	 		 	 	 	 	 	 										errores++;
 	 	 		 	 	 	 										else
 	 	 		 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 		 	 	 	 											{
 	 	 		 	 	 	 												ubicados [r1] = true;
 	 	 		 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 												ubique = true;
 	 	 		 	 	 	 											}
 	 	 		 	 	 	 											else
 	 	 		 	 	 	 												errores++;
 	 	 		 	 	 								else
 	 	 		 	 	 									errores++;
 	 	 	 	 	 	 						}
 	 	 										else
 	 	 											if(ubicados[5] == false)
 	 	 		 	 	 								if(ubicados[6] == false)
 	 	 		 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 								ubique = true;
 	 	 		 	 	 									}
 	 	 		 	 	 									else
 	 	 		 	 	 										errores++;
 	 	 		 	 	 								else
 	 	 		 	 	 									if(orden[6] == null)
 	 	 		 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 									}
 	 	 		 	 	 	 									else
 	 	 		 	 	 	 										errores++;
 	 	 		 	 	 									else
 	 	 		 	 	 										if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 		 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 	 									}
 	 	 		 	 	 	 	 									else
 	 	 		 	 	 	 	 										errores++;
 	 	 		 	 	 										else
 	 	 		 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 		 	 	 											{
 	 	 		 	 	 												ubicados [r1] = true;
 	 	 	 	 	 	 	 	 	 									orden[r1] = jugadores.get(t);
 	 	 	 	 	 	 	 	 	 									ubique = true;
 	 	 		 	 	 											}
 	 	 		 	 	 											else
 	 	 		 	 	 												errores++;
 	 	 		 	 	 							else
 	 	 		 	 	 								if(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 	 		 	 	 									if(ubicados[6] == false)
 	 	 		 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 									}
 	 	 		 	 	 	 									else
 	 	 		 	 	 	 										errores++;
 	 	 		 	 	 	 								else
 	 	 		 	 	 	 									if(orden[6] == null)
 	 	 		 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 	 									}
 	 	 		 	 	 	 	 									else
 	 	 		 	 	 	 	 										errores++;
 	 	 		 	 	 	 									else
 	 	 		 	 	 	 										if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 	 		 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 	 		 	 	 	 	 	 										ubicados [r1] = true;
 	 	 		 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 	 	 	 	 								ubique = true;
 	 	 		 	 	 	 	 	 									}
 	 	 		 	 	 	 	 	 									else
 	 	 		 	 	 	 	 	 										errores++;
 	 	 		 	 	 	 										else
 	 	 		 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 	 		 	 	 	 											{
 	 	 		 	 	 	 												ubicados [r1] = true;
 	 	 		 	 	 	 												orden[r1] = jugadores.get(t);
 	 	 		 	 	 	 												ubique = true;
 	 	 		 	 	 	 											}
 	 	 		 	 	 	 											else
 	 	 		 	 	 	 												errores++;
 	 	 		 	 	 								else
 	 	 		 	 	 									errores++;
 	 	 					}
 	 	 			}
 	 	 			//Ordeno por rank
 	 	 			Jugador jug;
 	 	 			if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
 	 	 				jug = orden[1];
 	 	 				orden[1] = orden[2];
 	 	 				orden[2] = jug;
 	 	 			}
 	 	 			if(orden[2] == null || (orden[3] != null && orden[2].getRanking()>orden[3].getRanking())){
 	 	 				jug = orden[2];
 	 	 				orden[2] = orden[3];
 	 	 				orden[3] = jug;
 	 	 			}
 	 	 			if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
 	 	 				jug = orden[1];
 	 	 				orden[1] = orden[2];
 	 	 				orden[2] = jug;
 	 	 			}
 	 	 			if(orden[5] == null || (orden[6] != null && orden[5].getRanking()>orden[6].getRanking())){
 	 	 				jug = orden[5];
 	 	 				orden[5] = orden[6];
 	 	 				orden[6] = jug;
 	 	 			}
 	 	 			if(orden[6] == null || (orden[7] != null && orden[6].getRanking()>orden[7].getRanking())){
 	 	 				jug = orden[6];
 	 	 				orden[6] = orden[7];
 	 	 				orden[7] = jug;
 	 	 			}
 	 	 			if(orden[5] == null || (orden[6] != null && orden[5].getRanking()>orden[6].getRanking())){
 	 	 				jug = orden[5];
 	 	 				orden[5] = orden[6];
 	 	 				orden[6] = jug;
 	 	 			}
 	 			}
 				else
 					if(i == 9){
 						orden = new Jugador[9]; //9 jugadores (sin byes)
 		 	 	 		for (int l=0;l<9;l++)
 		 	 	 			orden[l] = null;
 		 	 	 		ubicados = new boolean[9];
 		 	 	 		for (int l=0;l<9;l++)
 		 	 	 			ubicados[l] = false;
 		 	 	 		orden[0] = jugadores.get(0);
 		 	 	 		ubicados[0] = true;
 		 	 	 		orden[3] = jugadores.get(1);
 		 	 	 		ubicados[3] = true;
 		 	 	 		orden[6] = jugadores.get(2);
 		 	 	 		ubicados[6] = true;
						//ubico el resto de jugadores
 		 	 			for (int t = 3; t<jugadores.size();t++){
 		 	 				ubique = false;
 		 	 				int errores = 0;
 		 	 				while(!ubique){
 		 	 					r1 = r.nextInt(9);
	 	 						if (ubicados[r1] == false)
 		 	 						if(r1 == 1){
 		 	 							if(ubicados[2] == false)
 		 									if(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 									ubicados [r1] = true;
 		 									orden[r1] = jugadores.get(t);
 		 									ubique = true;
 		 									}
 		 									else
 		 										errores++;
 		 								else
 		 									if (!(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 										ubicados [r1] = true;
 		 	 									orden[r1] = jugadores.get(t);
 		 	 									ubique = true;
 		 	 									}
 		 	 								else
 		 	 									errores++;
 		 	 						}
 		 	 						else
 		 	 							if(r1 == 2){
 		 	 								if(ubicados[1] == false)
 			 									if(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 			 									ubicados [r1] = true;
 			 									orden[r1] = jugadores.get(t);
 			 									ubique = true;
 			 									}
 			 									else
 			 										errores++;
 			 								else
 			 									if (!(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 			 										ubicados [r1] = true;
 			 	 									orden[r1] = jugadores.get(t);
 			 	 									ubique = true;
 			 	 									}
 			 	 								else
 			 	 									errores++;
 		 	 						}
 		 	 							else
 		 	 								if(r1 == 4){
 		 	 									if(ubicados[5] == false)
 		 		 									if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 		 									ubicados [r1] = true;
 		 		 									orden[r1] = jugadores.get(t);
 		 		 									ubique = true;
 		 		 									}
 		 		 									else
 		 		 										errores++;
 		 		 								else
 		 		 									if (!(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 		 										ubicados [r1] = true;
 		 		 	 									orden[r1] = jugadores.get(t);
 		 		 	 									ubique = true;
 		 		 	 									}
 		 		 	 								else
 		 		 	 									errores++;
 		 	 	 	 						}
 		 	 								else
 		 	 									if(r1 == 5){
 		 	 										if(ubicados[4] == false)
 		 	 		 									if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 	 		 									ubicados [r1] = true;
 		 	 		 									orden[r1] = jugadores.get(t);
 		 	 		 									ubique = true;
 		 	 		 									}
 		 	 		 									else
 		 	 		 										errores++;
 		 	 		 								else
 		 	 		 									if (!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 	 		 										ubicados [r1] = true;
 		 	 		 	 									orden[r1] = jugadores.get(t);
 		 	 		 	 									ubique = true;
 		 	 		 	 									}
 		 	 		 	 								else
 		 	 		 	 									errores++;
 		 	 									}
 		 	 									else
 		 	 										if(r1 == 7){
 		 	 											if(ubicados[8] == false)
 		 	 			 									if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 	 			 									ubicados [r1] = true;
 		 	 			 									orden[r1] = jugadores.get(t);
 		 	 			 									ubique = true;
 		 	 			 									}
 		 	 			 									else
 		 	 			 										errores++;
 		 	 			 								else
 		 	 			 									if (!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 	 			 										ubicados [r1] = true;
 		 	 			 	 									orden[r1] = jugadores.get(t);
 		 	 			 	 									ubique = true;
 		 	 			 	 									}
 		 	 			 	 								else
 		 	 			 	 									errores++;
 	 		 	 									}
 		 	 										else{
 		 	 											if(ubicados[7] == false)
 		 	 			 									if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 	 			 									ubicados [r1] = true;
 		 	 			 									orden[r1] = jugadores.get(t);
 		 	 			 									ubique = true;
 		 	 			 									}
 		 	 			 									else
 		 	 			 										errores++;
 		 	 			 								else
 		 	 			 									if (!(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 	 			 										ubicados [r1] = true;
 		 	 			 	 									orden[r1] = jugadores.get(t);
 		 	 			 	 									ubique = true;
 		 	 			 	 									}
 		 	 			 	 								else
 		 	 			 	 									errores++;
 		 	 				}}
 		 	 			}
 		 	 			//Ordeno por rank
 			 	 		Jugador jug;
 			 	 		if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
 			 	 			jug = orden[1];
 							orden[1] = orden[2];
 			  				orden[2] = jug;
 						}
 			 	 		if(orden[4] == null || (orden[5] != null && orden[4].getRanking()>orden[5].getRanking())){
 			 	 			jug = orden[4];
 			 	 			orden[4] = orden[5];
 			 				orden[5] = jug;
 			  			}
 			 	 		if(orden[7] == null || (orden[8] != null && orden[7].getRanking()>orden[8].getRanking())){
 			 	 			jug = orden[7];
 			 	 			orden[7] = orden[8];
 			 				orden[8] = jug;
 			  			}
 					}
 					else
 						if(i == 10 || i == 11){
 							orden = new Jugador[12]; //3 zonas de 4 jugadores. 12 jugadores, con 1/2 bye
 		 	 	 	 		for (int l=0;l<12;l++)
 		 	 	 	 			orden[l] = null;
 		 	 	 	 		ubicados = new boolean[12];
 		 	 	 	 		for (int l=0;l<12;l++)
 		 	 	 	 			ubicados[l] = false;
 		 	 	 	 		orden[0] = jugadores.get(0);
 		 	 	 	 		ubicados[0] = true;
 		 	 	 	 		orden[4] = jugadores.get(1);
 		 	 	 	 		ubicados[4] = true;
 		 	 	 	 		orden[8] = jugadores.get(2);
		 	 	 	 		ubicados[8] = true;
		 	 	 	 		//Asigno byes
		 	 	 	 		r1 = r.nextInt(3);
		 	 	 	 		if (jugadores.size() == 10){
		 	 	 	 		if(r1 == 0){
		 	 	 	 			ubicados[3] = true;
		 	 	 	 			r1 = r.nextInt(2);
		 	 	 	 			if(r1 == 0)
		 	 	 	 				ubicados[7] = true;
		 	 	 	 			else
		 	 	 	 				ubicados[11] = true;
		 	 	 	 		}
		 	 	 	 		else
		 	 	 	 			if (r1 == 1)
		 	 	 	 			{
			 	 	 	 			ubicados[7] = true;
			 	 	 	 			r1 = r.nextInt(2);
			 	 	 	 			if(r1 == 0)
			 	 	 	 				ubicados[3] = true;
			 	 	 	 			else
			 	 	 	 				ubicados[11] = true;
			 	 	 	 		}
		 	 	 	 			else
		 	 	 	 			{
			 	 	 	 			ubicados[11] = true;
			 	 	 	 			r1 = r.nextInt(2);
			 	 	 	 			if(r1 == 0)
			 	 	 	 				ubicados[3] = true;
			 	 	 	 			else
			 	 	 	 				ubicados[7] = true;
			 	 	 	 		}
		 	 	 	 		}
		 	 	 	 		else
		 	 	 	 			if (r1 == 0)
		 	 	 	 				ubicados[3] = true;
		 	 	 	 			else
		 	 	 	 				if (r1 == 1)
		 	 	 	 					ubicados[7] = true;
		 	 	 	 				else
		 	 	 	 					ubicados[11] = true;

 		 	 	 	 		//ubico el resto de jugadores
 		 	 	 			for (int t = 3; t<jugadores.size();t++){
 		 	 	 				ubique = false;
 		 	 	 				int errores = 0;
 		 	 	 				while(!ubique){
 		 	 	 					r1 = r.nextInt(12);
 		 	 	 					if (ubicados[r1] == false)
 		 	 	 						if(r1 == 1){
 		 	 	 							if(ubicados[2] == false)
	 	 	 	 								if(ubicados[3] == false)
	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 								ubique = true;
	 	 	 	 									}
	 	 	 	 									else
	 	 	 	 										errores++;
	 	 	 	 								else
	 	 	 	 									if(orden[3] == null)
	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 									}
	 	 	 	 	 									else
	 	 	 	 	 										errores++;
	 	 	 	 									else
	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 									}
	 	 	 	 	 	 									else
	 	 	 	 	 	 										errores++;
	 	 	 	 										else
	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
	 	 	 	 											{
	 	 	 	 												ubicados [r1] = true;
	 	 	 	 												orden[r1] = jugadores.get(t);
	 	 	 	 												ubique = true;
	 	 	 	 											}
	 	 	 	 											else
	 	 	 	 												errores++;
	 	 	 	 							else
	 	 	 	 								if(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
	 	 	 	 									if(ubicados[3] == false)
	 	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 									}
	 	 	 	 	 									else
	 	 	 	 	 										errores++;
	 	 	 	 	 								else
	 	 	 	 	 									if(orden[3] == null)
	 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 									}
	 	 	 	 	 	 									else
	 	 	 	 	 	 										errores++;
	 	 	 	 	 									else
	 	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 	 									}
	 	 	 	 	 	 	 									else
	 	 	 	 	 	 	 										errores++;
	 	 	 	 	 										else
	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
	 	 	 	 	 											{
	 	 	 	 	 												ubicados [r1] = true;
	 	 	 	 	 												orden[r1] = jugadores.get(t);
	 	 	 	 	 												ubique = true;
	 	 	 	 	 											}
	 	 	 	 	 											else
	 	 	 	 	 												errores++;
	 	 	 	 								else
	 	 	 	 									errores++;
 		 	 	 						}
 		 	 	 						else
 		 	 	 							if(r1 == 2){
 		 	 	 							if(ubicados[1] == false)
	 	 	 	 								if(ubicados[3] == false)
	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 								ubique = true;
	 	 	 	 									}
	 	 	 	 									else
	 	 	 	 										errores++;
	 	 	 	 								else
	 	 	 	 									if(orden[3] == null)
	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 									}
	 	 	 	 	 									else
	 	 	 	 	 										errores++;
	 	 	 	 									else
	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 									}
	 	 	 	 	 	 									else
	 	 	 	 	 	 										errores++;
	 	 	 	 										else
	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
	 	 	 	 											{
	 	 	 	 												ubicados [r1] = true;
	 	 	 	 												orden[r1] = jugadores.get(t);
	 	 	 	 												ubique = true;
	 	 	 	 											}
	 	 	 	 											else
	 	 	 	 												errores++;
	 	 	 	 							else
	 	 	 	 								if(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
	 	 	 	 									if(ubicados[3] == false)
	 	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 									}
	 	 	 	 	 									else
	 	 	 	 	 										errores++;
	 	 	 	 	 								else
	 	 	 	 	 									if(orden[3] == null)
	 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 									}
	 	 	 	 	 	 									else
	 	 	 	 	 	 										errores++;
	 	 	 	 	 									else
	 	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 	 									}
	 	 	 	 	 	 	 									else
	 	 	 	 	 	 	 										errores++;
	 	 	 	 	 										else
	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
	 	 	 	 	 											{
	 	 	 	 	 												ubicados [r1] = true;
	 	 	 	 	 												orden[r1] = jugadores.get(t);
	 	 	 	 	 												ubique = true;
	 	 	 	 	 											}
	 	 	 	 	 											else
	 	 	 	 	 												errores++;
	 	 	 	 								else
	 	 	 	 									errores++;
 		 	 	 						}
 		 	 	 							else
 		 	 	 								if(r1 == 3){
 		 	 	 								if(ubicados[1] == false)
 		 	 	 	 								if(ubicados[2] == false)
 		 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 								ubique = true;
 		 	 	 	 									}
 		 	 	 	 									else
 		 	 	 	 										errores++;
 		 	 	 	 								else
 		 	 	 	 									if(orden[2] == null)
 		 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 									}
 		 	 	 	 	 									else
 		 	 	 	 	 										errores++;
 		 	 	 	 									else
 		 	 	 	 										if(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 	 									}
 		 	 	 	 	 	 									else
 		 	 	 	 	 	 										errores++;
 		 	 	 	 										else
 		 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 	 	 	 											{
 		 	 	 	 												ubicados [r1] = true;
 		 	 	 	 												orden[r1] = jugadores.get(t);
 		 	 	 	 												ubique = true;
 		 	 	 	 											}
 		 	 	 	 											else
 		 	 	 	 												errores++;
 		 	 	 	 							else
 		 	 	 	 								if(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 	 	 	 									if(ubicados[2] == false)
 		 	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 									}
 		 	 	 	 	 									else
 		 	 	 	 	 										errores++;
 		 	 	 	 	 								else
 		 	 	 	 	 									if(orden[2] == null)
 		 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 	 									}
 		 	 	 	 	 	 									else
 		 	 	 	 	 	 										errores++;
 		 	 	 	 	 									else
 		 	 	 	 	 										if(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 	 	 									}
 		 	 	 	 	 	 	 									else
 		 	 	 	 	 	 	 										errores++;
 		 	 	 	 	 										else
 		 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 	 	 	 	 											{
 		 	 	 	 	 												ubicados [r1] = true;
 		 	 	 	 	 												orden[r1] = jugadores.get(t);
 		 	 	 	 	 												ubique = true;
 		 	 	 	 	 											}
 		 	 	 	 	 											else
 		 	 	 	 	 												errores++;
 		 	 	 	 								else
 		 	 	 	 									errores++;
 		 	 	 	 	 						}
 		 	 	 								else
 		 	 	 									if(r1 == 5){
 		 	 	 									if(ubicados[6] == false)
 			 	 	 	 								if(ubicados[7] == false)
 			 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 								ubique = true;
 			 	 	 	 									}
 			 	 	 	 									else
 			 	 	 	 										errores++;
 			 	 	 	 								else
 			 	 	 	 									if(orden[7] == null)
 			 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 									}
 			 	 	 	 	 									else
 			 	 	 	 	 										errores++;
 			 	 	 	 									else
 			 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 	 									}
 			 	 	 	 	 	 									else
 			 	 	 	 	 	 										errores++;
 			 	 	 	 										else
 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 			 	 	 	 											{
 			 	 	 	 												ubicados [r1] = true;
 			 	 	 	 												orden[r1] = jugadores.get(t);
 			 	 	 	 												ubique = true;
 			 	 	 	 											}
 			 	 	 	 											else
 			 	 	 	 												errores++;
 			 	 	 	 							else
 			 	 	 	 								if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 			 	 	 	 									if(ubicados[7] == false)
 			 	 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 									}
 			 	 	 	 	 									else
 			 	 	 	 	 										errores++;
 			 	 	 	 	 								else
 			 	 	 	 	 									if(orden[7] == null)
 			 	 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 	 									}
 			 	 	 	 	 	 									else
 			 	 	 	 	 	 										errores++;
 			 	 	 	 	 									else
 			 	 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 	 	 									}
 			 	 	 	 	 	 	 									else
 			 	 	 	 	 	 	 										errores++;
 			 	 	 	 	 										else
 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 			 	 	 	 	 											{
 			 	 	 	 	 												ubicados [r1] = true;
 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 			 	 	 	 	 												ubique = true;
 			 	 	 	 	 											}
 			 	 	 	 	 											else
 			 	 	 	 	 												errores++;
 			 	 	 	 								else
 			 	 	 	 									errores++;
 		 	 	 	 	 	 						}
 		 	 	 									else
 		 	 	 										if(r1 == 6){
 		 	 	 			 							if(ubicados[5] == false)
 	 			 	 	 	 								if(ubicados[7] == false)
 	 			 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 									}
 	 			 	 	 	 									else
 	 			 	 	 	 										errores++;
 	 			 	 	 	 								else
 	 			 	 	 	 									if(orden[7] == null)
 	 			 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 									}
 	 			 	 	 	 	 									else
 	 			 	 	 	 	 										errores++;
 	 			 	 	 	 									else
 	 			 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 	 									}
 	 			 	 	 	 	 	 									else
 	 			 	 	 	 	 	 										errores++;
 	 			 	 	 	 										else
 	 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 			 	 	 	 											{
 	 			 	 	 	 												ubicados [r1] = true;
 	 			 	 	 	 												orden[r1] = jugadores.get(t);
 	 			 	 	 	 												ubique = true;
 	 			 	 	 	 											}
 	 			 	 	 	 											else
 	 			 	 	 	 												errores++;
 	 			 	 	 	 							else
 	 			 	 	 	 								if(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 			 	 	 	 									if(ubicados[7] == false)
 	 			 	 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 									}
 	 			 	 	 	 	 									else
 	 			 	 	 	 	 										errores++;
 	 			 	 	 	 	 								else
 	 			 	 	 	 	 									if(orden[7] == null)
 	 			 	 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 	 									}
 	 			 	 	 	 	 	 									else
 	 			 	 	 	 	 	 										errores++;
 	 			 	 	 	 	 									else
 	 			 	 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 	 	 									}
 	 			 	 	 	 	 	 	 									else
 	 			 	 	 	 	 	 	 										errores++;
 	 			 	 	 	 	 										else
 	 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 			 	 	 	 	 											{
 	 			 	 	 	 	 												ubicados [r1] = true;
 	 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 												ubique = true;
 	 			 	 	 	 	 											}
 	 			 	 	 	 	 											else
 	 			 	 	 	 	 												errores++;
 	 			 	 	 	 								else
 	 			 	 	 	 									errores++;
 		 	 	 	 	 	 	 						}
 		 	 	 										else
 		 	 	 											if(r1 == 7){
 		 	 	 											if(ubicados[5] == false)
 		 			 	 	 	 								if(ubicados[6] == false)
 		 			 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 									}
 		 			 	 	 	 									else
 		 			 	 	 	 										errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									if(orden[6] == null)
 		 			 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 									else
 		 			 	 	 	 										if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 										else
 		 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 											{
 		 			 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 												ubique = true;
 		 			 	 	 	 											}
 		 			 	 	 	 											else
 		 			 	 	 	 												errores++;
 		 			 	 	 	 							else
 		 			 	 	 	 								if(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 			 	 	 	 									if(ubicados[6] == false)
 		 			 	 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 	 								else
 		 			 	 	 	 	 									if(orden[6] == null)
 		 			 	 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 	 									}
 		 			 	 	 	 	 	 	 									else
 		 			 	 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 										else
 		 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 	 											{
 		 			 	 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 												ubique = true;
 		 			 	 	 	 	 											}
 		 			 	 	 	 	 											else
 		 			 	 	 	 	 												errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									errores++;
 		 	 	 											}
 		 	 	 											else
 		 	 	 											if(r1 == 9){
 		 	 	 											if(ubicados[10] == false)
 		 			 	 	 	 								if(ubicados[11] == false)
 		 			 	 	 	 									if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 									}
 		 			 	 	 	 									else
 		 			 	 	 	 										errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									if(orden[11] == null)
 		 			 	 	 	 										if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 									else
 		 			 	 	 	 										if(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 										else
 		 			 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 											{
 		 			 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 												ubique = true;
 		 			 	 	 	 											}
 		 			 	 	 	 											else
 		 			 	 	 	 												errores++;
 		 			 	 	 	 							else
 		 			 	 	 	 								if(orden[10].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 			 	 	 	 									if(ubicados[11] == false)
 		 			 	 	 	 	 									if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 	 								else
 		 			 	 	 	 	 									if(orden[11] == null)
 		 			 	 	 	 	 										if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										if(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 	 									}
 		 			 	 	 	 	 	 	 									else
 		 			 	 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 										else
 		 			 	 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 	 											{
 		 			 	 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 												ubique = true;
 		 			 	 	 	 	 											}
 		 			 	 	 	 	 											else
 		 			 	 	 	 	 												errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									errores++;
 		 	 	 											}
 		 	 	 											else
 		 	 	 											if(r1 == 10){
 		 	 	 											if(ubicados[9] == false)
 		 			 	 	 	 								if(ubicados[11] == false)
 		 			 	 	 	 									if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 									}
 		 			 	 	 	 									else
 		 			 	 	 	 										errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									if(orden[11] == null)
 		 			 	 	 	 										if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 									else
 		 			 	 	 	 										if(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 										else
 		 			 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 											{
 		 			 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 												ubique = true;
 		 			 	 	 	 											}
 		 			 	 	 	 											else
 		 			 	 	 	 												errores++;
 		 			 	 	 	 							else
 		 			 	 	 	 								if(orden[9].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 			 	 	 	 									if(ubicados[11] == false)
 		 			 	 	 	 	 									if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 	 								else
 		 			 	 	 	 	 									if(orden[11] == null)
 		 			 	 	 	 	 										if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										if(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 	 									}
 		 			 	 	 	 	 	 	 									else
 		 			 	 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 										else
 		 			 	 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 	 											{
 		 			 	 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 												ubique = true;
 		 			 	 	 	 	 											}
 		 			 	 	 	 	 											else
 		 			 	 	 	 	 												errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									errores++;
 		 	 	 											}
 		 	 	 											else
 		 	 	 												if(ubicados[9] == false)
 		 	 	 													if(ubicados[10] == false)
 		 	 	 														if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 															ubicados [r1] = true;
 		 	 	 															orden[r1] = jugadores.get(t);
 		 	 	 															ubique = true;
 		 	 	 														}
 		 	 	 														else
 		 	 	 															errores++;
 		 	 	 													else
 		 	 	 														if(orden[10] == null)
 		 	 	 															if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																ubicados [r1] = true;
 		 	 	 																orden[r1] = jugadores.get(t);
 		 	 	 																ubique = true;
 		 	 	 															}
 		 	 	 															else
 		 	 	 																errores++;
 		 	 	 														else
 		 	 	 															if(orden[10].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 	 	 																if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																	ubicados [r1] = true;
 		 	 	 																	orden[r1] = jugadores.get(t);
 		 	 	 																	ubique = true;
 		 	 	 																}
 		 	 	 																else
 		 	 	 																	errores++;
 		 	 	 															else
 		 	 	 																if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 	 	 																{
 		 	 	 																	ubicados [r1] = true;
 		 	 	 																	orden[r1] = jugadores.get(t);
 		 	 	 																	ubique = true;
 		 	 	 																}
 		 	 	 																else
 		 	 	 																	errores++;
 		 	 	 												else
 		 	 	 													if(orden[9].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 	 	 														if(ubicados[10] == false)
 		 	 	 															if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																ubicados [r1] = true;
 		 	 	 																orden[r1] = jugadores.get(t);
 		 	 	 																ubique = true;
 		 	 	 															}
 		 	 	 															else
 		 	 	 																errores++;
 		 	 	 														else
 		 	 	 															if(orden[10] == null)
 		 	 	 																if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																	ubicados [r1] = true;
 		 	 	 																	orden[r1] = jugadores.get(t);
 		 	 	 																	ubique = true;
 		 	 	 																}
 		 	 	 																else
 		 	 	 																	errores++;
 		 	 	 															else
 		 	 	 																if(orden[10].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 	 	 																	if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																		ubicados [r1] = true;
 		 	 	 																		orden[r1] = jugadores.get(t);
 		 	 	 																		ubique = true;
 		 	 	 																	}
 		 	 	 																	else
 		 	 	 																		errores++;
 		 	 	 																else
 		 	 	 																	if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 	 	 																	{
 		 	 	 																		ubicados [r1] = true;
 		 	 	 																		orden[r1] = jugadores.get(t);
 		 	 	 																		ubique = true;
 		 	 	 																	}
 		 	 	 																	else
 		 	 	 																		errores++;
 		 	 	 													else
 		 	 	 														errores++;
 		 	 	 					}
 		 	 	 			}
 		 	 	 			//Ordeno por rank
 		 	 	 			Jugador jug;
 		 	 	 			if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
 		 	 	 				jug = orden[1];
 		 	 	 				orden[1] = orden[2];
 		 	 	 				orden[2] = jug;
 		 	 	 			}
 		 	 	 			if(orden[2] == null || (orden[3] != null && orden[2].getRanking()>orden[3].getRanking())){
 		 	 	 				jug = orden[2];
 		 	 	 				orden[2] = orden[3];
 		 	 	 				orden[3] = jug;
 		 	 	 			}
 		 	 	 			if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
 		 	 	 				jug = orden[1];
 		 	 	 				orden[1] = orden[2];
 		 	 	 				orden[2] = jug;
 		 	 	 			}
 		 	 	 			if(orden[5] == null || (orden[6] != null && orden[5].getRanking()>orden[6].getRanking())){
 		 	 	 				jug = orden[5];
 		 	 	 				orden[5] = orden[6];
 		 	 	 				orden[6] = jug;
 		 	 	 			}
 		 	 	 			if(orden[6] == null || (orden[7] != null && orden[6].getRanking()>orden[7].getRanking())){
 		 	 	 				jug = orden[6];
 		 	 	 				orden[6] = orden[7];
 		 	 	 				orden[7] = jug;
 		 	 	 			}
 		 	 	 			if(orden[5] == null || (orden[6] != null && orden[5].getRanking()>orden[6].getRanking())){
 		 	 	 				jug = orden[5];
 		 	 	 				orden[5] = orden[6];
 		 	 	 				orden[6] = jug;
 		 	 	 			}
 		 	 	 			if(orden[9] == null || (orden[10] != null && orden[9].getRanking()>orden[10].getRanking())){
		 	 	 				jug = orden[9];
		 	 	 				orden[9] = orden[10];
		 	 	 				orden[10] = jug;
		 	 	 			}
		 	 	 			if(orden[10] == null || (orden[11] != null && orden[10].getRanking()>orden[11].getRanking())){
		 	 	 				jug = orden[10];
		 	 	 				orden[10] = orden[11];
		 	 	 				orden[11] = jug;
		 	 	 			}
		 	 	 			if(orden[9] == null || (orden[10] != null && orden[9].getRanking()>orden[10].getRanking())){
		 	 	 				jug = orden[9];
		 	 	 				orden[9] = orden[10];
		 	 	 				orden[10] = jug;
		 	 	 			}
 						}
 						else
 							if(i == 12)
 							{
 								orden = new Jugador[12]; //12 jugadores (sin byes)
 		 		 	 	 		for (int l=0;l<12;l++)
 		 		 	 	 			orden[l] = null;
 		 		 	 	 		ubicados = new boolean[12];
 		 		 	 	 		for (int l=0;l<12;l++)
 		 		 	 	 			ubicados[l] = false;
 		 		 	 	 		orden[0] = jugadores.get(0);
 		 		 	 	 		ubicados[0] = true;
 		 		 	 	 		orden[3] = jugadores.get(1);
 		 		 	 	 		ubicados[3] = true;
 		 		 	 	 		orden[6] = jugadores.get(2);
 		 		 	 	 		ubicados[6] = true;
 		 		 	 	 		orden[9] = jugadores.get(3);
 		 		 	 	 		ubicados[9] = true;
 		 		 	 	 		//ubico el resto de jugadores
 		 		 	 			for (int t = 4; t<jugadores.size();t++){
 		 		 	 				ubique = false;
 		 		 	 				int errores = 0;
 		 		 	 				while(!ubique){
 		 		 	 					r1 = r.nextInt(12);
 		 		 	 					if (ubicados[r1] == false)
 		 		 	 						if(r1 == 1){
 		 		 	 						if(ubicados[2] == false)
 		 	 									if(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 	 									ubicados [r1] = true;
 		 	 									orden[r1] = jugadores.get(t);
 		 	 									ubique = true;
 		 	 									}
 		 	 									else
 		 	 										errores++;
 		 	 								else
 		 	 									if (!(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 	 										ubicados [r1] = true;
 		 	 	 									orden[r1] = jugadores.get(t);
 		 	 	 									ubique = true;
 		 	 	 									}
 		 	 	 								else
 		 	 	 									errores++; 		 		 	 						}
 		 		 	 						else
 		 		 	 							if(r1 == 2){
 		 		 	 							if(ubicados[1] == false)
 		 	 	 									if(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 	 	 									ubicados [r1] = true;
 		 	 	 									orden[r1] = jugadores.get(t);
 		 	 	 									ubique = true;
 		 	 	 									}
 		 	 	 									else
 		 	 	 										errores++;
 		 	 	 								else
 		 	 	 									if (!(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[0].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 	 	 										ubicados [r1] = true;
 		 	 	 	 									orden[r1] = jugadores.get(t);
 		 	 	 	 									ubique = true;
 		 	 	 	 									}
 		 	 	 	 								else
 		 	 	 	 									errores++;
 		 		 	 						}
 		 		 	 							else
 		 		 	 								if(r1 == 4){
 		 		 	 								if(ubicados[5] == false)
 		 		 	 									if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 		 	 									ubicados [r1] = true;
 		 		 	 									orden[r1] = jugadores.get(t);
 		 		 	 									ubique = true;
 		 		 	 									}
 		 		 	 									else
 		 		 	 										errores++;
 		 		 	 								else
 		 		 	 									if (!(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 		 	 										ubicados [r1] = true;
 		 		 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 									ubique = true;
 		 		 	 	 									}
 		 		 	 	 								else
 		 		 	 	 									errores++;
 		 		 	 	 	 						}
 		 		 	 								else
 		 		 	 									if(r1 == 5){
 		 		 	 									if(ubicados[4] == false)
 		 		 	 	 									if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 		 	 	 									ubicados [r1] = true;
 		 		 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 									ubique = true;
 		 		 	 	 									}
 		 		 	 	 									else
 		 		 	 	 										errores++;
 		 		 	 	 								else
 		 		 	 	 									if (!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 		 	 	 										ubicados [r1] = true;
 		 		 	 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 	 									ubique = true;
 		 		 	 	 	 									}
 		 		 	 	 	 								else
 		 		 	 	 	 									errores++;
 		 		 	 									}
 		 		 	 									else
 		 		 	 										if(r1 == 7){
 		 		 	 										if(ubicados[8] == false)
 		 		 	  	 									if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 		 	  	 									ubicados [r1] = true;
 		 		 	  	 									orden[r1] = jugadores.get(t);
 		 		 	  	 									ubique = true;
 		 		 	  	 									}
 		 		 	  	 									else
 		 		 	  	 										errores++;
 		 		 	  	 								else
 		 		 	  	 									if (!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 		 	  	 										ubicados [r1] = true;
 		 		 	  	 	 									orden[r1] = jugadores.get(t);
 		 		 	  	 	 									ubique = true;
 		 		 	  	 	 									}
 		 		 	  	 	 								else
 		 		 	  	 	 									errores++;
 		 	 		 	 									}
 		 		 	 										else
 		 		 	 											if(r1 == 8){
 		 		 	 											if(ubicados[7] == false)
 		 		 	 	 	 									if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 		 	 	 	 									ubicados [r1] = true;
 		 		 	 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 	 									ubique = true;
 		 		 	 	 	 									}
 		 		 	 	 	 									else
 		 		 	 	 	 										errores++;
 		 		 	 	 	 								else
 		 		 	 	 	 									if (!(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 		 	 	 	 										ubicados [r1] = true;
 		 		 	 	 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 	 	 									ubique = true;
 		 		 	 	 	 	 									}
 		 		 	 	 	 	 								else
 		 		 	 	 	 	 									errores++;
 		 		 	 											}
 		 		 	 											else
 		 		 	 											if(r1 == 10){
 		 		 	 											if(ubicados[11] == false)
 		 		 	 	 	 									if(orden[9].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 		 	 	 	 									ubicados [r1] = true;
 		 		 	 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 	 									ubique = true;
 		 		 	 	 	 									}
 		 		 	 	 	 									else
 		 		 	 	 	 										errores++;
 		 		 	 	 	 								else
 		 		 	 	 	 									if (!(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[9].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 		 	 	 	 										ubicados [r1] = true;
 		 		 	 	 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 	 	 									ubique = true;
 		 		 	 	 	 	 									}
 		 		 	 	 	 	 								else
 		 		 	 	 	 	 									errores++;
 		 		 	 											}
 		 		 	 											else{
 		 		 	 												if(ubicados[10] == false)
 		 		 	 	 	 									if(orden[9].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7){
 		 		 	 	 	 									ubicados [r1] = true;
 		 		 	 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 	 									ubique = true;
 		 		 	 	 	 									}
 		 		 	 	 	 									else
 		 		 	 	 	 										errores++;
 		 		 	 	 	 								else
 		 		 	 	 	 									if (!(orden[10].getFederacion().equals(jugadores.get(t).getFederacion()) && !(orden[9].getFederacion().equals(jugadores.get(t).getFederacion()))) || errores == 7){
 		 		 	 	 	 										ubicados [r1] = true;
 		 		 	 	 	 	 									orden[r1] = jugadores.get(t);
 		 		 	 	 	 	 									ubique = true;
 		 		 	 	 	 	 									}
 		 		 	 	 	 	 								else
 		 		 	 	 	 	 									errores++;
 		 		 	 				}}
 		 		 	 			}
 		 		 	 			//Ordeno por rank
 		 			 	 		Jugador jug;
 		 			 	 		if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
 		 			 	 			jug = orden[1];
 		 							orden[1] = orden[2];
 		 			  				orden[2] = jug;
 		 						}
 		 			 	 		if(orden[4] == null || (orden[5] != null && orden[4].getRanking()>orden[5].getRanking())){
 		 			 	 			jug = orden[4];
 		 			 	 			orden[4] = orden[5];
 		 			 				orden[5] = jug;
 		 			  			}
 		 			 	 		if(orden[7] == null || (orden[8] != null && orden[7].getRanking()>orden[8].getRanking())){
 		 			 	 			jug = orden[7];
 		 			 	 			orden[7] = orden[8];
 		 			 				orden[8] = jug;
 		 			  			}
 		 			 	 		if(orden[10] == null || (orden[11] != null && orden[10].getRanking()>orden[11].getRanking())){
		 			 	 			jug = orden[10];
		 			 	 			orden[10] = orden[11];
		 			 				orden[11] = jug;
		 			  			}
 							}
 							else //13 a 16, 4 zonas de 4 mas byes
 							{
 								orden = new Jugador[16]; //4 zonas de 4 jugadores. 16 jugadores, con 1/2/3 bye
 	 		 	 	 	 		for (int l=0;l<16;l++)
 	 		 	 	 	 			orden[l] = null;
 	 		 	 	 	 		ubicados = new boolean[16];
 	 		 	 	 	 		for (int l=0;l<12;l++)
 	 		 	 	 	 			ubicados[l] = false;
 	 		 	 	 	 		orden[0] = jugadores.get(0);
 	 		 	 	 	 		ubicados[0] = true;
 	 		 	 	 	 		orden[4] = jugadores.get(1);
 	 		 	 	 	 		ubicados[4] = true;
 	 		 	 	 	 		orden[8] = jugadores.get(2);
 			 	 	 	 		ubicados[8] = true;
 			 	 	 	 		orden[12] = jugadores.get(3);
			 	 	 	 		ubicados[12] = true;
			 	 	 	 		//Asigno byes
			 	 	 	 		r1 = r.nextInt(4);
			 	 	 	 		if(jugadores.size() == 15)
			 	 	 	 			if(r1 == 0)
			 	 	 	 				ubicados[3] = true;
			 	 	 	 			else
			 	 	 	 				if(r1 == 1)
			 	 	 	 					ubicados[7] = true;
			 	 	 	 				else
			 	 	 	 					if(r1 == 2)
			 	 	 	 						ubicados[11] = true;
			 	 	 	 					else
			 	 	 	 						ubicados[15] = true;
			 	 	 	 		if(jugadores.size() == 14)
			 	 	 	 		{
			 	 	 	 			if(r1 == 0)
			 	 	 	 			{
			 	 	 	 				ubicados[3] = true;
			 	 	 	 				r1 = r.nextInt(3);
			 	 	 	 				if(r1 == 0)
			 	 	 	 					ubicados[7] = true;
			 	 	 	 				else
			 	 	 	 					if(r1 == 1)
			 	 	 	 						ubicados[11] = true;
			 	 	 	 					else
			 	 	 	 						ubicados[15] = true;
			 	 	 	 			}
			 	 	 	 			else
			 	 	 	 			if(r1 == 1)
			 	 	 	 			{
			 	 	 	 				ubicados[7] = true;
			 	 	 	 				r1 = r.nextInt(3);
			 	 	 	 				if(r1 == 0)
			 	 	 	 					ubicados[3] = true;
			 	 	 	 				else
			 	 	 	 					if(r1 == 1)
			 	 	 	 						ubicados[11] = true;
			 	 	 	 					else
			 	 	 	 						ubicados[15] = true;
			 	 	 	 			}
			 	 	 	 			else
			 	 	 	 			if(r1 == 2)
			 	 	 	 			{
			 	 	 	 				ubicados[11] = true;
			 	 	 	 				r1 = r.nextInt(3);
			 	 	 	 				if(r1 == 0)
			 	 	 	 					ubicados[3] = true;
			 	 	 	 				else
			 	 	 	 					if(r1 == 1)
			 	 	 	 						ubicados[7] = true;
			 	 	 	 					else
			 	 	 	 						ubicados[15] = true;
			 	 	 	 			}
			 	 	 	 			else
			 	 	 	 			{
			 	 	 	 				ubicados[15] = true;
			 	 	 	 				r1 = r.nextInt(3);
			 	 	 	 				if(r1 == 0)
			 	 	 	 					ubicados[3] = true;
			 	 	 	 				else
			 	 	 	 					if(r1 == 1)
			 	 	 	 						ubicados[7] = true;
			 	 	 	 					else
			 	 	 	 						ubicados[11] = true;
			 	 	 	 			}
			 	 	 	 		}
			 	 	 	 		if(jugadores.size() == 13)
			 	 	 	 		{
			 	 	 	 			if(r1 == 0)
			 	 	 	 			{
			 	 	 	 				ubicados[3] = true;
			 	 	 	 				r1 = r.nextInt(3);
			 	 	 	 				if(r1 == 0)
			 	 	 	 				{
			 	 	 	 					ubicados[7] = true;
			 	 	 	 					r1 = r.nextInt(2);
			 	 	 	 					if (r1 == 0)
			 	 	 	 						ubicados[11] = true;
			 	 	 	 					else
			 	 	 	 						ubicados[15] = true;
			 	 	 	 				}
			 	 	 	 				else
			 	 	 	 					if(r1 == 1)
			 	 	 	 					{
			 	 	 	 						ubicados[11] = true;
			 	 	 	 						r1 = r.nextInt(2);
			 	 	 	 						if (r1 == 0)
			 	 	 	 							ubicados[7] = true;
			 	 	 	 						else
			 	 	 	 							ubicados[15] = true;
			 	 	 	 					}
			 	 	 	 					else
			 	 	 	 					{
			 	 	 	 						ubicados[15] = true;
			 	 	 	 						r1 = r.nextInt(2);
			 	 	 	 						if (r1 == 0)
			 	 	 	 							ubicados[7] = true;
			 	 	 	 						else
			 	 	 	 							ubicados[11] = true;
			 	 	 	 					}
			 	 	 	 			}
			 	 	 	 			else
			 	 	 	 				if (r1 == 1)
			 	 	 	 				{
			 	 	 	 					ubicados[7] = true;
			 	 	 	 					r1 = r.nextInt(3);
			 	 	 	 					if(r1 == 0)
			 	 	 	 					{
			 	 	 	 						ubicados[3] = true;
			 	 	 	 						r1 = r.nextInt(2);
			 	 	 	 						if (r1 == 0)
			 	 	 	 							ubicados[11] = true;
			 	 	 	 						else
			 	 	 	 							ubicados[15] = true;
			 	 	 	 					}
			 	 	 	 					else
			 	 	 	 						if(r1 == 1){
			 	 	 	 							ubicados[11] = true;
			 	 	 	 							r1 = r.nextInt(2);
			 	 	 	 							if (r1 == 0)
			 	 	 	 								ubicados[3] = true;
			 	 	 	 							else
			 	 	 	 								ubicados[15] = true;
			 	 	 	 						}
			 	 	 	 						else
			 	 	 	 						{
			 	 	 	 							ubicados[15] = true;
			 	 	 	 							r1 = r.nextInt(2);
			 	 	 	 							if (r1 == 0)
			 	 	 	 								ubicados[3] = true;
			 	 	 	 							else
			 	 	 	 								ubicados[11] = true;
			 	 	 	 						}
			 	 	 	 				}
			 	 	 	 				else
			 	 	 	 					if (r1 == 2)
			 	 	 	 					{
			 	 	 	 						ubicados[11] = true;
			 	 	 	 						r1 = r.nextInt(3);
			 	 	 	 						if(r1 == 0)
			 	 	 	 						{
			 	 	 	 							ubicados[3] = true;
			 	 	 	 							r1 = r.nextInt(2);
			 	 	 	 							if (r1 == 0)
			 	 	 	 								ubicados[7] = true;
			 	 	 	 							else
			 	 	 	 								ubicados[15] = true;
			 	 	 	 						}
			 	 	 	 						else
			 	 	 	 							if(r1 == 1){
			 	 	 	 								ubicados[7] = true;
			 	 	 	 								r1 = r.nextInt(2);
			 	 	 	 								if (r1 == 0)
			 	 	 	 									ubicados[3] = true;
			 	 	 	 								else
			 	 	 	 									ubicados[15] = true;
			 	 	 	 							}
			 	 	 	 							else
			 	 	 	 							{
			 	 	 	 								ubicados[15] = true;
			 	 	 	 								r1 = r.nextInt(2);
			 	 	 	 								if (r1 == 0)
			 	 	 	 									ubicados[3] = true;
			 	 	 	 								else
			 	 	 	 									ubicados[7] = true;
			 	 	 	 							}
			 	 	 	 					}
			 	 	 	 					else
			 	 	 	 					{
			 	 	 	 						ubicados[15] = true;
			 	 	 	 						r1 = r.nextInt(3);
			 	 	 	 						if(r1 == 0)
			 	 	 	 						{
			 	 	 	 							ubicados[3] = true;
			 	 	 	 							r1 = r.nextInt(2);
			 	 	 	 							if (r1 == 0)
			 	 	 	 								ubicados[7] = true;
			 	 	 	 							else
			 	 	 	 								ubicados[11] = true;
			 	 	 	 						}
			 	 	 	 						else
			 	 	 	 							if(r1 == 1){
			 	 	 	 								ubicados[7] = true;
			 	 	 	 								r1 = r.nextInt(2);
			 	 	 	 								if (r1 == 0)
			 	 	 	 									ubicados[3] = true;
			 	 	 	 								else
			 	 	 	 									ubicados[11] = true;
			 	 	 	 							}
			 	 	 	 							else
			 	 	 	 							{
			 	 	 	 								ubicados[11] = true;
			 	 	 	 								r1 = r.nextInt(2);
			 	 	 	 								if (r1 == 0)
			 	 	 	 									ubicados[3] = true;
			 	 	 	 								else
			 	 	 	 									ubicados[7] = true;
			 	 	 	 							}
			 	 	 	 					}
			 	 	 	 		}
			 	 	 	 		//ubico el resto de jugadores
			 	 	 	 		for (int t = 4; t<jugadores.size();t++){
 	 		 	 	 				ubique = false;
 	 		 	 	 				int errores = 0;
 	 		 	 	 			while(!ubique){
 		 	 	 					r1 = r.nextInt(16);
 		 	 	 					if (ubicados[r1] == false)
 		 	 	 						if(r1 == 1){
 		 	 	 							if(ubicados[2] == false)
	 	 	 	 								if(ubicados[3] == false)
	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 								ubique = true;
	 	 	 	 									}
	 	 	 	 									else
	 	 	 	 										errores++;
	 	 	 	 								else
	 	 	 	 									if(orden[3] == null)
	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 									}
	 	 	 	 	 									else
	 	 	 	 	 										errores++;
	 	 	 	 									else
	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 									}
	 	 	 	 	 	 									else
	 	 	 	 	 	 										errores++;
	 	 	 	 										else
	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
	 	 	 	 											{
	 	 	 	 												ubicados [r1] = true;
	 	 	 	 												orden[r1] = jugadores.get(t);
	 	 	 	 												ubique = true;
	 	 	 	 											}
	 	 	 	 											else
	 	 	 	 												errores++;
	 	 	 	 							else
	 	 	 	 								if(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
	 	 	 	 									if(ubicados[3] == false)
	 	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 									}
	 	 	 	 	 									else
	 	 	 	 	 										errores++;
	 	 	 	 	 								else
	 	 	 	 	 									if(orden[3] == null)
	 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 									}
	 	 	 	 	 	 									else
	 	 	 	 	 	 										errores++;
	 	 	 	 	 									else
	 	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 	 									}
	 	 	 	 	 	 	 									else
	 	 	 	 	 	 	 										errores++;
	 	 	 	 	 										else
	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
	 	 	 	 	 											{
	 	 	 	 	 												ubicados [r1] = true;
	 	 	 	 	 												orden[r1] = jugadores.get(t);
	 	 	 	 	 												ubique = true;
	 	 	 	 	 											}
	 	 	 	 	 											else
	 	 	 	 	 												errores++;
	 	 	 	 								else
	 	 	 	 									errores++;
 		 	 	 						}
 		 	 	 						else
 		 	 	 							if(r1 == 2){
 		 	 	 							if(ubicados[1] == false)
	 	 	 	 								if(ubicados[3] == false)
	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 								ubique = true;
	 	 	 	 									}
	 	 	 	 									else
	 	 	 	 										errores++;
	 	 	 	 								else
	 	 	 	 									if(orden[3] == null)
	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 									}
	 	 	 	 	 									else
	 	 	 	 	 										errores++;
	 	 	 	 									else
	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 									}
	 	 	 	 	 	 									else
	 	 	 	 	 	 										errores++;
	 	 	 	 										else
	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
	 	 	 	 											{
	 	 	 	 												ubicados [r1] = true;
	 	 	 	 												orden[r1] = jugadores.get(t);
	 	 	 	 												ubique = true;
	 	 	 	 											}
	 	 	 	 											else
	 	 	 	 												errores++;
	 	 	 	 							else
	 	 	 	 								if(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
	 	 	 	 									if(ubicados[3] == false)
	 	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 									}
	 	 	 	 	 									else
	 	 	 	 	 										errores++;
	 	 	 	 	 								else
	 	 	 	 	 									if(orden[3] == null)
	 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 									}
	 	 	 	 	 	 									else
	 	 	 	 	 	 										errores++;
	 	 	 	 	 									else
	 	 	 	 	 										if(orden[3].getFederacion().equals(jugadores.get(t).getFederacion()))
	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
	 	 	 	 	 	 	 										ubicados [r1] = true;
	 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
	 	 	 	 	 	 	 	 	 								ubique = true;
	 	 	 	 	 	 	 									}
	 	 	 	 	 	 	 									else
	 	 	 	 	 	 	 										errores++;
	 	 	 	 	 										else
	 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
	 	 	 	 	 											{
	 	 	 	 	 												ubicados [r1] = true;
	 	 	 	 	 												orden[r1] = jugadores.get(t);
	 	 	 	 	 												ubique = true;
	 	 	 	 	 											}
	 	 	 	 	 											else
	 	 	 	 	 												errores++;
	 	 	 	 								else
	 	 	 	 									errores++;
 		 	 	 						}
 		 	 	 							else
 		 	 	 								if(r1 == 3){
 		 	 	 								if(ubicados[1] == false)
 		 	 	 	 								if(ubicados[2] == false)
 		 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 								ubique = true;
 		 	 	 	 									}
 		 	 	 	 									else
 		 	 	 	 										errores++;
 		 	 	 	 								else
 		 	 	 	 									if(orden[2] == null)
 		 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 									}
 		 	 	 	 	 									else
 		 	 	 	 	 										errores++;
 		 	 	 	 									else
 		 	 	 	 										if(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 	 									}
 		 	 	 	 	 	 									else
 		 	 	 	 	 	 										errores++;
 		 	 	 	 										else
 		 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 	 	 	 											{
 		 	 	 	 												ubicados [r1] = true;
 		 	 	 	 												orden[r1] = jugadores.get(t);
 		 	 	 	 												ubique = true;
 		 	 	 	 											}
 		 	 	 	 											else
 		 	 	 	 												errores++;
 		 	 	 	 							else
 		 	 	 	 								if(orden[1].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 	 	 	 									if(ubicados[2] == false)
 		 	 	 	 	 									if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 									}
 		 	 	 	 	 									else
 		 	 	 	 	 										errores++;
 		 	 	 	 	 								else
 		 	 	 	 	 									if(orden[2] == null)
 		 	 	 	 	 										if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 	 									}
 		 	 	 	 	 	 									else
 		 	 	 	 	 	 										errores++;
 		 	 	 	 	 									else
 		 	 	 	 	 										if(orden[2].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 	 	 	 	 										ubicados [r1] = true;
 		 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 	 	 	 	 	 	 	 	 								ubique = true;
 		 	 	 	 	 	 	 									}
 		 	 	 	 	 	 	 									else
 		 	 	 	 	 	 	 										errores++;
 		 	 	 	 	 										else
 		 	 	 	 	 											if(!(orden[0].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 	 	 	 	 											{
 		 	 	 	 	 												ubicados [r1] = true;
 		 	 	 	 	 												orden[r1] = jugadores.get(t);
 		 	 	 	 	 												ubique = true;
 		 	 	 	 	 											}
 		 	 	 	 	 											else
 		 	 	 	 	 												errores++;
 		 	 	 	 								else
 		 	 	 	 									errores++;
 		 	 	 	 	 						}
 		 	 	 								else
 		 	 	 									if(r1 == 5){
 		 	 	 									if(ubicados[6] == false)
 			 	 	 	 								if(ubicados[7] == false)
 			 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 								ubique = true;
 			 	 	 	 									}
 			 	 	 	 									else
 			 	 	 	 										errores++;
 			 	 	 	 								else
 			 	 	 	 									if(orden[7] == null)
 			 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 									}
 			 	 	 	 	 									else
 			 	 	 	 	 										errores++;
 			 	 	 	 									else
 			 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 	 									}
 			 	 	 	 	 	 									else
 			 	 	 	 	 	 										errores++;
 			 	 	 	 										else
 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 			 	 	 	 											{
 			 	 	 	 												ubicados [r1] = true;
 			 	 	 	 												orden[r1] = jugadores.get(t);
 			 	 	 	 												ubique = true;
 			 	 	 	 											}
 			 	 	 	 											else
 			 	 	 	 												errores++;
 			 	 	 	 							else
 			 	 	 	 								if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 			 	 	 	 									if(ubicados[7] == false)
 			 	 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 									}
 			 	 	 	 	 									else
 			 	 	 	 	 										errores++;
 			 	 	 	 	 								else
 			 	 	 	 	 									if(orden[7] == null)
 			 	 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 	 									}
 			 	 	 	 	 	 									else
 			 	 	 	 	 	 										errores++;
 			 	 	 	 	 									else
 			 	 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 			 	 	 	 	 	 	 										ubicados [r1] = true;
 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 			 	 	 	 	 	 	 	 	 								ubique = true;
 			 	 	 	 	 	 	 									}
 			 	 	 	 	 	 	 									else
 			 	 	 	 	 	 	 										errores++;
 			 	 	 	 	 										else
 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 			 	 	 	 	 											{
 			 	 	 	 	 												ubicados [r1] = true;
 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 			 	 	 	 	 												ubique = true;
 			 	 	 	 	 											}
 			 	 	 	 	 											else
 			 	 	 	 	 												errores++;
 			 	 	 	 								else
 			 	 	 	 									errores++;
 		 	 	 	 	 	 						}
 		 	 	 									else
 		 	 	 										if(r1 == 6){
 		 	 	 										if(ubicados[5] == false)
 	 			 	 	 	 								if(ubicados[7] == false)
 	 			 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 									}
 	 			 	 	 	 									else
 	 			 	 	 	 										errores++;
 	 			 	 	 	 								else
 	 			 	 	 	 									if(orden[7] == null)
 	 			 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 									}
 	 			 	 	 	 	 									else
 	 			 	 	 	 	 										errores++;
 	 			 	 	 	 									else
 	 			 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 	 									}
 	 			 	 	 	 	 	 									else
 	 			 	 	 	 	 	 										errores++;
 	 			 	 	 	 										else
 	 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 			 	 	 	 											{
 	 			 	 	 	 												ubicados [r1] = true;
 	 			 	 	 	 												orden[r1] = jugadores.get(t);
 	 			 	 	 	 												ubique = true;
 	 			 	 	 	 											}
 	 			 	 	 	 											else
 	 			 	 	 	 												errores++;
 	 			 	 	 	 							else
 	 			 	 	 	 								if(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 			 	 	 	 									if(ubicados[7] == false)
 	 			 	 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 									}
 	 			 	 	 	 	 									else
 	 			 	 	 	 	 										errores++;
 	 			 	 	 	 	 								else
 	 			 	 	 	 	 									if(orden[7] == null)
 	 			 	 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 	 									}
 	 			 	 	 	 	 	 									else
 	 			 	 	 	 	 	 										errores++;
 	 			 	 	 	 	 									else
 	 			 	 	 	 	 										if(orden[7].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 			 	 	 	 	 	 	 										ubicados [r1] = true;
 	 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 	 	 	 	 								ubique = true;
 	 			 	 	 	 	 	 	 									}
 	 			 	 	 	 	 	 	 									else
 	 			 	 	 	 	 	 	 										errores++;
 	 			 	 	 	 	 										else
 	 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 			 	 	 	 	 											{
 	 			 	 	 	 	 												ubicados [r1] = true;
 	 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 	 			 	 	 	 	 												ubique = true;
 	 			 	 	 	 	 											}
 	 			 	 	 	 	 											else
 	 			 	 	 	 	 												errores++;
 	 			 	 	 	 								else
 	 			 	 	 	 									errores++;
 		 	 	 	 	 	 	 						}
 		 	 	 										else
 		 	 	 											if(r1 == 7){
 		 	 	 											if(ubicados[5] == false)
 		 			 	 	 	 								if(ubicados[6] == false)
 		 			 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 									}
 		 			 	 	 	 									else
 		 			 	 	 	 										errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									if(orden[6] == null)
 		 			 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 									else
 		 			 	 	 	 										if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 										else
 		 			 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 											{
 		 			 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 												ubique = true;
 		 			 	 	 	 											}
 		 			 	 	 	 											else
 		 			 	 	 	 												errores++;
 		 			 	 	 	 							else
 		 			 	 	 	 								if(orden[5].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 			 	 	 	 									if(ubicados[6] == false)
 		 			 	 	 	 	 									if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 	 								else
 		 			 	 	 	 	 									if(orden[6] == null)
 		 			 	 	 	 	 										if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										if(orden[6].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 	 									}
 		 			 	 	 	 	 	 	 									else
 		 			 	 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 										else
 		 			 	 	 	 	 											if(!(orden[4].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 	 											{
 		 			 	 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 												ubique = true;
 		 			 	 	 	 	 											}
 		 			 	 	 	 	 											else
 		 			 	 	 	 	 												errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									errores++;
 		 	 	 											}
 		 	 	 											else
 		 	 	 											if(r1 == 9){
 		 	 	 											if(ubicados[10] == false)
 		 			 	 	 	 								if(ubicados[11] == false)
 		 			 	 	 	 									if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 									}
 		 			 	 	 	 									else
 		 			 	 	 	 										errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									if(orden[11] == null)
 		 			 	 	 	 										if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 									else
 		 			 	 	 	 										if(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 										else
 		 			 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 											{
 		 			 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 												ubique = true;
 		 			 	 	 	 											}
 		 			 	 	 	 											else
 		 			 	 	 	 												errores++;
 		 			 	 	 	 							else
 		 			 	 	 	 								if(orden[10].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 			 	 	 	 									if(ubicados[11] == false)
 		 			 	 	 	 	 									if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 	 								else
 		 			 	 	 	 	 									if(orden[11] == null)
 		 			 	 	 	 	 										if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										if(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 	 									}
 		 			 	 	 	 	 	 	 									else
 		 			 	 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 										else
 		 			 	 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 	 											{
 		 			 	 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 												ubique = true;
 		 			 	 	 	 	 											}
 		 			 	 	 	 	 											else
 		 			 	 	 	 	 												errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									errores++;
 		 	 	 											}
 		 	 	 											else
 		 	 	 											if(r1 == 10){
 		 	 	 											if(ubicados[9] == false)
 		 			 	 	 	 								if(ubicados[11] == false)
 		 			 	 	 	 									if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 									}
 		 			 	 	 	 									else
 		 			 	 	 	 										errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									if(orden[11] == null)
 		 			 	 	 	 										if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 									else
 		 			 	 	 	 										if(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 										else
 		 			 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 											{
 		 			 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 												ubique = true;
 		 			 	 	 	 											}
 		 			 	 	 	 											else
 		 			 	 	 	 												errores++;
 		 			 	 	 	 							else
 		 			 	 	 	 								if(orden[9].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 			 	 	 	 									if(ubicados[11] == false)
 		 			 	 	 	 	 									if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 									}
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										errores++;
 		 			 	 	 	 	 								else
 		 			 	 	 	 	 									if(orden[11] == null)
 		 			 	 	 	 	 										if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 									}
 		 			 	 	 	 	 	 									else
 		 			 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 									else
 		 			 	 	 	 	 										if(orden[11].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 			 	 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 			 	 	 	 	 	 	 										ubicados [r1] = true;
 		 			 	 	 	 	 	 	 	 	 								orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 	 	 	 	 								ubique = true;
 		 			 	 	 	 	 	 	 									}
 		 			 	 	 	 	 	 	 									else
 		 			 	 	 	 	 	 	 										errores++;
 		 			 	 	 	 	 										else
 		 			 	 	 	 	 											if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 			 	 	 	 	 											{
 		 			 	 	 	 	 												ubicados [r1] = true;
 		 			 	 	 	 	 												orden[r1] = jugadores.get(t);
 		 			 	 	 	 	 												ubique = true;
 		 			 	 	 	 	 											}
 		 			 	 	 	 	 											else
 		 			 	 	 	 	 												errores++;
 		 			 	 	 	 								else
 		 			 	 	 	 									errores++;
 		 	 	 											}
 		 	 	 											else
 		 	 	 												if(r1 == 11){
 		 	 	 												if(ubicados[9] == false)
 		 	 	 													if(ubicados[10] == false)
 		 	 	 														if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 															ubicados [r1] = true;
 		 	 	 															orden[r1] = jugadores.get(t);
 		 	 	 															ubique = true;
 		 	 	 														}
 		 	 	 														else
 		 	 	 															errores++;
 		 	 	 													else
 		 	 	 														if(orden[10] == null)
 		 	 	 															if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																ubicados [r1] = true;
 		 	 	 																orden[r1] = jugadores.get(t);
 		 	 	 																ubique = true;
 		 	 	 															}
 		 	 	 															else
 		 	 	 																errores++;
 		 	 	 														else
 		 	 	 															if(orden[10].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 	 	 																if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																	ubicados [r1] = true;
 		 	 	 																	orden[r1] = jugadores.get(t);
 		 	 	 																	ubique = true;
 		 	 	 																}
 		 	 	 																else
 		 	 	 																	errores++;
 		 	 	 															else
 		 	 	 																if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 	 	 																{
 		 	 	 																	ubicados [r1] = true;
 		 	 	 																	orden[r1] = jugadores.get(t);
 		 	 	 																	ubique = true;
 		 	 	 																}
 		 	 	 																else
 		 	 	 																	errores++;
 		 	 	 												else
 		 	 	 													if(orden[9].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 		 	 	 														if(ubicados[10] == false)
 		 	 	 															if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																ubicados [r1] = true;
 		 	 	 																orden[r1] = jugadores.get(t);
 		 	 	 																ubique = true;
 		 	 	 															}
 		 	 	 															else
 		 	 	 																errores++;
 		 	 	 														else
 		 	 	 															if(orden[10] == null)
 		 	 	 																if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																	ubicados [r1] = true;
 		 	 	 																	orden[r1] = jugadores.get(t);
 		 	 	 																	ubique = true;
 		 	 	 																}
 		 	 	 																else
 		 	 	 																	errores++;
 		 	 	 															else
 		 	 	 																if(orden[10].getFederacion().equals(jugadores.get(t).getFederacion()))
 		 	 	 																	if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 		 	 	 																		ubicados [r1] = true;
 		 	 	 																		orden[r1] = jugadores.get(t);
 		 	 	 																		ubique = true;
 		 	 	 																	}
 		 	 	 																	else
 		 	 	 																		errores++;
 		 	 	 																else
 		 	 	 																	if(!(orden[8].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 		 	 	 																	{
 		 	 	 																		ubicados [r1] = true;
 		 	 	 																		orden[r1] = jugadores.get(t);
 		 	 	 																		ubique = true;
 		 	 	 																	}
 		 	 	 																	else
 		 	 	 																		errores++;
 		 	 	 													else
 		 	 	 														errores++;
 		 	 	 												}
 		 	 	 												else
 		 	 	 												if(r1 == 13){
 	 		 	 	 												if(ubicados[14] == false)
 	 		 	 	 													if(ubicados[15] == false)
 	 		 	 	 														if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 															ubicados [r1] = true;
 	 		 	 	 															orden[r1] = jugadores.get(t);
 	 		 	 	 															ubique = true;
 	 		 	 	 														}
 	 		 	 	 														else
 	 		 	 	 															errores++;
 	 		 	 	 													else
 	 		 	 	 														if(orden[15] == null)
 	 		 	 	 															if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																ubicados [r1] = true;
 	 		 	 	 																orden[r1] = jugadores.get(t);
 	 		 	 	 																ubique = true;
 	 		 	 	 															}
 	 		 	 	 															else
 	 		 	 	 																errores++;
 	 		 	 	 														else
 	 		 	 	 															if(orden[15].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 															else
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 		 	 	 																{
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 												else
 	 		 	 	 													if(orden[14].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 		 	 	 														if(ubicados[15] == false)
 	 		 	 	 															if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																ubicados [r1] = true;
 	 		 	 	 																orden[r1] = jugadores.get(t);
 	 		 	 	 																ubique = true;
 	 		 	 	 															}
 	 		 	 	 															else
 	 		 	 	 																errores++;
 	 		 	 	 														else
 	 		 	 	 															if(orden[15] == null)
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 															else
 	 		 	 	 																if(orden[15].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 		 	 	 																	if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																		ubicados [r1] = true;
 	 		 	 	 																		orden[r1] = jugadores.get(t);
 	 		 	 	 																		ubique = true;
 	 		 	 	 																	}
 	 		 	 	 																	else
 	 		 	 	 																		errores++;
 	 		 	 	 																else
 	 		 	 	 																	if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 		 	 	 																	{
 	 		 	 	 																		ubicados [r1] = true;
 	 		 	 	 																		orden[r1] = jugadores.get(t);
 	 		 	 	 																		ubique = true;
 	 		 	 	 																	}
 	 		 	 	 																	else
 	 		 	 	 																		errores++;
 	 		 	 	 													else
 	 		 	 	 														errores++;
 	 		 	 	 												}
 		 	 	 												else
 		 	 	 												if(r1 == 14){
 	 		 	 	 												if(ubicados[13] == false)
 	 		 	 	 													if(ubicados[15] == false)
 	 		 	 	 														if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 															ubicados [r1] = true;
 	 		 	 	 															orden[r1] = jugadores.get(t);
 	 		 	 	 															ubique = true;
 	 		 	 	 														}
 	 		 	 	 														else
 	 		 	 	 															errores++;
 	 		 	 	 													else
 	 		 	 	 														if(orden[15] == null)
 	 		 	 	 															if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																ubicados [r1] = true;
 	 		 	 	 																orden[r1] = jugadores.get(t);
 	 		 	 	 																ubique = true;
 	 		 	 	 															}
 	 		 	 	 															else
 	 		 	 	 																errores++;
 	 		 	 	 														else
 	 		 	 	 															if(orden[15].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 															else
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 		 	 	 																{
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 												else
 	 		 	 	 													if(orden[13].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 		 	 	 														if(ubicados[15] == false)
 	 		 	 	 															if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																ubicados [r1] = true;
 	 		 	 	 																orden[r1] = jugadores.get(t);
 	 		 	 	 																ubique = true;
 	 		 	 	 															}
 	 		 	 	 															else
 	 		 	 	 																errores++;
 	 		 	 	 														else
 	 		 	 	 															if(orden[15] == null)
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 															else
 	 		 	 	 																if(orden[15].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 		 	 	 																	if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																		ubicados [r1] = true;
 	 		 	 	 																		orden[r1] = jugadores.get(t);
 	 		 	 	 																		ubique = true;
 	 		 	 	 																	}
 	 		 	 	 																	else
 	 		 	 	 																		errores++;
 	 		 	 	 																else
 	 		 	 	 																	if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 		 	 	 																	{
 	 		 	 	 																		ubicados [r1] = true;
 	 		 	 	 																		orden[r1] = jugadores.get(t);
 	 		 	 	 																		ubique = true;
 	 		 	 	 																	}
 	 		 	 	 																	else
 	 		 	 	 																		errores++;
 	 		 	 	 													else
 	 		 	 	 														errores++;
 	 		 	 	 												}
 		 	 	 												else
 		 	 	 												if(r1 == 15){
 	 		 	 	 												if(ubicados[13] == false)
 	 		 	 	 													if(ubicados[14] == false)
 	 		 	 	 														if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 															ubicados [r1] = true;
 	 		 	 	 															orden[r1] = jugadores.get(t);
 	 		 	 	 															ubique = true;
 	 		 	 	 														}
 	 		 	 	 														else
 	 		 	 	 															errores++;
 	 		 	 	 													else
 	 		 	 	 														if(orden[14] == null)
 	 		 	 	 															if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																ubicados [r1] = true;
 	 		 	 	 																orden[r1] = jugadores.get(t);
 	 		 	 	 																ubique = true;
 	 		 	 	 															}
 	 		 	 	 															else
 	 		 	 	 																errores++;
 	 		 	 	 														else
 	 		 	 	 															if(orden[14].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 															else
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 		 	 	 																{
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 												else
 	 		 	 	 													if(orden[13].getFederacion().equals(jugadores.get(t).getFederacion()) || errores == 7)
 	 		 	 	 														if(ubicados[14] == false)
 	 		 	 	 															if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																ubicados [r1] = true;
 	 		 	 	 																orden[r1] = jugadores.get(t);
 	 		 	 	 																ubique = true;
 	 		 	 	 															}
 	 		 	 	 															else
 	 		 	 	 																errores++;
 	 		 	 	 														else
 	 		 	 	 															if(orden[14] == null)
 	 		 	 	 																if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																	ubicados [r1] = true;
 	 		 	 	 																	orden[r1] = jugadores.get(t);
 	 		 	 	 																	ubique = true;
 	 		 	 	 																}
 	 		 	 	 																else
 	 		 	 	 																	errores++;
 	 		 	 	 															else
 	 		 	 	 																if(orden[14].getFederacion().equals(jugadores.get(t).getFederacion()))
 	 		 	 	 																	if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7){
 	 		 	 	 																		ubicados [r1] = true;
 	 		 	 	 																		orden[r1] = jugadores.get(t);
 	 		 	 	 																		ubique = true;
 	 		 	 	 																	}
 	 		 	 	 																	else
 	 		 	 	 																		errores++;
 	 		 	 	 																else
 	 		 	 	 																	if(!(orden[12].getFederacion().equals(jugadores.get(t).getFederacion())) || errores == 7)
 	 		 	 	 																	{
 	 		 	 	 																		ubicados [r1] = true;
 	 		 	 	 																		orden[r1] = jugadores.get(t);
 	 		 	 	 																		ubique = true;
 	 		 	 	 																	}
 	 		 	 	 																	else
 	 		 	 	 																		errores++;
 	 		 	 	 													else
 	 		 	 	 														errores++;
 	 		 	 	 												}
 		 	 	 					}
 	 		 	 	 			}
 	 		 	 	 			//Ordeno por rank
 	 		 	 	 			Jugador jug;
 	 		 	 	 			if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
 	 		 	 	 				jug = orden[1];
 	 		 	 	 				orden[1] = orden[2];
 	 		 	 	 				orden[2] = jug;
 	 		 	 	 			}
 	 		 	 	 			if(orden[2] == null || (orden[3] != null && orden[2].getRanking()>orden[3].getRanking())){
 	 		 	 	 				jug = orden[2];
 	 		 	 	 				orden[2] = orden[3];
 	 		 	 	 				orden[3] = jug;
 	 		 	 	 			}
 	 		 	 	 			if(orden[1] == null || (orden[2] != null && orden[1].getRanking()>orden[2].getRanking())){
 	 		 	 	 				jug = orden[1];
 	 		 	 	 				orden[1] = orden[2];
 	 		 	 	 				orden[2] = jug;
 	 		 	 	 			}
 	 		 	 	 			if(orden[5] == null || (orden[6] != null && orden[5].getRanking()>orden[6].getRanking())){
 	 		 	 	 				jug = orden[5];
 	 		 	 	 				orden[5] = orden[6];
 	 		 	 	 				orden[6] = jug;
 	 		 	 	 			}
 	 		 	 	 			if(orden[6] == null || (orden[7] != null && orden[6].getRanking()>orden[7].getRanking())){
 	 		 	 	 				jug = orden[6];
 	 		 	 	 				orden[6] = orden[7];
 	 		 	 	 				orden[7] = jug;
 	 		 	 	 			}
 	 		 	 	 			if(orden[5] == null || (orden[6] != null && orden[5].getRanking()>orden[6].getRanking())){
 	 		 	 	 				jug = orden[5];
 	 		 	 	 				orden[5] = orden[6];
 	 		 	 	 				orden[6] = jug;
 	 		 	 	 			}
 	 		 	 	 			if(orden[9] == null || (orden[10] != null && orden[9].getRanking()>orden[10].getRanking())){
 			 	 	 				jug = orden[9];
 			 	 	 				orden[9] = orden[10];
 			 	 	 				orden[10] = jug;
 			 	 	 			}
 			 	 	 			if(orden[10] == null || (orden[11] != null && orden[10].getRanking()>orden[11].getRanking())){
 			 	 	 				jug = orden[10];
 			 	 	 				orden[10] = orden[11];
 			 	 	 				orden[11] = jug;
 			 	 	 			}
 			 	 	 			if(orden[9] == null || (orden[10] != null && orden[9].getRanking()>orden[10].getRanking())){
 			 	 	 				jug = orden[9];
 			 	 	 				orden[9] = orden[10];
 			 	 	 				orden[10] = jug;
 			 	 	 			}
 			 	 	 			if(orden[13] == null || (orden[14] != null && orden[13].getRanking()>orden[14].getRanking())){
			 	 	 				jug = orden[13];
			 	 	 				orden[13] = orden[14];
			 	 	 				orden[14] = jug;
			 	 	 			}
			 	 	 			if(orden[14] == null || (orden[15] != null && orden[14].getRanking()>orden[15].getRanking())){
			 	 	 				jug = orden[14];
			 	 	 				orden[14] = orden[15];
			 	 	 				orden[15] = jug;
			 	 	 			}
			 	 	 	 }
	return orden;
}
@Override
public boolean equals(Object object)
{
    boolean Mismo = false;

    if (object != null && object instanceof Torneo)
    {
    	Torneo otro = (Torneo) object;
        Mismo = this.Nombre.equals(otro.getNombre());
    }

    return Mismo;
}
public String getNombre() {
	return Nombre;
}
public void setNombre(String nombre) {
	Nombre = nombre;
}
}
