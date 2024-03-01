The assessment aims to allow you to demonstrate your expertise in Kotlin and Android
development and test your experience with or ability to learn about the Ethereum
blockchain.
We appreciate that you’ll likely only have evenings or a weekend to complete this, so please
don’t spend too much time on it. To simplify this process we have prepared a simple
project setup for you - you don’t have to use it if you don’t want to.

### The assessment

We ask you to build an app to check your crypto balances. The intro screen will show your
wallet address. The second screen will show your ERC-20 tokens balance.
Please also complete the questions at the end of this document.

### Intro Screen:

* Just display the given Ethereum wallet address, no need for any calculations
here
* Show a button to navigate to the 2nd screen
Note that the Ethereum address will be hardcoded in the app; The user won’t
be able to change it.

### ERC-20 screen:

* Show a search box where user can look for ERC-20 tokens
* As the user types in the search box, show the balances of all the tokens
matching the query text
* The balance should be a properly formatted token amount belonging to the
user - no need to convert them to dollar value e.g. 0.0123 DAI, 12.456 USDC
* If the balance is positive, display it in green text; if it is zero, display it in red
text

### What we expect from you:

* Use Kotlin best practices
* Show how you can use Coroutines (preferable) / RxJava to handle concurrency
* Put extra focus on fetching token balances. We would like to see how you’ll design
that and what will be your strategy to handle bad networking, caching, and
refreshing
Note: fetch balances only for tokens the user searches for - not for all known tokens.
* Show us an app architecture that facilitates future changes in the app while being
easy to maintain and understand by all team members
* While the UI can be simple and look like the wireframes, small additions that
could improve the user experience are welcome.

### Tech Details:

* Hardcoded Ethereum account address:
0xd8dA6BF26964aF9D7eEd9e03E53415D37aA96045
* For ERC-20: https://etherscan.io/apis.
You can use this API key for convenience
E5QFXD7ZYRH7THQM5PIXB8JD4GY38SEJZ4
This API has a rate-limiting of 5 calls per second, you will have to handle that
limitation
* ERC-20 tokens the user can search for are found here:
https://api.ethplorer.io/getTopTokens?limit=100&apiKey=freekey
* For this exercise do NOT use any other endpoint from ethplorer

### Questions:

Please submit the answers to these questions along with the code:
* How long did you spend on the exercise?
* What would you improve if you had more time?
* What would you like to highlight in the code?
* If you had to store the private key associated with an Ethereum account on an
Android device, how would you make that storage secure?