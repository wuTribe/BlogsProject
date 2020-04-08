package wu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    private int aid; // 主键id
    private String path; // 文件所在路径
    private String fileName; // 文件的名称
    private Date time; // 文件创建的名称
    private int auid; // 文章所属的用户
    private String title; // 文章的标题
    private String content; // 文章的内容
}
