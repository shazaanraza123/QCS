@echo off
echo Downloading required Hadoop JAR files...

REM Create lib directory if it doesn't exist
if not exist "lib" mkdir lib

echo Downloading hadoop-common-3.3.4.jar...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-common/3.3.4/hadoop-common-3.3.4.jar' -OutFile 'lib\hadoop-common-3.3.4.jar'"

echo Downloading hadoop-mapreduce-client-core-3.3.4.jar...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-mapreduce-client-core/3.3.4/hadoop-mapreduce-client-core-3.3.4.jar' -OutFile 'lib\hadoop-mapreduce-client-core-3.3.4.jar'"

echo Downloading hadoop-hdfs-client-3.3.4.jar...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-hdfs-client/3.3.4/hadoop-hdfs-client-3.3.4.jar' -OutFile 'lib\hadoop-hdfs-client-3.3.4.jar'"

echo Downloading hadoop-annotations-3.3.4.jar...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-annotations/3.3.4/hadoop-annotations-3.3.4.jar' -OutFile 'lib\hadoop-annotations-3.3.4.jar'"

echo Downloading hadoop-auth-3.3.4.jar...
powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-auth/3.3.4/hadoop-auth-3.3.4.jar' -OutFile 'lib\hadoop-auth-3.3.4.jar'"

echo.
echo JAR files downloaded successfully!
echo You can now run: compile_and_jar.bat
pause
