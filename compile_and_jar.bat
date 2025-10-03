@echo off
echo Compiling and creating JAR files for Q1 and Q2...

REM Set Hadoop classpath
set HADOOP_CLASSPATH=.\lib\hadoop-common-3.3.4.jar;.\lib\hadoop-mapreduce-client-core-3.3.4.jar;.\lib\hadoop-hdfs-client-3.3.4.jar;.\lib\hadoop-annotations-3.3.4.jar;.\lib\hadoop-auth-3.3.4.jar

REM Create output directory
if not exist "output" mkdir output

echo Compiling Q1Analysis.java...
javac -cp "%HADOOP_CLASSPATH%" Q1Analysis.java
if %errorlevel% neq 0 (
    echo Error compiling Q1Analysis.java
    pause
    exit /b 1
)

echo Compiling Q2Analysis.java...
javac -cp "%HADOOP_CLASSPATH%" Q2Analysis.java
if %errorlevel% neq 0 (
    echo Error compiling Q2Analysis.java
    pause
    exit /b 1
)

echo Creating Q1Analysis.jar...
jar cf Q1Analysis.jar Q1Analysis*.class
if %errorlevel% neq 0 (
    echo Error creating Q1Analysis.jar
    pause
    exit /b 1
)

echo Creating Q2Analysis.jar...
jar cf Q2Analysis.jar Q2Analysis*.class
if %errorlevel% neq 0 (
    echo Error creating Q2Analysis.jar
    pause
    exit /b 1
)

echo Cleaning up class files...
del *.class

echo.
echo JAR files created successfully:
echo - Q1Analysis.jar
echo - Q2Analysis.jar
echo.
echo You can now run the Hadoop commands as specified in the README.
pause
