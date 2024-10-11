### CS451 Introduction to Parallel and Distributed Computing Assignment 2
**Illinois Institute of Technology**  

* Batkhishig Dulamsurankhor (bdulamsurankhor@hawk.iit.edu) - A20543498

### Used technology

I used java version 17 to write, compile and run the program, and used maven to compile the source code.

### Folder structure

```bash
.
├── README.md
├── cert.crt
├── consumer.properties
├── dependency-reduced-pom.xml
├── doc # latex doc
│   ├── images
│   │   ├── image1.png
│   │   ├── image2.png
│   │   └── image3.png
│   ├── main.aux
│   ├── main.log
│   ├── main.pdf
│   └── main.tex
├── pom.xml
├── producer.properties
├── src # source code
│   ├── BdulamsurankhorConsumer.java
│   └── BdulamsurankhorProducer.java
└── target # compiled output
```

### Running the program

1. Go to source directory and run maven command to generate all java bytecode for the application.
    ```bash
    mvn clean package
    ``` 

2. Generate jks file from certificate.
    ```bash
    keytool -import -alias kafka-broker -file cert.crt -keystore kafka.truststore.jks -storepass changeit
    ``` 

3. Start consumer app.
    ```bash
    java -jar target/consumer.jar Topic_Three
    ``` 

4. Start producer app.
    ```bash
    java -jar target/producer.jar Topic_Three
    ``` 

### Screenshots for step 2
I have included the screenshots from step 2 in the writeup at the end.