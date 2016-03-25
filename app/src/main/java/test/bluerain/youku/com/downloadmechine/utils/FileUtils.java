package test.bluerain.youku.com.downloadmechine.utils;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Project: DownloadMechine.
 * Data: 2016/3/25.
 * Created by 8luerain.
 * Contact:<a href="mailto:8luerain@gmail.com">Contact_me_now</a>
 */
public class FileUtils {

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
}
