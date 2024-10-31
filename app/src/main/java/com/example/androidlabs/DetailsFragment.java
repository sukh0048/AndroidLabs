package com.example.androidlabs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

    // Argument keys
    private static final String ARG_NAME = "name";
    private static final String ARG_HEIGHT = "height";
    private static final String ARG_MASS = "mass";
    private static final String ARG_BIRTH_YEAR = "birthYear";

    public DetailsFragment() {
        // Required empty public constructor
    }

    // Factory method to create a new instance of DetailsFragment with character details
    public static DetailsFragment newInstance(String name, String height, String mass, String birthYear) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_HEIGHT, height);
        args.putString(ARG_MASS, mass);
        args.putString(ARG_BIRTH_YEAR, birthYear);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment's layout
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Retrieve arguments and update TextViews
        if (getArguments() != null) {
            String name = getArguments().getString(ARG_NAME);
            String height = getArguments().getString(ARG_HEIGHT);
            String mass = getArguments().getString(ARG_MASS);
            String birthYear = getArguments().getString(ARG_BIRTH_YEAR);

            // Initialize and set text for each TextView
            TextView nameTextView = view.findViewById(R.id.characterName);
            TextView heightTextView = view.findViewById(R.id.characterHeight);
            TextView massTextView = view.findViewById(R.id.characterMass);
            TextView birthYearTextView = view.findViewById(R.id.characterBirthYear);

            nameTextView.setText("Name: " + name);
            heightTextView.setText("Height: " + height + " cm");
            massTextView.setText("Mass: " + mass + " kg");
            birthYearTextView.setText("Birth Year: " + birthYear);
        }

        return view;
    }
}
