# M1n10n'5_53cr37 - PicoCTF 

----
Download the APK from the given URL and extract using jadx 
```bash
┌──(kali㉿kali)-[~/PicoCTF/Revese_Engineer/Minion_Secret]
└─$ jadx -d /home/kali/PicoCTF/Revese_Engineer/Minion_Secret/app /home/kali/PicoCTF/Revese_Engineer/Minion_Secret/minions.apk
INFO  - loading ...
INFO  - processing ...
ERROR - finished with errors, count: 17  
```

From `resource/res/values/strings.xml`  found the weird string 
```xml
<string name="Banana">OBUWG32DKRDHWMLUL53TI43OG5PWQNDSMRPXK3TSGR3DG3BRNY4V65DIGNPW2MDCGFWDGX3DGBSDG7I=</string>
```

After some analisys that weird string using encoded base32 so try to decode :
```bash
┌──(kali㉿kali)-[~/PicoCTF/Revese_Engineer/Minion_Secret]
└─$ echo 'OBUWG32DKRDHWMLUL53TI43OG5PWQNDSMRPXK3TSGR3DG3BRNY4V65DIGNPW2MDCGFWDGX3DGBSDG7I=' | base32 -d
picoCTF{1t_w4sn7_h4rd_unr4v3l1n9_th3_m0b1l3_c0d3}
```