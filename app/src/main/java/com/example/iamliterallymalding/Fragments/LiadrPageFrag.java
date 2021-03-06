package com.example.iamliterallymalding.Fragments;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.iamliterallymalding.OpenGL.OpenGLRenderer;
import com.example.iamliterallymalding.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LiadrPageFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiadrPageFrag extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static GLSurfaceView openGLView;
    private static float [] lidarData;

    public LiadrPageFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LiadrPageFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static LiadrPageFrag newInstance(String param1, String param2) {
        LiadrPageFrag fragment = new LiadrPageFrag();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_liadr_page, container, false);

        openGLView = v.findViewById(R.id.LidarView);

        getParentFragmentManager().setFragmentResultListener("lidarRequest", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                lidarData = bundle.getFloatArray("lidarData").clone();
                LiadrPageFrag.renderLidar();
            }
        });

        Button lidarHomeClick = v.findViewById(R.id.LidarPageButton);
        lidarHomeClick.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.LidarPageButton){
            Navigation.findNavController(v).navigate(R.id.action_liadrPageFrag_to_generalOw);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            openGLView.onResume();
        }
        catch (NullPointerException e){
            renderLidar();
            openGLView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        openGLView.onPause();

        try {
            openGLView.onPause();
        }
        catch (NullPointerException e){
            renderLidar();
            openGLView.onPause();
        }
    }

    protected static void renderLidar(){
        openGLView.setRenderer(new OpenGLRenderer(lidarData));
    }

}