package com.neilmarietta.hipchops.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neilmarietta.hipchops.R;
import com.neilmarietta.hipchops.presentation.model.EmoticonUrls;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IOMessageAdapter extends RecyclerView.Adapter<IOMessageAdapter.IOMessageViewHolder> {

    private final LayoutInflater mLayoutInflater;

    private List<IOMessage> mMessages = new ArrayList<>();

    public IOMessageAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMessages(List<IOMessage> messages) {
        if (messages == null) {
            int size = mMessages.size();
            mMessages = new ArrayList<>();
            notifyItemRangeRemoved(0, size);
        } else {
            mMessages = messages;
            notifyDataSetChanged();
        }
    }

    public void addMessage(IOMessage message) {
        if (mMessages == null)
            mMessages = new ArrayList<>();
        // Add to the first position
        mMessages.add(0, message);
        // RecyclerView uses a DefaultItemAnimator by default.
        notifyItemInserted(0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return (mMessages != null) ? mMessages.size() : 0;
    }

    @Override
    public IOMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = mLayoutInflater.inflate(R.layout.cardview_message, parent, false);
        return new IOMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IOMessageViewHolder holder, final int position) {
        final IOMessage message = mMessages.get(position);
        holder.input.setText(message.getInput());
        holder.output.setText(message.getJsonOutput());

        renderEmoticons(holder, message);
    }

    private void renderEmoticons(IOMessageViewHolder holder, IOMessage message) {
        Context context = holder.emoticons.getContext();
        holder.emoticons.removeAllViews();

        for (String shortcut : message.getMessage().getEmoticons()) {
            String url = EmoticonUrls.CACHE.get(shortcut);

            if (url == null) continue;

            ImageView imageView = new ImageView(context);
            holder.emoticons.addView(imageView);

            Picasso picasso = Picasso.with(context);
            picasso.setIndicatorsEnabled(true);
            picasso.load(url)
                    .resize(100, 100)
                    .centerInside()
                    .into(imageView);
        }
    }

    static class IOMessageViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_input) TextView input;
        @Bind(R.id.tv_output) TextView output;
        @Bind(R.id.tv_emoticons) LinearLayout emoticons;

        public IOMessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}