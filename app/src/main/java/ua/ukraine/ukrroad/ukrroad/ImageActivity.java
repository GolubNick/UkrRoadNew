package ua.ukraine.ukrroad.ukrroad;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.GridView;

import java.sql.SQLException;
import java.util.ArrayList;

import ua.ukraine.ukrroad.ukrroad.adapter.ImageAdapter;
import ua.ukraine.ukrroad.ukrroad.database.table.Image;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;
import ua.ukraine.ukrroad.ukrroad.dialogfragment.ChooseAction;
import ua.ukraine.ukrroad.ukrroad.helpers.FileHelper;
import ua.ukraine.ukrroad.ukrroad.helpers.HelperFactory;

public class ImageActivity extends Activity {
    String idIssue;
    GridView gridView;
    Issue issue;
    ImageAdapter imageAdapter;
    ChooseAction chooseAction;
    FileHelper fileHelper;
    Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        idIssue = getIntent().getStringExtra(getResources().getString(R.string.IDISUE));

        gridView = (GridView)findViewById(R.id.gridViewImages);
        try {
            issue = HelperFactory.getHelper().getIssueDAO().getIssueById(Integer.parseInt(idIssue));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Image> listImageIssues = new ArrayList<>(issue.getImages());
        chooseAction = new ChooseAction();
        imageAdapter = new ImageAdapter(this, listImageIssues);
        gridView.setAdapter(imageAdapter);

        fileHelper = new FileHelper();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddImage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseAction.show(getFragmentManager(), getResources().getString(R.string.TAG));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)) {
            if (resultCode == Activity.RESULT_OK) {
                String imagePath = data != null ? getRealPathFromURI(data.getData()) : fileHelper.mUri.getPath();
                Image image = new Image();
                image.setImagePath(imagePath);
                image.setIssue(issue);
                try {
                    HelperFactory.getHelper().getImageDAO().create(image);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                imageAdapter.updateContent(image);
            }
        }
        chooseAction.dismiss();
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
