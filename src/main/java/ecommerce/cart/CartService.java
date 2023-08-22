package ecommerce.cart;

import java.util.List;

// Note: the generic type parameter T is used to represent the type of the cart.
public interface CartService {

	/*
	 * Get all carts of TestMart
	 * API endpoint to get data: https://dummyjson.com/carts
	 */
	List<Cart> getAllCarts();

	/*
	 * Get a single cart
	 * API endpoint to get data: https://dummyjson.com/carts/{cartId}
	 */
	Cart getCart(Integer cartId);

	/*
	 * Get carts of a user
	 * API endpoint to get data: https://dummyjson.com/carts/user/{userIds}
	 */
	List<Cart> getUserCarts(Integer userId);
}
