@echo off

REM Set the build tool and application variables
SET BUILD_TOOL=maven
SET APPLICATION=transactionsApplication

REM Perform clean, compile, and run operations based on the selected build tool
IF "%BUILD_TOOL%"=="maven" (
    echo Cleaning and compiling with Maven...
    call mvn clean compile
    echo Running the application...
    call mvn spring-boot:run -Dspring-boot.run.arguments=--spring.application.name=%APPLICATION%
) ELSE IF "%BUILD_TOOL%"=="make" (
    echo Cleaning and compiling with Make...
    make clean compile
    echo Running the application...
    make run APPLICATION=%APPLICATION%
) ELSE IF "%BUILD_TOOL%"=="ant" (
    echo Cleaning and compiling with Ant...
    ant clean compile
    echo Running the application...
    ant run -Dapplication=%APPLICATION%
) ELSE (
    echo Invalid build tool selected.
    exit /b 1
)