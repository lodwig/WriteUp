import requests

def brute_input(input1, input2, output):
    print(f"[+] {input1}:{input2}:{output}")
    url = "http://activist-birds.picoctf.net:50086/check"
    headers = {"User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:140.0) Gecko/20100101 Firefox/140.0", "Accept": "*/*", "Accept-Language": "en-US,en;q=0.5", "Accept-Encoding": "gzip, deflate, br", "Referer": "http://activist-birds.picoctf.net:50086/", "Content-Type": "application/json", "Origin": "http://activist-birds.picoctf.net:50086", "Connection": "keep-alive", "Priority": "u=0"}
    json={"circuit": [{"input1": input1, "input2": input2, "output": output}]}
    r = requests.post(url, headers=headers, json=json)
    if("wrong answer" in r.text):
        return False
    else:
        print(r.text)
        return True


answer = False
for inp1 in range(1,9):
    for inp2 in range(1,9):
        for out in range(1,9):
            if(brute_input(inp1, inp2, out)):
               answer = True
               break
        if(answer):
            break
    if(answer):
        break