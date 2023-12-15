# Proiect GlobalWaves  - Etapa 2

used the skeleton provided by the OOP team.

## Functionalities

## 1. User

### 1.1 User Inheritance
Artist and Host classes as User subclasses.
* `Artist` - Has a list of albums, events and merch.
* `Host` - Has a list of podcasts and a list of announcements.

### 1.2 Builder Design Pattern
Builder Design Pattern for the `User` class and its subclasses.

The User/Artist/Host classes have a static inner class called User/Artist/HostBuilder which has the following attributes:
* the `User.Builder` class allows to construct a User object with various optional parameters
  (e.g.: playlists, likedSongs, followedPlaylists, etc.), whikle still ensuring that mandatory parameters
  (e.g.:username, type, age, city) are provided.
* the builder pattern simplifies the instantiation of `User` objects and 
enhances the readability of the code when creating instances with different configurations.

### 1.3 Features
User class methods:
* `switchConnectionStatus` - switches the connection status of the user.
* `printCurrentPage` - prints the current page of the user.
* for Artist: 
  * `addAlbum`, `showAlbums`
  * `addEvent`, `addMerch`
* for Host: 
  * `addPodcast`, `showPodcasts`
  * `addAnnouncement`

## 2. Admin

### 2.1 Features
New methods for the Admin class:
* `getOnlineUsers`, `getUsers`
* `addUser` - adds a user to the list of users, regardless of its type.
* `deleteUser` - deletes a user from the list of users; the user can't be deleted if:
    * it is an artist and a user is listening to one of its songs.
    * it is a host and a user is listening to one of its podcasts.
    * a user is currently on its page.
    * a user is listening to one of its playlists.
* `getTop5Albums`, `getTop5Artists` based on the number of likes on their songs.
* `removeAlbum`, `removePodcast` - removes an album/podcast from the list of albums/podcasts., the object can't be deleted if: 
    * a user is listening to one of the album's songs, or it is located in one of the user's playlists.
    * a user is listening to one of the podcast's episodes.


## 3. SearchBar

### 3.1 Features
New cases for the Search Bar:
* album
  * by name
* host
  * by name
* album
  * by name
  * by artist
  * by description

## Implementation
* `User` class and its subclasses have been implemented using the Builder Design Pattern.
* `Admin` tracks all the object instances of the application.

Every command gets through the `CommandRunner` class, which is responsible for parsing the command and calling the appropriate method, 
which afterward is done through the `Admin` class.

## Conclusion 
The project was a good opportunity to learn about building and upgrading a real world application, 
with different relations between classes and different functionalities.

