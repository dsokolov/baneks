package me.ilich.baneks.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.ilich.baneks.R;
import me.ilich.baneks.commands.AbstractCommand;
import me.ilich.baneks.commands.TopCommand;
import me.ilich.baneks.commands.errors.ErrorResponse;
import me.ilich.baneks.data.TopItem;
import me.ilich.baneks.states.NumberAnekState;
import me.ilich.juggler.change.Add;
import me.ilich.juggler.gui.JugglerFragment;

public class TopAneksFragment extends JugglerFragment {

    public static TopAneksFragment create() {
        return new TopAneksFragment();
    }

    @Bind(R.id.l_aneks)
    RecyclerView aneksRecyclerView;
    private Adapter adapter = new Adapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        aneksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        aneksRecyclerView.setAdapter(adapter);
        TopCommand.Callback<List<TopItem>> callback = new AbstractCommand.Callback<List<TopItem>>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(List<TopItem> successResponse) {
                adapter.setData(successResponse);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(ErrorResponse errorResponse) {

            }

            @Override
            public void onFinish() {

            }
        };
        TopCommand command = new TopCommand(callback);
        command.execute();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.t_top_pos)
        TextView topPosTextView;
        @Bind(R.id.t_top_title)
        TextView topTitleTextView;

        public ViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(getContext()).inflate(R.layout.listitem_top, parent, false));
            ButterKnife.bind(this, itemView);
        }

    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<TopItem> items = new ArrayList<>();

        public void setData(List<TopItem> items) {
            this.items.clear();
            this.items.addAll(items);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final TopItem topItem = items.get(position);
            holder.topPosTextView.setText(String.format(getString(R.string.top_pos), position + 1));
            holder.topTitleTextView.setText(topItem.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateTo().state(Add.deeper(new NumberAnekState(topItem.getId())));
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

    }

}
