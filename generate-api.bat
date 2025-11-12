@echo off

rem 确保脚本以ANSI编码保存

setlocal enabledelayedexpansion

echo 检查Java环境...
java -version > nul 2>&1
if %errorlevel% neq 0 (
    echo 错误：未找到Java环境，请确保已安装JDK并配置JAVA_HOME环境变量
    pause
    exit /b 1
)

echo Java环境检查通过

set JAR_FILE=openapi-generator-cli-6.6.0.jar
set OUTPUT_DIR=.
set API_PACKAGE=com.example.forum.api
set MODEL_PACKAGE=com.example.forum.model

rem 检查JAR文件是否存在，如果不存在则提示下载
if not exist "%JAR_FILE%" (
    echo 警告：未找到OpenAPI Generator JAR包
    echo 请手动下载：https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/6.6.0/openapi-generator-cli-6.6.0.jar
    echo 并将其放在当前目录下
    pause
    exit /b 1
)

echo 开始生成API代码...
java -jar "%JAR_FILE%" generate ^
    -i openapi.yaml ^
    -g spring ^
    -o "%OUTPUT_DIR%" ^
    --api-package %API_PACKAGE% ^
    --model-package %MODEL_PACKAGE% ^
    --library spring-boot ^
    --additional-properties=interfaceOnly=true,useSpringController=true,useBeanValidation=true,dateLibrary=java8,useTags=true,useSwaggerAnnotations=true,hideGenerationTimestamp=true,useSpringdocV3=true

if %errorlevel% equ 0 (
    echo 清理多余文件...
    if exist "pom.xml" del "pom.xml" /q
    if exist ".openapi-generator-ignore" del ".openapi-generator-ignore" /q
    if exist ".openapi-generator" rmdir /s /q ".openapi-generator"
    if exist "docs" rmdir /s /q "docs"
    if exist "src\test" rmdir /s /q "src\test"
    
    echo 代码生成成功！
    echo 生成的API接口位于：%API_PACKAGE%
    echo 生成的数据模型位于：%MODEL_PACKAGE%
) else (
    echo 代码生成失败！
)

endlocal
pause