### Tasks I have completed

- Add a login screen to authenticate a user with the application
  - Needed Input fields: Username & Password and a Button
- The logged-in session needs to be stored
  - The form of the Session will be a JWT-Token
- Add Logout capabilities
- Show list of active (where the status is `PUBLISHED`) job ads (with either pagination or lazy loading) (refer to `/design/JobList.png`)
- Add an indicator to the list screen whether the currently authenticated user already applied to a specific job

### Tasks not done

- Create a `My Profile` Tab, via which candidates can adjust profile attributes (for example name)
  - Also requires Backend Work
- Add Password Change Capabilities
  - Also requires Backend Work
- When clicking on an available job, show a new screen with more details and the option to apply
- Add search capabilities
  - Also requires Backend Work
- Tab for `Show My Applications`, which should show all the current authenticated users applications (need to create a new backend endpoint as well)
  - Also requires Backend Work

### Stack that was used

- Jetpack Compose without using Compose navigation
- Hilt for dependency Injection
- MVVM Patterns with a Uni-Directional Flow based on MVI pattern, something I call MVVM-VS (MVVM- ViewState)
- Api
  - Retrofit
  - Graphql

### Things I would have done with more time

- Introduce Integration testing
- Modify the backend error response to have a uniform error handling mechanism
-
