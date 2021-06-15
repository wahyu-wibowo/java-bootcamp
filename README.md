## **Prerequisite:**
1. Postgresql is installed and running
2. Make sure its accessible in `localhost:5432/postgres`
3. Make sure its password is correctly configured in `resources/application.properties` file
4. Sample of csv file for account upload is on `account.csv`

## **How to try application:**
1. Run `JavaBootcamp.java` class
2. Open http://localhost:8080 in browser
3. You can login using dummy account `123` and pin `321`


## **Known Issue:**
1. Account is sent in url param, not body (makes the app bypassable)
2. PIN unmasked, unencrypted
3. Data might be not ACID
4. Need better error handling and thrown message