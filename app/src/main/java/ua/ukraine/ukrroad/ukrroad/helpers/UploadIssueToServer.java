package ua.ukraine.ukrroad.ukrroad.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ua.ukraine.ukrroad.ukrroad.R;
import ua.ukraine.ukrroad.ukrroad.database.table.Image;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;

public abstract class UploadIssueToServer extends AsyncTask<Issue, Void, String> {
    List<Image> imageIssues;
    ArrayList<byte[]> dataImage;
    ByteArrayOutputStream bos;
    private Context context;
    String result;

    public UploadIssueToServer (Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(Issue[] params) {
        try {
            dataImage = new ArrayList<>();
            imageIssues = HelperFactory.getHelper().getImageDAO().getImagesByIssue(params[0]);

            for (Image imageIssue : imageIssues) {
                bos = new ByteArrayOutputStream();
                BitmapFactory.decodeFile(imageIssue.getImagePath()).compress(Bitmap.CompressFormat.JPEG, 75, bos);
                dataImage.add(bos.toByteArray());
            }

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(context.getResources().getString(R.string.URL));
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            ByteArrayBody bab = null;
            for (int count = 0; count < dataImage.size(); count++){
                bab = new ByteArrayBody(dataImage.get(count), "image" + count + ".jpg");
                reqEntity.addPart("photo", bab);
            }
            reqEntity.addPart("address", new StringBody(params[0].getAddress()));
            reqEntity.addPart("comment", new StringBody(params[0].getComment()));
            reqEntity.addPart("email", new StringBody(params[0].getEmail()));
            reqEntity.addPart("type", new StringBody(params[0].getDefect()));
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            result = "Files have been uploaded successfully!";
        } catch (Exception e) {
            result = "Exception errre";
            e.printStackTrace();
        }
        return String.valueOf(result);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        getResult(result);
    }

    public abstract void getResult(String result);
}
