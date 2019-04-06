package com.wd.ASFlowerWeb.config;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016091900548516";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCINI4IioNEb/kR9k9DWsCHna2KiLDaFhkoO5fBikgv36fDUucoDYrG8dFVbjMaAtf9NFvuG1DBnKM3Rf6HQXR0ZQ3GE+y0RRj0jVsOty2d78kdv/+NTCthURlN6dXDCGdYYYygNj/mWG77ZzIK6Ejx9gWQuGy6d7YFQ4es/v7RKQFFdho1Ifn3lNGzmTipjAQZjJqzyxcXOIiogttLJrxMZ3vhQT5k+4HSSj4DnAbAlxf2u/uxeE8kq+Qic5f6HZX1kWBhc+cUz3vCHFeTifVuXRw6qMEC9N9xTMRIDYE/Y89xWAhn0xrvhbfHyEHtqaSkACVreoCSRGdX2x55ABYjAgMBAAECggEAZzqdoy+HiWjr9ocdT8kKPj5s9p/emYtCCdIQlCi/yTjTJyA//YU19s3zf7IDnOUm2crDGdUuAW877HpqDAy5vDWSmNsk/QOZhE9WAfMm6ZqAbMHFZ0uhXSJf8UMWNbi6yqs0L4CuMWA4zOVLciTWlDuHwEVmcnSkSZ0CyUSBz/cD5Is4LYFpAR0SRK+gdoLsJaaIV9CIYMAd4RV+VW9mrGzAuOriGgvyFW5fqtj10+GSKzQXzt/4ayA6zQH2WK1Sx3JpIm9bn7WKavrhYy3BTVnexAyoVmAyeyIMxXJu0arLtIsoYeiFVPeTdV/w74jiJjRKbEpOV4Fyyqtnv0oLsQKBgQD2m1DWDCV1yGTdQ60J8oPMpOOWfOJYMV+xE1sAgZQEkjbxtOU2BfL6MZh+ShiSejGKhEHKB0cevqFZW9VdXPU3o+VdRWvvWcqHd7a9babLXugU2NINc1V8o3SatllJ7bD3qQf49+GUGKPyD2JJ5Zaf7rOzE38zj6SHaCRN5quFXQKBgQCNZLR2M62N3wV34vCuYRY+EgzRoZUdmfeRLtIdjDiYD9oxmRnZkWXrfsIHqx7IT+IYC4TzS9VjrOfTNDrSVaRrCQEkqGSgVWLuk8iMC9qLV4ZdyNbqd3KP7QC5mHhbIPQy9d6qy2GhZ+QBymY0UMxjrklxm9s1aofTF4sYoajRfwKBgHQTp3kZPoiySsfbkxebj25ELtwm7GOW2fP/qFYUqBd+u14KR9ml8zjRH3ZSaj6IRcltd4og4tkV4dyt3UCVANevzcaZZrTDDxG0x89iTaBsi9FEPZsXLqX8SmaIbn7d4u6wIZCPHR8YehL2Ks2dw8iEExG3m6gpdMhHvM7uRM4BAoGAcm3w+sSCDhJTS543qPjb1monQ7TgiG9mA3KAC/fCiZYywPcmC4V3laolf61GoVi9IzaXZ4uU7DEwAScA/97dzN4htbguj+/qvCiQIn8pZKH2FYqIHIOFDOQDNTEnHqFB4MhJZ7JnlggNaEGUi3xknbucprw7ITTRLc6c/NdAWCMCgYEA1sqsjcysS2aKau21T8aFtSNb9KHMW0NAOtvhz4K/NV6D4RSD5GWHsjNE21hwOHp3WmDPk6ac0rJ4bSpZXSFTytWwnPIhJDQ6UHpajN98UEwJu8vNFlvXq95SUqXTEM37kkIuJxvbN5fc9z49Hzmzzk0UXXJKdTKTUZ+aiAOW3vw=";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/notify_url.jsp";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://商户网关地址/alipay.trade.wap.pay-JAVA-UTF-8/return_url.jsp";
	// 请求网关地址
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwqTffdk9gRL1oNLhulbCtNFtVTHsGUJikQk+h2Oju9NJtyJFC76WqBV8Xpzrlwff8lJx+WHquuNfwnBaMF+GbVJ6eY+jn2QUPtJcW7WGBneCpmFmCC5uNFxgvoqmZiXE6rp8ySojn+tW/L3yfJ08HSqsjhdQ2Z/3OBwT5h1uUsyp/tbPhR32vzkyOB4+jYEvUOk2hW+bEEmhGSpCQdUbsZqq+qpoLJsCH0JBeT1szfyfCXFBKg2XJ3HjngMlMsFIrWtgcQ+qxndK9498SwZgG3p62ub5Vq2TclJ4N8G8fFK4Szo/Av0hd7xogKZe7rdmeb9fC3cNV5taIGRD0cpMyQIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
