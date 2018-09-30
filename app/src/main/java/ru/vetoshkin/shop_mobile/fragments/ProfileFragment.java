package ru.vetoshkin.shop_mobile.fragments;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ru.vetoshkin.shop_mobile.R;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


}
