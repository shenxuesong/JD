package example.dell.jd.EventBus;

/**
 * Created by Dell on 2017/12/3.
 */

public class MessageEvent1 {
    private  String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public MessageEvent1(String uid) {
        this.uid = uid;
    }
}
