
package ch.njol.skript.doc.skunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Example {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("example")
    @Expose
    private String example;
    @SerializedName("forid")
    @Expose
    private String forid;
    @SerializedName("votes")
    @Expose
    private String votes;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("xf_id")
    @Expose
    private String xfId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("upvote")
    @Expose
    private boolean upvote;
    @SerializedName("downvote")
    @Expose
    private boolean downvote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getForid() {
        return forid;
    }

    public void setForid(String forid) {
        this.forid = forid;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getXfId() {
        return xfId;
    }

    public void setXfId(String xfId) {
        this.xfId = xfId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isUpvote() {
        return upvote;
    }

    public void setUpvote(boolean upvote) {
        this.upvote = upvote;
    }

    public boolean isDownvote() {
        return downvote;
    }

    public void setDownvote(boolean downvote) {
        this.downvote = downvote;
    }

}
