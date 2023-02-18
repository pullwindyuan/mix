package org.xxpay.shop.demo;

import sun.security.pkcs.PKCS7;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class getApkCert {
    public static void main(String[] args) throws CertificateException, IOException {
        //FileInputStream fis = new FileInputStream("F:/app/META-INF/CERT.RSA");
        //FileInputStream fis = new FileInputStream("F:/app3/META-INF/CERT.RSA");
        FileInputStream fis = new FileInputStream("F:/META-INF/BIBI.RSA");
        PKCS7 pkcs7 = new PKCS7(fis);
        X509Certificate publicKey = pkcs7.getCertificates()[0];
        System.out.println("issuer1:" + publicKey.getIssuerDN());
        System.out.println("subject2:" + publicKey.getSubjectDN());
        System.out.println(publicKey.getPublicKey());
    }
}