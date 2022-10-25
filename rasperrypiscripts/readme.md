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