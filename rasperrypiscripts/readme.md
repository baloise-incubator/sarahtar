# Raspberry pi installation scripts
Scripts that u can use should prepare this project running

## Autostart and Display preparation step:

prepare autostart file
- copy the script sarahtar file to /etc/init.d (you can use nano command if you have not sufficient rights to copy file)
- sudo chmod 755 /etc/init.d/sarahtar
---
add to autostart
- sudo update-rc.d sarahtar defaults
---
test if autostart works
- sudo /etc/init.d/sarahtar start
- sudo /etc/init.d/sarahtar stop
---
remove from autostart if needed
- sudo update-rc.d sarahtar remove

## Using command "sr start", "sr stop":

To achieve that, please create new file "sr" in "/usr/bin" and copy the saratar autostart content there, save
then u can use the commands "sr start" and "sr stop" to control the lifecycle of the application