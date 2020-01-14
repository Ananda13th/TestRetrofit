package example.com.testrecycleview.Model;

public class BaseResponse {

    private String error_code;
    private String error_messsage;
    private String epoch;

    public String getErrorCode() {
        return error_code;
    }

    public void setErrorCode(String error_code) {
        this.error_code = error_code;
    }

    public String getErrorMesssage() {
        return error_messsage;
    }

    public void setErrorMesssage(String error_messsage) {
        this.error_messsage = error_messsage;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }
}
