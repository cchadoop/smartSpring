package com.smart.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayOpenPublicTemplateMessageIndustryModifyRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayOpenPublicTemplateMessageIndustryModifyResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.smart.config.AlipayConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class refundController {

    public static String APP_ID = "2021000119606000";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCoDlxFIQ+z1B99awtRcmNZS21xMlyf2jr5Rd97477kAIwC+HZmQ52htCstZ938zALkMRSO3HNJBStNom705CzObvAIYqKdZecJryzWQsuI4j7ebH+6cA+7XAthc39zk6KlnuWXe46fBZOVnJsT0Hz12mAM3QABFm7GAZaoVx4/7W4NuwYX6q67XEvSI8fT946vXej2woK4hQuurkX53yxtHaQimis5ICT8mOtQfnw/8EDT/RNtWws2gfTHIQyYl8vJZdt7vh86HtXQ75Zyb8sSmouBqcVufkBYt2ZCMuQNz9NcoRRebziKGAcWFh9ju952SrBN5f3ugNBiWh/vMfUbAgMBAAECggEAFZQaduni2UVQccsv3dphNVMBD3rW99OOpPk60+6PUTlcwPjkQwdFk0DOFntyvoX3jRNcP2fBCcw3HjXQJ3Ow6STHf1wi4zD1dIHgsPZMqhva1LZ7xBWlf3o3H+EzqUR4qul3M3cy/eo6ZAiCxQ2NLghHMv+hg4I84QHNNXpSMQT3SsDKzn5nBm/fSj7dKBF2fzeX9PJG+fD8Frjf0eVb6RWVwlQ4gEacxffY/QPtlaWSFqDitPdqbjSCAPZiNYQL3KfHnTgOqxA2IOyWjxPUSF58JbdDV/f3HpT2p50eJ5gg70o0Qbzqt3u3C+dwyaIP3FB5+pJjhzdgbuV19PJdiQKBgQDRk2RjS/McapmXqpk7ulw1yiqbmhKjO+APyGJVOYLFir9SnehXOImGWWfvYQPAHJKj/nT4X3FOO9YJMOS0/uSYNYEf+If818luWNijERPzNL97xv37Os5fRmFkSc+VOfglEm99yQb2xG18BFrYe7hbtDM+VzxtvTqvOaPn/1hdHwKBgQDNSHlzsetq3PpiUOkGfUAA7M2YuyPIXBBXgj2BBNXKgJufjZQUqgEYPxWi3Uop0miKybLHANPxrm+tuZL6e2wnqVJF/i/HkjimUQleK9W7kXk1gczTgQKQKcnoZflyZkEbXrGeAiE1Qa7Y1tHrk98Uri94MtNcH0wp6YdfkG/shQKBgEGHoMAbo2yjOFtgEIHIh8SZ7jCxQFqiAjFtKrITRXyXIbOnhFqhZv8HPaRv5UhQptl4WwK+dSNNOmOtUhX2DWw6bqGciIjawDUsurBiSqalO6psUkYX4SeRTiqZmbrDYX/V/96I2RCX4tQAWJeQN9kUw2EezdpSbOMVVtbX1L1pAoGBAMsHH992G0CQly5/mUhqvc9sZRbmF0jFDQCPdibRUmS6d7AAxXD+hqClW8+9Dul4CR8ixyccwYGpJRjrjdsgEGLViuJguH6oNlD/msVBk1y0Tu3Fl3+je5Z7Tg8ENAaZ7VCVghOlRZS3hqc9J1bX4+0/LQGzOulEpv5Y0iOI5blFAoGAcx1pwMijOIfWqz+Glb0LRTWqMf+NvQxjI55D9U74J7T5BzJB/vXIylxQp0/lP1pVs3gMn/huScuFXlhhlRhvfaFUbznxcTLNO3MF3RHcRJy0HgmBkO3/7tIbCDaCPt43yphDWKrWkxLCRtxCbRkcVCTbjhBpTA1rKTiYE8BH2cU=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmyIuVLw+Kv4X2b0lM2Vw8+L8ibArOlRfXkUkLpd6fh0uqQ/PzkuFG6z+FsJLeTVnM2wbMxO/Pv3qsTvGhSZtBYTHQIAEGPBAovhDsR7zhIHchG3LMXq1Uw+31PfdGQYD8Pnh74wXhuhCpFj7g2TW0QRX81XOZjUMTrwHNTP+Tp81vhVw1rulV5Ryybp8gcpirnb7s2Aa37np2LOhhpbEeaPXHM5dpe+TUmnlXyH7E/k5HdNmeyg8TUynFHbsDxnFAoFCwwP6c1MMxxe91bS+0SFTlWgKjuaUyFrGw6C+msSdgCmLmWVVufMg5217EIYMTNgMZdg01oiWuItfn1HsGQIDAQAB";

    public static String CHARSET = "UTF-8";

    @RequestMapping("refund")
    public void refund() throws AlipayApiException {
        // 实例化客户端
        // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.open.public.template.message.industry.modify
        AlipayOpenPublicTemplateMessageIndustryModifyRequest request = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数
        //此次只是参数展示，未进行字符串转义，实际情况下请转义
        request.setBizContent("  {" + "    \"primary_industry_name\":\"IT科技/IT软件与服务\"," + "    \"primary_industry_code\":\"10001/20102\"," + "    \"secondary_industry_code\":\"10001/20102\"," + "    \"secondary_industry_name\":\"IT科技/IT软件与服务\"" + " }");
        AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(request);
        //调用成功，则处理业务逻辑
        if (response.isSuccess()) {
            //.....
        }
    }


    @RequestMapping("pay")
    public String pay() throws AlipayApiException {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo("TradeNo");
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping("pay2")
    @ResponseBody
    public String pay2() throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = "d202201271728";

        //付款金额，必填
//        String total_amount = order.getOrderAmount();
        String total_amount = "5999";
        //订单名称，必填
        String subject = "iphone13";

        //商品描述，可空
//        String body = "用户订购商品个数：" + order.getBuyCounts();
        String body = "用户订购商品个数：" + 2;

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

}
