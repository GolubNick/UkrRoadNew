package ua.ukraine.ukrroad.ukrroad.dialogfragment;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ua.ukraine.ukrroad.ukrroad.ListActivity;
import ua.ukraine.ukrroad.ukrroad.R;
import ua.ukraine.ukrroad.ukrroad.helpers.FileHelper;

public class ChooseAction extends DialogFragment implements AdapterView.OnItemClickListener {
    FileHelper fileHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getResources().getString(R.string.chooseAction));
        View view = inflater.inflate(R.layout.fragment_choose_action, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listViewFragmentDialog);
        fileHelper = new FileHelper();
        String[] items = getResources().getStringArray(R.array.actions);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        switch (position) {
            case 0:
                fileHelper.setUri(getActivity());
                intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileHelper.mUri);
                getActivity().startActivityForResult(intent,
                        getResources().getInteger(R.integer.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE));
                break;
            case 1:
                intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(intent, getResources().getInteger(R.integer.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) && resultCode == getResources().getInteger(R.integer.RESULT_OK)) {
            Intent intent = new Intent(getActivity(), ListActivity.class);
            if (data != null)
                intent.putExtra(getResources().getString(R.string.PATHIMAGE), getRealPathFromURI(data.getData()));
            else
                intent.putExtra(getResources().getString(R.string.PATHIMAGE), fileHelper.mUri.getPath());
            startActivity(intent);
        }
    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}