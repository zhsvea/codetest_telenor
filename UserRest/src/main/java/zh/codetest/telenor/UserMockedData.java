package zh.codetest.telenor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class UserMockedData {
    private static final int MAX_NUMBER_PERSONAL_USERS = 200;
    private static final int MAX_NUMBER_BUSINESS_SMALL_USERS = 100;
    private static final int MAX_NUMBER_BUSINESS_BIG_USERS = 100;

    private List<User> users;

    private static UserMockedData instance = null;

    public static UserMockedData getInstance() {
        if (instance == null) {
            instance = new UserMockedData();
        }
        return instance;
    }

    private static final AtomicLong count = new AtomicLong(0);

    private static long generateNextId() {
        return count.getAndIncrement();
    }

    private void populate() {
        this.users = new ArrayList<>();
        for (int i = 0 ; i < MAX_NUMBER_PERSONAL_USERS ; ++i) {
            users.add(new User.Builder(User.AccountType.PERSONAL).userId(generateNextId()).build());
        }
        for (int j = 0 ; j < MAX_NUMBER_BUSINESS_SMALL_USERS ; ++j) {
            users.add(new User.Builder(User.AccountType.BUSINESS).businessScale(User.BusinessScale.SMALL)
                    .userId(generateNextId()).build());
        }
        for (int k = 0 ; k < MAX_NUMBER_BUSINESS_BIG_USERS ; ++k) {
            users.add(new User.Builder(User.AccountType.BUSINESS).businessScale(User.BusinessScale.BIG)
                    .userId(generateNextId()).build());
        }
    }

    public UserMockedData() {
        populate();
    }

    public List<User> findAllUsers() {
        return users;
    }

    public User findPersonalUser(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Illegal ID value: " + id);
        }

        for (User user : users) {
            if (user.getAccount() == User.AccountType.PERSONAL && user.getId() == id) {
                return new User.Builder(User.AccountType.PERSONAL).userId(id).build();
            }
        }
        return null;
    }

    public User findBusinessUser(User.BusinessScale businessScale) {
        if (businessScale == null) {
            throw new IllegalArgumentException("BusinessScale must not be null");
        }

        for (User user : users) {
            if (user.getAccount() == User.AccountType.BUSINESS && user.getType() == businessScale) {
                return new User.Builder(User.AccountType.PERSONAL).businessScale(businessScale).build();
            }
        }
        return null;
    }

}
