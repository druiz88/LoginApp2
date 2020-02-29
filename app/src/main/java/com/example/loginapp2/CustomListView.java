package com.example.loginapp2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class CustomListView extends ArrayAdapter<String>{

    private String[] id;
    private String[] name;
    private String[] lname;
    private String[] email;
    private Activity context;

    public CustomListView(Activity context,String[] id,String[] name,String[] lname,String[] email) {
        super(context, R.layout.activity_dashboard,name);
        this.context=context;
        this.id=id;
        this.name=name;
        this.lname=lname;
        this.email=email;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View r = convertView;
        ViewHolder viewHolder = null;
        if(r == null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.list_adapter,null,true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)r.getTag();

        }

        viewHolder.tvw1.setText(id[position]);
        viewHolder.tvw2.setText(name[position]);
        viewHolder.tvw3.setText(lname[position]);
        viewHolder.tvw4.setText(email[position]);

        return r;
    }

    class ViewHolder{

        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        TextView tvw4;

        ViewHolder(View v){
            tvw1=v.findViewById(R.id.textID);
            tvw2=v.findViewById(R.id.textFirstName);
            tvw3=v.findViewById(R.id.textLastName);
            tvw4=v.findViewById(R.id.textEmail);
        }
    }
}
