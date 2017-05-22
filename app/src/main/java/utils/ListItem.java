package utils;

import java.io.Serializable;

/**
 * Created by guoying on 2017/2/28.
 * Description：
 */

public interface ListItem extends Serializable{

    <T extends ListItem> T newObject();

}
