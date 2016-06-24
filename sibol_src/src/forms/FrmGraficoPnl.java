/*
 * FrmGraficoPnl.java
 *
 * Created on 21 de julio de 2006, 12:39
 */

package forms;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import errores.NoSuperaRestriccionesException;
import funciones.Base64;
import funciones.BaseDatos;
import funciones.Config;
import funciones.ExampleFileFilter;
import funciones.Sesion;
import funciones.UtilidadesSQL;
import graph.Grabador;
import graph.Verificador;
import graph.beans.CCBean;
import graph.beans.CTBean;
import graph.beans.D1Bean;
import graph.beans.FUSBean;
import graph.beans.GRDBean;
import graph.beans.ICCBean;
import graph.beans.ICPBean;
import graph.beans.IGBean;
import graph.MyGraph;
import graph.MyGraphModel;
import graph.beans.AcomBean;
import graph.beans.CGPBean;
import graph.beans.ContactoBean;
import graph.beans.GenericBean;
import graph.beans.IG2Bean;
import graph.beans.KABean;
import graph.beans.M1Bean;
import graph.beans.RDBean;
import graph.beans.SBean;
import graph.beans.SchucoBean;
import graph.beans.VABean;
import graph.beans.ZetacBean;
import graph.cell.CajetinSCell;
import graph.cell.FantasmaCell;
import graph.cell.RectangleCell;
import graph.cell.SymbolAcomCell;
import graph.cell.SymbolCCCell;
import graph.cell.SymbolCC2Cell;
import graph.cell.SymbolCGPCell;
import graph.cell.SymbolCTCell;
import graph.cell.SymbolContactoACell;
import graph.cell.SymbolContactoCCell;
import graph.cell.SymbolD1Cell;
import graph.cell.SymbolD2Cell;
import graph.cell.SymbolEQ1Cell;
import graph.cell.SymbolEQ2Cell;
import graph.cell.SymbolFTCell;
import graph.cell.SymbolFUS2Cell;
import graph.cell.SymbolFUSCell;
import graph.cell.SymbolGRDCell;
import graph.cell.SymbolICCCell;
import graph.cell.SymbolICPCell;
import graph.cell.SymbolIG2Cell;
import graph.cell.SymbolIGCell;
import graph.cell.SymbolKA2Cell;
import graph.cell.SymbolKACell;
import graph.cell.SymbolM1Cell;
import graph.cell.SymbolRDCell;
import graph.cell.SymbolRJCell;
import graph.cell.SymbolS1Cell;
import graph.cell.SymbolS2Cell;
import graph.cell.SymbolSchucoCell;
import graph.cell.SymbolVACell;
import graph.cell.SymbolVoltCell;
import graph.cell.SymbolZetacCell;
import graph.cell.TextCell;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.BeanInfo;
import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.Expression;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PersistenceDelegate;
import java.beans.PropertyDescriptor;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import main.Constantes;
import main.Mensaje;
import org.jgraph.JGraph;
import org.jgraph.graph.AbstractCellView;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.PortView;
import org.jgraph.graph.VertexView;
import org.jgraph.plaf.basic.BasicGraphUI;

/**
 *
 * @author  sanjose
 */
public class FrmGraficoPnl extends javax.swing.JPanel implements KeyListener, MouseListener {
    private GraphModel model = null;
    private MyGraph graph = null;
    private String simboloSeleccionado = null;
    private int x0, y0, x1, y1;
    
    /**
     * Creates new form FrmGraficoPnl
     */
    public FrmGraficoPnl() {
        initComponents();
        initJGraph();
        cargarXML();
    }
    
    private void initJGraph() {
        // construímos el modelo y el gráfico
        //model = new DefaultGraphModel();
        model = new MyGraphModel();
        graph = new MyGraph(model);
        graph.setPreferredSize(new Dimension(Constantes.TAMPAG_WIDTH + Constantes.TAMPAG_OFFSET, Constantes.TAMPAG_HEIGHT + Constantes.TAMPAG_OFFSET));
        // establecemos el máximo número de elementos para arrastrar y que no nos salga el recuadro de arrastre
        // esto evita problemas de "descuadre" en el "grid" cuando arrastramos muchos elementos del gráfico
        ((BasicGraphUI) graph.getUI()).MAXCELLS = Constantes.MAX_ELEMENTOS_DRAG;
        ((BasicGraphUI) graph.getUI()).MAXHANDLES = Constantes.MAX_ELEMENTOS_DRAG;
        jScrollPaneGraph.setViewportView(graph);
        instalarListeners(graph);
        insertarSimboloFantasma();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jFileChooser = new javax.swing.JFileChooser();
        jScrollPaneGraph = new javax.swing.JScrollPane();
        jToolBarMain = new javax.swing.JToolBar();
        jBtnCortar = new javax.swing.JButton();
        jBtnCopiar = new javax.swing.JButton();
        jBtnPegar = new javax.swing.JButton();
        jBtnBorrar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jBtnAumentarZoom = new javax.swing.JButton();
        jBtnDisminuirZoom = new javax.swing.JButton();
        jBtnZoomNormal = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jBtnGuardarImagen = new javax.swing.JButton();
        jBtnGuardarPDF = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        jBtnEsquemasTipo = new javax.swing.JButton();
        jToolBarSimbolos = new javax.swing.JToolBar();
        jBtnSimbCGP = new javax.swing.JButton();
        jBtnSimbFUS = new javax.swing.JButton();
        jBtnSimbFUS2 = new javax.swing.JButton();
        jBtnSimbCC = new javax.swing.JButton();
        jBtnSimbCC2 = new javax.swing.JButton();
        jBtnSimbICP = new javax.swing.JButton();
        jBtnSimbIG = new javax.swing.JButton();
        jBtnSimbIG2 = new javax.swing.JButton();
        jBtnSimbRD = new javax.swing.JButton();
        jBtnSimbM1 = new javax.swing.JButton();
        jBtnSimbD1 = new javax.swing.JButton();
        jBtnSimbD2 = new javax.swing.JButton();
        jToolBarSimbolos2 = new javax.swing.JToolBar();
        jBtnSimbCT = new javax.swing.JButton();
        jBtnSimbICC = new javax.swing.JButton();
        jBtnSimbVA = new javax.swing.JButton();
        jBtnSimbZetac = new javax.swing.JButton();
        jBtnSimbSchuco = new javax.swing.JButton();
        jBtnSimbContactoA = new javax.swing.JButton();
        jBtnSimbContactoC = new javax.swing.JButton();
        jBtnSimbVolt = new javax.swing.JButton();
        jBtnSimbRJ = new javax.swing.JButton();
        jToolBarSimbolos3 = new javax.swing.JToolBar();
        jBtnSimbFT = new javax.swing.JButton();
        jBtnSimbKA = new javax.swing.JButton();
        jBtnSimbKA2 = new javax.swing.JButton();
        jBtnSimbAcom = new javax.swing.JButton();
        jBtnSimbEQ1 = new javax.swing.JButton();
        jBtnSimbEQ2 = new javax.swing.JButton();
        jBtnCajetinSalidas = new javax.swing.JButton();
        jBtnTXT = new javax.swing.JButton();
        jBtnRecuadro = new javax.swing.JButton();
        jToolBarSimbolos4 = new javax.swing.JToolBar();
        jBtnSimbS1 = new javax.swing.JButton();
        jBtnSimbS2 = new javax.swing.JButton();
        jBtnSimbGRD = new javax.swing.JButton();

        jFileChooser.setAcceptAllFileFilterUsed(false);
        jFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);

