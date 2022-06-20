package desktop;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;

import core.employee.Employee;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GenericEmployeeView extends JFrame {
	private Employee employee;
	private JFrame parent;
	private static final long serialVersionUID = -6746602820794452880L;
	
	public GenericEmployeeView(JFrame parent, Employee employee) {
		this.parent = parent;
		this.employee = employee;
		
		getContentPane().setLayout(new MigLayout("", "[grow][grow][grow]", "[][][][][]"));
		
		JLabel lblNameOfPerson = new JLabel("Name: " + employee.getName() + " " + employee.getSurname());
		getContentPane().add(lblNameOfPerson, "cell 1 2");
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logOut();
			}
		});
		getContentPane().add(btnLogout, "cell 1 4,growx");
	}
	
	public void logOut() {
		setVisible(false);
		dispose();
		parent.setVisible(true);
	}
}
