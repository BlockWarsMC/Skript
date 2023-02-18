
package ch.njol.skript.doc.skunity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class SkUnityElements {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("result")
    @Expose
    private Result result;
    @SerializedName("extra_one")
    @Expose
    private String extraOne;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getExtraOne() {
        return extraOne;
    }

    public void setExtraOne(String extraOne) {
        this.extraOne = extraOne;
    }

}
