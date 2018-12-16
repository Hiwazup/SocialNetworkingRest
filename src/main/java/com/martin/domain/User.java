package com.martin.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class User {
    private String username;
    private Collection<Post> posts = new ArrayList<>();
    private HashSet<String> followedUsernames = new HashSet<>();

    public User(){}

    public User(final String username){
        this.username = username;
    }

    public User(final String username, final Collection<Post> posts) {
        this.username = username;
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public Collection<Post> getPosts() {
        return posts;
    }

    public void setPosts(Collection<Post> posts) {
        this.posts = posts;
    }

    public HashSet<String> getFollowedUsernames() {
        return followedUsernames;
    }

    public void addPost(final Post post){
        posts.add(post);
    }

    public void addFollower(final String username){
        followedUsernames.add(username);
    }
}
