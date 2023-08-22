import ecommerce.AbstractTestMartAppFeatures;
import ecommerce.cart.Cart;
import ecommerce.cart.CartItem;
import ecommerce.cart.CartService;
import ecommerce.product.Product;
import ecommerce.product.ProductService;
import ecommerce.user.User;
import ecommerce.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AbstractTestMartAppFeaturesTest {

    private ProductService productService;
    private UserService userService;
    private CartService cartService;

    private AbstractTestMartAppFeatures appFeatures;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        userService = mock(UserService.class);
        cartService = mock(CartService.class);

        appFeatures = new AbstractTestMartAppFeatures(productService, userService, cartService);
    }

    @Test
    public void testGetProductTitlesByWorseRating() {
        List<Product> products = Arrays.asList(
                new Product(1, "Product 1", "product 1 details", 32.7,"household",4.5,null),
                new Product(2, "Product 2", "product 2 details", 15.99,"cloth",3.5,null),
                new Product(3, "Product 3", "product 3 details", 23.3,"food",4.5,null)
        );

        when(productService.getAllProducts()).thenReturn(products);
        appFeatures.getProductTitlesByWorseRating(3.5);
        assertEquals(1, products.stream().findFirst().get().getId());
        assertEquals("household", products.stream().findFirst().get().getCategory());
        assertEquals(4.5, products.stream().findFirst().get().getRating());
    }

    @Test
    public void testGetCartWithHighestTotal() {
        List<Cart> carts = Arrays.asList(
                new Cart(1, 1, Arrays.asList(new CartItem(1, 2), new CartItem(2, 3))),
                new Cart(2, 1, Arrays.asList(new CartItem(1, 3), new CartItem(3, 1))),
                new Cart(3, 2, Arrays.asList(new CartItem(2, 1)))
        );

        when(cartService.getAllCarts()).thenReturn(carts);
        assertEquals(1, carts.stream().findFirst().get().getId());
        assertEquals(1, carts.stream().findFirst().get().getItems().stream().findFirst().get().getProductId());
        assertEquals(2, carts.stream().findFirst().get().getItems().stream().findFirst().get().getQuantity());
    }

    @Test
    public void testGetCartWithLowestTotal() {
        List<Cart> carts = Arrays.asList(
                new Cart(1, 1, Arrays.asList(new CartItem(1, 2), new CartItem(2, 3))),
                new Cart(2, 1, Arrays.asList(new CartItem(1, 3), new CartItem(3, 1))),
                new Cart(3, 2, Arrays.asList(new CartItem(2, 1)))
        );

        when(cartService.getAllCarts()).thenReturn(carts);
        assertEquals(3, carts.stream().count());
        assertEquals(1, carts.stream().findFirst().get().getUserId());
    }

    @Test
    public void testAddProductImagesToUserCart() {
        User user = new User(1, "username", "email", "Full Name", "1234567890", "Address");

        List<CartItem> cartItems = Arrays.asList(new CartItem(1, 2), new CartItem(2, 3));
        Cart cart = new Cart(1, user.getId(), cartItems);

        when(cartService.getUserCarts(user.getId())).thenReturn(Arrays.asList(cart));
        when(productService.getProduct(1)).thenReturn(new Product(1, "Product 1", "product 1 details", 32.7,"household",4.5,null));
        when(productService.getProduct(2)).thenReturn(new Product(2, "Product 2", "product 2 details", 15.99,"cloth",3.2,null));
        assertEquals("username", user.getUsername());
        assertEquals(1, cartItems.stream().findFirst().get().getProductId());

    }
}
