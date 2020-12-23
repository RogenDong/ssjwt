package demo.sb.ssjwt.mod.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import demo.sb.ssjwt.common.enums.GenderEnum;
import demo.sb.ssjwt.mod.dao.UserInfoDao;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

/**
 * 值对象：用户信息
 */
@Data
@NoArgsConstructor
public class UserInfoVo {

    /**
     * 站龄
     */
    String duration = "--";
    String nickname;
    GenderEnum gender;

    private static final Logger log = LoggerFactory.getLogger(UserInfoVo.class);

    @JsonCreator
    public UserInfoVo(String nickname, GenderEnum gender) {
        this.gender = gender;
        this.nickname = nickname;
    }

    public UserInfoVo(UserInfoDao dao) {
        gender = dao.getGender();
        nickname = dao.getNickname();
        setDuration(dao.getSignUpDate());
    }

    /**
     * 用户注册时间转换为站龄
     *
     * @param signUpDate 注册日期
     */
    public void setDuration(LocalDate signUpDate) {
        int year, month, day;
        LocalDate today = LocalDate.now();
        year = today.getYear() - signUpDate.getYear();
        month = today.getMonthValue() - signUpDate.getMonthValue();
        if (month < 0) {
            if (year > 0) {
                month += 12;
                double tmp = year - 1 + month / 12d;
                duration = tmp + "年";
            } else {
                log.error("用户 {} 的注册日期异常！", nickname);
                return;
            }
        } else if (month > 0) {
            if (year > 0) {
                double tmp = year + month / 12d;
                duration = tmp + "年";
            } else {
                duration = month + "月";
            }
        }
        if (year == 0) {
            day = today.getDayOfMonth() - signUpDate.getDayOfMonth();
            int tmp = day / 7;
            if (tmp > 0) {
                duration = tmp + "周";
            } else {
                if (day < 1) {
                    day = 1;
                }
                duration = day + "天";
            }
        } else {
            if (year < 0) {
                log.error("用户 {} 的注册日期异常！", nickname);
                return;
            }
            duration = year + "年";
        }
    }
}
