I have created a MVVM Pattern and the following descriptions will explain what each part does in the code. The MVVM pattern gave me a very decoupled, organized, and extendable code base to work with. If I ever need to create another view to display repo information ALL on one page (Rather than two sections like it is right now) all I would need to do is subscribe to the listViewModel and the data would be retrieved automatically. After I would just display it on the screen without any additional work.
Models:

•repoModel - Represents the repo, contains all the info that the view would require for displaying the repo
•userModel - Represents the user, contains all the info that the view would require for displaying the user

Services: I had this section to further organize my code. This package would include the services required for getting the user and repos
	•	githubImplementation - a class that stores the services needed to retrieve data for the app, including user and the repos that the user has. Is considered a singleton since only a single service instance would be required. Uses the gitHubService to conduct calls
	•	gitHubService - A file containing all the retrofit get calls. Is used by the githubImplementation to do the get calls.

utils: Classes and functions to assist me in idling and animations

viewModels:
	•	listViewModel: This is the view model that uses the repo model combined with the getRepoList service to retrieve the data and add it to a Observable to be used on the view
	•	userViewModel: This is the view model that uses the user model combined with the getUser service to retrieve the data and add it to a Observable to be used on the view

Views:
	•	repoAdapter: This class is responsible to serve as the adapter for the recycler view and controls which list is displayed inside the recycler view.
	•	repoClickCallback: Basic interface that serves as nothing more than a reference to a click function that is implemented inside the RepoListFragment.
	•	MainActivtiy: The Heart of the app, where everything starts. This view is responsible for starting the repoListFragment and managing the subscriptions for the userViewModel
	•	RepoListFragment: The view responsible for displaying the repo list and managing the click callback so that it can display the appropriate information in relation to the clicked recycler view portion that was clicked. It uses it’s subscriptions to listViewModel to display the list.

Looking back over my code that I created, I feel that there would be somethings that I would fix/add. Notably I would add more documentation to paint a more clearer picture of what my code does to a outsider. I would also use more libraries such as RxJava to create even more readable code. I would also combine both the repo and user retrieval into a single functionality. Getting them separately is redundant since both have the same requirements (The username) to get the data. Finally I would also consider testing on devices with a much higher or lower pixel density and/or resolution.

While doing this take home assignment there was a single thing that I had trouble with and could not be fixed: * When you click on a recycler view item and a overlay with a card pops up the background elements become invisible. I have a theory is that it has something to do with how I either layer all the layouts or the colour of the background doesn’t have transparency. I attempted several things in order for me to fix this, including moving around the layering of everything and working with the Alpha and reworking some parts of the animation helper.

My testing approach for this project was to try to replicate the process that the video displayed. I also implemented a idling resource so that while the recycler view and the avatar/username loads espresso waits until it’s done.

The testing functions and their descriptions are as follows:
	•	searchElementsPresent: Checks if the initial screen and both elements (The edit text AND the button) are present
	•	searchFunctional: Checks if the search works and all views that should be displayed (Avatar, username, recycler view, and both elements of the card view) are displayed.
	•	repoListClickFunctional: checks if the click for the recycler view is registered and the appropriate card view pops up with the correct data in it. Right after it makes sure that the overlay’s OnClick works as well. Then for the final step it checks if the information has changed once it clicks on another view.

Looking back at it, what I would’ve changed about my testing approach is to introduce a new activityTestRule that would not automatically destroy the activity and instead would continue running the other tests, removing the code that types in Octocat into the edit text for repoListClickFunctional. Saving on both time and making things more efficient.
