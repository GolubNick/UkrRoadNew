package ua.ukraine.ukrroad.ukrroad.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import ua.ukraine.ukrroad.ukrroad.R;

public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> listFiles;
    LayoutInflater inflater;
    public ImageAdapter(Context context, ArrayList<String> listFiles){
        this.context = context;
        this.listFiles = listFiles;
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    public void updateContent (String updates) {
        listFiles.add(updates);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listFiles.size();
    }

    @Override
    public Object getItem(int position) {
        return listFiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.images, parent, false);
        }
        ImageView imageView = (ImageView)view.findViewById(R.id.image);
        Bitmap imageBitmap = decodeFile(listFiles.get(position));

        imageView.setImageBitmap(imageBitmap);
        return view;
    }
    public Bitmap decodeFile(String uriFile) throws OutOfMemoryError {
        String filePath = uriFile;
        BitmapFactory.Options bmOptions;
        Bitmap imageBitmap;
        try {
            imageBitmap = BitmapFactory.decodeFile(filePath);
        } catch (OutOfMemoryError e) {
            bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 4;
            bmOptions.inPurgeable = true;
            imageBitmap = BitmapFactory.decodeFile(filePath, bmOptions);
        }
        return imageBitmap;
    }
}