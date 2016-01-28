Download this
http://sourceforge.net/projects/ev3.lejos.p/files/0.9.1-beta/leJOS_EV3_0.9.1-beta.tar.gz/download

Place the extracted file in a folder to hold long term
I use $USER\bin\ but where ever you prefer will work
You will need to uncompress the source files for ev3classes and dbus

Open the project structure menu
Move to the modules tab
On the far right click "Add Content Root"
Move to your files you uncompressed
add /lib/ev3/
Main should no longer show as an error

# Pulled from:
http://www.rapidpm.org/2013/12/27/developing-lejos-programs-with-intelli.html
However the site is out of date, but the jist is still there

# Deploying code
Move to your gradle options on the right of IntelliJ

Expand the *build* folder

Run the *clean* command

Run the *build* command

You may see errors, but if it completes it should be fine for the time being

Expand the *other* folder

Run the *deployEV3* to push the code to the EV3

** Remember to be connect to House Stark for this step **

If these options are not available, try clicking the plus symbol at the top of the gradle projects tab and add the *build.gradle* file from the local code base.


# Tentative plan:
+ Push code via gradle
- Brains talking
- Robot movement
- Basic sensing
- Omni pilot moves
- Omni pilot moves based on sensors