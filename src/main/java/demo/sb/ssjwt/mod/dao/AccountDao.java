package demo.sb.ssjwt.mod.dao;

import demo.sb.ssjwt.common.mod.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 账户类
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_account")
@EqualsAndHashCode(callSuper = true)
public class AccountDao extends BaseDao {

    /**
     * 登录号
     */
    private String codename;

    /**
     * 登录密码
     */
    private String password;
}
