package com.wsd.knowledge.util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import java.nio.charset.Charset;
import java.util.Properties;

public class WeiXinPushUtil{
	  private static Logger logger = Logger.getLogger(WeiXinPushUtil.class);
	  private static final String APPLICATION_JSON = "application/json";
      private static final String CONTENT_TYPE_TEXT_JSON = "text/json";


	  public static void main(String[] args){
//		String id="1379";
//		String enpassword="8BBD6FE6E5CB076ED9F1512A215803424860B0069FF8E65127CA74E62F923AF6";
		String postUrl="{\"Uid\":1300,\"Content\":\"创建人:李四\\n标题:测试\\n内容:知识库系统消息" +
                "\\n\",\"" +
                "AgentId\":1000003,\"Title\":\"知识库系统：消息通知\",\"Url\":\"http://report.wsloan.com:8888/gd-mobile//#/?id="+1300+"\"}";
		logger.info(postUrl);
      try {
			  String s = httpPostWithJSON(postUrl);
			  logger.info(s);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }

	/**
	 * 微信推送的方法
	 * @param json
	 * @return
	 * @throws Exception
	 */
	  public static String httpPostWithJSON(String json) throws Exception {
			Properties prop = new Properties();

//			try {
////			  prop=ReadPropertiesUtil.readProperties();
//			} catch (RuntimeException e) {
//				e.printStackTrace();
//			}
            String result = null;
		    CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://report.wsloan.com:8888/workweixin/api/Message");
			httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
			StringEntity se = new StringEntity(json, Charset.forName("UTF-8"));
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
			httpPost.setEntity(se);
			CloseableHttpResponse responseBody = httpClient.execute(httpPost);
			result = EntityUtils.toString(responseBody.getEntity(), "UTF-8");
			return result;
      }


}
