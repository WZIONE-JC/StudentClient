package com.example.studentclient;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyClass extends Activity {
    private ListView listv;

    //private int[] pic={R.drawable.ic_1,R.drawable.ic_2,R.drawable.ic_3};
    private	String[] classname={"移动应用开发","操作系统"};
    private  String[] person={"张迪老师","张迪老师"};
    private  String[] date={"2019.9.1","2019.9.1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        listv = (ListView) findViewById(R.id.listView1);

        List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
        for(int i=0;i<classname.length;i++){
            Map<String,Object> listItem = new HashMap<String,Object>();
            //listItem.put("pic", pic[i]);
            listItem.put("classname", classname[i]);
            listItem.put("person", person[i]);
            listItem.put("date", date[i]);
            listItems.add(listItem);
        }
        SimpleAdapter simleAdapter = new SimpleAdapter(MyClass.this, listItems,
                R.layout.course_layout	,new String[]{"classname","person","date"},
                new int[]{R.id.classname, R.id.person, R.id.date});

        listv.setAdapter(simleAdapter);

    }
}