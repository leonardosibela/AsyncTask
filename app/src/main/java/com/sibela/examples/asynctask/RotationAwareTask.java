package com.sibela.examples.asynctask;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RotationAwareTask extends AsyncTask<Void, Void, List<Item>> {

    ItemsDisplay itemsDisplay = null;

    int progress = 0;

    RotationAwareTask(ItemsDisplay itemsDisplay) {
        attach(itemsDisplay);
    }

    @Override
    protected List<Item> doInBackground(Void... unused) {

        List<Item> items = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            SystemClock.sleep(500);
            Item item = new Item("Name " + i);
            items.add(item);
            Log.e("ADDING_ITEM", "Name " + i);
            publishProgress();
        }

        return items;
    }

    @Override
    protected void onProgressUpdate(Void... unused) {
        if (itemsDisplay == null) {
            Log.i("RotationAsync", "onProgressUpdate() skipped -- no itemsDisplay");
        } else {
            progress += 5;
            itemsDisplay.updateProgress(progress);
        }
    }

    @Override
    protected void onPostExecute(List<Item> items) {
        if (itemsDisplay == null) {
            Log.i("RotationAsync", "onPostExecute() skipped -- no itemsDisplay");
        } else {
            itemsDisplay.display(items);
        }
    }

    void detach() {
        itemsDisplay = null;
    }

    void attach(ItemsDisplay itemsDisplay)   {
        this.itemsDisplay = itemsDisplay;
    }

    int getProgress() {
        return (progress);
    }
}