/*
 * FrmTramitarPnl.java
 *
 * Created on 26 de octubre de 2006, 13:53
 */
package forms;

import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exceptionhandler.BrowserLauncherErrorHandler;
import errores.NoSuperaRestriccionesException;
import forms.recoger.ValidarDatos;
import funciones.Config;
import funciones.HTMLEncode;
import funciones.Sesion;
import funciones.Utilidades;
import funciones.UtilidadesSQL;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import main.Constantes;
import main.Mensaje;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import xml.GenerarFlujoXML;

/**
 *
 * @author  sanjose
 */
public class FrmTramitarPnl extends javax.swing.JPanel {

    /** Creates new form FrmTramitarPnl */
    public FrmTramitarPnl() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLblVerificando = new javax.swing.JLabel();
        jLblPDF = new javax.swing.JLabel();
        jLblXML = new javax.swing.JLabel();
        jLblTramitacion = new javax.swing.JLabel();
        jBtnTramitar = new javax.swing.JButton();
        jLblXMLValidar = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/logo_sibol.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("A continuaci�n se va a proceder a realizar la tramitaci�n con Industria.");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLblVerificando.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
        jLblVerificando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/jgraph.gif"))); // NOI18N
        jLblVerificando.setText("Verificando datos de la instalaci�n");

        jLblPDF.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
        jLblPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/jgraph.gif"))); // NOI18N
        jLblPDF.setText("Generando PDF del esquema unifiliar de la instalaci�n");

        jLblXML.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
        jLblXML.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/jgraph.gif"))); // NOI18N
        jLblXML.setText("Generando XML para la tramitaci�n con Industria");

        jLblTramitacion.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
        jLblTramitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/jgraph.gif"))); // NOI18N
        jLblTramitacion.setText("<html>Iniciando la tramitaci�n con Industria<br>(por favor, espere mientras se abre el navegador)</html>");

