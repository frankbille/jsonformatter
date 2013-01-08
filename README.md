JSON Formatter [![Build Status](https://travis-ci.org/frankbille/jsonformatter.png?branch=master)](https://travis-ci.org/frankbille/jsonformatter)
==============

Very simple JSON formatter, which can help in determining why a JSON file is invalid.

When dealing with very large JSON files, it is easy to get lost in them and difficult
to find mistakes. With this tool you can format a JSON file, even if it is not well-formed,
and look at the output in a text editor, to see wrongly formatted lines. This will
be an indication of that something is wrong.


Usage
-----

To use you have two options:

1. Download the latest binary, which is built everytime a push is made:  
   http://github-files.s3.amazonaws.com/jsonformatter/target/jsonformatter.jar
   
   Run application  
   ```java -jar jsonformatter.jar```

2. Checkout the source code, build using [Maven][maven] and run the built artifact
   1. Checkout the code  
      ```git clone https://github.com/frankbille/jsonformatter.git```
   2. Go into the checked out folder  
      ```cd jsonformatter```
   3. Build project using [Maven][maven]  
      ```mvn package```
   4. Run application  
      ```java -jar target/jsonformatter.jar```


[maven]: http://maven.apache.org