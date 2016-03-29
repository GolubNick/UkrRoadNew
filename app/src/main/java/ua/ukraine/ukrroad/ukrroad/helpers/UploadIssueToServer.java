package ua.ukraine.ukrroad.ukrroad.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

//import com.golub.nick.ukrroad.db.DbManager;
//import com.golub.nick.ukrroad.db.Model.ImageIssue;
//import com.golub.nick.ukrroad.db.Model.Issue;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.ByteArrayBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ua.ukraine.ukrroad.ukrroad.R;
import ua.ukraine.ukrroad.ukrroad.database.table.Image;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;

public abstract class UploadIssueToServer extends AsyncTask<Issue, Void, String> {
    List<Image> imageIssues;
    ArrayList<byte[]> dataImage;
    ByteArrayOutputStream bos;
    String responseStr = null;
    private Context context;

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
            HttpURLConnection connection = (HttpURLConnection) new URL(context.getResources().getString(R.string.URL)).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.connect();

//            HttpClient httpClient = new DefaultHttpClient();
//            HttpPost postRequest = new HttpPost(postReceiverUrl);
//            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//            ByteArrayBody bab = null;
//            for (int count = 0; count < dataImage.size(); count++){
//                bab = new ByteArrayBody(dataImage.get(count), "image" + count + ".jpg");
//                reqEntity.addPart("photo", bab);
//            }
//            reqEntity.addPart("address", new StringBody("марселичка"));
//            reqEntity.addPart("comment", new StringBody("комент"));
//            reqEntity.addPart("email", new StringBody("мейл"));
//            reqEntity.addPart("type", new StringBody("люк"));
//            postRequest.setEntity(reqEntity);
//            HttpResponse response = httpClient.execute(postRequest);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseStr;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        getResult(result);
    }

    public abstract void getResult(String result);
}
