package model;

public class BaseResponse {
    private Boolean success;
    private Object items;

    public BaseResponse() {}

    public BaseResponse(Boolean success, Object items) {
        this.success = success;
        this.items = items;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getItems() {
        return items;
    }

    public void setItems(Object items) {
        this.items = items;
    }
}
