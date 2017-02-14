package me.anandprabhu.todoapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;

import me.anandprabhu.todoapp.R;
import me.anandprabhu.todoapp.adapter.RecyclerViewAdapter;
import me.anandprabhu.todoapp.data.model.TodoListItem;

/**
 * Created by Anand Prabhu.
 */

public class TodoListFragment extends Fragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ARG_DATA = "ARG_DATA";
    public static final String ARG_PENDING = "ARG_PENDING";
    public static final String ARG_COMPLETED = "ARG_COMPLETED";
    // Constants
    private static final String TAG = "TodoListFragment";
    private RecyclerView pendingListView;
    private RecyclerView completedListView;

    private int tabPosition;
    private ArrayList<TodoListItem> pendingListItems = new ArrayList<>();
    private ArrayList<TodoListItem> completedListItems = new ArrayList<>();

    public TodoListFragment() {
        // Empty public constructor
    }

    public static TodoListFragment newInstance(int position, Bundle args) {
        Bundle bundle = new Bundle();
        bundle.putInt("ARG_PAGE", position);
        bundle.putBundle("ARG_DATA", args);
        TodoListFragment fragment = new TodoListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            tabPosition = bundle.getInt(ARG_PAGE);
            Bundle data = bundle.getBundle(ARG_DATA);

            if (data != null) {
                pendingListItems = Parcels.unwrap(data.getParcelable(ARG_PENDING));
                completedListItems = Parcels.unwrap(data.getParcelable(ARG_COMPLETED));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Read data from the Bundle
        readBundle(getArguments());

        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pendingListView = (RecyclerView) view.findViewById(R.id.pending_list);
        completedListView = (RecyclerView) view.findViewById(R.id.completed_list);

        //create and set layout manager for each RecyclerView
        RecyclerView.LayoutManager pendingLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager completedLayoutManager = new LinearLayoutManager(getContext());

        pendingListView.setLayoutManager(pendingLayoutManager);
        completedListView.setLayoutManager(completedLayoutManager);

        // Set data based on Page
        setData(tabPosition);

        //Initializing and set adapter for each RecyclerView
        final RecyclerViewAdapter pendingListAdapter = new RecyclerViewAdapter(getContext(), pendingListItems);
        final RecyclerViewAdapter completedListAdapter = new RecyclerViewAdapter(getContext(), completedListItems);

        pendingListView.setAdapter(pendingListAdapter);
        completedListView.setAdapter(completedListAdapter);

        pendingListAdapter.setOnItemClickListener(new RecyclerViewAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick Position : " + position);
            }
        });
    }

    private void setData(int tabPosition) {
        if (tabPosition == 0) {
            // TODO Get current days data
        } else {
            // TODO Get all days data
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
