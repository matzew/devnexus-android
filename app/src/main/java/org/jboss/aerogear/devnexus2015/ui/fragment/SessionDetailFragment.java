package org.jboss.aerogear.devnexus2015.ui.fragment;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.devnexus.util.GsonUtils;
import org.devnexus.util.TrackRoomUtil;
import org.devnexus.vo.Presentation;
import org.devnexus.vo.Speaker;
import org.devnexus.vo.contract.PresentationContract;
import org.jboss.aerogear.devnexus2015.MainActivity;
import org.jboss.aerogear.devnexus2015.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by summers on 1/5/15.
 */
public class SessionDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TOOLBAR_TITLE = "SessionDetailFragment.toolbar_title";
    private static final String PRESENTATION_ID = "SessionDetailFragment.presentation_id";
    private static final int DETAIL_LOADER = 0x0100;
    private Toolbar toolbar;
    private RecyclerView speakersView;
    private ContentResolver resolver;
    private TextView description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.session_detail_layout, null);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getArguments().getString(TOOLBAR_TITLE));
        ((MainActivity) getActivity()).attachToolbar(toolbar);
        speakersView = (RecyclerView) view.findViewById(R.id.speakers);
        speakersView.setLayoutManager(new LinearLayoutManager(getActivity()));
        resolver = getActivity().getContentResolver();
        speakersView.setAdapter(new SpeakerSessionAdapter(new ArrayList<Speaker>(1), getActivity()));
        description = (TextView) view.findViewById(R.id.description);
        Spinner spinner = (Spinner) toolbar.findViewById(R.id.spinner_nav);
        getLoaderManager().initLoader(DETAIL_LOADER, getArguments(), this);
        return view;
    }

    public static SessionDetailFragment newInstance(String title, int presentationId){
        Bundle args = new Bundle();
        args.putString(TOOLBAR_TITLE, title);
        args.putInt(PRESENTATION_ID, presentationId);
        SessionDetailFragment fragment = new SessionDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), PresentationContract.URI, null, PresentationContract.toQuery(PresentationContract.PRESENTATION_ID), new String[]{"" + args.getInt(PRESENTATION_ID)}, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        Presentation presentation = GsonUtils.GSON.fromJson(data.getString(0), Presentation.class);

        description.setText(presentation.description);
        toolbar.setBackgroundColor(getResources().getColor(TrackRoomUtil.forTrack(presentation.track.name)));
        speakersView.setAdapter(new SpeakerSessionAdapter(presentation.speakers, loader.getContext()));

        data.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public static class SpeakerSessionAdapter extends RecyclerView.Adapter<SpeakerSessionAdapter.ViewHolder> {

        private final List<Speaker> items;
        private final Context mContext;

        public SpeakerSessionAdapter(List<Speaker> speakers, Context context) {
            this.items = speakers;
            this.mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.speakers_session_detail_layout, viewGroup, false);

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Speaker speaker = getItem(position);
            Picasso.with(mContext).load("http://devnexus.com/s/speakers/"+speaker.id+".jpg").placeholder(R.drawable.speaker).fit().centerCrop().into((holder).photo);
            holder.bio.setText(speaker.bio);
        }

        private Speaker getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final ImageView photo;
            public final View speakerView;
            public final TextView bio;
            public ViewHolder(View itemView) {
                super(itemView);
                speakerView = itemView;
                photo = (ImageView) itemView.findViewById(R.id.photo);
                bio = (TextView) itemView.findViewById(R.id.bio);
            }
        }

    }
}