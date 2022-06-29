package desktop.employee;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.AppContext;
import core.account.Account;
import core.account.AccountController;
import core.account.exception.AccountNotFoundException;
import core.common.FieldEmptyException;
import core.employee.Employee;
import core.employee.EmployeeController;
import desktop.GenericEmployeeView;
import net.miginfocom.swing.MigLayout;

public class EmployeeLoginView extends JFrame {
	private AccountController accountController;
	private EmployeeController employeeController;

	private static final long serialVersionUID = -8759366384873309375L;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private AppContext ctx;

	public EmployeeLoginView(AppContext ctx) {
		this.ctx = ctx;
		this.accountController = ctx.getAccountController();
		this.employeeController = ctx.getEmployeeController();

		getContentPane().setLayout(new MigLayout("", "[20%][10%][grow][20%]", "[30%,grow][][][20%,grow][20%,grow][20%]"));

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblEmail, "cell 1 1,growx");

		txtEmail = new JTextField();
		getContentPane().add(txtEmail, "cell 2 1,growx");
		txtEmail.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblPassword, "cell 1 2,growx");

		txtPassword = new JPasswordField();
		getContentPane().add(txtPassword, "cell 2 2,growx");
		txtPassword.setColumns(10);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		getContentPane().add(btnLogin, "cell 1 4 2 1,grow");
		getRootPane().setDefaultButton(btnLogin);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				super.componentShown(e);
				txtEmail.requestFocus();
			}
		});
	}

	private void login() {
		String email = txtEmail.getText();
		String password = txtPassword.getText();

		try {
			Account acc = accountController.login(email, password);
			Employee e = acc.getEmployee();

			txtEmail.setText("");
			txtPassword.setText("");

			GenericEmployeeView d = new GenericEmployeeView(ctx, this, e);
			d.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			d.setSize(this.getSize());
			d.setLocationRelativeTo(this);
			d.setVisible(true);
			this.setVisible(false);

			d.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent windowEvent) {
					((GenericEmployeeView) d).logOut();
				}
			});

		} catch (AccountNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Email or password invalid.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (FieldEmptyException e) {
			JOptionPane.showMessageDialog(null, "Field can't be empty.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
