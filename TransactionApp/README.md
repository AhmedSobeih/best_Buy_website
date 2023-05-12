## To run the transactions app you need to do the following:
1. Run the postgres database:  
    a. login to postgress with your username and password (if prompted) using: `psql -h localhost -p 5432 -U <superuser>`
    b. Create the database using `CREATE DATABASE transactions;`
    c. Create database user using `CREATE USER postgres WITH PASSWORD 'password';`
    d. Give user access to the created database `GRANT ALL PRIVILEGES ON DATABASE transactions TO postgres;`
2. Run the Redis server  
    a. Download redis zip file from this link https://github.com/microsoftarchive/redis/releases/download/win-3.2.100/Redis-x64-3.2.100.zip  
    b. Extract the zip file  
    c. In the extracted folder you will find `redis-server.exe`, run this executable   
    d. Go to file `./src/main/resources/application.propertires` and edit the redis configuration values to match the running redis server  
3. In the application root directory, run `./build_and_run.bat` script to run the application connecting to both postgres database and redis cache servers  
4. Go to `http://localhost:8091/swagger-ui/index.html` to use the app swagger   