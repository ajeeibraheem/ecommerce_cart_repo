package ecommerce.product;

import java.util.List;

// Note: the generic type parameters P and C are used to represent the types of the product and category, respectively
public interface ProductService {

	/*
	 * Get all products of TestMart
	 * API endpoint to get data: https://dummyjson.com/products
	 */
	List<Product> getAllProducts();

	/*
	 * Get all products of TestMart using parameters
	 * API endpoint to get data: https://dummyjson.com/products?limit={limit}&skip={skip}&select={comma separated
	 * fields of product}
	 */
	List<Product> getAllProducts(int limit, int skip, String... fields);

	/*
	 * Get a single product
	 * API endpoint to get data: https://dummyjson.com/products/{productId}
	 */
	Product getProduct(Integer productId);

	/**
	 * Search for products in TestMart
	 * API endpoint to get data: https://dummyjson.com/products/search?q={query}
	 */
	List<Product> searchProducts(String query);

	/*
	 * Get all products categories in TestMart
	 * API endpoint to get data: https://dummyjson.com/products/categories
	 */
	List<String> getCategories();

	/*
	 * Get all products of a category
	 * API endpoint to get data: https://dummyjson.com/products/category/{categoryName}
	 */
	List<Product> getProductsByCategory(String categoryName);
}
