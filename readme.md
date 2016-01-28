# leJOS setup on your machine
Download [version 9.1 of leJOS] (http://sourceforge.net/projects/ev3.lejos.p/files/0.9.1-beta/leJOS_EV3_0.9.1-beta.tar.gz/download)

Place the extracted file in a folder to hold long term

*I use $USER\bin\ but where ever you prefer will work*

You will need to uncompress the source files for *ev3classes* and *dbus*

Inside IntelliJ, open *File > Project Structure* menu (If you have the toolbar displayed, it's the half blue half grey icon)

Move to the *Modules* tab

On the far right click 'Add Content Root'

Move to your files you uncompressed

Add the base leJOS folder and IntelliJ should find the source code for *dbusjava-src* and *ev3classes-src*

Inside the class *Main*, errors should no longer show

## Sites referenced, if you are having problems these may help:
http://www.rapidpm.org/2013/12/27/developing-lejos-programs-with-intelli.html

https://github.com/jornfranke/lejos-ev3-example

# Deploying code:
Move to your gradle options on the right of IntelliJ

Expand the *build* folder

Run the *clean* command

Run the *build* command

You may see errors, but if it completes it should be fine for the time being

Expand the *other* folder

Run the *deployEV3* to push the code to the EV3

**Remember to be connect to the wireless AP *House Stark* for this step**

If these options are not available, try clicking the plus symbol at the top of the gradle projects tab and add the *build.gradle* file from the local code base.


# Tentative plan:
- [x] Push code via gradle
- [ ] Brains talking
- [ ] Robot movement
- [ ] Basic sensing
- [ ] Omni pilot moves
- [ ] Omni pilot moves based on sensors