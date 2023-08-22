package ecommerce.product;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private static final String BASE_URL = "https://dummyjson.com/";

    private final OkHttpClient client = new OkHttpClient();

    public ProductServiceImpl(OkHttpClient mockClient) {
    }

    @Override
    public List<Product> getAllProducts() {
        String url = BASE_URL + "products";

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseProductList(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch products from the API.", e);
        }
    }

    @Override
    public List<Product> getAllProducts(int limit, int skip, String... fields) {
        String url = BASE_URL + "products?limit=" + limit + "&skip=" + skip + "&select=" + String.join(",", fields);

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseProductList(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch products from the API.", e);
        }
    }

    @Override
    public Product getProduct(Integer productId) {
        String url = BASE_URL + "products/" + productId;

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseProduct(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch product with ID " + productId + " from the API.", e);
        }
    }

    @Override
    public List<Product> searchProducts(String query) {
        String url = BASE_URL + "products/search?q=" + query;

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseProductList(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch products from the API.", e);
        }
    }

    @Override
    public List<String> getCategories() {
        String url = BASE_URL + "products/categories";

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseCategories(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch categories from the API.", e);
        }
    }

    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        String url = BASE_URL + "products/category/" + categoryName;

        try {
            String jsonResponse = fetchJsonResponse(url);
            return parseProductList(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch products from the API.", e);
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

    private List<Product> parseProductList(String jsonResponse) {
        List<Product> products = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            var productJson = jsonArray.getJSONObject(i);
            Product product = new Product();
            product.setId(productJson.getInt("id"));
            product.setTitle(productJson.getString("title"));
            product.setDescription(productJson.getString("description"));
            product.setPrice(productJson.getDouble("price"));
            product.setCategory(productJson.getString("category"));
            products.add(product);
        }
        return products;
    }

    private Product parseProduct(String jsonResponse) {
        var productJson = new JSONObject(jsonResponse);
        Product product = new Product();
        product.setId(productJson.getInt("id"));
        product.setTitle(productJson.getString("title"));
        product.setDescription(productJson.getString("description"));
        product.setPrice(productJson.getDouble("price"));
        product.setCategory(productJson.getString("category"));
        return product;
    }

    private List<String> parseCategories(String jsonResponse) {
        List<String> categories = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
            categories.add(jsonArray.getString(i));
        }
        return categories;
    }
}


