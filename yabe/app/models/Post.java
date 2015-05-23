package models;

/**
 * Created by PB5N0097 on 5/6/2015.
 */
import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Post extends Model {

    @Required
    public String title;

    @Required
    public Date postedAt;

    @Lob
    @Required
    @MaxSize(10000)
    public String content;

    @Required
    @ManyToOne
    public User author;

    @OneToMany(mappedBy="post", cascade=CascadeType.ALL)
    public List<Comment> comments;

    @ManyToMany(cascade=CascadeType.PERSIST)
    public Set<Tag> tags;

    public Post(User author, String title, String content) {
        this.comments = new ArrayList<Comment>();
        this.tags = new TreeSet<Tag>();
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
    }
    public String toString(){
        return title;
    }
    public Post addComment(String author, String content) {
        Comment newComment = new Comment(this, author, content).save();
        this.comments.add(newComment);
        this.save();
        return this;
    }

    public Post previous(){
        return Post.find("postedAt < ? ORDER BY postedAt DESC",postedAt).first();
    }

    public Post next(){
        return Post.find("postedAt > ? ORDER BY postedAt ASC",postedAt).first();
    }

    public Post tagItWith(String name){
        tags.add(Tag.findOrCreateByName(name));
        return this;
    }

    /*
    public static List<Post> findTaggedWith(String tag) {
        return Post.find(
                "SELECT DISTINCT p FROM Post p JOIN p.tags AS t WHERE t.name = ?", tag).fetch();
    }
    */
    public static List<Post> findTaggedWith(String... tags) {
        return Post.find(
                "SELECT DISTINCT p FROM Post p join p.tags AS t WHERE t.name in (:tags) GROUP BY p.id, p.author, p.title, p.content,p.postedAt HAVING count(t.id) = :size"
        ).bind("tags", tags).bind("size", tags.length).fetch();
    }
}
