package sample.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/19 14:57
 */
public class UploadSetting extends JDialog implements ActionListener {
    private Font m_f = new Font("MS Song", Font.PLAIN, 12);
    private TextField patientName;
    private JComboBox examType;
    private JComboBox isUpload;
    private JButton set;

    public UploadSetting() {
        Image im = (new ImageIcon(this.getClass().getResource("/icon.jpg"))).getImage();
        setIconImage(im);
        setLocation(200, 200);
        setSize(240, 180);

        Container container = getContentPane();
        this.setLayout(null);
        this.setResizable(false);

        Label label0 = new Label("patient");
        label0.setBounds(20, 10, 60, 25);
        container.add(label0);
        patientName = new TextField("");
        patientName.setBounds(90, 10, 80, 25);
        container.add(patientName);

        Label label1 = new Label("examtype");
        label1.setBounds(20, 40, 60, 25);
        container.add(label1);
        String[] eT = {"type 1","type 2"};
        examType = new JComboBox(eT);
        examType.setBounds(90, 40, 80, 25);
        container.add(examType);

        Label label2 = new Label("upload");
        label2.setBounds(20, 70, 60, 25);
        container.add(label2);
        String[] iU = {"是","否"};
        isUpload = new JComboBox(iU);
        isUpload.setBounds(90, 70, 80, 25);
        container.add(isUpload);

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
        if(patientName.getText()!=Config.patientName){
            Config.patientUUID = UUID.randomUUID().toString().replace("-","");
        }
        Config.patientName=patientName.getText();
        System.out.println(Config.patientName);

        int nExamType= examType.getSelectedIndex();
        if(nExamType==0)
            Config.examType = "examType 1";
        if(nExamType==1)
            Config.examType = "examType 2";
        System.out.println(Config.examType);

        int nIsUp = isUpload.getSelectedIndex();
        if (nIsUp == 0)
            Config.isUpload=true;
        else if (nIsUp == 1)
            Config.isUpload = false;
        System.out.println(Config.isUpload);
    }
}
