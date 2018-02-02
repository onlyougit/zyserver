部署项目注意事项：
1、手动打开ScheduledConfiguration.java中的任务，把注释打开就行：
//@Scheduled(fixedDelay = 3000)
//@Scheduled(cron="0 3 15 ? * *")
//@Scheduled(cron="0 4 15 ? * *")
//@Scheduled(cron="0 5 15 ? * *")
2、把application.yml中的激活环境改成生产环境：
  profiles:
    active: prod
3、使用mvn/mvnw clean package -Dmaven.test.skip=true来打包，否则不会生成日志文件
4、解压war包：jar xvf checkwinpay.war

https证书：
1、keytool -genkey -alias dyb_tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore D:\SSL\keystore.p12 -validity 36500
2、证书：
openssl genrsa -des3 -out dyb.key 1024
openssl req -new -key dyb.key -out dyb.csr
openssl rsa -in dyb.key.org -out dyb.key
openssl x509 -req -days 36500 -in dyb.csr -signkey dyb.key -out dyb.crt
subject=/C=zh/ST=xiamen/L=xiamen/O=dyb/OU=yinglibao/CN=wangweibin/emailAddress=996041341@qq.com

字符串缓冲区最大长度：
1、show VARIABLES like '%max_allowed_packet%';
2、set global max_allowed_packet = 1024*1024;【1M】
3、重启mysql服务才能生效。

https请求：
C:\Program Files\Java\jdk1.8.0_121\jre\lib\security\java.security
注释掉下面两行：
jdk.certpath.disabledAlgorithms
jdk.tls.disabledAlgorithms

ftp安装：
yum install -y vsftp
vsftp安装目录：/etc/vsftpd
启动vsftp服务：service vsftpd start
查看服务名称：netstat -lptn或netstat -antup | grep ftp
设置开机启动：chkconfig vsftpd on
