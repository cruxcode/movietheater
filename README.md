# Walmart Movie Theater Challenge

**Assumptions**
- It is assumed that all the seats allocated for one request should be in the same row. This implies that a user cannot request for more than 20 seats. If the input file contains requests with more than 20, those requests will be ignored.
- Their can be many KPIs to signify customer satisfaction. Here I am trying to maximize a simple KPI i.e. accomodate maximum number of people. I believe that for a customer the biggest source of dissatisfaction would be not being able to book tickets.
- If a request cannot be satisfied, then nothing will be written in the output file for that particular request.

**About Codebase**
- src/main/java/movie/App.java - entry point for the command line
- src/main/java/movie/AllocatorResult.java - a class that contains the result of a request
- src/main/java/movie/AllocatorStrategy.java - an interface that enables strategy pattern over algorithms to allocate seats
- src/main/java/movie/BaselineAllocator.java - a class that implements AllocatorStrategy interface
- src/main/java/movie/RandomAllocator.java - a class that implements AllocatorStrategy interface
- src/main/java/movie/PsuedoMatrix.java - a class that implements an abstract data structure to represent a movie theater
- src/main/java/movie/FreeRange.java - a simple class to represent and manipulate continous seats

**System Design**
The system design aims to satisfy following type of requirements in future:
- The shape of theater can be very different than just a simple matrix. (PsuedoMatrix.java provides an API that can give a sense of matrix but it is not an actual matrix)
- Different algorithm can used for differnt theaters depending upon local laws. (AllocatorStrategy.java provides the interface for Strategy Pattern)
- The requirement of buffer seats to be left empty can change in future. (PsuedoMatrix.java takes buffer seats as parameter)
- There can be many possible combination of the shape of matrix and the algorithm to allocate seats.

**Practices followed**
Following noteworthy code practices have been taken into account to prevent code smells:
- Keep methods small i.e. less than 10 lines of code.
- Method names should explain what the method does, thus, making code readable and reducing the need to add comments
- Prevent bloating of a class with too many methods i.e. less than 15 methods

**Build Dependencies**
- Maven 3.6.3 or later

**Build Command**

`mvn package`

**Run Command**

`java -Dfile.encoding=UTF-8 -classpath <path_of_this_repo>/target/classes:<path_of_maven_repo>/org/apache/logging/log4j/log4j-api/2.14.0/log4j-api-2.14.0.jar:<path_of_maven_repo>/org/apache/logging/log4j/log4j-core/2.14.0/log4j-core-2.14.0.jar movie.App <absolute_path_of_input_file>`

