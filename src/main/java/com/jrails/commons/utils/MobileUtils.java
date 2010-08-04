/* MobileUtil.java
 * --------------------------------------
 * CREATED ON Mar 16, 2006 2:30:43 AM
 *
 * MSN arden.emily@msn.com
 * QQ 103099587（太阳里的雪）
 * MOBILE 13590309275
 *
 * ALL RIGHTS RESERVED BY ZHENUU CO,.LTD.
 * --------------------------------------
 */
package com.jrails.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 手机号码工具类
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
public class MobileUtils {

    public static String checkMobile(String mobile) {
        Pattern p1 = Pattern.compile("^(\\+{0,1}86){0,1}(1[3]\\d{9}|15[89]\\d{8})");
        Matcher m1 = p1.matcher(mobile);
        if (m1.matches()) {
            Pattern p2 = Pattern.compile("^((\\+{0,1}86){0,1})");
            Matcher m2 = p2.matcher(mobile);
            StringBuffer sb = new StringBuffer();
            while (m2.find()) {
                m2.appendReplacement(sb, "");
            }
            m2.appendTail(sb);
            return sb.toString();

        } else {
            return null;
        }
    }

    /**
     * 检测手机号码是否合法
     *
     * @param mobile
     * @return
     */
    public static boolean validate(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        // String regex = "((\\(\\d{2,3}\\))|(\\d{3}\\-))?13|15\\d{9}";
//		String regex = "^13|15\\d{9}";
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(mobile);
//		boolean validate = m.matches();

        Pattern p1 = Pattern.compile("^(\\+{0,1}86){0,1}(1[3]\\d{9}|15[89]\\d{8})");
        Matcher m1 = p1.matcher(mobile);
        if (m1.matches()) {
            return true;
        }

        return false;
    }

    /**
     * 格式化手机号
     *
     * @param mobile
     * @return
     */
    public static String formate(String mobile) {
        if (mobile != null && !mobile.equals("null") && !mobile.equals("")) {
            mobile = mobile.trim();
            if (mobile.startsWith("86")) {
                mobile = mobile.substring(2);
            }
            if (mobile.startsWith("+86")) {
                mobile = mobile.substring(3);
            }
        }
        return mobile;
    }
    
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

    public static void main(String... args) {
        String mobile = "13590309275";
        boolean validate = MobileUtils.validate(mobile);
        System.out.println("validate:" + validate);
    }
}
