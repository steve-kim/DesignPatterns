rm -f TestBinaryTreeCanonical.class BinaryTreeCanonical.class
javac -cp ./*:. TestBinaryTreeCanonical.java BinaryTreeCanonical.java
java -cp ./*:. org.junit.runner.JUnitCore TestBinaryTreeCanonical
result=$?
echo "result is " $result
