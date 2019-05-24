package sample.main;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.ptr.*;
import sample.SDKAPI.CSDKAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class CMainFrame extends JFrame {
    static {
        //初始化当前线程com库
        CSDKAPI.INSTANCE.AVerInitialize();
    }
    public static class DEMOSTATE {
        public static final int DEMO_STATE_STOP = 0;
        public static final int DEMO_STATE_PREVIEW = 1;
        public static final int DEMO_STATE_CAP_IMAGE = 2;
        public static final int DEMO_STATE_RECORD = 4;
        public static final int DEMO_STATE_CALLBACK_VIDEO = 8;
        public static final int DEMO_STATE_CALLBACK_AUDIO = 16;
        public static final int DEMO_STATE_CALLBACK_ESTS = 32;
    }

    public Panel getM_PanelViewWindow() {
        return m_PanelViewWindow;
    }

    // 窗体控件
    private Panel m_PanelViewWindow;
    private Font m_f = new Font("MS Song", Font.PLAIN, 12);
    // 菜单栏成员属性声明
    private JMenuBar m_mb; // 菜单棒
    private JMenu m_FileMenu; // 文件菜单
    private JMenu m_DeviceMenu; // 设备菜单
    private JMenu m_FunctionMenu; // 功能菜单
    private JMenu m_UploadMenu; //上传菜单
    private JMenu m_HelpMenu; //帮助菜单
    private JMenuItem m_FileMenuRestore, m_FileMenuExit; // 文件菜单的菜单项
    private JCheckBoxMenuItem[] m_DeviceMenuItemDevice; // 设备菜单的菜单项

    private JMenuItem m_FunctionMenuStartStreaming, m_FunctionMenuStopStreaming, m_FunctionMenuCaptureImage,
            m_FunctionMenuDeviceSetting, m_FunctionMenuStartRecord, m_FunctionMenuStopRecord; // 功能菜单的菜单项
    private JMenuItem m_uploadSetting;
    private JMenuItem m_HelpMenuAbout; // 帮助菜单的菜单项

    // 窗体属性
    private int m_DeviceNum = 0;// 采集卡数量
    private int m_iDeviceIndex = -1;
    private boolean m_bIsStartStreaming = false;
    private boolean m_bHadSetVideoRenderer = false;
    private Pointer m_hCaptureDevice = null;
    private Pointer m_hRecordObject = null;
    private NotifyEventCallback m_lpCallback;

    private static HWND createHWNDByComponent(Component parent) {
        return new HWND(Native.getComponentPointer(parent));
    }

    private void CreatMainFrameMenu() {// 菜单栏的实现
        // 文件菜单的实现
        m_mb = new JMenuBar(); // 创建菜单棒
        m_FileMenu = new JMenu("文件");// 创建菜单
        m_FileMenuExit = new JMenuItem("退出");
        FileMenuHandler hFile = new FileMenuHandler(); // 创建监听器
        m_FileMenuExit.addActionListener(hFile);
        m_FileMenu.add(m_FileMenuExit);
        m_FileMenu.setFont(m_f); // 设置菜单中文体的字体
        // 设备菜单的实现
        m_DeviceMenu = new JMenu("设备");
        IntByReference ibrDeviceNum = new IntByReference(0);
        CSDKAPI.INSTANCE.AVerGetDeviceNum(ibrDeviceNum);
        m_DeviceNum = ibrDeviceNum.getValue();
        Pointer pDeviceName = new Memory(200);
        IntByReference ibrDeviceType = new IntByReference(0);
        String strDeviceName;
        int iDeviceType;
        m_DeviceMenuItemDevice = new JCheckBoxMenuItem[m_DeviceNum];
        DeviceMenuHandler hDevice = new DeviceMenuHandler(); // 创建监听器
        for (int i = 0; i < m_DeviceNum; i++) {
            pDeviceName.clear(200);
            CSDKAPI.INSTANCE.AVerGetDeviceName(i, pDeviceName);
            strDeviceName = new String(pDeviceName.getCharArray(0, 100)).trim();
            ibrDeviceType.setValue(0);
            CSDKAPI.INSTANCE.AVerGetDeviceType(i, ibrDeviceType);
            iDeviceType = ibrDeviceType.getValue();
            {
                m_DeviceMenuItemDevice[i] = new JCheckBoxMenuItem(strDeviceName);
                m_DeviceMenuItemDevice[i].addActionListener(hDevice);// 注册监听器
                m_DeviceMenu.add(m_DeviceMenuItemDevice[i]);
            }
        }
        m_DeviceMenu.setFont(m_f);


        // 功能菜单的实现
        m_FunctionMenu = new JMenu("功能"); // 创建菜单
        m_FunctionMenuStartStreaming = new JMenuItem("开启视频流");
        m_FunctionMenuStartStreaming.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                Event.CTRL_MASK));
        m_FunctionMenuStopStreaming = new JMenuItem("关闭视频流");
        m_FunctionMenuStopStreaming.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
                Event.CTRL_MASK));
        m_FunctionMenuDeviceSetting = new JMenuItem("视频设置");
        m_FunctionMenuCaptureImage = new JMenuItem("捕获图像");
        m_FunctionMenuStartRecord = new JMenuItem("开始录屏");
        m_FunctionMenuStartRecord.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                Event.CTRL_MASK));
        m_FunctionMenuStopRecord = new JMenuItem("结束录屏");
        m_FunctionMenuStopRecord.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                Event.CTRL_MASK));
        FunctionMenuHandler hfunction = new FunctionMenuHandler(); // 创建事件监听器
        m_FunctionMenuStartStreaming.addActionListener(hfunction); // 注册二级菜单项的监听器
        m_FunctionMenuStopStreaming.addActionListener(hfunction);
        m_FunctionMenuCaptureImage.addActionListener(hfunction);
        m_FunctionMenuDeviceSetting.addActionListener(hfunction);
        m_FunctionMenuStartRecord.addActionListener(hfunction);
        m_FunctionMenuStopRecord.addActionListener(hfunction);
        m_FunctionMenu.add(m_FunctionMenuStartStreaming);
        m_FunctionMenu.add(m_FunctionMenuStopStreaming);
        m_FunctionMenu.add(m_FunctionMenuDeviceSetting);
        m_FunctionMenu.addSeparator(); // 添加分隔线
        m_FunctionMenu.add(m_FunctionMenuCaptureImage);
        m_FunctionMenu.add(m_FunctionMenuStartRecord);
        m_FunctionMenu.add(m_FunctionMenuStopRecord);
        m_FunctionMenu.setFont(m_f);

        m_FunctionMenuStartStreaming.setEnabled(false);
        m_FunctionMenuStopStreaming.setEnabled(false);
        m_FunctionMenuDeviceSetting.setEnabled(false);
        m_FunctionMenuCaptureImage.setEnabled(false);
        m_FunctionMenuStartRecord.setEnabled(false);
        m_FunctionMenuStopRecord.setEnabled(false);

        m_UploadMenu = new JMenu("上传");
        m_uploadSetting = new JMenuItem("上传设置");
        m_uploadSetting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                Event.CTRL_MASK));
        UploadMenuHandler hUpload=new UploadMenuHandler();
        m_uploadSetting.addActionListener(hUpload);
        m_UploadMenu.add(m_uploadSetting);
        m_UploadMenu.setFont(m_f);
        m_uploadSetting.setEnabled(true);

        m_HelpMenu = new JMenu("帮助");
        m_HelpMenuAbout = new JMenuItem("关于");
        HelpMenuHandler hHelp=new HelpMenuHandler();
        m_HelpMenuAbout.addActionListener(hHelp);
        m_HelpMenu.add(m_HelpMenuAbout);
        m_HelpMenu.setFont(m_f);
        m_HelpMenu.setEnabled(true);

        // 将菜单全部添加菜单棒里
        m_mb.add(m_FileMenu);
        m_mb.add(m_DeviceMenu);
        m_mb.add(m_FunctionMenu);
        m_mb.add(m_UploadMenu);
        m_mb.add(m_HelpMenu);
        this.setJMenuBar(m_mb);
    }

    private void CreatMainFrameViewWindow() {
        m_PanelViewWindow = new Panel(); // 创建一个视频显示区
        PanelViewWindowHandler hPanel = new PanelViewWindowHandler();//创建事件监听器
        m_PanelViewWindow.addComponentListener(hPanel);
        m_PanelViewWindow.setBackground(new Color(0, 0, 0));
        this.add(m_PanelViewWindow);
    }

    public CMainFrame() {
        super("天启录屏");
        CMainFrameHandler hCMainFrame = new CMainFrameHandler();
        this.addWindowListener(hCMainFrame);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Ole32.INSTANCE.CoUninitialize();
            }
        });
        Ole32.INSTANCE.CoInitialize(null);
        //设置logo
        Image im = (new ImageIcon(this.getClass().getResource("/icon.jpg"))).getImage();
        setIconImage(im);
        setSize(600, 450);
        setLocation(0, 0);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("ERROR Reason:" + e);
        }
        CreatMainFrameMenu();
        CreatMainFrameViewWindow();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    protected void finalize() {
        if (m_hCaptureDevice != null) {
            DeleteCaptureDevice();
        }
    }

    private void DeleteCaptureDevice() {
        if (m_bIsStartStreaming) {
            CSDKAPI.INSTANCE.AVerStopStreaming(m_hCaptureDevice);
            m_bIsStartStreaming = false;
        }
        CSDKAPI.INSTANCE.AVerDeleteCaptureObject(m_hCaptureDevice);
        m_hCaptureDevice = null;
    }

    // 自定义类实现文件菜单项的事件处理
    private class FileMenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == m_FileMenuRestore) {
                // loadFile();
            } else {
                System.exit(0);
            }
        }
    }

    // 窗口关闭变化的事件处理
    private class CMainFrameHandler implements WindowListener {

        @Override
        public void windowActivated(WindowEvent arg0) {
            // TODO 自动生成的方法存根

        }

        @Override
        public void windowClosed(WindowEvent arg0) {
            // TODO 自动生成的方法存根

        }

        @Override
        public void windowClosing(WindowEvent arg0) {
            if (m_hCaptureDevice != null) {
                DeleteCaptureDevice();
            }
        }

        @Override
        public void windowDeactivated(WindowEvent arg0) {
            // TODO 自动生成的方法存根

        }

        @Override
        public void windowDeiconified(WindowEvent arg0) {
            // TODO 自动生成的方法存根

        }

        @Override
        public void windowIconified(WindowEvent arg0) {
            // TODO 自动生成的方法存根

        }

        @Override
        public void windowOpened(WindowEvent arg0) {
            // TODO 自动生成的方法存根

        }

    }


    // 控件大小变化的事件处理
    private class PanelViewWindowHandler implements ComponentListener {
        public void componentResized(ComponentEvent e) {
            CSDKAPI.RECT.ByValue rectClient = new CSDKAPI.RECT.ByValue();
            Dimension rv = new Dimension();
            m_PanelViewWindow.getSize(rv);
            rectClient.left = 0;
            rectClient.top = 0;
            rectClient.right = rv.width;
            rectClient.bottom = rv.height;
            CSDKAPI.INSTANCE.AVerSetVideoWindowPosition(m_hCaptureDevice, rectClient);
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            // TODO 自动生成的方法存根
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            // TODO 自动生成的方法存根
        }

        @Override
        public void componentShown(ComponentEvent e) {
            // TODO 自动生成的方法存根
        }
    }

    // 编辑菜单菜单项的事件处理
    private class DeviceMenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Ole32.INSTANCE.CoInitialize(null);
            if (((JCheckBoxMenuItem) e.getSource()).getState() == false) {
                DeleteCaptureDevice();
                m_iDeviceIndex = -1;
                Ole32.INSTANCE.CoUninitialize();
                return;
            }
            if (m_hCaptureDevice != null) {
                DeleteCaptureDevice();
                for (int i = 0; i < m_DeviceNum; i++) {
                    {
                        if (m_DeviceMenuItemDevice[i].getState() == true) {
                            m_DeviceMenuItemDevice[i].setState(false);
                        }
                    }
                }
                m_iDeviceIndex = -1;
                ((JCheckBoxMenuItem) e.getSource()).setState(true);
            }
            NativeLong nl = new NativeLong(0);
            HWND hwnd = createHWNDByComponent(m_PanelViewWindow);
            PointerByReference pbrCaptureObject = new PointerByReference();
            for (int i = 0; i < m_DeviceNum; i++) {
                if (e.getSource() == m_DeviceMenuItemDevice[i]) {
                    nl = CSDKAPI.INSTANCE.AVerCreateCaptureObjectEx(i, CSDKAPI.CAPTURETYPE.CAPTURETYPE_ALL, hwnd,
                            pbrCaptureObject);
                    m_iDeviceIndex = i;
                    break;
                }
            }
            m_hCaptureDevice = pbrCaptureObject.getValue();
            switch (nl.intValue()) {
                case CSDKAPI.ERRORCODE.CAP_EC_SUCCESS: {
                    m_FunctionMenuStartStreaming.setEnabled(true);
                    m_FunctionMenuStopStreaming.setEnabled(false);
                    m_FunctionMenuDeviceSetting.setEnabled(true);
                    m_FunctionMenuCaptureImage.setEnabled(false);
                    m_FunctionMenuStartRecord.setEnabled(false);
                    m_FunctionMenuStopRecord.setEnabled(false);
                }
                break;
                case CSDKAPI.ERRORCODE.CAP_EC_DEVICE_IN_USE:
                    JOptionPane.showMessageDialog(null, "The capture device has already been used.", "AVer Cap SDK Demo",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Can't initialize the capture device.", "AVer Cap SDK Demo",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
            Ole32.INSTANCE.CoUninitialize();
            return;
        }
    }

    // 格式菜单二级菜单的菜单项的事件处理
    private class FunctionMenuHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Ole32.INSTANCE.CoInitialize(null);
            if (e.getSource() == m_FunctionMenuStartStreaming) {
                OnFunctionStartstreaming();
                m_FunctionMenuStartStreaming.setEnabled(false);
                m_FunctionMenuStopStreaming.setEnabled(true);
                m_FunctionMenuDeviceSetting.setEnabled(false);
                m_FunctionMenuCaptureImage.setEnabled(true);
                m_FunctionMenuStartRecord.setEnabled(true);
                m_FunctionMenuStopRecord.setEnabled(false);
            }
            if (e.getSource() == m_FunctionMenuStopStreaming) {
                OnFunctionStopstreaming();
                m_FunctionMenuStartStreaming.setEnabled(true);
                m_FunctionMenuStopStreaming.setEnabled(false);
                m_FunctionMenuDeviceSetting.setEnabled(true);
                m_FunctionMenuCaptureImage.setEnabled(false);
                m_FunctionMenuStartRecord.setEnabled(false);
                m_FunctionMenuStopRecord.setEnabled(false);
            }
            if (e.getSource() == m_FunctionMenuDeviceSetting) {
                DlgDeviceSetting oDlgDeviceSetting = new DlgDeviceSetting(m_hCaptureDevice);
                oDlgDeviceSetting.setVisible(true);
            }
            if (e.getSource() == m_FunctionMenuCaptureImage) {
                CaptureImage();
            }
            if (e.getSource() == m_FunctionMenuStartRecord) {
                m_FunctionMenuStartRecord.setEnabled(false);
                m_FunctionMenuStopRecord.setEnabled(true);
                StarRecord();
            }
            if (e.getSource() == m_FunctionMenuStopRecord) {
                m_FunctionMenuStartRecord.setEnabled(true);
                m_FunctionMenuStopRecord.setEnabled(false);
                StopRecord();
            }

            Ole32.INSTANCE.CoUninitialize();
        }
    }

    private class UploadMenuHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == m_uploadSetting) {
                UploadSetting uploadSetting= new UploadSetting();
                uploadSetting.setVisible(true);
            } else if(e.getSource()=="以后写") {
                //todo
            }
        }
    }

    private class HelpMenuHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==m_HelpMenuAbout){
                //弹出天启相关信息
            }
        }
    }

    private void StarRecord() {

        javax.swing.filechooser.FileSystemView fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
        String strPath = fsv.getDefaultDirectory().toString();
        strPath = strPath + "\\RecordConfig.ini";
        System.out.println(strPath);
        Pointer strFileName;
        strFileName = new Memory(Native.WCHAR_SIZE * (strPath.length() + 1));
        strFileName.setWideString(0, strPath);
        PointerByReference pbrRecordObject = new PointerByReference();
        //参数分别为视频采集对象句柄，record对象句柄和配置文件路径
        if (CSDKAPI.INSTANCE.AVerStartRecordFile(m_hCaptureDevice, pbrRecordObject, strFileName).intValue() != CSDKAPI.ERRORCODE.CAP_EC_SUCCESS) {
            JOptionPane.showMessageDialog(null, "Can't start record.", "AVer Cap SDK Demo",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        m_hRecordObject = pbrRecordObject.getValue();
    }

    private void StopRecord() {
        CSDKAPI.INSTANCE.AVerStopRecordFile(m_hRecordObject);
        //格式化视频名称
        String pwd=(System.getProperty("user.dir"));
        File newMp4=new File(pwd+"\\Temp.mp4");
        String newName="";
        if(newMp4.exists()&&newMp4.isFile()){
            newName=pwd + File.separator + Config.patientName + "_" + Config.examType + "_" + System.currentTimeMillis()+".mp4";
            newMp4.renameTo(new File(newName));
            System.out.println(newName);
        }
        if(Config.isUpload){
            try {
                new FileUploadClient("192.168.1.217",8080).sendFile(newName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void CaptureImage() {
        CSDKAPI.CAPTURE_SINGLE_IMAGE_INFO.ByReference CaptureSingleImageInfo = new CSDKAPI.CAPTURE_SINGLE_IMAGE_INFO.ByReference();
        CaptureSingleImageInfo.iVersion = 1;
        CaptureSingleImageInfo.iImageType = CSDKAPI.IMAGESAVETYPE.IMAGETYPE_BMP;
        CaptureSingleImageInfo.iOverlayMix = 0;
        CaptureSingleImageInfo.iOverlayMix = 0;
        javax.swing.filechooser.FileSystemView fsv = javax.swing.filechooser.FileSystemView.getFileSystemView();
        String strPath = fsv.getDefaultDirectory().toString();
        strPath = strPath + "\\CaptureSDKImage.bmp";
        CaptureSingleImageInfo.strFileName = new Memory(Native.WCHAR_SIZE * (strPath.length() + 1));
        CaptureSingleImageInfo.strFileName.setWideString(0, strPath);
        CSDKAPI.INSTANCE.AVerCaptureSingleImage(m_hCaptureDevice, CaptureSingleImageInfo);
    }

    private void Startstreaming() {
        if (!m_bHadSetVideoRenderer) {
            CSDKAPI.INSTANCE.AVerSetVideoRenderer(m_hCaptureDevice, CSDKAPI.VIDEORENDERER.VIDEORENDERER_EVR);
        }
//        Pointer pMainFrame;
//        Pointer.setValue(0,this,this.getClass());
        CSDKAPI.INSTANCE.AVerSetEventCallback(m_hCaptureDevice, m_lpCallback, 1, null);

        CSDKAPI.RECT.ByValue rectClient = new CSDKAPI.RECT.ByValue();
        //RECT rectClient = new RECT();
        Dimension rv = new Dimension();
        m_PanelViewWindow.getSize(rv);
        rectClient.left = 0;
        rectClient.top = 0;
        rectClient.right = rv.width;
        System.out.println("width of stream is: " + rectClient.right);
        rectClient.bottom = rv.height;
        System.out.println("height of stream is: " + rectClient.bottom);
        CSDKAPI.INSTANCE.AVerSetVideoWindowPosition(m_hCaptureDevice, rectClient);
        if (CSDKAPI.INSTANCE.AVerStartStreaming(m_hCaptureDevice).intValue() != CSDKAPI.ERRORCODE.CAP_EC_SUCCESS) {
            JOptionPane.showMessageDialog(null, "Can't start streaming.", "AVer Cap SDK Demo",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        m_bIsStartStreaming = true;
    }

    private void OnFunctionStartstreaming() {
        Startstreaming();
    }

    private void Stopstreaming() {
        CSDKAPI.INSTANCE.AVerStopStreaming(m_hCaptureDevice);
        m_bIsStartStreaming = false;
    }

    private void OnFunctionStopstreaming() {
        Stopstreaming();
    }

    public class NotifyEventCallback implements CSDKAPI.NOTIFYEVENTCALLBACK {
        @Override
        public int invoke(int dwEventCode, Pointer lpEventData, Pointer lpUserData) {
            switch (dwEventCode) {
                case CSDKAPI.EVENT.EVENT_CHECKCOPP: {
                    int iErrorID = lpEventData.getInt(0);
                    String strErrorID = new String();
                    switch (iErrorID) {
                        case CSDKAPI.COPP_ERR.COPP_ERR_UNKNOWN:
                            strErrorID = "COPP_ERR_UNKNOWN";
                            break;
                        case CSDKAPI.COPP_ERR.COPP_ERR_NO_COPP_HW:
                            strErrorID = "COPP_ERR_NO_COPP_HW";
                            break;
                        case CSDKAPI.COPP_ERR.COPP_ERR_NO_MONITORS_CORRESPOND_TO_DISPLAY_DEVICE:
                            strErrorID = "COPP_ERR_NO_MONITORS_CORRESPOND_TO_DISPLAY_DEVICE";
                            break;
                        case CSDKAPI.COPP_ERR.COPP_ERR_CERTIFICATE_CHAIN_FAILED:
                            strErrorID = "COPP_ERR_CERTIFICATE_CHAIN_FAILED";
                            break;
                        case CSDKAPI.COPP_ERR.COPP_ERR_STATUS_LINK_LOST:
                            strErrorID = "COPP_ERR_STATUS_LINK_LOST";
                            break;
                        case CSDKAPI.COPP_ERR.COPP_ERR_NO_HDCP_PROTECTION_TYPE:
                            strErrorID = "COPP_ERR_NO_HDCP_PROTECTION_TYPE";
                            break;
                        case CSDKAPI.COPP_ERR.COPP_ERR_HDCP_REPEATER:
                            strErrorID = "COPP_ERR_HDCP_REPEATER";
                            break;
                        case CSDKAPI.COPP_ERR.COPP_ERR_HDCP_PROTECTED_CONTENT:
                            strErrorID = "COPP_ERR_HDCP_PROTECTED_CONTENT";
                            break;
                        case CSDKAPI.COPP_ERR.COPP_ERR_GET_CRL_FAILED:
                            strErrorID = "COPP_ERR_GET_CRL_FAILED";
                            break;
                    }
                    JOptionPane.showMessageDialog(null, strErrorID, "AVer Cap SDK Demo",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
                default:
                    return 0;
            }
            return 1;
        }
    }
}