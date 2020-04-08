package wu.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultInfo implements Serializable {
    private boolean flag; // 是否为错误信息
    private String errorMsg; // 发生错误信息对象
    private Object data; // 返回数据结果
}
