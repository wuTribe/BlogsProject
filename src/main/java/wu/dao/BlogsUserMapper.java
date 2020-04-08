package wu.dao;

import org.apache.ibatis.annotations.Param;
import wu.bean.User;

public interface BlogsUserMapper {
    /**
     * 向数据库中增加用户（注册）
     *
     * @param user 用户的信息
     * @return 影响的行数
     */
    int addUser(@Param("user") User user);

    /**
     * 根据用户名查询用户信息
     *
     * @param user 用户填写的信息
     * @return 如果查询成功，返回该用户的所有详细信息，否则返回null
     */
    User queueUserByUsername(@Param("user") User user);


    /**
     * 根据id查询用户详细信息
     *
     * @param user 封装的用户信息
     * @return 详细的用户信息
     */
    User queueUserByUid(@Param("user") User user);
}
