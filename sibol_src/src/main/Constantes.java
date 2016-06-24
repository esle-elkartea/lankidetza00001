/*
 * Constantes.java
 *
 * Created on 30 de mayo de 2006, 15:43
 *
 */
package main;

/**
 * Constantes de la aplicaci�n
 *
 * @author sanjose
 */
public class Constantes {
    // tama�o de p�gina (A4 apaisado)
    public static int TAMPAG_WIDTH = 842 * 2;
    public static int TAMPAG_HEIGHT = 595 * 2;
    // distancia de margen para el scroll de p�gina
    public static int TAMPAG_OFFSET = 500;
    // desplazamiento para situar el gr�fico en el PDF
    public static int PDF_SCALE = 48;
    //public static int PDF_OFFSETX = 20;
    public static int PDF_OFFSETX = 10;
    //public static int PDF_OFFSETY = -230;
    public static int PDF_OFFSETY = 18;
    // desplazamiento para situar el texto en el PDF
    public static int PDF_OFFSETX_TEXTO = 420;
    public static int PDF_OFFSETY_TITULAR = 68;
    public static int PDF_OFFSETY_DIRECION1 = 42;
    public static int PDF_OFFSETY_DIRECION2 = 26;
    // punto de inserci�n inicial de una nueva celda
    public static int CELL_STARTX = 50;
    public static int CELL_STARTY = 50;
    // tama�o al que est�n dibujados los s�mbolos
    public static int SYMBOL_WIDTH = 100;
    public static int SYMBOL_HEIGHT = 100;
    // anchura de las reglas
    public static int RULER_WIDTH = 15;
    // ajuste de posici�n X de los puertos superior e inferior
    public static int PORT_XOFFSET = 2;
    // ajuste de posici�n Y de los puertos laterales
    public static int PORT_YOFFSET = 0;
    // tama�o de la rejilla
    public static int GRID_SIZE = 10;
    // tama�o inicial de las celdas
    public static int CELL_WIDTH = 100;
    public static int CELL_HEIGHT = 100;
    // tama�o m�nimo inicial de las celdas de texto
    public static int TEXT_WIDTH = 200;
    public static int TEXT_HEIGHT = 12;
    // tama�o m�nimo inicial de los rect�ngulos
    public static int RECTANGLE_WIDTH = 25;
    public static int RECTANGLE_HEIGHT = 25;
    // posici�n relativa de los textos respecto a los s�mbolos...
    // ...para los textos que aparecen a la derecha del s�mbolo
    public static int LABEL_POSX = -16;
    public static int LABEL_POSY = 22;
    // ...para los textos que aparecen debajo del s�mbolo, en el cajet�n
    public static int LABEL_POSX2 = 12;
    public static int LABEL_POSY2 = 16;
    // dimensiones del cajet�n de las salidas
    public static int CAJETIN_ANCHO = 100;
    public static int CAJETIN_ALTO = 112;
    // correcci�n de posici�n del cajet�n de las salidas
    public static int CAJETIN_POSX = 0;
    public static int CAJETIN_POSY = 6;
    // n�mero de filas del cuadro de datos de las salidas
    public static int NUMFILAS_SALIDA = 8;
    // n�mero de elementos soportados para drag-and-drop (para que no salga recuadro delimitador de arrastre)
    public static int MAX_ELEMENTOS_DRAG = 1000;
    // sufijos del nombre del SVG para los s�mbolos cuando est�n editados o no
    public static String SVG_EDITADO = "";
    public static String SVG_NOEDITADO = "";
    // variables de la clase Session
    // FrmNuevaInst.java
    public static String SES_ES_SIN_PROYECTO = "SINPROYECTO";    // Indica si el dise�o es sin proyecto
    public static String SES_ES_CON_BLOQUE_VIVIENDAS = "BLOQUES";    // Indica si el dise�o es con bloque de viviendas
    public static String SES_POTENCIA_PREVISTA = "POTENCIA";    // Potencia estimada para la nueva instalaci�n
    public static String SES_TENSION1 = "TENSION1";     // Tensi�n 1 de la instalaci�n
    public static String SES_TENSION2 = "TENSION2";     // Tensi�n 2 de la instalaci�n
    public static String SES_TIPO_INSTALACION_ELEGIDA = "TIPOS_INSTALACION_ELEGIDA";    // Tipo de instalaci�n seleccionada.
    // FrmDatosGestion.java
    public static String SES_INSTALADORES_ID = "ITID";    // Pesta�a Empresa/Instalador
    public static String SES_TECNICOS_CUALIFICADOS_ID = "TCID";    // Pesta�a T�cnico cualificado
    // Inserci�n/modificaci�n en la bd
    public static String SES_INSTALACIONES_ID = "INID";    // Indica si el dise�o es sin proyecto
    // InstComprendidas.java acceder a los indices de los arrays;
    public static int A = 1;
    public static int B = 2;
    public static int C = 3;
    public static int D = 4;
    public static int E = 5;
    public static int F = 6;
    public static int G = 7;
    public static int H = 8;
    public static int I = 9;
    public static int J = 10;
    public static int K = 11;
    public static int L = 12;
    public static int M = 13;
    // N�s de las pesta�as de FrmDatosTecnicosPnl.jTbbdPnDatosTecnicos
    public static final int SUMINISTRO = 0;
    public static final int INST_COMPRENDIDAS = 1;
    public static final int CARCT_GENERALES = 2;
    public static final int CARGAS_IND = 3;
    public static final int CARGAS_VIV = 4;
    public static final int CARGAS_OFI = 5;
    public static final int RESUMEN = 6;
    // Colores
    public static final int GRIS_COMBOS = 230;
    // Tipos de l�neas del cuadro de circuitos
    public static final int ACOMETIDA = 0;   
    public static final int LINEA_GENERAL = 1;
    public static final int INSTALACIONES_INDUSTRIALES_AGRARIAS_SERVICIOS = 2;
    public static final int DERIVACION_INDIVIDUAL = 3;
    public static final int VIVIENDAS_TIPO = 4;
    public static final int ALUMBRADO =5;
    public static final int FUERZA =6;

    // IDs de pantallas
    public static final int PANTALLA_ESQUEMA = 1;
    public static final int PANTALLA_DATOS_GESTION = 2;
    public static final int PANTALLA_DATOS_TECNICOS = 3;
    public static final int PANTALLA_TRAMITAR = 4;

    // validaci�n XSD
    public static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    public static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
    public static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
}
