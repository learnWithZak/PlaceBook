# PlaceBook

## Introduction
This Android application shows a Google map to the user, let him make some notes about the places he has visited.
For example, imagine that he visited a local restaurant, and wants to keep reminders about the menu items that he likes, this app will help him do keep those notes.

## Used technology
This application uses:
1. *Google Maps API.*
2. *Google Places API.*
3. *Google’s autocomplete API.*
4. *Room Persistence library.*
5. *Data binding.*
6. *ViewModel, LiveData, Repository (MVVM).*
7. *Coroutines.*
8. *Implicit intent* to share data with other apps.


## How to use
At startup, the Google map is automatically centered on the user’s location.
The user can tap on the map to create a bookmark at a specific place (with latitude and longitude), choose a specific category, add some additional notes (the best menu items for example!), edit his notes, take a photo or get it from the gallery, and share those reminders with his friends ;)
The user can also search for a place using Google’s autocomplete API.

## Bookmark types
As said earlier, the user can change the category assigned to a place. When he adds bookmarks for a variety of place types, we added different icons that are displayed on the map :
1. Gas category icons.
2. Restaurant category icons.
3. Shopping category icons.
4. Lodging category icons.
5. Other for other category icons.

### Google Autocomplete
As the user types in a place name or address, the search widget displays a dynamic list of choices.

https://user-images.githubusercontent.com/111642558/197823495-ffbe1697-c731-49d8-a17e-cda6bdabda1e.mp4


### Untitled bookmarks
When the user long-tap anywhere on the map and the bookmark pops up with a new untitled bookmark using a default photo.

https://user-images.githubusercontent.com/111642558/197928582-7f6257c7-0e71-47c0-bff2-32c479ddf1f2.mp4


### Share your notes with others
As you can see in this video demo, you can share your notes with your friends.
Also, you can choose/change the category of the bookmark.

https://user-images.githubusercontent.com/111642558/197823571-56c26091-1e45-40c6-ac9c-8a0d4d6a3258.mp4

### Image processing
Some additional operations were made to the captured image to avoid memory/performance issues.
The images captured from the camera can be much larger than what’s needed to display in the app.
As part of the processing of the newly captured photo, the app will downsample the photo to match a default photo size.