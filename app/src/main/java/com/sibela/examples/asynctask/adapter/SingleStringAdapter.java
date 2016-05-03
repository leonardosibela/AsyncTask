package com.sibela.examples.asynctask.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibela.examples.asynctask.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleStringAdapter<T> extends RecyclerView.Adapter<SingleStringAdapter.TitleDescriptionViewHolder> {

    private List<T> mList;

    public SingleStringAdapter(List<T> list) {
        mList = list;
    }

    @Override
    public TitleDescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_string_item, parent, false);
        return new TitleDescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TitleDescriptionViewHolder holder, int position) {
        if (mList.isEmpty())
            return;

        T item = mList.get(position);

        if (item != null) {
            holder.text.setText(item.toString());
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;

        return mList.size();
    }

    public void setContent(List<T> itens) {
        mList = itens;
        notifyDataSetChanged();
    }

    public List<T> getItens() {
        return mList;
    }

    public static class TitleDescriptionViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.text)
        TextView text;

        public TitleDescriptionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
