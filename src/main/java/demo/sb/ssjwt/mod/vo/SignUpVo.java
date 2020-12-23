package demo.sb.ssjwt.mod.vo;

import demo.sb.ssjwt.common.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 值对象：注册参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SignUpVo extends SignVo {
    String nickname;
    GenderEnum gender;
}
