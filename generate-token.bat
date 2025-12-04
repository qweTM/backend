@echo off
set /p username=请输入用户名: 
java -cp target/classes com.example.forum.util.TokenGenerator %username%
pause