        jToolBarMain.setFloatable(false);
        jBtnCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/cut.gif")));
        jBtnCortar.setText("Cortar");
        jBtnCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCortarActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnCortar);

        jBtnCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/copy.gif")));
        jBtnCopiar.setText("Copiar");
        jBtnCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCopiarActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnCopiar);

        jBtnPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/paste.gif")));
        jBtnPegar.setText("Pegar");
        jBtnPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPegarActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnPegar);

        jBtnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/delete.gif")));
        jBtnBorrar.setText("Borrar");
        jBtnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnBorrarActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnBorrar);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(1, 24));
        jSeparator2.setPreferredSize(new java.awt.Dimension(1, 10));
        jToolBarMain.add(jSeparator2);

        jBtnAumentarZoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/zoomin.gif")));
        jBtnAumentarZoom.setText("Zoom +");
        jBtnAumentarZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAumentarZoomActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnAumentarZoom);

        jBtnDisminuirZoom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/zoomout.gif")));
        jBtnDisminuirZoom.setText("Zoom -");
        jBtnDisminuirZoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDisminuirZoomActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnDisminuirZoom);

        jBtnZoomNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/zoom.gif")));
        jBtnZoomNormal.setText("1:1");
        jBtnZoomNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnZoomNormalActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnZoomNormal);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setMaximumSize(new java.awt.Dimension(1, 24));
        jSeparator3.setPreferredSize(new java.awt.Dimension(1, 10));
        jToolBarMain.add(jSeparator3);

        jBtnGuardarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/export.gif")));
        jBtnGuardarImagen.setText("Exportar como imagen");
        jBtnGuardarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarImagenActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnGuardarImagen);

        jBtnGuardarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/pdf.gif")));
        jBtnGuardarPDF.setText("Exportar a PDF");
        jBtnGuardarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGuardarPDFActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnGuardarPDF);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setMaximumSize(new java.awt.Dimension(1, 24));
        jSeparator4.setPreferredSize(new java.awt.Dimension(1, 10));
        jToolBarMain.add(jSeparator4);

        jBtnEsquemasTipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/esquema.gif")));
        jBtnEsquemasTipo.setText("Esquemas tipo");
        jBtnEsquemasTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEsquemasTipoActionPerformed(evt);
            }
        });

        jToolBarMain.add(jBtnEsquemasTipo);

        jToolBarSimbolos.setFloatable(false);
        jBtnSimbCGP.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbCGP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/cgp.png")));
        jBtnSimbCGP.setToolTipText("Caja general de protecci\u00f3n");
        jBtnSimbCGP.setAlignmentY(1.0F);
        jBtnSimbCGP.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbCGP.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbCGP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbCGPActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbCGP);

        jBtnSimbFUS.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbFUS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/fus1.png")));
        jBtnSimbFUS.setToolTipText("Fusible");
        jBtnSimbFUS.setAlignmentY(1.0F);
        jBtnSimbFUS.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbFUS.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbFUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbFUSActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbFUS);

        jBtnSimbFUS2.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbFUS2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/fus2.png")));
        jBtnSimbFUS2.setToolTipText("Fusible");
        jBtnSimbFUS2.setAlignmentY(1.0F);
        jBtnSimbFUS2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbFUS2.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbFUS2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbFUS2ActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbFUS2);

        jBtnSimbCC.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/cc1.png")));
        jBtnSimbCC.setToolTipText("Cuadro de contadores");
        jBtnSimbCC.setAlignmentY(1.0F);
        jBtnSimbCC.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbCC.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbCCActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbCC);

        jBtnSimbCC2.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbCC2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/cc2.png")));
        jBtnSimbCC2.setToolTipText("Cuadro de contadores");
        jBtnSimbCC2.setAlignmentY(1.0F);
        jBtnSimbCC2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbCC2.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbCC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbCC2ActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbCC2);

        jBtnSimbICP.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbICP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/icp.png")));
        jBtnSimbICP.setToolTipText("Interruptor de control de potencia");
        jBtnSimbICP.setAlignmentY(1.0F);
        jBtnSimbICP.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbICP.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbICP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbICPActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbICP);

        jBtnSimbIG.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbIG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/ig1.png")));
        jBtnSimbIG.setToolTipText("Interruptor autom\u00e1tico");
        jBtnSimbIG.setAlignmentY(1.0F);
        jBtnSimbIG.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbIG.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbIG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbIGActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbIG);

        jBtnSimbIG2.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbIG2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/ig2.png")));
        jBtnSimbIG2.setToolTipText("Interruptor autom\u00e1tico + diferencial");
        jBtnSimbIG2.setAlignmentY(1.0F);
        jBtnSimbIG2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbIG2.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbIG2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbIG2ActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbIG2);

        jBtnSimbRD.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbRD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/rd.png")));
        jBtnSimbRD.setToolTipText("Rel\u00e9 diferencial");
        jBtnSimbRD.setAlignmentY(1.0F);
        jBtnSimbRD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbRD.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbRD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbRDActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbRD);

        jBtnSimbM1.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbM1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/m.png")));
        jBtnSimbM1.setToolTipText("Interruptor magnetot\u00e9rmico");
        jBtnSimbM1.setAlignmentY(1.0F);
        jBtnSimbM1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbM1.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbM1ActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbM1);

        jBtnSimbD1.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbD1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/d1.png")));
        jBtnSimbD1.setToolTipText("Interruptor diferencial");
        jBtnSimbD1.setAlignmentY(1.0F);
        jBtnSimbD1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbD1.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbD1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbD1ActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbD1);

        jBtnSimbD2.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbD2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/d2.png")));
        jBtnSimbD2.setToolTipText("Interruptor diferencial");
        jBtnSimbD2.setAlignmentY(1.0F);
        jBtnSimbD2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbD2.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbD2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbD2ActionPerformed(evt);
            }
        });

        jToolBarSimbolos.add(jBtnSimbD2);

        jToolBarSimbolos2.setFloatable(false);
        jBtnSimbCT.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/ct.png")));
        jBtnSimbCT.setToolTipText("Contactor");
        jBtnSimbCT.setAlignmentY(1.0F);
        jBtnSimbCT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbCT.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbCTActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbCT);

        jBtnSimbICC.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbICC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/icc.png")));
        jBtnSimbICC.setToolTipText("Interruptor de corte en carga");
        jBtnSimbICC.setAlignmentY(1.0F);
        jBtnSimbICC.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbICC.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbICC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbICCActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbICC);

        jBtnSimbVA.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbVA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/va.png")));
        jBtnSimbVA.setToolTipText("Varistor");
        jBtnSimbVA.setAlignmentY(1.0F);
        jBtnSimbVA.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbVA.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbVAActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbVA);

        jBtnSimbZetac.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbZetac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/zetac.png")));
        jBtnSimbZetac.setToolTipText("Cetac");
        jBtnSimbZetac.setAlignmentY(1.0F);
        jBtnSimbZetac.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbZetac.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbZetac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbZetacActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbZetac);

        jBtnSimbSchuco.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbSchuco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/schuco.png")));
        jBtnSimbSchuco.setToolTipText("Schuko");
        jBtnSimbSchuco.setAlignmentY(1.0F);
        jBtnSimbSchuco.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbSchuco.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbSchuco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbSchucoActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbSchuco);

        jBtnSimbContactoA.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbContactoA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/contacto_a.png")));
        jBtnSimbContactoA.setToolTipText("Contacto abierto");
        jBtnSimbContactoA.setAlignmentY(1.0F);
        jBtnSimbContactoA.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbContactoA.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbContactoA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbContactoAActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbContactoA);

        jBtnSimbContactoC.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbContactoC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/contacto_c.png")));
        jBtnSimbContactoC.setToolTipText("Contacto cerrado");
        jBtnSimbContactoC.setAlignmentY(1.0F);
        jBtnSimbContactoC.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbContactoC.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbContactoC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbContactoCActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbContactoC);

        jBtnSimbVolt.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbVolt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/volt.png")));
        jBtnSimbVolt.setToolTipText("Medida");
        jBtnSimbVolt.setAlignmentY(1.0F);
        jBtnSimbVolt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbVolt.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbVolt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbVoltActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbVolt);

        jBtnSimbRJ.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbRJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/rj.png")));
        jBtnSimbRJ.setToolTipText("Reloj");
        jBtnSimbRJ.setAlignmentY(1.0F);
        jBtnSimbRJ.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbRJ.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbRJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbRJActionPerformed(evt);
            }
        });

        jToolBarSimbolos2.add(jBtnSimbRJ);

        jToolBarSimbolos3.setFloatable(false);
        jBtnSimbFT.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbFT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/ft.png")));
        jBtnSimbFT.setToolTipText("C\u00e9lula");
        jBtnSimbFT.setAlignmentY(1.0F);
        jBtnSimbFT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbFT.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbFT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbFTActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnSimbFT);

        jBtnSimbKA.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbKA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/ka1.png")));
        jBtnSimbKA.setToolTipText("Temporizador");
        jBtnSimbKA.setAlignmentY(1.0F);
        jBtnSimbKA.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbKA.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbKA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbKAActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnSimbKA);

        jBtnSimbKA2.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbKA2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/ka2.png")));
        jBtnSimbKA2.setToolTipText("Temporizador");
        jBtnSimbKA2.setAlignmentY(1.0F);
        jBtnSimbKA2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbKA2.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbKA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbKA2ActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnSimbKA2);

        jBtnSimbAcom.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbAcom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/acom.png")));
        jBtnSimbAcom.setToolTipText("Acometida");
        jBtnSimbAcom.setAlignmentY(1.0F);
        jBtnSimbAcom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbAcom.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbAcom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbAcomActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnSimbAcom);

        jBtnSimbEQ1.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbEQ1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/eq1.png")));
        jBtnSimbEQ1.setToolTipText("Equipo gen\u00e9rico");
        jBtnSimbEQ1.setAlignmentY(1.0F);
        jBtnSimbEQ1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbEQ1.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbEQ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbEQ1ActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnSimbEQ1);

        jBtnSimbEQ2.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbEQ2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/eq2.png")));
        jBtnSimbEQ2.setToolTipText("Equipo gen\u00e9rico");
        jBtnSimbEQ2.setAlignmentY(1.0F);
        jBtnSimbEQ2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbEQ2.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnSimbEQ2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbEQ2ActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnSimbEQ2);

        jBtnCajetinSalidas.setBackground(new java.awt.Color(255, 255, 255));
        jBtnCajetinSalidas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/cajetin_salida.png")));
        jBtnCajetinSalidas.setToolTipText("Cajet\u00edn para las salidas");
        jBtnCajetinSalidas.setAlignmentY(1.0F);
        jBtnCajetinSalidas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnCajetinSalidas.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnCajetinSalidas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCajetinSalidasActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnCajetinSalidas);

        jBtnTXT.setBackground(new java.awt.Color(255, 255, 255));
        jBtnTXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/text.gif")));
        jBtnTXT.setToolTipText("Texto");
        jBtnTXT.setAlignmentY(1.0F);
        jBtnTXT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnTXT.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTXTActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnTXT);

        jBtnRecuadro.setBackground(new java.awt.Color(255, 255, 255));
        jBtnRecuadro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/recuadro.png")));
        jBtnRecuadro.setToolTipText("Recuadro");
        jBtnRecuadro.setAlignmentY(1.0F);
        jBtnRecuadro.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnRecuadro.setMaximumSize(new java.awt.Dimension(58, 58));
        jBtnRecuadro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRecuadroActionPerformed(evt);
            }
        });

        jToolBarSimbolos3.add(jBtnRecuadro);

        jToolBarSimbolos4.setFloatable(false);
        jBtnSimbS1.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbS1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/s1_2.png")));
        jBtnSimbS1.setToolTipText("Salida directa");
        jBtnSimbS1.setAlignmentY(0.0F);
        jBtnSimbS1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbS1.setMaximumSize(new java.awt.Dimension(58, 116));
        jBtnSimbS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbS1ActionPerformed(evt);
            }
        });

        jToolBarSimbolos4.add(jBtnSimbS1);

        jBtnSimbS2.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbS2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/s2_2.png")));
        jBtnSimbS2.setToolTipText("Salida con borna");
        jBtnSimbS2.setAlignmentY(0.0F);
        jBtnSimbS2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbS2.setMaximumSize(new java.awt.Dimension(58, 116));
        jBtnSimbS2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbS2ActionPerformed(evt);
            }
        });

        jToolBarSimbolos4.add(jBtnSimbS2);

        jBtnSimbGRD.setBackground(new java.awt.Color(255, 255, 255));
        jBtnSimbGRD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/grd.png")));
        jBtnSimbGRD.setToolTipText("Toma tierra");
        jBtnSimbGRD.setAlignmentY(0.0F);
        jBtnSimbGRD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jBtnSimbGRD.setMaximumSize(new java.awt.Dimension(58, 116));
        jBtnSimbGRD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSimbGRDActionPerformed(evt);
            }
        });

        jToolBarSimbolos4.add(jBtnSimbGRD);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jToolBarSimbolos3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .add(jToolBarSimbolos2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
                .add(0, 0, 0)
                .add(jToolBarSimbolos4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
            .add(jToolBarSimbolos, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
            .add(jScrollPaneGraph, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jToolBarMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBarMain, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(jScrollPaneGraph, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .add(0, 0, 0)
                .add(jToolBarSimbolos, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(0, 0, 0)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jToolBarSimbolos2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, 0)
                        .add(jToolBarSimbolos3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jToolBarSimbolos4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnEsquemasTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEsquemasTipoActionPerformed
        FrmPrincipal frmPrincipal = (FrmPrincipal)Sesion.getInstance().getValorHt("objFrmPrincipal");
        DlgEsquemas dlgEsquemas = new DlgEsquemas(frmPrincipal, true);
        dlgEsquemas.setTitle("Esquemas Tipo");
        dlgEsquemas.setLocationRelativeTo(frmPrincipal);
        dlgEsquemas.setVisible(true);
    }//GEN-LAST:event_jBtnEsquemasTipoActionPerformed

    private void jBtnCajetinSalidasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCajetinSalidasActionPerformed
        seleccionarSimbolo("CAJETIN_SALIDA");
    }//GEN-LAST:event_jBtnCajetinSalidasActionPerformed

    private void jBtnGuardarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarPDFActionPerformed
        // establecemos escala 1:1 antes de exportar ya que sino el gráfico sale "cortado" (?)
        Dimension prefSize = graph.getPreferredSize();
        double scale = graph.getScale();
        graph.setPreferredSize(new Dimension(Constantes.TAMPAG_WIDTH + Constantes.TAMPAG_OFFSET, Constantes.TAMPAG_HEIGHT + Constantes.TAMPAG_OFFSET));
        graph.setScale(1);
        
        jBtnZoomNormalActionPerformed(evt);
        
        FrmPrincipal frmPrincipal = (FrmPrincipal)Sesion.getInstance().getValorHt("objFrmPrincipal");
        jFileChooser.setDialogTitle("Guardar como PDF");
        ExampleFileFilter filter = new ExampleFileFilter("pdf", "Documento PDF");
        jFileChooser.resetChoosableFileFilters();
        jFileChooser.setFileFilter(filter);
        int resultado = jFileChooser.showSaveDialog(frmPrincipal);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            if(!file.getName().toLowerCase().endsWith(".pdf")) file = new File(file.getPath()+".pdf");
            // TODO Confirmar la sobreescritura
            Cursor oldCursor = this.getCursor();
            try {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                obtenerPDFGrafo(new FileOutputStream(file));
                this.setCursor(oldCursor);
            } catch (FileNotFoundException ex) {
                this.setCursor(oldCursor);
                Mensaje.error("Error al guardar PDF:  " + ex.getMessage(), ex);
            } catch (IOException ex) {
                this.setCursor(oldCursor);
                Mensaje.error("Error al guardar PDF:  " + ex.getMessage(), ex);
            }
        }
        
        // reestablecemos la escala original
        graph.setPreferredSize(prefSize);
        graph.setScale(scale);
    }//GEN-LAST:event_jBtnGuardarPDFActionPerformed

    private void jBtnGuardarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGuardarImagenActionPerformed
        // establecemos escala 1:1 antes de exportar ya que sino el gráfico sale "cortado" (?)
        Dimension prefSize = graph.getPreferredSize();
        double scale = graph.getScale();
        graph.setPreferredSize(new Dimension(Constantes.TAMPAG_WIDTH + Constantes.TAMPAG_OFFSET, Constantes.TAMPAG_HEIGHT + Constantes.TAMPAG_OFFSET));
        graph.setScale(1);
        
        FrmPrincipal frmPrincipal = (FrmPrincipal)Sesion.getInstance().getValorHt("objFrmPrincipal");
        jFileChooser.setDialogTitle("Guardar como JPG");
        ExampleFileFilter filter = new ExampleFileFilter("jpg", "Imagen JPG");
        jFileChooser.resetChoosableFileFilters();
        jFileChooser.setFileFilter(filter);
        int resultado = jFileChooser.showSaveDialog(frmPrincipal);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            if(!file.getName().toLowerCase().endsWith(".jpg")) file = new File(file.getPath()+".jpg");
            // TODO Confirmar la sobreescritura
            Cursor oldCursor = this.getCursor();
            try {
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                BufferedImage bufferedImage = obtenerImagenGrafo();
                ImageIO.write(bufferedImage, "jpg", file);
                this.setCursor(oldCursor);
            } catch(IOException ioe) {
                this.setCursor(oldCursor);
                Mensaje.error("Error al guardar JPG:  " + ioe.getMessage(), ioe);
            }
        }
        
        // reestablecemos la escala original
        graph.setPreferredSize(prefSize);
        graph.setScale(scale);
    }//GEN-LAST:event_jBtnGuardarImagenActionPerformed
    
    private void jBtnSimbKA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbKA2ActionPerformed
        seleccionarSimbolo("KA2");
    }//GEN-LAST:event_jBtnSimbKA2ActionPerformed
    
    private void jBtnSimbRJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbRJActionPerformed
        seleccionarSimbolo("RJ");
    }//GEN-LAST:event_jBtnSimbRJActionPerformed
    
    private void jBtnSimbFTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbFTActionPerformed
        seleccionarSimbolo("FT");
    }//GEN-LAST:event_jBtnSimbFTActionPerformed
    
    private void jBtnSimbEQ1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbEQ1ActionPerformed
        seleccionarSimbolo("EQ2");
    }//GEN-LAST:event_jBtnSimbEQ1ActionPerformed
    
    private void jBtnSimbEQ2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbEQ2ActionPerformed
        seleccionarSimbolo("EQ1");
    }//GEN-LAST:event_jBtnSimbEQ2ActionPerformed
    
    private void jBtnSimbContactoAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbContactoAActionPerformed
        seleccionarSimbolo("ContactoA");
    }//GEN-LAST:event_jBtnSimbContactoAActionPerformed
    
    private void jBtnSimbContactoCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbContactoCActionPerformed
        seleccionarSimbolo("ContactoC");
    }//GEN-LAST:event_jBtnSimbContactoCActionPerformed
    
    private void jBtnSimbVoltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbVoltActionPerformed
        seleccionarSimbolo("Volt");
    }//GEN-LAST:event_jBtnSimbVoltActionPerformed
    
    private void jBtnSimbKAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbKAActionPerformed
        seleccionarSimbolo("KA");
    }//GEN-LAST:event_jBtnSimbKAActionPerformed
    
    private void jBtnSimbSchucoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbSchucoActionPerformed
        seleccionarSimbolo("Schuco");
    }//GEN-LAST:event_jBtnSimbSchucoActionPerformed
    
    private void jBtnSimbZetacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbZetacActionPerformed
        seleccionarSimbolo("Zetac");
    }//GEN-LAST:event_jBtnSimbZetacActionPerformed
    
    private void jBtnSimbVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbVAActionPerformed
        seleccionarSimbolo("VA");
    }//GEN-LAST:event_jBtnSimbVAActionPerformed
    
    private void jBtnSimbAcomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbAcomActionPerformed
        seleccionarSimbolo("Acom");
    }//GEN-LAST:event_jBtnSimbAcomActionPerformed
    
    private void jBtnSimbD2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbD2ActionPerformed
        seleccionarSimbolo("D2");
    }//GEN-LAST:event_jBtnSimbD2ActionPerformed
    
    private void jBtnSimbIG2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbIG2ActionPerformed
        seleccionarSimbolo("IG2");
    }//GEN-LAST:event_jBtnSimbIG2ActionPerformed
    
    private void jBtnSimbCC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbCC2ActionPerformed
        seleccionarSimbolo("CC2");
    }//GEN-LAST:event_jBtnSimbCC2ActionPerformed
    
    private void jBtnSimbFUS2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbFUS2ActionPerformed
        seleccionarSimbolo("FUS2");
    }//GEN-LAST:event_jBtnSimbFUS2ActionPerformed
    
    private void jBtnTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTXTActionPerformed
        seleccionarSimbolo("TEXTO");
    }//GEN-LAST:event_jBtnTXTActionPerformed
    
    private void jBtnRecuadroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRecuadroActionPerformed
        seleccionarSimbolo("CUADRO");
    }//GEN-LAST:event_jBtnRecuadroActionPerformed
                
    private void jBtnSimbS2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbS2ActionPerformed
        seleccionarSimbolo("S2");
    }//GEN-LAST:event_jBtnSimbS2ActionPerformed
    
    private void jBtnSimbS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbS1ActionPerformed
        seleccionarSimbolo("S1");
    }//GEN-LAST:event_jBtnSimbS1ActionPerformed
    
    private void jBtnSimbGRDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbGRDActionPerformed
        seleccionarSimbolo("GRD");
    }//GEN-LAST:event_jBtnSimbGRDActionPerformed
    
    private void jBtnSimbICCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbICCActionPerformed
        seleccionarSimbolo("ICC");
    }//GEN-LAST:event_jBtnSimbICCActionPerformed
    
    private void jBtnSimbCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbCTActionPerformed
        seleccionarSimbolo("CT");
    }//GEN-LAST:event_jBtnSimbCTActionPerformed
    
    private void jBtnSimbD1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbD1ActionPerformed
        seleccionarSimbolo("D1");
    }//GEN-LAST:event_jBtnSimbD1ActionPerformed
    
    private void jBtnSimbM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbM1ActionPerformed
        seleccionarSimbolo("M1");
    }//GEN-LAST:event_jBtnSimbM1ActionPerformed
    
    private void jBtnSimbRDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbRDActionPerformed
    seleccionarSimbolo("RD");
    }//GEN-LAST:event_jBtnSimbRDActionPerformed
    
    private void jBtnSimbIGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbIGActionPerformed
        seleccionarSimbolo("IG");
    }//GEN-LAST:event_jBtnSimbIGActionPerformed
    
    private void jBtnSimbICPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbICPActionPerformed
        seleccionarSimbolo("ICP");
    }//GEN-LAST:event_jBtnSimbICPActionPerformed
    
    private void jBtnSimbCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbCCActionPerformed
        seleccionarSimbolo("CC");
    }//GEN-LAST:event_jBtnSimbCCActionPerformed
    
    private void jBtnSimbFUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbFUSActionPerformed
        seleccionarSimbolo("FUS");
    }//GEN-LAST:event_jBtnSimbFUSActionPerformed
    
    private void jBtnSimbCGPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSimbCGPActionPerformed
        seleccionarSimbolo("CGP");
    }//GEN-LAST:event_jBtnSimbCGPActionPerformed
    
    private void jBtnZoomNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnZoomNormalActionPerformed
        graph.setPreferredSize(new Dimension(Constantes.TAMPAG_WIDTH + Constantes.TAMPAG_OFFSET, Constantes.TAMPAG_HEIGHT + Constantes.TAMPAG_OFFSET));
        graph.setScale(1);
    }//GEN-LAST:event_jBtnZoomNormalActionPerformed
    
    private void jBtnDisminuirZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDisminuirZoomActionPerformed
        Dimension d = graph.getPreferredSize();
        d.setSize(d.getWidth() / 2, d.getHeight() / 2);
        graph.setPreferredSize(d);
        double escala = graph.getScale() / 2;
        graph.setScale(escala);
    }//GEN-LAST:event_jBtnDisminuirZoomActionPerformed
    
    private void jBtnAumentarZoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAumentarZoomActionPerformed
        Dimension d = graph.getPreferredSize();
        d.setSize(2 * d.getWidth(), 2 * d.getHeight());
        graph.setPreferredSize(d);
        double escala = graph.getScale() * 2;
        graph.setScale(escala);
    }//GEN-LAST:event_jBtnAumentarZoomActionPerformed
    
    private void jBtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnBorrarActionPerformed
        if(!graph.isSelectionEmpty()) {
            Object[] cells = graph.getSelectionCells();
            cells = graph.getDescendants(cells);
            graph.getModel().remove(cells);
        }
    }//GEN-LAST:event_jBtnBorrarActionPerformed
    
    private void jBtnPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPegarActionPerformed
        TransferHandler.getPasteAction().actionPerformed(new ActionEvent(graph, evt.getID(), evt.getActionCommand()));;
    }//GEN-LAST:event_jBtnPegarActionPerformed
    
    private void jBtnCopiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCopiarActionPerformed
        TransferHandler.getCopyAction().actionPerformed(new ActionEvent(graph, evt.getID(), evt.getActionCommand()));;
    }//GEN-LAST:event_jBtnCopiarActionPerformed
    
    private void jBtnCortarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCortarActionPerformed
        TransferHandler.getCutAction().actionPerformed(new ActionEvent(graph, evt.getID(), evt.getActionCommand()));;
    }//GEN-LAST:event_jBtnCortarActionPerformed
    
    private void seleccionarSimbolo(String simbolo) {
        simboloSeleccionado = simbolo;
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        graph.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    }
    
    // escalar los puntos y dimensiones del elemento a insertar en el gráfico
    // en función de la escala actual
    // además ajustamos al "grid"
    private double escalarPunto(int p) {
        double escala = graph.getScale();
        double p1 = (double)p / (double)escala;
        return p1;
    }
    private Rectangle escalarPuntos(int x, int y, int ancho, int alto) {
        // aplicamos la escala
        double x1 = escalarPunto(x);
        double y1 = escalarPunto(y);
        double ancho1 = escalarPunto(ancho);
        double alto1 = escalarPunto(alto);
        
        // ajustamos al grid
        x1 = x1 / (double)Constantes.GRID_SIZE;
        x1 = Math.rint(x1) * (double)Constantes.GRID_SIZE;
        y1 = y1 / (double)Constantes.GRID_SIZE;
        y1 = Math.rint(y1) * (double)Constantes.GRID_SIZE;
        ancho1 = ancho1 / (double)Constantes.GRID_SIZE;
        ancho1 = Math.rint(ancho1) * (double)Constantes.GRID_SIZE;
        alto1 = alto1 / (double)Constantes.GRID_SIZE;
        alto1 = Math.rint(alto1) * (double)Constantes.GRID_SIZE;
        
        return(new Rectangle((int)x1, (int)y1, (int)ancho1, (int)alto1));
    }
    
    private void insertarSimbolo() {
        int x, y, x2, y2;
        
        if(x1 < x0 && y1 < y0) {
            x = x1;
            y = y1;
            x2 = x0;
            y2 = y0;
        }
        else {
            x = x0;
            y = y0;
            x2 =x1;
            y2 = y1;
        }
        
        if(simboloSeleccionado != null) {
            if(simboloSeleccionado.equals("CUADRO")) {
                int ancho = Math.abs(x2 - x);
                int alto = Math.abs(y2 - y);
                createRectangle(x, y, ancho, alto);
            }
            else if(simboloSeleccionado.equals("TEXTO")) {
                int ancho = Math.abs(x2 - x);
                int alto = Math.abs(y2 - y);
                createText(x, y, ancho, alto);
            }
            else if(simboloSeleccionado.equals("CAJETIN_SALIDA")) {
                createCajetinSalida(x, y);
            }
            else {
                // ajustamos para que el punto de inserción sea el del nodo superior
                int a = (int)((double)(Constantes.CELL_WIDTH / 2) * graph.getScale());
                x = x - a;
                if(x < 0) x = 0;

                if(simboloSeleccionado.equals("CGP")) createVertex(new SymbolCGPCell(new CGPBean("CGP","400/230", 100, 63)), 2, x, y);
                else if(simboloSeleccionado.equals("FUS")) createVertex(new SymbolFUSCell(new FUSBean("gG",2,63)), 2, x, y);
                else if(simboloSeleccionado.equals("CC")) createVertex(new SymbolCCCell(new CCBean("CC","En fachada")), 2, x, y);
                else if(simboloSeleccionado.equals("ICP")) createVertex(new SymbolICPCell(new ICPBean("ICP","2",63)), 2, x, y);
                else if(simboloSeleccionado.equals("IG")){
                    String numeroPolos = "4";
                    /*
                    FrmDatosTecnicosPnl frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
                    if(frm!=null){
                        if("230".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos="2";
                        } else if("400".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos="3";
                        } else if("400".equals(frm.jTxtFldTension1.getText()) && "230".equals(frm.jTxtFldTension2.getText())){
                            numeroPolos="4";
                        }
                    }
                    */
                    createVertex(new SymbolIGCell(new IGBean("IG",numeroPolos,100,"0,8 - 1",25)), 2, x, y);
                }
                else if(simboloSeleccionado.equals("RD")) createVertex(new SymbolRDCell(new RDBean("RDif",10,"","")), 2, x, y);
                else if(simboloSeleccionado.equals("M1")){
                    String numeroPolos = "2";
                    FrmDatosTecnicosPnl frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
                    if(frm!=null){
                        if("230".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos="2";
                        } else if("400".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos="3";
                        } else if("400".equals(frm.jTxtFldTension1.getText()) && "230".equals(frm.jTxtFldTension2.getText())){
                            numeroPolos="4";
                        }
                    }
                    createVertex(new SymbolM1Cell(new M1Bean("M1",numeroPolos,10,"C",6)), 2, x, y);
                }
                else if(simboloSeleccionado.equals("D1")){
                    double numeroPolos = 2;
                    FrmDatosTecnicosPnl frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
                    if(frm!=null){
                        if("230".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos=2;
                        } else if("400".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos=3;
                        }
                    }
                    createVertex(new SymbolD1Cell(new D1Bean("ID",numeroPolos,25,30)), 2, x, y);
                }
                else if(simboloSeleccionado.equals("CT")) createVertex(new SymbolCTCell(new CTBean("CA",230,2,25)), 2, x, y);
                else if(simboloSeleccionado.equals("ICC")) createVertex(new SymbolICCCell(new ICCBean("Q1","2",25)), 2, x, y);
                else if(simboloSeleccionado.equals("GRD")) createVertex2(new SymbolGRDCell(new GRDBean("TIERRA","Picas",2,0,0,0)), x, y, false);
                // TODO Instanciar el SBean con los valores por defecto
                else if(simboloSeleccionado.equals("S1")) createVertex2(new SymbolS1Cell(new SBean("C","",1,1,1,0,"Cu",0,230,0,"","H07V-K","",0,0)), x, y, true);
                else if(simboloSeleccionado.equals("S2")) createVertex2(new SymbolS2Cell(new SBean("C","",1,1,1,0,"Cu",0,230,0,"","H07V-K","",0,0)), x, y, true);
                else if(simboloSeleccionado.equals("FUS2")) createVertex(new SymbolFUS2Cell(new FUSBean("gG",2,63)), 2, x, y);
                else if(simboloSeleccionado.equals("CC2")) createVertex(new SymbolCC2Cell(new CCBean("CC","C")), 2, x, y);
                else if(simboloSeleccionado.equals("IG2")){
                    double numeroPolos = 4;
                    /*
                    FrmDatosTecnicosPnl frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
                    if(frm!=null){
                        if("230".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos=2;
                        } else if("400".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos=3;
                        } else if("400".equals(frm.jTxtFldTension1.getText()) && "230".equals(frm.jTxtFldTension2.getText())){
                            numeroPolos=4;
                        }
                    }
                    */
                    createVertex(new SymbolIG2Cell(new IG2Bean("IAD",numeroPolos,100,300,"0,8 - 1",25)), 2, x, y);
                }
                else if(simboloSeleccionado.equals("D2")){
                    double numeroPolos = 2;
                    FrmDatosTecnicosPnl frm = (FrmDatosTecnicosPnl)Sesion.getInstance().getValorHt("objFrmDatosTecnicosPnl");
                    if(frm!=null){
                        if("230".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos=2;
                        } else if("400".equals(frm.jTxtFldTension1.getText()) && ("".equals(frm.jTxtFldTension2.getText())||"0".equals(frm.jTxtFldTension2.getText()))){
                            numeroPolos=3;
                        }
                    }
                    createVertex(new SymbolD2Cell(new D1Bean("ID",numeroPolos,25,30)), 2, x, y);
                }
                else if(simboloSeleccionado.equals("Acom")) createVertex(new SymbolAcomCell(new AcomBean("ACOMETIDA",0,"","O","A")), 1, x, y);
                else if(simboloSeleccionado.equals("VA")) createVertex(new SymbolVACell(new VABean("VA","2","")), 2, x, y);
                else if(simboloSeleccionado.equals("Zetac")) createVertex(new SymbolZetacCell(new ZetacBean("Cetac","3",16)), 1, x, y);
                else if(simboloSeleccionado.equals("Schuco")) createVertex(new SymbolSchucoCell(new SchucoBean("Schuko")), 1, x, y);
                else if(simboloSeleccionado.equals("KA")) createVertex(new SymbolKACell(new KABean("KA",0,0,"Segundos")), 2, x, y);
                else if(simboloSeleccionado.equals("Volt")) createVertex(new SymbolVoltCell(new GenericBean("V","","","")), 1, x, y);
                else if(simboloSeleccionado.equals("ContactoC")) createVertex(new SymbolContactoCCell(new ContactoBean("Contacto","")), 2, x, y);
                else if(simboloSeleccionado.equals("ContactoA")) createVertex(new SymbolContactoACell(new ContactoBean("Contacto","")), 2, x, y);
                else if(simboloSeleccionado.equals("EQ1")) createVertex(new SymbolEQ1Cell(new GenericBean("EQ","","","")), 1, x, y);
                else if(simboloSeleccionado.equals("EQ2")) createVertex(new SymbolEQ2Cell(new GenericBean("EQ","","","")), 1, x, y);
                else if(simboloSeleccionado.equals("FT")) createVertex(new SymbolFTCell(new GenericBean("FT","","","")), 2, x, y);
                else if(simboloSeleccionado.equals("RJ")) createVertex(new SymbolRJCell(new GenericBean("RJ","","","")), 2, x, y);
                else if(simboloSeleccionado.equals("KA2")) createVertex(new SymbolKA2Cell(new KABean("KA",0,0,"Segundos")), 2, x, y);
            }            
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            graph.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            simboloSeleccionado = null;
        }
    }
    
    // inserta texto
    private void createText(int x, int y, int ancho, int alto) {
        DefaultGraphCell cell = new TextCell(new String("Texto"));
        
        if(ancho < Constantes.TEXT_WIDTH) ancho = Constantes.TEXT_WIDTH;
        if(alto < Constantes.TEXT_HEIGHT) alto = Constantes.TEXT_HEIGHT;
        
        // límites
        Rectangle r = escalarPuntos(x, y, ancho, alto);
        GraphConstants.setBounds(cell.getAttributes(), cell.getAttributes().createRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight()));
        
        // se puede redimensionar
        GraphConstants.setConstrained(cell.getAttributes(), false);
        GraphConstants.setSizeable(cell.getAttributes(), true);
        
        graph.getGraphLayoutCache().insert(cell);
    }
    
    // inserta un recuadro
    private void createRectangle(int x, int y, int ancho, int alto) {
        DefaultGraphCell cell = new RectangleCell();
        
        if(ancho < Constantes.RECTANGLE_WIDTH) ancho = Constantes.RECTANGLE_WIDTH;
        if(alto < Constantes.RECTANGLE_HEIGHT) alto = Constantes.RECTANGLE_HEIGHT;
        
        // color
        GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
        
        // límites
        Rectangle r = escalarPuntos(x, y, ancho, alto);
        GraphConstants.setBounds(cell.getAttributes(), cell.getAttributes().createRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight()));
        
        // se puede redimensionar
        GraphConstants.setConstrained(cell.getAttributes(), false);
        GraphConstants.setSizeable(cell.getAttributes(), true);
        
        // puertos
        double pos = GraphConstants.PERMILLE / 2;
        cell.addPort(cell.getAttributes().createPoint(0, 0));
        cell.addPort(cell.getAttributes().createPoint(0, GraphConstants.PERMILLE));
        cell.addPort(cell.getAttributes().createPoint(GraphConstants.PERMILLE, 0));
        cell.addPort(cell.getAttributes().createPoint(GraphConstants.PERMILLE, GraphConstants.PERMILLE));
        // poner puertos en las esquinas y en mitad de los laterales impede redimensionar el recuadro; "sacrificamos" los de los laterales
        //cell.addPort(cell.getAttributes().createPoint(0, pos));
        //cell.addPort(cell.getAttributes().createPoint(GraphConstants.PERMILLE, pos));
        
        graph.getGraphLayoutCache().insert(cell);
    }
    
    private void createCajetinSalida(int x, int y) {
        DefaultGraphCell cell = new CajetinSCell();
        
        // no se puede editar
        GraphConstants.setEditable(cell.getAttributes(), false);
        
        // color
        GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
               
        // límites
        Rectangle r = escalarPuntos(x, y, 0, 0);
        GraphConstants.setBounds(cell.getAttributes(), cell.getAttributes().createRect((int)r.getX(), (int)r.getY(), Constantes.CAJETIN_ANCHO, Constantes.CAJETIN_ALTO));
        
        // no se puede redimensionar
        GraphConstants.setSizeable(cell.getAttributes(), false);
               
        graph.getGraphLayoutCache().insert(cell);
    }
    
    // inserta un símbolo en el gráfico
    private void createVertex(DefaultGraphCell cell, int numPorts, int x, int y) {
        // color
        GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
        
        // alineación de la etiqueta de texto
        GraphConstants.setHorizontalAlignment(cell.getAttributes(), SwingConstants.RIGHT);
        GraphConstants.setVerticalAlignment(cell.getAttributes(), SwingConstants.CENTER);
        
        // límites
        Rectangle r = escalarPuntos(x, y, 0, 0);
        GraphConstants.setBounds(cell.getAttributes(), cell.getAttributes().createRect((int)r.getX(), (int)r.getY(), Constantes.CELL_WIDTH, Constantes.CELL_HEIGHT));
        
        // no se puede redimensionar
        GraphConstants.setSizeable(cell.getAttributes(), false);
        
        // puertos
        double pos = (GraphConstants.PERMILLE / 2) + Constantes.PORT_XOFFSET;
        cell.addPort(cell.getAttributes().createPoint(pos, 0));
        if(numPorts > 1) cell.addPort(cell.getAttributes().createPoint(pos, GraphConstants.PERMILLE));
        
        graph.getGraphLayoutCache().insert(cell);
    }
    
    // inserta un símbolo de salida o de tierra; estos son el doble de altos
    private void createVertex2(DefaultGraphCell cell, int x, int y, boolean tieneCajetin) {
        // color
        GraphConstants.setBorderColor(cell.getAttributes(), Color.BLACK);
        
        // alineación de la etiqueta de texto
        GraphConstants.setHorizontalAlignment(cell.getAttributes(), SwingConstants.RIGHT);
        GraphConstants.setVerticalAlignment(cell.getAttributes(), SwingConstants.CENTER);
        
        // límites
        Rectangle r = escalarPuntos(x, y, 0, 0);
        GraphConstants.setBounds(cell.getAttributes(), cell.getAttributes().createRect((int)r.getX(), (int)r.getY(),
                  Constantes.CELL_WIDTH, (Constantes.CELL_HEIGHT * 2) + (tieneCajetin ? Constantes.CAJETIN_ALTO : 0)));
        
        // no se puede redimensionar
        GraphConstants.setSizeable(cell.getAttributes(), false);
        
        // puertos
        double pos = (GraphConstants.PERMILLE / 2) + Constantes.PORT_XOFFSET;
        cell.addPort(cell.getAttributes().createPoint(pos, 0));
        /*
        pos = (GraphConstants.PERMILLE / 5) + Constantes.PORT_YOFFSET;
        cell.addPort(cell.getAttributes().createPoint(0, pos));
        if(puerto4) cell.addPort(cell.getAttributes().createPoint(GraphConstants.PERMILLE, pos));
        */
        
        graph.getGraphLayoutCache().insert(cell);
    }
    
    // inserta símbolo "fantasma" para permitir scroll por toda la zona de dibujo
    private void insertarSimboloFantasma() {       
        graph.getGraphLayoutCache().insert(new FantasmaCell(graph.getScale()));
    }
    
    // quitar símbolo "fantasma"
    private void quitarSimboloFantasma() {       
        for(int i=0;i<model.getRootCount();i++){
            Object obj = model.getRootAt(i);
            if(obj instanceof FantasmaCell) {
                Object[] array = new Object[1];
                array[0]=obj;
                model.remove(array);
            }
        }
    }
    
    /*
    // inserta símbolo "fantasma" que cubre toda la zona de dibujo  
    private void insertarSimboloFantasmaImagen() {
        graph.getGraphLayoutCache().insert(new FantasmaImageCell(graph.getScale()));
    }
    
    // quitar símbolo "fantasma"
    private void quitarSimboloFantasmaImagen() {       
        for(int i=0;i<model.getRootCount();i++){
            Object obj = model.getRootAt(i);
            if(obj instanceof FantasmaImageCell) {
                Object[] array = new Object[1];
                array[0]=obj;
                model.remove(array);
            }
        }
    }
    */
    
    // configuramos el XMLEncoder para serializar el gráfico a XML
    private void configurarEncoder(XMLEncoder encoder) {
        encoder.setExceptionListener(new ExceptionListener() {
            public void exceptionThrown(Exception e) {
                e.printStackTrace();
            }
        });
        
        encoder.setPersistenceDelegate(MyGraphModel.class, new DefaultPersistenceDelegate(new String[] { "roots", "attributes" }));
        //encoder.setPersistenceDelegate(DefaultGraphModel.class, new DefaultPersistenceDelegate(new String[] { "roots", "attributes" }));
        //encoder.setPersistenceDelegate(GraphModel.class, new DefaultPersistenceDelegate(new String[] { "roots", "attributes" }));
        
        encoder.setPersistenceDelegate(MyGraph.class, new DefaultPersistenceDelegate(new String[] { "model", "cache" }));
        
        encoder.setPersistenceDelegate(GraphLayoutCache.class, new DefaultPersistenceDelegate(new String[] { "model", "factory", "cellViews", "hiddenCellViews", "partial" }));
        encoder.setPersistenceDelegate(DefaultGraphCell.class, new DefaultPersistenceDelegate(new String[] { "userObject" }));
        encoder.setPersistenceDelegate(DefaultPort.class, new DefaultPersistenceDelegate(new String[] { "userObject" }));
        encoder.setPersistenceDelegate(AbstractCellView.class, new DefaultPersistenceDelegate(new String[] { "cell", "attributes" }));
        encoder.setPersistenceDelegate(DefaultEdge.class, new DefaultPersistenceDelegate(new String[] { "userObject" }));
        encoder.setPersistenceDelegate(DefaultEdge.DefaultRouting.class, new PersistenceDelegate() {
            protected Expression instantiate(Object oldInstance, Encoder out) {
                return new Expression(oldInstance, GraphConstants.class, "getROUTING_SIMPLE", null);
            }
        });
        encoder.setPersistenceDelegate(DefaultEdge.LoopRouting.class, new PersistenceDelegate() {
            protected Expression instantiate(Object oldInstance, Encoder out) {
                return new Expression(oldInstance, GraphConstants.class, "getROUTING_DEFAULT", null);
            }
        });
        
        encoder.setPersistenceDelegate(ArrayList.class, encoder.getPersistenceDelegate(List.class));
    }
    
    public static void makeCellViewFieldsTransient(Class c) {
        try {
            BeanInfo info = Introspector.getBeanInfo(c);
            PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor pd = propertyDescriptors[i];
                if (!pd.getName().equals("cell") && !pd.getName().equals("attributes")) {
                    pd.setValue("transient", Boolean.TRUE);
                }
            }
        } catch(IntrospectionException e) {
            e.printStackTrace();
        }
    }
    
    // instalamos los "listeners" necesarios para capturar teclas dentro del gráfico, etc...
    protected void instalarListeners(JGraph graph) {
        graph.addKeyListener(this);
        graph.addMouseListener(this);
        ///graph.getSelectionModel().addGraphSelectionListener(this);
        ///graph.getModel().addUndoableEditListener(undoManager);
        ///graph.getModel().addGraphModelListener(statusBar);
    }
    
    // desinstalamos los "listeners" de teclado y demás
    protected void desinstalarListeners(JGraph graph) {
        graph.removeKeyListener(this);
        graph.removeMouseListener(this);
        ///graph.getSelectionModel().removeGraphSelectionListener(this);
        ///graph.getModel().removeUndoableEditListener(undoManager);
        ///graph.getModel().removeGraphModelListener(statusBar);
    }
    
    /*
    private String comprobarRestricciones(boolean esVivienda) {
        String resultado = null;
        try {
            desinstalarListeners(graph);
            Verificador verificador = new Verificador(esVivienda);
            if(!verificador.cumpleRestricciones(graph.getGraphLayoutCache().getModel())){
                resultado = verificador.getMensajes();
            }
        } catch(Exception e) {
            Mensaje.error("Error al comprobar restricciones en gráfico:  " + e.getMessage(), e);
        } finally {
            instalarListeners(graph);
        }
        return resultado;
    }
    */
    
    public String[] guardarDatosBD(boolean esVivienda, boolean esReforma) {
        String[] resultado = new String[2];
        boolean errores = false;
        try {
            desinstalarListeners(graph);
            Verificador verificador = new Verificador(esVivienda, esReforma);
            if(!verificador.esVacio(graph.getGraphLayoutCache().getModel())){
                if(!verificador.cumpleRestricciones(graph.getGraphLayoutCache().getModel())){
                    resultado[0] = verificador.getMensajes();
                    errores = true;
                }
            }
            if (null!=verificador.getWarning()) {
                resultado[1]=verificador.getWarning();
            }
            Grabador.grabar(graph.getGraphLayoutCache().getModel(),getXML(), errores);
        } catch(Exception e) {
            Mensaje.error("Error al comprobar restricciones en gráfico:  " + e.getMessage(), e);
        } finally {
            instalarListeners(graph);
        }
        return resultado;
    }
    
    public String[] verificarEsquema(boolean esVivienda, boolean esReforma) {
        String[] resultado = new String[2];
        try {
            desinstalarListeners(graph);
            Verificador verificador = new Verificador(esVivienda, esReforma);
            if(!verificador.esVacio(graph.getGraphLayoutCache().getModel())){
                if(!verificador.cumpleRestricciones(graph.getGraphLayoutCache().getModel()))
                    resultado[0] = verificador.getMensajes();
            }
            if (null!=verificador.getWarning()) {
                resultado[1] = verificador.getWarning();
            }
        } catch(Exception e) {
            Mensaje.error("Error al comprobar restricciones en gráfico:  " + e.getMessage(), e);
        } finally {
            instalarListeners(graph);
        }
        return resultado;        
    }
    
    
    public String getXML() {
        String resultado = null;
        try {
            desinstalarListeners(graph);
            quitarSimboloFantasma();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            XMLEncoder e = new XMLEncoder(bos);
            configurarEncoder(e);
            makeCellViewFieldsTransient(PortView.class);
            makeCellViewFieldsTransient(VertexView.class);
            makeCellViewFieldsTransient(EdgeView.class);
            e.writeObject(graph.getGraphLayoutCache());
            e.close();
            resultado = bos.toString();
            bos.close();            
        } catch(Exception e) {
            Mensaje.error("Error al obtener xml:  " + e.getMessage(), e);
        } finally {
            insertarSimboloFantasma();
            instalarListeners(graph);
        }
        return resultado;
    }
    
    public void cargarXML() {
        BaseDatos bd = Sesion.getInstance().getBaseDatos();
        int idInstalacion = ((Integer)Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID)).intValue();
        ResultSet rs = null;
        
        String consulta = "SELECT INXML FROM INSTALACIONES WHERE INID = "+UtilidadesSQL.tratarParametroNumerico(idInstalacion);
        String xml = null;
        try {
            rs = bd.ejecSelect(consulta);
            if(rs.next()) xml = rs.getString(1);
        } catch(Exception e) {
            Mensaje.error("cargarXML en FrmGraficoPnl: "+e.getMessage(),e);
        }
        setXML(xml);
    }
       
    public void setXML(String xml){
        if(xml != null) {
            try {
                desinstalarListeners(graph);
                ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes());
                XMLDecoder d = new XMLDecoder(bis);
                graph.setGraphLayoutCache((GraphLayoutCache)d.readObject());
                jScrollPaneGraph.setViewportView(graph);
                d.close();
                bis.close();
            } catch(Exception e) {
                Mensaje.error("Error al cargar xml:  " + e.getMessage(), e);
            } finally {
                insertarSimboloFantasma();
                instalarListeners(graph);
            }
        }
    }
    
    /*
    private void guardarGrafico() {
        try {
            desinstalarListeners(graph);
            XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("C:/Temp/grafico.xml")));
            configurarEncoder(e);
            makeCellViewFieldsTransient(PortView.class);
            makeCellViewFieldsTransient(VertexView.class);
            makeCellViewFieldsTransient(EdgeView.class);
            quitarSimboloFantasma();
            e.writeObject(graph.getGraphLayoutCache());
            e.close();
            insertarSimboloFantasma();
        } catch(Exception e) {
            Mensaje.error("Error al guardar gráfico:  " + e.getMessage(), e);
        } finally {
            instalarListeners(graph);
        }
    }
    
    private void cargarGrafico() {
        try {
            desinstalarListeners(graph);
            XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("C:/Temp/grafico.xml")));
            //graph = (MyGraph)decoder.readObject();
            graph.setGraphLayoutCache((GraphLayoutCache)decoder.readObject());
            graph.inicializar();
            jScrollPaneGraph.setViewportView(graph);
            insertarSimboloFantasma();
        } catch(Exception e) {
            Mensaje.error("Error al cargar gráfico:  " + e.getMessage(), e);
        } finally {
            instalarListeners(graph);
        }
    }
    */
    
    // KeyListener para la tecla de borrado
    public void keyReleased(KeyEvent e) {
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) jBtnBorrarActionPerformed(null);
    }
    // MouseListener
    public void mouseClicked(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mousePressed(MouseEvent e) {
        if(simboloSeleccionado != null) {
            x0 = e.getX();
            y0 = e.getY();
        }
    }
    public void mouseReleased(MouseEvent e) {
        if(simboloSeleccionado != null) {
            x1 = e.getX();
            y1 = e.getY();
            insertarSimbolo();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAumentarZoom;
    private javax.swing.JButton jBtnBorrar;
    private javax.swing.JButton jBtnCajetinSalidas;
    private javax.swing.JButton jBtnCopiar;
    private javax.swing.JButton jBtnCortar;
    private javax.swing.JButton jBtnDisminuirZoom;
    private javax.swing.JButton jBtnEsquemasTipo;
    private javax.swing.JButton jBtnGuardarImagen;
    private javax.swing.JButton jBtnGuardarPDF;
    private javax.swing.JButton jBtnPegar;
    private javax.swing.JButton jBtnRecuadro;
    private javax.swing.JButton jBtnSimbAcom;
    private javax.swing.JButton jBtnSimbCC;
    private javax.swing.JButton jBtnSimbCC2;
    private javax.swing.JButton jBtnSimbCGP;
    private javax.swing.JButton jBtnSimbCT;
    private javax.swing.JButton jBtnSimbContactoA;
    private javax.swing.JButton jBtnSimbContactoC;
    private javax.swing.JButton jBtnSimbD1;
    private javax.swing.JButton jBtnSimbD2;
    private javax.swing.JButton jBtnSimbEQ1;
    private javax.swing.JButton jBtnSimbEQ2;
    private javax.swing.JButton jBtnSimbFT;
    private javax.swing.JButton jBtnSimbFUS;
    private javax.swing.JButton jBtnSimbFUS2;
    private javax.swing.JButton jBtnSimbGRD;
    private javax.swing.JButton jBtnSimbICC;
    private javax.swing.JButton jBtnSimbICP;
    private javax.swing.JButton jBtnSimbIG;
    private javax.swing.JButton jBtnSimbIG2;
    private javax.swing.JButton jBtnSimbKA;
    private javax.swing.JButton jBtnSimbKA2;
    private javax.swing.JButton jBtnSimbM1;
    private javax.swing.JButton jBtnSimbRD;
    private javax.swing.JButton jBtnSimbRJ;
    private javax.swing.JButton jBtnSimbS1;
    private javax.swing.JButton jBtnSimbS2;
    private javax.swing.JButton jBtnSimbSchuco;
    private javax.swing.JButton jBtnSimbVA;
    private javax.swing.JButton jBtnSimbVolt;
    private javax.swing.JButton jBtnSimbZetac;
    private javax.swing.JButton jBtnTXT;
    private javax.swing.JButton jBtnZoomNormal;
    private javax.swing.JFileChooser jFileChooser;
    public javax.swing.JScrollPane jScrollPaneGraph;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JToolBar jToolBarMain;
    private javax.swing.JToolBar jToolBarSimbolos;
    private javax.swing.JToolBar jToolBarSimbolos2;
    private javax.swing.JToolBar jToolBarSimbolos3;
    private javax.swing.JToolBar jToolBarSimbolos4;
    // End of variables declaration//GEN-END:variables
    
    private BufferedImage obtenerImagenGrafo(){
        BufferedImage resultado = null;
        Object[] selection = graph.getSelectionCells();
        boolean gridVisible = graph.isGridVisible();
        boolean doubleBuffered = graph.isDoubleBuffered();
        double escala = 1;
        graph.setGridVisible(false);
        graph.setDoubleBuffered(false);
        graph.clearSelection();
        Dimension d = graph.getPreferredSize();
        escala = graph.getScale();
        graph.setPreferredSize(new Dimension(Constantes.TAMPAG_WIDTH, Constantes.TAMPAG_HEIGHT));
        graph.setScale(1);
        quitarSimboloFantasma();
        ///insertarSimboloFantasmaImagen();
      
        resultado = graph.getImage(Color.WHITE,0);

        graph.setPreferredSize(d);
        graph.setScale(escala);
        insertarSimboloFantasma();
        ///quitarSimboloFantasmaImagen();
        graph.setSelectionCells(selection);
        graph.setGridVisible(gridVisible);
        graph.setDoubleBuffered(doubleBuffered);
        
        return resultado;
    }
    
    private void obtenerPDFGrafo(OutputStream os){
        Document document = new Document(PageSize.A4.rotate());
        try {
            BufferedImage bufferedImage = obtenerImagenGrafo();
            PdfWriter writer = PdfWriter.getInstance(document, os);
            document.open();
            PdfContentByte cb = writer.getDirectContent();
            Image image = Image.getInstance(bufferedImage, null);
            image.scalePercent(Constantes.PDF_SCALE);
            image.setAbsolutePosition(Constantes.PDF_OFFSETX, Constantes.PDF_OFFSETY);
            //image.setAbsolutePosition((plantilla.scaledWidth()-image.scaledWidth())/2, (plantilla.scaledHeight()-image.scaledHeight())/2);
            cb.addImage(image);
            image = null;
            ///Image plantilla = Image.getInstance(getClass().getResource("/resources/svg/plantilla_plano.png"));
            Image plantilla = Image.getInstance(Config.getInstance().getParametro("PLANTILLA_CAJETIN"));
            plantilla.setAbsolutePosition(0, 0);
            plantilla.scalePercent(Constantes.PDF_SCALE);
            cb.addImage(plantilla);
            
            // añadir texto titular y dirección
            ResultSet rs = null;
            BaseDatos bd = Sesion.getInstance().getBaseDatos();
            String nombreTitular = null;
            String direccion1 = null;
            String direccion2 = null;
            String domicilio, portal, bis, escalera, piso, puerta, cp, idProv, idMun, idLoc;
            String descProv = "";
            String descLoc = "";
            try {
                int idInstalacion = ((Integer)Sesion.getInstance().getValorHt(Constantes.SES_INSTALACIONES_ID)).intValue();
                rs = bd.ejecSelect("SELECT * FROM INSTALACIONES WHERE INID="+UtilidadesSQL.tratarParametroNumerico(idInstalacion));
                if (rs.next()) {
                    nombreTitular = rs.getString("INTITNOMBRE");
                    
                    domicilio = rs.getString("INEMPLAZAMIENTO");
                    
                    portal = rs.getString("INPORTALCAR");
                    bis = rs.getString("INBISCAR");
                    escalera = rs.getString("INESCALERACAR");
                    piso = rs.getString("INPISOCAR");
                    puerta = rs.getString("INPUERTACAR");
                    
                    cp = rs.getString("INCP");
                    idProv = rs.getString("INPRID");
                    idMun = rs.getString("INMUID");
                    idLoc = rs.getString("INLCID");
                    
                    rs = bd.ejecSelect("SELECT PRNOM FROM PROVINCIAS WHERE PRID="+idProv);
                    if (rs.next()) {
                        descProv = rs.getString("PRNOM");
                        rs = bd.ejecSelect("SELECT LCNOM FROM LOCALIDADES WHERE LCPRID="+idProv+" AND LCMUID="+idMun+" AND LCID="+idLoc);
                        if (rs.next()) {
                            descLoc = rs.getString("LCNOM");
                        }
                    }
                    direccion1=domicilio+" "+portal+" "+bis+" "+escalera+" "+piso+" "+puerta;
                    direccion2=cp+" "+descLoc+" ("+descProv+")";
                }
                                
                cb.beginText();
                cb.setColorFill(Color.black);
                cb.setColorStroke(Color.black);
                BaseFont font = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                cb.setFontAndSize(font, 14);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, nombreTitular, Constantes.PDF_OFFSETX_TEXTO, Constantes.PDF_OFFSETY_TITULAR, 0);
                cb.setFontAndSize(font, 10);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, direccion1, Constantes.PDF_OFFSETX_TEXTO, Constantes.PDF_OFFSETY_DIRECION1, 0);
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, direccion2, Constantes.PDF_OFFSETX_TEXTO, Constantes.PDF_OFFSETY_DIRECION2, 0);
                cb.endText();
            } catch(SQLException e){
            } finally {
                try{
                    if(rs!=null)rs.close();
                } catch(SQLException e){}
            }
        } catch(DocumentException de) {
            Mensaje.error("Error al construir PDF:  " + de.getMessage(), de);
        } catch(IOException ioe) {
            Mensaje.error("Error al construir PDF:  " + ioe.getMessage(), ioe);
        }
        document.close();
    }
    
    public String obtenerPDFGrafoBase64() throws NoSuperaRestriccionesException {
        boolean vivienda = UtilidadesSQL.esVivienda();
        boolean reforma = UtilidadesSQL.esReforma();
        Verificador verificador = new Verificador(vivienda, reforma);
        if(!verificador.cumpleRestricciones(this.graph.getModel())) {
            throw new NoSuperaRestriccionesException(verificador.getMensajes());
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        obtenerPDFGrafo(baos);        
        return Base64.encodeBytes(baos.toByteArray());
    }
}
