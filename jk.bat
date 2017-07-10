@javac -cp ./jsonic-1.3.10/* GameManage.java
@echo off
if "%ERRORLEVEL%" == "0" (
java GameManage
)
