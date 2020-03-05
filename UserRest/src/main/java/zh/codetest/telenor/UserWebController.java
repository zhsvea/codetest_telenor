package zh.codetest.telenor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserWebController {
    public static final String SUCCESS_MESSAGE_WELCOME_BUSINESS_USER = "Welcome, business user!";
    public static final String FAILURE_PREFIX = "ERROR: ";
    public static final String FAILURE_MESSAGE_THE_PATH_IS_NOT_YET_IMPLEMENTED = FAILURE_PREFIX + "the path is not yet implemented";

    UserMockedData userMockedData = UserMockedData.getInstance();

    @RequestMapping("/all")
    public List<User> showAllUsers() {
        return userMockedData.findAllUsers();
    }

    @GetMapping("/greeting")
    public String showUser(@RequestParam(value="account", required = true) String account,
                           @RequestParam(value="id", required = false, defaultValue = "0") long id,
                           @RequestParam(value="type", required = false) String type) {

        if ("personal".equals(account)) {
            return showPersonalUser(id);
        } else if ("business".equals(account)) {
            return showBusinessUser(type);
        } else {
            return FAILURE_PREFIX + "wrong input: account=" + account;
        }
    }

    private String showPersonalUser(long id){
        if (id <= 0) {
            return "Wrong input: id=" + id;
        } else {
            User user = userMockedData.findPersonalUser(id);
            if (user != null) {
                return "Hi, userid " + user.getId();
            } else {
                return FAILURE_PREFIX + "this user is not found: {account=personal" + ", id=" + id + "}";
            }
        }
    }

    private String showBusinessUser(String type) {
        if ("small".equals(type)) {
            return FAILURE_MESSAGE_THE_PATH_IS_NOT_YET_IMPLEMENTED;
        } else if ("big".equals(type)) {
            User user = userMockedData.findBusinessUser(User.BusinessScale.BIG);
            if (user != null) {
                return SUCCESS_MESSAGE_WELCOME_BUSINESS_USER;
            } else {
                return FAILURE_PREFIX + "this user is not found: {account=business" + ", type=" + type + "}";
            }
        } else {
            return FAILURE_PREFIX + "wrong input: type=" + type;
        }
    }

}
