package viewKit;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/*	gridx gridy 属性
 *  gridx设置组件起始点所在单元格的索引值，表示该单元格所在列的索引；gridy表示行
 *  gridwidth 组件以单元格为单位的宽度(所占用的列的个数)
 *  anchor设置组件在其所在显示区域的显示位置
 *  fill当组件不必占用整个单元格时的填充策略
 *  insets组件与周围组件的上、左、下、右的最小距离
 *  ipadx ipady用来在首选(默认)大小基础上更改组件的大小，正数为增加，负数为减小
 *  weightx设置组件所在列对额外空间的分布方式
 * */

public class GBC extends GridBagConstraints  
{  
	public GBC(){
		super();
	}
   //初始化左上角位置  
	//第一个参数是行索引,第二个是列索引,从0开始
   public GBC(int gridy, int gridx)  
   {  
	  super();
      this.gridx = gridx;  
      this.gridy = gridy;  
   }  
  
   //初始化左上角位置和所占行数和列数  
   public GBC(int gridx, int gridy, int gridwidth, int gridheight)  
   {  
	  super();
      this.gridx = gridx;  
      this.gridy = gridy;  
      this.gridwidth = gridwidth;  
      this.gridheight = gridheight;  
   }  
  
   //对齐方式  
   public GBC setAnchor(int anchor)  
   {  
      this.anchor = anchor;  
      return this;  
   }  
  
   //是否拉伸及拉伸方向  
   public GBC setFill(int fill)  
   {  
      this.fill = fill;  
      return this;  
   }  
  
   //x和y方向上的增量  
   public GBC setWeight(double weightx, double weighty)  
   {  
      this.weightx = weightx;  
      this.weighty = weighty;  
      return this;  
   }  
  
   //外部填充  
   public GBC setInsets(int distance)  
   {  
      this.insets = new Insets(distance, distance, distance, distance);  
      return this;  
   }  
  
   //外填充  
   public GBC setInsets(int top, int left, int bottom, int right)  
   {  
      this.insets = new Insets(top, left, bottom, right);  
      return this;  
   } 
   //外填充  
   public GBC setInsets(int top, int left)  
   {  
      this.insets = new Insets(top, left, top, left);  
      return this;  
   } 
  
   //内填充  
   public GBC setIpad(int ipadx, int ipady)  
   {  
      this.ipadx = ipadx;  
      this.ipady = ipady;  
      return this;  
   }  
}  
