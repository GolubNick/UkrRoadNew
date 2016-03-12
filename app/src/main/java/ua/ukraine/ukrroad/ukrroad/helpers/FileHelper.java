package ua.ukraine.ukrroad.ukrroad.helpers;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
import java.util.ArrayList;

import ua.ukraine.ukrroad.ukrroad.R;

public class FileHelper {
    private String ROOT_DIRECTORY = "SocProject";
    public static Uri mUri;

    public FileHelper(){}

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

    public void setUri(Context context){
        mUri = generateFileUri(context, context.getString(R.string.NAME_DIRECTORY));
    }

    public Uri generateFileUri(Context context, String nameDir) {
        File path;
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            path = new File(context.getFilesDir().getParent(), nameDir);
        else
            path = new File(Environment.getExternalStorageDirectory(), nameDir);
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