        jBtnTramitar.setText("Iniciar la tramitaci�n con Industria");
        jBtnTramitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTramitarActionPerformed(evt);
            }
        });

        jLblXMLValidar.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
        jLblXMLValidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/jgraph.gif"))); // NOI18N
        jLblXMLValidar.setText("Validando XML");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jBtnTramitar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 235, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLblTramitacion)
                    .add(jLblXMLValidar)
                    .add(jLblVerificando)
                    .add(jLblPDF)
                    .add(jLblXML))
                .addContainerGap(177, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jLabel2)
                .add(17, 17, 17)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLblVerificando)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLblPDF)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLblXML))
                    .add(jBtnTramitar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLblXMLValidar)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLblTramitacion)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 225, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 93, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnTramitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTramitarActionPerformed
        Rectangle r;
        cambiarLabel(jLblVerificando, false);
        cambiarLabel(jLblPDF, false);
        cambiarLabel(jLblXML, false);
        cambiarLabel(jLblXMLValidar, false);
        cambiarLabel(jLblTramitacion, false);

        Cursor oldCursor = this.getCursor();
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

        FrmPrincipal frmPrincipal = (FrmPrincipal) Sesion.getInstance().getValorHt("objFrmPrincipal");
        FrmGraficoPnl frm = frmPrincipal.getFrmGrafico();
        int idInstalacion = ((Integer) Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID)).intValue();

        cambiarLabel(jLblVerificando, true);

        ValidarDatos validar = null;
        validar = new ValidarDatos();

        validar.procesarDatosGestionPnl();
        if (!validar.esValidoDatosGestion()) {
            this.setCursor(oldCursor);
            Mensaje.error("Se han producido errores en el apartado DATOS DE GESTION:\n" + validar.getTxtErrorDatosGestion());
            return;
        }
        validar.procesarDatosTecnicosPnl();
        if (!validar.esValidoDatosTecnicos()) {
            this.setCursor(oldCursor);
            Mensaje.error("Se han producido errores en el apartado DATOS TECNICOS:\n" + validar.getTxtErrorDatosTecnicos());
            return;
        }
        boolean vivienda = UtilidadesSQL.esVivienda();
        boolean reforma = UtilidadesSQL.esReforma();
        String[] resultado = frm.verificarEsquema(vivienda, reforma);
        if (resultado != null) {
            if (null != resultado[0]) {
                this.setCursor(oldCursor);
                Mensaje.error("Se han producido errores en el apartado ESQUEMA UNIFILIAR:\n" + resultado[0]);
                return;
            } else if (null != resultado[1]) {
                JOptionPane optionPane = new JOptionPane();
                Object[] opciones = {"Si", "No"};
                int ret = optionPane.showOptionDialog(null, "Aviso en el apartado ESQUEMA UNIFILIAR:\n" + resultado[1] + "\n�Desea continuar?", "SIBOL", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                if (ret != JOptionPane.YES_OPTION) {
                    this.setCursor(oldCursor);
                    return;
                }
            }
        }

        cambiarLabel(jLblPDF, true);

        String esquema = null;
        try {
            esquema = frm.obtenerPDFGrafoBase64();
        } catch (NoSuperaRestriccionesException ex) {
            this.setCursor(oldCursor);
            Mensaje.error("Error al generar el BASE64\n" + ex.getMessage());
            return;
        }

        cambiarLabel(jLblXML, true);

        GenerarFlujoXML flujoXML = new GenerarFlujoXML(idInstalacion, esquema);
        String xml = flujoXML.getStrXML();
        if ((xml != null) && !xml.equals("")) {
            // validaci�n contra XSD
            cambiarLabel(jLblXMLValidar, true);

            class MyErrorHandler implements ErrorHandler {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    Mensaje.info("AVISO (linea " + exception.getLineNumber() + "): " + exception.getMessage(), false);
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw new SAXException("ERROR (linea " + exception.getLineNumber() + "): " + exception.getMessage());
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw new SAXException("ERROR FATAL (linea " + exception.getLineNumber() + "): " + exception.getMessage());
                }
            }

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            schemaFactory.setErrorHandler(new MyErrorHandler());
            try {
                File ficheroXSD = new File(Config.getInstance().getParametro("ESQUEMA_XSD"));
                Schema schemaXSD = schemaFactory.newSchema(ficheroXSD);
                Validator validator = schemaXSD.newValidator();
                validator.setErrorHandler(new MyErrorHandler());
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                factory.setValidating(true);
                factory.setAttribute(Constantes.JAXP_SCHEMA_LANGUAGE, Constantes.W3C_XML_SCHEMA);
                //factory.setAttribute(Constantes.JAXP_SCHEMA_SOURCE, ficheroXSD);
                DocumentBuilder parser = factory.newDocumentBuilder();
                Document document = parser.parse(new ByteArrayInputStream(xml.getBytes()));
                validator.validate(new DOMSource(document));
            }catch (Exception ex) {
                this.setCursor(oldCursor);
                Mensaje.error("Error al validar XML: " + ex.getMessage());
                return;
            }

            cambiarLabel(jLblTramitacion, true);

            // generamos el HTML de "enganche"
            StringBuffer htmlTramitacion = new StringBuffer();
            htmlTramitacion.append("<html>\n");
            htmlTramitacion.append("<head>\n");
            htmlTramitacion.append("<title>" + Config.getInstance().getParametro("ABREVIATURA") + "</title>\n");
            htmlTramitacion.append("</head>\n");
            htmlTramitacion.append("<h1>" + Config.getInstance().getParametro("ABREVIATURA") + "</h1>\n");
            htmlTramitacion.append("<h2>" + Config.getInstance().getParametro("TITULO") + "</h2><br/>\n");
            htmlTramitacion.append("<strong>Iniciando la tramitaci�n con Industria</strong>\n<br/><br/>");
            htmlTramitacion.append("Por favor, espere mientras se env�an los datos...\n");
            htmlTramitacion.append("<body onload='document.tramitar.submit()'>\n");
            htmlTramitacion.append("<form action='" + Config.getInstance().getParametro("URL_INDUSTRIA") + "' method='post' name='tramitar'>\n");
            htmlTramitacion.append("<input type='hidden' name='" + Config.getInstance().getParametro("URL_PARAMETRO_XML") + "' value='" + HTMLEncode.encode(xml) + "'>");
            htmlTramitacion.append("</form>\n");
            htmlTramitacion.append("</body>\n");
            htmlTramitacion.append("</html>\n");

            try {
                if (Config.getInstance().getParametro("DEBUG_XML").equals("S")) {
                    File tempXML = File.createTempFile("tramitacion_industria", ".xml");
                    BufferedWriter outXML = new BufferedWriter(new FileWriter(tempXML));
                    outXML.write(xml);
                    outXML.close();
                }

                File temp = File.createTempFile("tramitacion_industria", ".html");
                BufferedWriter out = new BufferedWriter(new FileWriter(temp));
                out.write(htmlTramitacion.toString());
                out.close();
                temp.deleteOnExit();

                this.setCursor(oldCursor);

                String url = temp.toURI().toURL().toString();

                BrowserLauncher launcher = new BrowserLauncher(null, new TramitarErrorHandler());

                // forzamos el uso de Internet Explorer (si est� disponible)
                launcher.openURLinBrowser("IE", url);

                this.setCursor(oldCursor);
            } catch (IOException e) {
                this.setCursor(oldCursor);
                Mensaje.error("Error al crear el fichero HTML de enlace con la tramitaci�n con Industria");
            } catch (Exception ex) {
                this.setCursor(oldCursor);
                Mensaje.error("Error al generar los datos de enlace con la tramitaci�n con Industria: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_jBtnTramitarActionPerformed

    private void cambiarLabel(JLabel lbl, boolean activar) {
        if (activar) {
            lbl.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.foreground"));
        } else {
            lbl.setForeground(javax.swing.UIManager.getDefaults().getColor("Label.disabledForeground"));
        }
        Rectangle r = lbl.getBounds();
        r.x = 0;
        r.y = 0;
        lbl.paintImmediately(r);
    }

    private static class TramitarErrorHandler implements BrowserLauncherErrorHandler {

        TramitarErrorHandler() {
        }

        public void handleException(Exception e) {
            Mensaje.error(e.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnTramitar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLblPDF;
    private javax.swing.JLabel jLblTramitacion;
    private javax.swing.JLabel jLblVerificando;
    private javax.swing.JLabel jLblXML;
    private javax.swing.JLabel jLblXMLValidar;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}