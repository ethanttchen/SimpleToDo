package com.example.simpletodo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    List<String> items;
    Button btndAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btndAdd=findViewById(R.id.btnAdd);
        etItem=findViewById(R.id.etItem);
        rvItems=findViewById(R.id.rvItems);
        loadItems();
        ItemsAdapter.OnLongClicklistener onLongClickListener= position -> {
            items.remove(position);
            itemsAdapter.notifyItemRemoved(position);
            Toast.makeText(getApplicationContext(),"Item was removed",Toast.LENGTH_SHORT).show();
            saveItems();

        };

        itemsAdapter=new ItemsAdapter(items,onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btndAdd.setOnClickListener(v -> {
            String todoItem =etItem.getText().toString();
            items.add(todoItem);
            itemsAdapter.notifyItemInserted(items.size()-1);
            etItem.setText("");
            Toast.makeText(getApplicationContext(),"Item was added",Toast.LENGTH_SHORT).show();
            saveItems();
        });

    }
    private File getDataFile()
    {
        return new File(getFilesDir(),"data.txt");
    }

    private void loadItems()
    {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(),Charset.defaultCharset()));
        }
        catch (IOException e){
            Log.e("MainActivity","Error reading Items",e);
            items=new ArrayList<>();
        }
    }

    private void saveItems()
    {
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing Items",e);
        }

    }
}

