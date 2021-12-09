package com.gingerman.mvidemo.data.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gingerman.mvidemo.AppExecutors;
import com.gingerman.mvidemo.data.bean.BasicBean;
import com.gingerman.mvidemo.data.bean.UserInfo;
import com.gingerman.mvidemo.data.http.ApiCall;
import com.gingerman.mvidemo.data.http.HttpService;
import com.gingerman.mvidemo.data.mvi.LoginIntent;
import com.gingerman.mvidemo.data.mvi.LoginViewState;

import retrofit2.Response;

public class MVIViewModel extends ViewModel {
    private final String TAG = MVIViewModel.class.getSimpleName();
    private final HttpService mHttpService = ApiCall.INSTANCE.getRetrofit();
    private final MutableLiveData<LoginViewState> loginViewState = new MutableLiveData<>();
    private String username;
    private String password;

    public MVIViewModel() {
        super();
    }

    public void action(LoginIntent intent) {
        if (intent instanceof LoginIntent.Login) {
            login();
        } else if (intent instanceof LoginIntent.ClearAccount) {
            username = "";
        } else if (intent instanceof LoginIntent.ClearPassword) {
            password = "";
        } else if (intent instanceof LoginIntent.UpdateAccount) {
            username = ((LoginIntent.UpdateAccount) intent).getAccount();
        } else if (intent instanceof LoginIntent.UpdatePassword) {
            password = ((LoginIntent.UpdatePassword) intent).getPassword();
        }
    }

    public void login() {
        loginViewState.setValue(new LoginViewState.Loading());
        AppExecutors.getInstance().networkIO().execute(() -> {
            try {
                Thread.sleep(3000);
                Response<BasicBean<UserInfo>> response = mHttpService.login(username, password).execute();
                if (response.isSuccessful()) {
                    UserInfo userInfo = response.body().getData();
                    loginViewState.postValue(new LoginViewState.LoginSuccess(userInfo));
                } else {
                    loginViewState.postValue(new LoginViewState.LoginFail(response.message()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MutableLiveData<LoginViewState> getLoginViewState() {
        return loginViewState;
    }
}
