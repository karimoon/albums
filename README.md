# Albums App

the application works in two modes:

- connected mode: retrieve the list of photos from the webservice.

- offline mode: the application allows you to display in this case the last list retrieved in online mode.

I've implemented in this project the clean architecture with MVVM pattern.

In terms of tools and libraries, I've used : 

  * Android architecture components (Room, ViewModel, LiveData, Lifecycles)
  * Koin for dependency injection
  * Retrofit
  * Rxjava, RxAndroid
  * RecyclerView
  * Junit 4 and Mockito 
  * Leak canary
