package com.evil.imagebrowser.network.request;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @项目名： SuperHttp
 * @包名： com.evil.http
 * @创建者: Noah.冯
 * @时间: 11:03
 * @描述： TODO
 */

public interface HttpSimulator{

    //window
    String WIN_WEB_SIMULATOR = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36";
    //mac
    String MAC_WEB_SIMULATOR = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36";
    //ipad
    String IPAD_WEB_SIMULATOR = "Mozilla/5.0 (iPad; CPU OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11B554a Safari/9537.53";
    //ios
    String IOS_WEB_SIMULATOR = "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11B554a Safari/9537.53";
    //android
    String ANDROID_WEB_SIMULATOR = "Mozilla/5.0 (Linux; U; Android 7.0.0; zh-CN; SCH-R530U Build/JZO54K) AppleWebKit/534.31 (KHTML, like Gecko) UCBrowser/9.3.2.349 U3/0.8.0 Mobile Safari/534.31";
    //weixin
    String WEICHAT_WEB_SIMULATOR = "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Mobile/11B554aMicroMessenger/5.0.3";


    //PC端：
    //safari 5.1 – MAC
    String SAFARI_MAC_WEB_SIMULATOR = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50";
    //safari 5.1 – Windows
    String SAFARI_WINDOWS_WEB_SIMULATOR = "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50";
    //IE 9.0
    String IE9_WEB_SIMULATOR = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0;";
    //IE 8.0
    String IE8_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)";
    //IE 7.0
    String IE7_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)";
    //IE 6.0
    String IE6_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)";

    //Firefox 4.0.1 – MAC
    String FIREFOX_MAC_WEB_SIMULATOR = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1";

    //Firefox 4.0.1 – Windows
    String FIREFOX_WINDOWS_WEB_SIMULATOR = "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1";

    //Opera 11.11 – MAC
    String OPERA_MAC_WEB_SIMULATOR = "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11";

    //Opera 11.11 – Windows
    String OPERA_WINDOWS_WEB_SIMULATOR = "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11";

    //Chrome 17.0 – MAC
    String CHROME_MAC_WEB_SIMULATOR = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11";

    //傲游（Maxthon）
    String MAXTHON_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)";

    //腾讯TT
    String TT_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)";

    //世界之窗（The World） 2.x
    String WORLD2_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)";

    //世界之窗（The World） 3.x
    String WORLD3_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)";

    //搜狗浏览器 1.x
    String SOUGOU_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)";

    // 360浏览器
    String SAFE360_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)";

    //Avant
    String AVANT_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Avant Browser)";

    //Green Browser
    String GREEN_BROWSER_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)";

    //移动设备端：

    //safari iOS 4.33 – iPhone
    String SAFARI_IPHONE_WEB_SIMULATOR = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";

    //safari iOS 4.33 – iPod Touch
    String SAFARI_IPOD_WEB_SIMULATOR = "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";

    //safari iOS 4.33 – iPad
    String SAFARI_IPAD_WEB_SIMULATOR = "Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5";

    //Android N1
    String ANDROID_N1_WEB_SIMULATOR = "Mozilla/5.0 (Linux; U; Android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

    //Android QQ浏览器 For android
    String ANDROID_QQ_WEB_SIMULATOR = "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";

    //Android Opera Mobile
    String OPERA_MOBILE_WEB_SIMULATOR = "Opera/9.80 (Android 2.3.4; Linux; Opera Mobi/observe-1107180945; U; en-GB) Presto/2.8.149 Version/11.10";

    //Android Pad Moto Xoom
    String ANDROID_PAD_WEB_SIMULATOR = "Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13";

    //BlackBerry
    String BLACK_BERRY_WEB_SIMULATOR = "Mozilla/5.0 (BlackBerry; U; BlackBerry 9800; en) AppleWebKit/534.1+ (KHTML, like Gecko) Version/6.0.0.337 Mobile Safari/534.1+";

    //WebOS HP Touchpad
    String WEBOS_WEB_SIMULATOR = "Mozilla/5.0 (hp-tablet; Linux; hpwOS/3.0.0; U; en-US) AppleWebKit/534.6 (KHTML, like Gecko) wOSBrowser/233.70 Safari/534.6 TouchPad/1.0";

    //Nokia N97
    String NOKIA_WEB_SIMULATOR = "Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18124";

    //Windows Phone Mango
    String WIN_PHONE_WEB_SIMULATOR = "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; HTC; Titan)";

    //UC无
    String UC_WEB_SIMULATOR = "UCWEB7.0.2.37/28/999";

    //UC标准
    String UCS_WEB_SIMULATOR = "NOKIA5700/ UCWEB7.0.2.37/28/999";

    //UCOpenwave
    String UC_OPENWAVE_WEB_SIMULATOR = "Openwave/ UCWEB7.0.2.37/28/999";

    //UC Opera
    String UC_OPERA_WEB_SIMULATOR = "Mozilla/4.0 (compatible; MSIE 6.0; ) Opera/UCWEB7.0.2.37/28/999";

    @StringDef({ANDROID_N1_WEB_SIMULATOR,ANDROID_PAD_WEB_SIMULATOR,ANDROID_QQ_WEB_SIMULATOR,ANDROID_WEB_SIMULATOR,AVANT_WEB_SIMULATOR,BLACK_BERRY_WEB_SIMULATOR,CHROME_MAC_WEB_SIMULATOR,FIREFOX_MAC_WEB_SIMULATOR,FIREFOX_WINDOWS_WEB_SIMULATOR,GREEN_BROWSER_WEB_SIMULATOR,IE6_WEB_SIMULATOR,IE7_WEB_SIMULATOR,IE8_WEB_SIMULATOR,IE9_WEB_SIMULATOR,IOS_WEB_SIMULATOR,IPAD_WEB_SIMULATOR,MAC_WEB_SIMULATOR,MAXTHON_WEB_SIMULATOR,NOKIA_WEB_SIMULATOR,OPERA_MAC_WEB_SIMULATOR,OPERA_MOBILE_WEB_SIMULATOR,OPERA_WINDOWS_WEB_SIMULATOR,SAFARI_IPAD_WEB_SIMULATOR,SAFARI_IPHONE_WEB_SIMULATOR,SAFARI_IPOD_WEB_SIMULATOR,SAFARI_MAC_WEB_SIMULATOR,SAFARI_WINDOWS_WEB_SIMULATOR,SAFE360_WEB_SIMULATOR,SOUGOU_WEB_SIMULATOR,TT_WEB_SIMULATOR,UCS_WEB_SIMULATOR,UC_OPENWAVE_WEB_SIMULATOR,UC_OPERA_WEB_SIMULATOR,UC_WEB_SIMULATOR,WEBOS_WEB_SIMULATOR,WEICHAT_WEB_SIMULATOR,WIN_PHONE_WEB_SIMULATOR,WIN_WEB_SIMULATOR,WORLD2_WEB_SIMULATOR,WORLD3_WEB_SIMULATOR})
    @Retention(RetentionPolicy.SOURCE)
    @interface TYPE{}
}
