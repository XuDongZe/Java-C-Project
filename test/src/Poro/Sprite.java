package Poro;

import java.awt.Point;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Animation.Animation;
import Constant.Constant;
import Constant.SpriteType;

public class Sprite implements Serializable {
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	protected static int nums;
	
	protected int no;
	protected String name;
	protected IconLabel iconLabel;
	protected SpriteType type;
	protected String descriptor;
	
	protected int height;
	protected int width;
	protected Point center;// ob from the sprite.
	
	static {
		nums = 0;
	}
	
	/**
	 * 
	* @Title: destroy 
	* @Description: 销毁该精灵，首先不显示图像，然后强制回收垃圾
	* @param 
	* @return void
	 */
	public void destroy() {
		iconLabel.setVisible(false);
		iconLabel = null;
		name = null;
		descriptor = null;
		
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		nums--;
	}
	
	public Sprite(String name, IconLabel iconLabel, SpriteType type, String deString) {
		this.name = name;
		this.iconLabel = iconLabel;
		iconLabel = iconLabel.selfDup();
		this.type = type;
		this.descriptor = deString;
		
		height = Constant.CS;
		width = Constant.CS;
		center = new Point(height/2, width/2);//default center point
		
		nums++;
		no = nums;
	}
	
	public Sprite(String name, String iconUrl, SpriteType type, String deString) {
		this(name, new IconLabel(iconUrl), type, deString);
	}
	
	public Sprite()	{
		this("default name", new IconLabel(), SpriteType.BACKGROUND, "default description");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public IconLabel getIconLabel() {
		return iconLabel;
	}
	public void setIconLabel(IconLabel iconLabel) {
		this.iconLabel = iconLabel;
	}
	public SpriteType getType() {
		return type;
	}
	public void setType(SpriteType type) {
		this.type = type;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public String toString() {
		return "Sprite [name=" + name + ", iconLabel=" + iconLabel + ", type=" + type + ", descriptor=" + descriptor
				+ "]";
	}
	
	@Override
	public int hashCode() {
		return no;
	}
}
