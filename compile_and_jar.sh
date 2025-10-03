#!/bin/bash

echo "Compiling and creating JAR files for Q1 and Q2..."

# Set Hadoop classpath (adjust path as needed)
export HADOOP_CLASSPATH=$HADOOP_HOME/share/hadoop/common/*:$HADOOP_HOME/share/hadoop/mapreduce/*:$HADOOP_HOME/share/hadoop/common/lib/*

# Create output directory
mkdir -p output

echo "Compiling Q1Analysis.java..."
javac -cp "$HADOOP_CLASSPATH" Q1Analysis.java
if [ $? -ne 0 ]; then
    echo "Error compiling Q1Analysis.java"
    exit 1
fi

echo "Compiling Q2Analysis.java..."
javac -cp "$HADOOP_CLASSPATH" Q2Analysis.java
if [ $? -ne 0 ]; then
    echo "Error compiling Q2Analysis.java"
    exit 1
fi

echo "Creating Q1Analysis.jar..."
jar cf Q1Analysis.jar Q1Analysis*.class
if [ $? -ne 0 ]; then
    echo "Error creating Q1Analysis.jar"
    exit 1
fi

echo "Creating Q2Analysis.jar..."
jar cf Q2Analysis.jar Q2Analysis*.class
if [ $? -ne 0 ]; then
    echo "Error creating Q2Analysis.jar"
    exit 1
fi

echo "Cleaning up class files..."
rm -f *.class

echo ""
echo "JAR files created successfully:"
echo "- Q1Analysis.jar"
echo "- Q2Analysis.jar"
echo ""
echo "You can now run the Hadoop commands as specified in the README."
