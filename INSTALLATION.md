## File Configuration

- Execute the following command in the application base directory to create a fifo pipe for the player
  ```sh
  mkfifo player_in 
  ```
  
## Network checks
- Network check - connect to Network where the server will serve
  - to check connectivity u can use this command on raspberry pi to check if your ports are accessible:
    ```sh
    nc -l 80
    ```
  - to confirm that your port 22 for ssh is listening u can use that for:
     ```sh
    netstat -tulpn | grep 22
    ```
  - to check connectivity to the raspberry pi on that 80 port u can use curl:
    ```sh
      curl xxx.xxx.xxx.xxx:80
      ```
## Set audio to HDMI (optionally)

      sudo raspi-config

System option
S2. Audio
Select your output. You can choose between hdmi output or jack port of raspberry pi


## Install the mplayer

    sudo apt install mplayer mplayer-gui

and confirm if the dependencies are needed