# Vitalikes

Test Challenge using Ethplorer and Etherscan apis. 

[Challenge description](CHALLENGE.md)

[Challenge feedback](FEEDBACK.md)

# Remarks from Zsolt Bertalan

## Tech

* I used commonly used libraries Coroutines, Dagger 2, AndroidX, and Compose.
* I also used less commonly used libraries, like MVIKotlin, Decompose and Essenty. See details in the structure section.
* I used my base library. This contains code that I could not find in other third party libraries, and what I use in 
  different projects regularly:
  * https://bitbucket.org/babestudios/babestudiosbase

## Structure

* I use a monorepo for such a tiny project, however I used a few techniques to show how I can build an app that 
  scales, even if they are an overkill as they are now.
* The three main sections (module groups in a larger project) are **data**, **domain**, and **ui**.
* **Domain** does not depend on anything and contains the api interfaces, and the model classes.
* **Data** implements the domain interfaces (repos) through the network, local and db packages or modules, and does not 
  depend on anything else, apart from platform and third party libraries.
* **Ui** uses the data implementations through dependency injection and the domain entities. **Root** package 
  provides the root implementations for Decompose Business Logic Components (BLoCs) and navigation.
* **Di** Dependency Injection through Dagger and Hilt

## Libraries used

* **MVIKotlin**, **Decompose** and **Essenty** are libraries from the same developer, who I know personally. Links to 
  the libraries:
    * https://github.com/arkivanov/MVIKotlin
      * An MVI library used on the screen or component level.
    * https://github.com/arkivanov/Decompose
      * A component based library built for Compose with Kotlin Multiplatform in mind, and provides the glue, what 
        normally the ViewModel and Navigation library does, but better. 
    * https://github.com/arkivanov/Essenty
      * Has some lifecycle and ViewModel wrappers and replacements.

## Justification for above libraries

* I've used MVIKotlin professionally for years.
* I adopted MVIKotlin, then Decompose for private projects too, so I'm most proficient with these. I could have used 
  the standard MVVM and Jetpack Navigation, but it would take more time for me at this point.
* These libraries address issues, that the Google libraries failed to address.
* Risks commonly associated with above libraries
  * Abandoned by author: No real risk, as I can copy to my project, others can pick up, or I can switch to similar.
  * Onboarding: For most people it will be new. That's a real problem, but the library is picking up.
  * More boilerplate initially: they need some initial setup, but they scale well for more screens.

## Tests

* I write many type of tests, here I only wrote unit tests and end to end tests.
* Unit tests use no dependency injection, they instead rely on mocked interfaces. I'm looking into 
doing that without mocking in the future.
* End to end tests use the real application dependencies, so they can be brittle.
* I can also write two kind of integration tests: for integration tests that are technically unit 
tests, I use Robolectric. These could test a class that is closely related to some Android classes, 
like LinkMovementMethod, or they test integration with something complex, like a database for 
example. In this case I use in-memory databases. The second type is an integration UI test, which 
similarly use in-memory database.
* Finally, I can write pure/functional UI tests, where the test target is the UI, so the database and the 
network is fully mocked.
* So to reiterate, no integration tests, and pure UI tests in this project.

## Problems dealt with

* Network error and empty database: showing error full screen.
* Rate limit on Ethplorer: use the database first, if any record exist.
* Rate limit on Etherscan: filter out invalid data, which is a result of failed calls, then retry (solution is 
  network only due to time limits, no db recovery, as it should be).
* Load the balances asynchronously and parallel.
* Search only when at least 3 characters are added.

## Room for improvement

* Instead of filtering rate limited data, it could be loaded from database, showing with a warning.
* Reduce the overall complexity and find better solutions to some hacky fixes in search.
* Use proper Exception classes in search.
* Dto, Dbo and domain classes have complicated relationships, maybe there is a way to simplify.
* Animated transitions between screens.
* More Unit tests. Only had time for very basic ones.

## Time spent

* Figuring out the api calls in Postman: 30 minutes
* Setting up the project and project files: 1 hour (should be much more, but I have a detailed template)
* Adjusting API calls, database, model and DTO classes: 1.5 hours
* Adjusting UI classes, Decompose classes for navigation and object creation: 1.5 hours
* Making the basic app working: 0.5 hour
* Adding search, adjusting list items, token conversion happy path: 2 hours (very complex, but I did this before)
* Search error handling, empty state: 1 hour
* Adding tests (I spent a lot of time with a particular flow testing problem unfortunately): 3 hours
* Bug fixing: 2 hours
* Writing readme: 1 hour
* Altogether: circa 14 hours

## Where would I store a private key

Private keys in Android should be stored in with the Android Keystore provider, which is available since API level 
18, so can be used on all devices nowadays. 

For additional security on API level 28 and newer one can use the hardware security module to import secure keys, 
which makes sure that the sensitive information will never be loaded into the device memory.
