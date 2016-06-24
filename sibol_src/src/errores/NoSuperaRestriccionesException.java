package errores;

public class NoSuperaRestriccionesException extends Exception{
       
    /**
     * Creates a new instance of NoSuperaRestriccionesException
     */
    public NoSuperaRestriccionesException() {
        super();
    }
        
    public NoSuperaRestriccionesException(String mensaje) {
        super(mensaje);
    }
    
}
