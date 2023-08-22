package ecommerce;

import ecommerce.cart.Cart;
import ecommerce.cart.CartItem;
import ecommerce.cart.CartService;
import ecommerce.product.Product;
import ecommerce.product.ProductService;
import ecommerce.user.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class  AbstractTestMartAppFeatures {

    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;

    public AbstractTestMartAppFeatures(ProductService productService, UserService userService, CartService cartService) {
        this.productService = productService;
        this.userService = userService;
        this.cartService = cartService;
    }

    public void getProductTitlesByWorseRating(double rating) {
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            if (product.getRating() <= rating) {
                System.out.println("Product Title: " + product.getTitle());
            }
        }
    }

    public Cart getCartWithHighestTotal() {
        List<Cart> carts = cartService.getAllCarts();
        return carts.stream()
                .max(Comparator.comparingDouble(this::calculateCartTotal))
                .orElse(null);
    }

    public Cart getCartWithLowestTotal() {
        List<Cart> carts = cartService.getAllCarts();
        return carts.stream()
                .min(Comparator.comparingDouble(this::calculateCartTotal))
                .orElse(null);
    }

    public List<Product> addProductImagesToUserCart(Integer userId) {
        List<Cart> userCarts = cartService.getUserCarts(userId);
        if (!userCarts.isEmpty()) {
            Cart userCart = userCarts.get(userId);
            for (CartItem cartItem : userCart.getItems()) {
                enrichProductWithImages(cartItem.getProductId());
            }
            return userCart.getItems().stream()
                    .map(cartItem -> productService.getProduct(cartItem.getProductId()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private double calculateCartTotal(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(this::calculateCartItemTotal)
                .sum();
    }

    private double calculateCartItemTotal(CartItem cartItem) {
        Product product = productService.getProduct(cartItem.getProductId());
        return product.getPrice() * cartItem.getQuantity();
    }

    private void enrichProductWithImages(int productId) {
        Product product = productService.getProduct(productId);
        List<String> images = fetchProductImages(productId);
        product.setImages(images);
    }

    private List<String> fetchProductImages(int productId) {
        Product product = productService.getProduct(productId);
        return product.getImages();
    }
}
