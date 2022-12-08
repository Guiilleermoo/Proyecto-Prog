package ventanas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import es.deusto.prog.III.Trabajador;
import es.deusto.prog.III.BD.GestorBD;

public class GestionEmpleados extends JFrame{
	protected GestorBD gestorBD;
	
	public GestionEmpleados(GestorBD gestorBD) {
		this.gestorBD = gestorBD;
		Container cp = this.getContentPane();
		
		JPanel izquierda = new JPanel();
		JPanel derecha = new JPanel();
		JPanel abajo = new JPanel();
		
		cp.add(izquierda , BorderLayout.WEST);
		cp.add(derecha, BorderLayout.EAST);
		cp.add(abajo, BorderLayout.SOUTH);
		derecha.setLayout(new GridLayout(6,2));
		abajo.setLayout(new GridLayout(1,2));
		
		JLabel NomYApell = new JLabel("Nombre y Apellidos:");
		JTextField NomYApell_1 = new JTextField();
		JLabel gmail = new JLabel("Gmail:");
		JTextField gmail_1 = new JTextField();
		JLabel contrasena = new JLabel("Contrase�a:");
		JTextField contrasena_1 = new JTextField();
		JLabel estatus = new JLabel("Estatus:");
		JComboBox estatus_1 = new JComboBox();
		estatus_1.addItem("Jefe");
		estatus_1.addItem("Empleado");
		JLabel salario = new JLabel("Salario");
		JTextField salario_1 = new JTextField();
		JLabel telefono = new JLabel("Telefono");
		JTextField telefono_1 = new JTextField();
		
		derecha.add(NomYApell);
		derecha.add(NomYApell_1);
		derecha.add(gmail);
		derecha.add(gmail_1);
		derecha.add(contrasena);
		derecha.add(contrasena_1);
		derecha.add(estatus);
		derecha.add(estatus_1);
		derecha.add(salario);
		derecha.add(salario_1);
		derecha.add(telefono);
		derecha.add(telefono_1);
		
		JScrollPane scroll = new JScrollPane();
		izquierda.add(scroll);
		
		DefaultListModel listaEmpleados = new DefaultListModel();
		JList listaE = new JList(listaEmpleados);
		scroll.setViewportView(listaE);
		
		JButton ver = new JButton("Ver");
		JButton anyadir = new JButton("A�adir");
		
		abajo.add(ver);
		abajo.add(anyadir);
		
		List<Trabajador> trabajadores = gestorBD.obtenerTrabajadores();
		trabajadores.forEach(t ->
		listaEmpleados.addElement(t.getNombreYApellidos())
		);
		
		ver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				listaE.repaint();
			}
		});
		
		this.setTitle("Gestion Empleados");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
		this.pack();
	}
	
	

}
