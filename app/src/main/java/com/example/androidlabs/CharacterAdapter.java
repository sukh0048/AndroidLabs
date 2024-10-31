package com.example.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class CharacterAdapter extends ArrayAdapter<Character> {

    public CharacterAdapter(Context context, List<Character> characters) {
        super(context, 0, characters);
    }

    public void updateCharacters(List<Character> characters) {
        clear();
        addAll(characters);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Character character = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.character_item, parent, false);
        }
        TextView nameTextView = convertView.findViewById(R.id.characterNameText);
        nameTextView.setText(character.getName());
        return convertView;
    }
}

