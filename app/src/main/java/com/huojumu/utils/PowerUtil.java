package com.huojumu.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * @author : Jie
 * Date: 2018/11/14
 * Description:
 */
public class PowerUtil {

    /**
     * 关机
     */
    public static void shutdown() {
        try {
            createSuProcess("reboot -p").waitFor(); //关机命令
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static Process createSuProcess() throws IOException {
        File rootUser = new File("/system/xbin/ru");
        if (rootUser.exists()) {
            return Runtime.getRuntime().exec(rootUser.getAbsolutePath());
        } else {
            return Runtime.getRuntime().exec("su");
        }
    }

    private static Process createSuProcess(String cmd) throws IOException {

        DataOutputStream os = null;
        Process process = createSuProcess();

        try {
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit $?\n");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }

        return process;
    }

}
