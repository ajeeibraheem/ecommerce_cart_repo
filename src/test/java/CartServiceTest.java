import ecommerce.cart.CartService;
import ecommerce.cart.CartServiceImpl;
import okhttp3.*;
import okio.Timeout;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    private OkHttpClient mockClient;
    private CartService cartService;


    @Before
    public void setUp() {
        mockClient = mock(OkHttpClient.class);
        cartService = new CartServiceImpl(mockClient);
    }



    @Test
    public void testGetAllCarts() throws IOException {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/carts").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{\"id\": 1, \"userId\": 100, \"items\": [{\"productId\": 101, \"quantity\": 2}]}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{\"id\": 1, \"userId\": 100, \"items\": [{\"productId\": 101, \"quantity\": 2}]}]", mockResponse.body().string());
    }

    @Test
    public void testGetCart() throws IOException {
        // Mock the response
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/carts/1").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{\"id\": 1, \"userId\": 100, \"items\": [{\"productId\": 101, \"quantity\": 2}]}, {...}, {...}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{\"id\": 1, \"userId\": 100, \"items\": [{\"productId\": 101, \"quantity\": 2}]}, {...}, {...}]", mockResponse.body().string());
    }

    @Test
    public void testGetUserCarts() throws IOException {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/carts/user/5").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{\"id\": 1, \"userId\": 5, \"items\": [{\"productId\": 101, \"quantity\": 2}]}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{\"id\": 1, \"userId\": 5, \"items\": [{\"productId\": 101, \"quantity\": 2}]}]", mockResponse.body().string());
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
