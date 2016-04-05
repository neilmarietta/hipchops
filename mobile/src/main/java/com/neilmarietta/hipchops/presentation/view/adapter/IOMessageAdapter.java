package com.neilmarietta.hipchops.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.neilmarietta.hipchops.HipChopsApplication;
import com.neilmarietta.hipchops.R;
import com.neilmarietta.hipchops.entity.Emoticon;
import com.neilmarietta.hipchops.internal.di.component.DaggerEmoticonComponent;
import com.neilmarietta.hipchops.internal.di.module.EmoticonModule;
import com.neilmarietta.hipchops.presentation.model.IOMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class IOMessageAdapter extends RecyclerView.Adapter<IOMessageAdapter.IOMessageViewHolder> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<IOMessage> mMessages;

    public IOMessageAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    public void setMessages(List<IOMessage> messages) {
        mMessages = messages;
        notifyDataSetChanged();
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

        fetchEmoticonsIfNeeded(message);
        renderEmoticons(holder, message);
    }

    private void renderEmoticons(IOMessageViewHolder holder, IOMessage message) {
        Context context = holder.emoticons.getContext();
        holder.emoticons.removeAllViews();

        List<String> emoticons = message.getMessage().getEmoticons();
        if (emoticons != null) {
            for (String shortcut : emoticons) {
                Emoticon emoticon = message.getEmoticons().get(shortcut);

                if (emoticon.getUrl() == null) continue;

                ImageView imageView = new ImageView(context);
                holder.emoticons.addView(imageView);

                Picasso picasso = Picasso.with(context);
                picasso.setIndicatorsEnabled(true);
                picasso.load(emoticon.getUrl())
                        .resize(100, 100)
                        .centerInside()
                        .into(imageView);
            }
        }
    }

    private void fetchEmoticonsIfNeeded(IOMessage message) {
        // TODO : Add Emoticon Cache (EmoticonDataStoreFactory)

        List<String> missingEmoticons = message.getMissingEmoticons();
        if (missingEmoticons.size() > 0) {
            for (String shortcut : missingEmoticons) {
                // Put a temporary Emoticon
                message.getEmoticons().put(shortcut, new Emoticon(shortcut));
                // Fetch all missing
                fetchEmoticon(shortcut, message);
            }
        }
    }

    private void fetchEmoticon(final String shortcut, final IOMessage message) {
        HipChopsApplication application = (HipChopsApplication) mContext.getApplicationContext();
        DaggerEmoticonComponent.builder()
                .apiConnectionComponent(application.getApiConnectionComponent())
                .emoticonModule(new EmoticonModule(shortcut))
                .build()
                .emoticonUseCase().execute(new Subscriber<Emoticon>() {
            @Override
            public void onCompleted() {
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Emoticon emoticon) {
                message.getEmoticons().put(emoticon.getShortcut(), emoticon);
            }
        });
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