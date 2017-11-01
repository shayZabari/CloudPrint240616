package com.example.shay_z.ht_fragmenttest01062016;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by shay_z on 01/06/2016.
 */
public class MyBaseAdapter extends BaseAdapter {
ArrayList<File> files;
    ArrayList<Integer> checkedList =new ArrayList<>();
    Context context;

    public MyBaseAdapter (Context context, ArrayList<File> files) {
        this.context = context;
        this.files = files;

    }

    public void toggleSelect(Integer position) { // very confuzing- (int index),(Integer object)
        if (checkedList.contains(position)) {
            checkedList.remove(position); // if Integer» remove object , if int»remove index !!!
            Log.d("shay", "checklist.remove = " + position);
        } else {
            checkedList.add(position);
            Log.d("shay", "checklist.add = " + position);
        }
        for (int i = 0; i < checkedList.size(); i++) {
            Log.d("shay", "checkedList.size-" + i + " is= " + checkedList.get(i));
        }
    }
    public MyBaseAdapter() {
    }

    @Override
    public int getCount() {
        return files.size();
    }

    @Override
    public Object getItem(int i) {
        return files.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Holder holder;
        View raw = null;
        TextView tv;
        if (view == null) {
            holder = new Holder();
            raw = LayoutInflater.from(context).inflate(R.layout.row_file, viewGroup, false);
            holder.tvFileName = (TextView) raw.findViewById(R.id.tvFileName);
            holder.imgFolder = (ImageView) raw.findViewById(R.id.imgFolder);
            holder.imgExtentionFile = (ImageView) raw.findViewById(R.id.imgExtention);
            holder.imgBlueCheck = (ImageView) raw.findViewById(R.id.blueCheck);
            raw.setTag(holder);

        } else {
            raw = view;
            holder = (Holder) raw.getTag();
        }
        final View finalRaw = raw;
        raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               finalRaw.setBackgroundColor(Color.parseColor("#42a5f5")); // blue background line
                if (checkedList.contains(i)) {
                    toggleSelect(i);

                    holder.imgBlueCheck.setVisibility(View.INVISIBLE);
                } else {
                    toggleSelect(i);
                    holder.imgBlueCheck.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "files="+checkedList.size(), Toast.LENGTH_SHORT).show();

                }
                Log.d("shay","position is =" + i);


            }
        });
        if (checkedList.contains(i)) {
            holder.imgBlueCheck.setVisibility(View.VISIBLE);
        } else {
            holder.imgBlueCheck.setVisibility(View.INVISIBLE);
        }
        holder.tvFileName.setText(files.get(i).getName());
        String nameOfTheFile = files.get(i).getName().toString();
        String pathOfTheFile = files.get(i).getParentFile().getName();

        if (nameOfTheFile.endsWith("pdf")) {
            holder.imgExtentionFile.setImageResource(R.drawable.pdf);
        } else if (nameOfTheFile.endsWith("xlsx")) {
            holder.imgExtentionFile.setImageResource(R.drawable.excel);
        } else if (nameOfTheFile.endsWith("docx")) {
            holder.imgExtentionFile.setImageResource(R.drawable.word);
        } else {
            holder.imgExtentionFile.setImageResource(R.drawable.folder);
        }

        if (pathOfTheFile.contentEquals("scratch")) {
            holder.imgFolder.setImageResource(R.drawable.dropbox);
        } else {
            holder.imgFolder.setImageResource(R.drawable.documents);
        }





        return raw;
    }


}
