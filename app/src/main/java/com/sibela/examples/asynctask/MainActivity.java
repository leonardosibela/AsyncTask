package com.sibela.examples.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.sibela.examples.asynctask.adapter.SingleStringAdapter;
import com.sibela.examples.asynctask.util.GsonUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemsDisplay {

    public static final String ITEMS = "items";
    @Bind(R.id.progress)
    ProgressBar bar;

    @Bind(R.id.list_item)
    RecyclerView recyclerView;

    private SingleStringAdapter<Item> adapter;

    private RotationAwareTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        task = (RotationAwareTask) getLastCustomNonConfigurationInstance();

        if (task == null) {
            task = new RotationAwareTask(this);
            task.execute();
        } else {
            task.attach(this);
            updateProgress(task.getProgress());
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        task.detach();
        return task;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (task.getStatus().equals(AsyncTask.Status.FINISHED)) {
            String strItems = GsonUtil.toJsonAsString(adapter.getItens());
            outState.putString(ITEMS, strItems);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String strItens = savedInstanceState.getString(ITEMS);
        if (strItens != null) {
            List<Item> items = GsonUtil.fromJsonList(strItens, Item.class);
            display(items);
        }
    }

    @Override
    public void display(List<Item> items) {
        recyclerView.setVisibility(View.VISIBLE);
        bar.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SingleStringAdapter<>(items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateProgress(int progress) {
        bar.setProgress(progress);
    }
}
