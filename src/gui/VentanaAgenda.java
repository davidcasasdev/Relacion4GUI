package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import clases.Contacto;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class VentanaAgenda extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTable table;
	
	private ArrayList<Contacto> agenda;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaAgenda frame = new VentanaAgenda();
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
	public VentanaAgenda() {
		this.agenda = new ArrayList<Contacto>();
		
		setTitle("Agenda Telefónica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[280.00,grow][20px:n][85.00px:n][grow][grow]", "[][][][][][][][][][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 0 1 10,grow");
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Nombre", "Tel\u00E9fono"
			}
		) {
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
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
		contentPane.add(lblNewLabel, "cell 2 0,alignx trailing");
		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Verdana", Font.PLAIN, 12));
		contentPane.add(txtNombre, "cell 3 0,growx");
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Teléfono:");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		contentPane.add(lblNewLabel_1, "cell 2 2,alignx trailing");
		
		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Verdana", Font.PLAIN, 12));
		txtTelefono.setColumns(10);
		contentPane.add(txtTelefono, "cell 3 2,growx");
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contacto c= recogerDatos();
				if (c!=null) {
					insertarContacto(c);
					mostrarContactos();
				}
			}
		});
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 12));
		contentPane.add(btnNewButton, "cell 3 4,growx");
		
		JButton btnNewButton_1 = new JButton("Eliminar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminar();
			}
		});
		btnNewButton_1.setFont(new Font("Verdana", Font.PLAIN, 12));
		contentPane.add(btnNewButton_1, "cell 3 6,growx");
		
		JButton btnNewButton_2 = new JButton("Salir");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_2.setFont(new Font("Verdana", Font.PLAIN, 12));
		contentPane.add(btnNewButton_2, "cell 3 8,growx");
	}

	protected void eliminar() {
		int [] seleccionado = table.getSelectedRows();
		for (int i = 0; i < table.getSelectedRowCount(); i++) {
			this.agenda.remove(i);
		}
		mostrarContactos();
	}

	protected void mostrarContactos() {
		DefaultTableModel modelo = (DefaultTableModel) table.getModel();
		modelo.setRowCount(0);
		
		for (Contacto contacto : agenda) {
			Object fila [] = {
					contacto.getNombre(),
					contacto.getTelefono()
			};
			modelo.addRow(fila);
		}
		
	}

	protected void insertarContacto(Contacto c) {
		if (!this.agenda.contains(c)) {
			this.agenda.add(c);
		} else {
			JOptionPane.showMessageDialog(contentPane, 
					"No se puede insertar, ya existe un contacto con ese nomnbre", 
					"Contacto duplicado", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected Contacto recogerDatos() {
		String nombre = txtNombre.getText();
		String telefono = txtTelefono.getText();
		
		Contacto contacto = null;
		
		if (nombre==null || nombre.isBlank() ||
			telefono == null || telefono.isBlank()) {
			JOptionPane.showMessageDialog(contentPane, 
					"Debe introducir el nombre del contacto y el teléfono", 
					"Erorr en los datos", JOptionPane.ERROR_MESSAGE);
			return contacto;
		}
		
//		try {
//			int tfno = Integer.parseInt(telefono);
//		} catch(NumberFormatException e) {
//			JOptionPane.showMessageDialog(contentPane, 
//					"El telefóno es incorrecto, revise los datos", 
//					"Erorr en los datos", JOptionPane.ERROR_MESSAGE);
//			return contacto;
//		}
		
		
		if (!Pattern.matches("[9|6|7][0-9]{8}", telefono) && 
			!Pattern.matches("[0-9]{3}", telefono)) {
			JOptionPane.showMessageDialog(contentPane, 
					"El telefóno es incorrecto, revise los datos", 
					"Erorr en los datos", JOptionPane.ERROR_MESSAGE);
			return contacto;
		}
			
		contacto = new Contacto(nombre,telefono);
		return contacto;
	}

}
