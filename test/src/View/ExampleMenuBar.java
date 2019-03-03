package View;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


/**
 * 
* @ClassName: ExampleMenuBar 
* @Description:
* @author xudongze
* @date 2018��9��4�� ����8:32:09 
*
 */
public abstract class ExampleMenuBar extends JMenuBar {
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	protected JMenu fileMenu;
	protected JMenuItem save, open, create, saveAs;
	
	public ExampleMenuBar() {
		init();
	}
	
	private void init() {
		fileMenu = new JMenu("File");
		open = new JMenuItem("Open...");
		create = new JMenuItem("New...");
		save = new JMenuItem("Save");
		saveAs = new JMenuItem("Save as...");
		
		fileMenu.add(open);
		fileMenu.addSeparator();
		fileMenu.add(create);
		fileMenu.addSeparator();
		fileMenu.add(save);
		fileMenu.add(saveAs);
		add(fileMenu);
	}
	
	protected abstract class MyOpenBntListener implements ActionListener{};
	protected abstract class MyCreateBntListener implements ActionListener{};
	protected abstract class MySaveBntListener implements ActionListener{};
	protected abstract class MySaveAsBntListener implements ActionListener{};
	
	/**
	 * 
	* @Title: addAllItemListeners 
	* @Description: �������е�JMenuItem �ļ�����
	* @param 
	* @return void
	 */
	protected abstract void initItemListeners();
	
	/**
	 * 
	* @Title: addItemAccelaretors 
	* @Description: ΪJmenuBar �е�����item�󶨿�ݼ�
	* @param 
	* @return void
	 */
	protected abstract void initItemAccelaretors();
	
	/**
	 * 
	* @Title: disableItems 
	* @Description: ��ʼ��item�Ƿ���Ч
	* @param 
	* @return void
	 */
	protected abstract void initItemEnables();

	public JMenu getFileMenu() {
		return fileMenu;
	}

	public void setFileMenu(JMenu fileMenu) {
		this.fileMenu = fileMenu;
	}

	public JMenuItem getSave() {
		return save;
	}

	public void setSave(JMenuItem save) {
		this.save = save;
	}

	public JMenuItem getOpen() {
		return open;
	}

	public void setOpen(JMenuItem open) {
		this.open = open;
	}

	public JMenuItem getCreate() {
		return create;
	}

	public void setCreate(JMenuItem create) {
		this.create = create;
	}

	public JMenuItem getSaveAs() {
		return saveAs;
	}

	public void setSaveAs(JMenuItem saveAs) {
		this.saveAs = saveAs;
	}

}