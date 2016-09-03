package br.com.odra.devnexus.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;

import com.google.maps.android.ui.IconGenerator;

import br.com.odra.devnexus.R;
import br.com.odra.devnexus.model.RoomMetaData;

public class GWCCMapIconGenerator {

    private final Context context;
    

    public GWCCMapIconGenerator(Context c) {
        this.context = c;
        
    }

    public Bitmap getIcon(RoomMetaData room, String title) {
        IconGenerator iconGenerator = new IconGenerator(context);
        iconGenerator.setTextAppearance(R.style.MapLabel);
        iconGenerator.setContentPadding(3,3,3,3);
        iconGenerator.setBackground(new ColorDrawable(room.color));
        return iconGenerator.makeIcon(title);

    }


}
