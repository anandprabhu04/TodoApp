package me.anandprabhu.todoapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import me.anandprabhu.todoapp.adapter.TodoListAdapter;
import me.anandprabhu.todoapp.data.model.TodoListItem;
import me.anandprabhu.todoapp.data.remote.TodoListService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoListActivity extends AppCompatActivity {

    public static final String ARG_POSITION = "ARG_POSITION";
    public static final String ARG_PENDING = "ARG_PENDING";
    public static final String ARG_COMPLETED = "ARG_COMPLETED";
    // Constants
    private static final String TAG = "TodoListActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView errorText;

    private List<TodoListItem> todoListItems;
    private ArrayList<TodoListItem> pendingItems = new ArrayList<>();
    private ArrayList<TodoListItem> completedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fetch data from the API Endpoint
        getTodoListData();

        Bundle args = new Bundle();
        args.putParcelable(ARG_PENDING, Parcels.wrap(pendingItems));
        args.putParcelable(ARG_COMPLETED, Parcels.wrap(completedItems));

        // Link ViewPager with it's PagerAdapter
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TodoListAdapter(getSupportFragmentManager(),
                TodoListActivity.this, args));

        // Add ViewPager to the TabLayout
        tabLayout = (TabLayout) findViewById(R.id.header_tabs);
        tabLayout.setupWithViewPager(viewPager);

        errorText = (TextView) findViewById(R.id.error_text);
        errorText.setVisibility(View.GONE);

        viewPager.setVisibility(View.GONE);
        tabLayout.setVisibility(View.GONE);
    }

    private void getTodoListData() {

        final ProgressDialog loading = ProgressDialog.show(this,
                this.getString(R.string.loading_title),
                this.getString(R.string.loading_please_wait),
                false, false);

        final TodoListService todoListService = TodoListService.retrofit.create(TodoListService.class);
        Call<List<TodoListItem>> call = todoListService.getTodoList();

        call.enqueue(new Callback<List<TodoListItem>>() {
            @Override
            public void onFailure(Call<List<TodoListItem>> call, Throwable t) {
                Log.d(TAG, "Error Occurred: " + t.getMessage());

                loading.dismiss();

                errorText.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
            }

            @Override
            public void onResponse(Call<List<TodoListItem>> call, Response<List<TodoListItem>> response) {
                loading.dismiss();

                todoListItems = response.body();

                if (todoListItems != null && todoListItems.size() > 0) {
                    for (int i = 0; i < todoListItems.size(); i++) {
                        if (todoListItems.get(i).getStatus().equals("PENDING")) {
                            pendingItems.add(todoListItems.get(i));
                        } else {
                            completedItems.add(todoListItems.get(i));
                        }
                    }
                    Log.d(TAG, "TodoList Items : " + todoListItems.toString());
                } else {
                    Log.d(TAG, "No item found");
                }

                viewPager.setVisibility(View.VISIBLE);
                tabLayout.setVisibility(View.VISIBLE);
                errorText.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(ARG_POSITION));
    }
}
