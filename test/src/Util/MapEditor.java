package Util;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.FlatteningPathIterator;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.JTextComponent.KeyBinding;

import Constant.Constant;
import Poro.IconLabel;
import Poro.Sprite;
import View.ExampleMenuBar;

/**
 * 
* @ClassName: MapEditor 
* @Description: 地图编辑器
* @author xudongze
* @date 2018年7月19日 下午4:51:03 
*
 */
public class MapEditor extends JFrame implements Constant {
	/** 
	* @Fields serialVersionUID : 1L
	*/ 
	private static final long serialVersionUID = 1L;
	
	private JScrollPane jsp;
	private MyEditPanel editPanel;
	private JPanel cardPanel;
	private MySpritePanel spritePanel;
	private JPanel resourcePanel;
	private MyManagePanel managePanel;

	private int[][] bgMap, fgMap;
	private int[][][] historyBgMap, historyFgMap;
	private int historyIndex, historySize;
	boolean isSaved, mapFnHasSelected;
	
	public CardLayout cardLayout = new CardLayout();
	
	private final static int[][] NullMap;
	
	static {
		NullMap = new int[ROW][COL];
		for(int i=0; i<ROW; i++)
			for(int j=0; j<COL; j++)
				NullMap[i][j] = UNEDIT;
	}
	
	private void initMap() {
		bgMap = new int [ROW][COL];
		fgMap = new int [ROW][COL];
		historyBgMap = new int [MAXHISTORY][ROW][COL];
		historyFgMap = new int [MAXHISTORY][ROW][COL];
		historyIndex = historySize = 0;
	}
	
