# Banking System

## Description of the project

In this banking system, `Admin` users can create `Account Holder` and `Third Party` users, and account holders' accounts. 

There are four  types of accounts: `Student Checking`, `Checking`, `Savings`, and `Credit Card`. Appart from their attributes (seen in the class diagram), these accounts have some specifications:
1. **Checking Accounts** 
* Are created when selected, and when the *primary owner* of that account is over 24 years. If not a **Student Checking Account** is created.
* Their minimum balance is 250.
* Their monthly maintnance fee is 12.

2.

In order to send or recieve money, the system uses `Transactions` which, apart from applying said transaction to the accounts, carries out different things:

1. **Penalty fees** are applied for account types which have a minimum balance. Whenever an account holder tries to transfer funds, this method applies the convenient penalty fee if the account is below its minimum balance. 

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
