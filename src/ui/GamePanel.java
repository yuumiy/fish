package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * java中常用的面板：JPanel    是一个面板，附着在窗体上
 * 自定义面板的步骤
 * 1、写一个类，继承JPanel
 * @author thinkpad
 *
 */
public class GamePanel extends JPanel{
	
	//定义背景图
	BufferedImage bg=ImageUtil.getImg("/img/bg.jpg");
	
	List<Fish> fishes=new ArrayList<Fish>();
	//池塘里鱼的数量
	int fishNum=50;
	//创建渔网
	Net net=new Net();
	
	
	public GamePanel() {
		//设置窗体背景颜色，这里用不着这个属性
		setBackground(Color.black);
		//创建所有的鱼
		for (int i = 0; i < fishNum; i++) {
			fishes.add(new Fish(this));
		}
		//开启鼠标监听器
		createMouseListener();
	}
	
	/**
	 * 创建鼠标监听器,监听鼠标的活动
	 */
	public void createMouseListener() {
		//1、创建鼠标适配器
		MouseAdapter adapter=new MouseAdapter() {
			//监听鼠标移动时间
			@Override
			public void mouseMoved(MouseEvent e) {
				//获取鼠标移动的位置
				//获取鼠标的横坐标，纵坐标
				int mx=e.getX();
				int my=e.getY();
				//让渔网移动到鼠标的位置上
				net.x=mx-net.w/2;
				net.y=my-net.h/2;
				repaint();
			}
			//监听鼠标点击事件,左键捕鱼，右键增大活力
			@Override
			public void mouseClicked(MouseEvent e) {
				//获取鼠标点击时对应的数字,左键16，右键4
				int m=e.getModifiers();
				if(m==16) {
					//捕鱼
					catchTheFish();
				}
				if(m==4) {
					//改变渔网大小
					changePower();
					//获取鼠标的横坐标，纵坐标
					int mx=e.getX();
					int my=e.getY();
					//让渔网移动到鼠标的位置上
					net.x=mx-net.w/2;
					net.y=my-net.h/2;
				}
				repaint();
			}
			//监听鼠标移出事件：当鼠标移出之后让渔网消失
			@Override
			public void mouseExited(MouseEvent e) {
				//隐藏渔网
				net.show=false;
				repaint();
			}
			//监听鼠标移入时间：当鼠标移入屏幕显示渔网
			@Override
			public void mouseEntered(MouseEvent e) {
				//显示渔网
				net.show=true;
				repaint();
			}
		};
		//2、将鼠标适配器加入到监听器中
		addMouseMotionListener(adapter);
		addMouseListener(adapter);
	}
	
	//点击左键时捕鱼
	protected synchronized void catchTheFish() {
		//因为所有的鱼都可能被捕到，遍历所有的鱼
		for (int i = 0; i < fishNum; i++) {
			//获取每一条鱼
			Fish f=fishes.get(i);
			//判断获取到的鱼是否被网捕到
			if(net.catchFish(f)) {
				f.blood--;
				if(f.blood==0) {
					//将鱼的状态改为被捕获
					f.catched=true;
					if(bullet>0)
						point=point+f.index*10;
				}
			}
		}
		if(bullet>=0)
			bullet=bullet-power;
		if(bullet<0) {
			bullet=0;
			JOptionPane.showMessageDialog(null, "弹药已用尽,"+"你最后的得分是："+point);
		}
	}

	//点击右键时改变渔网的活力
	int power=1;
	int bullet=500;
	int point=0;
	protected void changePower() {
		//每次点击右键增大power值
		power++;
		if(power>7) {
			power=1;
		}
		net.change(power);
	}

	/**
	 * 开始游戏的方法
	 */
	public void action() {
		//让鱼游动起来
		for (int i = 0; i < fishNum; i++) {
			Fish fish=fishes.get(i);
			//启动鱼的线程让鱼游动,因为鱼继承Thread类，可以用start方法
			fish.start();
		}
	}
	/**
	 * 画图方法
	 * g代表手上的画笔
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//g.drawImage(图片,横坐标,纵坐标,null)   0,0是左上角的点
		//画背景图
		g.drawImage(bg,0,0,null);
		//设置画笔颜色
		g.setColor(Color.yellow);
		//设置字体的大小
		g.setFont(new Font("楷体",Font.BOLD,20));
		//画文字
		g.drawString("分数:"+point, 20, 30);
		
		//画子弹个数
		g.drawString("子弹数:"+bullet, 300,30);
		
		//画火力值
		g.drawString("power:"+power, 650, 20);
		g.setColor(Color.red);
		g.drawString("右键调整火力值", 615, 40);
		
		//遍历所有的鱼,画鱼
		for (int i = 0; i <fishNum; i++) {
			Fish fish=fishes.get(i);
			
			//画实心矩形，作为血条
			g.fillRect(fish.x, fish.y-5, fish.blood*10, 5);
			//画鱼,因为画板是直接在游戏界面上，所以最后那个参数设置为空
			g.drawImage(fish.img, fish.x, fish.y, fish.w,fish.h,null);
		}
		
		//画渔网
		if(net.show) {
			g.drawImage(net.img, net.x, net.y,net.w,net.h,null);
		}
		
	}
}
