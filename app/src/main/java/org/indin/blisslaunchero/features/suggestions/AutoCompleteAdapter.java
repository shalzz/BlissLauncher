package org.indin.blisslaunchero.features.suggestions;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.indin.blisslaunchero.R;
import org.indin.blisslaunchero.features.launcher.LauncherActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoCompleteAdapter extends
        RecyclerView.Adapter<AutoCompleteAdapter.AutoCompleteViewHolder> {
    private List<String> mItems = new ArrayList<>();
    private final Context mContext;
    private final OnSuggestionClickListener mOnSuggestionClickListener;
    private final LayoutInflater mInflater;
    private String mQueryText;

    private static final String TAG = "AutoCompleteAdapter";

    public AutoCompleteAdapter(Context context) {
        super();
        mContext = context;
        mOnSuggestionClickListener = (LauncherActivity) mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public AutoCompleteAdapter.AutoCompleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
            int viewType) {

        View view = mInflater.inflate(R.layout.item_suggestion, parent, false);
        AutoCompleteViewHolder holder = new AutoCompleteViewHolder(view);
        view.setOnClickListener(
                v -> mOnSuggestionClickListener.onClick(mItems.get(holder.getAdapterPosition())));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AutoCompleteAdapter.AutoCompleteViewHolder holder,
            int position) {
        String suggestion = mItems.get(position);
        if (mQueryText != null) {
            SpannableStringBuilder spannable = new SpannableStringBuilder(suggestion);
            String lcSuggestion = suggestion.toLowerCase(Locale.getDefault());
            int queryTextPos = lcSuggestion.indexOf(mQueryText);
            while (queryTextPos >= 0) {
                spannable.setSpan(new StyleSpan(Typeface.BOLD),
                        queryTextPos, queryTextPos + mQueryText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new ForegroundColorSpan(Color.WHITE), queryTextPos,
                        queryTextPos + mQueryText.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                queryTextPos = lcSuggestion.indexOf(mQueryText, queryTextPos + mQueryText.length());
            }
            holder.mSuggestionTextView.setText(spannable);
        } else {
            holder.mSuggestionTextView.setText(suggestion);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateSuggestions(List<String> suggestions, String queryText) {
        mItems = suggestions;
        mQueryText = queryText;
        notifyDataSetChanged();
    }

    public void clearSuggestions() {
        mItems.clear();
        notifyDataSetChanged();
    }

    static class AutoCompleteViewHolder extends RecyclerView.ViewHolder {

        private TextView mSuggestionTextView;

        public AutoCompleteViewHolder(View itemView) {
            super(itemView);
            mSuggestionTextView = itemView.findViewById(R.id.suggestionTextView);
        }
    }

    public interface OnSuggestionClickListener {
        void onClick(String suggestion);
    }
}
