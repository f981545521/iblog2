package cn.acyou.iblog.web;

import com.google.common.base.Preconditions;
import com.google.common.escape.Escaper;
import com.google.common.html.HtmlEscapers;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class XssStringTypeHandler extends BaseTypeHandler<String> {

    private static final Escaper XSS_ESCAPER = HtmlEscapers.htmlEscaper();
    //https://stackoverflow.com/questions/27820971/why-a-surrogate-java-regexp-finds-hypen-minus/27823404#27823404
    private static final Pattern EMOJI_PATTERN = Pattern.compile("[\ud800\udc00-\udbff\udfff\ud800-\udfff]");

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        Preconditions.checkArgument(!EMOJI_PATTERN.matcher(parameter).find(), parameter + " 暂不支持!");
        if (parameter.contains("&")) {
            parameter = StringEscapeUtils.unescapeHtml(parameter);
        }
        ps.setString(i, XSS_ESCAPER.escape(parameter));

    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return cs.getString(columnIndex);
    }
}
