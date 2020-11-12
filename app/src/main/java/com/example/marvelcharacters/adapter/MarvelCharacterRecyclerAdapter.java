package com.example.marvelcharacters.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.marvelcharacters.MyApplication;
import com.example.marvelcharacters.R;
import com.example.marvelcharacters.activity.MainActivity;
import com.example.marvelcharacters.entity.Person;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MarvelCharacterRecyclerAdapter extends RecyclerView.Adapter<MarvelCharacterRecyclerAdapter.DataObjectHolder> {

    Person person;
    private List<Person> data;
    private Activity activity;
    private static LayoutInflater inflater = null;
    Box<Person> personBox;

    @Override
    public MarvelCharacterRecyclerAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.marvel_character_recycler_view, parent, false);
        MarvelCharacterRecyclerAdapter.DataObjectHolder myViewHolder = new MarvelCharacterRecyclerAdapter.DataObjectHolder(view);
        MyApplication myApplication = new MyApplication();
        BoxStore boxStore = myApplication.getBoxStore();
        personBox = boxStore.boxFor(Person.class);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MarvelCharacterRecyclerAdapter.DataObjectHolder holder, final int position) {
        if (data.size() <= 0) {
        } else {
            person = (Person) data.get(position);
            holder.tvName.setText(person.getName());
            holder.tvDescription.setText(person.getDescription());
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getRandomColor();
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .buildRoundRect(String.valueOf(holder.tvName.getText().toString().charAt(0)), color, 80);
            holder.ivCharacter.setImageDrawable(drawable);
            holder.personCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new AlertDialog.Builder(activity)
                            .setTitle(activity.getString(R.string.title_delete))
                            .setMessage(activity.getString(R.string.message_delete))
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    personBox.remove(data.get(position));
                                    MainActivity.personList.remove(data.get(position));
                                    notifyDataSetChanged();
                                }
                            }).create().show();
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(data!=null)
            return data.size();
        else return 0;
    }


    public MarvelCharacterRecyclerAdapter(Activity a, List<Person> d) {
        /********** Take passed values **********/
        activity = a;
        data = d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDescription;
        ImageView ivCharacter;
        CardView personCardView;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            ivCharacter = (ImageView) itemView.findViewById(R.id.iv_character);
            personCardView = (CardView) itemView.findViewById(R.id.person_card_view);
        }
    }
}