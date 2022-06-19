package desktop.employee;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import core.employee.EmployeeController;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeLoginView extends JPanel {
	private EmployeeController employeeController;
	
	private static final long serialVersionUID = -8759366384873309375L;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JButton btnLogin;
	
	public EmployeeLoginView(EmployeeController employeeController) {
		this.employeeController = employeeController;
		
		setLayout(new MigLayout("", "[20%,grow][][grow][20%,grow]", "[30%,grow][][][][30%,grow]"));
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblEmail, "cell 1 1,growx");
		
		txtEmail = new JTextField();
		add(txtEmail, "cell 2 1,growx");
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblPassword, "cell 1 2,growx");
		
		txtPassword = new JTextField();
		add(txtPassword, "cell 2 2,growx");
		txtPassword.setColumns(10);
		
		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		add(btnLogin, "cell 1 4 2 1,growx");
	}
	
	private void login() {
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		
		System.out.println(email + " " + password);
	}
}
