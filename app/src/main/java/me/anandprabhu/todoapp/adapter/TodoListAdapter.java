package me.anandprabhu.todoapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.anandprabhu.todoapp.ui.TodoListFragment;

/**
 * Created by Anand Prabhu.
 */

public class TodoListAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"TODAY", "LATER"};
    private Context context;
    private Bundle args;

    public TodoListAdapter(FragmentManager fm, Context context, Bundle args) {
        super(fm);
        this.context = context;
        this.args = args;
    }

    @Override
    public Fragment getItem(int position) {
        return TodoListFragment.newInstance(position, args);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
