import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class OneToFifty extends JFrame {

	private JPanel contentPane;
	int []sequence =new int[50];
	int next =0;
	boolean begin =false;
	int difficulty = 5;
	//{{control
	JFrame frame = new JFrame();
	JLabel lblNewLabel_1 = new JLabel("NEXT");
	JLabel lblNewLabel = new JLabel("时间 0");
	JButton btnNewButton_1 = new JButton("\u5F00\u59CB\u6E38\u620F");
	JButton[] buttonList = new JButton[36];
	List list1 = new ArrayList();
	List list2 = new ArrayList();
	ButtonGroup group=new ButtonGroup();
	JLabel label = new JLabel("\u96BE\u5EA6\u9009\u62E9\uFF1A");
	JRadioButton radioButton = new JRadioButton("\u7B80\u5355");
	JRadioButton radioButton_1 = new JRadioButton("\u4E2D\u7B49");
	JRadioButton radioButton_2 = new JRadioButton("\u56F0\u96BE");
	//}}
	Timer timer = new Timer(true);
	long time;
	ActionListener al = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(new Runnable(){
				 //A new thread to update the lblB immediately
                @Override
                public void run() {
					if(!begin)return;
					if(Integer.parseInt(((JButton)e.getSource()).getText())==next){
						if(next<=difficulty*difficulty){
							((JButton)e.getSource()).setText(""+ list2.get(next-1));
							((JButton)e.getSource()).setBackground(new Color(141,216,222));
						}
						else{
							((JButton)e.getSource()).setEnabled(false);
							((JButton)e.getSource()).setVisible(false);
						}
						next++;
						lblNewLabel_1.setText("NEXT "+next);
					if(next==difficulty*difficulty*2+1){
						btnNewButton_1.setText("开始游戏");
						lblNewLabel_1.setText("你赢了！");
						timer.cancel();
					}
				}
				else{
					time+=2;
					lblNewLabel.setText("时间 "+time);
				}
			}
		}).start();
		}
	};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OneToFifty frame = new OneToFifty();
					frame.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OneToFifty() {
		
		frame.setTitle("1to50");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(500,100, 100+100*difficulty, 280+difficulty*100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel.setBounds(30, 40, 150, 50);
		contentPane.add(lblNewLabel);
		for(int i=0;i<36;i++){
			buttonList[i] =new JButton(""+(i+1));
			buttonList[i].setFont(new Font("Tempus Sans ITC", Font.BOLD, 30));//"Tempus Sans ITC"
			buttonList[i].addActionListener(al);
			buttonList[i].setBounds(50+100*(i%difficulty), 120+100*(i/difficulty), 100, 100);
			buttonList[i].setBackground(Color.white);
			buttonList[i].setFocusPainted(false);
			contentPane.add(buttonList[i]);
			if(i>=difficulty*difficulty){
				buttonList[i].setVisible(false);
				buttonList[i].setEnabled(false);
					
			}
		}
		btnNewButton_1.setBackground(Color.WHITE);

		
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//困难度与边界设置
				if(radioButton.isSelected())difficulty=4;
				else if(radioButton_2.isSelected())difficulty=6;
				else difficulty=5;
				frame.setBounds(frame.getX(),frame.getY(), 100+100*difficulty, 280+difficulty*100);
				btnNewButton_1.setBounds(50*difficulty-14,32,128,64);
				lblNewLabel_1.setBounds(225+75*difficulty-64-150, 40, 150, 50);
				label.setBounds(98, 657, 122, 76);
				radioButton.setBounds(50*difficulty-35, 184+100*difficulty, 92, 27);
				radioButton_1.setBounds(50*difficulty+73, 184+100*difficulty, 92, 27);
				radioButton_2.setBounds(50*difficulty+173, 184+100*difficulty, 92, 27);
				label.setBounds(50*difficulty-250+98, 157+100*difficulty, 122, 76);
				//时间设置
				time =-1 ;
				timer.cancel();
				timer =new Timer(true);
				timer.schedule(new TimerTask(){
					public void run(){
						time+=1;
						lblNewLabel.setText("时间 "+time);
					}
				},0,1000);
				
				//序列与按钮设置
				list1.clear();
				list2.clear();
				for(int i=0;i<difficulty*difficulty;i++)list1.add(i+1);
				for(int i=0;i<difficulty*difficulty;i++)list2.add(i+difficulty*difficulty+1);
				for(int i=0;i<difficulty*difficulty;i++){
					buttonList[i].setBounds(50+100*(i%difficulty), 120+100*(i/difficulty), 100, 100);
					buttonList[i].setBackground(new Color(255,255,255));
					buttonList[i].setVisible(true);
					buttonList[i].setEnabled(true);
				}
				for(int i=difficulty*difficulty;i<36;i++){
					buttonList[i].setVisible(false);
					buttonList[i].setEnabled(false);
				}
				Collections.shuffle(list1);
				Collections.shuffle(list2);
				//初始化
				begin=true;
				next=1;
				for(int i=0;i<difficulty*difficulty;i++)buttonList[i].setText(""+list1.get(i));
				lblNewLabel_1.setText("NEXT "+next);
				btnNewButton_1.setText("重新开始");
			}
		});
		btnNewButton_1.setFont(new Font("楷体", Font.PLAIN, 23));
		btnNewButton_1.setBounds(236, 32, 128, 64);
		btnNewButton_1.setFocusPainted(false);
		contentPane.add(btnNewButton_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel_1.setForeground(Color.BLUE);
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(386, 40, 150, 50);
		contentPane.add(lblNewLabel_1);
		
		
		label.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		label.setBounds(98, 657, 122, 76);
		contentPane.add(label);
		
		
		radioButton.setFont(new Font("宋体", Font.PLAIN, 18));
		radioButton.setBounds(215, 684, 92, 27);
		radioButton.setFocusPainted(false);
		contentPane.add(radioButton);
		
		
		radioButton_1.setSelected(true);
		radioButton_1.setFont(new Font("宋体", Font.PLAIN, 18));
		radioButton_1.setBounds(313, 684, 92, 27);
		radioButton_1.setFocusPainted(false);
		contentPane.add(radioButton_1);
		
		
		radioButton_2.setFont(new Font("宋体", Font.PLAIN, 18));
		radioButton_2.setBounds(423, 684, 92, 27);
		radioButton_2.setFocusPainted(false);
		contentPane.add(radioButton_2);
		
		
		group.add(radioButton);
		group.add(radioButton_1);
		group.add(radioButton_2);
	}
}
