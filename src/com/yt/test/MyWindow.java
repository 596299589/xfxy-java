package com.yt.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class MyWindow extends JFrame {

	private static final long serialVersionUID = -6580208698260406651L;
	private MyService myService;

	int index;
	private ArrayList<Image> mImageList;

	public MyWindow() { // 构造函数
		initService();
		initView();
	}

	private void initService() {
		myService = MyService.getInstance();
		myService.init();
		myService.setOnMessageListener(mOnMessageListener);
	}

	private void initView() {
		this.setUndecorated(true); // 不显示边框
		// com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.0f);
		// //设置窗体透明度，需与setUndecorated(true)结合使用

		// 窗体风格
		// JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("幸福享印智拍馆");
		this.setResizable(true);// 可以调节窗口的大小

		// 得到显示器屏幕的宽高
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		int windowsWidth = width;
		int windowsHeight = height;
		this.setSize(windowsWidth, windowsHeight);// 窗口大小
		// 设置窗体在显示器居中显示
		// this.setBounds((width - windowsWidth) / 2,
		// (height - windowsHeight) / 2, windowsWidth, windowsHeight);

		try {
			// 更改左上角小图标
			Image image = ImageIO.read(this.getClass().getResource("/img/timg.jpg"));
			this.setIconImage(image);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		this.addWindowListener(new WindowAdapter() {// 关闭窗口自动退出程序
			public void windowClosing(WindowEvent we) {
				// myService.release();
				System.exit(0);
			}
		});

		initPanel();

		// 关闭窗口自动退出程序
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setWindowMaximum();
	}

	private MyJPanel mPanel;

	private void initPanel() {
		mImageList = new ArrayList<Image>();
		try {
			// 更改左上角小图标
			Image image11 = ImageIO.read(this.getClass().getResource("/img/1.jpg"));
			Image image12 = ImageIO.read(this.getClass().getResource("/img/2.jpg"));
			Image image13 = ImageIO.read(this.getClass().getResource("/img/3.jpg"));
			Image image14 = ImageIO.read(this.getClass().getResource("/img/4.jpg"));
			mImageList.add(image11);
			mImageList.add(image12);
			mImageList.add(image13);
			mImageList.add(image14);

		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		mPanel = new MyJPanel();
		mPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO 自动生成的方法存根
				setWindowMinimum();
			}
		});
		this.add(mPanel, BorderLayout.CENTER);
		Timer timer = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mPanel.repaint();
			}
		});
		timer.start();
	}

	class MyJPanel extends JPanel {
		private static final long serialVersionUID = -20088985234233354L;

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			// g.drawImage(imgs[index % imgs.length].getImage(), 0, 0,
			// getWidth(),
			// getHeight(), this);
			g.drawImage(mImageList.get(index % mImageList.size()), 0, 0, getWidth(), getHeight(), this);
			index++;
		}
	}

	MyService.OnMessageListener mOnMessageListener = new MyService.OnMessageListener() {

		@Override
		public void onMessage(String message) {
			// TODO 自动生成的方法存根
			if ("show".equals(message)) {
				setWindowMaximum();
			} else if ("hide".equals(message)) {
				setWindowMinimum();
			}
		}
	};

	/**
	 * 设置窗体最大化
	 */
	private void setWindowMaximum() {
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); // 最大化
	}

	/**
	 * 设置窗体最小化
	 */
	private void setWindowMinimum() {
		this.setAlwaysOnTop(false);
		// this.setVisible(false);
		this.setExtendedState(JFrame.ICONIFIED); // 最小化
	}

}
