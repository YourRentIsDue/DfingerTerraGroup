package com.example.iamliterallymalding.Fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iamliterallymalding.R;
import com.example.iamliterallymalding.Tasks.LidarFetch;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoadingScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadingScreen extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoadingScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoadingScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static LoadingScreen newInstance(String param1, String param2) {
        LoadingScreen fragment = new LoadingScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        LidarFetch lidar = new LidarFetch();

        Thread run = new Thread(lidar);

        run.start();

        View v = inflater.inflate(R.layout.fragment_loading_screen, container, false);

        lidar.getOutput().observe(getViewLifecycleOwner(), new Observer<float[]>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(float[] floats) {
                Bundle result = new Bundle();
                result.putFloatArray("lidarData", lidar.getOutput().getValue());
                getParentFragmentManager().setFragmentResult("lidarDataRequest", result);
                Navigation.findNavController(v).navigate(R.id.action_loadingScreen_to_generalOw);
            }
        });

        return v;
    }


}