package ua.ukraine.ukrroad.ukrroad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import ua.ukraine.ukrroad.ukrroad.Internet.Connect;
import ua.ukraine.ukrroad.ukrroad.Internet.OnInternetListener;
import ua.ukraine.ukrroad.ukrroad.database.table.Image;
import ua.ukraine.ukrroad.ukrroad.database.table.Issue;
import ua.ukraine.ukrroad.ukrroad.dialogfragment.CommentFragment;
import ua.ukraine.ukrroad.ukrroad.dialogfragment.EMailFragment;
import ua.ukraine.ukrroad.ukrroad.dialogfragment.ProblemFragment;
import ua.ukraine.ukrroad.ukrroad.helpers.HelperFactory;
import ua.ukraine.ukrroad.ukrroad.maps.MapsActivity;

public class ListActivity extends Activity implements AdapterView.OnItemClickListener, ProblemFragment.ProblemTransmitter, CommentFragment.CommentTransmitter, EMailFragment.EMailTransmitter {
    ListView listView;
    Issue issue;
    Image image;
    int idIssue;
    String pathImage;
    ProblemFragment problemFragment;

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

        try {
            issue = new Issue();
            HelperFactory.getHelper().getIssueDAO().create(issue);
            image = new Image();
            image.setImagePath(pathImage);
            image.setIssue(issue);
            HelperFactory.getHelper().getImageDAO().create(image);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idIssue = issue.getId();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.faSsendIssueButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connect contect = new Connect(getApplicationContext());
                contect.addListener(new OnInternetListener() {
                    @Override
                    public void onDisconnect() {
//                        Toast.makeText(ListActivity.this, "Internet Disconect", Toast.LENGTH_SHORT).show();
                        Snackbar.make(findViewById(R.id.listViewLayout), "Internet Disconect", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }

                    @Override
                    public void onConnect() {
                //todo
                    }
                });
            }
        });

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
                intent = new Intent(ListActivity.this, MapsActivity.class);
                intent.putExtra(getResources().getString(R.string.IDISUE), String.valueOf(idIssue));
                startActivity(intent);
                break;
            case 2:
                problemFragment.show(getFragmentManager(), getResources().getString(R.string.TAG));
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    @Override
    public void sendProblemName(String defect) {
        issue.setDefect(defect);
        try {
            HelperFactory.getHelper().getIssueDAO().updateIssue(issue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendComments(String comments) {
        issue.setComment(comments);
        try {
            HelperFactory.getHelper().getIssueDAO().updateIssue(issue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendEmail(String email) {
        issue.setEmail(email);
        try {
            HelperFactory.getHelper().getIssueDAO().updateIssue(issue);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}