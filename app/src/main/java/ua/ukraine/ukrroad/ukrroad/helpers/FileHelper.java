package ua.ukraine.ukrroad.ukrroad.helpers;

import android.net.Uri;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

public class FileHelper {
    private String ROOT_DIRECTORY = "SocProject";

    public ArrayList<String> getFiles(final File directory){
        ArrayList<String> uris = new ArrayList<>();
        if (directory.exists()){
            File[] files = directory.listFiles();
            for (File file : files){
                uris.add(file.getAbsolutePath());
            }
        }
        return uris;
    }

    public Uri generateFileUri(String nameDir) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return null;

        File path = new File(Environment.getExternalStorageDirectory(), nameDir);
        if (!path.exists()) {
            if (!path.mkdirs()) {
                return null;
            }
        }

        String timeStamp = String.valueOf(System.currentTimeMillis());
        File newFile = new File(path.getAbsolutePath() + File.separator + timeStamp + ".jpg");
        return Uri.fromFile(newFile);
    }

}
