CS4371 - Introduction to Big Data Management and Analytics
Homework 1 - Hadoop MapReduce Analysis
==============================================================

This submission contains the complete solution for Homework 1 with two main problems:
- Q1: Text Analysis with 3 subproblems (A, B, C)
- Q2: Inverted Index with 2 subproblems (A, B)

FILES INCLUDED:
==============
1. Q1Analysis.java - Java source code for Q1
2. Q2Analysis.java - Java source code for Q2
3. compile_and_jar.bat - Script to compile and create JAR files
4. Q1Analysis.jar - Compiled JAR file for Q1
5. Q2Analysis.jar - Compiled JAR file for Q2
6. README.txt - This instruction file

COMPILATION INSTRUCTIONS:
========================
1. Ensure Hadoop is properly installed and configured
2. Set HADOOP_HOME environment variable
3. Run the batch file: compile_and_jar.bat
   OR manually compile using:
   
   For Q1:
   javac -cp "%HADOOP_HOME%\share\hadoop\common\*;%HADOOP_HOME%\share\hadoop\mapreduce\*;%HADOOP_HOME%\share\hadoop\common\lib\*" Q1Analysis.java
   jar cf Q1Analysis.jar Q1Analysis*.class
   
   For Q2:
   javac -cp "%HADOOP_HOME%\share\hadoop\common\*;%HADOOP_HOME%\share\hadoop\mapreduce\*;%HADOOP_HOME%\share\hadoop\common\lib\*" Q2Analysis.java
   jar cf Q2Analysis.jar Q2Analysis*.class

EXECUTION COMMANDS:
==================

Q1 - Text Analysis:
-------------------
docker exec -it resourcemanager hadoop jar /tmp/Q1Analysis.jar /inputA /q1_output_A A
docker exec -it resourcemanager hadoop jar /tmp/Q1Analysis.jar /inputA /q1_output_B B
docker exec -it resourcemanager hadoop jar /tmp/Q1Analysis.jar /inputA /q1_output_C C

Q2 - Inverted Index:
--------------------
docker exec -it resourcemanager hadoop jar /tmp/Q2Analysis.jar /inputB /q2_output_A A
docker exec -it resourcemanager hadoop jar /tmp/Q2Analysis.jar /q2_output_A /q2_output_B B

DESCRIPTION OF SOLUTIONS:
========================

Q1 - Text Analysis:
-------------------
Part A (Word Count):
- Counts occurrences of every word in the input text
- Converts text to lowercase for consistent counting
- Uses regex pattern to tokenize words (letters only)

Part B (Target Words):
- Counts occurrences of specific words: "whale", "sea", "ship"
- Same tokenization as Part A
- Only emits key-value pairs for target words

Part C (Unique Words by Pattern):
- Groups words by (length, first character) pattern
- Counts unique words for each pattern
- Output format: "length,first_char" -> count

Q2 - Inverted Index:
--------------------
Part A (Inverted Index Construction):
- Maps each word to the line numbers where it appears
- Sorts line numbers for each word
- Output format: word -> comma-separated line numbers

Part B (Maximum Occurrence Words):
- Uses output from Part A as input
- Counts total occurrences per word using combiner
- Output format: word -> total_count

TECHNICAL DETAILS:
=================
- All solutions use proper Hadoop MapReduce framework
- Text preprocessing includes lowercase conversion
- Regex patterns ensure proper word tokenization
- Combiner is used in Q2B for optimization
- Single JAR file per problem as requested
- Command-line argument parsing for subproblem selection

EXPECTED OUTPUT FORMATS:
=======================
Q1A: word -> count
Q1B: target_word -> count
Q1C: length,first_char -> count
Q2A: word -> line_numbers
Q2B: word -> total_occurrences

NOTES:
======
- Make sure input directories exist in HDFS before running
- Output directories will be created automatically
- All solutions handle edge cases and empty inputs
- Code follows Hadoop best practices and DRY principles
