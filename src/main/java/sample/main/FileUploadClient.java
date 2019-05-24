package sample.main;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * @author poorguy
 * @version 0.0.1
 * @E-mail 494939649@qq.com
 * @created 2019/4/19 16:19
 */
public class FileUploadClient extends Socket {
//    private static final String SERVER_IP = "127.0.0.1"; // 服务端IP
//    private static final int SERVER_PORT = 8001; // 服务端端口

    private Socket client;

    private FileInputStream fis;

    private DataOutputStream dos;

    public FileUploadClient(String ip,int port) throws Exception {
        super(ip, port);
        this.client = this;
        System.out.println("Cliect[port:" + client.getLocalPort() + "] 成功连接服务端");
    }

    public void sendFile(String absoultPath) throws Exception {
        try {
            File file = new File(absoultPath);
            if (file.exists()) {
                fis = new FileInputStream(file);
                dos = new DataOutputStream(client.getOutputStream());

                // 文件名和长度
                dos.write((file.getName()+"|").getBytes("utf-8"));
                dos.flush();
                dos.write((Config.patientUUID+"|").getBytes("utf-8"));
                dos.flush();
//                dos.writeUTF(file.getName());
//                dos.flush();
//                dos.writeLong(file.length());
//                dos.flush();

                // 开始传输文件
                System.out.println("======== 开始传输文件:"+absoultPath+" ========");
                byte[] bytes = new byte[1024000];
                int length = 0;
                long progress = 0;
                while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                    dos.write(bytes, 0, length);
                    dos.flush();
                    progress += length;
                    System.out.print("| " + (100 * progress / file.length()) + "% |");
                }
               System.out.println();
                System.out.println("======== 文件传输成功 ========");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                fis.close();
            if (dos != null)
                dos.close();
            client.close();
        }
    }
}
