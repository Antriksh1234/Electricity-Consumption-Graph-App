package com.atandroidlabs.electricityconsumptiongraphapp;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class ElectricalMarkerView extends MarkerView {

    private TextView switchTextView, electricalTextView;
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */

    public ElectricalMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        switchTextView = findViewById(R.id.switch_number);
        electricalTextView = findViewById(R.id.electrical_units_used);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String switchText = "Switch " + (int) e.getX();
        switchTextView.setText(switchText);

        String electricalText =  "" + e.getY() + " Units";
        electricalTextView.setText(electricalText);
        // this will perform necessary layout stuff
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {
        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), getHeight());
            mOffset = new MPPointF(-(getWidth() / 2), 1f);
        }
        return mOffset;
    }
}
