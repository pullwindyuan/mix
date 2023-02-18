package org.hlpay.base.channel.unionpay.sdk;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UnionConfig {
  public static String merId = "777290058110048";


  public static String encoding_UTF8 = "UTF-8";

  public static String encoding_GBK = "GBK";

  public static String version = "5.0.0";


  public static String getCurrentTime() {
    return (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
  }


  public static String getOrderId() {
    return (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
  }







  public static String genHtmlResult(Map<String, String> data) {
    TreeMap<String, String> tree = new TreeMap<>();
    Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry<String, String> en = it.next();
      tree.put(en.getKey(), en.getValue());
    }
    it = tree.entrySet().iterator();
    StringBuffer sf = new StringBuffer();
    while (it.hasNext()) {
      Map.Entry<String, String> en = it.next();
      String key = en.getKey();
      String value = en.getValue();
      if ("respCode".equals(key)) {
        sf.append("<b>" + key + "=" + value + "</br></b>"); continue;
      }
      sf.append(key + "=" + value + "</br>");
    }
    return sf.toString();
  }







  public static List<Map> parseZMFile(String filePath) {
    int[] lengthArray = { 3, 11, 11, 6, 10, 19, 12, 4, 2, 21, 2, 32, 2, 6, 10, 13, 13, 4, 15, 2, 2, 6, 2, 4, 32, 1, 21, 15, 1, 15, 32, 13, 13, 8, 32, 13, 13, 12, 2, 1, 131 };
    return parseFile(filePath, lengthArray);
  }








  public static List<Map> parseZMEFile(String filePath) {
    int[] lengthArray = { 3, 11, 11, 6, 10, 19, 12, 4, 2, 21, 2, 32, 2, 6, 10, 13, 13, 4, 15, 2, 2, 6, 2, 4, 32, 1, 21, 15, 1, 15, 32, 13, 13, 8, 32, 13, 13, 12, 2, 1, 131 };
    return parseFile(filePath, lengthArray);
  }








  private static List<Map> parseFile(String filePath, int[] lengthArray) {
    List<Map> ZmDataList = new ArrayList<>();
    try {
      String encoding = "UTF-8";
      File file = new File(filePath);
      if (file.isFile() && file.exists()) {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);

        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = null;
        while ((lineTxt = bufferedReader.readLine()) != null) {

          Map<Integer, String> ZmDataMap = new LinkedHashMap<>();

          int leftIndex = 0;

          int rightIndex = 0;
          for (int j = 0; j < lengthArray.length; j++) {
            rightIndex = leftIndex + lengthArray[j];
            String filed = lineTxt.substring(leftIndex, rightIndex);
            leftIndex = rightIndex + 1;
            ZmDataMap.put(Integer.valueOf(j), filed);
          }
          ZmDataList.add(ZmDataMap);
        }
        read.close();
      } else {
        System.out.println("找不到指定的文件");
      }
    } catch (Exception e) {
      System.out.println("读取文件内容出错");
      e.printStackTrace();
    }
    for (int i = 0; i < ZmDataList.size(); i++) {
      System.out.println("行数: " + (i + 1));
      Map<Integer, String> ZmDataMapTmp = ZmDataList.get(i);

      for (Iterator<Integer> it = ZmDataMapTmp.keySet().iterator(); it.hasNext(); ) {
        Integer key = it.next();
        String value = ZmDataMapTmp.get(key);
        System.out.println("序号：" + key + " 值: '" + value + "'");
      }
    }
    return ZmDataList;
  }


  public static void main(String[] args) {
    System.out.println(AcpService.encryptTrack("12", "utf-8"));
    SDKConfig.getConfig().loadPropertiesFromSrc();

    Map<String, String> customerInfoMap = new HashMap<>();



    customerInfoMap.put("phoneNo", "13552535506");







    parseZMFile("C:\\Users\\wulh\\Desktop\\802310048993424_20150905\\INN15090588ZM_802310048993424");
  }
}

