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
* Missed tokens - if the balance wasn't fetch, the user doesn't see the token
* Missing requirement for green / red balances
* A lot of UI blinking when typing (e.g. wet -> weth)
* Compose issues - many redundant recompositions - not using stable types, no ImmutableList usage, using hashcode of token as a `key` - so if balance changes the same view won't be reused
* Coroutine issues - broken main-safety rule - (all method should be safe to be called on main-thread and dispatcher changes should be done in places that know they need to offload some work (e.g. `TokenDao#getTokens`), many unncessary updated due to not using `distinctUntilChanged`, creating empty coroutine scopes that are never cancelled
* Incorrect domain modeling - `Token` including `balance`, Balance represent as a string in a domain model, hardcoded label text exposed from domain layer
* Storage - storage is basically useless, as it's not used in offline mode, errors are still shown on UI even if we have the data, it's just treated as a source of truth - could have as well been just a cache in
* Other design issues - duplicated logic that can lead to bugs (filtering of tokens), same logic in UI and presentation layer (checking for min of 2 characters)
