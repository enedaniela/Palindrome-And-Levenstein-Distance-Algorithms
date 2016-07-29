.PHONY: build clean run

build:
	ls classes || mkdir classes;
	javac *.java -d classes;
run-p1: 
	java -Xmx512m -cp classes P1	
run-p2:
	java -Xmx512m -cp classes P2
clean:
	rm -r classes
