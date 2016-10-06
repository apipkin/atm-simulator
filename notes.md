# ATM Simulator

## Classes

### Interface

### Account
Can be instantiated with just a name or a name with a starting balance. 

#### Methods
`withdraw(amount)` > `double`
Removes the amount from the current balance. If more than 4 ammounts have been removed from the Account class, a service charge is also withdrawn.
Any amount or amount plus service charge that exceeds the current balance will throw an InsufficientFunds error.
Returns the new balance.

`deposit(amount)` > `double`
Adds the amount to the account balance.
Returns the new balance.

`transferTo(account, amount)` > `double`
Transfers the amount from the current account to the new account. In doing so, the withdrawal method is called and it can possibly throw the InsufficientFunds error.
Returns the new balance.

`getBalance()` > `double`
Returns the balance as a double to be used with further calculations.

`getBalanceCurrency()` > `String`
Returns the balance in the current locale (en-US) format

`toString()` > `String`
Returns a string in the format: [name]: $[balance]

### InsufficientFunds
`@extends` Exception

Can be instantiated with a custom message or the default method. 

## Looking Forward
I would create a Ledger class and record transaction types, amounts, and dates.

I would also create two specific classes for each acount type to handle things like interest,
withdrawal limits, and fees separately just as with real accounts.

The ability to name an account aside from the type would be beneficial if you had more than
one account of any type.

 Do currently rounding and flooring to get around the IEEE 754 floating point math errors 
 such as 0.1 + 0.2 != 0.3. When dealing with currency this can lead to large mistakes.