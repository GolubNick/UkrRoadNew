package ua.ukraine.ukrroad.ukrroad.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
    private Context context;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    String address = "mars";
    String email = "email@em.com";
    String type = "luk";
    String comment = "hello";
    int maxBufferSize = 1 * 1024 * 1024;
    byte[] buffer;
    int serverResponseCode = 0;

    int bytesRead, bytesAvailable, bufferSize;


    public UploadIssueToServer (Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(Issue[] params) {
        try {
            dataImage = new ArrayList<>();
            imageIssues = HelperFactory.getHelper().getImageDAO().getImagesByIssue(params[0]);

            HttpURLConnection connection = (HttpURLConnection) new URL(context.getResources().getString(R.string.URL)).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control", "no-cache");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Encoding", "");
            //conn.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("ENCTYPE", "multipart/form-data");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            connection.setRequestProperty("uploaded_file", "profile_picture");
            connection.setRequestProperty("address", address);
            connection.setRequestProperty("comment", comment);
            connection.setRequestProperty("email", email);
            connection.setRequestProperty("type", type);
//todo
//            connection.connect();

            dos = new DataOutputStream(connection.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"email\"" + lineEnd + lineEnd
                    + email + lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"address\"" + lineEnd + lineEnd
                    + address + lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"comment\"" + lineEnd + lineEnd
                    + comment + lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"type\"" + lineEnd + lineEnd
                    + type + lineEnd);

            for (Image imageIssue : imageIssues) {
                bos = new ByteArrayOutputStream();
                BitmapFactory.decodeFile(imageIssue.getImagePath()).compress(Bitmap.CompressFormat.JPEG, 75, bos);
                dataImage.add(bos.toByteArray());
            }

            // create a buffer of  maximum size
            ByteArrayInputStream fileInputStream = new ByteArrayInputStream(dataImage.get(0));//todo
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = connection.getResponseCode();

            String serverResponseMessage = connection.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : "
                    + serverResponseMessage + ": " + serverResponseCode);

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();
            connection.disconnect();

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
//            String sResponse;
//            StringBuilder s = new StringBuilder();
//
//            while ((sResponse = reader.readLine()) != null) {
//                s = s.append(sResponse);
//            }
//            System.out.println("Response: " + s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(serverResponseCode);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        getResult(result);
    }

    public abstract void getResult(String result);
}
