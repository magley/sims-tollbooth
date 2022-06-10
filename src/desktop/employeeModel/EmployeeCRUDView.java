package desktop.employeeModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import core.employeeModel.EmployeeController;
import core.employeeModel.IEmployeeService;
import net.miginfocom.swing.MigLayout;

public class EmployeeCRUDView extends JFrame {
	private static final long serialVersionUID = -6914554783122799209L;
	private JTable table;
	
	private IEmployeeService service;
	private EmployeeController controller;
	private JTextField txtSurname;
	private JTextField txtEmail;
	private JTextField txtPassword;
	private JTextField txtName;
	
	private JLabel lblName;
	private JLabel lblSurname;
	private JLabel lblEmail;
	private JLabel lblPassword;
	
	public EmployeeCRUDView(IEmployeeService service, EmployeeController controller) {
		this.service = service;
		this.controller = controller;
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][][]"));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 0,grow");
		
		table = new JTable();
		table.getTableHeader().setReorderingAllowed(false);
		table.setModel(new EmployeeTableModel(service));
		table.addMouseListener(new MouseAdapter() {
			// For testing purposes.
			public void mouseClicked(MouseEvent me) {        
				int row = table.getSelectedRow();
        	 	if (row != -1) {
        	 		txtName.setText((String)table.getValueAt(row, 1));
        	 		txtSurname.setText((String)table.getValueAt(row, 2));
        	 		txtEmail.setText((String)table.getValueAt(row, 3));
        	 		txtPassword.setText((String)table.getValueAt(row, 4));
        	 	}
			}
		});
		scrollPane.setViewportView(table);
		
		lblName = new JLabel("Name");
		getContentPane().add(lblName, "flowx,cell 0 1,alignx trailing");
		
		txtName = new JTextField();
		getContentPane().add(txtName, "cell 0 1,growx");
		txtName.setColumns(10);
		
		lblSurname = new JLabel("Surname");
		getContentPane().add(lblSurname, "cell 0 1");
		
		txtSurname = new JTextField();
		getContentPane().add(txtSurname, "cell 0 1,growx");
		txtSurname.setColumns(10);
		
		lblEmail = new JLabel("Email");
		getContentPane().add(lblEmail, "cell 0 1");
		
		txtEmail = new JTextField();
		txtEmail.setText("");
		getContentPane().add(txtEmail, "cell 0 1,growx");
		txtEmail.setColumns(10);
		
		lblPassword = new JLabel("Password");
		getContentPane().add(lblPassword, "cell 0 1");
		
		txtPassword = new JTextField();
		getContentPane().add(txtPassword, "cell 0 1,growx");
		txtPassword.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addEmployee();
			}
		});
		getContentPane().add(btnAdd, "flowx,cell 0 2");
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saveDb();
			}
		});
		getContentPane().add(btnSave, "cell 0 2");
	}
	
	private void addEmployee() {	
		try {
			controller.add(txtName.getText(), txtSurname.getText(), txtEmail.getText(), txtPassword.getText());
			((EmployeeTableModel)(table.getModel())).fireTableDataChanged();
			table.setRowSelectionInterval(table.getRowCount() - 1, table.getRowCount() - 1);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Warning", JOptionPane.WARNING_MESSAGE);
		}	
	}
	
	private void saveDb() {
		service.save();
	}
}