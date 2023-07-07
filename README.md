# WeatherApp (Java)

Design UI by using Responsive Layout.

Using Restful API (Weather api) to get the latest 5 days' weather of a particular city.

Reverse weather data to local database, in case no internet.

## APK of weatherApp:

https://github.com/yluoc/WeatherApp-APK.git

## Main function for weatherApp:

- Get the internet data from the weather API we use, using java to processing those data
  into the specific format that people can easily understand
  
- Display the chosen city's weather condition, temperature, wind director,
  and other different kinds of index, and the weather for following 3 days.

- Saving the specific city's weather data into local database, and people can check the weather data
  if not internet.

- Searching, deleting, modifing, and checking the weatherdata we saved in the local database.

- Besides the above operations, people can also Modify the background, check the current version, clear all the 
  saved data, and share the app to others.

## Container class layout
- RelativeLayout
- LinearLayout
## Basic controls
- TextView
- Button
- ImageView
- EditText
## Complex controls
- ListView
   - BaseAdapter
- GridView
- ViewPager
  - FragmentBaseAdapter
- CardView
## Activity
- Creating and binding layouts
- Life Cycle
- Jumping and passing values
## Fragment
- Loading into the ViewPager
- Fragment passes value to activity
## Database saving - operations of database
- SQLiteOpenHelper's creating and using.
- Calling SQLitDatabase function
## Use of third-party frameworks
- Picasso： Framework for loading web images
- xutils： Package complete multifunctional framework, mainly for network data acquisition functions
- Gson： Framework for parsing json data
