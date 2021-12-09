package com.gingerman.mvidemo.data.mvi;

public interface LoginIntent {
    final class Login implements LoginIntent {
    }

    final class ClearAccount implements LoginIntent {
    }

    final class ClearPassword implements LoginIntent {
    }

    final class UpdateAccount implements LoginIntent {
        private final String account;

        public UpdateAccount(String account) {
            this.account = account;
        }

        public String getAccount() {
            return account;
        }
    }

    final class UpdatePassword implements LoginIntent {
        private final String password;

        public UpdatePassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }
    }
}
