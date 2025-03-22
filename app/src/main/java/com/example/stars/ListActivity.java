package com.example.stars;

import static android.content.ContentValues.TAG;
import androidx.appcompat.widget.SearchView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.MenuItemCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.stars.adapter.StarAdapter;
import com.example.stars.beans.Star;
import com.example.stars.service.StarService;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService  service = StarService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        stars = new ArrayList<>();

        if(service.findAll().isEmpty()){
            init();
        }

        recyclerView = findViewById(R.id.recycle_view);
        //insérer le code
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void init() {
        service.create(new Star("Angelina Jolie", "https://m.media-amazon.com/images/M/MV5BODg3MzYwMjE4N15BMl5BanBnXkFtZTcwMjU5NzAzNw@@._V1_FMjpg_UX1000_.jpg", 3.5f));
        service.create(new Star("Said Naciri", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbcts0N0TwbXZOoo8o6Y1e0luQYSSoQjLLXA&s ", 3.0f));
        service.create(new Star("Michelle Rodriguez", "https://th.bing.com/th/id/OIP.qqlJfZNTCZbz03f98wBc6AHaH3?rs=1&pid=ImgDetMain", 5.0f));
        service.create(new Star("Aziz Akhannouch", "https://www.famousbirthdays.com/faces/akhannouch-aziz-image.jpg", 4.0f));
        service.create(new Star("Idris Elba", "https://th.bing.com/th/id/R.43e027767423c2e515769a41cec70355?rik=xUPLVpt8FcL7JQ&pid=ImgRaw&r=0", 5.0f));
        //  service.create(new Star("Damson Idris", "https://www.cheatsheet.com/wp-content/uploads/2021/04/damson-idris.jpg", 3.5f));
        //service.create(new Star("Leonardo Dicaprio", "https://www.cheatsheet.com/wp-content/uploads/2024/04/Leonardo-DiCaprio-Denzel-Washington.jpg", 3.5f));
        // service.create(new Star("Denzel Washington", "https://www.cheatsheet.com/wp-content/uploads/2024/11/denzel-washington-actors.webp", 4.5f));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("ListActivity", "onCreateOptionsMenu started");

        getMenuInflater().inflate(R.menu.menu, menu);

        Log.d("ListActivity", "Menu inflated successfully");

        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        if (menuItem == null) {
            Log.e("ListActivity", "menuItem is null! Check menu.xml");
            return true; // Prevent crash
        }
        Log.d("ListActivity", "MenuItem found"+menuItem);

        // ✅ Correct cast: Use androidx.appcompat.widget.SearchView

        SearchView searchView = (SearchView) menuItem.getActionView();
        if (searchView == null) {
            Log.e("ListActivity", "SearchView is null! Cannot set listener.");
            return true; // Prevent crash
        }
        Log.d("ListActivity", "SearchView initialized"+searchView);

        // ✅ Only set listener if searchView is not null

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SearchView", "Search submitted: " + query);
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SearchView", "Search text changed: " + newText);

                filterResults(newText);
                return true;
            }
        });

        Log.d("ListActivity", "SearchView listener set successfully");
        return true;
    }
    private void performSearch(String query) {
        List<Star> searchResults = service.performSearch(query);
        starAdapter.updateList(searchResults);
    }

    private void filterResults(String query) {
        List<Star> filteredResults = service.performSearch(query);
        starAdapter.updateList(filteredResults);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.partger) {
            // Share text
            String shareText = "Check out these stars!";
            String mimeType = "text/plain";

            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Share via")
                    .setText(shareText)
                    .startChooser();

            return true; // Indicate that the event was handled
        }

        return super.onOptionsItemSelected(item);
    }





}