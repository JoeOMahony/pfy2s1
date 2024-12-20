
# Programming Fundamentals 2 (Y2S1) Final Assignment
 ****For:**** Dave Hearne as part of Programming Fundamentals 2 (29916) [2024-2025] <br />
****Author:****  Joe O’Mahony<br />
****Student Number:**** 21788075 (Waterford Campus)<br />
****Email:**** [21788075@mail.wit.ie](mailto:21788075@mail.wit.ie) <br />
****Project Folder:****  [`joe_omahony/`]() <br />

  

## Walk-Through Video

****YouTube Video:****  [https://youtu.be/iuSKNHEJjNE](https://youtu.be/iuSKNHEJjNE)

  

## Overview

This repository contains my Programming Fundamentals 2 final assignment as per the specification provided, and passing all tests provided.
  

## Project Structure

```tree
joe_omahony/
├─ docs/           # Documentation (JavaDoc generated HTML)
├─ lib/            # Third-party libraries (XStream)
├─ out/            # Compiled output (production build)
├─ src/            # Source code
│  ├─ controllers/ # Package for controller classes handling Note logic (NoteAPI and NoteAPITest)
│  ├─ main/        # Package for entry-point class (Driver)
│  ├─ models/      # Package for data models representing Notes and Items (Item, Note, ItemTest, NoteTest)
│  └─ utils/       # Package for utility classes for general utilities, categories, input handling, etc. (Utilities, CategoryUtility, ScannerInput, UtilitiesTest, CategoryUtilityTest)
├─ joe_omahony.iml # IntelliJ project file (metadata)
├─ notes.xml       # Pre-populated data file with Notes and Items covering full program functionality (XStream)
└─ README.md       # This read-me
```
  

## Running the Application

1. Navigate to the [joe_omahony/src/main](src/main) directory<br />
2. Compile and run [Driver.java](src/main/Driver.java) using your preferred IDE or command line<br /> 

## Documentation
For detailed class and API information, open [docs/index.html](docs/index.html)  in your web browser. This includes details on available classes, methods, and utilities generated from JavaDoc.  

  

## Dependencies

•  **XStream (v1.4.21)**  for data serialisation/deserialisation. The JAR is located in the lib/ directory  <br />
•  **Jupiter JUnit** for test cases. The JAR is not provided in the project structure, if you do not have it imported already visit the [JUnit Maven repository page](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api)