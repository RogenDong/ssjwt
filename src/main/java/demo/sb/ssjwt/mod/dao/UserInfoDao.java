package demo.sb.ssjwt.mod.dao;

import demo.sb.ssjwt.common.enums.GenderEnum;
import demo.sb.ssjwt.common.mod.BaseDao;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 用户信息
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_user_info")
@EqualsAndHashCode(callSuper = true)
public class UserInfoDao extends BaseDao {

    /**
     * 关联账户<br>
     * 查询时懒加载<br>
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", updatable = false)
    private AccountDao account;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    @Enumerated
    private GenderEnum gender = GenderEnum.SECRET;

    /**
     * 注册日期
     */
    @Column(name = "sign_up_date")
    private LocalDate signUpDate = LocalDate.now();
}
