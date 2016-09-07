@echo off
cd/d d:/dest
rmdir/s/q com
java -jar D:\soft\mybatis-generator-core-1.3.2-bundle\lib\mybatis-generator-core-1.3.2.jar -configfile D:\javawork\future\res\mybatis\generator-huanghao.xml
pause