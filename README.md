# YoutubeSearchApp
An android app that can find YouTube videos you want to watch through search and actually play videos 🙌👀


### Open API
* [Youtube Data API](https://developers.google.com/youtube/v3)
  - Note: The quota for using the API per day is fixed, and it is initialized at midnight PT every day.

### Library
* Jetpack Navigation
   - Reason for use: To simplify the movement between fragments. Also, the entire flow is great at a glance.
 
 * Koin
   - Reason for use: For dependency injection. Dependency injection is used for the following reasons.
     1) Easy to test and refactor
     2) Enhances code readability
     3) Separation of object creation and use
   
* Retrofit
   - Reason for use: For speed, readability and convenience in network communication.
   
* Glide
   - Reason for use: Because it loads url images stably and handles image-related processing such as loading failure processing / out of memory / caching.
