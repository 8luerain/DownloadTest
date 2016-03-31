package test.bluerain.youku.com.downloadmechine.utils;

import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Project: DownloadMechine.
 * Data: 2016/3/25.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class FileUtils {


    public static File getFileFromPath(String path) {
        File file = new File(path);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    public static boolean removeFileFromPath(String path) {
        Log.d("TAG", "remove file path is ......." + path);
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        return file.delete();
    }

    public static boolean renameFile(String orgFilePath, String newFilePath) {
        File file = new File(orgFilePath);
        if (!file.exists()) {
            return false;
        }
        file.renameTo(new File(newFilePath));
        return true;
    }

    public static void closeQuilty(Closeable closeable) {

        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public static boolean saveRandomAccessFile(RandomAccessFile file, int startLength, byte[] dataByte) {

        try {
            if (startLength < 0 || startLength > file.length()) {
                throw new RuntimeException("跳过的字节大小无效！");
            }
            file.seek(startLength);
            file.write(dataByte);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 计算文件的下载速度
     *
     * @param lastTime
     * @param endTime
     * @param downloadSize
     * @return 下载速度，单位暂定KB
     */
    public static int caculateDownloadVelocity(long lastTime, long endTime, int downloadSize) {

        long during = (endTime - lastTime);
        long time = during == 0 ? 1 : during;
        long velocity = (long) (downloadSize / (time / 1.0));

        return (int) velocity;
    }

    /**
     * 计算文件的下载进度
     *
     * @param downloadLength
     * @param fileTotalLength
     * @return 下载百分比
     */
    public static int calculateDownloadProgress(int downloadLength, int fileTotalLength) {

        return (int) ((downloadLength * 1.0 / fileTotalLength) * 100);
    }
}
