package wu.bean;

public class ResultInfo {
    private boolean flag; // 是否为错误信息
    private String errorMsg; // 发生错误信息对象
    private Object data; // 返回数据结果

    @Override
    public String toString() {
        return "ResultInfo{" +
                "flag=" + flag +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public ResultInfo() {
    }

    public ResultInfo(boolean flag, String errorMsg, Object data) {
        this.flag = flag;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
