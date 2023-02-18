 package org.hlpay.base.channel.unionpay.sdk;

 import java.io.ByteArrayInputStream;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FilenameFilter;
 import java.io.IOException;
 import java.io.InputStream;
 import java.math.BigInteger;
 import java.security.KeyFactory;
 import java.security.KeyStore;
 import java.security.KeyStoreException;
 import java.security.NoSuchAlgorithmException;
 import java.security.NoSuchProviderException;
 import java.security.PrivateKey;
 import java.security.Provider;
 import java.security.PublicKey;
 import java.security.Security;
 import java.security.UnrecoverableKeyException;
 import java.security.cert.CertPathBuilder;
 import java.security.cert.CertPathBuilderException;
 import java.security.cert.CertStore;
 import java.security.cert.CertificateException;
 import java.security.cert.CertificateFactory;
 import java.security.cert.CollectionCertStoreParameters;
 import java.security.cert.PKIXBuilderParameters;
 import java.security.cert.PKIXCertPathBuilderResult;
 import java.security.cert.TrustAnchor;
 import java.security.cert.X509CertSelector;
 import java.security.cert.X509Certificate;
 import java.security.spec.RSAPublicKeySpec;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Map;
 import java.util.Set;
 import java.util.concurrent.ConcurrentHashMap;
 import org.bouncycastle.jce.provider.BouncyCastleProvider;























 public class CertUtil
 {
   private static KeyStore keyStore = null;

   private static X509Certificate encryptCert = null;

   private static PublicKey encryptTrackKey = null;

   private static X509Certificate validateCert = null;

   private static X509Certificate middleCert = null;

   private static X509Certificate rootCert = null;

   private static Map<String, X509Certificate> certMap = new HashMap<>();

   private static final Map<String, KeyStore> keyStoreMap = new ConcurrentHashMap<>();

   static {
     init();
   }




   private static void init() {
     try {
       addProvider();
       initSignCert();
       initMiddleCert();
       initRootCert();
       initEncryptCert();
       initTrackKey();
       initValidateCertFromDir();
     } catch (Exception e) {
       LogUtil.writeErrorLog("init失败。（如果是用对称密钥签名的可无视此异常。）", e);
     }
   }




   private static void addProvider() {
     if (Security.getProvider("BC") == null) {
       LogUtil.writeLog("add BC provider");
       Security.addProvider((Provider)new BouncyCastleProvider());
     } else {
       Security.removeProvider("BC");
       Security.addProvider((Provider)new BouncyCastleProvider());
       LogUtil.writeLog("re-add BC provider");
     }
     printSysInfo();
   }




   private static void initSignCert() {
     if (!"01".equals(SDKConfig.getConfig().getSignMethod())) {
       LogUtil.writeLog("非rsa签名方式，不加载签名证书。");
       return;
     }
     if (SDKConfig.getConfig().getSignCertPath() == null ||
       SDKConfig.getConfig().getSignCertPwd() == null ||
       SDKConfig.getConfig().getSignCertType() == null) {
       LogUtil.writeErrorLog("WARN: acpsdk.signCert.path或acpsdk.signCert.pwd或acpsdk.signCert.type为空。 停止加载签名证书。");

       return;
     }
     if (null != keyStore) {
       keyStore = null;
     }
     try {
       keyStore = getKeyInfo(SDKConfig.getConfig().getSignCertPath(),
           SDKConfig.getConfig().getSignCertPwd(),
           SDKConfig.getConfig().getSignCertType());
       LogUtil.writeLog("InitSignCert Successful. CertId=[" +
           getSignCertId() + "]");
     } catch (IOException e) {
       LogUtil.writeErrorLog("InitSignCert Error", e);
     }
   }




   private static void initMiddleCert() {
     LogUtil.writeLog("加载中级证书==>" + SDKConfig.getConfig().getMiddleCertPath());
     if (!SDKUtil.isEmpty(SDKConfig.getConfig().getMiddleCertPath())) {
       middleCert = initCert(SDKConfig.getConfig().getMiddleCertPath());
       LogUtil.writeLog("Load MiddleCert Successful");
     } else {
       LogUtil.writeLog("WARN: acpsdk.middle.path is empty");
     }
   }




   private static void initRootCert() {
     LogUtil.writeLog("加载根证书==>" + SDKConfig.getConfig().getRootCertPath());
     if (!SDKUtil.isEmpty(SDKConfig.getConfig().getRootCertPath())) {
       rootCert = initCert(SDKConfig.getConfig().getRootCertPath());
       LogUtil.writeLog("Load RootCert Successful");
     } else {
       LogUtil.writeLog("WARN: acpsdk.rootCert.path is empty");
     }
   }




   private static void initEncryptCert() {
     LogUtil.writeLog("加载敏感信息加密证书==>" + SDKConfig.getConfig().getEncryptCertPath());
     if (!SDKUtil.isEmpty(SDKConfig.getConfig().getEncryptCertPath())) {
       encryptCert = initCert(SDKConfig.getConfig().getEncryptCertPath());
       LogUtil.writeLog("Load EncryptCert Successful");
     } else {
       LogUtil.writeLog("WARN: acpsdk.encryptCert.path is empty");
     }
   }




   private static void initTrackKey() {
     if (!SDKUtil.isEmpty(SDKConfig.getConfig().getEncryptTrackKeyModulus()) &&
       !SDKUtil.isEmpty(SDKConfig.getConfig().getEncryptTrackKeyExponent())) {
       encryptTrackKey = getPublicKey(SDKConfig.getConfig().getEncryptTrackKeyModulus(),
           SDKConfig.getConfig().getEncryptTrackKeyExponent());
       LogUtil.writeLog("LoadEncryptTrackKey Successful");
     } else {
       LogUtil.writeLog("WARN: acpsdk.encryptTrackKey.modulus or acpsdk.encryptTrackKey.exponent is empty");
     }
   }




   private static void initValidateCertFromDir() {
     if (!"01".equals(SDKConfig.getConfig().getSignMethod())) {
       LogUtil.writeLog("非rsa签名方式，不加载验签证书。");
       return;
     }
     certMap.clear();
     String dir = SDKConfig.getConfig().getValidateCertDir();
     LogUtil.writeLog("加载验证签名证书目录==>" + dir + " 注：如果请求报文中version=5.1.0那么此验签证书目录使用不到，可以不需要设置（version=5.0.0必须设置）。");
     if (SDKUtil.isEmpty(dir)) {
       LogUtil.writeErrorLog("WARN: acpsdk.validateCert.dir is empty");
       return;
     }
     CertificateFactory cf = null;
     FileInputStream in = null;
     try {
       cf = CertificateFactory.getInstance("X.509", "BC");
     } catch (NoSuchProviderException e) {
       LogUtil.writeErrorLog("LoadVerifyCert Error: No BC Provider", e);
       return;
     } catch (CertificateException e) {
       LogUtil.writeErrorLog("LoadVerifyCert Error", e);
       return;
     }
     File fileDir = new File(dir);
     File[] files = fileDir.listFiles(new CerFilter());
     for (int i = 0; i < files.length; i++) {
       File file = files[i];
     }

























     LogUtil.writeLog("LoadVerifyCert Finish");
   }







   private static void loadSignCert(String certFilePath, String certPwd) {
     KeyStore keyStore = null;
     try {
       keyStore = getKeyInfo(certFilePath, certPwd, "PKCS12");
       keyStoreMap.put(certFilePath, keyStore);
       LogUtil.writeLog("LoadRsaCert Successful");
     } catch (IOException e) {
       LogUtil.writeErrorLog("LoadRsaCert Error", e);
     }
   }






   private static X509Certificate initCert(String path) {
     X509Certificate encryptCertTemp = null;
     CertificateFactory cf = null;
     FileInputStream in = null;
     try {
       cf = CertificateFactory.getInstance("X.509", "BC");
       in = new FileInputStream(path);
       encryptCertTemp = (X509Certificate)cf.generateCertificate(in);

       LogUtil.writeLog("[" + path + "][CertId=" + encryptCertTemp
           .getSerialNumber().toString() + "]");
     } catch (CertificateException e) {
       LogUtil.writeErrorLog("InitCert Error", e);
     } catch (FileNotFoundException e) {
       LogUtil.writeErrorLog("InitCert Error File Not Found", e);
     } catch (NoSuchProviderException e) {
       LogUtil.writeErrorLog("LoadVerifyCert Error No BC Provider", e);
     } finally {
       if (null != in) {
         try {
           in.close();
         } catch (IOException e) {
           LogUtil.writeErrorLog(e.toString());
         }
       }
     }
     return encryptCertTemp;
   }






   public static PrivateKey getSignCertPrivateKey() {
     try {
       Enumeration<String> aliasenum = keyStore.aliases();
       String keyAlias = null;
       if (aliasenum.hasMoreElements()) {
         keyAlias = aliasenum.nextElement();
       }
       PrivateKey privateKey = (PrivateKey)keyStore.getKey(keyAlias,
           SDKConfig.getConfig().getSignCertPwd().toCharArray());
       return privateKey;
     } catch (KeyStoreException e) {
       LogUtil.writeErrorLog("getSignCertPrivateKey Error", e);
       return null;
     } catch (UnrecoverableKeyException e) {
       LogUtil.writeErrorLog("getSignCertPrivateKey Error", e);
       return null;
     } catch (NoSuchAlgorithmException e) {
       LogUtil.writeErrorLog("getSignCertPrivateKey Error", e);
       return null;
     }
   }






   public static PrivateKey getSignCertPrivateKeyByStoreMap(String certPath, String certPwd) {
     if (!keyStoreMap.containsKey(certPath)) {
       loadSignCert(certPath, certPwd);
     }

     try {
       Enumeration<String> aliasenum = ((KeyStore)keyStoreMap.get(certPath)).aliases();
       String keyAlias = null;
       if (aliasenum.hasMoreElements()) {
         keyAlias = aliasenum.nextElement();
       }

       PrivateKey privateKey = (PrivateKey)((KeyStore)keyStoreMap.get(certPath)).getKey(keyAlias, certPwd.toCharArray());
       return privateKey;
     } catch (KeyStoreException e) {
       LogUtil.writeErrorLog("getSignCertPrivateKeyByStoreMap Error", e);
       return null;
     } catch (UnrecoverableKeyException e) {
       LogUtil.writeErrorLog("getSignCertPrivateKeyByStoreMap Error", e);
       return null;
     } catch (NoSuchAlgorithmException e) {
       LogUtil.writeErrorLog("getSignCertPrivateKeyByStoreMap Error", e);
       return null;
     }
   }






   public static PublicKey getEncryptCertPublicKey() {
     if (null == encryptCert) {
       String path = SDKConfig.getConfig().getEncryptCertPath();
       if (!SDKUtil.isEmpty(path)) {
         encryptCert = initCert(path);
         return encryptCert.getPublicKey();
       }
       LogUtil.writeErrorLog("acpsdk.encryptCert.path is empty");
       return null;
     }

     return encryptCert.getPublicKey();
   }





   public static void resetEncryptCertPublicKey() {
     encryptCert = null;
   }






   public static PublicKey getEncryptTrackPublicKey() {
     if (null == encryptTrackKey) {
       initTrackKey();
     }
     return encryptTrackKey;
   }







   public static PublicKey getValidatePublicKey(String certId) {
     X509Certificate cf = null;
     if (certMap.containsKey(certId)) {

       cf = certMap.get(certId);
       return cf.getPublicKey();
     }

     initValidateCertFromDir();
     if (certMap.containsKey(certId)) {

       cf = certMap.get(certId);
       return cf.getPublicKey();
     }
     LogUtil.writeErrorLog("缺少certId=[" + certId + "]对应的验签证书.");
     return null;
   }








   public static String getSignCertId() {
     try {
       Enumeration<String> aliasenum = keyStore.aliases();
       String keyAlias = null;
       if (aliasenum.hasMoreElements()) {
         keyAlias = aliasenum.nextElement();
       }

       X509Certificate cert = (X509Certificate)keyStore.getCertificate(keyAlias);
       return cert.getSerialNumber().toString();
     } catch (Exception e) {
       LogUtil.writeErrorLog("getSignCertId Error", e);
       return null;
     }
   }






   public static String getEncryptCertId() {
     if (null == encryptCert) {
       String path = SDKConfig.getConfig().getEncryptCertPath();
       if (!SDKUtil.isEmpty(path)) {
         encryptCert = initCert(path);
         return encryptCert.getSerialNumber().toString();
       }
       LogUtil.writeErrorLog("acpsdk.encryptCert.path is empty");
       return null;
     }

     return encryptCert.getSerialNumber().toString();
   }















   private static KeyStore getKeyInfo(String pfxkeyfile, String keypwd, String type) throws IOException {
     LogUtil.writeLog("加载签名证书==>" + pfxkeyfile);
     FileInputStream fis = null;
     try {
       KeyStore ks = KeyStore.getInstance(type, "BC");
       LogUtil.writeLog("Load RSA CertPath=[" + pfxkeyfile + "],Pwd=[" + keypwd + "],type=[" + type + "]");
       fis = new FileInputStream(pfxkeyfile);
       char[] nPassword = null;
       nPassword = (null == keypwd || "".equals(keypwd.trim())) ? null : keypwd.toCharArray();
       if (null != ks) {
         ks.load(fis, nPassword);
       }
       return ks;
     } catch (Exception e) {
       LogUtil.writeErrorLog("getKeyInfo Error", e);
       return null;
     } finally {
       if (null != fis) {
         fis.close();
       }
     }
   }






   public static String getCertIdByKeyStoreMap(String certPath, String certPwd) {
     if (!keyStoreMap.containsKey(certPath))
     {
       loadSignCert(certPath, certPwd);
     }
     return getCertIdIdByStore(keyStoreMap.get(certPath));
   }






   private static String getCertIdIdByStore(KeyStore keyStore) {
     Enumeration<String> aliasenum = null;
     try {
       aliasenum = keyStore.aliases();
       String keyAlias = null;
       if (aliasenum.hasMoreElements()) {
         keyAlias = aliasenum.nextElement();
       }

       X509Certificate cert = (X509Certificate)keyStore.getCertificate(keyAlias);
       return cert.getSerialNumber().toString();
     } catch (KeyStoreException e) {
       LogUtil.writeErrorLog("getCertIdIdByStore Error", e);
       return null;
     }
   }










   private static PublicKey getPublicKey(String modulus, String exponent) {
     try {
       BigInteger b1 = new BigInteger(modulus);
       BigInteger b2 = new BigInteger(exponent);
       KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
       RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
       return keyFactory.generatePublic(keySpec);
     } catch (Exception e) {
       LogUtil.writeErrorLog("构造RSA公钥失败：" + e);
       return null;
     }
   }







   public static X509Certificate genCertificateByStr(String x509CertString) {
     X509Certificate x509Cert = null;
     try {
       CertificateFactory cf = CertificateFactory.getInstance("X.509", "BC");

       InputStream tIn = new ByteArrayInputStream(x509CertString.getBytes("ISO-8859-1"));
       x509Cert = (X509Certificate)cf.generateCertificate(tIn);
     } catch (Exception e) {
       LogUtil.writeErrorLog("gen certificate error", e);
     }
     return x509Cert;
   }





   public static X509Certificate getMiddleCert() {
     if (null == middleCert) {
       String path = SDKConfig.getConfig().getMiddleCertPath();
       if (!SDKUtil.isEmpty(path)) {
         initMiddleCert();
       } else {
         LogUtil.writeErrorLog("acpsdk.middleCert.path not set in acp_sdk.properties");
         return null;
       }
     }
     return middleCert;
   }





   public static X509Certificate getRootCert() {
     if (null == rootCert) {
       String path = SDKConfig.getConfig().getRootCertPath();
       if (!SDKUtil.isEmpty(path)) {
         initRootCert();
       } else {
         LogUtil.writeErrorLog("acpsdk.rootCert.path not set in acp_sdk.properties");
         return null;
       }
     }
     return rootCert;
   }






   private static String getIdentitiesFromCertficate(X509Certificate aCert) {
     String tDN = aCert.getSubjectDN().toString();
     String tPart = "";
     if (tDN != null) {
       String[] tSplitStr = tDN.substring(tDN.indexOf("CN=")).split("@");
       if (tSplitStr != null && tSplitStr.length > 2 && tSplitStr[2] != null)
       {
         tPart = tSplitStr[2]; }
     }
     return tPart;
   }







   private static boolean verifyCertificateChain(X509Certificate cert) {
     if (null == cert) {
       LogUtil.writeErrorLog("cert must Not null");
       return false;
     }

     X509Certificate middleCert = getMiddleCert();
     if (null == middleCert) {
       LogUtil.writeErrorLog("middleCert must Not null");
       return false;
     }

     X509Certificate rootCert = getRootCert();
     if (null == rootCert) {
       LogUtil.writeErrorLog("rootCert or cert must Not null");
       return false;
     }


     try {
       X509CertSelector selector = new X509CertSelector();
       selector.setCertificate(cert);

       Set<TrustAnchor> trustAnchors = new HashSet<>();
       trustAnchors.add(new TrustAnchor(rootCert, null));
       PKIXBuilderParameters pkixParams = new PKIXBuilderParameters(trustAnchors, selector);


       Set<X509Certificate> intermediateCerts = new HashSet<>();
       intermediateCerts.add(rootCert);
       intermediateCerts.add(middleCert);
       intermediateCerts.add(cert);

       pkixParams.setRevocationEnabled(false);

       CertStore intermediateCertStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(intermediateCerts), "BC");

       pkixParams.addCertStore(intermediateCertStore);

       CertPathBuilder builder = CertPathBuilder.getInstance("PKIX", "BC");



       PKIXCertPathBuilderResult result = (PKIXCertPathBuilderResult)builder.build(pkixParams);
       LogUtil.writeLog("verify certificate chain succeed.");
       return true;
     } catch (CertPathBuilderException e) {
       LogUtil.writeErrorLog("verify certificate chain fail.", e);
     } catch (Exception e) {
       LogUtil.writeErrorLog("verify certificate chain exception: ", e);
     }
     return false;
   }











   public static boolean verifyCertificate(X509Certificate cert) {
     if (null == cert) {
       LogUtil.writeErrorLog("cert must Not null");
       return false;
     }
     try {
       cert.checkValidity();

       if (!verifyCertificateChain(cert)) {
         return false;
       }
     } catch (Exception e) {
       LogUtil.writeErrorLog("verifyCertificate fail", e);
       return false;
     }

     if (SDKConfig.getConfig().isIfValidateCNName()) {

       if (!"中国银联股份有限公司".equals(getIdentitiesFromCertficate(cert))) {
         LogUtil.writeErrorLog("cer owner is not CUP:" + getIdentitiesFromCertficate(cert));
         return false;
       }

     }
     else if (!"中国银联股份有限公司".equals(getIdentitiesFromCertficate(cert)) &&
       !"00040000:SIGN".equals(getIdentitiesFromCertficate(cert))) {
       LogUtil.writeErrorLog("cer owner is not CUP:" + getIdentitiesFromCertficate(cert));
       return false;
     }

     return true;
   }




   private static void printSysInfo() {
     LogUtil.writeLog("================= SYS INFO begin====================");
     LogUtil.writeLog("os_name:" + System.getProperty("os.name"));
     LogUtil.writeLog("os_arch:" + System.getProperty("os.arch"));
     LogUtil.writeLog("os_version:" + System.getProperty("os.version"));
     LogUtil.writeLog("java_vm_specification_version:" +
         System.getProperty("java.vm.specification.version"));
     LogUtil.writeLog("java_vm_specification_vendor:" +
         System.getProperty("java.vm.specification.vendor"));
     LogUtil.writeLog("java_vm_specification_name:" +
         System.getProperty("java.vm.specification.name"));
     LogUtil.writeLog("java_vm_version:" +
         System.getProperty("java.vm.version"));
     LogUtil.writeLog("java_vm_name:" + System.getProperty("java.vm.name"));
     LogUtil.writeLog("java.version:" + System.getProperty("java.version"));
     LogUtil.writeLog("java.vm.vendor=[" + System.getProperty("java.vm.vendor") + "]");
     LogUtil.writeLog("java.version=[" + System.getProperty("java.version") + "]");
     printProviders();
     LogUtil.writeLog("================= SYS INFO end=====================");
   }




   private static void printProviders() {
     LogUtil.writeLog("Providers List:");
     Provider[] providers = Security.getProviders();
     for (int i = 0; i < providers.length; i++) {
       LogUtil.writeLog((i + 1) + "." + providers[i].getName());
     }
   }



   static class CerFilter
     implements FilenameFilter
   {
     public boolean isCer(String name) {
       if (name.toLowerCase().endsWith(".cer")) {
         return true;
       }
       return false;
     }

     public boolean accept(File dir, String name) {
       return isCer(name);
     }
   }
 }
