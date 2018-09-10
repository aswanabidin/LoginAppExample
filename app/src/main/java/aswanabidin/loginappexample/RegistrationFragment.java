package aswanabidin.loginappexample;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    private EditText etName;
    private EditText etUserName;
    private EditText etUserPassword;
    private Button btnRegister;
    private ProgressDialog progressDialog;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Registration process...");

        etName = (EditText) view.findViewById(R.id.et_name_register);
        etUserName = (EditText) view.findViewById(R.id.et_username_register);
        etUserPassword = (EditText) view.findViewById(R.id.et_password_register);
        btnRegister = (Button) view.findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });


        return view;
    }

    public void performRegistration() {

        String name = etName.getText().toString();
        String username = etUserName.getText().toString();
        String password = etUserPassword.getText().toString();

        Call<User> call = MainActivity.apiInterface.performRegistration(name, username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.body().getResponse().equals("oke")) {
                    MainActivity.prefConfig.displayToast("Registration success...");
                } else if (response.body().getResponse().equals("already exist")) {
                    MainActivity.prefConfig.displayToast("User already exist...");
                } else if (response.body().getResponse().equals("error")) {
                    MainActivity.prefConfig.displayToast("Something went wrong...");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        progressDialog.dismiss();

        etName.setText("");
        etUserPassword.setText("");
        etUserName.setText("");
    }

}
