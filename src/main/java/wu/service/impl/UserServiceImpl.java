package wu.service.impl;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wu.bean.Article;
import wu.bean.User;
import wu.dao.BlogsUserMapper;
import wu.service.UserService;

@Service
@NoArgsConstructor
public class UserServiceImpl implements UserService {
    // 获取数据库操作对象
    private BlogsUserMapper userDAO;

    @Autowired
    public UserServiceImpl(BlogsUserMapper userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public int addUser(User user) {
        return userDAO.addUser(user);
    }

    @Override
    public User queueUserByUsername(User user) {
        return userDAO.queueUserByUsername(user);
    }

    @Override
    public User queueUserByUid(User user) {
        User rsUser = queueUserByUsername(user);

        // 查询的对象不为空时，并且密码符合，返回查询到的对象
        if (rsUser != null && user != null && rsUser.getPassword().equals(user.getPassword())) {
            return rsUser;
        }

        return null;
    }

    @Override
    public User queueUserByAuid(Article article) {
        User user = new User(article.getAuid(), null, null, null, null);
        return userDAO.queueUserByUid(user);
    }
}
