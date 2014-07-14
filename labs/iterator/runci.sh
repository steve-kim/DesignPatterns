rm -f TestCustomIterator.class CustomIterator.class
javac -cp ./*:. TestCustomIterator.java CustomIterator.java
java -cp ./*:. org.junit.runner.JUnitCore TestCustomIterator