	public MapEditor() {
		super();
		initMap();
		
		resourcePanel = new JPanel();
		setJMenuBar(new MyEditMenuBar(this));
		editPanel = new MyEditPanel(this);
		spritePanel = new MySpritePanel();
		managePanel = new MyManagePanel();
		clearEditMap();
		clearHistoryMap(false);
		isSaved = true;
		mapFnHasSelected = false;
		
		cardPanel = new JPanel();

		jsp = new JScrollPane(editPanel);
		jsp.setPreferredSize(new Dimension(MapUtil.stepToPx(ROW), MapUtil.stepToPx(COL)));  
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setBorder(BorderFactory.createEtchedBorder());
        cardPanel.setLayout(cardLayout);
        cardPanel.add(jsp);
        cardPanel.add(spritePanel);
        
		setLayout(new BorderLayout());
		add(resourcePanel, BorderLayout.WEST);
		add(cardPanel, BorderLayout.CENTER);
		add(managePanel, BorderLayout.EAST);
		
		setVisible(true);
		pack();
		setResizable(false);
		ShowCenter.showCenter(this);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent we) {
				  if( isSaved == false ) {
					 int res = JOptionPane.showConfirmDialog(getParent(), "仍有改动未保存, 确定要退出吗?"
							 , "确认", JOptionPane.YES_NO_OPTION);
					 if( res == JOptionPane.YES_OPTION ) {
						 System.exit(0);
					 }
				  }else {
					  System.exit(0);
				  }
			  }
		});
	}
	
	/**
	 * 
	* @Title: copyPoint 
	* @Description: 将地图里以点(px,py)为基准点(右上方)，复制到size大小的方形里。
	* @param @param array 二维地图
	* @param @param px	基准点横坐标, 单位为step
	* @param @param py  基准点纵坐标, 单位为step
	* @param @param size 扩展的大小, 可选值为1, 2, 3, 4, 5
	* @return void
	 */
	private void copyPoint(int [][] array, int px, int py, int size) {
		int value = array[px][py];
		for(int i=px; i<ROW && i<px+size; i++)
			for(int j=py; j<COL && j<py+size; j++)
				array[i][j] = value;
	}
	private void copyMap(int [][]dst, int source[][], int row, int col) {
		for(int i=0; i<ROW; i++)
			for(int j=0; j<COL; j++)
				dst[i][j] = source[i][j];
	}
	
	private void setCoursor(int px, int py) {
		setCursor(Cursor.getDefaultCursor());
	}
	private void setCoursor(int px, int py, ImageIcon icon, int size) {
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
				icon.getImage(), new Point(px,  py),"mouse");
		setCursor(cursor);
	}
	/**
	 * 
	* @Title: clearHistoryMap 
	* @Description: 清除历史记录, 在打开新地图, 创建新地图, 执行一次save或者saveAs操作后.
	* @param @param isAdd 清除历史记录后是否将当前地图添加到历史纪录
	* @return void
	 */
	private void clearHistoryMap(boolean isAdd) {
		if( historyBgMap != null  ) {
			for(int k=0; k<historyBgMap.length; k++) {
				copyMap(historyBgMap[k], NullMap, ROW, COL);
				copyMap(historyFgMap[k], NullMap, ROW, COL);
			}
		}
		
		historyIndex = historySize = 0;
		if(isAdd == true)
			addHistoryList();
		
		getMyMenuBar().getPrev().setEnabled(false);
		getMyMenuBar().getNext().setEnabled(false);
	}
	private void clearEditMap() {
		if( bgMap != null && fgMap != null ) {
			copyMap(bgMap, NullMap, ROW, COL);
			copyMap(fgMap, NullMap, ROW, COL);
		}
		
		repaint();
	}
	private void addHistoryList() {
		if( historyBgMap != null && historySize >= MAXHISTORY && historyFgMap != null ) {
			for( int k=1; k<=historySize-1; k++ ) {
				copyMap(historyBgMap[k-1], historyBgMap[k], ROW, COL);
				copyMap(historyFgMap[k-1], historyFgMap[k], ROW, COL);
			}
		}
		if(historyBgMap != null && historyFgMap != null) {
			historySize = historySize >= MAXHISTORY ? MAXHISTORY : historySize + 1;
			copyMap(historyBgMap[historySize - 1], bgMap, ROW, COL);
			copyMap(historyFgMap[historySize - 1], fgMap, ROW, COL);
		}
		
		historyIndex = historySize > 0 ? historySize - 1 : 0;
	}
	private void loadMap(String mapFn) {
		bgMap = MapUtil.getCurrentBgMap(mapFn);
		fgMap = MapUtil.getCurrentFgMap(mapFn);
		
		addHistoryList();
		editPanel.repaint();
	}
	
	/**
	 * 
	* @Title: drawGrid 
	* @Description: 辅助线显示与否，快捷键 ctrl-g，注意在面板中要先drwaMap然后再drawGrid，覆盖问题
	* @param @param g
	* @return void
	 */
	private void drawGrid(Graphics g) { 
		//设置虚线
        g.setColor(Color.GRAY);
        BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, 
        		BasicStroke.JOIN_BEVEL, 10.0f, new float[] {2.5f, 2.5f}, 0);
        ((Graphics2D) g).setStroke(stroke);
        //画竖线
		for(int x=1; x<=COL; x++) {
			g.drawLine(	MapUtil.stepToPx(x), 0, MapUtil.stepToPx(x), MapUtil.stepToPx(ROW));
		}
		//画横线
		for(int y=1; y<=ROW; y++) {
			g.drawLine(0, MapUtil.stepToPx(y), MapUtil.stepToPx(COL), MapUtil.stepToPx(y));
		}
	}
	
	private void drawMap(Graphics g) {
		for(int y = 0; y < ROW; y++) {
			for(int x = 0; x < COL; x++) {
				if( bgMap[y][x] != UNEDIT ) {
					g.drawImage(ImageUtil.decodeImg(bgMap[y][x]).getImage(),
								MapUtil.stepToPx(x), MapUtil.stepToPx(y),
								null);
				}
				if( fgMap[y][x] != UNEDIT ) {
					g.drawImage(ImageUtil.decodeImg(fgMap[y][x]).getImage(),
							MapUtil.stepToPx(x), MapUtil.stepToPx(y),
							null);
				}
			}
		}
	}
	
	private class MyPrevBntListener implements ActionListener {
		public void actionPerformed(ActionEvent ea) {
			historyIndex = historyIndex <= 0 ? 0 : historyIndex - 1;
			copyMap(bgMap, historyBgMap[historyIndex], ROW, COL);
			copyMap(fgMap, historyFgMap[historyIndex], ROW, COL);
			
			if( historyIndex > 0 ) 
				getMyMenuBar().getPrev().setEnabled(true);
			else
				getMyMenuBar().getPrev().setEnabled(false);
			if( historyIndex < historySize - 1 ) 
				getMyMenuBar().getNext().setEnabled(true);
			else 
				getMyMenuBar().getNext().setEnabled(false);
			
			editPanel.repaint();
		}
	}
	private class MyNextBntListener implements ActionListener {
		public void actionPerformed(ActionEvent ea) {
			historyIndex = historyIndex >= historySize - 1 ? historySize - 1: historyIndex + 1;
			copyMap(bgMap, historyBgMap[historyIndex], ROW, COL);
			copyMap(fgMap, historyFgMap[historyIndex], ROW, COL);
			
			if( historyIndex < historySize - 1 ) 
				getMyMenuBar().getNext().setEnabled(true);
			else 
				getMyMenuBar().getNext().setEnabled(false);
			if( historyIndex > 0 ) 
				getMyMenuBar().getPrev().setEnabled(true);
			else
				getMyMenuBar().getPrev().setEnabled(false);
			
			editPanel.repaint();
		}
	}
	
	private MyEditMenuBar getMyMenuBar() {
		return (MyEditMenuBar)getJMenuBar();
	}
	private MyManagePanel getMyManagePanel() {
		return managePanel;
	}
	private MyEditPanel getMyEditPanel() {
		return editPanel;
	}
	private boolean getIsSaved(){
		return isSaved;
	}
	
	private class MyEditMenuBar extends ExampleMenuBar {
		/** 
		* @Fields serialVersionUID : 
		*/ 
		private static final long serialVersionUID = 1L;
		private JMenu 	edit, 
						sprite;
		private JMenuItem 	prev, next, grid,
							inport, outport;
		private MapEditor editor;
		
		public MyEditMenuBar(MapEditor editor) {
			this.editor = editor;
			init();
		}
		
		private void init() {
			edit = new JMenu("Edit");
			prev = new JMenuItem("Undo");
			next = new JMenuItem("Redo");
			grid = new JMenuItem("Grid");
			edit.add(prev);
			edit.add(next);
			edit.addSeparator();
			edit.add(grid);
			
			sprite = new JMenu("Sprite");
			inport = new JMenuItem("Import...");
			outport = new JMenuItem("Export...");
			sprite.add(inport);
			sprite.add(outport);
			
		    add(edit);
			add(sprite);
			
			initItemEnables();
			initItemListeners();
			initItemAccelaretors();
		}
		
		private class MyImportBntListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.out.println("import sprite");
			}
		}
		
		private class MyExportBntListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.out.println("exports sprite");
			}
		}
		
		private class MyGridBntListener implements ActionListener {
			public void actionPerformed(ActionEvent paramActionEvent) {
				editor.getMyEditPanel().setDrawGrid( !editor.getMyEditPanel().getDrawGrid() );
				editor.getMyEditPanel().repaint();
			}
		}
		private class MyOpenBntListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				int res = JOptionPane.YES_OPTION;
				if( isSaved == false ) {
					res = JOptionPane.showConfirmDialog(editor, "当前文件有改动未保存, 仍要打开新文件吗?"
							, "确认", JOptionPane.YES_NO_OPTION);
				}
				if( res == JOptionPane.YES_OPTION ) {
					JFileChooser fc = FileManagerUtil.getFileChooser("map");
					if(fc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
						String selectPath = fc.getSelectedFile().getPath();
						mapFnHasSelected = true;
						loadMap(selectPath);
						
						saveAs.setEnabled(true);
						editor.setTitle(selectPath);
						
						clearHistoryMap(true);
					}
				}
			}
		}
		private class MyCreateBntListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				int res = JOptionPane.YES_OPTION;
				if( isSaved == false ) {
					res = JOptionPane.showConfirmDialog(editor, "当前文件有改动未保存, 仍要创建新文件吗?"
							, "确认", JOptionPane.YES_NO_OPTION);
				}
				if( res == JOptionPane.YES_OPTION ) {
					JFileChooser fc = FileManagerUtil.getFileChooser("map");
					if(fc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
						String selectPath = fc.getSelectedFile().getPath();
						mapFnHasSelected = true;
						MapUtil.saveMap(selectPath, null, null);
						MapUtil.setCurrentFn(selectPath);
						
						save.setEnabled(true);
						saveAs.setEnabled(true);
						isSaved = false;
						editor.setTitle("*" + selectPath);
						
						clearEditMap();
						clearHistoryMap(true);
					}
				}
			}
		}
		private class MySaveBntListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				//保存到当前打开文件
				MapUtil.saveMap(MapUtil.getCurrentFn(), bgMap, fgMap);
			    save.setEnabled(false);
				isSaved = true;
				editor.setTitle(editor.getTitle().replaceAll("\\*", ""));
				
				clearHistoryMap(true);
			}
		}
		private class MySaveAsBntListener implements ActionListener {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fc = FileManagerUtil.getFileChooser();;
				
				if(fc.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
					String selectPath = fc.getSelectedFile().getPath();
					mapFnHasSelected = true;
					MapUtil.saveMap(selectPath, bgMap, fgMap);
					isSaved = true;
					editor.setTitle(editor.getTitle().replaceAll("\\*", ""));
					
					clearHistoryMap(true);
				}
			}
		}

		@Override
		public void initItemListeners() {
			open.addActionListener(new MyOpenBntListener());
			create.addActionListener(new MyCreateBntListener());
			save.addActionListener(new MySaveBntListener());
			saveAs.addActionListener(new MySaveAsBntListener());
			
			prev.addActionListener(new MyPrevBntListener());
			next.addActionListener(new MyNextBntListener());
			grid.addActionListener(new MyGridBntListener());
			
			inport.addActionListener(new MyImportBntListener());
			outport.addActionListener(new MyExportBntListener());
		}
		@Override
		public void initItemAccelaretors() {
			open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
			create.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
			save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
			saveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
			
			prev.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
			next.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
			grid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
		}
		@Override
		public void initItemEnables() {
			save.setEnabled(false);
			saveAs.setEnabled(false);
			prev.setEnabled(false);
			next.setEnabled(false);
		}

		public JMenuItem getPrev() {
			return prev;
		}

		public JMenuItem getNext() {
			return next;
		}

		public JMenuItem getGrid() {
			return grid;
	}
}
	
	private class MyManagePanel extends JPanel {
		/** 
		* @Fields serialVersionUID : 
		*/ 
		private static final long serialVersionUID = 1L;
		
		private JPanel choosePanel;
		private IconLabel chooseIcon;
		private ArrayList<Sprite> sprites;
		private JComboBox<String> chooseTypes, chooseSizes;
		private final String[] typeStrs = {"背景", "NPC", "怪物", "工具", "障碍物"};
		private final String[] sizeStrs = {"1*1", "2*2", "3*3", "4*4", "5*5"};
		private int size;

		public MyManagePanel() {
			super();
			init();
		}
		
		private void init() {
			size = 1;
			sprites = new ArrayList<>();
			choosePanel = new JPanel();
			GridBagLayout layout = new GridBagLayout();
			choosePanel.setLayout(layout);
//			icons = ImageUtil.getIconLabelMatrix(
//					ImageUtil.processIconLabels(ImageUtil.loadIcon()), 5);
//			for(int i=0; i<sprites.length; i++) {
//				for(int j=0; sprites[i]!=null && j<sprites[i].length; j++) {
//					if(sprites[i][j] != null) {
//						IconLabel label = sprites[i][j].getIconLabel();
//						layout.setConstraints(label, new GBC(i, j));
//						choosePanel.add(label);
//					}
//				}
//			}
			JScrollPane jScrollPane = new JScrollPane(choosePanel);
			JScrollBar vbar = jScrollPane.getVerticalScrollBar();
			vbar.setPreferredSize(new Dimension(10, 0));
			jScrollPane.setPreferredSize(new Dimension((int)choosePanel.getPreferredSize().getWidth()
					+ (int)vbar.getPreferredSize().getWidth(), 100));
			jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			chooseTypes = new JComboBox<>();
			for(int i=0; i<typeStrs.length; i++)	chooseTypes.addItem(typeStrs[i]);
			chooseTypes.setMaximumRowCount(4);
			chooseTypes.addActionListener(new myTypeListener());
			
			chooseSizes = new JComboBox<>();
			for(int i=0; i<sizeStrs.length; i++)	chooseSizes.addItem(sizeStrs[i]);
			chooseSizes.addActionListener(new mySizeListener());

			JPanel panel = new JPanel();
			layout = new GridBagLayout();
			layout.setConstraints(chooseTypes, new GBC(0, 0).setInsets(5));
			layout.setConstraints(chooseSizes, new GBC(0, 1).setInsets(5));
			panel.setLayout(layout);
			panel.add(chooseTypes);
			panel.add(chooseSizes);
			
			setLayout(new BorderLayout());
			add(panel, BorderLayout.NORTH);
			add(jScrollPane, BorderLayout.CENTER);
			
			setSize(new Dimension(200, 600));
			setBorder(BorderFactory.createEtchedBorder());
		}
		
		private class mySizeListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String select = (String) chooseSizes.getSelectedItem();
				size = Integer.parseInt( select.substring(0, 1) );
				System.out.println("size=" + size);
			}
		}
	
		private class myTypeListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String select = (String) chooseTypes.getSelectedItem();
				if(select.equals("背景")) {
					
				}else if(select.equals("NPC")) {
					
				}else if(select.equals("怪物")) {
					
				}else if(select.equals("工具")) {

				}else if(select.equals("障碍物")) {

				}
			}
		}

		public JComboBox<String> getChooseTypes() {
			return chooseTypes;
		}
		public int getChooseSize() {
			return size;
		}
	}
	
	private class MySpritePanel extends JPanel implements Constant {
		public MySpritePanel() {
			setBackground(Color.red);
			setPreferredSize(new Dimension(MapUtil.stepToPx(COL), MapUtil.stepToPx(ROW)));
			setVisible(true);
		}
	}
	
	private class MyEditPanel extends JPanel implements Constant {
		/** 
		* @Fields serialVersionUID : 1L
		*/ 
		private static final long serialVersionUID = 1L;
		private int x, y;
		private MapEditor editor;
		private boolean isDrawGrid;
		
		public MyEditPanel(MapEditor editor) {
			this.editor = editor;
			x = y = -1;
			isDrawGrid = false;
			
			addMouseListener(new MyPanelListner());
			addMouseMotionListener(new MyPanelListner());
			
			//setPreferredSize(new Dimension(MapUtil.stepToPx(ROW), MapUtil.stepToPx(COL)));
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			drawMap(g);
			if( isDrawGrid ) { drawGrid(g); }
		}
		
		private class MyPanelListner extends MouseAdapter {
			public void mousePressed(MouseEvent me) {
				x = MapUtil.pxToStep( me.getX() ); 
				y = MapUtil.pxToStep( me.getY() );
				int size = editor.getMyManagePanel().getChooseSize();
				//ImageIcon img  = (ImageIcon) managePanel.getChooseImgs().getSelectedItem();
				String type = (String) managePanel.getChooseTypes().getSelectedItem();
				
				if( type.equals("背景") ) {
					//bgMap[y][x] = ImageUtil.encodeImg(img);
					if( size != 1 )	copyPoint(bgMap, y, x, size);
				}
				else { 
					//fgMap[y][x] = ImageUtil.encodeImg(img);
					if( size != 1 )	copyPoint(fgMap, y, x, size);
				}
				
				repaint();
			}
			
			public void mouseReleased(MouseEvent me) {
				addHistoryList();
				
				isSaved = false;
				if( mapFnHasSelected )
					editor.getMyMenuBar().getSave().setEnabled(true);
				editor.getMyMenuBar().getSaveAs().setEnabled(true);
				editor.setTitle("*" + editor.getTitle().replaceAll("\\*", ""));
				
				
				if( historyIndex > 0 ) 
					editor.getMyMenuBar().getPrev().setEnabled(true);
				else 
					editor.getMyMenuBar().getPrev().setEnabled(false);
				if( historyIndex < historySize - 1 )
					editor.getMyMenuBar().getNext().setEnabled(true);
				else 
					editor.getMyMenuBar().getNext().setEnabled(false);
			}
			
			public void mouseDragged(MouseEvent me) {
				x = MapUtil.pxToStep( me.getX() ); 
				y = MapUtil.pxToStep( me.getY() );
				int size = editor.getMyManagePanel().getChooseSize();
				//ImageIcon img  = (ImageIcon) managePanel.getChooseImgs().getSelectedItem();
				String type = (String) managePanel.getChooseTypes().getSelectedItem();
				
				if( type.equals("背景") ) {
					//bgMap[y][x] = ImageUtil.encodeImg(img);
					if( size != 1 )	copyPoint(bgMap, y, x, size);
				}
				else { 
					//fgMap[y][x] = ImageUtil.encodeImg(img);
					if( size != 1 )	copyPoint(fgMap, y, x, size);
				}
				
				repaint();
			}
			
			public void mouseMoved(MouseEvent me) {
				x = MapUtil.pxToStep( me.getX() + CS/2 ); 
				y = MapUtil.pxToStep( me.getY() + CS/2 );
				int size = editor.getMyManagePanel().getChooseSize();
				//ImageIcon imgIcon  = (ImageIcon) managePanel.getChooseImgs().getSelectedItem();
				
				//System.out.println(x + "," + y);
				//System.out.println(MapUtil.pxToStep( me.getX()-CS/2 ) + "," + MapUtil.pxToStep( me.getY()-CS/2 ) );
				
				//setCoursor(x, y, imgIcon, size);
			}
			
			public void mouseExited(MouseEvent me) {
				x = MapUtil.pxToStep( me.getX() + CS/2); 
				y = MapUtil.pxToStep( me.getY() + CS/2);
				setCoursor(x, y);
			}
		}

		public boolean getDrawGrid() {
			return isDrawGrid;
		}
		public void setDrawGrid(boolean isDrawGrid) {
			this.isDrawGrid = isDrawGrid;
		}
	}
	
	public static void main(String[] args) {
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException |InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
        
		new MapEditor();
	}
}


