package demo.sb.ssjwt.sev;

import demo.sb.ssjwt.mod.vo.SignUpVo;
import demo.sb.ssjwt.mod.vo.UserInfoVo;

import java.util.List;

/**
 * 服务接口：用户信息访问
 */
public interface UserInfoSev {

    /**
     * 查询匹配昵称的用户
     *
     * @param nickname 昵称
     * @return 用户信息的值对象
     */
    List<UserInfoVo> findByNicknameLike(String nickname);

    /**
     * 保存用户信息
     *
     * @param accountId 关联账户的id
     * @param info      用户信息
     * @return 用户数据的id
     */
    Long saveUserInfo(Long accountId, SignUpVo info);
}
