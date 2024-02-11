# Breeds
Test Challenge using Dogs API

# Remarks from Zsolt Bertalan

## Tech

* I used commonly used libraries Retrofit 2, Coroutines, Dagger 2, AndroidX, Coil and Compose.
* I also used less commonly used libraries, like MVIKotlin, Decompose and Essenty. See details in 
the structure section.
* I used my base library. This contains code that I could not find in other third party libraries, and what I use in 
  different projects regularly:
  * https://bitbucket.org/babestudios/babestudiosbase

## Structure

* I use a monorepo for such a tiny project, however I used a few techniques to show how I can build an app that 
  scales, even if they are an overkill as they are now.
* The three main sections (module groups in a larger project) are **data**, **domain**, and **ui**.
* **Domain** does not depend on anything and contains the api interfaces, and the model classes.
* **Data** implements the domain interfaces (repos) through the network and db packages or modules, and does not 
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
* **KotlinResult** is a replacement for the Kotlin Result monad, with a lot of useful extensions.
  * https://github.com/michaelbull/kotlin-result

## Repository and Error Handling

The app uses **network first** strategy. When the app starts, always tries to load the breeds from the network. If 
that fails, it loads data from the database. If there is no data in the database, the app displays an error screen. 
The user can retry to get data from the network by pressing the 'Retry' button. When the network call is a success, 
the data is upserted in the database, meaning it will insert new entries and update existing ones, determined by the 
primary key, which is the breed name in this case.

## Other features

* Expandable list to display the sub breeds. Starts as expanded. 
* Handles Dark Mode.
* To facilitate some tests, I added a sort button on the main screen.

## Tests

* I write many type of tests, here I only wrote two extremities: unit tests and end to end tests.
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
* So to reiterate, no integration tests and pure UI tests in this project.

## Room for improvement

* The expandable list could show the expand button only if there is a sub breed.
* The state of the expanded list items could be stored in the breeds screen state.
* Adding local storage. Given the static nature of the API I would use a cache-first strategy with it.
* The composable views could be made generic.
