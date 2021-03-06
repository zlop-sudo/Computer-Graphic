package GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import graphic.Core;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Panel_ extends JFrame {
	
	private Core core = new Core();
	
	private JPanel contentPane;
	private JTextField Cx;
	private JTextField Cy;
	private JTextField Cz;
	private JTextField Prefx;
	private JTextField Prefy;
	private JTextField Prefz;
	private JTextField V_x;
	private JTextField V_y;
	private JTextField V_z;
	private JTextField H;
	private JTextField D;
	private JTextField F;
	private JTextField Remove_back;
	private JTextField file_name;
	private JLabel lblNewLabel_10;
	private JTextArea textArea;
	private JLabel lblNewLabel_12;
	private JTextField L_x;
	private JTextField L_y;
	private JTextField L_z;
	private JLabel lblNewLabel_13;
	private JTextField I_l;
	private JLabel lblNewLabel_14;
	private JTextField I_a;
	private JLabel lblNewLabel_15;
	private JTextField Ka;
	private JLabel lblNewLabel_16;
	private JTextField Kd;
	private JLabel lblNewLabel_17;
	private JTextField Ks;
	private JLabel lblNewLabel_18;
	private JTextField n;
	private JLabel lblNewLabel_19;
	private JTextField Method;
	private JLabel lblNewLabel_21;
	private JTextField texture_path;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try  {
					Panel_ frame = new Panel_();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Panel_() {
		
		setTitle("Graphic Draw");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("C:");
		lblNewLabel.setBounds(19, 36, 28, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Camera");
		lblNewLabel_1.setBounds(19, 8, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Pref:");
		lblNewLabel_2.setBounds(19, 64, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("V':");
		lblNewLabel_3.setBounds(19, 92, 61, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("h:");
		lblNewLabel_4.setBounds(19, 120, 61, 16);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("d:");
		lblNewLabel_5.setBounds(19, 148, 61, 16);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("f:");
		lblNewLabel_6.setBounds(19, 176, 61, 16);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("back_culling(1 means apply):");
		lblNewLabel_7.setBounds(19, 209, 212, 16);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Geometry");
		lblNewLabel_8.setBounds(242, 8, 61, 16);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("File name:");
		lblNewLabel_9.setBounds(242, 36, 111, 16);
		contentPane.add(lblNewLabel_9);
		
		Cx = new JTextField();
		Cx.setBounds(59, 31, 33, 26);
		contentPane.add(Cx);
		Cx.setColumns(10);
		
		Cy = new JTextField();
		Cy.setBounds(101, 31, 33, 26);
		contentPane.add(Cy);
		Cy.setColumns(10);
		
		Cz = new JTextField();
		Cz.setBounds(146, 31, 33, 26);
		contentPane.add(Cz);
		Cz.setColumns(10);
		
		Prefx = new JTextField();
		Prefx.setBounds(59, 59, 33, 26);
		contentPane.add(Prefx);
		Prefx.setColumns(10);
		
		Prefy = new JTextField();
		Prefy.setBounds(101, 59, 33, 26);
		contentPane.add(Prefy);
		Prefy.setColumns(10);
		
		Prefz = new JTextField();
		Prefz.setBounds(146, 59, 33, 26);
		contentPane.add(Prefz);
		Prefz.setColumns(10);
		
		V_x = new JTextField();
		V_x.setBounds(59, 92, 33, 26);
		contentPane.add(V_x);
		V_x.setColumns(10);
		
		V_y = new JTextField();
		V_y.setBounds(101, 92, 33, 26);
		contentPane.add(V_y);
		V_y.setColumns(10);
		
		V_z = new JTextField();
		V_z.setBounds(146, 92, 33, 26);
		contentPane.add(V_z);
		V_z.setColumns(10);
		
		H = new JTextField();
		H.setBounds(59, 120, 45, 26);
		contentPane.add(H);
		H.setColumns(10);
		
		D = new JTextField();
		D.setBounds(59, 148, 45, 26);
		contentPane.add(D);
		D.setColumns(10);
		
		F = new JTextField();
		F.setBounds(59, 176, 45, 26);
		contentPane.add(F);
		F.setColumns(10);
		
		Remove_back = new JTextField();
		Remove_back.setBounds(19, 237, 61, 26);
		contentPane.add(Remove_back);
		Remove_back.setColumns(10);
		
		file_name = new JTextField();
		file_name.setBounds(242, 59, 130, 26);
		contentPane.add(file_name);
		file_name.setColumns(10);
		
		JButton btnNewButton = new JButton("Set Camera");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				set_camera();
			}
		});
		btnNewButton.setBounds(19, 275, 117, 29);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Load Model");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				set_g();
				show_location();
			}
		});
		btnNewButton_1.setBounds(242, 92, 117, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Draw");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				draw();
			}
		});
		btnNewButton_2.setBounds(371, 367, 117, 29);
		contentPane.add(btnNewButton_2);
		
		lblNewLabel_10 = new JLabel("Model Location(x,y,z)");
		lblNewLabel_10.setBounds(242, 133, 152, 16);
		contentPane.add(lblNewLabel_10);
		
		textArea = new JTextArea();
		textArea.setBounds(242, 153, 130, 72);
		contentPane.add(textArea);
		
		JLabel lblNewLabel_11 = new JLabel("Light");
		lblNewLabel_11.setBounds(409, 8, 61, 16);
		contentPane.add(lblNewLabel_11);
		
		lblNewLabel_12 = new JLabel("L_vector(point to source)");
		lblNewLabel_12.setBounds(409, 36, 185, 16);
		contentPane.add(lblNewLabel_12);
		
		L_x = new JTextField();
		L_x.setBounds(409, 59, 45, 26);
		contentPane.add(L_x);
		L_x.setColumns(10);
		L_x.setText("1");
		
		L_y = new JTextField();
		L_y.setBounds(464, 59, 45, 26);
		contentPane.add(L_y);
		L_y.setColumns(10);
		L_y.setText("1");
		
		L_z = new JTextField();
		L_z.setBounds(522, 59, 45, 26);
		contentPane.add(L_z);
		L_z.setColumns(10);
		L_z.setText("1");
		
		lblNewLabel_13 = new JLabel("I_light");
		lblNewLabel_13.setBounds(409, 92, 61, 16);
		contentPane.add(lblNewLabel_13);
		
		I_l = new JTextField();
		I_l.setBounds(522, 87, 45, 26);
		contentPane.add(I_l);
		I_l.setColumns(10);
		I_l.setText("1");
		
		lblNewLabel_14 = new JLabel("I_ambient");
		lblNewLabel_14.setBounds(406, 120, 77, 16);
		contentPane.add(lblNewLabel_14);
		
		I_a = new JTextField();
		I_a.setBounds(522, 115, 45, 26);
		contentPane.add(I_a);
		I_a.setColumns(10);
		I_a.setText("0.2");
		
		lblNewLabel_15 = new JLabel("Ka");
		lblNewLabel_15.setBounds(409, 153, 61, 16);
		contentPane.add(lblNewLabel_15);
		
		Ka = new JTextField();
		Ka.setBounds(522, 148, 45, 26);
		contentPane.add(Ka);
		Ka.setColumns(10);
		Ka.setText("0.3");
		
		lblNewLabel_16 = new JLabel("Kd");
		lblNewLabel_16.setBounds(409, 181, 61, 16);
		contentPane.add(lblNewLabel_16);
		
		Kd = new JTextField();
		Kd.setBounds(522, 176, 45, 26);
		contentPane.add(Kd);
		Kd.setColumns(10);
		Kd.setText("0.6");
		
		lblNewLabel_17 = new JLabel("Ks");
		lblNewLabel_17.setBounds(409, 209, 61, 16);
		contentPane.add(lblNewLabel_17);
		
		Ks = new JTextField();
		Ks.setBounds(522, 204, 45, 26);
		contentPane.add(Ks);
		Ks.setColumns(10);
		Ks.setText("0.8");
		
		lblNewLabel_18 = new JLabel("n");
		lblNewLabel_18.setBounds(409, 242, 61, 16);
		contentPane.add(lblNewLabel_18);
		
		n = new JTextField();
		n.setBounds(522, 237, 45, 26);
		contentPane.add(n);
		n.setColumns(10);
		n.setText("8");
		
		lblNewLabel_19 = new JLabel("Methods: 1-Constant, 2-Gourud, 3-Phong");
		lblNewLabel_19.setBounds(242, 280, 309, 16);
		contentPane.add(lblNewLabel_19);
		
		Method = new JTextField();
		Method.setBounds(522, 275, 45, 26);
		contentPane.add(Method);
		Method.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Set Light");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				set_light();
			}
		});
		btnNewButton_3.setBounds(450, 323, 117, 29);
		contentPane.add(btnNewButton_3);
		
		JLabel lblNewLabel_20 = new JLabel("4-Texture(Phong)");
		lblNewLabel_20.setBounds(311, 308, 143, 16);
		contentPane.add(lblNewLabel_20);
		
		lblNewLabel_21 = new JLabel("Texture Path:");
		lblNewLabel_21.setBounds(19, 328, 115, 16);
		contentPane.add(lblNewLabel_21);
		
		texture_path = new JTextField();
		texture_path.setBounds(19, 356, 130, 26);
		contentPane.add(texture_path);
		texture_path.setColumns(10);
		texture_path.setText("Texture1.jpeg");
		
		JButton btnNewButton_4 = new JButton("Set Texture");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTexture();
			}
		});
		btnNewButton_4.setBounds(17, 387, 117, 29);
		contentPane.add(btnNewButton_4);
	}
	
	public void set_camera() {
		double[] c = new double[3];
		double[] pref = new double[3];
		double[] v_ = new double[3];
		double h;
		double d;
		double f;
		int remove_back;
		c[0] = Double.parseDouble(Cx.getText());
		c[1] = Double.parseDouble(Cy.getText());
		c[2] = Double.parseDouble(Cz.getText());
		pref[0] = Double.parseDouble(Prefx.getText());
		pref[1] = Double.parseDouble(Prefy.getText());
		pref[2] = Double.parseDouble(Prefz.getText());
		v_[0] = Double.parseDouble(V_x.getText());
		v_[1] = Double.parseDouble(V_y.getText());
		v_[2] = Double.parseDouble(V_z.getText());
		h = Double.parseDouble(H.getText());
		d = Double.parseDouble(D.getText());
		f = Double.parseDouble(F.getText());
		remove_back = Integer.parseInt(Remove_back.getText());
		core.camera.initial(c, pref, v_, h, d, f, remove_back);
	}
	
	public void set_light() {
		double[] l = new double[3];
		double i_light;
		double i_ambient;
		double ka;
		double kd;
		double ks;
		double N;
		l[0] = Double.parseDouble(L_x.getText());
		l[1] = Double.parseDouble(L_y.getText());
		l[2] = Double.parseDouble(L_z.getText());
		i_light = Double.parseDouble(I_l.getText());
		i_ambient = Double.parseDouble(I_a.getText());
		ka = Double.parseDouble(Ka.getText());
		kd = Double.parseDouble(Kd.getText());
		ks = Double.parseDouble(Ks.getText());
		N = Double.parseDouble(n.getText());
		core.illu.initial(l, i_light, i_ambient, ka, kd, ks, N);
	}
	
	public void set_g() {
		String pathname = file_name.getText();
		core.g.reset(pathname);
	}
	
	public void draw() {
		int method = Integer.parseInt(Method.getText());
		if (method == 1) {
			core.constant_draw();
		}
		else if (method == 2) {
			core.gouraud_draw();
		}
		else if (method == 3) {
			core.phong_draw(false);
		}
		else if (method == 4) {
			core.phong_draw(true);
		}
	}
	
	public void show_location() {
		textArea.setText(core.getModelLoc());
	}
	
	public void setTexture() {
		core.t.readTexture(texture_path.getText());
	}
}
