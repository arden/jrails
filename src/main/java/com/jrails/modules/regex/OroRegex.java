package com.jrails.modules.regex;

import com.jrails.commons.utils.StringUtils;
import org.apache.oro.text.regex.*;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.LinkedHashMap;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-20 19:35:52
 */
public final class OroRegex {
    /**
     * 替换
     *
     * @param str
     * @param reg
     * @param str2
     * @param num
     * @return
     */
    public static String replace(String str, String reg, String str2, int num) {
        String result = str;
        if (num == 0) {
            num = Util.SUBSTITUTE_ALL;
        }
        try {
            String content = str;
            String ps1 = reg;
            PatternCompiler orocom = new Perl5Compiler();
            Pattern pattern1 = orocom.compile(ps1);
            PatternMatcher matcher = new Perl5Matcher();
            result = Util.substitute(matcher, pattern1, new Perl5Substitution(str2), content, num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 过滤Go标签
     *
     * @param content
     * @param params
     * @return
     */
    public static String parseGoContent(String content, String params) {
        String pattern = "(<\\s*go\\s+(?:[^\\s>]\\s*){0,})href\\s*=\\s*(\"|'|)([^\\2\\s>]*)\\2((?:\\s*[^\\s>]){0,}\\s*>)";
        return parseContent(content, pattern, params);
    }

    /**
     * 过滤A标签
     *
     * @param content
     * @param params
     * @return
     */
    public static String parseLinkContent(String content, String params) {
        String pattern = "(<\\s*a\\s+(?:[^\\s>]\\s*){0,})href\\s*=\\s*(\"|'|)([^\\2\\s>]*)\\2((?:\\s*[^\\s>]){0,}\\s*>)";
        return parseContent(content, pattern, params);
    }

    /**
     * 过滤form标签
     *
     * @param content
     * @param params
     * @return
     */
    public static String parseFormContent(String content, String params) {
        String pattern = "(<\\s*form\\s+(?:[^\\s>]\\s*){0,})action\\s*=\\s*(\"|'|)([^\\2\\s>]*)\\2((?:\\s*[^\\s>]){0,}\\s*>)";
        return parseContent(content, pattern, params);
    }

    /**
     * 同时过滤A和Go标签
     *
     * @param content
     * @param params
     * @return
     */
    public static String parseWapContent(String content, String params) {
        //String pattern = "(<\\s*go\\s+(?:[^\\s>]\\s*){0,}|<\\s*a\\s+(?:[^\\s>]\\s*){0,})href\\s*=\\s*(\"|'|)([^\\2\\s>]*)\\2((?:\\s*[^\\s>]){0,}\\s*>)";
        //String pattern = "(<\\s*option\\s+(?:[^\\s>]\\s*){0,}|<\\s*go\\s+(?:[^\\s>]\\s*){0,}|<\\s*a\\s+(?:[^\\s>]\\s*){0,})href\\s*=\\s*(\"|'|)([^\\2\\s>]*)\\2((?:\\s*[^\\s>]){0,}\\s*>)";
        String pattern = "(<\\s*option\\s+(?:[^\\s>]\\s*){0,}|<\\s*go\\s+(?:[^\\s>]\\s*){0,}|<\\s*frame\\s+(?:[^\\s>]\\s*){0,}|<\\s*form\\s+(?:[^\\s>]\\s*){0,}|<\\s*a\\s+(?:[^\\s>]\\s*){0,})[onpick|href|action|src]\\s*=\\s*(\"|'|)([^\\2\\s>]*)\\2((?:\\s*[^\\s>]){0,}\\s*>)";
        return parseContent(content, pattern, params);
    }

    /**
     * 同时过滤A和Go标签
     *
     * @param content
     * @param params
     * @return
     */
    public static String parseHtmlContent(String content, String params) {
        String pattern = "(<\\s*option\\s+(?:[^\\s>]\\s*){0,}|<\\s*go\\s+(?:[^\\s>]\\s*){0,}|<\\s*frame\\s+(?:[^\\s>]\\s*){0,}|<\\s*form\\s+(?:[^\\s>]\\s*){0,}|<\\s*a\\s+(?:[^\\s>]\\s*){0,})[onpick|href|action|src]\\s*=\\s*(\"|'|)([^\\2\\s>]*)\\2((?:\\s*[^\\s>]){0,}\\s*>)";
        return parseContent(content, pattern, params);
    }

    /**
     * 过滤指定内容
     *
     * @param content
     * @param pattern
     * @param params
     * @return
     */
    private static String parseContent(String content, String pattern, String params) {
        String orignContent = content;
        String linkTagPatternStr = pattern;
        String hrefPatternStr = "(src='([^\"]+)')|(src=\"([^\"]+)\")|(action=\"([^\"]+)\")|(action='([^\"]+)')|(href=\"([^\"]+)\")|(href='([^\"]+)')|(onpick=\"([^\"]+)\")|(onpick='([^\"]+)')";
        //String hrefPatternStr = "([href|action|src]\\s*=\\s*(\"|'|)([^\\2\\s>]*)\\2((?:\\s*[^\\s>]){0,}\\s*>))";
        PatternCompiler complier = new Perl5Compiler();
        PatternMatcher matcher = new Perl5Matcher();
        try {
            Pattern linkPattern = complier.compile(linkTagPatternStr, Perl5Compiler.CASE_INSENSITIVE_MASK);
            Pattern hrefPattern = complier.compile(hrefPatternStr, Perl5Compiler.CASE_INSENSITIVE_MASK);
            PatternMatcherInput linkInput = new PatternMatcherInput(content);
            int count = content.length() / 3;
            int i = 0;
            while (matcher.contains(linkInput, linkPattern)) {
                i++;
                MatchResult match = matcher.getMatch();
                String linkContent = match.toString();
                //System.out.println("=========1" + linkContent);
                // 处理href部份
                PatternMatcherInput hrefInput = new PatternMatcherInput(linkContent);
                if (matcher.contains(hrefInput, hrefPattern)) {
                    match = matcher.getMatch();
                    String hrefContent = match.toString();
                    //System.out.println("=========2" + hrefContent);
                    //String targetHrefContent = parseLink(hrefContent, params);
                    String targetHrefContent = analyseLinkContent(hrefContent, params);
                    content = org.apache.commons.lang.StringUtils.replace(content, hrefContent, targetHrefContent);
                }
                // 主要担心死循环
                if (i >= count) break;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return orignContent;
        }
        return content;
    }

    public static void main(String[] args) throws MalformedPatternException {
        //String link = "<option onpick=\"/soft/top?mid=22222&amp;cid=1111\" value=\"05\">生活软件</option>";
        //String link = "<option onpick=\"/soft/top\" value=\"05\">生活软件</option>";
        //String link = "<option onpick=\"#card\" value=\"05\">生活软件</option>";
        String link = "<a href=\"/soft/china/company?\">网秦</a>";
        String params = "mid=12&amp;cid=22&amp;id=999";
        String s = parseWapContent(link, params);
        System.out.println(s);
    }

    /**
     * 分析Href中的内容
     *
     * @param linkContent
     * @param params
     * @return
     */
    private static String analyseLinkContent(String linkContent, String params) {
        String[] splits = linkContent.split("href=");
        String prefix = "href";
        if (linkContent.startsWith("action=") || linkContent.startsWith("ACTION=")) {
            splits = linkContent.split("action=");
            prefix = "action";
        } else if (linkContent.startsWith("src=") || linkContent.startsWith("SRC=")) {
            splits = linkContent.split("src=");
            prefix = "src";
        } else if (linkContent.startsWith("href=") || linkContent.startsWith("HREF=")) {
            splits = linkContent.split("href=");
            prefix = "href";
        } else if (linkContent.startsWith("onpick=") || linkContent.startsWith("ONPICK=")) {
            splits = linkContent.split("onpick=");
            prefix = "onpick";
        }
        if (splits != null && splits.length == 2) {
            String content = splits[1];
            if (content.startsWith("\"#") || content.startsWith("'#")) {
                return linkContent;
            }
            //System.out.println("content:" + content);
            int index = content.indexOf("?");                      
            // 查询参数
            String queryString = content;
            Map<String, String> orignQuerys = new LinkedHashMap<String, String>();
            if (index > 0) {
                queryString = content.substring(index + 1, content.length() - 1);
                //System.out.println("queryString:" + queryString);
                orignQuerys = parseQueryString(queryString);
            }
            Map<String, String> targetQuerys = parseQueryString(params);
            String queryParams = buildQueryParams(orignQuerys, targetQuerys);
            //System.out.println("queryParams:" + queryParams);
            String[] querySplits = content.split("\\?");
            if (querySplits != null && querySplits.length >= 1) {
                content = querySplits[0] + "?" + queryParams;
            } else {
                content += "?" + queryParams;
            }
            content = content.replaceAll("\"", "");
            content = content.replaceAll("'", "");
            String targetLink = prefix + "=\"" + content + "\"";
            //System.out.println("targetLink:" + targetLink);
            return targetLink;
        }
        return linkContent;
    }

    /**
     * 分析查询参数
     *
     * @param queryString
     * @return
     */
    private static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> querys = new LinkedHashMap<String, String>();
        if (!StringUtils.isEmpty(queryString)) {
            String[] splits = queryString.split("&amp;");
            if (splits != null) {
                for (String s : splits) {
                    String[] queryValue = s.split("=");
                    if (queryValue != null && queryValue.length >= 1) {
                        String key = queryValue[0];
                        String value = "";
                        if (queryValue.length == 2) {
                            value = queryValue[1];
                        }
                        //System.out.println("key:value:" + key + ":" + value);
                        querys.put(key, value);
                    }
                }
            }
        }
        return querys;
    }

    /**
     * 构造查询参数
     *
     * @param orignQuerys
     * @param targetQuerys
     * @return
     */
    private static String buildQueryParams(Map<String, String> orignQuerys, Map<String, String> targetQuerys) {
        String queryParams = "";
        Set<String> keys = null;
        keys = targetQuerys.keySet();
        for (String key : keys) {
            String orignValue = orignQuerys.get(key);
            String targetValue = targetQuerys.get(key);
            //System.out.println("key:" + key);
            if (!(orignValue != null && !StringUtils.isEmpty(orignValue))) {
                // 原来就有这个参数
                orignQuerys.put(key, targetValue);
            } else if (orignValue == null) {
                orignQuerys.put(key, targetValue);
            }
        }
        keys = orignQuerys.keySet();
        int index = 0;
        int size = keys.size();
        for (String key : keys) {
            index++;
            String value = orignQuerys.get(key);
            if (index < size) {
                queryParams += key + "=" + value + "&amp;";
            } else {
                queryParams += key + "=" + value;
            }
        }
        return queryParams;
    }
}
