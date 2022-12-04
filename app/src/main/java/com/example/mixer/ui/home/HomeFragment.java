package com.example.mixer.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mixer.R;
import com.example.mixer.Song;
import com.example.mixer.databinding.FragmentHomeBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class HomeFragment extends Fragment {
    private static final int READ_EXTERNAL_STORAGE=1;
    private FragmentHomeBinding binding;

    ListView songList;//list view to put songs in
    ArrayAdapter adap;
    Uri uri;
    Vector<String> pathlist;//stores paths to songs to be able to play them
    public static int playingIndex=0;//the index of the currently playing song
    MediaPlayer mp;
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
        //when play/pause button is clicked call function "pp()"
        Button ppb=root.findViewById(R.id.ppb);
        ppb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp(view);
            }
        });
        //when next button is clicked increase index then call play
        Button nexts=root.findViewById(R.id.nexts);
        nexts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playingIndex++;
                //the mod makes sure the index number is never out of array bounds
                playingIndex=playingIndex%pathlist.size();
                play();
            }
        });
        //SAME as last one
        Button lasts=root.findViewById(R.id.lasts);
        nexts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playingIndex--;
                playingIndex=playingIndex%pathlist.size();
                play();
            }
        });

        checkperms();//checks needed permissions to access files
        mp = new MediaPlayer();
        songList=root.findViewById(R.id.listOfSongs);
        //make a simple string array adapter
        adap=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,loadlist(getContext()));
        songList.setAdapter(adap);//connect listview with the adapter

        //on clicking an item call the play function and set playing index to clicked item
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playingIndex=i;
                play();
            }
        });
        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void checkperms(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)==
                PackageManager.PERMISSION_DENIED){//if permission denied ask for it
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//
//        }
//    }

    public String[] loadlist(Context context){
        uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//get identifier for all audio on device
        //uri.getPath();
        String[] projec={//what data to query
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DATA
        };
        //cursor will point to the start of the data
        Cursor cursor=context.getContentResolver().query(uri,projec,null,null,null);

        Vector<String> templist=new Vector<>();//vector of names of songs to be displayed
        pathlist=new Vector<>();
//        System.out.println(cursor.getColumnCount());
        if(cursor!=null){//move on data and store it
            while(cursor.moveToNext()){
                templist.add(cursor.getString(0));
                pathlist.add(cursor.getString(1));

            }
            cursor.close();
        }
        //the adapter takes an array not a vector so we need to convert the names vector "templist" to an array
        String[] finalList=new String[templist.size()];

        for(int i=0;i<templist.size();i++){
            finalList[i]=templist.get(i);
        }
        //return the array
        return finalList;
    }
    public void play() {
        System.out.println("before reset__________________"+playingIndex+"__________________");

        //reset the mediaplayer to avoid playing two songs at once
        mp.reset();
        System.out.println("after reset__________________"+playingIndex+"__________________");
        try{
            String path = pathlist.elementAt(playingIndex);//path of song to be played
            System.out.println("after get__________________"+playingIndex+"__________________");
            try {
                //start playing
                mp.setDataSource(path);
                mp.prepare();
                mp.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return;
        }


        //System.out.println(path);



    }
    public void pp(View view){
        if(mp.isPlaying()){//if playing pause is paused play
            mp.pause();
        }
        else{
            mp.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();//stop playing on destroy
    }
}