# Banking System

## Description of the project

In this banking system, `Admin` users can create `Account Holder` and `Third Party` users, and account holders' accounts. 

There are four  types of accounts: `Student Checking`, `Checking`, `Savings`, and `Credit Card`. Appart from their attributes (seen in the class diagram), these accounts have some specifications:
1. **Checking Accounts** 
* Are created when selected, and when the *primary owner* of that account is over 24 years. If not a **Student Checking Account** is created.
* Their minimum balance is 250.
* Their monthly maintnance fee is 12.

2. **Savings Accounts**
* Their minimum balance must be between 100 and 1000, if not defined the default is 1000.
* Their interest rate, which is added yearly, must be between 0 and 0.5, if not defined the default is  0.0025.

3. **Credit Card Accounts**
* Their credit limit must be between 100 and 100,000, if not defined the default is 100.
* Their interest rate, which is added monthly, must be between 0.1 and 0.2, if not defined the default is  0.2.

In order to send or recieve money, the system uses `Transactions` which, apart from applying said transaction to the accounts, carries out different things:

1. **Penalty fees** are applied for account types which have a minimum balance. Whenever an account holder tries to transfer funds, this method applies the penalty fee of 40 if the account is below its minimum balance. 

2. **Validations** this method validates different aspects of the transaction:
* It validates the logged account holder is a primary or secondary owner of the *payer account*, so it can transfer funds.
* It validates the *payer account* has sufficient funds to make the transaction.
* It validates both the *payer account* and the *target account* aren't frozen.
* It validates the *target account* recipient's name.
* It validates, in the case of *third party* transactions, the provided *secret key* belongs to the *target account*.
* It validates, for *third party* transactions , that the *hashed key* present in the *HTTP request header* belongs to the third party.

3. **Fraud Detection** uses two validations to make sure there isn't fraudulent activity:
* It validates the time passed since the *payer account's* last transaction is over a second ago, if not it freezes the account.
* It validates that, in the last 24 hours, the *payer account's* total transactions aren't over 150% of the highest daily total amount of transactions for that account (but has a minimum of 500, in case the *payer account* hasn't been very active). If not the system freezes the account. 


Other than transactions, account holders can acces their accounts by logging in with their credentials. When users access their saving or credit card accounts, **interest rates** are applied to their accounts, verifying the time elapsed since the last interest rate was applied and adding that sum to the account's balance. 

## Set Up

1. Clone or Download the project from the repository.

2. Open the directory as a project on a IDE as IntelliJ.

3. Run the *MidtermBankingSystemApplication.java * file on the path:

```
./src/main/java/com/ironhack/midtermbankingsystem/MidtermBankingSystemApplication.java
```

### Technologies Used

<img src="https://cdn.jsdelivr.net/npm/programming-languages-logos/src/java/java_256x256.png" width=50>Java  <img src="https://raw.githubusercontent.com/docker-library/docs/c408469abbac35ad1e4a50a6618836420eb9502e/mysql/logo.png" width=60>MySQL <img src="https://user-images.githubusercontent.com/33158051/103466606-760a4000-4d14-11eb-9941-2f3d00371471.png" width=60>SpringBoot 

## Class Diagram
<img src="https://github.com/Openbank-Java-Bootcamp/Lisa-banking-system/blob/main/class-diagram-bs.drawio.png" />


## Routes

| Route  | Method | Access |
| ------------- | ------------- | ------------- |
| /api/accounts/admin/status/{id}  | PATCH  | Admin  |
| /api/accounts/admin/balance/{id}  | PATCH  | Admin  |
| /api/accounts/admin/delete/{id}  | DELETE  | Admin  |
| /api/accounts/balance/{id}  | GET  | Admin & Account Holder  |
| /api/account-holders/admin/new  | POST  | Admin  |
| /api/account-holders/admin/delete/{id}  | DELETE  | Admin  |
| /api/admins/admin/new  | POST  | Admin  |
| /api/checking-accounts/admin/new  | POST  | Admin  |
| /api/credit-card-accounts/admin/new  | POST  | Admin  |
| /api/credit-card-accounts/{id}  | GET  | Admin & Account Holder  |
| /api/savings-accounts/admin/new  | POST  | Admin  |
| /api/savings-accounts/{id}  | GET  | Admin & Account Holder  |
| /api/student-checking-accounts/admin/new  | POST  | Admin  |
| /api/third-parties/admin/new  | POST  | Admin  |
| /api/transactions/account-holder/new  | POST  | Admin & Account Holder  |
| /api/transactions/third-party/new  | POST  | third party (key header HTTP request)  |

## Resources

Some things that have helped me solve errors or widen my knowledge:

https://stackoverflow.com/questions/15203485/spring-test-security-how-to-mock-authentication

https://fasterxml.github.io/jackson-modules-java8/javadoc/datetime/2.9/com/fasterxml/jackson/datatype/jsr310/JavaTimeModule.html

https://www.netsurfingzone.com/hibernate/failed-to-lazily-initialize-a-collection-of-role-could-not-initialize-proxy-no-session/

https://stackoverflow.com/questions/23645091/spring-data-jpa-and-hibernate-detached-entity-passed-to-persist-on-manytomany-re

