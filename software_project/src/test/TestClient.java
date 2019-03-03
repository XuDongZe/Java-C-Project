package test;

import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import Constant.Constants;
import data.CCommonData;
import data.MsgEntry;
import Net.*;

public class TestClient implements Constants{
	private ClientNet cNet;
	private MsgEntry msg;
	
	public TestClient() {
		msg = new MsgEntry(LOGIN).setID("xudongze").setPsw("xdz1120jc87");
		cNet = new ClientNet();
		cNet.sendMsg(cNet.getSocket(), msg);
	}
	
	public static void main(String[] args) {
		//new TestClient();
	}
}
