package example.dell.jd.net;

import java.io.IOException;

/**
 * Created by Dell on 2017/11/30.
 */

public interface CGSB<T> {
    void chengGong(T t);
    void shiBai(IOException e);
}
