package demo.sb.ssjwt.ds;

import demo.sb.ssjwt.mod.dao.RuleMappingDao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 权限映射表的数据访问
 */
public interface RuleMappingDs extends CrudRepository<RuleMappingDao, Long> {

    /**
     * 查询权限映射
     *
     * @param account_id 账户id
     * @return 映射关系
     */
    List<RuleMappingDao> findByAccount_Id(Long account_id);
}
