# DroidWizard
[![](https://jitpack.io/v/praslnx8/DroidWizard.svg)](https://jitpack.io/#praslnx8/DroidWizard)

Kickstarter MVP framework for android projects

We appreciate developers to be part of the active development community for the DroidWizard framework

# **Views**
#### _CoreFragment_ 
Every Fragments should extend CoreFragment<CP extends CorePresenter> and implement respective callback 

#### _CoreActivity_ 
Every Activity should extend CoreActivity<CP extends CorePresenter> and implement respective callback

#### _CoreAdapter_ 
Every RecyclerView Adapter should extend CoreAdapter<Item, ViewHolder>

# **Presenters**
#### _CorePresenter_ 
Every Views listed above should attached to a presenter that extends CorePresenter<CB extends 
CoreCallBack>

#### _CoreCallBack_ 
The interface that talks to view from presenter

# **Model**
#### _CoreModelEngine_ 
ModelEngines are the one that deals with data make api call and decide what to do with data 
when to fetch from db and when to fetch from Server etc.
 
ModelEngines are singleton class that runs in application context irrespective of state of your views

# Example
Have a look at example app such as SimpleActivity and SimpleFragment that fetch data from stackoverflow website

# Implementation
Step 1. Add the JitPack repository to your build file Build.gradle (app)
```
 repositories {
	maven { url 'https://jitpack.io' }
}
```
Step 2. Add the dependency
```
dependencies {
	compile 'com.github.praslnx8:DroidWizard:beta'
}
```

**Enjoy!**
