package wu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private int uid; // 用户id
    private String username; // 用户名
    private String password; // 用户密码
    private ArrayList<Article> articles; // 文章
    private Date time; // 文章时间
}
