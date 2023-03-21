package apiCurrencies;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RequestRateBody {
    private String app_id="975acd4d4a4d4d568baaf8a644074168";
    private String base;
    private String symbols;

    public String getAppId() {
        return app_id;
    }

    public void setAppId(String appId) {
        this.app_id = appId;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getSymbols() {
        return symbols;
    }

    public void setSymbols(String symbols) {
        this.symbols = symbols;
    }

    public RequestRateBody(String symbols) {
        this.symbols = symbols;
    }

}
