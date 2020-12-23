package demo.sb.ssjwt.mod.dao;

import demo.sb.ssjwt.common.enums.RuleEnum;
import demo.sb.ssjwt.common.mod.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 权限角色与账户的映射关系
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_rule_mapping")
@EqualsAndHashCode(callSuper = true)
public class RuleMappingDao extends BaseDao {

    /**
     * 关联账户<br>
     * 查询时懒加载<br>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", updatable = false)
    private AccountDao account;

    /**
     * 权限角色
     */
    @Enumerated
    @Column(name = "rule_code")
    private RuleEnum rule;
}
