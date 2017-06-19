package rbt.shodowrabbitshop.base;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/11 0011.
 */
public class DefaultUtils {
    /**
     * 写文件
     *
     * @param fileName
     * @param content
     * @param codeType
     * @return isException
     */
    public static boolean writeFileData(String fileName, String content, String codeType) {
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                try {
                    file.getParentFile().mkdirs();
                }catch (Exception e) {
                    Log.i("writing error1", e.toString());
                }
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.i("writing error2", e.toString());
                }
            }
            FileOutputStream fot = new FileOutputStream(fileName);
            byte[] bytes = content.getBytes(codeType);
            fot.write(bytes);
            fot.close();
        } catch (Exception e) {
            Log.i("writing error3", e.toString());
            return true;
        }
        return false;
    }
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
        }
    }
}
