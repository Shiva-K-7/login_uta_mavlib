package edu.uta.mavs.login_uta_mavlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ReservedBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_books);

        ListView lsitView1 = (ListView) findViewById(R.id.list_reserved_books);

        ArrayList<String> arrayList = new ArrayList<>();


        arrayList.add("Design Patterns");
        arrayList.add("Artificial Intelligence");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        lsitView1.setAdapter(arrayAdapter);

        ListView lsitView = (ListView) findViewById(R.id.list_checkedout_books);

        ArrayList<String> arrayList1 = new ArrayList<>();

        arrayList1.add("Software Engineering Due Date: 04/11/2020");
        arrayList1.add("Theory of Computation Due Date: 04/15/2020");


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList1);

        lsitView.setAdapter(arrayAdapter1);
    }
}
