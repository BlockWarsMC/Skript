
package ch.njol.skript.doc.skunity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Result {

    @SerializedName("elements")
    @Expose
    private List<Element> elements = null;
    @SerializedName("votes")
    @Expose
    private List<Object> votes = null;
    @SerializedName("starred")
    @Expose
    private List<Object> starred = null;
    @SerializedName("addons")
    @Expose
    private List<Addon> addons = null;

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public List<Object> getVotes() {
        return votes;
    }

    public void setVotes(List<Object> votes) {
        this.votes = votes;
    }

    public List<Object> getStarred() {
        return starred;
    }

    public void setStarred(List<Object> starred) {
        this.starred = starred;
    }

    public List<Addon> getAddons() {
        return addons;
    }

    public void setAddons(List<Addon> addons) {
        this.addons = addons;
    }

}
