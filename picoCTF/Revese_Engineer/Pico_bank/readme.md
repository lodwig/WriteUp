PICO CTF : http://amiable-citadel.picoctf.net:49964/

Download the apk from the website and using jadx to decompile the application 

```bash
┌──(kali㉿kali)-[~/PicoCTF/Revese_Engineer/Pico_bank]
└─$ jadx -d /home/kali/PicoCTF/Revese_Engineer/Pico_bank/picoBank /home/kali/PicoCTF/Revese_Engineer/Pico_bank/pico-bank.apk
INFO  - loading ...
INFO  - processing ...
ERROR - finished with errors, count: 23
```

Looking fot main activity that we found creds `jhonson`
And found OTP value from `res/value/strings.xml
```xml
<string name="otp_value">9673</string>
```

From request on /verify-otp response :
```json
{
    "success":true,
    "message":"OTP verified successfully",
    "flag":"s3cur3d_m0b1l3_l0g1n_976ea739}",
    "hint":"The other part of the flag is hidden in the app"
}
```
From transaction we found that weird numeric transaction amound 
```java
this.transactionList.add(new Transaction("Grocery Shopping", "2023-07-21", "$ 1110000", false));
this.transactionList.add(new Transaction("Electricity Bill", "2023-07-20", "$ 1101001", false));
this.transactionList.add(new Transaction("Salary", "2023-07-18", "$ 1100011", true));
this.transactionList.add(new Transaction("Internet Bill", "2023-07-17", "$ 1101111", false));
this.transactionList.add(new Transaction("Freelance Payment", "2023-07-16", "$ 1000011", true));
this.transactionList.add(new Transaction("Dining Out", "2023-07-15", "$ 1010100", false));
this.transactionList.add(new Transaction("Gym Membership", "2023-07-14", "$ 1000110", false));
this.transactionList.add(new Transaction("Stocks Dividend", "2023-07-13", "$ 1111011", true));
this.transactionList.add(new Transaction("Car Maintenance", "2023-07-12", "$ 110001", false));
this.transactionList.add(new Transaction("Gift Received", "2023-07-11", "$ 1011111", true));
this.transactionList.add(new Transaction("Rent", "2023-07-10", "$ 1101100", false));
this.transactionList.add(new Transaction("Water Bill", "2023-07-09", "$ 110001", false));
this.transactionList.add(new Transaction("Interest Earned", "2023-07-08", "$ 110011", true));
this.transactionList.add(new Transaction("Medical Expenses", "2023-07-07", "$ 1100100", false));
this.transactionList.add(new Transaction("Transport", "2023-07-06", "$ 1011111", false));
this.transactionList.add(new Transaction("Bonus", "2023-07-05", "$ 110100", true));
this.transactionList.add(new Transaction("Subscription Service", "2023-07-04", "$ 1100010", false));
this.transactionList.add(new Transaction("Freelance Payment", "2023-07-03", "$ 110000", true));
this.transactionList.add(new Transaction("Entertainment", "2023-07-02", "$ 1110101", false));
this.transactionList.add(new Transaction("Groceries", "2023-07-01", "$ 1110100", false));
this.transactionList.add(new Transaction("Insurance Premium", "2023-06-28", "$ 1011111", false));
this.transactionList.add(new Transaction("Charity Donation", "2023-06-26", "$ 1100010", true));
this.transactionList.add(new Transaction("Vacation Expense", "2023-06-26", "$ 110011", false));
this.transactionList.add(new Transaction("Home Repairs", "2023-06-24", "$ 110001", false));
this.transactionList.add(new Transaction("Pet Care", "2023-06-22", "$ 1101110", false));
this.transactionList.add(new Transaction("Personal Loan", "2023-06-18", "$ 1100111", true));
this.transactionList.add(new Transaction("Childcare", "2023-06-15", "$ 1011111", false));
```
And using this we get the binary :
```bash
┌──(kali㉿kali)-[~/PicoCTF/Revese_Engineer/Pico_bank]
└─$ cat temp.txt | awk -F'$' '{print $2}' | awk -F'"' '{print $1}'
 1110000
 1101001
 1100011
 1101111
 1000011
 1010100
 1000110
 1111011
 110001
 1011111
 1101100
 110001
 110011
 1100100
 1011111
 110100
 1100010
 110000
 1110101
 1110100
 1011111
 1100010
 110011
 110001
 1101110
 1100111
 1011111
```

After using this website to convert binary to ASCII `https://www.rapidtables.com/convert/number/binary-to-string.html`
Found the others flag is : `picoCTF{1_l13d_4b0ut_b31ng_`

And combined with the flag from OTP is :
```bash
picoCTF{1_l13d_4b0ut_b31ng_s3cur3d_m0b1l3_l0g1n_976ea739}
```