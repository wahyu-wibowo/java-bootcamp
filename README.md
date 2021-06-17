## **Prerequisite:**
1. Postgresql is installed and running
2. Make sure its accessible in `localhost:5432/postgres`
3. Make sure postgresql password is correctly configured in `resources/application.properties` file
4. Sample of csv file for account upload is on `account.csv`

## **How to try application:**
1. Run `JavaBootcamp.java` class using your IDE **OR** double-click `run.bat` file
2. Open http://localhost:8080 in browser
3. You can login using dummy account `112233` and pin `012108` 


## **Known Issue:**
1. Account is sent in url param, not body (makes the app bypassable)
2. PIN unmasked, unencrypted
3. Data might be not ACID (untested)
4. Need better error handling and thrown message
5. Some dependency stated in POM maybe not needed