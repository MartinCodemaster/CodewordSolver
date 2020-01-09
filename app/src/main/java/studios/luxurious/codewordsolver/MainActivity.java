package studios.luxurious.codewordsolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView list;
    EditText edtWord;
    ArrayList<String> filteredList = new ArrayList<>();
    BufferedReader reader;

    ArrayAdapter adapter;

    CardView resultsnumber;
    TextView results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Locate the ListView in listview_main.xml
        list = findViewById(R.id.listview);
        resultsnumber = findViewById(R.id.resultsnumber);
        results = findViewById(R.id.results);


        adapter = new ArrayAdapter<>(this, R.layout.list_view, R.id.textView, filteredList);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        edtWord = findViewById(R.id.edtWord);


    }


    public void Search(View view) {

        String query = edtWord.getText().toString().trim();


        filteredList.clear();


        try {
            final InputStream file = getAssets().open("words.txt");
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();

            while (line != null) {

                if (line.length() == query.length()) {


                    boolean addWord = true;

                    for (int i = 0; i < query.length(); i++) {

                        if (query.charAt(i) != '.') {

                            if (query.charAt(i) != line.charAt(i)) {
                                addWord = false;
                            }
                        }

                    }

                    if (addWord) {
                        filteredList.add(line);

                    }
                }

                line = reader.readLine();
            }

            if (filteredList.size() == 0) {
                resultsnumber.setVisibility(View.VISIBLE);
                results.setText(getResources().getString(R.string.no_words_found));
            } else {

                resultsnumber.setVisibility(View.VISIBLE);
                results.setText(filteredList.size() + " words found");
            }
            adapter.notifyDataSetChanged();

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(results.getWindowToken(),0);


        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
