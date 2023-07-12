package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import clases.Contacto;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class Ejercicio4_1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTable tabla;
	
	private ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio4_1 frame = new Ejercicio4_1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ejercicio4_1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 572, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow,fill][329.00][68.00][118.00][grow,fill]", "[grow,fill][grow][68.00][65.00][67.00][62.00][64.00][grow][grow,fill]"));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 1 1 1 7,grow");
		
		tabla = new JTable();
		tabla.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Tel\u00E9fono"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(tabla);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		contentPane.add(lblNewLabel, "cell 2 2,alignx trailing");
		
		txtNombre = new JTextField();
		contentPane.add(txtNombre, "cell 3 2,growx");
		txtNombre.setColumns(10);
		
		JLabel lblTeléfono = new JLabel("Teléfono");
		contentPane.add(lblTeléfono, "cell 2 3,alignx trailing");
		
		txtTelefono = new JTextField();
		contentPane.add(txtTelefono, "cell 3 3,growx");
		txtTelefono.setColumns(10);
		
		JButton btnAñadir = new JButton("Añadir");
		btnAñadir.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String nombre = txtNombre.getText();
				String telefono = txtTelefono.getText();
				
				if (nombre == null || nombre.isBlank() ||
						telefono == null || telefono.isBlank())
				 {
					JOptionPane.showMessageDialog(contentPane, 
							"Debe introducir el nombre y teléfono"
							, "Datos requeridos",
							JOptionPane.WARNING_MESSAGE);
					return;
				 }
				if (! validarTelefono(telefono))
				 {
					JOptionPane.showMessageDialog(contentPane, 
							"El teléfono introducido es incorrecto"
							, "Teléfono incorrecto",
							JOptionPane.WARNING_MESSAGE);
					return;
				 }
				
				Contacto contacto = new Contacto(nombre, telefono);
				añadirContacto(contacto);
				
				limpiarFormulario();
				
				muestraPersonas();
			}
		});
		contentPane.add(btnAñadir, "cell 3 4,growx");
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				// tomamos el numero de fila seleccionada
				int seleccionada = tabla.getSelectedRow();
				
				if (seleccionada==-1) {
					// no hay ninguna seleccionada
					JOptionPane.showMessageDialog(contentPane, 
							"Debe seleccionar un contacto a borrar");
					return;
				}
				
				//recojo el modelo
				DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
				Contacto contacto = new Contacto();
				//tomamos el dni de la tabla, de la fila seleccionada y la columna 0 que es el dni
				contacto.setNombre((String) modelo.getValueAt(seleccionada, 0));
				
				
				if (! listaContactos.contains(contacto) ) {
					JOptionPane.showMessageDialog(contentPane, 
							"No existe ningun contacto con ese nombre",
							"Nombre no encontrado", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				listaContactos.remove(contacto);
				muestraPersonas();
			}
		});
		contentPane.add(btnEliminar, "cell 3 5,growx");
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(contentPane, 
						"Cerrando...", "Saludos", 
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});
		contentPane.add(btnSalir, "cell 3 6,growx");
	}

	protected boolean validarTelefono(String movil)
	{
		Pattern pattern1 = Pattern.compile("[6|7|9][0-9]{8}$");
		Pattern pattern2 = Pattern.compile("[0|1|9][0-9]{2}$");
		if (pattern1.matcher(movil).matches() || pattern2.matcher(movil).matches())
			return true;
		return false;
	}

	protected void añadirContacto(Contacto contacto)
	{
		if(this.listaContactos.contains(contacto) ) {
			JOptionPane.showMessageDialog(contentPane, 
					"Ya existe esa persona en sus contactos", "Nombre ya existe", 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.listaContactos.add(contacto);
	}

	protected void limpiarFormulario()
	{
		txtNombre.setText("");
		txtTelefono.setText("");
	}

	protected void muestraPersonas()
	{
		// REcoge el modelo de la tabla con sus filas y columnas
		DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
		// vacía la JTable
		modelo.setRowCount(0);
		
		// recorremos la lista de personas
		for (Contacto contacto : listaContactos) {
			// componemos una fila de la tabla
			Object fila [] = {
				contacto.getNombre(), contacto.getTelefono()
			};
			
			// añade la fila al modelo
			modelo.addRow(fila);
		}
	}
}
