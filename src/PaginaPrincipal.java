/* requires itextpdf-5.1.2.jar or similar */
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;



@SuppressWarnings("serial")
public class PaginaPrincipal extends javax.swing.JFrame {	
	
	//Panel Principal
	private JPanel panel;
	
	private Sistema sist;
	
	//Botones
	private JButton btnCerrar;
	private JButton btnCerrarA;
	private JButton BotonCargarArchivo;
	private JButton BotonReiniciarTorneo;
	private JButton BotonCrearNuevoTorneo;
	private JButton BotonCrearListaAceptacion;
	private JButton BotonReiniciarAAT;
	private JButton BotonReiniciarAAT2;
	private JButton BotonReiniciarAAT3;
	private JButton BotonAbrirTorneo;
	private JButton BotonCrearArchivo;
	private JButton BotonAbrirTorneo2;
	private JButton BotonCrearArchivo2;
	private JButton BotonAbrirTorneo3;
	private JButton BotonCrearArchivo3;
	
	//Labels
	private JLabel label_1;
	private JLabel label;
	private JLabel label_3;
	private JLabel lblAcercaDe4;
	private JLabel lblAcercaDe3;
	private JLabel lblAcercaDe2;
	private JLabel lblAcercaDe1;
	private JLabel lblAyuda4;
	private JLabel lblAyuda3;
	private JLabel lblAyuda2;
	private JLabel lblAyuda1;
	private JLabel lblEncargado;
	private JLabel lblNroTorneo;
	private JLabel LblArchivos;
	private JLabel LblArchivos2;
	private JLabel LblArchivos3;
	private JLabel lblCargoTorneo;
	private JLabel lblCiudad;
	private JLabel lblCiudad2;
	private JLabel lblCargoArchivo;
	private JLabel lblCargoArchivo2;
	private JLabel lblCargoArchivo3;
	
	//Ventana JDialogs Acerca De y Ayuda
	private JDialog dlgAcercaDe;
	private JDialog dlgAyuda;
	
	//Separadores esteticos
	private JSeparator separador1;
	private JSeparator separador2;	
	private JSeparator separador3;
	private JSeparator separador4;
	private JSeparator separador5;
	private JSeparator separador6;
	
	
	//Campos Ingreso texto
	private JTextField CampotxtEncargado;
	private JTextField Ciudad;
	private JTextField Ciudad2;
	private JTextField CampoNroTorneo;
	
	//Menu y sub Menu, barra superior
	private JMenuBar menuBar;
	private JMenu mnGeneral;
	private JMenuItem mntmSalir;
	private JMenuItem menuItem;
	private JMenuItem mntmAcercaDe;
	private JLabel label_31;
	
