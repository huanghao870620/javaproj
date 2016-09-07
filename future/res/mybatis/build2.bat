@echo off
cd/d d:/dest
rmdir/s/q com
java -jar D:\soft\mybatis-generator-core-1.3.2-bundle\lib\mybatis-generator-core-1.3.2.jar -configfile d:\javawork\future\res\mybatis\generator2.xml
pause