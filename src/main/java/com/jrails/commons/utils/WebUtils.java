package com.jrails.commons.utils;

import com.opensymphony.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Set;
import java.util.Collections;

/**
 * Created by arden
 * User: <a href="mailto:arden.emily@gmail.com">arden</a>
 * Date: 2009-2-17 14:44:51
 */
public class WebUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

    /**
     * @param request
     * @param paramName
     * @return String 参数值
     */
    public static String getString(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return StringUtils.nullStringToEmptyString(value);
    }

    public static long getLong(HttpServletRequest request, String paramName) {
        return TextUtils.parseLong(request.getParameter(paramName));
    }

    public static int getInteger(HttpServletRequest request, String paramName) {
        return TextUtils.parseInt(request.getParameter(paramName));
    }

    public static int getShort(HttpServletRequest request, String paramName) {
        if (request.getParameter(paramName) == null) {
            return 0;
        } else {
            try {
                return new Short(request.getParameter(paramName));
            } catch (Exception e) {
                logger.error("获取参数错误：" + paramName + ":"
                        + request.getParameter(paramName));
                return 0;
            }
        }
    }

    public static String getStringByAttribute(HttpServletRequest request,
                                              String paramName) {
        if (request.getAttribute(paramName) != null)
            try {
                return (String) request.getAttribute(paramName);
            } catch (Exception e) {
                logger.error("获取参数错误：" + paramName + ":"
                        + request.getAttribute(paramName));
            }
        return "";
    }

    public static long getLongByAttribute(HttpServletRequest request,
                                          String paramName) {
        return TextUtils.parseLong(request.getAttribute(paramName).toString());
    }

    public static int getIntegerByAttribute(HttpServletRequest request,
                                            String paramName) {
        return TextUtils.parseInt(request.getAttribute(paramName).toString());
    }

    public static int getShortByAttribute(HttpServletRequest request,
                                          String paramName) {
        if (request.getAttribute(paramName) == null)
            return 0;
        else {
            try {
                return new Short(request.getAttribute(paramName).toString());
            } catch (Exception e) {
                logger.error("获取参数错误：" + paramName + ":"
                        + request.getAttribute(paramName));
                return 0;
            }
        }
    }

    /**
     * 获取终端UA
     *
     * @param request
     * @return String userAgent
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            userAgent = request.getHeader("user-agent");
        }
        if (userAgent == null) {
            String X_Wap_Profile = request.getHeader("X-Wap-Profile");
            if (X_Wap_Profile != null) {
                int index = X_Wap_Profile.lastIndexOf("/");
                String tempInfo = X_Wap_Profile.substring(index + 1);
                if (tempInfo != null) {
                    String[] splits = tempInfo.split(".");
                    if (splits != null && splits.length > 0) {
                        userAgent = splits[0];
                    }
                }
            }
        }
        if (userAgent != null) {
            int place = userAgent.indexOf("/");
            if (place >= 0)
                userAgent = userAgent.substring(0, place);
            place = userAgent.indexOf("*");
            if (place >= 0)
                userAgent = userAgent.substring(0, place);
        }
        if (userAgent == null) {
            userAgent = request.getParameter("cx");
        }
        if (userAgent == null)
            return "";
        return userAgent;
    }

    /**
     * 获取手机号
     *
     * @param request
     * @return String 手机号
     */
    public static String getMobile(HttpServletRequest request) {
        String mobile = request.getHeader("x-up-calling-line-id");
        if (mobile == null) {
            mobile = request.getHeader("x-up-subno");
        }
        if (mobile == null) {
            // 信息如：GPRS,8613590309275,10.204.121.245,SZGGSNO3BNK,unsecured
            String netinfo = request.getHeader("X-Network-info");
            if (netinfo != null) {
                String[] splits = netinfo.split(",");
                if (splits != null) {
                    mobile = splits[1];
                }
            }
        }
        if ((mobile != null) && (!"".equals(mobile))) {
            if (mobile.startsWith("861"))
                mobile = mobile.substring(2, 13);
            else if (mobile.startsWith("0861"))
                mobile = mobile.substring(3, 14);
        } else {
            mobile = request.getHeader("X-Nx-Clid");
        }

        return mobile;
    }

    public static String getServerUrl(HttpServletRequest request) {
        String serverName = request.getServerName();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        String serverUrl = scheme + "://" + serverName;
        if (port != 80) {
            serverUrl = scheme + "://" + serverName + ":" + port;
        }
        return serverUrl;
    }
    
    public static final String[] mobileAgent = { "Mozilla", "httpclient",
		"Opera", "sohu", "WinWAP", "Internet", "Microsoft", "OPWV",
		"OPWV-SDK", "OWG1 UP", "OWG1", "timewe", "CDR", "SpiderMan",
		"Baiduspider", "msnbot", "Google", "Googlebot", "Timewebot",
		"Nokia6820", "iaskspider", "iaskspider2",
		"iaskspider2 iask@staff.sina.com.cn", "Mytest agent" };

	public static String[] getMobileUAInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String header = request.getHeader("user-agent");
		String via = request.getHeader("via");
		String header_mobileNO = request.getHeader("x-up-calling-line-id");
		String uni_mobileNO = request.getParameter("DEVICEID");
	
		String mobileType = "UNKNOW";
		String mobileNo = "UNKNOW";
		String[] retStr = new String[] { "", "" ," "};
		try {
			if (session.getAttribute("phoneno") != null
					&& !(session.getAttribute("phoneno").equals(""))) {
				mobileNo = (String) session.getAttribute("phoneno");
			}
			mobileNo = request.getParameter("PHONENO");
			if (mobileNo == null || mobileNo.equals("")
					|| mobileNo.equals("UNKNOW")) {
				mobileNo = uni_mobileNO;
			}
			if (mobileNo == null || mobileNo.equals("")
					|| mobileNo.equals("UNKNOW")) {
				mobileNo = header_mobileNO;
			}
			if (mobileNo == null || mobileNo.equals("")
					|| mobileNo.equals("UNKNOW") || mobileNo.equals("null")) {
				mobileNo = request.getParameter("MISC_MSISDN");
			}
	
			// session.setAttribute("phoneno", mobileNo);
			int pos = 0;
			if (header != null) {
				pos = header.indexOf("/");
			}
			if (pos >= 0) {
				if (header != null) {
					mobileType = header.substring(0, pos);
				}
			} else {
				mobileType = header;
			}
			int pos_star = mobileType.indexOf("*");
			if (pos_star >= 0) {
				mobileType = mobileType.substring(0, pos_star);
			}
			if (mobileType == null || mobileType.equals("")) {
				mobileType = "Mozilla";
			}
		} catch (Exception e) {
			logger.info("==UA ERROR=!");
			return retStr;
		}
		retStr[2] = mobileType;
		if(via==null && (header==null || header.toUpperCase().indexOf("UCWEB")==-1)){  //不是通过移动网关过来的用户，且不是通过UCWEB上来的用户		
			mobileType = "ismozilla";
		}
		if(via!=null && mobileType.equals("UNKNOW")){
			if(via.length()>20){
				mobileType = via.substring(0,20);
			}else{
				mobileType = via;
			}
		}
		retStr[0] = mobileType;
		retStr[1] = mobileNo;
		return retStr;
	}
    
    public static boolean isOpera(HttpServletRequest request) {
		String[] mobileInfo = WebUtils.getMobileUAInfo(request);
		if(mobileInfo[0]!=null && mobileInfo[0].equals("ismozilla")){
			return true;
		}
		return false;
	}
    
    public static boolean isMozilla(HttpServletRequest request) {		
		String[] mobileInfo = WebUtils.getMobileUAInfo(request);	
		if (mobileInfo[2].startsWith("Mozilla") || mobileInfo[2].startsWith("Opera")) {
			return true;
		}
		return false;
	}

    /**
     * 分析查询参数
     * @param queryString
     * @return
     */
    public static Map<String,String> parseQueryString(String queryString) {
        if (!StringUtils.isEmpty(queryString)) {
            Map<String,String> querys = new WeakHashMap<String,String>();
            if (queryString.contains("?")) {
                int index = queryString.indexOf("?");
                queryString = queryString.substring(index + 1);
            }
            if (!StringUtils.isEmpty(queryString)) {
                String[] splits = queryString.split("&amp;");
                if (!queryString.contains("&amp;")) {
                    splits = queryString.split("&");
                }
                if (splits != null) {
                    for (String s : splits) {
                        String[] queryValue = s.split("=");
                        if (queryValue != null && queryValue.length >= 1) {
                            String key = queryValue[0];
                            String value = "";
                            if (queryValue.length == 2) {
                                value = queryValue[1];
                            }
                            querys.put(key, value);
                        }
                    }
                }
            }
            return querys;
        }
        return null;
    }

    /**
     * 构造查询参数
     * @param orignQuerys
     * @param targetQuerys
     * @return
     */
    public static String buildQueryParams(Map<String,String> orignQuerys, Map<String,String> targetQuerys, String split) {
        if (orignQuerys != null && !orignQuerys.isEmpty()) {
            String queryParams = "";
            Set<String> keys = targetQuerys.keySet();
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
                    queryParams += key + "=" + value + split;
                } else {
                    queryParams += key + "=" + value;
                }
            }
            queryParams = StringUtils.nullStringToEmptyString(queryParams);
            return queryParams;
        }
        return "";
    }

    /**
     * 构造查询参数
     * @param orignQuerys
     * @return
     */
    public static String buildQueryParams(Map<String,String> orignQuerys, String split) {
        if (orignQuerys != null && !orignQuerys.isEmpty()) {
            String queryParams = "";
            Set<String> keys = orignQuerys.keySet();
            int index = 0;
            int size = keys.size();
            for (String key : keys) {
                index++;
                String value = orignQuerys.get(key);
                if (index < size) {
                    queryParams += key + "=" + value + split;
                } else {
                    queryParams += key + "=" + value;
                }
            }
            queryParams = StringUtils.nullStringToEmptyString(queryParams);
            return queryParams;
        }
        return "";
    }
}
