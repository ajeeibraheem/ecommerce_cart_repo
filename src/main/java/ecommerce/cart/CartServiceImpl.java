package ecommerce.cart;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartServiceImpl implements CartService {

    private static final String BASE_URL = "https://dummyjson.com/";

    private final OkHttpClient client = new OkHttpClient();

    public CartServiceImpl(OkHttpClient mockClient) {
    }

    @Override
    public List<Cart> getAllCarts() {
        String url = BASE_URL + "carts";

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseCartList(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch carts from the API.", e);
        }
    }

    @Override
    public Cart getCart(Integer cartId) {
        String url = BASE_URL + "carts/" + cartId;

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseCart(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch cart with ID " + cartId + " from the API.", e);
        }
    }

    @Override
    public List<Cart> getUserCarts(Integer userId) {
        String url = BASE_URL + "carts/user/" + userId;

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseCartList(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch carts for user with ID " + userId + " from the API.", e);
        }
    }

    private String fetchJsonResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private List<Cart> parseCartList(String jsonResponse) {
        List<Cart> carts = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            var cartJson = jsonArray.getJSONObject(i);
            Cart cart = new Cart();
            cart.setId(cartJson.getInt("id"));
            cart.setUserId(cartJson.getInt("userId"));
            cart.setItems(parseCartItemArray(cartJson.getJSONArray("items")));
            carts.add(cart);
        }
        return carts;
    }

    private List<CartItem> parseCartItemArray(JSONArray jsonArray) {
        List<CartItem> cartItems = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            var cartItemJson = jsonArray.getJSONObject(i);
            CartItem cartItem = new CartItem();
            cartItem.setProductId(cartItemJson.getInt("productId"));
            cartItem.setQuantity(cartItemJson.getInt("quantity"));
            cartItems.add(cartItem);
        }
        return cartItems;
    }

    private Cart parseCart(String jsonResponse) {
        var cartJson = new JSONObject(jsonResponse);
        Cart cart = new Cart();
        cart.setId(cartJson.getInt("id"));
        cart.setUserId(cartJson.getInt("userId"));
        cart.setItems(parseCartItemArray(cartJson.getJSONArray("items")));
        return cart;
    }
}

