package ui;


import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * 操作图片的工具类
 * @author thinkpad
 *
 */
public class ImageUtil {
	/**
	 * 读取指定路径下的图片
	 * @param path
	 * @return
	 */
	public static BufferedImage getImg(String path) {
		//ImageIO  传输数据的通道(需要把图片转换成流)
		try {
			//尝试根据地址去找图片
			BufferedImage img=ImageIO.read(ImageUtil.class.getResource(path));
			//如果找到就返回图片
			return img;
		} catch (IOException e) {
			//下面代码会输出找不到的原因
			e.printStackTrace();
		}
		return null;
	}
}
