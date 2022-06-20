package desktop.employee;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import core.account.Account;
import core.account.AccountController;
import core.account.exception.AccountNotFoundException;
import core.common.FieldEmptyException;
import core.employee.Employee;
import core.employee.EmployeeController;
import core.employee.exception.NoEmployeeWithAccountException;
import desktop.GenericEmployeeView;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EmployeeLoginView extends JFrame {
	private AccountController accountController;
	private EmployeeController employeeController;
	
	private static final long serialVersionUID = -8759366384873309375L;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JButton btnLogin;
	
	public EmployeeLoginView(AccountController accountController, EmployeeController employeeController) {
		this.accountController = accountController;
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
		
		try {
			Account acc = accountController.login(email, password);
			Employee e = employeeController.getByAccount(acc);
			
			txtEmail.setText("");
			txtPassword.setText("");
			
			JFrame d = new GenericEmployeeView(this, e);
			d.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			d.setSize(this.getSize());
			d.setLocationRelativeTo(this);
			d.setVisible(true);
			this.setVisible(false);
			
			d.addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
	            	((GenericEmployeeView) d).logOut();
	            }
	        });
			
		} catch (AccountNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Email or password invalid.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field can't be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (NoEmployeeWithAccountException e) {
			JOptionPane.showMessageDialog(null, "No employee found for this account.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
