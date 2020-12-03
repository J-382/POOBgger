package dominio;

/**
 * Exception class for the Frogger's implementation
 * @version 1.0
 * @author Angie Medina - Jose Perez
 *
 */
public class POOggerException extends Exception{
	public static final String ERROR_ABRIR = "An error has occur. Cannot open file ";
	public static final String ERROR_SALVAR = "An error has occur. Cannot save file ";
	public static final String ERROR_EXPORTAR = "An error has occur. Cannot export file";
	public static final String ERROR_IMPORTAR = "An error has occur. Cannot import file";
	public static final String CLASE_NO_ENCONTRADA = "Class not found";
	public static final String TIPO_ERRONEO_DAT = "The file's extension is wrong, it must be a .dat";
	public static final String TIPO_ERRONEO_TXT = "The file's extension is wrong, it must be a .txt";
	public static final String ARCHIVO_NO_ENCONTRADO = "That file doesn't exists";
	public static final String ERROR_INFORMACION = "The information doesn't match with the structure";
	public POOggerException(String message) {
		super(message);
	}
}
