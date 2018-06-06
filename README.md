# Design pattern used
This project contains the MVVM Pattern. The MVVM pattern makes code decoupled, organized, and extendable. If I ever need to create another view to display repo information (Rather than two sections like it is right now) all I would need to do is subscribe to the listViewModel after I would just display the data on the screen without any additional work.

# Models
repoModel - Model of the repo, the listViewModel would use this model.

userModel - Model of the user, the userViewModel would use this model.

# Services
I had this section to further organize my code. This package would include the services required for getting the user and repos

githubImplementation - a class that stores the services needed to conduct user and repo retrieval calls for the app. This class is a singleton since only a single service instance would be required. Uses the gitHubService to know which calls to make.

gitHubService - A file containing all the retrofit get calls. Is used by the githubImplementation.

# utils
Classes and functions to assist in idling and animations

# viewModel
listViewModel: View model that calls the getRepoList service to get a list of repos assoicated with a ID. Uses the repoModel as the data mold.

userViewModel: View model that calls the getUser service to get a user assoicated with a ID. Uses the userModel as the data mold.

# Views
repoAdapter: Responsible for the recycler view and controls which list is displayed.

repoClickCallback: Basic interface with a callback function that is implemented inside the RepoListFragment to manage recycler view item clicks.

MainActivtiy: The Heart of the app, where everything starts. This view is responsible for starting the repoListFragment and managing the subscriptions for the userViewModel

RepoListFragment: Responsible for displaying the repo list and managing the click callback on the recycler view so that it can display the appropriate information. It uses it’s subscriptions to listViewModel to display the list.

# Development Retrospective
Looking back over my code that I created there are several things I would have fixed/added. Firstly I would add more documentation to paint a more clearer picture of what my code does to another party. Secondly I would also use more libraries such as RxJava to create more readable code. Thirdly I would combine the repo and user retrieval into a single call. Getting them separately is redundant since both have the same requirements (The username) for getting the data. Finally I would also consider testing on devices with a much higher/lower pixel density and/or resolution.

# Issues
While doing this take home assignment there was a single thing that I had trouble with and I attempted to fix:

When you click on a recycler view item and a overlay with a card pops up the background elements become invisible. I have a theory that it has something to do with how I created the hierarchy or the colour of the overlay doesn’t have proper transparency. I tried moving around the hierarchy of layers and working with the Alpha but none resulted in a success. 

# Testing functions

My testing approach for this project was to try to replicate the process that the video displayed. I also implemented a idling resource so that espresso has to wait until the avatar, username, and repo list are fully loaded.

searchElementsPresent: Checks if the initial screen and both elements (The edit text AND the button) are present

searchFunctional: Checks if the search works and all views that should be displayed (Avatar, username, recycler view, and both elements of the card view) are displayed.

repoListClickFunctional: checks if the click for the recycler view is registered and the appropriate card view pops up with the correct data in it. Right after it makes sure that the overlay’s OnClick works as well. Then for the final step it checks if the information has changed once it clicks on another view.

# Testing Strategy Retrospective
What I would’ve changed about my testing approach is to introduce a new activityTestRule that would not destroy the activity and instead allow the running of the other tests, removing the code that inputs Octocat into the edit text for repoListClickFunctional. Saving on both time and making things more efficient.
