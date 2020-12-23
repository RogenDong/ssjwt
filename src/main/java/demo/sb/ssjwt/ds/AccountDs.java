package demo.sb.ssjwt.ds;

import demo.sb.ssjwt.mod.dao.AccountDao;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * 账户表数据访问
 */
public interface AccountDs extends CrudRepository<AccountDao, Long> {

    /**
     * 获取账户
     * @param codename 登录号（登录号是唯一的）
     * @return 账户实体的 Optional 包装
     */
    Optional<AccountDao> getByCodename(String codename);
}
