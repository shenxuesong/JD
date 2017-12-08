package example.dell.jd.EventBus;

/**
 * Created by Dell on 2017/12/3.
 */

public class MessageEvent3 {
   private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public MessageEvent3(boolean check) {
        this.check = check;
    }
}
