package example.dell.jd.EventBus;

/**
 * Created by Dell on 2017/12/3.
 */

public class MessageEvent {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MessageEvent(String name) {
        this.name = name;
    }
}
