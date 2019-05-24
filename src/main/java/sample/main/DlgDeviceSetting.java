package sample.main;

import com.sun.jna.Pointer;
import sample.SDKAPI.CSDKAPI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DlgDeviceSetting extends JDialog implements ActionListener {
    private Font m_f = new Font("MS Song", Font.PLAIN, 12);
    private JComboBox videosource;
    private JComboBox resoltion;
    private JComboBox framerate;
    private JButton set;
    private Pointer m_hCaptureDevice = null;

    public DlgDeviceSetting(Pointer hCaptureDevice) {
        Image im = (new ImageIcon(this.getClass().getResource("/icon.jpg"))).getImage();
        setIconImage(im);
        setSize(400, 300);
        setLocation(550, 300);

        m_hCaptureDevice = hCaptureDevice;

        setSize(240, 180);
        Container container = getContentPane();
        this.setLayout(null);
        this.setResizable(false);


        Label label0 = new Label("datasource");
        label0.setBounds(20, 10, 60, 25);
        container.add(label0);
        String[] src = {"Compsite", "S-Video", "Component", "HDMI", "VGA", "SDI", "DVI"};
        videosource = new JComboBox(src);
        videosource.setBounds(90, 10, 80, 25);
        container.add(videosource);

        Label label1 = new Label("Resolution");
        label1.setBounds(20, 40, 60, 25);
        container.add(label1);
        String[] res = {"640x480", "720x480", "720x576", "1280x720", "1920x1080", "3840x2160"};
        resoltion = new JComboBox(res);
        resoltion.setBounds(90, 40, 80, 25);
        container.add(resoltion);

        Label label2 = new Label("framerate");
        label2.setBounds(20, 70, 60, 25);
        container.add(label2);
        String[] frm = {"2500", "3000", "5000", "6000"};
        framerate = new JComboBox(frm);
        framerate.setBounds(90, 70, 80, 25);
        container.add(framerate);

        set = new JButton("确定");
        set.setBounds(90, 110, 60, 25);
        set.addActionListener(this);
        container.add(set);
    }

    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();
        if (label.equals("确定")) {
            SetParam();
            dispose();
        }
    }

    private void SetParam() {

        int nSource = videosource.getSelectedIndex();
        CSDKAPI.INSTANCE.AVerSetVideoSource(m_hCaptureDevice, nSource);

        int nResolution = resoltion.getSelectedIndex();
        if (nResolution == 0)
            nResolution = CSDKAPI.VIDEORESOLUTION.VIDEORESOLUTION_640X480;
        else if (nResolution == 1)
            nResolution = CSDKAPI.VIDEORESOLUTION.VIDEORESOLUTION_720X480;
        else if (nResolution == 2)
            nResolution = CSDKAPI.VIDEORESOLUTION.VIDEORESOLUTION_720X576;
        else if (nResolution == 3)
            nResolution = CSDKAPI.VIDEORESOLUTION.VIDEORESOLUTION_1280X720;
        else if (nResolution == 4)
            nResolution = CSDKAPI.VIDEORESOLUTION.VIDEORESOLUTION_1920X1080;
        else if (nResolution == 5)
            nResolution = CSDKAPI.VIDEORESOLUTION.VIDEORESOLUTION_3840X2160;
        CSDKAPI.VIDEO_RESOLUTION.ByReference tempResolution = new CSDKAPI.VIDEO_RESOLUTION.ByReference();
        tempResolution.iVersion = 1;
        tempResolution.iVideoResolution = nResolution;

        CSDKAPI.INSTANCE.AVerSetVideoResolutionEx(m_hCaptureDevice, tempResolution);

        int nFramerate = framerate.getSelectedIndex();
        if (nFramerate == 0)
            nFramerate = 2500;
        else if (nFramerate == 1)
            nFramerate = 3000;
        else if (nFramerate == 2)
            nFramerate = 5000;
        else if (nFramerate == 3)
            nFramerate = 6000;

        CSDKAPI.INSTANCE.AVerSetVideoInputFrameRate(m_hCaptureDevice, nFramerate);
    }
}

