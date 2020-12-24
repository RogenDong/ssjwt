package demo.sb.ssjwt.mod.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import demo.sb.ssjwt.common.enums.RuleEnum;
import demo.sb.ssjwt.mod.dao.RuleMappingDao;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AccountDetailVo implements UserDetails {

    private final String codename;
    @JsonIgnore
    private final String password;
    private final List<RuleMappingDao> ruleMappings;

    public String getRules() {
        return ruleMappings.stream()
                .map(RuleMappingDao::getRule)
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruleMappings.stream()
                .map(RuleMappingDao::getRule)
                .filter(Objects::nonNull)
                .map(RuleEnum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return codename;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
