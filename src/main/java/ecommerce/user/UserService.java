package ecommerce.user;

import java.util.List;

// Note: the generic type parameter U is used to represent the type of the user.
public interface UserService {

	/*
	 * Get all users of TestMart
	 * API endpoint to get data: https://dummyjson.com/users
	 */
	List<User> getAllUsers();

	/*
	 * Get a single user
	 * API endpoint to get data: https://dummyjson.com/users/{userId}
	 */
	User getUser(Integer userId);

	/*
	 * Search users
	 * API endpoint to get data: https://dummyjson.com/users/search?q={query}
	 */
	List<User> searchUsers(String query);
}
