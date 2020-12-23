package demo.sb.ssjwt.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
    FEMALE("女性"),
    MALE("男性"),
    SECRET("保密");

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
    public static GenderEnum creator(Integer code) {
        for (GenderEnum e : values()) {
            if (e.ordinal() == code) {
                return e;
            }
        }
        return null;
    }
}
