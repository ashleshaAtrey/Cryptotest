JCC= javac
JFLAGS= -g

default: cryptotest.class

cryptotest.class: cryptotest.java
	$(JCC) $(JFLAGS) cryptotest.java

clean:
	$(RM) *.class
