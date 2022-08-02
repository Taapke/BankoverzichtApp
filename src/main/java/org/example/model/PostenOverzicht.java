package org.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Taapke Bergsma <t.bergsma@st.hanze.nl>
 */

public class PostenOverzicht {
    List<Post> postList;

    public PostenOverzicht() {
        this.postList = new ArrayList<>();
    }

    public void addPost(Post post) {
        postList.add(post);
    }

    public List<Post> getPostList() {
        return postList;
    }
}
