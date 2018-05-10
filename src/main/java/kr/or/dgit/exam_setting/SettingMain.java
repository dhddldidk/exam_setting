package kr.or.dgit.exam_setting;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kr.or.dgit.exam_setting.service.BackUpService;
import kr.or.dgit.exam_setting.service.InitService;
import kr.or.dgit.exam_setting.service.LoadService;

@SuppressWarnings("serial")
public class SettingMain extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingMain frame = new SettingMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SettingMain() {
		initComponents();
	}

	private void initComponents() {
		setTitle("Setting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 127);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 5, 0));

		JButton btnInitial = new JButton(new AbstractBtnAction("초기화", this));
		contentPane.add(btnInitial);

		JButton btnDataBackUp = new JButton(new AbstractBtnAction("백업", this));
		contentPane.add(btnDataBackUp);

		JButton btnDataLoad = new JButton(new AbstractBtnAction("복원", this));
		contentPane.add(btnDataLoad);
	}

	public void initial() {
		System.out.println("??????????????????");
		InitService.getInstance().service();
		JOptionPane.showMessageDialog(null, "초기화 완료");
	}

	public void backupData() {
		BackUpService.getInstance().service();
		JOptionPane.showMessageDialog(null, "백업완료");
	}

	public void loadData() {
		LoadService.getInstance().service();
		JOptionPane.showMessageDialog(null, "복원 완료");
	}

}
