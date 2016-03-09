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
    ArrayList<String> list;
    Issue issue;
    ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        idIssue = getIntent().getStringExtra(getResources().getString(R.string.IDISUE));

        list = new ArrayList<>();
        gridView = (GridView)findViewById(R.id.gridViewImages);
        try {
            issue = HelperFactory.getHelper().getIssueDAO().getIssueById(Integer.parseInt(idIssue));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Image> listImageIssues = (ArrayList)issue.getImages();
        for (Image listImageIssue : listImageIssues)
            list.add(listImageIssue.getImagePath());

        imageAdapter = new ImageAdapter(this, list);
        gridView.setAdapter(imageAdapter);
    }
}
