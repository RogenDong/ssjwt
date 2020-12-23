package demo.sb.ssjwt.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 权限角色
 */
@Getter
@AllArgsConstructor
public enum RuleEnum {
    USER("用户"),
    PASSER("游客"),
    OPERATOR("运营"),
    ADMIN("管理");

    /**
     * 中文
     */
    private final String desc;

    /**
     * 对象转 json 时，取枚举的序号
     *
     * @return 枚举的序号
     * @see Enum#ordinal()
     */
    @JsonValue
    public int getOrdinal() {
        return super.ordinal();
    }

    /**
     * 通过序号获取枚举
     *
     * @param code 序号
     * @return 枚举，找不到时返回null
     */
    @JsonCreator
    public static RuleEnum creator(Integer code) {
        for (RuleEnum e : values()) {
            if (e.ordinal() == code) {
                return e;
            }
        }
        return null;
    }
}
