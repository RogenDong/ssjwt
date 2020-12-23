package demo.sb.ssjwt.ds;


import demo.sb.ssjwt.mod.dao.UserInfoDao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * 用户信息表数据访问
 */
public interface UserInfoDs extends CrudRepository<UserInfoDao, Long> {

    /**
     * 模糊匹配用户
     *
     * @param nickname 昵称（传进来前需要自己加“%”）
     * @return 昵称符合搜索条件的用户
     */
    List<UserInfoDao> findByNicknameLike(String nickname);

    /**
     * 以账户 id 查询用户信息
     *
     * @param account_id 账户 id
     * @return 信息实体的 Optional 包装
     */
    Optional<UserInfoDao> getByAccount_Id(Long account_id);

    /**
     * 以登录号查询用户信息
     *
     * @param account_codename 登录号
     * @return 信息实体的 Optional 包装
     */
    Optional<UserInfoDao> getByAccount_Codename(String account_codename);
}
