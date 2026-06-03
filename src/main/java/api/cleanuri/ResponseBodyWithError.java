package api.cleanuri;

public class ResponseBodyWithError {

    private String error;

    public ResponseBodyWithError() {
    }

    public ResponseBodyWithError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
