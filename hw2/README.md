### CS451 Introduction to Parallel and Distributed Computing Assignment 2
**Illinois Institute of Technology**  

* Batkhishig Dulamsurankhor (bdulamsurankhor@hawk.iit.edu) - A20543498

### Used technology

I used java version 17 to write, compile and run the program, and used ant to compile the source code.

### Folder structure

```bash
.
├── README.md
├── app.config
├── bin
│   ├── app.config
│   ├── src
│   │   ├── BdulamsurankhorClient.class
│   │   ├── BdulamsurankhorHandler.class
│   │   └── BdulamsurankhorServer.class
│   └── words.txt
├── build.xml
├── deploy.sh
├── src
│   ├── BdulamsurankhorClient.java
│   ├── BdulamsurankhorHandler.java
│   └── BdulamsurankhorServer.java
├── words.txt
└── writeup.pdf
```

### Running the program

1. Go to source directory and run ant command to generate all java bytecode for peer and testing in bin folder.
    ```bash
    ant
    ``` 

2. Go to bin folder:
    ```bash
    cd bin
    ``` 

3. Setup configurations in ```app.config```:
    ```bash
    ip=3.16.22.216                # ip of the game server
    port=80                   # port of the game server
    thread=5                    # max threads (for game server)
    ``` 
4. Start server app.
    ```bash
    java src.BdulamsurankhorServer
    ``` 
5. Start client app.
    ```bash
    java src.BdulamsurankhorClient
    ``` 

### Deployment on a remote machine (optional)

1. Start a vm (assumption: debian)

2. Create a deployement key add it to the repo

3. Copy deploy.sh file and run it in the vm
    ```bash
    ./deploy.sh
    ```