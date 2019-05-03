
public class Jugador {

private String nombre;
private int ranking;
private String federacion;
private String apellido;
private boolean espreclasificado;
private int preclasific;
private String estado;
private String club;
private String Nacionalidad;
private String TipoDoc;
private String Provincia;
private char genero;
private String FechaNac;
private String Nrodoc;
private String Localidad;
private String Direccion;
private String CP;
private String telefono;
private String Email;
private String Nrocasa;
private String Depto;
private String celular;

public Jugador (String nom, String ape, String fed, String c, String nac, String Tipodoc, String prov,
		char g, String Fecha, String nrodoc, String Loc, String Direc, String cp, String tel, String em,
		String nrocas, String dpto, String cel){
	nombre = nom;
	apellido = ape;
	federacion = fed;
	ranking = -1;
	espreclasificado = false;
	preclasific = 0;
	estado = "";
	club = c;
	Nacionalidad = nac;
	TipoDoc = Tipodoc;
	Provincia = prov;
	genero = g;
	FechaNac = Fecha;
	Nrodoc = nrodoc;
	Localidad = Loc;
	Direccion = Direc;
	CP = cp;
	telefono = tel;
	Email = em;
	Nrocasa = nrocas;
	Depto = dpto;
	celular = cel;
}

public Jugador (String nom, String ape, String fed, int rank, String c, String nac, String Tipodoc, String prov,
		char g, String Fecha, String nrodoc, String Loc, String Direc, String cp, String tel, String em,
		String nrocas, String dpto, String cel){
	nombre = nom;
	apellido = ape;
	federacion = fed;
	ranking = rank;
	espreclasificado = false;
	preclasific = 0;
	estado = "";
	club = c;
	Nacionalidad = nac;
	TipoDoc = Tipodoc;
	Provincia = prov;
	genero = g;
	FechaNac = Fecha;
	Nrodoc = nrodoc;
	Localidad = Loc;
	Direccion = Direc;
	CP = cp;
	telefono = tel;
	Email = em;
	Nrocasa = nrocas;
	Depto = dpto;
	celular = cel;
}
public String getClub() {
	return club;
}
public void setClub(String club) {
	this.club = club;
}
public String getNacionalidad() {
	return Nacionalidad;
}
public void setNacionalidad(String nacionalidad) {
	Nacionalidad = nacionalidad;
}
public String getTipoDoc() {
	return TipoDoc;
}
public void setTipoDoc(String tipoDoc) {
	TipoDoc = tipoDoc;
}
public String getProvincia() {
	return Provincia;
}
public void setProvincia(String provincia) {
	Provincia = provincia;
}
public char getGenero() {
	return genero;
}
public void setGenero(char genero) {
	this.genero = genero;
}
public String getFechaNac() {
	return FechaNac;
}
public void setFechaNac(String fechaNac) {
	FechaNac = fechaNac;
}
public String getNrodoc() {
	return Nrodoc;
}
public void setNrodoc(String nrodoc) {
	this.Nrodoc = nrodoc;
}
public String getLocalidad() {
	return Localidad;
}
public void setLocalidad(String localidad) {
	Localidad = localidad;
}
public String getDireccion() {
	return Direccion;
}
public void setDireccion(String direccion) {
	Direccion = direccion;
}
public String getCP() {
	return CP;
}
public void setCP(String cP) {
	CP = cP;
}
public String getTelefono() {
	return telefono;
}
public void setTelefono(String telefono) {
	this.telefono = telefono;
}
public String getEmail() {
	return Email;
}
public void setEmail(String email) {
	Email = email;
}
public String getNrocasa() {
	return Nrocasa;
}
public void setNrocasa(String nrocasa) {
	Nrocasa = nrocasa;
}
public String getDepto() {
	return Depto;
}
public void setDepto(String depto) {
	Depto = depto;
}
public String getCelular() {
	return celular;
}
public void setCelular(String celular) {
	this.celular = celular;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public Integer getRanking() {
	return ranking;
}

public void setRanking(int ranking) {
	this.ranking = ranking;
}

public String getFederacion() {
	return federacion;
}

public void setFederacion(String federacion) {
	this.federacion = federacion;
}

public String getApellido() {
	return apellido;
}

public void setApellido(String apellido) {
	this.apellido = apellido;
}
public boolean isEspreclasificado() {
	return espreclasificado;
}
public void setEspreclasificado(boolean espreclasificado) {
	this.espreclasificado = espreclasificado;
}
public int getPreclasific() {
	return preclasific;
}
public void setPreclasific(int preclasific) {
	this.preclasific = preclasific;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
@Override
public boolean equals(Object object)
{
    boolean Mismo = false;

    if (object != null && object instanceof Jugador)
    {
    	Jugador otro = (Jugador) object;
        Mismo = (this.getNrodoc().equals(otro.getNrodoc()) && this.getApellido().equals(otro.getApellido()) && this.getNombre().equals(otro.getNombre()) );
    }

    return Mismo;
}


}
