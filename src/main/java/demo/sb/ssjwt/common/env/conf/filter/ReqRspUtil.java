package demo.sb.ssjwt.common.env.conf.filter;

import demo.sb.ssjwt.common.env.cmpo.JacksonBean;
import demo.sb.ssjwt.common.mod.R;
import demo.sb.ssjwt.common.util.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 请求、响应的常用操作<br>
 * 只在当前包可用<br>
 */
class ReqRspUtil {
    private Logger log;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * 初始化请求或响应
     *
     * @param request     请求
     * @param response    响应
     * @param callerClass 调用处
     */
    static ReqRspUtil build(HttpServletRequest request, HttpServletResponse response, Class<?> callerClass) {
        ReqRspUtil it = new ReqRspUtil();
        it.log = LoggerFactory.getLogger(callerClass);
        it.response = response;
        it.request = request;
        return it;
    }

    /**
     * 解析请求体的数据对象
     *
     * @param converter 解析过程
     * @param defValue  默认值
     * @param <BT>      JSON 参数的转换类型
     * @param <RT>      目标类型
     * @return 请求参数的实体
     */
    <BT, RT> RT getByReq(Class<BT> reqBodyType, RT defValue, Function<BT, RT> converter) {
        if (request == null) {
            return defValue;
        }
        try (ServletInputStream ris = request.getInputStream()) {
            BT reqBody = JacksonBean.OM.readValue(ris, reqBodyType);
            return converter.apply(reqBody);
        } catch (Exception e) {
            if (log != null) {
                log.error("登录参数解析失败！");
                log.error(e.getMessage(), e);
            }
        }
        return defValue;
    }

    /**
     * 写入响应结果
     *
     * @param result 目标数据
     * @param and    写入时的附加操作
     */
    void writeRspResult(R<?> result, Consumer<HttpServletResponse> and) {
        if (response == null) {
            return;
        }
        response.setContentType(CommonConst.APPLICATION_JSON_UTF8);
        response.setCharacterEncoding(CommonConst.ENCODING_UTF8);
        if (and != null) {
            and.accept(response);
        }
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JacksonBean.OM.writeValueAsString(result));
            writer.flush();
        } catch (Exception e) {
            if (log != null) {
                log.error("响应结果写入失败！");
                log.error(e.getMessage(), e);
            }
        }
    }
}
