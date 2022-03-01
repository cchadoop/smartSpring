package com.smart.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import nl.bitwalker.useragentutils.Version;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author smart
 * @date: 2021/4/16 11:28
 */

@Slf4j
public class HttpRequestUtil {

    /**
     * 获取本地ip
     *
     * @return
     */
    public static String getIpAddress() {
        try {
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
                    continue;
                } else {
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("IP地址获取失败" + e.toString());
        }
        return "";
    }

    /**
     * 获取发起请求的IP地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取发起请求的浏览器名称
     */
    public static String getBrowserName(HttpServletRequest request) {
        try {
            String header = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            Browser browser = userAgent.getBrowser();
            return browser.getName();
        } catch (Exception e) {
            log.error("浏览器名称获取失败" + e.toString());
            return "";
        }
    }

    /**
     * 获取发起请求的浏览器版本号
     */
    public static String getBrowserVersion(HttpServletRequest request) {
        try {
            String header = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            //获取浏览器信息
            Browser browser = userAgent.getBrowser();
            //获取浏览器版本号
            Version version = browser.getVersion(header);
            return version.getVersion();
        } catch (Exception e) {
            log.error("浏览器版本号获取失败" + e.toString());
            return "";
        }
    }

    /**
     * 获取发起请求的操作系统名称
     */
    public static String getOsName(HttpServletRequest request) {
        try {
            String header = request.getHeader("User-Agent");
            UserAgent userAgent = UserAgent.parseUserAgentString(header);
            OperatingSystem operatingSystem = userAgent.getOperatingSystem();
            return operatingSystem.getName();
        } catch (Exception e) {
            log.error("操作系统名称获取失败" + e.toString());
            return "";
        }
    }

    /**
     * 根据切点获取方法名
     *
     * @param joinPoint
     * @return
     */
    public static String getMethod(JoinPoint joinPoint) {
        try {
            Signature sig = joinPoint.getSignature();
            MethodSignature msig = null;
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
            msig = (MethodSignature) sig;
            Object target = joinPoint.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            return currentMethod.getName();
        } catch (NoSuchMethodException e) {
            log.error("操作系统名称获取失败" + e.toString());
            return "";
        }
    }

    /**
     * 根据切点获取参数json
     *
     * @param joinPoint
     * @return
     */
    public static String getParams(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            Object[] params = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse
                        || args[i] instanceof MultipartFile || args[i] instanceof MultipartFile[]) {
                    continue;
                }
                params[i] = args[i];
            }
            return JSONObject.toJSONString(params);
        } catch (Exception e) {
            log.error("无法解析参数", e);
            return "";
        }
    }
}
