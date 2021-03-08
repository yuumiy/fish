package ui;


import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 鱼类
 * @author thinkpad
 *
 */
public class Fish extends Thread{
	BufferedImage img;//鱼点前显示的图片
	int x;//鱼的横坐标
	int y;//鱼的纵坐标
	int w;//鱼的宽度
	int h;//鱼的高度
	GamePanel panel;
	//定义一个产生随机数的类
	Random rd=new Random();
	//定义集合，用来存放鱼游动时所有图片
	List<BufferedImage> animation=new ArrayList<BufferedImage>();
	//定义集合，用来存放鱼被捕时显示动画的所有图片
	List<BufferedImage> catchAnimation=new ArrayList<BufferedImage>();
	//鱼被捕时动画图片的数量
	int catchNum;
	//判断鱼是否被捕获的开关
	boolean catched;
	//鱼的血量
	int blood;
	//鱼的种类
	int index;
	
	
	public Fish(GamePanel panel) {
		this.panel=panel;
		
		//随机出鱼的种类      1-11
		index=rd.nextInt(11)+1;
		//拼接鱼图片名称的前缀
		String fishName="/img/fish0"+index+"_";
		//加载鱼游动时动画图片
		for (int i = 0; i < 10; i++) {
			int fi=i+1;
			String prefix=((fi==10)?"":"0")+fi;
			//拼接图片地址
			String imageName=fishName+prefix+".png";
			//使用工具类，加载图片
			BufferedImage img=ImageUtil.getImg(imageName);
			animation.add(img);
		}
		catchNum=index<=7?2:4;
		String imgName="";
		for (int i = 1; i <= catchNum; i++) {
			//拼接鱼被捕图片的路径
			imgName=fishName+"catch_0"+i+".png";
			//根据图片的路径加载图片
			BufferedImage img=ImageUtil.getImg(imgName);
			catchAnimation.add(img);
		}
		//确定鱼显示的图片
		img=animation.get(0);
			
		//确定鱼的大小，图片大小即为鱼的大小
		w=img.getWidth();
		h=img.getHeight();
		
		//确定游戏开始时鱼显示的位置
		//next(num) 表示会在[0,num)随机生成一整数
		x=rd.nextInt(800-w);
		y=rd.nextInt(480-h);
		//设置鱼的血量,按一下让血量减少1，血减少到0时消失
		blood=index+2;
	}
	
	//线程启动时会执行的方法
	@Override
	public void run() {
		/**
		 * 让鱼一直移动
		 */
		while(true) {
			if(catched) {
				//显示鱼被捕的动画
				turnOver();
				//让鱼变为没被捕的状态，重新出现
				catched=false;
				//让鱼出场，重新出现
				getOut();
				
			}
			//调用鱼游动的方法
			move();
			//线程休眠(休眠的毫秒数)
			try {
				Thread.sleep(100);
				panel.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	//显示鱼被捕时的动画
	private void turnOver() {
		//使用一个循环让动画显示4遍
		for(int j=0;j<4;j++) {
			for (int i = 0; i <catchNum; i++) {
				img=catchAnimation.get(i);
				w=img.getWidth();
				h=img.getHeight();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//鱼移动的步数
	int step_num=0;
	/**
	 * 鱼游动的方法
	 */
	public void move() {
		//增加鱼的步数
		step_num ++;
		//切换图片
		img=animation.get(step_num%10);
		w=img.getWidth();
		h=img.getHeight();
		//向左移动
		x-=3;
		//如果鱼向左移动到屏幕外面之后，让鱼重新出现
		if(x<=-w) {
			getOut();
		}
	}
	/**
	 * 被捕或者越界了，重新出场
	 */
	public void getOut() {
		x=800;
		y=rd.nextInt(480-h);
		blood=index+2;
	}
}
