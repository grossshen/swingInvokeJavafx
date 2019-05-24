package sample.main;

import com.sun.jna.platform.win32.Ole32;

public class CSDKCapDemo
{
	static CMainFrame jfrm = new CMainFrame();
	public static void main(String[] args)
	{
		Ole32.INSTANCE.CoInitialize(null);
		jfrm.setVisible(true);
//		System.out.println("do destory");
//        CSDKAPI.INSTANCE.AVerUninitialize();
        Ole32.INSTANCE.CoUninitialize();
	};

}
