package api.cleanuri;

public class ResponseBody {

    private String result_url;

    public ResponseBody() {
    }

    public ResponseBody(String result_url) {
        this.result_url = result_url;
    }

    public String getResultUrl() {
        return result_url;
    }

    public void setResult_url(String result_url) {
        this.result_url = result_url;
    }
}
