package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import clases.Profesor;
import net.miginfocom.swing.MigLayout;

public class VentanaProfesores extends JFrame {

	private JPanel contentPane;
	private JTextField txtDni;
	private JTextField txtNombre;
	private JTextField txtSalario;
	private final ButtonGroup grupoTipo = new ButtonGroup();
	private ArrayList<Profesor> listaProfesores;
	private JRadioButton rdbFijo;
	private JRadioButton rdbTemporal;
	
	private DialogoDatos ventanaDatos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaProfesores frame = new VentanaProfesores();
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
	public VentanaProfesores() {
		this.listaProfesores = new ArrayList<Profesor>();
		ventanaDatos = new DialogoDatos();
		ventanaDatos.setModal(false);
		setTitle("Profesores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][87.00][grow][][]", "[][][][][][][][][][][77.00,grow][grow]"));
		
		JLabel lblNewLabel = new JLabel("DNI:");
		contentPane.add(lblNewLabel, "cell 1 1,alignx trailing");
		
		txtDni = new JTextField();
		contentPane.add(txtDni, "flowx,cell 2 1 2 1,growx");
		txtDni.setColumns(10);
		
		JButton btnNewButton = new JButton("Añadir");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertarProfesor();
				limpiarformulario();
				mostrarDatos();
			}
		});
		contentPane.add(btnNewButton, "cell 5 1,growx");
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		contentPane.add(lblNewLabel_1, "cell 1 3,alignx trailing");
		
		txtNombre = new JTextField();
		contentPane.add(txtNombre, "cell 2 3 2 1,growx");
		txtNombre.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Limpiar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarformulario();
			}
		});
		contentPane.add(btnNewButton_1, "cell 5 3,growx");
		
		JLabel lblNewLabel_2 = new JLabel("Salario:");
		contentPane.add(lblNewLabel_2, "cell 1 5,alignx trailing");
		
		txtSalario = new JTextField();
		contentPane.add(txtSalario, "cell 2 5 2 1,growx");
		txtSalario.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("Buscar");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		contentPane.add(btnNewButton_2, "cell 5 5,growx");
		
		JButton btnNewButton_4 = new JButton("Mostrar Datos");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarDatos();
			}
		});
		contentPane.add(btnNewButton_4, "cell 5 7");
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Tipo de Contrato", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(panel, "cell 1 9 2 2,grow");
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		rdbFijo = new JRadioButton("Fijo");
		rdbFijo.setSelected(true);
		grupoTipo.add(rdbFijo);
		panel.add(rdbFijo);
		
		rdbTemporal = new JRadioButton("Temporal");
		grupoTipo.add(rdbTemporal);
		panel.add(rdbTemporal);
		
		JButton btnNewButton_3 = new JButton("Salir");
		contentPane.add(btnNewButton_3, "cell 5 9,growx");
	}

	protected void mostrarDatos() {
		ventanaDatos.mostrarDatos(listaProfesores);
		
	}

	protected void buscar() {
		String dni = txtDni.getText();
		
		if (dni ==null || dni.isBlank()) {
			JOptionPane.showMessageDialog(contentPane, 
					"Debe introduccir el DNI",
					"Error en los datos", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		Profesor p = new Profesor();
		p.setDni(dni);
		int posicion = this.listaProfesores.indexOf(p);
		if (posicion==-1) {
			JOptionPane.showMessageDialog(contentPane, 
					"Profesor con el dni "+dni+ " no encontrado",
					"Error en los datos", JOptionPane.ERROR_MESSAGE);
			return;
		}
		p = this.listaProfesores.get(posicion);
		
		// rellenamos la interfaz
		txtDni.setText(p.getDni());
		txtNombre.setText(p.getNombre());
		txtSalario.setText(""+p.getSalario());
		rdbFijo.setSelected(p.isFijo());
		
	}

	protected void limpiarformulario() {
		txtDni.setText("");
		txtNombre.setText("");
		txtSalario.setText("");
		rdbFijo.setSelected(true);
		//rdbTemporal.setSelected(false);
	}

	protected void insertarProfesor() {
		try {
			String dni = txtDni.getText();
			String nombre = txtNombre.getText();
			double salario = Double.parseDouble(txtSalario.getText());
//			boolean fijo =true;
//			
//			if (!rdbFijo.isSelected()) {
//				fijo=false;
//			}
			
			if (dni == null || dni.isBlank() ||
					nombre==null || nombre.isBlank()) {
				JOptionPane.showMessageDialog(contentPane, 
						"Debe introduccir el nombre y el DNI",
						"Error en los datos", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			if (this.listaProfesores.contains(dni)) {
				JOptionPane.showMessageDialog(contentPane, 
						"Ya existe un profesor con ese DNI",
						"Error en los datos", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Profesor p = new Profesor(dni,nombre,salario,rdbFijo.isSelected());
			this.listaProfesores.add(p);
			
		} catch (NumberFormatException e ) {
			JOptionPane.showMessageDialog(contentPane, 
					"El salario debe ser un dato numérico",
					"Error en los datos", JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
