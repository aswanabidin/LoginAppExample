package aswanabidin.loginappexample;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
public class LoginFragment extends Fragment {

    private EditText etUserName, etUserPassword;
    private Button btnLogin;
    private Button btntoRegister;
    OnLoginFormActivityListener loginFormActivityListener;

    public interface OnLoginFormActivityListener{
        public void performRegister();
        public void performLogin(String name);
    }


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUserName = (EditText) view.findViewById(R.id.et_username_login);
        etUserPassword = (EditText) view.findViewById(R.id.et_password_login);
        btnLogin = (Button) view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });

        btntoRegister = (Button) view.findViewById(R.id.btn_toregister);
        btntoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFormActivityListener.performRegister();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFormActivityListener = (OnLoginFormActivityListener) activity;
    }

    private void performLogin(){

        String username = etUserName.getText().toString();
        String password = etUserPassword.getText().toString();

        Call<User> call = MainActivity.apiInterface.performUserLogin(username,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.body().getResponse().equals("ok")){

                    MainActivity.prefConfig.writeLoginStatus(true);
                    loginFormActivityListener.performLogin(response.body().getName());
                    MainActivity.prefConfig.displayToast("Login success...");

                } else if (response.body().getResponse().equals("failed")){
                    MainActivity.prefConfig.displayToast("Login Failed...Please try again...");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        etUserName.setText("");
        etUserPassword.setText("");
    }
}
