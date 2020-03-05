package zh.codetest.telenor;

import java.util.Objects;

public class User {
    public enum AccountType {
        PERSONAL,
        BUSINESS
    }

    public enum BusinessScale {
        SMALL,
        BIG
    }

    private final AccountType account;
    private final Long id;
    private final BusinessScale type;

    public static class Builder {
        // Required parameters
        private final AccountType accountType;

        // Optional parameters
        private Long userId = null;
        private BusinessScale businessScale = null;

        public Builder(AccountType accountType) {
            if (accountType == null) {
                throw new IllegalArgumentException("Account type must not be null");
            }
            this.accountType = accountType;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder businessScale(BusinessScale businessScale) {
            this.businessScale = businessScale;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        this.account = builder.accountType;
        this.id = builder.userId;
        this.type = builder.businessScale;
    }

    public AccountType getAccount() {
        return account;
    }

    public Long getId() {
        return id;
    }

    public BusinessScale getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return account == user.account &&
                Objects.equals(id, user.id) &&
                type == user.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, id, type);
    }

    @Override
    public String toString() {
        return "User{" +
                "account=" + account +
                ", id=" + id +
                ", type=" + type +
                '}';
    }
}
