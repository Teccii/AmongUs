package tecci.amogus.util;

import java.io.*;

public final class FileUtil {
    public static void copy(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }

            String[] files = src.list();

            if (files == null) {
                return;
            }

            for (String file : files) {
                File newSrc = new File(src, file);
                File newDest = new File(dest, file);

                copy(newSrc, newDest);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        }
    }

    public static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            if (files == null) {
                return;
            }

            for (File f : files) {
                delete(f);
            }
        }

        file.delete();
    }
}
