JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES =	\
	MinHeapNodeImpl.java	\
	Rbt.java	\
	MinHeap.java	\
	risingCity.java	\
	RbtNodeImpl.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class