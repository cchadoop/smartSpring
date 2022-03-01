package com.smart.config;


import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2021000119606000";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCoDlxFIQ+z1B99awtRcmNZS21xMlyf2jr5Rd97477kAIwC+HZmQ52htCstZ938zALkMRSO3HNJBStNom705CzObvAIYqKdZecJryzWQsuI4j7ebH+6cA+7XAthc39zk6KlnuWXe46fBZOVnJsT0Hz12mAM3QABFm7GAZaoVx4/7W4NuwYX6q67XEvSI8fT946vXej2woK4hQuurkX53yxtHaQimis5ICT8mOtQfnw/8EDT/RNtWws2gfTHIQyYl8vJZdt7vh86HtXQ75Zyb8sSmouBqcVufkBYt2ZCMuQNz9NcoRRebziKGAcWFh9ju952SrBN5f3ugNBiWh/vMfUbAgMBAAECggEAFZQaduni2UVQccsv3dphNVMBD3rW99OOpPk60+6PUTlcwPjkQwdFk0DOFntyvoX3jRNcP2fBCcw3HjXQJ3Ow6STHf1wi4zD1dIHgsPZMqhva1LZ7xBWlf3o3H+EzqUR4qul3M3cy/eo6ZAiCxQ2NLghHMv+hg4I84QHNNXpSMQT3SsDKzn5nBm/fSj7dKBF2fzeX9PJG+fD8Frjf0eVb6RWVwlQ4gEacxffY/QPtlaWSFqDitPdqbjSCAPZiNYQL3KfHnTgOqxA2IOyWjxPUSF58JbdDV/f3HpT2p50eJ5gg70o0Qbzqt3u3C+dwyaIP3FB5+pJjhzdgbuV19PJdiQKBgQDRk2RjS/McapmXqpk7ulw1yiqbmhKjO+APyGJVOYLFir9SnehXOImGWWfvYQPAHJKj/nT4X3FOO9YJMOS0/uSYNYEf+If818luWNijERPzNL97xv37Os5fRmFkSc+VOfglEm99yQb2xG18BFrYe7hbtDM+VzxtvTqvOaPn/1hdHwKBgQDNSHlzsetq3PpiUOkGfUAA7M2YuyPIXBBXgj2BBNXKgJufjZQUqgEYPxWi3Uop0miKybLHANPxrm+tuZL6e2wnqVJF/i/HkjimUQleK9W7kXk1gczTgQKQKcnoZflyZkEbXrGeAiE1Qa7Y1tHrk98Uri94MtNcH0wp6YdfkG/shQKBgEGHoMAbo2yjOFtgEIHIh8SZ7jCxQFqiAjFtKrITRXyXIbOnhFqhZv8HPaRv5UhQptl4WwK+dSNNOmOtUhX2DWw6bqGciIjawDUsurBiSqalO6psUkYX4SeRTiqZmbrDYX/V/96I2RCX4tQAWJeQN9kUw2EezdpSbOMVVtbX1L1pAoGBAMsHH992G0CQly5/mUhqvc9sZRbmF0jFDQCPdibRUmS6d7AAxXD+hqClW8+9Dul4CR8ixyccwYGpJRjrjdsgEGLViuJguH6oNlD/msVBk1y0Tu3Fl3+je5Z7Tg8ENAaZ7VCVghOlRZS3hqc9J1bX4+0/LQGzOulEpv5Y0iOI5blFAoGAcx1pwMijOIfWqz+Glb0LRTWqMf+NvQxjI55D9U74J7T5BzJB/vXIylxQp0/lP1pVs3gMn/huScuFXlhhlRhvfaFUbznxcTLNO3MF3RHcRJy0HgmBkO3/7tIbCDaCPt43yphDWKrWkxLCRtxCbRkcVCTbjhBpTA1rKTiYE8BH2cU=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmyIuVLw+Kv4X2b0lM2Vw8+L8ibArOlRfXkUkLpd6fh0uqQ/PzkuFG6z+FsJLeTVnM2wbMxO/Pv3qsTvGhSZtBYTHQIAEGPBAovhDsR7zhIHchG3LMXq1Uw+31PfdGQYD8Pnh74wXhuhCpFj7g2TW0QRX81XOZjUMTrwHNTP+Tp81vhVw1rulV5Ryybp8gcpirnb7s2Aa37np2LOhhpbEeaPXHM5dpe+TUmnlXyH7E/k5HdNmeyg8TUynFHbsDxnFAoFCwwP6c1MMxxe91bS+0SFTlWgKjuaUyFrGw6C+msSdgCmLmWVVufMg5217EIYMTNgMZdg01oiWuItfn1HsGQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
