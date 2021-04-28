package com.example.giribasicamplifier;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PedalAdapter extends RecyclerView.Adapter<PedalAdapter.ViewHolder> {

    private ArrayList<Pedal> pedalList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton imageBtn;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            imageBtn = (ImageButton) view.findViewById(R.id.pedalImageButton);
            /*        toggleDelayEffect = findViewById(R.id.delayButton);
        toggleDelayEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delay = !delay;
                LiveEffectEngine.setDelay(delay);
            }
        });*/
        }

        public ImageButton getImageButton() {
            return imageBtn;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public PedalAdapter(ArrayList<Pedal> dataSet) {
        pedalList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pedal_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getImageButton().setBackground(pedalList.get(position).getHappy());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return pedalList.size();
    }
}