	private JComboBox<String> Desplegable;
	private JComboBox<String> Desplegable1;
	private JComboBox<String> Desplegable2;
	private JComboBox<String> Desplegable3;
	private String direccion;
	private String direccion2;
	private String direccion3;
	private String direccion4;
	private JOptionPane mensaje;
	private JCheckBox opcionPreviaG1;
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try 
				{
					PaginaPrincipal frame = new PaginaPrincipal();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PaginaPrincipal() 
	{
		super("Federacion Bahiense de Tenis");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./Config/oie_transparent.png"));
		initGUI();
	    
	}
	   
	
	@SuppressWarnings("static-access")
	private void initGUI() 
	{
	   try 
	   {
	      javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
	   } 
	   catch(Exception e) 
	   {
	       e.printStackTrace();
	   }  
	   try {	
		   
	   //Inicializacion y Configuracion del Frame Principal
	   getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
	   this.setTitle("Version Beta - FBH");
	   this.setSize(900, 650);
	   this.setLocationRelativeTo(null);
	   this.setResizable(false);
	   setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	   
	   direccion = "";
	   //Creacion y Agregado del Panel
	   panel = new JPanel();
	   getContentPane().add(panel);
	   panel.setLayout(null);
	   mensaje = new JOptionPane();
	   
	   //Creacion, Configuracion y Agregado de los separadores
	   separador1 = new JSeparator();
	   separador1.setBounds(10, 48, 870, 2);
	   panel.add(separador1);
	   separador3 = new JSeparator();
	   separador3.setBounds(10, 323, 870, 2);
	   panel.add(separador3);
	   separador5 = new JSeparator();
	   separador5.setBounds(10, 573, 870, 2);
	   panel.add(separador5);
	   separador2 = new JSeparator();
	   separador2.setOrientation(SwingConstants.VERTICAL);
	   separador2.setBounds(450, 58, 2, 255);
	   panel.add(separador2);
	   separador6 = new JSeparator();
	   separador6.setOrientation(SwingConstants.VERTICAL);
	   separador6.setBounds(450, 333, 2, 230);
	   panel.add(separador6);
	   
	   //Combobox Grado
	   String[] Options = {"Tipo de torneo", "Grado 1", "Grado 2"
               ,"Grado 3", "Grado 4"};
	   Desplegable = new JComboBox<String>(Options);
	   Desplegable.setBounds(20, 265, 175, 25);
	   panel.add(Desplegable);
	   
	   Desplegable1 = new JComboBox<String>(Options);
	   Desplegable1.setBounds(20, 507, 175, 25);
	   panel.add(Desplegable1);
	   
	   Desplegable2 = new JComboBox<String>(Options);
	   Desplegable2.setBounds(470, 412, 175, 25);
	   panel.add(Desplegable2);
	   
	   Desplegable3 = new JComboBox<String>(Options);
	   Desplegable3.setBounds(470, 120, 175, 25);
	   panel.add(Desplegable3);
	   
	   //Checkbox opcion g1 para crear torneo
	   opcionPreviaG1 = new JCheckBox("Previo G1");
	   opcionPreviaG1.setBounds(200, 270, 75, 15);
	   panel.add(opcionPreviaG1);
	   
	   //Creacion, Configuracion y Agregado de los Labels
	   ImageIcon adm = new ImageIcon("./Config/FBH.png");
	   label = new JLabel();
	   label.setIcon(adm);
	   label.setBounds(17, 60, 130, 72);
	   panel.add(label);
	   label_3 = new JLabel("Crear");
	   label_3.setFont(new java.awt.Font("KaiTi",1,16));
	   label_3.setForeground(new java.awt.Color(159,140,98));
	   label_3.setBounds(155, 61, 100, 34);
	   panel.add(label_3);
	   label_31 = new JLabel("Torneo Grado");
	   label_31.setFont(new java.awt.Font("KaiTi",1,16));
	   label_31.setForeground(new java.awt.Color(159,140,98));
	   label_31.setBounds(155, 80, 125, 34);
	   panel.add(label_31);
	   label_1 = new JLabel("Federacion Bahiense de Tenis");
	   label_1.setFont(new java.awt.Font("KaiTi",1,18));
	   label_1.setForeground(new java.awt.Color(159,140,98));
	   label_1.setBounds(280, 0, 335, 46);
	   panel.add(label_1);
	   lblCargoTorneo = new JLabel("");
	   lblCargoTorneo.setBounds(20, 290, 335, 46);
	   lblCargoTorneo.setVisible(false);
	   panel.add(lblCargoTorneo);
	   lblCargoArchivo = new JLabel("");
	   lblCargoArchivo.setBounds(20, 535, 335, 46);
	   lblCargoArchivo.setVisible(false);
	   panel.add(lblCargoArchivo);
	   lblCargoArchivo2 = new JLabel("");
	   lblCargoArchivo2.setBounds(470, 535, 335, 46);
	   lblCargoArchivo2.setVisible(false);
	   panel.add(lblCargoArchivo2);
	   lblCargoArchivo3 = new JLabel("");
	   lblCargoArchivo3.setBounds(470, 290, 335, 46);
	   lblCargoArchivo3.setVisible(false);
	   panel.add(lblCargoArchivo3);
	   //Creacion, Configuracion y Agregado de los Botones
	   BotonCargarArchivo = new JButton("Abrir Archivo");
	   BotonCargarArchivo.setBounds(276, 61, 127, 34);
	   BotonCargarArchivo.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonCargarArchivoActionPerformed(evt);
           }
        });
	   panel.add(BotonCargarArchivo);
	   BotonReiniciarTorneo = new JButton("Reiniciar");
	   BotonReiniciarTorneo.setBounds(276, 240, 127, 34);
	   BotonReiniciarTorneo.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonReiniciarTorneoActionPerformed(evt);
           }
        });
	   panel.add(BotonReiniciarTorneo);
	   
	   BotonCrearListaAceptacion = new JButton("Crear Aceptaciones");
	   BotonCrearListaAceptacion.setBounds(276, 120, 127, 34);
	   BotonCrearListaAceptacion.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonCrearListaAceptacionActionPerformed(evt);
           }
        });
	   panel.add(BotonCrearListaAceptacion);
	   
	   BotonCrearNuevoTorneo = new JButton("Crear Torneo");
	   BotonCrearNuevoTorneo.setBounds(276, 180, 127, 34);
	   BotonCrearNuevoTorneo.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonCrearNuevoTorneoActionPerformed(evt);
           }
        });
	   panel.add(BotonCrearNuevoTorneo);
	   BotonReiniciarAAT = new JButton("Reiniciar");
	   BotonReiniciarAAT.setBounds(276, 478, 127, 34);
	   BotonReiniciarAAT.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonReiniciarAATActionPerformed(evt);
           }
        });
	   panel.add(BotonReiniciarAAT);
	   
	   BotonReiniciarAAT2 = new JButton("Reiniciar");
	   BotonReiniciarAAT2.setBounds(726, 478, 127, 34);
	   BotonReiniciarAAT2.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonReiniciarAAT2ActionPerformed(evt);
           }
        });
	   panel.add(BotonReiniciarAAT2);
	   
	   BotonReiniciarAAT3 = new JButton("Reiniciar");
	   BotonReiniciarAAT3.setBounds(726, 180, 127, 34);
	   BotonReiniciarAAT3.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonReiniciarAAT3ActionPerformed(evt);
           }
        });
	   panel.add(BotonReiniciarAAT3);
	   
	   BotonAbrirTorneo = new JButton("Abrir Torneo");
	   BotonAbrirTorneo.setBounds(276, 370, 127, 34);
	   BotonAbrirTorneo.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonAbrirTorneoActionPerformed(evt);
           }
        });
	   panel.add(BotonAbrirTorneo);
	   
	   BotonAbrirTorneo2 = new JButton("Abrir Archivo");
	   BotonAbrirTorneo2.setBounds(726, 370, 127, 34);
	   BotonAbrirTorneo2.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonAbrirTorneo2ActionPerformed(evt);
           }
        });
	   panel.add(BotonAbrirTorneo2);
	   
	   BotonAbrirTorneo3 = new JButton("Abrir Archivo");
	   BotonAbrirTorneo3.setBounds(726, 60, 127, 34);
	   BotonAbrirTorneo3.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonAbrirTorneo3ActionPerformed(evt);
           }
        });
	   panel.add(BotonAbrirTorneo3);
	   
	   BotonCrearArchivo = new JButton("Crear Archivo AAT");
	   BotonCrearArchivo.setBounds(276, 424, 127, 34);
	   BotonCrearArchivo.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonCrearArchivoActionPerformed(evt);
           }
        });
	   panel.add(BotonCrearArchivo);
	   
	   BotonCrearArchivo2 = new JButton("Crear Jugador");
	   BotonCrearArchivo2.setBounds(726, 424, 127, 34);
	   BotonCrearArchivo2.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonCrearArchivo2ActionPerformed(evt);
           }
        });
	   panel.add(BotonCrearArchivo2);
	   
	   BotonCrearArchivo3 = new JButton("Crear Cuadros");
	   BotonCrearArchivo3.setBounds(726, 120, 127, 34);
	   BotonCrearArchivo3.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent evt) {
              BotonCrearArchivo3ActionPerformed(evt);
           }
        });
	   panel.add(BotonCrearArchivo3);

	   //Creacion, Configuracion y Agregado de los Campos de Password
	   
	   CampoNroTorneo = new JTextField();
	   CampoNroTorneo.setBounds(20, 467, 35, 25);
	   panel.add(CampoNroTorneo);
	   Ciudad = new JTextField();
	   Ciudad.setBounds(20, 225, 175, 25);
	   panel.add(Ciudad);
	   Ciudad2 = new JTextField();
	   Ciudad2.setBounds(20, 412, 175, 25);
	   panel.add(Ciudad2);
	   CampotxtEncargado = new JTextField();
	   CampotxtEncargado.setBounds(20, 175, 175, 25);
	   panel.add(CampotxtEncargado);
	   
	   //Creacion, Configuracion y Agregado del Campo del Inspector
	   lblEncargado = new JLabel("Encargado Torneo");
	   lblEncargado.setBounds(20, 155, 100, 14);
	   panel.add(lblEncargado);
	   
	   lblCiudad = new JLabel("Sede del Torneo");
	   lblCiudad.setBounds(20, 205, 100, 14);
	   panel.add(lblCiudad);
	   
	   lblNroTorneo = new JLabel("Numero de torneo");
	   lblNroTorneo.setBounds(20, 447, 115, 14);
	   panel.add(lblNroTorneo);
	   
	   lblCiudad2 = new JLabel("Sede del Torneo");
	   lblCiudad2.setBounds(20, 392, 100, 14);
	   panel.add(lblCiudad2);
	   
	   LblArchivos = new JLabel("Cargar Resultados");
	   LblArchivos.setForeground(new Color(159, 140, 98));
	   LblArchivos.setFont(new Font("KaiTi", Font.BOLD, 16));
	   LblArchivos.setBounds(20, 336, 155, 20);
	   panel.add(LblArchivos);
	   LblArchivos2 = new JLabel("Cargar Nuevos Jugadores");
	   LblArchivos2.setForeground(new Color(159, 140, 98));
	   LblArchivos2.setFont(new Font("KaiTi", Font.BOLD, 16));
	   LblArchivos2.setBounds(470, 336, 220, 20);
	   panel.add(LblArchivos2);
	   LblArchivos3 = new JLabel("Completar Cuadros");
	   LblArchivos3.setForeground(new Color(159, 140, 98));
	   LblArchivos3.setFont(new Font("KaiTi", Font.BOLD, 16));
	   LblArchivos3.setBounds(470, 61, 220, 20);
	   panel.add(LblArchivos3);
	  	   
	   //Creacion, Configuracion y Agregado del Menu, barra superior
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		mnGeneral = new JMenu("General");
		menuBar.add(mnGeneral);
		menuItem = new JMenuItem("Ayuda");
		mnGeneral.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               mnuAyudaMouseClicked(evt);
            }
         });
		mntmAcercaDe = new JMenuItem("Acerca De..");
		mntmAcercaDe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               mnuAcercaDeMouseClicked(evt);
            }
         });
		mnGeneral.add(mntmAcercaDe);
		separador4 = new JSeparator();
		mnGeneral.add(separador4);
		mntmSalir = new JMenuItem("Salir");
		mnGeneral.add(mntmSalir);
		mntmSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               mniSalirActionPerformed(evt);
            }
         });
		
		//Creacion, Configuracion y Agregado del Dialogo Acerca de..
		{
            dlgAcercaDe = new JDialog(this);
            GroupLayout dlgAcercaDeLayout = new GroupLayout((JComponent)dlgAcercaDe.getContentPane());
            dlgAcercaDe.getContentPane().setLayout(dlgAcercaDeLayout);
            dlgAcercaDe.setTitle("Acerca de...");
            dlgAcercaDe.setMinimumSize(new java.awt.Dimension(394, 202));
            dlgAcercaDe.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
            dlgAcercaDe.setUndecorated(true);
            {
               lblAcercaDe1 = new JLabel();
               lblAcercaDe1.setFont(new java.awt.Font("Dialog", 1, 14));
               lblAcercaDe1.setText("Version Beta - SW Administrativo FBH");
               lblAcercaDe1.setHorizontalAlignment(SwingConstants.CENTER);
            }
            {
               btnCerrar = new JButton();
               btnCerrar.setText("Cerrar");
               btnCerrar.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent evt) {
                     btnCerrarActionPerformed(evt);
                  }
               });
            }
            {
               lblAcercaDe2 = new JLabel();
               lblAcercaDe2.setText("Realizado por ");
               lblAcercaDe2.setHorizontalAlignment(SwingConstants.CENTER);
            }
            {
               lblAcercaDe3 = new JLabel();
               lblAcercaDe3.setText("<html><center>Junca Christian, Peralta Tomas.<br/>");
               lblAcercaDe3.setHorizontalTextPosition(SwingConstants.LEADING);
               lblAcercaDe3.setHorizontalAlignment(SwingConstants.CENTER);
            }
            {
               lblAcercaDe4 = new JLabel();
               lblAcercaDe4.setHorizontalAlignment(SwingConstants.CENTER);
            }
            dlgAcercaDeLayout.setHorizontalGroup(dlgAcercaDeLayout.createSequentialGroup()
               .addContainerGap(62, 62)
               .addGroup(dlgAcercaDeLayout.createParallelGroup()
                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                       .addComponent(lblAcercaDe3, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
                       .addGap(0, 0, Short.MAX_VALUE))
                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                       .addComponent(lblAcercaDe4, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
                       .addGap(0, 0, Short.MAX_VALUE))
                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                       .addComponent(lblAcercaDe1, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
                       .addGap(0, 0, Short.MAX_VALUE))
                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                       .addGap(127)
                       .addGroup(dlgAcercaDeLayout.createParallelGroup()
                           .addGroup(GroupLayout.Alignment.LEADING, dlgAcercaDeLayout.createSequentialGroup()
                               .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, Short.MAX_VALUE))
                           .addGroup(dlgAcercaDeLayout.createSequentialGroup()
                               .addComponent(lblAcercaDe2, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
                               .addGap(0, 0, Short.MAX_VALUE)))
                       .addGap(132)))
               .addContainerGap(212, 212));
            dlgAcercaDeLayout.setVerticalGroup(dlgAcercaDeLayout.createSequentialGroup()
               .addContainerGap()
               .addComponent(lblAcercaDe1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
               .addGap(22)
               .addComponent(lblAcercaDe2, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
               .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
               .addComponent(lblAcercaDe3, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
               .addGap(20)
               .addComponent(lblAcercaDe4, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
               .addGap(24)
               .addComponent(btnCerrar, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
               .addContainerGap(670, Short.MAX_VALUE));
         }
		
		
		//Creacion, Configuracion y Agregado del Dialogo Ayuda
				{
		            dlgAyuda = new JDialog(this);
		            GroupLayout dlgAcercaDeLayout = new GroupLayout((JComponent)dlgAyuda.getContentPane());
		            dlgAyuda.getContentPane().setLayout(dlgAcercaDeLayout);
		            dlgAyuda.setMinimumSize(new java.awt.Dimension(394, 202));
		            dlgAyuda.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
		            dlgAyuda.setUndecorated(true);
		            {
		               lblAyuda1 = new JLabel();
		               lblAyuda1.setFont(new java.awt.Font("Dialog", 1, 14));
		               lblAyuda1.setText("Ayuda uso sistema administrativo");
		               lblAyuda1.setHorizontalAlignment(SwingConstants.CENTER);
		            }
		            {
		               btnCerrarA = new JButton();
		               btnCerrarA.setText("Cerrar");
		               btnCerrarA.addActionListener(new ActionListener() {
		                  public void actionPerformed(ActionEvent evt) {
		                     btnCerrarAActionPerformed(evt);
		                  }
		               });
		            }
		            {
		               lblAyuda2 = new JLabel();
		               lblAyuda2.setHorizontalAlignment(SwingConstants.CENTER);
		            }
		            {
		               lblAyuda3 = new JLabel();
		               lblAyuda3.setText("<html><center>La ventana se divide en dos mitades,<br/> la primera es la encargada de la organizacion del torneo.<br/> La segunda utiliza los torneos ya realizados para crear los archivos requeridos por la AAT.<br/>Ambas secciones requieren que se les asignen ciertos archivos");
		               lblAyuda3.setHorizontalTextPosition(SwingConstants.LEADING);
		               lblAyuda3.setHorizontalAlignment(SwingConstants.CENTER);
		            }
		            {
		               lblAyuda4 = new JLabel();
		               }
		            dlgAcercaDeLayout.setHorizontalGroup(dlgAcercaDeLayout.createSequentialGroup()
		               .addContainerGap(62, 62)
		               .addGroup(dlgAcercaDeLayout.createParallelGroup()
		                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
		                       .addComponent(lblAyuda3, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
		                       .addGap(0, 0, Short.MAX_VALUE))
		                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
		                       .addComponent(lblAyuda4, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
		                       .addGap(0, 0, Short.MAX_VALUE))
		                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
		                       .addComponent(lblAyuda1, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
		                       .addGap(0, 0, Short.MAX_VALUE))
		                   .addGroup(dlgAcercaDeLayout.createSequentialGroup()
		                       .addGap(127)
		                       .addGroup(dlgAcercaDeLayout.createParallelGroup()
		                           .addGroup(GroupLayout.Alignment.LEADING, dlgAcercaDeLayout.createSequentialGroup()
		                               .addComponent(btnCerrarA, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
		                               .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, Short.MAX_VALUE))
		                           .addGroup(dlgAcercaDeLayout.createSequentialGroup()
		                               .addComponent(lblAyuda2, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
		                               .addGap(0, 0, Short.MAX_VALUE)))
		                       .addGap(132)))
		               .addContainerGap(212, 212));
		            dlgAcercaDeLayout.setVerticalGroup(dlgAcercaDeLayout.createSequentialGroup()
		               .addContainerGap()
		               .addComponent(lblAyuda1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
		               .addGap(22)
		               .addComponent(lblAyuda2, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
		               .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
		               .addComponent(lblAyuda3, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
		               .addGap(20)
		               .addComponent(lblAyuda4, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
		               .addGap(24)
		               .addComponent(btnCerrarA, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
		               .addContainerGap(670, Short.MAX_VALUE));
		         }
				
				
	      } catch (Exception e) {
	          e.printStackTrace();
	       }
	    }
	

	
	
	//Oyentes Botones y Menu desplegable
	
		//Oyente boton Acerca De
		private void mnuAcercaDeMouseClicked (ActionEvent evt)
		{
			dlgAcercaDe.setMinimumSize(new java.awt.Dimension(400, 280));
			dlgAcercaDe.setLocationRelativeTo(null);
			dlgAcercaDe.setVisible(true);
		}
	
		//Oyente boton Ayuda
		private void mnuAyudaMouseClicked (ActionEvent evt)
		{
			dlgAyuda.setMinimumSize(new java.awt.Dimension(400, 280));
			dlgAyuda.setLocationRelativeTo(null);
			dlgAyuda.setVisible(true);
		}
	
		//Oyente boton cerrar
		private void mniSalirActionPerformed(ActionEvent evt) 
		{
			this.dispose();
		}
	
		//Oyente boton cerrar Acerca De
		private void btnCerrarActionPerformed(ActionEvent evt) 
		{
			dlgAcercaDe.setVisible(false);
		}
	
		//Oyente boton cerrar ayuda
		private void btnCerrarAActionPerformed(ActionEvent evt) 
		{
			dlgAyuda.setVisible(false);
		}
		
		private void BotonReiniciarTorneoActionPerformed(ActionEvent evt) 
		{
			CampotxtEncargado.setText("");
			lblCargoTorneo.setVisible(false);
			Ciudad.setText(null);
			Desplegable.setSelectedIndex(0);
			direccion = "";
		}

	
		private void BotonCargarArchivoActionPerformed(ActionEvent evt) 
		{
			JFileChooser elegir = new JFileChooser();
            int opcion = elegir.showOpenDialog(BotonCargarArchivo);
       
            //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
            if (opcion == JFileChooser.APPROVE_OPTION) {
                String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
                direccion = AcomodarBarras(pathArchivo);
                lblCargoTorneo.setText("Se esta utilizando el archivo: " + direccion);
                lblCargoTorneo.setVisible(true);
            }
		}
		

	
		private String AcomodarBarras(String direccion2) {
			String retorno = "";
			for (int i = 0; i<direccion2.length();i++)
				if (direccion2.charAt(i) == '\\')
					retorno +='/';
				else
					retorno +=direccion2.charAt(i);
			return retorno;
			
		}

		private void BotonReiniciarAATActionPerformed(ActionEvent evt) 
		{
			CampoNroTorneo.setText("");
			Ciudad2.setText(null);
			Desplegable1.setSelectedIndex(0);
			lblCargoArchivo.setVisible(false);
			direccion2 = "";
		}
	
		private void BotonReiniciarAAT2ActionPerformed(ActionEvent evt) 
		{
			Desplegable2.setSelectedIndex(0);
			lblCargoArchivo2.setVisible(false);
			direccion3 = "";
		}
		
		private void BotonReiniciarAAT3ActionPerformed(ActionEvent evt) 
		{
			Desplegable3.setSelectedIndex(0);
			lblCargoArchivo3.setVisible(false);
			direccion4 = "";
		}
	
		@SuppressWarnings("static-access")
		private void BotonCrearNuevoTorneoActionPerformed(ActionEvent evt) 
		{   
			String grado = (String) Desplegable.getSelectedItem();
			boolean aG1 = opcionPreviaG1.isSelected();
			if (direccion !="" && !grado.equals("Tipo de torneo"))
			{
				sist = new Sistema(direccion, CampotxtEncargado.getText(), grado, Ciudad.getText(),1,aG1);
            	sist.Cuadros();
            	mensaje.showMessageDialog(null, "Los cuadros han sido creados.");
            }
		}
		
		@SuppressWarnings("static-access")
		private void BotonCrearListaAceptacionActionPerformed(ActionEvent evt) 
		{   
			String grado = (String) Desplegable.getSelectedItem();
			boolean aG1 = opcionPreviaG1.isSelected();
			System.out.println(aG1);
			if (direccion !="" && !grado.equals("Tipo de torneo"))
			{
				sist = new Sistema(direccion, CampotxtEncargado.getText(), grado, Ciudad.getText(),1,aG1);
            	sist.Aceptaciones();
            	mensaje.showMessageDialog(null, "Las listas de inscriptos han sido creadas.");
			}
			
		}
		

		
		@SuppressWarnings("static-access")
		private void BotonCrearArchivoActionPerformed(ActionEvent evt) 
		{		
			String grado = (String) Desplegable1.getSelectedItem();
			if (direccion2 !="" && !grado.equals("Tipo de torneo"))
			{	
				//Creo variable sistema y creo archivo resultados AAT
				sist = new Sistema(direccion2, CampoNroTorneo.getText(), grado, Ciudad2.getText(), 0,false);
				sist.CierreTorneo(1); //opcion 1 indica que hago el archivo txt aat
				mensaje.showMessageDialog(null, "El archivo ha sido creado.");
            }
			
		}

		private void BotonAbrirTorneoActionPerformed(ActionEvent evt) 
		{
			JFileChooser elegir = new JFileChooser();
            int opcion = elegir.showOpenDialog(BotonCargarArchivo);
       
            //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
            if (opcion == JFileChooser.APPROVE_OPTION) {
                String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
                direccion2 = AcomodarBarras(pathArchivo);
                lblCargoArchivo.setText("Se esta utilizando el archivo: " + direccion2);
                lblCargoArchivo.setVisible(true);
            }
		}
		
		@SuppressWarnings("static-access")
		private void BotonCrearArchivo2ActionPerformed(ActionEvent evt) 
		{		
			String grado = (String) Desplegable2.getSelectedItem();
			if (direccion3 !="" && !grado.equals("Tipo de torneo"))
			{	
				sist = new Sistema(direccion3, grado);
				sist.CrearJugadorNuevo();// Creo los archivos correspondientes a los nuevos jugadores AAT a agregar
				mensaje.showMessageDialog(null, "Los archivos han sido creados.");
            }
			
		}

		private void BotonAbrirTorneo2ActionPerformed(ActionEvent evt) 
		{
			JFileChooser elegir = new JFileChooser();
            int opcion = elegir.showOpenDialog(BotonCargarArchivo);
       
            //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
            if (opcion == JFileChooser.APPROVE_OPTION) {
                String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
                direccion3 = AcomodarBarras(pathArchivo);
                lblCargoArchivo2.setText("Se esta utilizando el archivo: " + direccion3);
                lblCargoArchivo2.setVisible(true);
            }
		}
		
		@SuppressWarnings("static-access")
		private void BotonCrearArchivo3ActionPerformed(ActionEvent evt) 
		{		
			String grado = (String) Desplegable3.getSelectedItem();
			if (direccion4 !="" && !grado.equals("Tipo de torneo"))
			{	
				sist = new Sistema(direccion4, grado);
				sist.CierreTorneo(2); //opcion 2 indica que completo cuadros.
				mensaje.showMessageDialog(null, "El archivo ha sido creado.");
            }
			
		}

		private void BotonAbrirTorneo3ActionPerformed(ActionEvent evt) 
		{
			JFileChooser elegir = new JFileChooser();
            int opcion = elegir.showOpenDialog(BotonCargarArchivo);
       
            //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
            if (opcion == JFileChooser.APPROVE_OPTION) {
                String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
                direccion4 = AcomodarBarras(pathArchivo);
                lblCargoArchivo3.setText("Se esta utilizando el archivo: " + direccion4);
                lblCargoArchivo3.setVisible(true);
            }
		}

	
	
	
}