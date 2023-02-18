
package ch.njol.skript.doc.skunity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Element {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("doc")
    @Expose
    private String doc;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("addon")
    @Expose
    private String addon;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("pattern")
    @Expose
    private String pattern;
    @SerializedName("plugin")
    @Expose
    private String plugin;
    @SerializedName("eventvalues")
    @Expose
    private String eventvalues;
    @SerializedName("changers")
    @Expose
    private String changers;
    @SerializedName("returntype")
    @Expose
    private String returntype;
    @SerializedName("is_array")
    @Expose
    private String isArray;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("reviewed")
    @Expose
    private boolean reviewed;
    @SerializedName("versions")
    @Expose
    private String versions;
    @SerializedName("snippets")
    @Expose
    private List<Object> snippets = null;
    @SerializedName("searchsettings_bonus")
    @Expose
    private int searchsettingsBonus;
    @SerializedName("alphaid")
    @Expose
    private int alphaid;
    @SerializedName("examples")
    @Expose
    private List<Example> examples = null;
    @SerializedName("element_info")
    @Expose
    private List<Object> elementInfo = null;
    @SerializedName("starred")
    @Expose
    private boolean starred;
    @SerializedName("type")
    @Expose
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPlugin() {
        return plugin;
    }

    public void setPlugin(String plugin) {
        this.plugin = plugin;
    }

    public String getEventvalues() {
        return eventvalues;
    }

    public void setEventvalues(String eventvalues) {
        this.eventvalues = eventvalues;
    }

    public String getChangers() {
        return changers;
    }

    public void setChangers(String changers) {
        this.changers = changers;
    }

    public String getReturntype() {
        return returntype;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
    }

    public String getIsArray() {
        return isArray;
    }

    public void setIsArray(String isArray) {
        this.isArray = isArray;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isReviewed() {
        return reviewed;
    }

    public void setReviewed(boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public List<Object> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<Object> snippets) {
        this.snippets = snippets;
    }

    public int getSearchsettingsBonus() {
        return searchsettingsBonus;
    }

    public void setSearchsettingsBonus(int searchsettingsBonus) {
        this.searchsettingsBonus = searchsettingsBonus;
    }

    public int getAlphaid() {
        return alphaid;
    }

    public void setAlphaid(int alphaid) {
        this.alphaid = alphaid;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public List<Object> getElementInfo() {
        return elementInfo;
    }

    public void setElementInfo(List<Object> elementInfo) {
        this.elementInfo = elementInfo;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
