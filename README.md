# LicenseClientJava
This is the client api for the LcsSys-Server written in Java

## Maven

```
<repositories>
  <repository>
    <id>LicenseClientJava-repository</id>
    <url>https://raw.github.com/LcsSys/LicenseClientJava/repository/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>pw.nergon</groupId>
    <artifactId>LicenseClient</artifactId>
    <version>1.0</version>
</dependency>
```


 ## Normal JAR
 
 Import the normal jar as libary into your Project and you can use it
 
 ## Usage
 
 ```java
 License client = new LicenseClient("serverip",5678);
 client.configSSL("trustStorePass", "trustStore", "keyStorePass", "keyStore");
 if(!client.checkKey("key", true)) {
  System.out.println("Key is wrong");
  System.exit(1);
 }
 ```
  
## Genertaing Certificates

Please use our tutorial or our installer to create certificates

