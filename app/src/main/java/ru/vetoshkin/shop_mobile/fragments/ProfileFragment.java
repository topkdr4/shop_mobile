package ru.vetoshkin.shop_mobile.fragments;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import ru.vetoshkin.shop_mobile.R;
import ru.vetoshkin.shop_mobile.user.User;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }


    private Button saveButton;
    private CheckBox isDispatch;
    private EditText userName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        this.isDispatch = view.findViewById(R.id.profile_mail_dispatch);
        this.userName   = view.findViewById(R.id.profile_user_name);
        this.saveButton = view.findViewById(R.id.profile_save);

        User currentUser = User.getInstance();
        userName.setText(currentUser.getName());
        isDispatch.setChecked(currentUser.isDispatch());

        this.saveButton.setOnClickListener(v -> {
            Log.e("SAVE_PROFILE", "IS_DISPATCH: " + isDispatch.isChecked());
            Log.e("SAVE_PROFILE", "USER_NAME: "   + userName.getText().toString());
        });

        return view;
    }


}
