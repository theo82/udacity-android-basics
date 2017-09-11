**News APP**

This app is using, the guardian news api(http://open-platform.theguardian.com/documentation/). The network operation is done in a background thread and uses Loaders. 

No matter how many times you request data from a loader, by default the Loader will only fetch data once. Second when an activity shuts down, 
it will automatically tell all of its Loaders to quit what they are doing, since the data won't be used. Finally, Loaders persist across activity configuration changes like rotations. So if we kick off a Loader to go fetch data and the phone is rotated, the loader will automatically return its data to the new activity when it’s done.

**Starting a Loader**

Within your activity you can start a Loader with the help of the LoaderManager. Every intro activity comes equipped with a LoaderManager which takes care of creating, reusing, destroying loaders at the appropriate time. Once we get a reference to the LoaderManager, we can now call the initLoader method. 

Loader<D> initLoader(int id, Bundle args, LoaderCallbacks<D> callback) 

And can be called as 

getLoaderManager().initLoader(0 ,null, this);

initLoader() ensures that loader is both initialised and active. It has two possible outcomes:

If the loader with the specified ID already exists, the last created loader is used. 
Now if the Loader with the ID doesn’t exist, then the LoaderCallback, onCreateLoader() method will be triggered, which should return a new Loader. So when we rotate the device over and over again, the same Loader will be used, instead of creating a new one on each orientation change. 

**Creating a Loader**

So how does the Loader get created? By implementing the LoaderCallbacks interface. Our activity needs to override the following three callback methods in order to interact with the LoaderManager. 

*onCreateLoader()*: When this method is called, we need to create and return a new Loader object which will fetch data from the server. 

*onLoadFinished()*: This method is called when the Loader is finished loading data on the background thread. This is a good place to update the UI with the list of data. 

*onLoaderReset()*: This method gets called when the previous created loader is being reset. 

LoaderManager interacts with an activity to manage one or more Loader instances. In order for our Activity to be a client that interacts with the LoaderManager, we need MainActivity to implement the LoaderManager.LoaderCallbacks interface. Then the activity must override the: 

onCreateLoader() method to create and return a new Loader instance.
onLoadFinished() method to receive the data once the Loader has finished.
onLoaderReset() method to handle when the previously created Loader is no longer needed, and the existing data can be discarded. 

To start a loader when the app is launched in the MainActivity onCreate() method, we should call getLoaderManager().initLoader(0, null, this).

![screenshot_1505107488](https://user-images.githubusercontent.com/7755518/30259568-c176ce40-96ca-11e7-8e38-0fb017540b1e.png)
![screenshot_1505107495](https://user-images.githubusercontent.com/7755518/30259570-c1e15724-96ca-11e7-8370-7b51f57477de.png)
