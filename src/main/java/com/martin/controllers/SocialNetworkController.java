package com.martin.controllers;

import com.martin.exceptions.ErrorDetails;
import com.martin.exceptions.RestApiException;
import com.martin.domain.Post;
import com.martin.domain.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@RestController
public class SocialNetworkController {

    private static HashMap<String, User> users = new HashMap<>();

    /**
     * Get the messages for a specified user
     * @param username The specified user
     * @return List of posts by the user
     * @throws RestApiException If the user doesn't exist throw an exception
     */
    @GetMapping("/messages/{username}")
    public ResponseEntity<Collection<Post>> getMessages(@PathVariable String username) throws RestApiException {
        final User user = getUser(username);
        final Collection<Post> posts = user.getPosts();

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Gets the timeline of the followed users for the specified user
     * @param username The current user
     * @return A list of posts by all followed users
     * @throws RestApiException If the current user does not exist throw an exception
     */
    @GetMapping("/timeline/{username}")
    public ResponseEntity<ArrayList<Post>> getTimeline(@PathVariable String username) throws RestApiException {
        final User user = getUser(username);

        final HashSet<String> followers = user.getFollowedUsernames();
        final ArrayList<Post> posts = new ArrayList<>();

        for(String followerUsername: followers){
            User follower = getUser(followerUsername);
            posts.addAll(follower.getPosts());
        }

        posts.sort(Comparator.comparing(Post::getTimestamp));

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Posts a message for a user
     * @param post Contains the current user and their message
     * @throws RestApiException If the message is greater than the maximum 140 characters.
     */
    @PostMapping("/postMessage")
    public void postMessage(@RequestBody Post post) throws RestApiException{
        final String userMessage = post.getMessage();
        if(userMessage.length() > 140){
            throw new RestApiException(HttpStatus.BAD_REQUEST, "Maximum character length is 140 characters");
        }

        final String username = post.getUsername();
        final User user = getUserOrCreate(username);
        final Collection<Post> posts = user.getPosts();

        posts.add(post);
        user.setPosts(posts);

        users.put(username, user);
    }

    /**
     * Follows a specified user.
     * @param username Username of the current user
     * @param followUser User that is to be followed
     * @throws RestApiException If the user provided obviously can't be followed such as they don't exist.
     */
    @PutMapping("user/{username}/follow")
    public void followUser(@PathVariable String username, @RequestBody User followUser) throws RestApiException {
        final String followUsername = followUser.getUsername();
        final User user = getUser(username);

        if(username.equals(followUsername)){
            throw new RestApiException(HttpStatus.BAD_REQUEST, "Users cannot follow themselves");
        } else if (!users.containsKey(followUsername)) {
            throw new RestApiException(HttpStatus.BAD_REQUEST, "User " + followUsername + " doesn't exist");
        }

        user.addFollower(followUsername);
    }

    /**
     * Gets a user from a specified username if it exists otherwise creates a new user
     * @param username The username of the user requested
     * @return The user with the corresponding username
     */
    private User getUserOrCreate(final String username){
        return users.getOrDefault(username, new User(username));
    }

    /**
     * Gets a user from a specified username if it exists otherwise throw an exception
     * @param username The username of the user requested
     * @return The user with the corresponding username
     * @throws RestApiException If the user doesn't exist throw an exception
     */
    private User getUser(final String username) throws RestApiException {
        if(!users.containsKey(username)){
            throw new RestApiException(HttpStatus.BAD_REQUEST, "User " + username + " does not exist");
        }

        return users.get(username);
    }

    /**
     * Provides an error code and details when an exception is thrown
     * @param e The thrown exception
     * @param request The request that was made
     * @return Details of the exception thrown and a HTTP status
     */
    @ExceptionHandler(RestApiException.class)
    private ResponseEntity<ErrorDetails> handleRestApiException(RestApiException e, WebRequest request){
        final ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(), request.getDescription(false));
        final HttpStatus value = e.getStatusCode();
        return new ResponseEntity<> (errorDetails, value);
    }
}
