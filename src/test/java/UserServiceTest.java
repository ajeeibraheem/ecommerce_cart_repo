import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import ecommerce.user.UserService;
import ecommerce.user.UserServiceImpl;
import okhttp3.*;
import okio.Timeout;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UserServiceTest {

    private OkHttpClient mockClient;
    private UserService userService;

    @BeforeEach
    public void setup() {
        mockClient = mock(OkHttpClient.class);
        userService = new UserServiceImpl(mockClient);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/users").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{" +
                        "\"id\": 1," +
                        "\"username\": \"user1\"," +
                        "\"email\": \"user1@example.com\"," +
                        "\"fullName\": \"User One\"," +
                        "\"phone\": \"1234567890\"," +
                        "\"address\": \"123 Street\"" +
                        "}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{" +
                "\"id\": 1," +
                "\"username\": \"user1\"," +
                "\"email\": \"user1@example.com\"," +
                "\"fullName\": \"User One\"," +
                "\"phone\": \"1234567890\"," +
                "\"address\": \"123 Street\"" +
                "}]", mockResponse.body().string());
    }

    @Test
    public void testGetUser() throws Exception {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/users/10").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{" +
                        "\"id\": 10," +
                        "\"username\": \"user1\"," +
                        "\"email\": \"user1@example.com\"," +
                        "\"fullName\": \"User One\"," +
                        "\"phone\": \"1234567890\"," +
                        "\"address\": \"123 Street\"" +
                        "}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{" +
                "\"id\": 10," +
                "\"username\": \"user1\"," +
                "\"email\": \"user1@example.com\"," +
                "\"fullName\": \"User One\"," +
                "\"phone\": \"1234567890\"," +
                "\"address\": \"123 Street\"" +
                "}]", mockResponse.body().string());
    }

    @Test
    public void testSearchUsers() throws Exception {
        var mockResponse = new Response.Builder()
                .request(new Request.Builder().url("https://dummyjson.com/products/search?q={username:user1}").build())
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .body(ResponseBody.create(MediaType.get("application/json"), "[{" +
                        "\"id\": 10," +
                        "\"username\": \"user1\"," +
                        "\"email\": \"user1@example.com\"," +
                        "\"fullName\": \"User One\"," +
                        "\"phone\": \"1234567890\"," +
                        "\"address\": \"123 Street\"" +
                        "}]"))
                .message("OK")
                .build();
        when(mockClient.newCall(any(Request.class))).thenReturn(new CallMock(mockResponse));
        assertTrue(mockResponse.isSuccessful());
        assertEquals("application/json; charset=utf-8", mockResponse.body().contentType().toString());
        assertEquals("[{" +
                "\"id\": 10," +
                "\"username\": \"user1\"," +
                "\"email\": \"user1@example.com\"," +
                "\"fullName\": \"User One\"," +
                "\"phone\": \"1234567890\"," +
                "\"address\": \"123 Street\"" +
                "}]", mockResponse.body().string());
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
