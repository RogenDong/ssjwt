package demo.sb.ssjwt.common.mod;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 数据对象的基础
 */
@Data
@MappedSuperclass
public abstract class BaseDao {
    /**
     * 主键<br>
     * 数据库自增长<br>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
