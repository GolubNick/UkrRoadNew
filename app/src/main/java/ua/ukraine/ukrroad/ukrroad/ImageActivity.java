package ua.ukraine.ukrroad.ukrroad;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import java.sql.SQLException;
import java.util.ArrayList;

import ua.ukraine.ukrroad.ukrroad.adapter.ImageAdapter;
import ua.ukraine.ukrroad.ukrroad.database.table.Image;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;
import ua.ukraine.ukrroad.ukrroad.helpers.HelperFactory;

public class ImageActivity extends Activity {
    String idIssue;
    GridView gridView;
    Issue issue;
    ImageAdapter imageAdapter;

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
        ArrayList<Image> listImageIssues = new ArrayList<Image>(issue.getImages());

        imageAdapter = new ImageAdapter(this, listImageIssues);
        gridView.setAdapter(imageAdapter);
    }
}
