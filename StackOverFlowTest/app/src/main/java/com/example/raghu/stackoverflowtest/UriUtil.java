package com.example.raghu.stackoverflowtest;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by raghu on 24/4/17.
 */

public class UriUtil {

    public static File createFileForPic() throws IOException {
        String fileName = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(new Date()) + ".jpg";
        File storageDic = MyClass.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(storageDic, fileName);
    }

    public static File createTmpFileForPic() throws IOException {
        String fileName = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault()).format(new Date());
        File storageDic = MyClass.getInstance().getExternalFilesDir("");
        return File.createTempFile(fileName, ".jpg", storageDic);
    }

    public static Uri fromFile(@NonNull Context context, @NonNull File file) {
        if (context == null || file == null) {
            throw new RuntimeException("context or file can't be null");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, "com.example.raghu.stackoverflowtest.fileProvider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    //content://com.example.raghu.stackoverflowtest.fileProvider/external_files/Android/data/com.example.raghu.stackoverflowtest/files/Pictures/JPEG_20170424_1557166091150846937.jpg
}