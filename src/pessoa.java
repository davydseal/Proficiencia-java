import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class pessoa extends JFrame {

	private JPanel contentPane;
	private JTextField nome;
	private JTextField sobrenome;
	private JTextField cpf;
	Connection con;
	Statement st;
	ResultSet rs;
	private JTextField id;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| javax.swing.UnsupportedLookAndFeelException ex) {
			System.err.println(ex);
		}		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pessoa frame = new pessoa();
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
	public pessoa() {
		

	 
		setTitle("Cadastro de pessoa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 344, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 308, 384);
		panel.setBackground(Color.WHITE);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome");
		lblNewLabel.setBounds(10, 123, 46, 14);
		panel.add(lblNewLabel);
		
		nome = new JTextField();
		nome.setBounds(10, 148, 288, 20);
		panel.add(nome);
		nome.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Sobrenome");
		lblNewLabel_1.setBounds(10, 179, 59, 14);
		panel.add(lblNewLabel_1);
		
		sobrenome = new JTextField();
		sobrenome.setBounds(10, 204, 288, 20);
		panel.add(sobrenome);
		sobrenome.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Cpf");
		lblNewLabel_2.setBounds(10, 235, 46, 14);
		panel.add(lblNewLabel_2);
		
		cpf = new JTextField();
		try{

	           javax.swing.text.MaskFormatter format_cpf = new javax.swing.text.MaskFormatter("###.###.###-##");

	           cpf = new javax.swing.JFormattedTextField(format_cpf);

	        }catch (Exception e){}
		cpf.setBounds(10, 260, 288, 20);
		panel.add(cpf);
		cpf.setColumns(10);
		

		
		JComboBox nome1 = new JComboBox();
		nome1.setBounds(10, 90, 288, 22);
		nome1.setModel(new DefaultComboBoxModel(new String[] {""}));
		try {
			Connection con = Conexao.faz_conexao();
			//con = DriverManager.getConnection("jdbc:mysql://localhost/terra", "root", "");
			st = con.createStatement();
			String s = "select * from pessoa";
			rs = st.executeQuery(s);
			
			while(rs.next()){
				nome1.addItem(rs.getString("nome"));
			}
			
		} catch (Exception e) {
			//JOptionPane.showMessageDialog(null, "ERROR");
		}
		finally 
		{
			try {
				st.close();
				rs.close();
				con.close();
				
			} catch (Exception e2) {
				//JOptionPane.showMessageDialog(null, "ERROR CLOSE");
			}
		}
		nome1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
try {
					
					String sql ="select * from pessoa where nome=?";
					Connection con = Conexao.faz_conexao();
					//con = DriverManager.getConnection("jdbc:mysql://localhost/terra", "root", "");
					PreparedStatement statement = con.prepareStatement(sql);
					statement.setString(1, (String)nome1.getSelectedItem() );
					ResultSet set = statement.executeQuery();
					

					while (set.next()) {
				      id.setText(set.getString("id"));
				      nome.setText(set.getString("nome"));
				      sobrenome.setText(set.getString("sobrenome"));
				      cpf.setText(set.getString("cpf"));
				     
				
				      

				     
					 

					  nome.setEnabled(true);
				
						
						

					}
					
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				
				
			
			}
		});
		
		
		JButton btnsalvar = new JButton("Salvar");
		btnsalvar.setBounds(11, 307, 89, 23);
		btnsalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o campo nome!");
				}
				else if(sobrenome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o campo sobrenome!");
				}
				else if(cpf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o campo cpf!");
				}
				
				else {
				
					
						try {
							Connection con2 = Conexao.faz_conexao();
							String sql2 = "insert into pessoa(nome, sobrenome, cpf) value (?, ?, ?)";
							
							PreparedStatement stmt = con2.prepareStatement(sql2);
							
							stmt.setString(1, nome.getText());
							stmt.setString(2, sobrenome.getText());
							stmt.setString(3, cpf.getText());
							

							
							
							stmt.execute();
							
							stmt.close();
							con2.close();
							JOptionPane.showMessageDialog(null, "Usuario cadastrado com sucesso!");
							nome.setText("");
							sobrenome.setText("");
							cpf.setText("");
							pessoa.this.dispose();
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
			}
					
				 
				
			
		});
		panel.add(btnsalvar);
		
		JButton btneditar = new JButton("Editar");
		btneditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o campo nome!");
				}
				else if(sobrenome.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o campo sobrenome!");
				}
				else if(cpf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha o campo cpf!");
				}
				else {
				try {		
					Connection con = Conexao.faz_conexao();
					
					String sql = "update pessoa set nome=?, sobrenome=?, cpf=? where id=?";
					PreparedStatement stmt = con.prepareStatement(sql);
					
					stmt.setString(1, nome.getText());
					stmt.setString(2, sobrenome.getText());
					stmt.setString(3, cpf.getText());
					stmt.setString(4, id.getText());

			
					stmt.execute();
					
					stmt.close();
					con.close();
					JOptionPane.showMessageDialog(null, "Usuario editado com sucesso!!!");
					pessoa.this.dispose();
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
		});
		btneditar.setBounds(209, 307, 89, 23);
		btneditar.setEnabled(false);
		panel.add(btneditar);
		
		
		
		nome1.setEnabled(false);
		panel.add(nome1);
		
		
		
		
		
		JButton btnsair = new JButton("Sair");
		btnsair.setBounds(209, 341, 89, 23);
		btnsair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pessoa.this.dispose();
			}
		});
		panel.add(btnsair);
		
		JLabel lblNewLabel_3 = new JLabel("Nome");
		lblNewLabel_3.setBounds(10, 67, 46, 14);
		panel.add(lblNewLabel_3);
		
		id = new JTextField();
		id.setBounds(10, 36, 59, 20);
		panel.add(id);
		id.setEnabled(false);
		id.setEditable(false);
		id.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Id");
		lblNewLabel_4.setBounds(10, 11, 46, 14);
		panel.add(lblNewLabel_4);
		
		JButton btndeletar = new JButton("Deletar");
		btndeletar.setEnabled(false);
		btndeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(id.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Selecione uma pessoa!");
				}
			
				else {
				try {		
					Connection con = Conexao.faz_conexao();
					
					String sql = "delete from pessoa where id=?";
					PreparedStatement stmt = con.prepareStatement(sql);
					

					stmt.setString(1, id.getText());

			
					stmt.execute();
					
					stmt.close();
					con.close();
					JOptionPane.showMessageDialog(null, "Usuario excluido com sucesso!!!");
					pessoa.this.dispose();
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
			
		});
		btndeletar.setBounds(10, 341, 89, 23);
		panel.add(btndeletar);
		JButton btnlocalizar = new JButton("Localizar");
		btnlocalizar.setBounds(110, 307, 89, 23);
		btnlocalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nome1.setEnabled(true);
				btnlocalizar.setEnabled(false);
				btneditar.setEnabled(true);
				btndeletar.setEnabled(true);
				btnsalvar.setEnabled(false);
			}
		});
		panel.add(btnlocalizar);
		
		JButton btnNewButton = new JButton("Limpar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				id.setText("");
				nome.setText("");
				sobrenome.setText("");
				cpf.setText("");
				nome1.setSelectedItem(null);
				nome1.setEnabled(false);
				btnsalvar.setEnabled(true);
				btnlocalizar.setEnabled(true);
				btneditar.setEnabled(false);
				btndeletar.setEnabled(false);
			}
		});
		btnNewButton.setBounds(109, 341, 89, 23);
		panel.add(btnNewButton);
		
	}
}
