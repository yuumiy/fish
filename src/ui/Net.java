package ui;
/**
 * 渔网类
 * @author thinkpad
 *
 */

import java.awt.image.BufferedImage;

public class Net {
	//渔网的图片
	BufferedImage img;
	int x;//渔网的横坐标
	int y; //渔网的纵坐标
	int w; //渔网的宽度
	int h;  //渔网的高度
	boolean show;//是否显示渔网
	
	public Net() {
		//确定网的图片
		img=ImageUtil.getImg("/img/net_1.png");
		//确定图片的大小，用图片的大小作为网的大小
		w=img.getWidth();
		h=img.getHeight();
		//确定游戏开始时，网的位置
		x=200;
		y=200;
	}
	
	//改变渔网的大小
	public void change(int power) {
		//根据power值选择图片
		img=ImageUtil.getImg("/img/net_"+power+".png");
		//重新获取图片大小
		w=img.getWidth();
		h=img.getHeight();
	}
	
	//判断鱼是否被网捕到
	public boolean catchFish(Fish f) {
		boolean hit=f.x<=x+w && f.x>=x-f.w && f.y<=y+h && f.y>=y-f.h;
		return hit;
	}
}
