#!/bin/bash
ant;
ant make-jar;
java -Xmx1g -jar jars/assembler.jar test_cases/3.asm a.out;
diff -s a.out test_cases/3.out;
