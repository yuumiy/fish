package ui;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.JFrame;

/**
 * Java中的窗体类：JFrame     是一个窗体
 * 自定义窗体步骤：
 * 1、写一个类，继承JFrame
 * 2、写一个构造方法，确定窗体的特点
 * @author thinkpad
 *
 */
public class GameFrame extends JFrame{
	/**
	 * 构造方法：名称和类名一样
	 */
	public GameFrame() {
		//设置标题
		setTitle("捕鱼达人");
		//设置窗体的大小
		setSize(800,480);
		//设置窗体的图标
		URL url = this.getClass().getResource("/img/welcome.jpg");
		Image img = Toolkit.getDefaultToolkit().getImage(url);
		this.setIconImage(img);
		//设置位置居中显示
		setLocationRelativeTo(null);
		//设置不允许改变窗体的大小
		setResizable(false);
		//设置默认的关闭选项
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		//使用模具创建窗体
		GameFrame frame=new GameFrame();
		//使用面板模具，制作面板
		GamePanel panel=new GamePanel();
		//开始游戏
		panel.action();
		//将面板加入到窗体中
		frame.add(panel);
		//显示窗体
		frame.setVisible(true);
	}
}
