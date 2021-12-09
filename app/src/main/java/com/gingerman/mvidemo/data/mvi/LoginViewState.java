package com.gingerman.mvidemo.data.mvi;

import com.gingerman.mvidemo.data.bean.UserInfo;

public interface LoginViewState {
    final class Loading implements LoginViewState {
    }

    final class LoginSuccess implements LoginViewState {
        public LoginSuccess(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        private final UserInfo userInfo;

        public UserInfo getUserInfo() {
            return userInfo;
        }
    }

    final class LoginFail implements LoginViewState {
        public LoginFail(String error) {
            this.error = error;
        }

        private final String error;

        public String getError() {
            return error;
        }
    }
}
