package ua.ukraine.ukrroad.ukrroad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;

import ua.ukraine.ukrroad.ukrroad.database.table.Image;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;
import ua.ukraine.ukrroad.ukrroad.helpers.HelperFactory;

public class ListActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView listView;
    Issue issue;
    Image image;
    int idIssue;
    String pathImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        pathImage = getIntent().getStringExtra(getResources().getString(R.string.PATHIMAGE)).toString();
        String[] listItems = getResources().getStringArray(R.array.items);

        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        issue = new Issue();
        image = new Image();
        image.setImagePath(pathImage);
        issue.addImage(image);

        try {
            idIssue = HelperFactory.getHelper().getIssueDAO().create(issue);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(ListActivity.this, ImageActivity.class);
                intent.putExtra(getResources().getString(R.string.IDISUE), String.valueOf(idIssue));
                startActivity(intent);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;

        }
    }
}