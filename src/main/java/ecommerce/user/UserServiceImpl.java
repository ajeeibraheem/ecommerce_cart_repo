package ecommerce.user;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final String BASE_URL = "https://dummyjson.com/";

    private final OkHttpClient client;

    public UserServiceImpl(OkHttpClient client) {
        this.client = client;
    }

    @Override
    public List<User> getAllUsers() {
        String url = BASE_URL + "users";

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseUserList(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch users from the API.", e);
        }
    }

    @Override
    public User getUser(Integer userId) {
        String url = BASE_URL + "users/" + userId;

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseUser(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch user with ID " + userId + " from the API.", e);
        }
    }

    @Override
    public List<User> searchUsers(String query) {
        String url = BASE_URL + "users/search?q=" + query;

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseUserList(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to search users with query: " + query, e);
        }
    }

    public String fetchJsonResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private List<User> parseUserList(String jsonResponse) {
        List<User> users = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject userJson = jsonArray.getJSONObject(i);
            User user = parseUser(userJson.toString());
            users.add(user);
        }
        return users;
    }

    private User parseUser(String jsonResponse) {
        JSONObject userJson = new JSONObject(jsonResponse);
        User user = new User();
        user.setId(userJson.getInt("id"));
        user.setUsername(userJson.getString("username"));
        user.setEmail(userJson.getString("email"));
        user.setFullName(userJson.getString("fullName"));
        user.setPhone(userJson.getString("phone"));
        user.setAddress(userJson.getString("address"));
        return user;
    }
}

