package com.sibela.examples.asynctask;

import java.util.List;

public interface ItemsDisplay {

    void display(List<Item> items);

    void updateProgress(int progress);
}
