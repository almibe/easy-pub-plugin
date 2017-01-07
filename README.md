# easy-pub-plugin
A gradle plugin that attempts to apply sane defaults to make bintray publication less verbose.

##Required Configuration
This plugin tries to limit the user to only needing to set up the bare minimum of configuration.  As a result of this
there are only two places where you need to set up configuration.  One is most likely global for using bintray and the
other is specific for each project.

###Global Bintray Settings
If you have read through the [gradle-bintray-plugin](https://github.com/bintray/gradle-bintray-plugin) documentation
you should already know about setting up your binray username and key in your ~/.gradle/gradle.properties file.

###Project Settings
For the two settings that can't be figured out using common gradle settings that are required by jcenter (vcsUrl
and licenses), this plugin expects those to be set in project properties.  The easiest way to do this is to set them
in the gradle.properties file for that particular project.

##Demo
No need for separate demo, this project uses the plugin itself.  See build.gradle and gradle.properties.  Notice that
you will need to enable jcenter repository to use this plugin.
