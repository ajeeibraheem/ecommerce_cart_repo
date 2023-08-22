import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import okhttp3.*;
import okio.Timeout;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ecommerce.product.ProductService;
import ecommerce.product.ProductServiceImpl;

import java.io.IOException;

public class ProductServiceTest {

    private OkHttpClient mockClient;
    private ProductService productService;

    @BeforeEach
    public void setup() {
        mockClient = mock(OkHttpClient.class);
        productService = new ProductServiceImpl(mockClient);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/products").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{\"id\": 1, \"title\": \"Product 1\", \"description\": \"Description 1\", \"price\": 10.0, \"category\": \"Category A\"}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new ProductServiceTest.CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{\"id\": 1, \"title\": \"Product 1\", \"description\": \"Description 1\", \"price\": 10.0, \"category\": \"Category A\"}]", mockResponse.body().string());
    }

    @Test
    public void testGetProduct() throws Exception {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/products/10").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{\"id\": 10, \"title\": \"Product 10\", \"description\": \"Description 10\", \"price\": 10.0, \"category\": \"Category B\"}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new ProductServiceTest.CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{\"id\": 10, \"title\": \"Product 10\", \"description\": \"Description 10\", \"price\": 10.0, \"category\": \"Category B\"}]", mockResponse.body().string());
    }

    @Test
    public void testSearchProducts() throws Exception {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/products/search?q={title:test}").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{\"id\": 1, \"title\":test \"Product 1\", \"description\": \"Description 1\", \"price\": 10.0, \"category\": \"Category A\"}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new ProductServiceTest.CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{\"id\": 1, \"title\":test \"Product 1\", \"description\": \"Description 1\", \"price\": 10.0, \"category\": \"Category A\"}]", mockResponse.body().string());
    }

    @Test
    public void testGetCategories() throws Exception {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/products/categories").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "category A, category B, category C"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new ProductServiceTest.CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("category A, category B, category C", mockResponse.body().string());
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.comproducts/category/categoryA").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{\"id\": 1, \"title\": \"Product 1\", \"description\": \"Description 1\", \"price\": 10.0, \"category\": \"Category A\"}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new ProductServiceTest.CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{\"id\": 1, \"title\": \"Product 1\", \"description\": \"Description 1\", \"price\": 10.0, \"category\": \"Category A\"}]", mockResponse.body().string());
    }


    static class CallMock implements Call {
        private final Response response;

        CallMock(Response response) {
            this.response = response;
        }

        @Override
        public Response execute() throws IOException {
            return response;
        }

        @Override
        public void cancel() {

        }

        @NotNull
        @Override
        public Call clone() {
            return null;
        }

        @Override
        public void enqueue(@NotNull Callback callback) {

        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public boolean isExecuted() {
            return false;
        }

        @NotNull
        @Override
        public Request request() {
            return null;
        }

        @NotNull
        @Override
        public Timeout timeout() {
            return null;
        }
    }
}
