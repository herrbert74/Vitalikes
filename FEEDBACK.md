### Positive

* Nice architecture approach with clear responsibilities and boundaries
* Very clear communication of ideas and reasoning behind decisions taken
* Proper formatting of balances
* Balance fetching & error handling
* Good theming
* Consistent & readable code style
* Up to date with the Android ecosystem and the latest trends

### Negative - Solution issues

* Search input issues - no debouncing, not cancelling on-going queries when query changes, not trimming the input, 
  all of them resulting in hammering down the BE after each character
  * Fixed. Cancelling WAS done, debouncing and trimming was indeed missing
* Missed tokens - if the balance wasn't fetch, the user doesn't see the token
  * This is only an issue if there is no data in the database, and there is no network connection. Fixing this would 
    need a complete rewrite of the getTokens function. This was not feasible the way it was written.
* Missing requirement for green / red balances
  * Fixed
* A lot of UI blinking when typing (e.g. wet -> weth)
  * That's just a duplication of the Search and Compose issues above and below (?)
* Compose issues - many redundant recompositions - not using stable types, no ImmutableList usage, using hashcode of 
  token as a `key` - so if balance changes the same view won't be reused
  * Fixed. Useful feedback, my Compose knowledge is (was? :) ) limited due to only have used it in hobby projects.
* Coroutine issue #1 - broken main-safety rule - (all method should be safe to be called on main-thread and dispatcher 
  changes should be done in places that know they need to offload some work (e.g. `TokenDao#getTokens`)
  * That's something I should consider in the future, but it's not a coroutine problem, but an architecture 
    recommendation
* Coroutine issue #2 - many unnecessary updates due to not using `distinctUntilChanged`, creating empty coroutine 
  scopes that are never cancelled
  * The 'distinctUntilChanged' function is not needed, because state.subscribeAsState() already handles that. Not sure 
    where the empty coroutine scopes are, probably MVIKotlin scope was mistaken for it. That's not empty, and it's 
    lifecycle-aware.
* Incorrect domain modeling - `Token` including `balance`, Balance represent as a string in a domain model, 
  hardcoded label text exposed from domain layer
  * This has some merit, however... The project is already more complex than it needs to be, but I never have had 
    separate domain and presentation classes before, and I would consider this premature optimization for this project.
* Storage - storage is basically useless, as it's not used in offline mode, errors are still shown on UI even if we 
  have the data, it's just treated as a source of truth - could have as well been just a cache in
  * I did that for the top tokens, but I ran out of time to do that for the balances, which failed the whole caching.
* Other design issues - duplicated logic that can lead to bugs (filtering of tokens), same logic in UI and 
  presentation layer (checking for min of 2 characters)
  * This logic is not duplicated, only the same check was used for different purposes. The check in the ui might be 
    offloaded to the Executor or Reducer?!
