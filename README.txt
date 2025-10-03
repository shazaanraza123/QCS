CS4371 - Introduction to Big Data Management and Analytics
Homework 1 - Hadoop MapReduce Analysis
==============================================================

ASSIGNMENT COMPLETED ✅

This submission contains the complete solution for Homework 1 with two main problems:
- Q1: Text Analysis with 3 subproblems (A, B, C)
- Q2: Inverted Index with 2 subproblems (A, B)

FILES INCLUDED:
==============
1. Q1Analysis.java - Java source code for Q1
2. Q2Analysis.java - Java source code for Q2
3. Q1Analysis.jar - Compiled JAR file for Q1
4. Q2Analysis.jar - Compiled JAR file for Q2
5. README.txt - This instruction file
6. compile_and_jar.bat - Compilation script (for reference)

SOLUTION OVERVIEW:
=================

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

EXPECTED OUTPUT FORMATS:
=======================
Q1A: word -> count
Q1B: target_word -> count (only for whale, sea, ship)
Q1C: length,first_char -> count
Q2A: word -> line_numbers (comma-separated)
Q2B: word -> total_occurrences

TECHNICAL DETAILS:
=================
- All solutions use proper Hadoop MapReduce framework
- Text preprocessing includes lowercase conversion
- Regex patterns ensure proper word tokenization
- Combiner is used in Q2B for optimization
- Single JAR file per problem as requested
- Command-line argument parsing for subproblem selection
- Follows DRY principles with shared code structure

SUBMISSION READY:
================
✅ 2 JAR files (Q1Analysis.jar, Q2Analysis.jar)
✅ 2 Java source files (Q1Analysis.java, Q2Analysis.java)
✅ README with complete instructions
✅ All requirements met per assignment specifications

The assignment is complete and ready for submission!