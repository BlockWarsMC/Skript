
package ch.njol.skript.doc.skunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Addon {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("xf_id")
    @Expose
    private String xfId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("addon_name")
    @Expose
    private String addonName;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("forums_resource_id")
    @Expose
    private String forumsResourceId;
    @SerializedName("hidden")
    @Expose
    private String hidden;
    @SerializedName("colour")
    @Expose
    private String colour;
    @SerializedName("autoupdate")
    @Expose
    private String autoupdate;
    @SerializedName("versions")
    @Expose
    private String versions;
    @SerializedName("type")
    @Expose
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getXfId() {
        return xfId;
    }

    public void setXfId(String xfId) {
        this.xfId = xfId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddonName() {
        return addonName;
    }

    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getForumsResourceId() {
        return forumsResourceId;
    }

    public void setForumsResourceId(String forumsResourceId) {
        this.forumsResourceId = forumsResourceId;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getAutoupdate() {
        return autoupdate;
    }

    public void setAutoupdate(String autoupdate) {
        this.autoupdate = autoupdate;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
